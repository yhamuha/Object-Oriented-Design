package locker_system.locker;

import locker_system.account.AccountLockerPolicy;
import locker_system.pkg.MaximumStoragePeriodExceededException;
import locker_system.pkg.ShippingPackage;
import locker_system.pkg.ShippingStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class Locker {
    private final LockerSize size;
    private ShippingPackage currentPackage;
    private Date assignmentDate;
    private String accessCode;

    public Locker(LockerSize size) {
        this.size = size;
    }

    public void assignPackage(ShippingPackage pkg, Date date) {
        this.currentPackage = pkg;
        this.assignmentDate = date;
        this.accessCode = generateAccessCode();
    }

    public void releaseLocker() {
        this.currentPackage = null;
        this.assignmentDate = null;
        this.accessCode = null;
    }

    public BigDecimal calculateStorageCharges() {
        if (currentPackage == null || assignmentDate == null) {
            return BigDecimal.ZERO;
        }
        
        AccountLockerPolicy policy = currentPackage.getUser().getLockerPolicy();
        long totalDaysUsed = (new Date().getTime() - assignmentDate.getTime()) / (1000 * 60 * 60 * 24);
        
        if (totalDaysUsed > policy.getMaximumPeriodDays()) {
            currentPackage.updateShippingStatus(ShippingStatus.EXPIRED);
            throw new MaximumStoragePeriodExceededException(
                "Package has exceeded maximum allowed storage period of " + policy.getMaximumPeriodDays() + " days");
        }
        
        long chargeableDays = Math.max(0, totalDaysUsed - policy.getFreePeriodDays());
        return size.dailyCharge.multiply(new BigDecimal(chargeableDays));
    }

    public boolean isAvailable() {
        return currentPackage == null;
    }

    public boolean checkAccessCode(String code) {
        return this.accessCode != null && accessCode.equals(code);
    }

    private String generateAccessCode() {
        Random random = new Random();
        int accessCode = 100000 + random.nextInt(900000);
        return String.valueOf(accessCode);
    }

    public String getAccessCode() {
        return accessCode;
    }

    public LockerSize getSize() {
        return this.size;
    }

    public ShippingPackage getPackage() {
        return currentPackage;
    }

    public void setPackage(ShippingPackage pkg) {
        this.currentPackage = pkg;
    }

    public ShippingPackage getCurrentPackage() {
        return currentPackage;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setCurrentPackage(ShippingPackage currentPackage) {
        this.currentPackage = currentPackage;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
