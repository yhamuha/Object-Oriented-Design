package elevator_system.dispatch;

import elevator_system.components.Direction;
import elevator_system.components.ElevatorCar;

import java.util.List;

public class FirstComeFirstServeStrategy implements DispatchingStrategy {
    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction) {
        for (ElevatorCar elevator : elevators) {
            if (elevator.isIdle() || elevator.getCurrentDirection() == direction) {
                return elevator;
            }
        }
        return elevators.get((int) (Math.random() * elevators.size()));
    }
}
