package grocery_store.discount.strategy;

import java.math.BigDecimal;

public class PercentageBasedStrategy implements DiscountCalculationStrategy {
    private final BigDecimal percentage;

    public PercentageBasedStrategy(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public BigDecimal calculateDiscountedPrice(BigDecimal originalPrice) {
        return originalPrice.multiply(BigDecimal.ONE.subtract(percentage.divide(BigDecimal.valueOf(100))));
    }
}
