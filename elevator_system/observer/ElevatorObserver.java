package elevator_system.observer;

import elevator_system.components.Direction;

public interface ElevatorObserver {
    void update(int floor, Direction direction);
}
