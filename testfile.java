import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

import user.UserProfile; // your package
import tracking.CaloriesTracker;
import tracking.SleepTracker;
import tracking.StepsTracker;
import workout.*;


public class Testfile extends Application {

    private UserProfile user;

    // Trackers
    private StepsTracker stepsTracker;
    private CaloriesTracker caloriesTracker;
    private SleepTracker sleepTracker;

    // Profile Tab Fields
    private TextField nameField, ageField, weightField;
    private ComboBox<String> goalDropdown;
    private TextField calorieGoalField, sleepGoalField;
    private double userCalorieGoal = 0.0;
    private double userSleepGoal = 0.0;

    // Workout Tab Fields
    private ComboBox<String> workoutChoiceDropdown;
    private VBox workoutInputsBox;
    private Label caloriesBurnedLabel;

    // Tracking Tab
    private Label stepsLabel, caloriesLabel, sleepLabel;
    private int totalSteps = 0;
    private double totalCalories = 0.0;
    private double totalSleep = 0.0;

    @Override
    public void start(Stage stage) {

        // ---------------- WELCOME SCENE ----------------
        VBox welcomeLayout = new VBox(25);
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #beff74ff, #8b83f7ff);");
        welcomeLayout.setPadding(new Insets(40));

        Label welcomeLabel = new Label("Welcome to FitnessApp");
        welcomeLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 34));
        welcomeLabel.setStyle("-fx-text-fill: #79006dff;");

        Label descLabel = new Label("Your personal fitness companion to track, train, and transform!");
        descLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 16));
        descLabel.setTextFill(Color.web("#060404ff"));

        Button startBtn = new Button("Create Your Profile");
        startBtn.setStyle("-fx-background-color: #116e7bff ; -fx-text-fill: white ; -fx-font-weight: bold; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 10 25;");

        welcomeLayout.getChildren().addAll(welcomeLabel, descLabel, startBtn);
        Scene welcomeScene = new Scene(welcomeLayout, 800, 600);

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

        profileForm.addRow(0, new Label("Enter Name:"), nameField);
        profileForm.addRow(1, new Label("Enter Age:"), ageField);
        profileForm.addRow(2, new Label("Enter Weight (kg):"), weightField);
        profileForm.addRow(3, new Label("Fitness Goal:"), goalDropdown);
        profileForm.addRow(4, new Label("Enter Daily Calorie Goal:"), calorieGoalField);
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

        Label wLabel = new Label("Fitness planner & Calories Log");
        wLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 34));
        wLabel.setStyle("-fx-text-fill: #79006dff;");

        HBox workoutGoalRow = new HBox(10);
        workoutGoalRow.setAlignment(Pos.TOP_LEFT);
        Label goalLabel = new Label("Fitness Goal:");
        goalLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        ComboBox<String> workoutGoalDropdown = new ComboBox<>();
        workoutGoalDropdown.setPromptText("Select your goal");
        workoutGoalRow.getChildren().addAll(goalLabel, workoutGoalDropdown);

        HBox workoutChoiceRow = new HBox(10);
        workoutChoiceRow.setAlignment(Pos.CENTER_LEFT);
        Label chooseWorkoutLabel = new Label("Choose Workout:");
        chooseWorkoutLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        workoutChoiceDropdown = new ComboBox<>();
        workoutChoiceDropdown.setPromptText("Select workout");
        workoutChoiceRow.getChildren().addAll(chooseWorkoutLabel, workoutChoiceDropdown);

        workoutInputsBox = new VBox(10);
        workoutInputsBox.setAlignment(Pos.CENTER_LEFT);

        caloriesBurnedLabel = new Label();
        caloriesBurnedLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 16));

        workoutLayout.getChildren().addAll(wLabel,workoutGoalRow, workoutChoiceRow, workoutInputsBox, caloriesBurnedLabel);

        // ---------------- TRACKING TAB ----------------
        Label trackingTitle = new Label("Daily Fitness Tracking");
        trackingTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        trackingTitle.setTextFill(Color.web("#79006dff"));
        VBox trackingLayout = new VBox(15);
        trackingLayout.setAlignment(Pos.CENTER);
        trackingLayout.setPadding(new Insets(30));
        trackingLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffeaa7, #67f3e3ff);");

        stepsLabel = new Label();
        caloriesLabel = new Label();
        sleepLabel = new Label();

        Button logStepsBtn = new Button("Log Steps");
        logStepsBtn.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");

        Button logSleepBtn = new Button("Log Sleep");
        logSleepBtn.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");
        trackingLayout.getChildren().addAll(trackingTitle,stepsLabel, caloriesLabel, sleepLabel, logStepsBtn, logSleepBtn);

        // ---------------- UTILITIES TAB ----------------
        VBox utilitiesLayout = new VBox(15);
        utilitiesLayout.setAlignment(Pos.CENTER);
        utilitiesLayout.setPadding(new Insets(30));
        utilitiesLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffeaa7, #67f3e3ff);");

        Label utilTitle = new Label("Unit Conversion:");
        utilTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 18));
        utilTitle.setStyle("-fx-text-fill: #79006dff;");

        Button kgToLbs = new Button("kg -> lbs");
        kgToLbs.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");

        Button lbsToKg = new Button("lbs -> kg");
        lbsToKg.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");

        Button kmToMiles = new Button("km -> miles");
        kmToMiles.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");

        Button milesToKm = new Button("miles -> km");
        milesToKm.setStyle("-fx-background-color: #fc35cdff; -fx-text-fill: white ; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 15;");
        utilitiesLayout.getChildren().addAll(utilTitle, kgToLbs, lbsToKg, kmToMiles, milesToKm);

        // ---------------- TABS ----------------
        TabPane tabs = new TabPane();
        Tab profileTab = new Tab("Profile", profileLayout);
        Tab workoutTab = new Tab("Workout", workoutLayout);
        Tab trackingTabPane = new Tab("Tracking", trackingLayout);
        Tab utilitiesTab = new Tab("Utilities", utilitiesLayout);
        tabs.getTabs().addAll(profileTab, workoutTab, trackingTabPane, utilitiesTab);
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene mainScene = new Scene(tabs, 800, 600);

        // ---------------- BUTTON ACTIONS ----------------
        startBtn.setOnAction(e -> stage.setScene(mainScene));

        saveProfileBtn.setOnAction(e -> {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double weight = Double.parseDouble(weightField.getText());
            String goal = goalDropdown.getValue();
            userCalorieGoal = Double.parseDouble(calorieGoalField.getText());
            userSleepGoal = Double.parseDouble(sleepGoalField.getText());
            user = new UserProfile(name, age, weight, goal);

            stepsTracker = new StepsTracker(10000);
            caloriesTracker = new CaloriesTracker(userCalorieGoal);
            sleepTracker = new SleepTracker(userSleepGoal);

            // Clear profile form and show summary
            VBox summaryBox = new VBox(10);
            summaryBox.setAlignment(Pos.CENTER_LEFT);
            summaryBox.setPadding(new Insets(20));
            summaryBox.setStyle("-fx-background-color: rgba(231, 218, 247, 1); -fx-border-color: #79006dff; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label summaryLabel = new Label(
                    "Name: " + user.getName() +
                    "\nAge: " + user.getAge() +
                    "\nWeight: " + user.getWeight() +
                    "\nGoal: " + user.getGoal() +
                    "\nDaily Calorie Goal: " + userCalorieGoal +
                    "\nDaily Sleep Goal: " + userSleepGoal
            );
            summaryLabel.setFont(Font.font("Poppins", 18));
            summaryBox.getChildren().add(summaryLabel);

            profileLayout.getChildren().clear();
            profileLayout.getChildren().addAll(profileTitle, summaryBox);

            // Set Workout goal based on profile
            workoutGoalDropdown.setValue(goal);
            updateWorkoutChoices(goal);
        });

        workoutGoalDropdown.setOnAction(e -> updateWorkoutChoices(workoutGoalDropdown.getValue()));
        workoutChoiceDropdown.setOnAction(e -> showWorkoutInputs(workoutChoiceDropdown.getValue()));

        logStepsBtn.setOnAction(e -> {
            TextInputDialog stepsDialog = new TextInputDialog();
            stepsDialog.setHeaderText("Enter steps walked:");
            stepsDialog.showAndWait().ifPresent(s -> {
                totalSteps += Integer.parseInt(s);
                stepsTracker.update(Integer.parseInt(s));
                updateTrackingLabels();
            });
        });

        logSleepBtn.setOnAction(e -> {
            TextInputDialog sleepDialog = new TextInputDialog();
            sleepDialog.setHeaderText("Enter sleep hours:");
            sleepDialog.showAndWait().ifPresent(s -> {
                totalSleep += Double.parseDouble(s);
                sleepTracker.update(Double.parseDouble(s));
                updateTrackingLabels();
            });
        });

        stage.setTitle("FitnessApp UI");
        stage.setScene(welcomeScene);
        stage.show();
    }

    private void updateWorkoutChoices(String goal) {
        workoutChoiceDropdown.getItems().clear();
        if (goal == null) return;

        switch (goal) {
            case "Weight Loss" -> workoutChoiceDropdown.getItems().addAll("HIIT","Cycling","Jump Rope","Swimming");
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
            double minutes = Double.parseDouble(input.getText());
            double calories = switch(workout){
                case "HIIT" -> minutes*12;
                case "Cycling" -> minutes*8;
                case "Jump Rope" -> minutes*10;
                case "Swimming" -> minutes*8;
                case "Weight Lifting" -> minutes*6;
                case "Push-Ups" -> minutes*5;
                case "Squats" -> minutes*5;
                case "Bench Press" -> minutes*6;
                case "Yoga" -> minutes*4;
                case "Stretching" -> minutes*3;
                case "Pilates" -> minutes*5;
                case "Tai Chi" -> minutes*4;
                case "Running","Jogging","Walking" -> minutes*10;
                case "Rowing" -> minutes*9;
                case "Stair Climbing" -> minutes*9;
                case "Jump Squats" -> minutes*11;
                case "Light Weights" -> minutes*6;
                case "Cardio Mix" -> minutes*8;
                default -> 0.0;
            };
            totalCalories += calories;
            caloriesTracker.update(calories);
            caloriesBurnedLabel.setText(String.format("You burned %.1f kcal — %.1f%% of your daily goal!", calories, calories/userCalorieGoal*100));
            updateTrackingLabels();
        });

        workoutInputsBox.getChildren().addAll(label, input, calc);
    }

    private void updateTrackingLabels() {
        stepsLabel.setText("Total Steps: "+totalSteps);
        caloriesLabel.setText(String.format("Calories Burned: %.1f / %.1f kcal", totalCalories,userCalorieGoal));
        sleepLabel.setText(String.format("Sleep: %.1f / %.1f h", totalSleep,userSleepGoal));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
