package testStructure.utils;

public enum CheckoutSectionInfo {
    PAYMENT_INFORMATION(".summary_info > div:nth-child(2)"),
    SHIPPING_INFORMATION(".summary_info > div:nth-child(4)"),
    ITEM_TOTAL(".summary_info > div:nth-child(6)"),
    TAX(".summary_info > div:nth-child(7)");

    private final String locator;

    CheckoutSectionInfo(String locator) {
        this.locator = locator;
    }

    public String getSectionLocator() {
        return locator;
    }
}
