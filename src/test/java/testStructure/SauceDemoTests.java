package testStructure;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testStructure.pages.CheckoutCompletePage;
import testStructure.pages.CheckoutOverviewPage;
import testStructure.pages.CustomerInformationPage;
import testStructure.pages.LoginPage;
import testStructure.pages.ProductDetailsPage;
import testStructure.pages.ProductsOverviewPage;
import testStructure.pages.ShoppingCartPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testStructure.utils.CheckoutSectionInfo;
import testStructure.utils.Products;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoTests {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private ProductsOverviewPage productsOverviewPage;
    private ProductDetailsPage productDetailsPage;
    private ShoppingCartPage shoppingCartPage;
    private CustomerInformationPage customerInformationPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;
    private LoginPage loginPage;

    @BeforeEach
    public void startSession() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com");
    }

    @AfterEach
    public void closeSession() {
        browser.close();
        playwright.close();
    }

    @Test
    public void testLogin_lockedOutUser() {
        // Login with a locked out user
        loginPage = new LoginPage(page);
        loginPage.loginAs("locked_out_user", "secret_sauce");
        loginPage.validateLockedOutError();
    }

    @ParameterizedTest(name = "{index} => Product: ''{0}'', Price: ''{1}''")
    @MethodSource("addItemToCart_arguments")
    public void testAdd_itemToCart(String productName, String productPrice, int itemPosition) {
        // Login user
        loginPage = new LoginPage(page);
        loginPage.loginAs("standard_user", "secret_sauce");

        // Verify the page is loaded
        assertThat(page.getByText("Products")).isVisible();

        // Select a product
        productsOverviewPage = new ProductsOverviewPage(page);
        Assertions.assertEquals(productsOverviewPage.getProductPrice(itemPosition), (productPrice));
        productsOverviewPage.selectProduct(productName);

        // Add the item to cart
        productDetailsPage = new ProductDetailsPage(page);
        productDetailsPage.verifyProductVisibility(productName);
        productDetailsPage.addProductToCart();
    }

    @Test
    public void testRemove_itemFromCart() {
        // Login user
        loginPage = new LoginPage(page);
        loginPage.loginAs("standard_user", "secret_sauce");

        // Verify the page is loaded
        assertThat(page.getByText("Products")).isVisible();

        // Select a product
        productsOverviewPage = new ProductsOverviewPage(page);
        Assertions.assertEquals(productsOverviewPage.getProductPrice(Products.BACKPACK.getItemPosition()), (Products.BACKPACK.getPriceAsText()));
        productsOverviewPage.selectProduct(Products.BACKPACK.getItemName());

        // Add the item to cart
        productDetailsPage = new ProductDetailsPage(page);
        productDetailsPage.verifyProductVisibility(Products.BACKPACK.getItemName());
        productDetailsPage.addProductToCart();

        // Verify the shopping cart and remove the added item
        shoppingCartPage = new ShoppingCartPage(page);
        shoppingCartPage.openShoppingCart();
        shoppingCartPage.verifyProductByName(Products.BACKPACK.getItemName());
        shoppingCartPage.removeProductFromCart();
        shoppingCartPage.verifyProductIsRemoved();
    }

    @Test
    public void testAdd_multipleItemsToCart() {
        // Login user
        loginPage = new LoginPage(page);
        loginPage.loginAs("standard_user", "secret_sauce");

        // Verify the page is loaded
        assertThat(page.getByText("Products")).isVisible();

        // Select a product
        productsOverviewPage = new ProductsOverviewPage(page);
        Assertions.assertEquals(productsOverviewPage.getProductPrice(Products.BACKPACK.getItemPosition()), (Products.BACKPACK.getPriceAsText()));
        productsOverviewPage.selectProduct(Products.BACKPACK.getItemName());

        // Add the item to cart
        productDetailsPage = new ProductDetailsPage(page);
        productDetailsPage.verifyProductVisibility(Products.BACKPACK.getItemName());
        productDetailsPage.addProductToCart();

        // Verify the shopping cart
        shoppingCartPage = new ShoppingCartPage(page);
        shoppingCartPage.openShoppingCart();
        shoppingCartPage.verifyProductByName(Products.BACKPACK.getItemName());
        shoppingCartPage.returnToProductOverviewPage();

        // Select an additional product
        Assertions.assertEquals(productsOverviewPage.getProductPrice(Products.ONESIE.getItemPosition()), (Products.ONESIE.getPriceAsText()));
        productsOverviewPage.selectProduct(Products.ONESIE.getItemName());

        // Add the additional item to cart
        productDetailsPage.verifyProductVisibility(Products.ONESIE.getItemName());
        productDetailsPage.addProductToCart();

        // Verify the shopping cart
        shoppingCartPage.openShoppingCart();
        shoppingCartPage.verifyProductByName(Products.ONESIE.getItemName());
        shoppingCartPage.verifyProductByName(Products.BACKPACK.getItemName());
    }

    @Test
    public void testSuccessfulCheckout() {
        // Login user
        loginPage = new LoginPage(page);
        loginPage.loginAs("standard_user", "secret_sauce");

        // Verify the page is loaded
        assertThat(page.getByText("Products")).isVisible();

        // Select a product
        productsOverviewPage = new ProductsOverviewPage(page);
        Assertions.assertEquals(productsOverviewPage.getProductPrice(Products.FLEECE_JACKET.getItemPosition()), (Products.FLEECE_JACKET.getPriceAsText()));
        productsOverviewPage.selectProduct(Products.FLEECE_JACKET.getItemName());

        // Add the item to cart
        productDetailsPage = new ProductDetailsPage(page);
        productDetailsPage.verifyProductVisibility(Products.FLEECE_JACKET.getItemName());
        productDetailsPage.addProductToCart();

        // Verify the shopping cart and proceed to checkout
        shoppingCartPage = new ShoppingCartPage(page);
        shoppingCartPage.openShoppingCart();
        shoppingCartPage.verifyProductByName(Products.FLEECE_JACKET.getItemName());
        shoppingCartPage.proceedToCheckout();

        // Fill in customer information
        customerInformationPage = new CustomerInformationPage(page);
        customerInformationPage.fillCheckoutDetails("FirstName", "LastName", UUID.randomUUID().toString());
        customerInformationPage.continueToNextPage();

        // Verify the checkout overview details
        checkoutOverviewPage = new CheckoutOverviewPage(page);
        checkoutOverviewPage.verifyProductByName(Products.FLEECE_JACKET.getItemName());
        assertTrue(checkoutOverviewPage
                .getCheckoutInfo(CheckoutSectionInfo.PAYMENT_INFORMATION.getSectionLocator())
                .contains("SauceCard"));
        assertTrue(checkoutOverviewPage
                .getCheckoutInfo(CheckoutSectionInfo.SHIPPING_INFORMATION.getSectionLocator())
                .contains("Free Pony Express Delivery!"));
        assertTrue(checkoutOverviewPage
                .getCheckoutInfo(CheckoutSectionInfo.ITEM_TOTAL.getSectionLocator())
                .contains(Products.FLEECE_JACKET.getPriceAsText()));
        checkoutOverviewPage.finishCheckout();

        // Verify that the checkout has been successful
        checkoutCompletePage = new CheckoutCompletePage(page);
        checkoutCompletePage.verifyOrderCompletion();
    }

    private static Stream<Arguments> addItemToCart_arguments() {
        return Stream.of(
                Arguments.of(Products.T_SHIRT.getItemName(), Products.T_SHIRT.getPriceAsText(), Products.T_SHIRT.getItemPosition()),
                Arguments.of(Products.BIKE_LIGHT.getItemName(), Products.BIKE_LIGHT.getPriceAsText(), Products.BIKE_LIGHT.getItemPosition()),
                Arguments.of(Products.ONESIE.getItemName(), Products.ONESIE.getPriceAsText(), Products.ONESIE.getItemPosition()),
                Arguments.of(Products.FLEECE_JACKET.getItemName(), Products.FLEECE_JACKET.getPriceAsText(), Products.FLEECE_JACKET.getItemPosition()),
                Arguments.of(Products.T_SHIT_ALL_THE_THINGS.getItemName(), Products.T_SHIT_ALL_THE_THINGS.getPriceAsText(), Products.T_SHIT_ALL_THE_THINGS.getItemPosition()),
                Arguments.of(Products.BACKPACK.getItemName(), Products.BACKPACK.getPriceAsText(), Products.BACKPACK.getItemPosition())
        );
    }
}
