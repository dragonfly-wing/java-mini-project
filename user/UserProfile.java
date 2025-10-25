package user;
import java.util.Scanner;
public class UserProfile {
    private String name;
    private int age;
    private double weight;
    private String goal;

    // Default constructor → calls parameterized constructor
    public UserProfile() {
        this("Guest", 18, 60.0, "Stay Fit");
    }

    // Parameterized constructor
    public UserProfile(String name, int age, double weight, String goal) {
        if (age <= 0) throw new InvalidInputException("Age must be positive!");
        if (weight <= 0) throw new NegativeValueException("Weight must be positive!");

        this.name = name;
        this.age = age;
        this.weight = weight;
        this.goal = goal;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getWeight() { return weight; }
    public String getGoal() { return goal; }

    // Setters with validation
    public void setWeight(double weight) {
        if (weight <= 0) throw new NegativeValueException("Weight must be positive!");
        this.weight = weight;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    // Display user info
    public void displayProfile() {
        System.out.println("=== USER PROFILE ===");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Goal: " + goal);
        System.out.println("===================");
    }

    // Optional: create a profile interactively
    public static UserProfile createFromInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter weight (kg): ");
        double weight = sc.nextDouble();
        sc.nextLine(); // consume newline
        System.out.print("Enter fitness goal: ");
        String goal = sc.nextLine();
sc.close();

        return new UserProfile(name, age, weight, goal);     }
}

