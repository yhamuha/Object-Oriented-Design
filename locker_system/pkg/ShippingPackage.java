package locker_system.pkg;

import locker_system.account.Account;
import locker_system.locker.LockerSize;

import java.math.BigDecimal;


public interface ShippingPackage {
    String getOrderId();

    Account getUser();

    BigDecimal getWidth();

    BigDecimal getHeight();

    BigDecimal getDepth();

    ShippingStatus getStatus();

    void updateShippingStatus(ShippingStatus status);

    LockerSize getLockerSize();
}
