package testStructure.utils;

public enum Products {
    BACKPACK(29.99, "Sauce Labs Backpack", 1),
    BIKE_LIGHT(9.99, "Sauce Labs Bike Light", 2),
    T_SHIRT(15.99, "Sauce Labs Bolt T-Shirt", 3),
    FLEECE_JACKET(49.99, "Sauce Labs Fleece Jacket", 4),
    ONESIE(7.99, "Sauce Labs Onesie", 5),
    T_SHIT_ALL_THE_THINGS(15.99, "Test.allTheThings() T-Shirt (Red)", 6);

    private final double price;
    private final String itemName;
    private final int itemPosition;

    Products(double price, String itemName, int itemPosition) {
        this.price = price;
        this.itemName = itemName;
        this.itemPosition = itemPosition;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceAsText() {
        return "$" + price;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPosition() {
        return itemPosition;
    }
}
