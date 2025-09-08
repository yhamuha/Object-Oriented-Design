package vending_machine;

public class InvalidTransactionException extends Throwable {
    public InvalidTransactionException(String message) {
        super(message);
    }
}
