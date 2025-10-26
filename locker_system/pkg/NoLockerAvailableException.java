package locker_system.pkg;

public class NoLockerAvailableException extends RuntimeException {
    public NoLockerAvailableException(String message) {
        super(message);
    }
} 
