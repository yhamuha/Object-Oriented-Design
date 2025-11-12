package restaurant.reservation;

import restaurant.table.Layout;
import restaurant.table.Table;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservationManager {
    private final Layout layout;
    private final Set<Reservation> reservations = new HashSet<>();

    public ReservationManager(Layout layout) {
        this.layout = layout;
    }

    public LocalDateTime[] findAvailableTimeSlots(LocalDateTime rangeStart, LocalDateTime rangeEnd, int partySize) {

        LocalDateTime current = rangeStart;
        List<LocalDateTime> possibleReservations = new ArrayList<>();
        while (!current.isAfter(rangeEnd)) {
            Table availableTable = layout.findAvailableTable(partySize, current);
            if (availableTable != null) {
                possibleReservations.add(current);
            }
            current = current.plusHours(1);
        }
        return possibleReservations.toArray(new LocalDateTime[0]);
    }

    public Reservation createReservation(String partyName, int partySize, LocalDateTime desiredTime) {
        desiredTime = desiredTime.truncatedTo(ChronoUnit.HOURS);
        Table table = layout.findAvailableTable(partySize, desiredTime);
        Reservation reservation = new Reservation(partyName, partySize, desiredTime, table);
        table.addReservation(reservation);
        reservations.add(reservation);
        return reservation;
    }

    public void removeReservation(String partyName, int partySize, LocalDateTime reservationTime) {

        for (Reservation reservation : new HashSet<>(reservations)) {
            if (reservation.getTime().equals(reservationTime) &&
                    reservation.getPartySize() == partySize &&
                    reservation.getPartyName().equals(partyName)) {

                Table table = reservation.getAssignedTable();
                table.removeReservation(reservationTime);

                reservations.remove(reservation);
                return;
            }
        }
    }

    public Set<Reservation> getReservations() {
        return new HashSet<>(reservations);
    }
}
