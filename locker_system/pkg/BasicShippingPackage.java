package locker_system.pkg;

import locker_system.account.Account;
import locker_system.locker.LockerSize;

import java.math.BigDecimal;


public class BasicShippingPackage implements ShippingPackage {
    private final String orderId;
    private final Account user;
    private final BigDecimal width;
    private final BigDecimal height;
    private final BigDecimal depth;
    private ShippingStatus status;

    public BasicShippingPackage(String orderId, Account user, BigDecimal width, BigDecimal height, BigDecimal depth) {
        this.orderId = orderId;
        this.user = user;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.status = ShippingStatus.CREATED;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public Account getUser() {
        return user;
    }

    @Override
    public BigDecimal getWidth() {
        return width;
    }

    @Override
    public BigDecimal getHeight() {
        return height;
    }

    @Override
    public BigDecimal getDepth() {
        return depth;
    }

    @Override
    public ShippingStatus getStatus() {
        return status;
    }

    @Override
    public void updateShippingStatus(ShippingStatus status) {
        this.status = status;
    }

    @Override
    public LockerSize getLockerSize() {
        for(LockerSize size : LockerSize.values()) {
            if(size.getWidth().compareTo(width) >= 0 && 
               size.getHeight().compareTo(height) >= 0 && 
               size.getDepth().compareTo(depth) >= 0) {
                return size;
            }
        }
        throw new PackageIncompatibleException("No locker size available for the package");
    }
}
