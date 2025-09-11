package elevator_system.dispatch;

import elevator_system.components.Direction;
import elevator_system.components.ElevatorCar;

import java.util.List;

public interface DispatchingStrategy {
    ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction);
}
