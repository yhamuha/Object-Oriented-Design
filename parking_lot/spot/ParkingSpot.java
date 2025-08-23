package parking_lot.spot;

import parking_lot.vehicle.Vehicle;
import parking_lot.vehicle.VehicleSize;

public interface ParkingSpot {
    boolean isAvailable();
    void occupy(Vehicle vehicle);
    void vacate();
    int getSpotNumber();
    VehicleSize getSize();
}
