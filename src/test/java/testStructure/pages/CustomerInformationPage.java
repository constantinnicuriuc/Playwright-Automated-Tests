package testStructure.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CustomerInformationPage {
    private Page page;
    private Locator firstNameLocator;
    private Locator lastNameLocator;
    private Locator zipCodeLocator;
    private Locator continueLocator;
    private Locator cancelLocator;

    public CustomerInformationPage(Page page) {
        this.page = page;
        this.firstNameLocator = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name"));
        this.lastNameLocator = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name"));
        this.zipCodeLocator = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Zip/Postal Code"));
    }

    public void fillCheckoutDetails(String firstName, String lastName, String zipCode) {
        this.firstNameLocator.fill(firstName);
        this.lastNameLocator.fill(lastName);
        this.zipCodeLocator.fill(zipCode);
    }

    public void continueToNextPage() {
        this.continueLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue"));
        this.continueLocator.click();
    }

    public void returnToHomePage() {
        this.cancelLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Go back Cancel"));
        this.cancelLocator.click();
    }
}
