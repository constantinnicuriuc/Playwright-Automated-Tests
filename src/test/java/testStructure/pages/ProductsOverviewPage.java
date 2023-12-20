package testStructure.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.List;

public class ProductsOverviewPage {

    private Page page;
    private Locator productLocator;

    public ProductsOverviewPage(Page page) {

        this.page = page;
    }

    public void selectProduct(String productName) {
        this.productLocator = page.locator("xpath=//a/div[text()='" + productName + "']");
        this.productLocator.click();
    }

    public String getProductPrice(int itemNumber) {
        this.productLocator = page.locator("#inventory_container > div > div:nth-child(" + itemNumber + ") > div.inventory_item_description > div.pricebar > div");
        return productLocator.textContent();
    }

}
