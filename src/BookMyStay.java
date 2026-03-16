abstract class Room {
    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails(String roomType) {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 350, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 500, 6000);
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("---- Room Details ----");

        single.displayRoomDetails("Single Room");
        System.out.println("Available: " + singleRoomAvailable);
        System.out.println();

        doubleRoom.displayRoomDetails("Double Room");
        System.out.println("Available: " + doubleRoomAvailable);
        System.out.println();

        suite.displayRoomDetails("Suite Room");
        System.out.println("Available: " + suiteRoomAvailable);
    }
}