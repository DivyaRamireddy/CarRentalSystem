package ProjectWork;
import java.util.*;
class Car {
    private String id;
    private String model;
    private double pricePerDay;
    private boolean isRented;
    public Car(String id, String model, double pricePerDay) {
        this.id = id;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.isRented = false;
    }
    public String getId() {
        return id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public double getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public boolean isRented() {
        return isRented;
    }
    public void setRented(boolean rented) {
        isRented = rented;
    }
    @Override
    public String toString() {
        return "ID: " + id + ", Model: " + model + ", Price per day: Rs" + pricePerDay + ", Rented: " + (isRented ? "Yes" : "No");
    }
}
public class CarRentalSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Car> cars = new ArrayList<>();
    private static final List<Car> rentedCars = new ArrayList<>();
    
    // To store customer credentials: username -> password
    private static final Map<String, String> customerCredentials = new HashMap<>();
    
    private static final String adminPassword = "admin123"; // Fixed password for Admin
    public static void main(String[] args) {
        System.out.println("Welcome to the Car Rental System!");
        
        // Main loop for role selection
        while (true) {
            System.out.println("\nAre you an Admin or Customer?");
            System.out.print("Enter 'Admin' or 'Customer' (or type 'exit' to quit): ");
            String role = scanner.nextLine().trim().toLowerCase();
            if (role.equals("admin")) {
                adminLogin();
            } else if (role.equals("customer")) {
                customerLogin();
            } else if (role.equals("exit")) {
                System.out.println("Exiting the program...");
                break; // Exit the loop and end the program
            } else {
                System.out.println("Invalid role. Please enter 'Admin' or 'Customer'.");
            }
        }
    }
    // Admin Login
    private static void adminLogin() {
        System.out.print("Enter Admin password: ");
        String password = scanner.nextLine().trim();
        if (password.equals(adminPassword)) {
            adminMenu();
        } else {
            System.out.println("Incorrect password. Access denied.");
        }
    }
    // Customer Login
    private static void customerLogin() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1: 
                registerCustomer();
                break;
            case 2:
                loginCustomer();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    // Register a new customer
    private static void registerCustomer() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        // Check if username already exists
        if (customerCredentials.containsKey(username)) {
            System.out.println("Username already exists. Please try a different one.");
            return;
        }
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        customerCredentials.put(username, password);
        System.out.println("Registration successful. You can now log in.");
    }
    // Login existing customer
    private static void loginCustomer() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        // Check if credentials are correct
        if (customerCredentials.containsKey(username) && customerCredentials.get(username).equals(password)) {
            System.out.println("Login successful!");
            customerMenu();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    // Admin Menu
    private static void adminMenu() {
        int choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Create a New Car");
            System.out.println("2. Update Car Details");
            System.out.println("3. Delete a Car");
            System.out.println("4. View All Cars");
            System.out.println("5. Exit Admin Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    createCar();
                    break;
                case 2:
                    updateCar();
                    break;
                case 3:
                    deleteCar();
                    break;
                case 4:
                    viewAllCars();
                    break;
                case 5:
                    System.out.println("Exiting Admin Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5); // Loop until admin chooses to exit
    }
    // Customer Menu
    private static void customerMenu() {
        int choice;
        do {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. View Booked Cars");
            System.out.println("4. Exit Customer Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    rentCar();
                    break;
                case 2:
                    returnCar();tal
                    break;
                case 3:
                    viewBookedCars();
                    break;
                case 4:
                    System.out.println("Exiting Customer Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4); // Loop until customer chooses to exit
    }
    // Admin Features
    private static void createCar() {
        System.out.print("Enter Car ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Car Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter Price per Day: ");
        double pricePerDay = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        cars.add(new Car(id, model, pricePerDay));
        System.out.println("Car added successfully.");
    }
    private static void updateCar() {
        System.out.print("Enter Car ID to update: ");
        String id = scanner.nextLine();
        Car car = findCarById(id);
        if (car != null) {
            System.out.print("Enter new model (leave blank to keep unchanged): ");
            String model = scanner.nextLine();
            if (!model.isBlank()) {
                car.setModel(model);
            }
            System.out.print("Enter new price per day (-1 to keep unchanged): ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            if (price != -1) {
                car.setPricePerDay(price);
            }
            System.out.println("Car updated successfully.");
        } else {
            System.out.println("Car not found.");
        }
    }
    private static void deleteCar() {
        System.out.print("Enter Car ID to delete: ");
        String id = scanner.nextLine();
        Car car = findCarById(id);
        if (car != null) {
            cars.remove(car);
            System.out.println("Car deleted successfully.");
        } else {
            System.out.println("Car not found.");
        }
    }
    private static void viewAllCars() {
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            System.out.println("\nAll Cars:");
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }
    // Customer Features
    private static void rentCar() {
        System.out.println("Available Cars:");
        for (Car car : cars) {
            if (!car.isRented()) {
                System.out.println(car);
            }
        }
        System.out.print("Enter Car ID to rent: ");
        String id = scanner.nextLine();
        Car car = findCarById(id);
        if (car != null && !car.isRented()) {
            car.setRented(true);
            rentedCars.add(car);
            System.out.println("Car rented successfully.");
        } else if (car != null && car.isRented()) {
            System.out.println("Car is already rented.");
        } else {
            System.out.println("Car not found.");
        }
    }
    private static void returnCar() {
        System.out.print("Enter Car ID to return: ");
        String id = scanner.nextLine();
        Car car = findCarById(id);
        if (car != null && car.isRented()) {
            car.setRented(false);
            rentedCars.remove(car);
            System.out.println("Car returned successfully.");
        } else if (car != null && !car.isRented()) {
            System.out.println("Car is not currently rented.");
        } else {
            System.out.println("Car not found.");
        }
    }
    private static void viewBookedCars() {
        if (rentedCars.isEmpty()) {
            System.out.println("No cars are currently rented.");
        } else {
            System.out.println("\nBooked Cars:");
            for (Car car : rentedCars) {
                System.out.println(car);
            }
        }
    }
    // Utility Method
    private static Car findCarById(String id) {
        for (Car car : cars) {
            if (car.getId().equalsIgnoreCase(id)) {
                return car;
            }
        }
        return null;
    }
}
