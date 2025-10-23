package grocery_store.discount;

import grocery_store.Item;
import grocery_store.OrderItem;
import grocery_store.discount.criteria.DiscountCriteria;
import grocery_store.discount.strategy.DiscountCalculationStrategy;

import java.math.BigDecimal;

public class DiscountCampaign {
    private final String discountId;
    private final String name;
    private final DiscountCriteria criteria;
    private final DiscountCalculationStrategy calculationStrategy;

    public DiscountCampaign(String discountId,
                            String name,
                            DiscountCriteria criteria,
                            DiscountCalculationStrategy calculationStrategy) {
        this.discountId = discountId;
        this.name = name;
        this.criteria = criteria;
        this.calculationStrategy = calculationStrategy;
    }

    public boolean isApplicable(Item item) {
        return criteria.isApplicable(item);
    }

    public BigDecimal calculateDiscount(OrderItem item) {
        return calculationStrategy.calculateDiscountedPrice(item.calculatePrice());
    }

    public String getName() {
        return name;
    }

    public String getDiscountId() {
        return discountId;
    }
}
