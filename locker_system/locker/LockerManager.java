package locker_system.locker;

import locker_system.*;
import locker_system.account.Account;
import locker_system.pkg.MaximumStoragePeriodExceededException;
import locker_system.pkg.ShippingPackage;
import locker_system.pkg.ShippingStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LockerManager {
    private final Site site;
    private final NotificationInterface notificationService;
    private final Map<String, Account> accounts;
    private final Map<String, Locker> accessCodeMap = new HashMap<>();

    public LockerManager(Site site, Map<String, Account> accounts, NotificationInterface notificationService) {
        this.site = site;
        this.accounts = accounts;
        this.notificationService = notificationService;
    }

    public Locker assignPackage(ShippingPackage pkg, Date date) {
        Locker locker = site.placePackage(pkg, date);
        if (locker != null) {
            accessCodeMap.put(locker.getAccessCode(), locker);
            notificationService.sendNotification("Package assigned to locker" + locker.getAccessCode(), pkg.getUser());
        }
        return locker;
    }

    public Locker pickUpPackage(String accessCode) {
        Locker locker = accessCodeMap.get(accessCode);
        if (locker != null && locker.checkAccessCode(accessCode)) {
            try {
                BigDecimal charge = locker.calculateStorageCharges();
                ShippingPackage pkg = locker.getPackage();
                locker.releaseLocker();
                pkg.getUser().addUsageCharge(charge);
                pkg.updateShippingStatus(ShippingStatus.RETRIEVED);
                return locker;
            } catch (MaximumStoragePeriodExceededException e) {
                locker.releaseLocker();
                return locker;
            }
        }
        return null;
    }

    public Account getAccount(String testAccount) {
        return accounts.get(testAccount);
    }
}


