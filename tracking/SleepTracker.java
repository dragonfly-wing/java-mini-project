package tracking;

import java.util.ArrayList;
import java.util.List;

public class SleepTracker implements Trackable {
    private double goal;       // hours
    private double current;
    private List<Double> history;

    public SleepTracker(double goal) {
        if (goal <= 0) throw new RuntimeException("Sleep goal must be positive.");
        this.goal = goal;
        this.current = 0;
        this.history = new ArrayList<>();
    }

    @Override
    public void update(double value) {
        if (value < 0) throw new RuntimeException("Sleep hours cannot be negative.");
        current += value;
        history.add(current);
    }

    @Override
    public void displayProgress() {
        System.out.println("Sleep: " + current + "/" + goal + " hours (" + getCompletionPercentage() + "%)");
        System.out.println("History: " + history);
    }

    @Override
    public double getCompletionPercentage() {
        return (current / goal) * 100;
    }

    // --- new helper ---
    public double getLatest() {
        if (history.isEmpty()) return 0;
        return history.get(history.size() - 1);
    }
}

