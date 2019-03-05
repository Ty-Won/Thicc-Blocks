package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AvailableLevelsPage implements IPage {

    Stage stage;

    private int numberOfLevels = 0;

    public AvailableLevelsPage(Stage stage) {
        this.stage = stage;
    }

    public void display() {

        // Create the login grid pane
        GridPane gridPane = createGridPane();
        // Add the UI components to the grid pane
        try {
            addUIComponents(gridPane);
        } catch (FileNotFoundException e) {
            Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error loading page:",
                    e.getMessage());
        }

        // Create the scene with gridPane as the root node
        Scene scene = new Scene(gridPane, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT,
                Color.WHITE);
        // Set the title scene and display it
        stage.setTitle("Available Levels");
        stage.setScene(scene);
        stage.show();

    }

    private GridPane createGridPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and
        // horizontally
        gridPane.setAlignment(Pos.CENTER);


        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) throws FileNotFoundException {
        
        ObservableList<String> data = FXCollections.observableArrayList();

        try {
            numberOfLevels = Block223Controller.getCurrentDesignableGame().getNrLevels();
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }

        for (int playableLevelNumber=1; playableLevelNumber <= numberOfLevels; playableLevelNumber++){
            data.add("Level " + playableLevelNumber);
        }
        
        ListView<String> listView = new ListView<String>(data);
        gridPane.add(listView, 1, 1);

        Label headerLabel = new Label("Available Levels");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 1, 0, 1, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        Button deleteButton = new Button("DELETE");
        deleteButton.setPrefHeight(40);
        deleteButton.setDefaultButton(true);
        deleteButton.setPrefWidth(130);
        deleteButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        deleteButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(deleteButton, 0, 3, 1, 1);
        GridPane.setHalignment(deleteButton, HPos.CENTER);
        GridPane.setMargin(deleteButton, new Insets(20, 0,20,0));

        Button editButton = new Button("EDIT");
        editButton.setPrefHeight(40);
        editButton.setDefaultButton(true);
        editButton.setPrefWidth(130);
        editButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        editButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(editButton, 1, 3, 1, 1);
        GridPane.setHalignment(editButton, HPos.CENTER);
        GridPane.setMargin(editButton, new Insets(20, 0, 20, 0));


        Button addButton = new Button("ADD");
        addButton.setPrefHeight(40);
        addButton.setDefaultButton(true);
        addButton.setPrefWidth(130);
        addButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        addButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(addButton, 1, 2, 1, 1);
        GridPane.setHalignment(addButton, HPos.CENTER);
        GridPane.setMargin(addButton, new Insets(20, 0, 20, 0));


        Button doneButton = new Button("DONE");
        doneButton.setPrefHeight(40);
        doneButton.setDefaultButton(true);
        doneButton.setPrefWidth(130);
        doneButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        doneButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(doneButton, 0, 4, 2, 1);
        GridPane.setHalignment(doneButton, HPos.CENTER);
        GridPane.setMargin(doneButton, new Insets(20, 0, 20, 0));


        //EDIT
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedId = listView.getSelectionModel().getSelectedIndex();

                //Index is invalid
                if (selectedId == -1) {
                    Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Edit Level",
                            "Please select a Level to edit");
                } else {
                    int levelId = selectedId + 1;
                    EditLevelPage editLevelPage = (EditLevelPage)Block223Application.getPage(Pages.EditLevel);
                    editLevelPage.setLevelID(levelId);
                    editLevelPage.display();
                }
            }
        });

        //DONE
        doneButton.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                UpdateGamePage updateGamePage = (UpdateGamePage) Block223Application.getPage(Pages.UpdateGame);
                updateGamePage.display();
            }
        });


        // DELETE
        deleteButton.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event){

                if (numberOfLevels > 0) {
                     // Decrese the number of levels
                    numberOfLevels--;
                    int selectedId = numberOfLevels;

                    try {
                        TOGame game = Block223Controller.getCurrentDesignableGame();
                        
                        Block223Controller.setGameDetails(
                            numberOfLevels, 
                            game.getNrBlocksPerLevel(),
                            game.getMinBallSpeedX(),
                            game.getMinBallSpeedY(),
                            game.getBallSpeedIncreaseFactor(),
                            game.getMaxPaddleLength(),
                            game.getMinPaddleLength());

                        data.remove(selectedId);

                        numberOfLevels = Block223Controller.getCurrentDesignableGame().getNrLevels();
                    } catch (InvalidInputException e) {
                        showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
                    }

                }

            }
        });

    

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // Increase the number of levels
                numberOfLevels++;

                if (numberOfLevels <= Game.MAX_NR_LEVELS){
                    try {
                        TOGame game = Block223Controller.getCurrentDesignableGame();
                        
                        Block223Controller.setGameDetails(
                            numberOfLevels, 
                            game.getNrBlocksPerLevel(),
                            game.getMinBallSpeedX(),
                            game.getMinBallSpeedY(),
                            game.getBallSpeedIncreaseFactor(),
                            game.getMaxPaddleLength(),
                            game.getMinPaddleLength());

                        numberOfLevels = Block223Controller.getCurrentDesignableGame().getNrLevels();
                        data.add("Level " + numberOfLevels);

                    } catch (InvalidInputException e) {
                        showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
                    }

                }

            }
        });

    }

     /**
     * Helper method for showing alert
     */
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
