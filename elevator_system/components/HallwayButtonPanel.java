package elevator_system.components;

import elevator_system.observer.ElevatorObserver;

import java.util.ArrayList;
import java.util.List;

public class HallwayButtonPanel {
    private final int floor;
    private final List<ElevatorObserver> observers;

    public HallwayButtonPanel(int floor) {
        this.floor = floor;
        this.observers = new ArrayList<>();
    }

    public void pressButton(Direction direction) {
        notifyObservers(direction);
    }

    public void addObserver(ElevatorObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(Direction direction) {
        for (ElevatorObserver observer : observers) {
            observer.update(floor, direction);
        }
    }
}
