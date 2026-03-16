import java.util.*;

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int current = inventory.get(roomType);
        inventory.put(roomType, current - 1);
    }
}

class BookingService {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> roomAllocations;

    private int roomCounter = 1;

    public BookingService(Queue<Reservation> bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;

        allocatedRoomIds = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    public void processBookings() {

        System.out.println("---- Processing Booking Requests ----");

        while (!bookingQueue.isEmpty()) {

            Reservation request = bookingQueue.poll();
            String roomType = request.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = roomType.replace(" ", "").toUpperCase() + "-" + roomCounter++;

                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(roomType, new HashSet<>());
                roomAllocations.get(roomType).add(roomId);

                inventory.decrementRoom(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Allocated Room ID: " + roomId);
                System.out.println();

            } else {

                System.out.println("Reservation Failed for " + request.getGuestName()
                        + " (No " + roomType + " available)");
                System.out.println();
            }
        }
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Suite Room"));
        bookingQueue.add(new Reservation("Eve", "Suite Room"));

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService =
                new BookingService(bookingQueue, inventory);

        bookingService.processBookings();
    }
}