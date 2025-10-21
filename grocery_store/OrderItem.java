package grocery_store;

import grocery_store.discount.DiscountCampaign;

import java.math.BigDecimal;

public class OrderItem {
    private final Item item;
    private final int quantity;

    public OrderItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public BigDecimal calculatePrice() {
        return item.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal calculatePriceWithDiscount(DiscountCampaign newDiscount) {
        return newDiscount.calculateDiscount(this);
    }
}
