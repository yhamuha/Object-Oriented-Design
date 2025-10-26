package locker_system.account;


public class AccountLockerPolicy {
    final int freePeriodDays;
    final int maximumPeriodDays;

    public AccountLockerPolicy(int freePeriodDays, int maximumPeriodDays) {
        this.freePeriodDays = freePeriodDays;
        this.maximumPeriodDays = maximumPeriodDays;
    }

    public int getFreePeriodDays() {
        return freePeriodDays;
    }

    public int getMaximumPeriodDays() {
        return maximumPeriodDays;
    }
}
