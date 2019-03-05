package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Block223;
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

public class CreateGamePage implements IPage {
	
	Stage stage;
	
	public CreateGamePage(Stage stage) {
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
                	IPage availableGames = Block223Application.getPage(Pages.AvaliableGames);
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
        // Add Header
        Label headerLabel = new Label("Create a New Game");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel,0,0,4,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(40,0,20,0));

        // Add Game name Label
        Label gameNameLabel = new Label("Game Name: ");
        gridPane.add(gameNameLabel,0,1);

        // Add Game name Field
        TextField gameNameField = new TextField();
        gameNameField.setAlignment(Pos.CENTER);
        gameNameField.setPrefHeight(40);
        gridPane.add(gameNameField,1,1,3,1);
        
        // Add Max Blocks Per Level Label
        Label maxBlocksLabel = new Label("Max Blocks Per Level: ");
        gridPane.add(maxBlocksLabel,0,2);
        
        //Add numeric spinner for max blocks per level
        Spinner<Integer> spinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5);
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
        TextField minLengthField = new TextField();
        minLengthField.setAlignment(Pos.CENTER);
        minLengthField.setPrefHeight(40);
        gridPane.add(minLengthField,1,4);
        
        // Add Paddle Max Length Label
        Label maxLengthLabel = new Label("Max Length:");
        gridPane.add(maxLengthLabel,2,4);
        
        // Add Paddle Max Length Field
        TextField maxLengthField = new TextField();
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
        
        // Add Ball Min Speed Label
        Label minSpeedLabel = new Label("Min Speed: ");
        gridPane.add(minSpeedLabel,0,6);

        // Add Ball Min Speed Field
        TextField minSpeedField = new TextField();
        minSpeedField.setAlignment(Pos.CENTER);
        minSpeedField.setPrefHeight(40);
        gridPane.add(minSpeedField,1,6);
        
        // Add Ball Speed Up Factor Label
        Label speedUpLabel = new Label("Speed Up\nFactor:");
        gridPane.add(speedUpLabel,2,6);
        
        // Add Ball Speed Up Factor Field
        TextField speedUpField = new TextField();
        speedUpField.setAlignment(Pos.CENTER);
        speedUpField.setPrefHeight(40);
        gridPane.add(speedUpField,3,6);
        
        // Add Levels Button
        Button levelsButton = new Button("Levels");
        levelsButton.setPrefHeight(60);
        levelsButton.setDefaultButton(true);
        levelsButton.setPrefWidth(150);
        gridPane.add(levelsButton, 0, 7, 2, 1);
        GridPane.setHalignment(levelsButton, HPos.CENTER);
        GridPane.setMargin(levelsButton, new Insets(20,0,20,0));
        
        // Add Blocks Button
        Button blocksButton = new Button("Blocks");
        blocksButton.setPrefHeight(60);
        blocksButton.setDefaultButton(true);
        blocksButton.setPrefWidth(150);
        gridPane.add(blocksButton, 2, 7, 2, 1);
        GridPane.setHalignment(blocksButton, HPos.CENTER);
        GridPane.setMargin(blocksButton, new Insets(20,0,20,0));
        
        // Add Create Game Button
        Button createButton = new Button("Create");
        createButton.setPrefHeight(60);
        createButton.setDefaultButton(true);
        createButton.setPrefWidth(150);
        gridPane.add(createButton, 0, 8, 4, 1);
        GridPane.setHalignment(createButton, HPos.CENTER);
        GridPane.setMargin(createButton, new Insets(20,0,20,0));
        
        
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeed = Integer.parseInt(minSpeedField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.createGame(gameNameField.getText());
                    	
                    	Block223Controller.selectGame(gameNameField.getText());
                    	
                    	Block223Controller.setGameDetails(1, spinner.getValue(), minSpeed, minSpeed, speedUpFactor, maxLength, minLength);
                    	
                    	IPage availableGames = Block223Application.getPage(Pages.AvaliableGames);
                    	availableGames.display();
    	                
    				} catch (InvalidInputException e) {
    					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Create Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Create Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
        
        levelsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeed = Integer.parseInt(minSpeedField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.createGame(gameNameField.getText());
                    	
                    	//Selects the game that was just made as the currentDesignableGame so that the available levels page can access the right one
                    	Block223Controller.selectGame(gameNameField.getText());
                    	
                    	Block223Controller.setGameDetails(1, spinner.getValue(), minSpeed, minSpeed, speedUpFactor, maxLength, minLength);

                    	IPage availableLevels = Block223Application.getPage(Pages.AvailableLevels);
                    	availableLevels.display();
    	                
    				} catch (InvalidInputException e) {
    					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Create Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Create Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
        
        blocksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	int minSpeed = Integer.parseInt(minSpeedField.getText());
                	double speedUpFactor = Double.parseDouble(speedUpField.getText());
                	int maxLength = Integer.parseInt(maxLengthField.getText());
                	int minLength = Integer.parseInt(minLengthField.getText());
                	
                	try {
                    	Block223Controller.createGame(gameNameField.getText());
                    	
                    	//Selects the game that was just made as the currentDesignableGame so that the available levels page can access the right one
                    	Block223Controller.selectGame(gameNameField.getText());
                    	
                    	Block223Controller.setGameDetails(1, spinner.getValue(), minSpeed, minSpeed, speedUpFactor, maxLength, minLength);
                    	
                    	//Currently redirects to the welcome page until other pages are added!!
                    	//TODO Change to redirect to Available Blocks page
                    	WelcomePage welcomePage = new WelcomePage(stage);
    	                welcomePage.display();
    	                
    				} catch (InvalidInputException e) {
    					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Create Game Error", e.getMessage());
    				}
                	
                } catch (NumberFormatException n) {
                	showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Create Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
                }
              
            }
        });
    }
    
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
