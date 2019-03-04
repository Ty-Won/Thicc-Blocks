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
import javafx.scene.control.Alert.AlertType;
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

public class AvailableGames implements IPage {
	
	Stage stage;
	
	public AvailableGames(Stage stage) {
		this.stage = stage;
	}
	
	public void display() {

        // Create the login grid pane
        GridPane gridPane = createGridPane();
        // Add the UI components to the grid pane
        try {
			addUIComponents(gridPane);
		} catch (FileNotFoundException e) {
			Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error loading page:", e.getMessage());
		}
    	
        // Create the scene with gridPane as the root node
        Scene scene = new Scene(gridPane, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT, Color.WHITE);
        // Set the title scene and display it
        stage.setTitle("Thicc Blocks Application");
        stage.setScene(scene);
        stage.show();
        
	}
	
	private GridPane createGridPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);
        
        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) throws FileNotFoundException {
    	ObservableList data = 
    	        FXCollections.observableArrayList();
    	
    	List<TOGame> toGames = null;
    	try {
			toGames = Block223Controller.getDesignableGames();
			//if(toGames != null) Components.showAlert(AlertType.INFORMATION, stage.getOwner(), "", "Num games loaded:\n" + toGames.size());
			//else Components.showAlert(AlertType.INFORMATION, stage.getOwner(), "", "toGames is null");
		} catch (InvalidInputException e) {
			Components.showAlert(AlertType.INFORMATION, stage.getOwner(), "", "Error getting games:\n" + e.getMessage());
		}
    	
    	for(TOGame game : toGames) {
    		data.add(game.getName());
    	}
    	ListView<String> listView = new ListView<String>(data);
    	gridPane.add(listView, 0, 1);
    	
        Label headerLabel = new Label("Available Games");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
    	
        Button editButton = new Button("EDIT");
        editButton.setPrefHeight(40);
        editButton.setDefaultButton(true);
        editButton.setPrefWidth(130);
        editButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        editButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(editButton, 1, 1, 1, 1);
        GridPane.setHalignment(editButton, HPos.CENTER);
        GridPane.setMargin(editButton, new Insets(20, 0,20,0));
        
        Button deleteButton = new Button("DELETE");
        deleteButton.setPrefHeight(40);
        deleteButton.setDefaultButton(true);
        deleteButton.setPrefWidth(130);
        deleteButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        deleteButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(deleteButton, 3, 1, 1, 1);
        GridPane.setHalignment(deleteButton, HPos.CENTER);
        GridPane.setMargin(deleteButton, new Insets(20, 0,20,0));
        
        Button addButton = new Button("ADD");
        addButton.setPrefHeight(40);
        addButton.setDefaultButton(true);
        addButton.setPrefWidth(130);
        addButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        addButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(addButton, 3, 2, 1, 1);
        GridPane.setHalignment(addButton, HPos.CENTER);
        GridPane.setMargin(addButton, new Insets(20, 0,20,0));
        
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {       
            	String selectedGameName = listView.getSelectionModel().getSelectedItem();
            	if(selectedGameName == null) {
                	Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Edit Game" , "Please select a game to edit");   
            	}
            	else {
        			Game game = Block223Application.getBlock223().findGame(selectedGameName);
					if(game == null) Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Edit Game" , "selected game is null");
            		
					Block223Application.setCurrentGame(game);
                	IPage updateGamePage = Block223Application.getPage(Pages.UpdateGame);
                	updateGamePage.display();
            	}   
            }
        });
        
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {       
            	String selectedGameName = listView.getSelectionModel().getSelectedItem();
            	if(selectedGameName == null) {
                	Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Delete Game" , "Please select a game to delete");   
            	}
            	else {
            		try {
            			Game game = Block223Application.getBlock223().findGame(selectedGameName);
						if(game == null) Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Delete Game" , "game is null before delete");

						Block223Controller.deleteGame(selectedGameName);
						
						game = Game.getWithName(selectedGameName);
						if(game == null) Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Delete Game" , "game is null after delete");
						data.remove(selectedGameName);
	                	Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Delete Game" , "Successfully deleted game: " + selectedGameName);   
						
					} catch (InvalidInputException e) {
	                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Delete Game" , "Unable to delete the selected game:\n" + e.getMessage());   
					}
            		
            	}   
            }
        });
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {       
            	IPage createGamePage = Block223Application.getPage(Pages.CreateGame);
            	createGamePage.display();
            }
        });
    }

}
