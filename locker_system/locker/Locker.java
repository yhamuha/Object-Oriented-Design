package locker_system.locker;

import java.util.Date;
import java.util.Random;

public class Locker {
    private final LockerSize size;
    private Date assignmentDate;
    private String accessCode;

    public Locker(LockerSize size) {
        this.size = size;
    }

    public boolean checkAccessCode(String code) {
        return this.accessCode != null && accessCode.equals(code);
    }

    private String generateAccessCode() {
        Random random = new Random();
        int accessCode = 100000 + random.nextInt(900000);
        return String.valueOf(accessCode);
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
