package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UpdateGamePage implements IPage {
	
	Stage stage;
	
	public UpdateGamePage(Stage stage) {
		this.stage = stage;
	}
	
	public void display() {
		// Create the create game grid pane
        GridPane gridPane = createGridPane();
        // Add the UI components to the grid pane
        addUIComponents(gridPane);
        
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
                	IPage availableGames = Block223Application.getPage(Pages.AvaliableGamesAdmin);
                	availableGames.display();
                }
            });
        } catch (FileNotFoundException e) {
        	System.out.println("File not found");
        }

        //Create a border pane which will hold the gridPane in the center of the screen and the HBox at the top
        BorderPane root = new BorderPane();
	    root.setPadding(new Insets(20)); // space between elements and window border
	    root.setCenter(gridPane);
	    root.setTop(hbButtons);
	    
        // Create the scene with borderPane as the root node (since it contains everything else)
        Scene scene = new Scene(root, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT);
        // Set the scene and display it
        stage.setScene(scene);
        stage.show(); 
	}
	
	private GridPane createGridPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40)); 

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.LEFT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstraints = new ColumnConstraints(150,150, Double.MAX_VALUE);
        columnTwoConstraints.setHalignment(HPos.CENTER);
        
        //Column Three
        ColumnConstraints columnThreeConstraints = new ColumnConstraints(150,150, Double.MAX_VALUE);
        columnThreeConstraints.setHalignment(HPos.CENTER);
        
        //Column Four
        ColumnConstraints columnFourConstraints = new ColumnConstraints(150,150, Double.MAX_VALUE);
        columnFourConstraints.setHalignment(HPos.CENTER);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints, columnThreeConstraints, columnFourConstraints);
        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) {
        TOGame game = null;
		try {
			game = Block223Controller.getCurrentDesignableGame();
		} catch (InvalidInputException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        // Add Header
        Label headerLabel = new Label("Update a Game");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel,0,0,4,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(40,0,20,0));
        
        // Add Game name Label
        Label gameNameLabel = new Label("Game Name: ");
        gridPane.add(gameNameLabel,0,1);

        // Add Game name Field
        TextField gameNameField = new TextField(game.getName());
        gameNameField.setAlignment(Pos.CENTER);
        gameNameField.setPrefHeight(40);
        gridPane.add(gameNameField,1,1,3,1);
        
        // Add Max Blocks Per Level Label
        Label maxBlocksLabel = new Label("Max Blocks Per Level: ");
        gridPane.add(maxBlocksLabel,0,2);
        
        //Add numeric spinner for max blocks per level
        Spinner<Integer> spinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, game.getNrBlocksPerLevel());
        spinner.setValueFactory(valueFactory);
        spinner.setPrefHeight(40);
        gridPane.add(spinner, 2, 2);
        
        // Add Paddle Header
        Label paddleLabel = new Label("Paddle");
        paddleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
        
        StackPane paddlePane = new StackPane();
        paddlePane.setStyle("-fx-background-color: grey;");
        paddlePane.getChildren().addAll(paddleLabel);
        
        StackPane.setAlignment(paddleLabel, Pos.CENTER);
        gridPane.add(paddlePane,0,3,4,1);
        
        // Add Paddle Min Length Label
        Label minLengthLabel = new Label("Min Length: ");
        gridPane.add(minLengthLabel,0,4);

        // Add Paddle Min Length Field
        TextField minLengthField = new TextField(Integer.toString(game.getMinPaddleLength()));
        minLengthField.setAlignment(Pos.CENTER);
        minLengthField.setPrefHeight(40);
        gridPane.add(minLengthField,1,4);
        
        // Add Paddle Max Length Label
        Label maxLengthLabel = new Label("Max Length:");
        gridPane.add(maxLengthLabel,2,4);
        
        // Add Paddle Max Length Field
        TextField maxLengthField = new TextField(Integer.toString(game.getMaxPaddleLength()));
        maxLengthField.setAlignment(Pos.CENTER);
        maxLengthField.setPrefHeight(40);
        gridPane.add(maxLengthField,3,4);
        
        // Add Ball Header
        Label ballLabel = new Label("Ball");
        ballLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
        
        StackPane ballPane = new StackPane();
        ballPane.setStyle("-fx-background-color: grey;");
        ballPane.getChildren().addAll(ballLabel);
        
        StackPane.setAlignment(ballLabel, Pos.CENTER);
        gridPane.add(ballPane,0,5,4,1);
        //GridPane.setHalignment(ballLabel, HPos.CENTER);
        
        // Add Ball Min Speed X Label
        Label minSpeedXLabel = new Label("Min Speed: ");
        gridPane.add(minSpeedXLabel,0,6);

        // Add Ball Min Speed X Field
        TextField minSpeedXField = new TextField(Integer.toString(game.getMinBallSpeedX()));
        minSpeedXField.setAlignment(Pos.CENTER);
        minSpeedXField.setPrefHeight(40);
        gridPane.add(minSpeedXField,1,6);
        
        // Add Ball Min Speed X Label
        Label minSpeedYLabel = new Label("Min Speed: ");
        gridPane.add(minSpeedYLabel,2,6);

        // Add Ball Min Speed X Field
        TextField minSpeedYField = new TextField(Integer.toString(game.getMinBallSpeedY()));
        minSpeedYField.setAlignment(Pos.CENTER);
        minSpeedYField.setPrefHeight(40);
        gridPane.add(minSpeedYField,3,6);
        
        // Add Ball Speed Up Factor Label
        Label speedUpLabel = new Label("Speed Up Factor:");
        gridPane.add(speedUpLabel,0,7);
        
        // Add Ball Speed Up Factor Field
        TextField speedUpField = new TextField(Double.toString(game.getBallSpeedIncreaseFactor()));
        speedUpField.setAlignment(Pos.CENTER);
        speedUpField.setPrefHeight(40);
        gridPane.add(speedUpField,1,7);
        
        // Add Levels Button
        Button levelsButton = new Button("Levels");
        levelsButton.setPrefHeight(60);
        levelsButton.setDefaultButton(true);
        levelsButton.setPrefWidth(150);
        levelsButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        levelsButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(levelsButton, 0, 8, 2, 1);
        GridPane.setHalignment(levelsButton, HPos.CENTER);
        GridPane.setMargin(levelsButton, new Insets(20,0,20,0));
        
        // Add Blocks Button
        Button blocksButton = new Button("Blocks");
        blocksButton.setPrefHeight(60);
        blocksButton.setDefaultButton(true);
        blocksButton.setPrefWidth(150);
        blocksButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        blocksButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(blocksButton, 2, 8, 2, 1);
        GridPane.setHalignment(blocksButton, HPos.CENTER);
        GridPane.setMargin(blocksButton, new Insets(20,0,20,0));
        
        // Add Test Game Button
        Button testButton = new Button("Test");
        testButton.setPrefHeight(60);
        testButton.setDefaultButton(true);
        testButton.setPrefWidth(150);
        testButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        testButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(testButton, 0, 9, 2, 1);
        GridPane.setHalignment(testButton, HPos.CENTER);
        GridPane.setMargin(testButton, new Insets(20,0,20,0));
        

        // Add Publish Game Button
        Button publishButton = new Button("Publish");
        publishButton.setPrefHeight(60);
        publishButton.setDefaultButton(true);
        publishButton.setPrefWidth(150);
        publishButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        publishButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(publishButton, 2, 9, 2, 1);
        GridPane.setHalignment(publishButton, HPos.CENTER);
        GridPane.setMargin(publishButton, new Insets(20,0,20,0));
        
        // Add Update Game Button
        Button updateButton = new Button("Update");
        updateButton.setPrefHeight(60);
        updateButton.setDefaultButton(true);
        updateButton.setPrefWidth(150);
        updateButton.setStyle("-fx-background-color: #008000;-fx-text-fill: #fff;");
        updateButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(updateButton, 2, 10, 2, 1);
        GridPane.setHalignment(updateButton, HPos.CENTER);
        GridPane.setMargin(updateButton, new Insets(20,0,20,0));
      
        
        final int gameNrLevels = game.getNrLevels();
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeedX = Integer.parseInt(minSpeedXField.getText());
                	int minSpeedY = Integer.parseInt(minSpeedYField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.updateGame(gameNameField.getText(), gameNrLevels, spinner.getValue(), minSpeedX, minSpeedY, speedUpFactor, maxLength, minLength);
    					Block223Persistence.save(Block223Application.getBlock223());
    					
                    	IPage availableGames = Block223Application.getPage(Pages.AvaliableGamesAdmin);
                    	availableGames.display();
    				} catch (InvalidInputException e) {
    					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
        
        levelsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeedX = Integer.parseInt(minSpeedXField.getText());
                	int minSpeedY = Integer.parseInt(minSpeedYField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.updateGame(gameNameField.getText(), gameNrLevels, spinner.getValue(), minSpeedX, minSpeedY, speedUpFactor, maxLength, minLength);
    					
                    	IPage availableLevels = Block223Application.getPage(Pages.AvailableLevels);
                    	availableLevels.display();
    				} catch (InvalidInputException e) {
    					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
        
        blocksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeedX = Integer.parseInt(minSpeedXField.getText());
                	int minSpeedY = Integer.parseInt(minSpeedYField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.updateGame(gameNameField.getText(), gameNrLevels, spinner.getValue(), minSpeedX, minSpeedY, speedUpFactor, maxLength, minLength);
    					
                    	IPage availableBlocks = Block223Application.getPage(Pages.AvailableBlocks);
                    	availableBlocks.display();
    				} catch (InvalidInputException e) {
    					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
        
        publishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeedX = Integer.parseInt(minSpeedXField.getText());
                	int minSpeedY = Integer.parseInt(minSpeedYField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.updateGame(gameNameField.getText(), gameNrLevels, spinner.getValue(), minSpeedX, minSpeedY, speedUpFactor, maxLength, minLength);
    					Block223Persistence.save(Block223Application.getBlock223());
                        
                        // Publish the game
                        Block223Controller.publishGame();
                        
                    	IPage availableGames = Block223Application.getPage(Pages.AvaliableGamesAdmin);
                    	availableGames.display();
    				} catch (InvalidInputException e) {
    					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
        
        testButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeedX = Integer.parseInt(minSpeedXField.getText());
                	int minSpeedY = Integer.parseInt(minSpeedYField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.updateGame(gameNameField.getText(), gameNrLevels, spinner.getValue(), minSpeedX, minSpeedY, speedUpFactor, maxLength, minLength);
    					Block223Persistence.save(Block223Application.getBlock223());
                        
                        // TODO: Add code to start testing a game
    					// Block223Controller.testGame(ui);
                        
    				} catch (InvalidInputException e) {
    					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Update Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
        
        

    }

}
