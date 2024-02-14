import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String CarId;
    private String Brand;
    private String Modal;
    private double carbasepriceparday;
    private boolean isAvalable;
    public Car(String carId,String brand,String modal,double carbasepriceparday) {
        this.CarId = carId;
        this.Brand = brand;
        this.Modal = modal;
        this.carbasepriceparday = carbasepriceparday;
        this.isAvalable = true;
    }
    public String getCarId(){

        return CarId;
    }
    public String getBrand(){

        return Brand;
    }
    public String getModal(){

        return Modal;
    }
    public double calculateprice(int rentalDays){

        return carbasepriceparday *  rentalDays;
    }
    public boolean isAvalable(){

        return isAvalable;
    }
    public void rent(){

        isAvalable=false;
    }
    public void returnCar(){

        isAvalable=true;
    }
}

class Customer {
    private String Customer_Id;
    private String name;
    private String Mo_number;
    private String IdProoph;

    public Customer(String customer_Id, String name, String mo_number, String idProoph) {
        this.Customer_Id = customer_Id;
        this.name = name;
        this.Mo_number = mo_number;
        this.IdProoph = idProoph;
    }

    public String getCustomer_Id() {

        return Customer_Id;
    }

    public String getName() {

        return name;
    }

    public String getMo_number() {

        return Mo_number;
    }

    public String getIdProoph() {

        return IdProoph;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;

    }

    public Car getCar() {

        return car;
    }

    public Customer getCustomer() {

        return customer;
    }

    public int getDays() {

        return days;
    }
}

class CarRentalSystem {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addcar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {

        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvalable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent this time..");
        }

    }

    public void returnCar(Car car) {

        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            car.returnCar();
            System.out.println("Car returned Successfully.");
        } else {
            System.out.println("Car was not rented..");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("=== Car Rental System ======");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit ");
            System.out.println("Enter your choice:");

            int choice = sc.nextInt();
            sc.nextInt();//Consume nextline
            if (choice == 1) {
                System.out.println("\n==Rent a car==\n");
                System.out.println("Enter your Name");
                String CustomerName = sc.nextLine();

                System.out.println("\n Available cars");
                for (Car car : cars) {
                    if (car.isAvalable()) {
                        System.out.println(car.getCarId() + " " + car.getBrand() + " " + car.getModal());

                    }
                }
                System.out.println("\nEnter a car ID you want to rent:");
                String carId = sc.nextLine();
                System.out.println("Enter the number of  days for rental: ");
                int retalDays = sc.nextInt();
                sc.nextLine();//consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), "Name" + (customers.size()),
                        "Mo_Numnber" + (customers.size()), "idProoph" + (customers.size()));
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvalable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculateprice(retalDays);
                    System.out.println("\n==Rental Information ==\n");
                    System.out.println("Customer ID:" + newCustomer.getCustomer_Id());
                    System.out.println("Customer Name:" + newCustomer.getName());
                    System.out.println("Customer Mo.Number:" + newCustomer.getMo_number());
                    System.out.println("Customer IDProoph:" + newCustomer.getIdProoph());
                    System.out.println("car:" + selectedCar.getBrand() + " " + selectedCar.getModal());
                    System.out.println("Rental Days" + retalDays);
                    System.out.println("Total Price: $%.2f%n" + totalPrice);
                    System.out.println("\nConfirm rental (Y/N):");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, retalDays);
                        System.out.println("\nCar rented successfully");

                    } else {
                        System.out.println("\nRental canceled");
                    }

                } else {
                    System.out.println("\nInvalid car selection or not available for rent");
                }

            } else if (choice == 2) {
                System.out.println("\n==Return a Car==\n");
                System.out.println("Enter the car ID you want to return.");
                String CarID = sc.nextLine();

                Car CarToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(CarID) && !car.isAvalable()) {
                        CarToReturn = car;
                        break;
                    }

                }
                if (CarToReturn != null) {
                    Customer customer = null;
                    for (Rental rental = null; ; ) {
                        if (rental.getCar() == CarToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(CarToReturn);
                        System.out.println("Car returned successfully by" + customer.getName());
                    } else {
                        System.out.println("car was not rented or rental information is missing");

                    }
                }
                }
            else if (choice == 3) {
                    break;
                } else {
                    System.out.println("invalid choice. Please enter a valid option.");

                }

            System.out.println("\nThanks you for using Car Rental System");
        }


    }
}

    public class Main {
        public static void main(String[] args) {
            CarRentalSystem rentalSystem = new CarRentalSystem();
            Car car1 = new Car("C001", "Toyota", "Camry", 150.0);
            Car car2 = new Car("C002", "Honda", " Accord", 200.0);
            Car car3 = new Car("C003", "Thar", "Mahendra", 250.0);
            Car car4 = new Car("C004", "Maruti", "Tata", 100.0);
            rentalSystem.addcar(car1);
            rentalSystem.addcar(car2);
            rentalSystem.addcar(car3);
            rentalSystem.addcar(car4);

            rentalSystem.menu();

        }
    }
