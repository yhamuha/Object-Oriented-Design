package restaurant.reservation;

import restaurant.menu.MenuItem;

public class OrderItem {
    private final MenuItem item;
    private Status status = Status.PENDING;

    public OrderItem(MenuItem item) {
        this.item = item;
    }

    public MenuItem getItem() {
        return item;
    }

    public void sendToKitchen() {
        if (status == Status.PENDING) status = Status.SENT_TO_KITCHEN;
    }

    public void deliverToCustomer() {
        if (status == Status.SENT_TO_KITCHEN) status = Status.DELIVERED;
    }

    public void cancel() {
        if (status == Status.PENDING || status == Status.SENT_TO_KITCHEN) {
            status = Status.CANCELED;

        }
    }

    public Status getStatus() {
        return status;
    }
}
