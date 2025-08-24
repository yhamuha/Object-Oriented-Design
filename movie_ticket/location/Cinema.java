package movie_ticket.location;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private final String name;
    private final String location;
    private final List<Room> rooms;

    public Cinema(String name, String location) {
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
