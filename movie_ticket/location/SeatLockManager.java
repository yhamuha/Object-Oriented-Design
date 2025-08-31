package movie_ticket.location;

import movie_ticket.location.Seat;
import movie_ticket.showing.Screening;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SeatLockManager {
    private final Map<String, SeatLock> lockedSeats = new ConcurrentHashMap<>();
    private final Duration lockDuration;
    
    public SeatLockManager(Duration lockDuration) {
        this.lockDuration = lockDuration;
    }
    
    public synchronized boolean lockSeat(Screening screening, Seat seat, String userId) {
        String lockKey = generateLockKey(screening, seat);
        
        // Clean up lock if expired (on-demand cleanup when another process attempts to lock)
        cleanupLockIfExpired(lockKey);
        // Check if a seat is already locked
        if (isLocked(screening, seat)) {
            return false;
        }
        
        // Create a new lock with expiration time
        SeatLock lock = new SeatLock(userId, LocalDateTime.now().plus(lockDuration));
        lockedSeats.put(lockKey, lock);
        return true;
    }
    
    public synchronized boolean isLocked(Screening screening, Seat seat) {
        String lockKey = generateLockKey(screening, seat);
        
        // Clean up lock if expired (on-demand cleanup)
        cleanupLockIfExpired(lockKey);
        
        // If we reach here, either no lock exists or it's valid
        return lockedSeats.containsKey(lockKey);
    }
    
    private void cleanupLockIfExpired(String lockKey) {
        SeatLock lock = lockedSeats.get(lockKey);
        if (lock != null && lock.isExpired()) {
            lockedSeats.remove(lockKey);
        }
    }
    
    private String generateLockKey(Screening screening, Seat seat) {
        return screening.getId() + "-" + seat.getSeatNumber();
    }
    
    private static class SeatLock {
        private final String userId;
        private final LocalDateTime expirationTime;
        
        public SeatLock(String userId, LocalDateTime expirationTime) {
            this.userId = userId;
            this.expirationTime = expirationTime;
        }
        
        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expirationTime);
        }
        
        public String getUserId() {
            return userId;
        }
    }
}