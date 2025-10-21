package grocery_store.discount.criteria;

import grocery_store.Item;

public interface DiscountCriteria {
    boolean isApplicable(Item item);
}

