package locker_system.pkg;


public class MaximumStoragePeriodExceededException extends RuntimeException {
    public MaximumStoragePeriodExceededException(String message) {
        super(message);
    }
} 
