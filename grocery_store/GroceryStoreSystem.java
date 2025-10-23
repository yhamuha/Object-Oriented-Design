package grocery_store;

import grocery_store.discount.DiscountCampaign;

import java.util.ArrayList;
import java.util.List;

public class GroceryStoreSystem {
    private final Catalog catalog;
    private final Inventory inventory;
    private List<DiscountCampaign> activeDiscounts = new ArrayList<>();
    private final Checkout checkout;

    public GroceryStoreSystem(){
        this.catalog = new Catalog();
        this.inventory = new Inventory();
        this.checkout = new Checkout(activeDiscounts);
    }

    public GroceryStoreSystem(Catalog catalog, Inventory inventory, List<DiscountCampaign> activeDiscounts) {
        this.catalog = catalog;
        this.inventory = inventory;
        this.activeDiscounts = activeDiscounts;
        this.checkout = new Checkout(activeDiscounts);
    }

    public void addOrUpdateItem(Item item) {
        catalog.updateItem(item);
    }

    public void updateInventory(String barcode, int count) {
        inventory.addStock(barcode, count);
    }

    public void addDiscountCampaign(DiscountCampaign discount) {
        activeDiscounts.add(discount);
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public Item getItemByBarcode(String barcode) {
        return catalog.getItem(barcode);
    }

    public void removeItem(String barcode) {
        catalog.removeItem(barcode);
    }
}



