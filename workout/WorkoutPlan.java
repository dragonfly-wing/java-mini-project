package workout;

// Abstract base class for all workout types
public abstract class WorkoutPlan {
    protected String name;
    protected int duration; // minutes
    protected String intensity; // Low, Medium, High

    // Constructor
    public WorkoutPlan(String name, int duration, String intensity) {
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
    }

    // Abstract method to calculate calories burned
    public abstract double calculateCaloriesBurned();

    // Display workout info
    public void displayPlan() {
        System.out.println("Workout: " + name);
        System.out.println("Duration: " + duration + " mins");
        System.out.println("Intensity: " + intensity);
        System.out.println("Estimated Calories Burned: " + calculateCaloriesBurned());
        System.out.println("-------------------------");
    }

    // Getters
    public String getName() { return name; }
    public int getDuration() { return duration; }
    public String getIntensity() { return intensity; }
}

