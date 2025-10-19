package tracking;

import java.util.ArrayList;
import java.util.List;

// Tracks steps
public class StepsTracker implements Trackable {
    private int goal;
    private int current;
    private List<Integer> history;

    public StepsTracker(int goal) {
        if (goal <= 0) throw new RuntimeException("Step goal must be positive.");
        this.goal = goal;
        this.current = 0;
        this.history = new ArrayList<>();
    }

    @Override
    public void update(double value) {
        if (value < 0) throw new RuntimeException("Steps cannot be negative.");
        current += (int)value;
        history.add(current);
    }

    @Override
    public void displayProgress() {
        System.out.println("Steps: " + current + "/" + goal + " (" + getCompletionPercentage() + "%)");
        System.out.println("History: " + history);
    }

    @Override
    public double getCompletionPercentage() {
        return ((double)current / goal) * 100;
    }

    // --- helper ---
    public int getLatest() {
        if (history.isEmpty()) return 0;
        return history.get(history.size() - 1);
    }
}

