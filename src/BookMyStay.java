import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n---- Current Inventory ----");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class BookingHistory {

    private Map<String, Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return confirmedBookings.get(id);
    }

    public void removeReservation(String id) {
        confirmedBookings.remove(id);
    }
}

class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        rollbackStack.push(reservationId);

        inventory.incrementRoom(r.getRoomType());

        history.removeReservation(reservationId);

        System.out.println("Cancellation Successful for " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("SINGLEROOM-1", "Single Room"));
        history.addReservation(new Reservation("DOUBLEROOM-2", "Double Room"));

        CancellationService service =
                new CancellationService(inventory, history);

        service.cancelBooking("SINGLEROOM-1");

        service.cancelBooking("SINGLEROOM-1");

        service.cancelBooking("DOUBLEROOM-2");

        service.showRollbackStack();

        inventory.displayInventory();
    }
}