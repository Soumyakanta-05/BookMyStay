import java.util.*;

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void showAllBookings() {

        System.out.println("---- Booking History ----");

        List<Reservation> reservations = history.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }
    }

    public void showSummary() {

        System.out.println("\n---- Booking Summary ----");

        List<Reservation> reservations = history.getAllReservations();

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : reservations) {
            String roomType = r.getRoomType();
            countMap.put(roomType, countMap.getOrDefault(roomType, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " Bookings: " + countMap.get(type));
        }
    }
}

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("SINGLEROOM-1", "Alice", "Single Room"));
        history.addReservation(new Reservation("DOUBLEROOM-2", "Bob", "Double Room"));
        history.addReservation(new Reservation("SINGLEROOM-3", "Charlie", "Single Room"));

        BookingReportService reportService =
                new BookingReportService(history);

        reportService.showAllBookings();
        reportService.showSummary();
    }
}