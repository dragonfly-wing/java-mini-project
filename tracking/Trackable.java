package tracking;

// Interface for anything that can be tracked
public interface Trackable {
    void update(double value);            // add a new entry
    void displayProgress();               // show progress
    double getCompletionPercentage();     // optional: % of goal achieved
}

