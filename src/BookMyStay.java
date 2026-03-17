import java.util.*;

class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Added Service: " + service.getServiceName()
                + " to Reservation: " + reservationId);
    }

    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        double totalCost = 0;

        for (Service s : services) {
            System.out.println("- " + s.getServiceName() + " : ₹" + s.getCost());
            totalCost += s.getCost();
        }

        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "SINGLEROOM-1";

        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1200));
        manager.addService(reservationId, new Service("Extra Bed", 800));

        manager.displayServices(reservationId);
    }
}