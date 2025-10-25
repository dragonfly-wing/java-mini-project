import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import user.UserProfile;
import tracking.*;
import workout.*;

import java.util.ArrayList;
import java.util.List;

public class Test extends Application {

    private UserProfile user;
    private StepsTracker stepsTracker;
    private CaloriesTracker caloriesTracker;
    private SleepTracker sleepTracker;

    private TextField nameField, ageField, weightField;
    private ComboBox<String> goalDropdown;
    private TextField calorieGoalField, sleepGoalField;

    private VBox workoutInputsBox;
    private ComboBox<String> workoutGoalDropdown, workoutChoiceDropdown;
    private Label caloriesBurnedLabel;

    private Label stepsLabel, caloriesLabel, sleepLabel;

    private double totalCalories = 0;

    @Override
    public void start(Stage stage) {

        // ---------------- WELCOME SCENE ----------------
        VBox welcomeLayout = new VBox(25);
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #beff74ff, #8b83f7ff);");
        welcomeLayout.setPadding(new Insets(40));

        Label welcomeLabel = new Label("Welcome to FitnessApp");
        welcomeLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 34));
        welcomeLabel.setTextFill(Color.web("#79006dff"));

        Label descLabel = new Label("Your personal fitness companion to track, train, and transform!");
        descLabel.setFont(Font.font("Poppins", 16));
        descLabel.setTextFill(Color.web("#060404ff"));

        Button startBtn = new Button("Create Your Profile");
        startBtn.setStyle("-fx-background-color: #085151ff ; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 25;");

        welcomeLayout.getChildren().addAll(welcomeLabel, descLabel, startBtn);
        Scene welcomeScene = new Scene(welcomeLayout, 900, 650);

        // ---------------- PROFILE TAB ----------------
        GridPane profileForm = new GridPane();
        profileForm.setVgap(10);
        profileForm.setHgap(10);
        profileForm.setAlignment(Pos.CENTER);

        nameField = new TextField();
        ageField = new TextField();
        weightField = new TextField();
        goalDropdown = new ComboBox<>(FXCollections.observableArrayList(
                "Weight Loss", "Muscle Gain", "Flexibility", "Endurance", "General Fitness"
        ));
        goalDropdown.setPromptText("Select your goal");

        calorieGoalField = new TextField();
        sleepGoalField = new TextField();

        profileForm.addRow(0, new Label("Name:"), nameField);
        profileForm.addRow(1, new Label("Age:"), ageField);
        profileForm.addRow(2, new Label("Weight (kg):"), weightField);
        profileForm.addRow(3, new Label("Fitness Goal:"), goalDropdown);
        profileForm.addRow(4, new Label("Daily Calorie Goal:"), calorieGoalField);
        profileForm.addRow(5, new Label("Daily Sleep Goal (h):"), sleepGoalField);

        VBox profileLayout = new VBox(20);
        profileLayout.setAlignment(Pos.CENTER);
        profileLayout.setPadding(new Insets(30));
        profileLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffeaa7, #67f3e3ff);");

        Label profileTitle = new Label("Create Your Profile");
        profileTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        profileTitle.setTextFill(Color.web("#79006dff"));

        Button saveProfileBtn = new Button("Save Profile");
        saveProfileBtn.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");

        profileLayout.getChildren().addAll(profileTitle, profileForm, saveProfileBtn);

        // ---------------- WORKOUT TAB ----------------
        VBox workoutLayout = new VBox(20);
        workoutLayout.setPadding(new Insets(30));
        workoutLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffeaa7, #67f3e3ff);");

        Label wLabel = new Label("Workout Planner & Calories Log");
        wLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        wLabel.setTextFill(Color.web("#79006dff"));

        HBox workoutGoalRow = new HBox(10);
        workoutGoalRow.setAlignment(Pos.CENTER_LEFT);
        Label goalLabel = new Label("Fitness Goal:");
        goalLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 18));
        workoutGoalDropdown = new ComboBox<>();
        workoutGoalDropdown.setPromptText("Select goal");
        workoutGoalRow.getChildren().addAll(goalLabel, workoutGoalDropdown);

        HBox workoutChoiceRow = new HBox(10);
        workoutChoiceRow.setAlignment(Pos.CENTER_LEFT);
        Label chooseWorkoutLabel = new Label("Choose Workout:");
        chooseWorkoutLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 18));
        workoutChoiceDropdown = new ComboBox<>();
        workoutChoiceDropdown.setPromptText("Select workout");
        workoutChoiceRow.getChildren().addAll(chooseWorkoutLabel, workoutChoiceDropdown);

        workoutInputsBox = new VBox(10);
        workoutInputsBox.setAlignment(Pos.CENTER_LEFT);

        caloriesBurnedLabel = new Label();
        caloriesBurnedLabel.setFont(Font.font("Poppins", 16));

        workoutLayout.getChildren().addAll(wLabel, workoutGoalRow, workoutChoiceRow, workoutInputsBox, caloriesBurnedLabel);

        // ---------------- TRACKING TAB ----------------
        VBox trackingLayout = new VBox(15);
        trackingLayout.setPadding(new Insets(30));
        trackingLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffeaa7, #67f3e3ff);");

        Label trackingTitle = new Label("Daily Fitness Tracking");
        trackingTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        trackingTitle.setTextFill(Color.web("#79006dff"));

        stepsLabel = new Label("Steps: 0");
        caloriesLabel = new Label("Calories: 0");
        sleepLabel = new Label("Sleep: 0");

        Button logStepsBtn = new Button("Log Steps");
        logStepsBtn.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");
        Button logSleepBtn = new Button("Log Sleep");
        logSleepBtn.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");

        trackingLayout.getChildren().addAll(trackingTitle, stepsLabel, caloriesLabel, sleepLabel, logStepsBtn, logSleepBtn);

        // ---------------- TABS ----------------
        TabPane tabs = new TabPane();
        Tab profileTab = new Tab("Profile", profileLayout);
        Tab workoutTab = new Tab("Workout", workoutLayout);
        Tab trackingTab = new Tab("Tracking", trackingLayout);
        tabs.getTabs().addAll(profileTab, workoutTab, trackingTab);
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene mainScene = new Scene(tabs, 900, 650);

        // ---------------- BUTTON ACTIONS ----------------
        startBtn.setOnAction(e -> stage.setScene(mainScene));

        saveProfileBtn.setOnAction(e -> {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double weight = Double.parseDouble(weightField.getText());
            String goal = goalDropdown.getValue();
            double calorieGoal = Double.parseDouble(calorieGoalField.getText());
            double sleepGoal = Double.parseDouble(sleepGoalField.getText());

            user = new UserProfile(name, age, weight, goal);
            stepsTracker = new StepsTracker(10000);
            caloriesTracker = new CaloriesTracker(calorieGoal);
            sleepTracker = new SleepTracker(sleepGoal);

            // Update Profile Layout
            VBox summaryBox = new VBox(10);
            summaryBox.setAlignment(Pos.CENTER_LEFT);
            summaryBox.setPadding(new Insets(20));
            summaryBox.setStyle("-fx-background-color: #d9c2f5; -fx-border-color: #79006dff; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label summaryLabel = new Label(
                    "Name: " + user.getName() +
                    "\nAge: " + user.getAge() +
                    "\nWeight: " + user.getWeight() +
                    "\nGoal: " + user.getGoal() +
                    "\nDaily Calorie Goal: " + calorieGoal +
                    "\nDaily Sleep Goal: " + sleepGoal
            );
            summaryLabel.setFont(Font.font("Poppins", 18));
            summaryBox.getChildren().add(summaryLabel);

            profileLayout.getChildren().clear();
            profileLayout.getChildren().addAll(profileTitle, summaryBox);

            // Workout goal dropdown
            workoutGoalDropdown.setItems(FXCollections.observableArrayList(
                    "Weight Loss","Muscle Gain","Flexibility","Endurance","General Fitness"
            ));
            workoutGoalDropdown.setValue(goal);

            // Show recommended workouts
            workoutGoalDropdown.setOnAction(e2 -> updateWorkoutChoices(workoutGoalDropdown.getValue()));
            workoutChoiceDropdown.setOnAction(e3 -> showWorkoutInputs(workoutChoiceDropdown.getValue()));
        });

        // Tracking buttons
    logStepsBtn.setOnAction(e -> {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter steps walked:");
        dialog.showAndWait().ifPresent(s -> {
           try {
            int steps = Integer.parseInt(s);
            stepsTracker.update(steps);      // <-- use update()
            updateTrackingLabels();
           } catch(Exception ex) {
            showAlert("Invalid input!");
           }
         });
    });
    logSleepBtn.setOnAction(e -> {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setHeaderText("Enter sleep hours:");
    dialog.showAndWait().ifPresent(s -> {
        try {
            double hours = Double.parseDouble(s);
            sleepTracker.update(hours);     // <-- use update()
            updateTrackingLabels();
        } catch(Exception ex) {
            showAlert("Invalid input!");
        }
    });
});


        stage.setTitle("FitnessApp");
        stage.setScene(welcomeScene);
        stage.show();
    }

    private void updateWorkoutChoices(String goal) {
        workoutChoiceDropdown.getItems().clear();
        if (goal == null) return;

        switch (goal) {
            case "Weight Loss" -> workoutChoiceDropdown.getItems().addAll("HIIT","Cycling","Jump Rope","Running");
            case "Muscle Gain" -> workoutChoiceDropdown.getItems().addAll("Weight Lifting","Push-Ups","Squats","Bench Press");
            case "Flexibility" -> workoutChoiceDropdown.getItems().addAll("Yoga","Stretching","Pilates","Tai Chi");
            case "Endurance" -> workoutChoiceDropdown.getItems().addAll("Running","Rowing","Stair Climbing","Jump Squats");
            case "General Fitness" -> workoutChoiceDropdown.getItems().addAll("Jogging","Light Weights","Yoga","Cardio Mix");
        }
    }

    private void showWorkoutInputs(String workout) {
    workoutInputsBox.getChildren().clear();
    if (workout == null) return;

    Label label = new Label("Enter duration in minutes:");
    TextField input = new TextField();
    Button calc = new Button("Calculate Calories");

    calc.setOnAction(e -> {
        try {
            double minutes = Double.parseDouble(input.getText());
            double calories = estimateCalories(workout, minutes);
            totalCalories += calories;
            caloriesBurnedLabel.setText("Calories burned: " + totalCalories);
            caloriesTracker.update(calories);  // <-- use update()
            updateTrackingLabels();
        } catch(Exception ex) {
            showAlert("Invalid input!");
        }
    });

    workoutInputsBox.getChildren().addAll(label, input, calc);
}


    private double estimateCalories(String workout, double minutes) {
        return switch (workout) {
            case "HIIT" -> minutes * 10;
            case "Cycling", "Rowing" -> minutes * 8;
            case "Jump Rope", "Running", "Stair Climbing", "Jump Squats" -> minutes * 9;
            case "Weight Lifting", "Push-Ups", "Squats", "Bench Press", "Light Weights" -> minutes * 7;
            case "Yoga", "Stretching", "Pilates", "Tai Chi" -> minutes * 4;
            case "Jogging", "Cardio Mix" -> minutes * 6;
            default -> minutes * 5;
        };
    }
    private void updateTrackingLabels() {
    stepsLabel.setText("Steps: " + stepsTracker.getLatest());
    caloriesLabel.setText("Calories: " + caloriesTracker.getLatest());
    sleepLabel.setText("Sleep: " + sleepTracker.getLatest());
}

    
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
