package tracking;

import java.util.ArrayList;
import java.util.List;

// Tracks calories burned
public class CaloriesTracker implements Trackable {
    private double goal;
    private double current;
    private List<Double> history;

    public CaloriesTracker(double goal) {
        if (goal <= 0) throw new RuntimeException("Calories goal must be positive.");
        this.goal = goal;
        this.current = 0;
        this.history = new ArrayList<>();
    }

    @Override
    public void update(double value) {
        if (value < 0) throw new RuntimeException("Calories cannot be negative.");
        current += value;
        history.add(current);
    }

    @Override
    public void displayProgress() {
        System.out.println("Calories: " + current + "/" + goal + " (" + getCompletionPercentage() + "%)");
        System.out.println("History: " + history);
    }

    @Override
    public double getCompletionPercentage() {
        return (current / goal) * 100;
    }

    // --- helper ---
    public double getLatest() {
        if (history.isEmpty()) return 0;
        return history.get(history.size() - 1);
    }
}

