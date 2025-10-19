package workout;

public class CardioPlan extends WorkoutPlan {
    private double distance; // in kilometers

    public CardioPlan(String name, int duration, String intensity, double distance) {
        super(name, duration, intensity);
        this.distance = distance;
    }

    @Override
    public double calculateCaloriesBurned() {
        // Simplified formula: calories = duration (min) * intensity factor
        double factor = switch (intensity.toLowerCase()) {
            case "low" -> 5.0;
            case "medium" -> 8.0;
            case "high" -> 12.0;
            default -> 6.0;
        };
        return duration * factor + distance * 50; // distance adds extra burn
    }

    public double getDistance() { return distance; }
}

