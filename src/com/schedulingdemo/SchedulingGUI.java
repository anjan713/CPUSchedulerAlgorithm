
package com.schedulingdemo;

import com.schedulingdemo.algorithms.FCFSScheduler;
import com.schedulingdemo.algorithms.RoundRobinScheduler;
import com.schedulingdemo.algorithms.SJFScheduler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SchedulingGUI extends Application {

    private List<Process> processes = new ArrayList<>();
    private TextArea resultArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CPU Scheduling Algorithms");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Process input fields
        TextField nameField = new TextField();
        TextField arrivalTimeField = new TextField();
        TextField burstTimeField = new TextField();

        grid.add(new Label("Process Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Arrival Time:"), 0, 1);
        grid.add(arrivalTimeField, 1, 1);
        grid.add(new Label("Burst Time:"), 0, 2);
        grid.add(burstTimeField, 1, 2);

        Button addButton = new Button("Add Process");
        grid.add(addButton, 1, 3);

        // Algorithm selection
        ComboBox<String> algorithmCombo = new ComboBox<>();
        algorithmCombo.getItems().addAll("FCFS", "SJF", "Round Robin");
        algorithmCombo.setValue("FCFS");
        grid.add(new Label("Select Algorithm:"), 0, 4);
        grid.add(algorithmCombo, 1, 4);

        TextField quantumField = new TextField();
        quantumField.setPromptText("Time Quantum (for RR)");
        grid.add(quantumField, 1, 5);

        Button runButton = new Button("Run Algorithm");
        grid.add(runButton, 1, 6);

        // New Clear button
        Button clearButton = new Button("Clear Results");
        grid.add(clearButton, 1, 7);

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(20);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(grid, resultArea);

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event handlers
        addButton.setOnAction(e -> addProcess(nameField, arrivalTimeField, burstTimeField));
        runButton.setOnAction(e -> runAlgorithm(algorithmCombo.getValue(), quantumField.getText()));
        clearButton.setOnAction(e -> clearResults());
    }

    private void addProcess(TextField nameField, TextField arrivalTimeField, TextField burstTimeField) {
        try {
            String name = nameField.getText();
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            int burstTime = Integer.parseInt(burstTimeField.getText());

            processes.add(new Process(name, arrivalTime, burstTime));
            resultArea.appendText("Process added: " + name + "\n");

            // Clear input fields
            nameField.clear();
            arrivalTimeField.clear();
            burstTimeField.clear();
        } catch (NumberFormatException ex) {
            resultArea.appendText("Invalid input. Please enter valid numbers.\n");
        }
    }

    private void runAlgorithm(String algorithm, String quantumText) {
        if (processes.isEmpty()) {
            resultArea.appendText("No processes to schedule.\n");
            return;
        }

        SchedulingAlgorithm scheduler;
        switch (algorithm) {
            case "FCFS":
                scheduler = new FCFSScheduler();
                break;
            case "SJF":
                scheduler = new SJFScheduler();
                break;
            case "Round Robin":
                try {
                    int quantum = Integer.parseInt(quantumText);
                    scheduler = new RoundRobinScheduler(quantum);
                } catch (NumberFormatException e) {
                    resultArea.appendText("Invalid time quantum. Using default value of 2.\n");
                    scheduler = new RoundRobinScheduler(2);
                }
                break;
            default:
                resultArea.appendText("Invalid algorithm selection.\n");
                return;
        }

        resultArea.appendText("\nRunning " + algorithm + " Algorithm:\n");
        resultArea.appendText(scheduler.schedule(new ArrayList<>(processes)));
    }

    private void clearResults() {
        processes.clear();
        resultArea.clear();
        resultArea.appendText("All processes and results cleared.\n");
    }
}