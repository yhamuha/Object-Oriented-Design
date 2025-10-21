package grocery_store.discount.criteria;

import grocery_store.Item;

public class CategoryBasedCriteria implements DiscountCriteria {
    private final String category;

    public CategoryBasedCriteria(String category) {
        this.category = category;
    }

    @Override
    public boolean isApplicable(Item item) {
        return item.getCategory().equals(category);
    }
}
