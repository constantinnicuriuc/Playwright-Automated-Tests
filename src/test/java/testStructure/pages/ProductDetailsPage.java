package testStructure.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductDetailsPage {
    private final Page page;
    private Locator productLocator;

    public ProductDetailsPage(Page page) {
        this.page = page;
    }

    public void verifyProductVisibility(String productName) {
        assertThat(this.page.locator(
                String.format("xpath=//div[contains(@class,'inventory_details_name') and text()='%s']", productName))).isVisible();
    }

    public void addProductToCart() {
        this.productLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart"));
        this.productLocator.click();
    }
}
