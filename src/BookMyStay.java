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

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class BookingProcessor extends Thread {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                request = queue.poll();
            }

            boolean success = inventory.allocateRoom(request.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName()
                        + " → Booking Confirmed for "
                        + request.getGuestName()
                        + " (" + request.getRoomType() + ")");
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " → Booking Failed for "
                        + request.getGuestName()
                        + " (" + request.getRoomType() + ")");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room")); // may fail
        bookingQueue.add(new Reservation("David", "Double Room"));
        bookingQueue.add(new Reservation("Eve", "Double Room")); // may fail

        RoomInventory inventory = new RoomInventory();

        Thread t1 = new BookingProcessor(bookingQueue, inventory);
        Thread t2 = new BookingProcessor(bookingQueue, inventory);
        Thread t3 = new BookingProcessor(bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}