package workout;

public class StrengthPlan extends WorkoutPlan {
    private int sets;
    private int reps;

    public StrengthPlan(String name, int duration, String intensity, int sets, int reps) {
        super(name, duration, intensity);
        this.sets = sets;
        this.reps = reps;
    }

    @Override
    public double calculateCaloriesBurned() {
        double factor = switch (intensity.toLowerCase()) {
            case "low" -> 4.0;
            case "medium" -> 6.0;
            case "high" -> 9.0;
            default -> 5.0;
        };
        return duration * factor + sets * reps * 0.5; // simple estimate
    }

    public int getSets() { return sets; }
    public int getReps() { return reps; }
}

