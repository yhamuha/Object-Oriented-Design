package file_search.operator;

// Implements less than comparison for numeric values
class LessThanOperator<T extends Number> implements ComparisonOperator<T> {
    @Override
    public boolean isMatch(final T attributeValue, final T expectedValue) {
        return Double.compare(attributeValue.doubleValue(), expectedValue.doubleValue()) < 0;
    }
}
