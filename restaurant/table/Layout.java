package restaurant.table;

import java.time.LocalDateTime;
import java.util.*;

public class Layout {
    private final Map<Integer, Table> tablesById = new HashMap<>();

    private final SortedMap<Integer, Set<Table>> tablesByCapacity = new TreeMap<>();

    public Layout(List<Integer> tableCapacities) {
        for (int i = 0; i < tableCapacities.size(); i++) {
            int capacity = tableCapacities.get(i);
            Table table = new Table(i, capacity);
            tablesById.put(i, table);
            tablesByCapacity.computeIfAbsent(capacity, k -> new HashSet<>()).add(table);
        }
    }

    public Table findAvailableTable(int partySize, LocalDateTime reservationTime) {
        for (Set<Table> tables : tablesByCapacity.tailMap(partySize).values()) {
            for (Table table : tables) {
                if (table.isAvailableAt(reservationTime)) {
                    return table;
                }
            }
        }
        return null;
    }
}
