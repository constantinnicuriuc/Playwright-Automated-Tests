package testStructure.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutOverviewPage {
    private Page page;
    private Locator checkoutLocator;
    private Locator finishCheckoutLocator;
    private Locator cancelLocator;

    public CheckoutOverviewPage(Page page) {
        this.page = page;
    }

    public void verifyProductByName(String productName) {
        assertThat(this.page.locator("xpath=//div[text()='" + productName + "']")).isVisible();
    }

    public String getCheckoutInfo(String checkoutSection) {
        checkoutLocator = this.page.locator(checkoutSection);
        return checkoutLocator.textContent();
    }

    public void returnToHomePage() {
        this.cancelLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Go back Cancel"));
        this.cancelLocator.click();
    }

    public void finishCheckout() {
        this.finishCheckoutLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Finish"));
        finishCheckoutLocator.click();
    }

}
