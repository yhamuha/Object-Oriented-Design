package grocery_store.discount.strategy;

import java.math.BigDecimal;

public interface DiscountCalculationStrategy {
    BigDecimal calculateDiscountedPrice(BigDecimal originalPrice);
}

