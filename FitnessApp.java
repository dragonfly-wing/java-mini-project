import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import user.*;
import workout.*;
import tracking.*;
import utils.*;
import java.util.*;

public class FitnessApp extends Application {

    private UserProfile user;
    private StepsTracker stepsTracker;
    private CaloriesTracker caloriesTracker;
    private SleepTracker sleepTracker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fitness Tracker");

        // Initialize user and trackers
        initializeUser();
        initializeTrackers();

        // TabPane for navigation
        TabPane tabs = new TabPane();
        tabs.getTabs().addAll(
                profileTab(),
                workoutTab(),
                trackingTab(),
                utilitiesTab()
        );

        Scene scene = new Scene(tabs, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --------------------------
    // Profile Tab
    // --------------------------
    private Tab profileTab() {
        Tab tab = new Tab("Profile");
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label nameLabel = new Label("Name: " + user.getName());
        Label ageLabel = new Label("Age: " + user.getAge());
        Label weightLabel = new Label("Weight: " + user.getWeight() + " kg");
        Label goalLabel = new Label("Fitness Goal: " + user.getGoal());

        Button updateWeightBtn = new Button("Update Weight");
        updateWeightBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Update Weight");
            dialog.setHeaderText("Enter new weight (kg):");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(wStr -> {
                try {
                    double w = Double.parseDouble(wStr);
                    user.setWeight(w);
                    weightLabel.setText("Weight: " + user.getWeight() + " kg");
                } catch (Exception ex) {
                    showError("Invalid input!");
                }
            });
        });

        Button updateGoalBtn = new Button("Update Goal");
        updateGoalBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog(user.getGoal());
            dialog.setTitle("Update Goal");
            dialog.setHeaderText("Enter new goal:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(g -> {
                user.setGoal(g);
                goalLabel.setText("Fitness Goal: " + user.getGoal());
            });
        });

        root.getChildren().addAll(nameLabel, ageLabel, weightLabel, goalLabel, updateWeightBtn, updateGoalBtn);
        tab.setContent(root);
        tab.setClosable(false);
        return tab;
    }

    // --------------------------
    // Workout Tab
    // --------------------------
    private Tab workoutTab() {
        Tab tab = new Tab("Workout");
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        ListView<String> workoutList = new ListView<>();
        Map<String, WorkoutPlan> map = new HashMap<>();

        // Suggested workouts
        CardioPlan run = new CardioPlan("Running", 30, "Medium", 5);
        CardioPlan cycle = new CardioPlan("Cycling", 45, "High", 15);
        StrengthPlan pushups = new StrengthPlan("Pushups", 20, "Medium", 3, 15);
        StrengthPlan squats = new StrengthPlan("Squats", 25, "Medium", 3, 20);
        YogaPlan hatha = new YogaPlan("Hatha Yoga", 40, "Low", "Hatha");
        YogaPlan vinyasa = new YogaPlan("Vinyasa Yoga", 30, "Medium", "Vinyasa");

        List<WorkoutPlan> workouts = List.of(run, cycle, pushups, squats, hatha, vinyasa);
        for (WorkoutPlan w : workouts) {
            workoutList.getItems().add(w.getName());
            map.put(w.getName(), w);
        }

        Button completeBtn = new Button("Complete Workout");
        completeBtn.setOnAction(e -> {
            String selected = workoutList.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a workout first!");
                return;
            }
            WorkoutPlan w = map.get(selected);
            caloriesTracker.update(w.calculateCaloriesBurned());
            showInfo("Workout completed! Calories updated: " + w.calculateCaloriesBurned());
        });

        root.getChildren().addAll(new Label("Suggested Workouts:"), workoutList, completeBtn);
        tab.setContent(root);
        tab.setClosable(false);
        return tab;
    }

    // --------------------------
    // Tracking Tab
    // --------------------------
    private Tab trackingTab() {
        Tab tab = new Tab("Tracking");
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label stepsLabel = new Label();
        Label caloriesLabel = new Label();
        Label sleepLabel = new Label();

        Button logStepsBtn = new Button("Log Steps");
        logStepsBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Log Steps");
            dialog.setHeaderText("Enter steps walked:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(sStr -> {
                try {
                    int s = Integer.parseInt(sStr);
                    stepsTracker.update(s);
                    updateTrackingLabels(stepsLabel, caloriesLabel, sleepLabel);
                } catch (Exception ex) { showError("Invalid input!"); }
            });
        });

        Button logSleepBtn = new Button("Log Sleep (hours)");
        logSleepBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Log Sleep");
            dialog.setHeaderText("Enter sleep hours:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(hStr -> {
                try {
                    double h = Double.parseDouble(hStr);
                    sleepTracker.update(h);
                    updateTrackingLabels(stepsLabel, caloriesLabel, sleepLabel);
                } catch (Exception ex) { showError("Invalid input!"); }
            });
        });

        updateTrackingLabels(stepsLabel, caloriesLabel, sleepLabel);
        root.getChildren().addAll(stepsLabel, caloriesLabel, sleepLabel, logStepsBtn, logSleepBtn);
        tab.setContent(root);
        tab.setClosable(false);
        return tab;
    }

    private void updateTrackingLabels(Label steps, Label calories, Label sleep) {
        steps.setText("Steps: " + stepsTracker.getLatest() + " (" + String.format("%.1f", stepsTracker.getCompletionPercentage()) + "%)");
        calories.setText("Calories burned: " + String.format("%.1f", caloriesTracker.getLatest()) + " (" + String.format("%.1f", caloriesTracker.getCompletionPercentage()) + "%)");
        sleep.setText("Sleep: " + String.format("%.1f", sleepTracker.getLatest()) + "h (" + String.format("%.1f", sleepTracker.getCompletionPercentage()) + "%)");
    }

    // --------------------------
    // Utilities Tab
    // --------------------------
    private Tab utilitiesTab() {
        Tab tab = new Tab("Utilities");
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label info = new Label("Unit Conversion:");

        Button kgToLbs = new Button("kg -> lbs");
        kgToLbs.setOnAction(e -> convertUnit("kgToLbs"));

        Button lbsToKg = new Button("lbs -> kg");
        lbsToKg.setOnAction(e -> convertUnit("lbsToKg"));

        Button kmToMiles = new Button("km -> miles");
        kmToMiles.setOnAction(e -> convertUnit("kmToMiles"));

        Button milesToKm = new Button("miles -> km");
        milesToKm.setOnAction(e -> convertUnit("milesToKm"));

        root.getChildren().addAll(info, kgToLbs, lbsToKg, kmToMiles, milesToKm);
        tab.setContent(root);
        tab.setClosable(false);
        return tab;
    }

    private void convertUnit(String type) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Unit Conversion");
        dialog.setHeaderText("Enter value:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(vStr -> {
            try {
                double v = Double.parseDouble(vStr);
                UnitConverter<Double> converter = new UnitConverter<>();
                double res = switch (type) {
                    case "kgToLbs" -> converter.kgToLbs(v);
                    case "lbsToKg" -> converter.lbsToKg(v);
                    case "kmToMiles" -> converter.kmToMiles(v);
                    case "milesToKm" -> converter.milesToKm(v);
                    default -> 0;
                };
                showInfo("Converted value: " + res);
            } catch (Exception ex) { showError("Invalid input!"); }
        });
    }

    // --------------------------
    // Initialization helpers
    // --------------------------
    private void initializeUser() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Create Profile");
        nameDialog.setHeaderText("Enter your name:");
        String name = nameDialog.showAndWait().orElse("User");

        TextInputDialog ageDialog = new TextInputDialog();
        ageDialog.setTitle("Create Profile");
        ageDialog.setHeaderText("Enter your age:");
        int age = Integer.parseInt(ageDialog.showAndWait().orElse("20"));

        TextInputDialog weightDialog = new TextInputDialog();
        weightDialog.setTitle("Create Profile");
        weightDialog.setHeaderText("Enter your weight (kg):");
        double weight = Double.parseDouble(weightDialog.showAndWait().orElse("60"));

        TextInputDialog goalDialog = new TextInputDialog();
        goalDialog.setTitle("Create Profile");
        goalDialog.setHeaderText("Enter your fitness goal:");
        String goal = goalDialog.showAndWait().orElse("Stay Fit");

        user = new UserProfile(name, age, weight, goal);
    }

    private void initializeTrackers() {
        TextInputDialog stepsDialog = new TextInputDialog();
        stepsDialog.setTitle("Set Daily Goal");
        stepsDialog.setHeaderText("Set daily step goal:");
        int stepGoal = Integer.parseInt(stepsDialog.showAndWait().orElse("10000"));

        TextInputDialog calorieDialog = new TextInputDialog();
        calorieDialog.setTitle("Set Daily Goal");
        calorieDialog.setHeaderText("Set daily calorie goal:");
        double calorieGoal = Double.parseDouble(calorieDialog.showAndWait().orElse("500"));

        TextInputDialog sleepDialog = new TextInputDialog();
        sleepDialog.setTitle("Set Daily Goal");
        sleepDialog.setHeaderText("Set daily sleep goal (hours):");
        double sleepGoal = Double.parseDouble(sleepDialog.showAndWait().orElse("8"));

        stepsTracker = new StepsTracker(stepGoal);
        caloriesTracker = new CaloriesTracker(calorieGoal);
        sleepTracker = new SleepTracker(sleepGoal);
    }

    // --------------------------
    // Utility methods
    // --------------------------
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

