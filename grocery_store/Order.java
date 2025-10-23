package grocery_store;

import grocery_store.discount.DiscountCampaign;

import java.math.BigDecimal;
import java.util.*;

public class Order {
    private final String orderId;
    private final List<OrderItem> items = new ArrayList<>();
    private final Map<OrderItem, DiscountCampaign> appliedDiscounts = new HashMap<>();
    private BigDecimal paymentAmount = BigDecimal.ZERO;

    public Order() {
        this.orderId = String.valueOf(UUID.randomUUID());
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public BigDecimal calculateSubtotal() {
        return items.stream()
                .map(OrderItem::calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotal() {
        return items.stream()
                .map(item -> {
                    DiscountCampaign discount = appliedDiscounts.get(item);
                    return discount != null ? item.calculatePriceWithDiscount(discount) : item.calculatePrice();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void applyDiscount(OrderItem item, DiscountCampaign discount) {
        appliedDiscounts.put(item, discount);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setPayment(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public BigDecimal calculateChange() {
        return paymentAmount.subtract(calculateTotal());
    }

    public Map<OrderItem, DiscountCampaign> getAppliedDiscounts() {
        return appliedDiscounts;
    }

    public String getOrderId() {
        return orderId;
    }
}
