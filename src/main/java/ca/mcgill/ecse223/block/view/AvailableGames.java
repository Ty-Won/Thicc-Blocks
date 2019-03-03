package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
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

public class AvailableGames {
	
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

        /*
        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40)); 

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // set background color to white
        gridPane.setStyle("-fx-background-color: #fff;");
        
        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
		*/
        
        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) throws FileNotFoundException {
    	ObservableList data = 
    	        FXCollections.observableArrayList();
    	
    	List<TOGame> toGames = null;
    	try {
			toGames = Block223Controller.getDesignableGames();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
            		Block223Application.setCurrentGame(Game.getWithName(selectedGameName));
                	UpdateGamePage updateGamePage = new UpdateGamePage(stage);
                	try {
						updateGamePage.display();
					} catch (InvalidInputException e) {
	                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Edit Game" , "Unable to switch to update game page: " + e.getMessage());   
					}
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
						Block223Controller.deleteGame(selectedGameName);
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
            	CreateGamePage createGamePage = new CreateGamePage(stage);
            	createGamePage.display();
            }
        });
        
    	/*
        // Add logo
        ImageView logo = new ImageView();   
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        Image image = new Image(new FileInputStream("Images/logo.PNG"));
        logo.setImage(image);
        gridPane.add(logo, 1, 0);
        GridPane.setHalignment(logo, HPos.CENTER);
        GridPane.setMargin(logo, new Insets(0,75,0,0));
        
        // Add Header
        Label headerLabel = new Label("THICC BLOCKS");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 0,1,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Username Label
        Label usernameLabel = new Label("Username ");
        usernameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(usernameLabel, 0,2);

        // Add Username Field
        TextField usernameField = new TextField();
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-border-color: black; -fx-border-radius: 1%;");
        gridPane.add(usernameField, 1,2);

        // Add Password Label
        Label passwordLabel = new Label("Password ");
        passwordLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(passwordLabel, 0, 3);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-border-color: black; -fx-border-radius: 1%;");
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 3);

        // Add Login Button
        Button loginButton = new Button("LOGIN");
        loginButton.setPrefHeight(40);
        loginButton.setDefaultButton(true);
        loginButton.setPrefWidth(130);
        loginButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        loginButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(loginButton, 0, 4, 2, 1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setMargin(loginButton, new Insets(20, 0,20,0));
        
        // Add Create User Button 
        Button createUserButon = new Button("Create User");
        createUserButon.setPrefHeight(40);
        createUserButon.setDefaultButton(true);
        createUserButon.setPrefWidth(130);
        createUserButon.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        createUserButon.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(createUserButon, 0, 5, 2, 1);
        GridPane.setHalignment(createUserButon, HPos.CENTER);
        GridPane.setMargin(createUserButon, new Insets(20, 0,20,0));

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(usernameField.getText().isEmpty()) {
                    Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error", "Please enter a username");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error", "Please enter a password");
                    return;
                }
                
                try {
					Block223Controller.login(usernameField.getText(), passwordField.getText());
					WelcomePage welcomePage = new WelcomePage(stage);
	                welcomePage.display();
					
				} catch (InvalidInputException e) {
					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error", e.getMessage());
				}
              
            }
        });
        
        createUserButon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(usernameField.getText().isEmpty()) {
                    Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", "Please enter a username");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", "Please enter a password");
                    return;
                }

                try {
					Block223Controller.register(usernameField.getText(), passwordField.getText(), null);
					Components.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Registration Successful!");
				} catch (InvalidInputException e) {
					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", e.getMessage());
				}
              
            }
        });
    	*/
    }

}
