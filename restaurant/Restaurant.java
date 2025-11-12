package restaurant;

import restaurant.menu.Menu;
import restaurant.menu.MenuItem;
import restaurant.reservation.Reservation;
import restaurant.reservation.ReservationManager;
import restaurant.table.Layout;
import restaurant.table.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Restaurant {
    private final String name;
    private final Menu menu;
    private final Layout layout;
    private final ReservationManager reservationManager;

    public Restaurant(String name, Menu menu, Layout layout) {
        this.name = name;
        this.menu = menu;
        this.layout = layout;
        this.reservationManager = new ReservationManager(layout);
    }

    public LocalDateTime[] findAvailableTimeSlots(LocalDateTime rangeStart, LocalDateTime rangeEnd, int partySize) {
        return reservationManager.findAvailableTimeSlots(rangeStart, rangeEnd, partySize);
    }

    public Reservation createScheduledReservation(String partyName, int partySize, LocalDateTime time) {
        return reservationManager.createReservation(partyName, partySize, time);
    }

    public void removeReservation(String partyName, int partySize, LocalDateTime reservationTime) {
        reservationManager.removeReservation(partyName, partySize, reservationTime);
    }

    public Reservation createWalkInReservation(String partyName, int partySize) {
        return reservationManager.createReservation(partyName, partySize, LocalDateTime.now());
    }

    public void orderItem(Table table, MenuItem item) {
        table.addOrder(item);
    }

    public void cancelItem(Table table, MenuItem item) {
        table.removeOrder(item);
    }

    public BigDecimal calculateTableBill(Table table) {
        return table.calculateBillAmount();
    }

    public String getName() {
        return name;
    }

    public Menu getMenu() {
        return menu;
    }

    public Layout getLayout() {
        return layout;
    }

    public ReservationManager getReservationManager() {
        return reservationManager;
    }
}

