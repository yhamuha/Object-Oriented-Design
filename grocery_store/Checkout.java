package grocery_store;

import grocery_store.discount.DiscountCampaign;

import java.math.BigDecimal;
import java.util.List;

public class Checkout {
    private Order currentOrder;
    private final List<DiscountCampaign> activeDiscounts;

    public Checkout(List<DiscountCampaign> activeDiscounts) {
        this.activeDiscounts = activeDiscounts;
        startNewOrder();
    }

    public void startNewOrder() {
        this.currentOrder = new Order();
    }

    public BigDecimal processPayment(BigDecimal paymentAmount) {
        currentOrder.setPayment(paymentAmount);
        return currentOrder.calculateChange();
    }

    public void addItemToOrder(Item item, int quantity) {
        OrderItem orderItem = new OrderItem(item, quantity);
        currentOrder.addItem(orderItem);

        for (DiscountCampaign newDiscount : activeDiscounts) {
            if (newDiscount.isApplicable(item)) {
                if (currentOrder.getAppliedDiscounts().containsKey(orderItem)) {
                    DiscountCampaign existingDiscount = currentOrder.getAppliedDiscounts().get(orderItem);
                    if (orderItem.calculatePriceWithDiscount(newDiscount)
                            .compareTo(orderItem.calculatePriceWithDiscount(existingDiscount)) > 0) {
                        currentOrder.applyDiscount(orderItem, newDiscount);
                    }
                } else {
                    currentOrder.applyDiscount(orderItem, newDiscount);
                }
            }
        }
    }

    public Receipt getReceipt() {
        return new Receipt(currentOrder);
    }

    public BigDecimal getOrderTotal() {
        return currentOrder.calculateTotal();
    }
}
