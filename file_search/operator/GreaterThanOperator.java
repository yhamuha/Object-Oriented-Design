package file_search.operator;

// Implements greater than comparison for numeric values
class GreaterThanOperator<T extends Number> implements ComparisonOperator<T> {
    @Override
    public boolean isMatch(final T attributeValue, final T expectedValue) {
        return Double.compare(attributeValue.doubleValue(), expectedValue.doubleValue()) > 0;
    }
}
