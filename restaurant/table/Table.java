package restaurant.table;

import restaurant.menu.MenuItem;
import restaurant.reservation.OrderItem;
import restaurant.reservation.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private final int tableId;
    private final int capacity;

    private final Map<LocalDateTime, Reservation> reservations = new HashMap<>();
    private final Map<MenuItem, List<OrderItem>> orderedItems = new HashMap<>();

    public Table(int tableId, int capacity) {
        this.tableId = tableId;
        this.capacity = capacity;
    }

    public BigDecimal calculateBillAmount() {
        return orderedItems.values().stream()
                .flatMap(List::stream)
                .map(OrderItem::getItem)
                .map(MenuItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addOrder(MenuItem item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            addOrder(item);
        }
    }

    public void addOrder(MenuItem item) {
        List<OrderItem> orderItems = orderedItems.get(item);
        if (orderItems == null) {
            orderItems = new ArrayList<>();
            orderedItems.put(item, orderItems);
            orderItems.add(new OrderItem(item));
        } else {
            orderItems.add(new OrderItem(item));
        }
    }

    public void removeOrder(MenuItem item) {
        List<OrderItem> orderItems = orderedItems.get(item);
        if (orderItems != null) {
            orderItems.remove(0);
            if (orderItems.isEmpty()) {
                orderedItems.remove(item);
            }
        }
    }

    public boolean isAvailableAt(LocalDateTime reservationTime) {
        return !reservations.containsKey(reservationTime);
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getTime(), reservation);
    }

    public void removeReservation(LocalDateTime reservationTime) {
        reservations.remove(reservationTime);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTableId() {
        return tableId;
    }

    public int getCurrentPartySize() {
        return reservations.values().stream()
                .mapToInt(Reservation::getPartySize)
                .sum();
    }

    public Map<MenuItem, List<OrderItem>> getOrderedItems() {
        return orderedItems;
    }
    
    @Override
    public String toString() {
        return "Table #" + getTableId() + " (Capacity: " + getCapacity() + ")";
    }
}
