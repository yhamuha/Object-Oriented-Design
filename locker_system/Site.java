package locker_system;

import locker_system.locker.Locker;
import locker_system.locker.LockerSize;
import locker_system.pkg.NoLockerAvailableException;
import locker_system.pkg.ShippingPackage;
import locker_system.pkg.ShippingStatus;

import java.util.*;


public class Site {
    final Map<LockerSize, Set<Locker>> lockers = new HashMap<>();

    public Site(Map<LockerSize, Integer> lockers) {
        for (Map.Entry<LockerSize, Integer> entry : lockers.entrySet()) {
            Set<Locker> lockerSet = new HashSet<>();
            for (int i = 0; i < entry.getValue(); i++) {
                lockerSet.add(new Locker(entry.getKey()));
            }
            this.lockers.put(entry.getKey(), lockerSet);
        }
    }

    public Locker findAvailableLocker(LockerSize size) {
        for (Locker locker : lockers.get(size)) {
            if (locker.isAvailable()) {
                return locker;
            }
        }
        return null;
    }

    public Locker placePackage(ShippingPackage pkg, Date date) {
        LockerSize size = pkg.getLockerSize();
        Locker locker = findAvailableLocker(size);
        if (locker != null) {
            locker.assignPackage(pkg, date);
            pkg.updateShippingStatus(ShippingStatus.IN_LOCKER);
            return locker;
        } 
        throw new NoLockerAvailableException("No locker of size " + size + " is currently available");
    }
}
