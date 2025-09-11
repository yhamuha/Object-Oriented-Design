package elevator_system;

import elevator_system.components.Direction;
import elevator_system.components.ElevatorCar;
import elevator_system.components.ElevatorStatus;
import elevator_system.dispatch.DispatchingStrategy;
import elevator_system.dispatch.ElevatorDispatch;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {

    private final List<ElevatorCar> elevators;
    private final ElevatorDispatch dispatchController;

    public ElevatorSystem(List<ElevatorCar> elevators, DispatchingStrategy strategy) {
        this.elevators = elevators;
        this.dispatchController = new ElevatorDispatch(strategy);
    }

    public List<ElevatorStatus> getAllElevatorStatuses() {
        List<ElevatorStatus> statuses = new ArrayList<>();
        for (ElevatorCar elevator : elevators) {
            statuses.add(elevator.getStatus());
        }
        return statuses;
    }

    public void requestElevator(int currentFloor, Direction direction) {
        dispatchController.dispatchElevatorCar(currentFloor, direction, elevators);
    }

    public void selectFloor(ElevatorCar car, int destinationFloor) {
        car.addFloorRequest(destinationFloor);
    }

}
