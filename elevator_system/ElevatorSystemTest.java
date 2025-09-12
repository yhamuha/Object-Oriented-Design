package elevator_system;

import elevator_system.components.Direction;
import elevator_system.components.ElevatorCar;
import elevator_system.components.ElevatorStatus;
import elevator_system.dispatch.FirstComeFirstServeStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ElevatorSystemTest {

    @Test
    public void testElevatorFeature() {
        System.out.println("\n=== Testing Elevator System Features ===");
        
        ElevatorCar car = new ElevatorCar(1);
        ElevatorSystem elevatorSystem = new ElevatorSystem(List.of(car), new FirstComeFirstServeStrategy());
        System.out.println("✓ Elevator system initialized with 1 elevator car starting at floor 1");
        System.out.println("✓ Using First-Come-First-Serve dispatching strategy");

        System.out.println("\n--- Initial State Check ---");
        List<ElevatorStatus> elevators = elevatorSystem.getAllElevatorStatuses();
        assertEquals(1, elevators.get(0).getCurrentFloor());
        assertEquals(Direction.IDLE, elevators.get(0).getCurrentDirection());
        System.out.println("✓ Elevator is at floor 1 and in IDLE state (ready for requests)");

        System.out.println("\n--- Requesting Elevator from Floor 3 (Going UP) ---");
        elevatorSystem.requestElevator(3, Direction.UP);
        System.out.println("✓ Elevator requested from floor 3 going UP");

        elevators = elevatorSystem.getAllElevatorStatuses();
        assertEquals(1, elevators.get(0).getCurrentFloor());
        assertEquals(Direction.UP, elevators.get(0).getCurrentDirection());
        assertFalse(car.isAtDestination());
        System.out.println("✓ Elevator direction changed to UP (preparing to move)");
        
        System.out.println("\n--- Elevator Movement: Step 1 ---");
        car.moveOneStep();
        elevators = elevatorSystem.getAllElevatorStatuses();
        assertEquals(2, elevators.get(0).getCurrentFloor());
        assertEquals(Direction.UP, elevators.get(0).getCurrentDirection());
        assertFalse(car.isAtDestination());
        System.out.println("✓ Elevator moved from floor 1 → floor 2");
        System.out.println("✓ Still moving UP toward destination (floor 3)");

        System.out.println("\n--- Elevator Movement: Step 2 ---");
        car.moveOneStep();
        elevators = elevatorSystem.getAllElevatorStatuses();
        assertEquals(3, elevators.get(0).getCurrentFloor());
        assertEquals(Direction.UP, elevators.get(0).getCurrentDirection());
        assertTrue(car.isAtDestination());
        System.out.println("✓ Elevator moved from floor 2 → floor 3");
        System.out.println("✓ Reached destination floor 3");

        System.out.println("\n--- Arriving at Destination ---");
        car.moveOneStep();
        elevators = elevatorSystem.getAllElevatorStatuses();
        assertEquals(3, elevators.get(0).getCurrentFloor());
        assertEquals(Direction.IDLE, elevators.get(0).getCurrentDirection());
        assertFalse(car.isAtDestination());
        System.out.println("✓ Elevator stopped at floor 3");
        System.out.println("✓ Direction changed back to IDLE (ready for next request)");

        System.out.println("\n--- Multiple Floor Requests ---");
        elevatorSystem.requestElevator(8, Direction.DOWN);
        elevatorSystem.requestElevator(6, Direction.DOWN);
        System.out.println("✓ New requests: Floor 8 (DOWN) and Floor 6 (DOWN)");
        System.out.println("✓ Elevator will handle requests in order (First-Come-First-Serve)");

        System.out.println("\n--- Moving to Floor 8 (First Request) ---");
        car.moveUntilNextFloor();
        elevators = elevatorSystem.getAllElevatorStatuses();
        assertEquals(8, elevators.get(0).getCurrentFloor());
        assertEquals(Direction.UP, elevators.get(0).getCurrentDirection());
        assertTrue(car.isAtDestination());
        System.out.println("✓ Elevator moved directly to floor 8 (first request)");
        System.out.println("✓ Direction is UP (moving upward to reach floor 8)");

        System.out.println("\n--- Moving to Floor 6 (Second Request) ---");
        car.nextDestination();
        car.moveUntilNextFloor();
        elevators = elevatorSystem.getAllElevatorStatuses();
        assertEquals(6, elevators.get(0).getCurrentFloor());
        assertEquals(Direction.DOWN, elevators.get(0).getCurrentDirection());
        assertTrue(car.isAtDestination());
        System.out.println("✓ Elevator moved from floor 8 → floor 6 (second request)");
        System.out.println("✓ Direction is DOWN (moving downward from floor 8 to floor 6)");
        System.out.println("✓ All requests completed successfully");
        System.out.println("=== Elevator System Test Completed Successfully ===\n");
    }
}
