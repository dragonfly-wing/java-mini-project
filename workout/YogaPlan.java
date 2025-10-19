package workout;

public class YogaPlan extends WorkoutPlan {
    private String type; // e.g., Hatha, Vinyasa

    public YogaPlan(String name, int duration, String intensity, String type) {
        super(name, duration, intensity);
        this.type = type;
    }

    @Override
    public double calculateCaloriesBurned() {
        double factor = switch (intensity.toLowerCase()) {
            case "low" -> 3.0;
            case "medium" -> 5.0;
            case "high" -> 7.0;
            default -> 4.0;
        };
        return duration * factor;
    }

    public String getType() { return type; }

    @Override
    public void displayPlan() {
        System.out.println("Yoga Workout: " + name + " (" + type + ")");
        System.out.println("Duration: " + duration + " mins");
        System.out.println("Intensity: " + intensity);
        System.out.println("Estimated Calories Burned: " + calculateCaloriesBurned());
        System.out.println("-------------------------");
    }
}

