package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
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
import javafx.scene.control.TextField;
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

public class RegisterPage implements IPage {
	
	Stage stage;
	
	public RegisterPage(Stage stage) {
		this.stage = stage;
	}
	
	public void display() {
		// Create the create game grid pane
        GridPane gridPane = createGridPane();
        // Add the UI components to the grid pane
        try {
			addUIComponents(gridPane);
		} catch (FileNotFoundException e) {
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
                	IPage loginPage = Block223Application.getPage(Pages.Login);
					loginPage.display();
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

        // set background color to white
        gridPane.setStyle("-fx-background-color: #fff;");
        
        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) throws FileNotFoundException {
        
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

        // Add Player Password Label
        Label passwordLabel = new Label("Player Password ");
        passwordLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(passwordLabel, 0, 3);

        // Add Player Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-border-color: black; -fx-border-radius: 1%;");
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 3);
        
        // Add Admin Password Label
        Label adminPasswordLabel = new Label("Admin Password ");
        adminPasswordLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(adminPasswordLabel, 0, 4);

        // Add Admin Password Field
        PasswordField adminPasswordField = new PasswordField();
        adminPasswordField.setStyle("-fx-border-color: black; -fx-border-radius: 1%;");
        adminPasswordField.setPrefHeight(40);
        gridPane.add(adminPasswordField, 1, 4);
        
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
        
        createUserButon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(usernameField.getText().isEmpty()) {
                    Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", "Please enter a username");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", "Please enter a player password");
                    return;
                }
                if(adminPasswordField.getText().isEmpty()) {
                	Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", "Please enter an admin password");
                    return;
                }

                try {
					Block223Controller.register(usernameField.getText(), passwordField.getText(), adminPasswordField.getText());
					IPage loginPage = Block223Application.getPage(Pages.Login);
					loginPage.display();
					//Components.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Registration Successful!");
				} catch (InvalidInputException e) {
					Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", e.getMessage());
				}
              
            }
        });
    }

}
