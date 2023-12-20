package testStructure.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutCompletePage {
    private Page page;
    private Locator orderCompleteLocator;
    private Locator returnHomeLocator;

    public CheckoutCompletePage(Page page) {
        this.page = page;
    }

    public void verifyOrderCompletion() {
        this.orderCompleteLocator = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Thank you for your Order!"));
        assertThat(orderCompleteLocator).isVisible();
    }

    public void returnToHomePage() {
        this.returnHomeLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Back Home"));
        this.returnHomeLocator.click();
    }
}
