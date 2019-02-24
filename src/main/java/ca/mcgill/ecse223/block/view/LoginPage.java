package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.application.Block223Application;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginPage {
	
	Stage stage;
	
	public LoginPage(Stage stage) {
		this.stage = stage;
	}
	
	public void display() {

        // Create the login grid pane
        GridPane gridPane = createGridPane();
        // Add the UI components to the grid pane
        addUIComponents(gridPane);
        // Create the scene with gridPane as the root node
        Scene scene = new Scene(gridPane, 1000, 600);
        // Set the title scene and display it
        stage.setTitle("ThiccBlocks Application");
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
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("THICC BLOCKS");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Username Label
        Label usernameLabel = new Label("Username ");
        gridPane.add(usernameLabel, 0,1);

        // Add Username Field
        TextField usernameField = new TextField();
        usernameField.setPrefHeight(40);
        gridPane.add(usernameField, 1,1);

        // Add Password Label
        Label passwordLabel = new Label("Password ");
        gridPane.add(passwordLabel, 0, 2);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 2);

        // Add Login Button
        Button loginButton = new Button("Login");
        loginButton.setPrefHeight(40);
        loginButton.setDefaultButton(true);
        loginButton.setPrefWidth(100);
        gridPane.add(loginButton, 0, 3, 2, 1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setMargin(loginButton, new Insets(20, 0,20,0));
        
        // Add Create User Button 
        Button createUserButon = new Button("Create User");
        createUserButon.setPrefHeight(40);
        createUserButon.setDefaultButton(true);
        createUserButon.setPrefWidth(100);
        gridPane.add(createUserButon, 0, 4, 2, 1);
        GridPane.setHalignment(createUserButon, HPos.CENTER);
        GridPane.setMargin(createUserButon, new Insets(20, 0,20,0));

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(usernameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error", "Please enter a username");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error", "Please enter a password");
                    return;
                }
                
                try {
					Block223Controller.login(usernameField.getText(), passwordField.getText());
					WelcomePage welcomePage = new WelcomePage(stage);
	                welcomePage.display();
				} catch (InvalidInputException e) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error", e.getMessage());
				}
              
            }
        });
        
        createUserButon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(usernameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", "Please enter a username");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", "Please enter a password");
                    return;
                }

                try {
					Block223Controller.register(usernameField.getText(), passwordField.getText(), null);
					showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Registration Successful!");
				} catch (InvalidInputException e) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Registration Error", e.getMessage());
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
