package elevator_system.observer;

import elevator_system.ElevatorSystem;
import elevator_system.components.Direction;
import elevator_system.components.HallwayButtonPanel;
import elevator_system.dispatch.ElevatorDispatchController;

class ObserverPatternExample {
    private final ElevatorSystem elevatorSystem;

    public ObserverPatternExample(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    public static void main(String[] args) {
        HallwayButtonPanel hallwayButtonPanel = new HallwayButtonPanel(5);
        ElevatorDispatchController controller = new ElevatorDispatchController();

        hallwayButtonPanel.addObserver(controller);
        hallwayButtonPanel.pressButton(Direction.UP);
    }
}
