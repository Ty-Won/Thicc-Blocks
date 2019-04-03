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
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AvailableGamesPlayer implements IPage {
	
	Stage stage;
	
	public AvailableGamesPlayer(Stage stage) {
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
		} catch (Exception e) {
			Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error loading page:", e.getMessage());
		}
        
        //Create an HBox to hold the back button at the top of the screen
        HBox hbButtons = new HBox();
        
        //Add Back Button, requires catch for file not found
        try {
        	FileInputStream backImageInput = new FileInputStream("Images/next.png");
        	Image backImage = new Image(backImageInput); 
            ImageView backImageView = new ImageView(backImage); 
            backImageView.setFitHeight(60);
            backImageView.setFitWidth(60);
            
            Button backButton = new Button("", backImageView);
            backButton.setStyle("-fx-base: #92D3FC;");	//Styles the default background color of the button
            hbButtons.getChildren().add(backButton);
            hbButtons.setAlignment(Pos.CENTER_LEFT);
            
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	Block223Controller.logout();
                	IPage login = Block223Application.getPage(Pages.Login);
                	login.display();
                }
            });
        } catch (FileNotFoundException e) {
        	System.out.println("File not found");
        }
    	
        BorderPane root = new BorderPane();
	    root.setPadding(new Insets(20)); // space between elements and window border
	    root.setCenter(gridPane);
	    root.setTop(hbButtons);
	    
        // Create the scene with gridPane as the root node
        Scene scene = new Scene(root, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT, Color.WHITE);
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

    private void addUIComponents(GridPane gridPane) throws FileNotFoundException, Exception {
    	ObservableList data = 
    	        FXCollections.observableArrayList();
    	
    	List<TOPlayableGame> games = Block223Controller.getPlayableGames();
    	
    	for(TOPlayableGame game : games) {
    		data.add(game.getName());
    	}
    	ListView<String> listView = new ListView<String>(data);
    	gridPane.add(listView, 0, 1);
    	
        Label headerLabel = new Label("Available Games");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        
        Button playButton = new Button("PLAY");
        playButton.setPrefHeight(40);
        playButton.setDefaultButton(true);
        playButton.setPrefWidth(130);
        playButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        playButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(playButton, 3, 1, 1, 1);
        GridPane.setHalignment(playButton, HPos.CENTER);
        GridPane.setMargin(playButton, new Insets(20, 0,20,0));
        
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {       
            	int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            	if (selectedIndex == -1) {
            		Components.showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Play Game" , "Please select a game to play");
            	}
            	TOPlayableGame selectedGame = games.get(selectedIndex);
            	int selectedGameId = selectedGame.getNumber();
            	PlayedGame selectedPlayedGame = null;
            	if (selectedGameId == -1) { // game id is -1 if playedGame doesn't exist yet
            		Game game = Block223Controller.getGameByName(selectedGame.getName());
            		selectedPlayedGame = new PlayedGame(Block223Application.getCurrentUser().getUsername(), game, Block223Application.getBlock223());
            	} else {
            		selectedPlayedGame = Block223Application.getBlock223().findPlayableGame(selectedGameId);
            	}
            	Block223Application.setCurrentPlayableGame(selectedPlayedGame);
            	// TODO: Add transition to game playing page 
            } 
        });
        
    }

}
