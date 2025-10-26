package locker_system.pkg;

public class PackageIncompatibleException extends RuntimeException {
    public PackageIncompatibleException(String s) {
        super(s);
    }
}
