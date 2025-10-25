package locker_system.locker;

import java.math.BigDecimal;

public enum LockerSize {

    SMALL("Small", new BigDecimal("5.00"), new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("10.00")),
    MEDIUM("Medium", new BigDecimal("10.00"), new BigDecimal("20.00"), new BigDecimal("20.00"), new BigDecimal("20.00")),
    LARGE("Large", new BigDecimal("15.00"), new BigDecimal("30.00"), new BigDecimal("30.00"), new BigDecimal("30.00"));

    final String sizeName;
    final BigDecimal dailyCharge;
    final BigDecimal width;
    final BigDecimal height;
    final BigDecimal depth;

    LockerSize(String sizeName, BigDecimal dailyCharge, BigDecimal width, BigDecimal height, BigDecimal depth) {
        this.sizeName = sizeName;
        this.dailyCharge = dailyCharge;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getDepth() {
        return depth;
    }

    public BigDecimal getHeight() {
        return height;
    }
}
