package testStructure.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ShoppingCartPage {
    private Page page;
    private Locator productLocator;
    private Locator continueShoppingLocator;
    private Locator checkoutLocator;

    public ShoppingCartPage(Page page) {
        this.page = page;
    }

    public void openShoppingCart() {
        this.productLocator = page.locator("a.shopping_cart_link");
        this.productLocator.click();
    }

    public void verifyProductByName(String productName) {
        assertThat(this.page.locator("xpath=//div[text()='" + productName + "']")).isVisible();
    }

    public void removeProductFromCart() {
        this.productLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove"));
        this.productLocator.click();
    }

    public void verifyProductIsRemoved() {
        assertThat(this.page.locator("xpath=//div[contains(@class,'removed_cart_item')]")).isAttached();
    }

    public void returnToProductOverviewPage() {
        this.continueShoppingLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Go back Continue Shopping"));
        this.continueShoppingLocator.click();
    }

    public void proceedToCheckout() {
        this.checkoutLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout"));
        this.checkoutLocator.click();
    }
}
