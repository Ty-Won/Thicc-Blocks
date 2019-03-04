package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.User;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WelcomePage implements IPage {

	Stage stage;
	
	public WelcomePage(Stage stage) {
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
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) throws FileNotFoundException {
        // Add Header
        Label headerLabel = new Label("THICC BLOCKS");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        
        // Add welcome message
        Label welcomeLabel = new Label("Welcome, " + Block223Application.getCurrentUser().getUsername() +"!");
        welcomeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
        gridPane.add(welcomeLabel, 0,1,2,1);
        GridPane.setHalignment(welcomeLabel, HPos.CENTER);
        GridPane.setMargin(welcomeLabel, new Insets(0,0,0,0));
        
        // Add logo
        ImageView logo = new ImageView();   
        logo.setFitHeight(175);
        logo.setFitWidth(175);
        Image image = new Image(new FileInputStream("Images/logo.PNG"));
        logo.setImage(image);
        gridPane.add(logo, 1, 2);
        GridPane.setHalignment(logo, HPos.CENTER);
        GridPane.setMargin(logo, new Insets(20,75,20,0));
        
        // Add logout Button
        Button logoutButtom = new Button("LOG OUT");
        logoutButtom.setPrefHeight(40);
        logoutButtom.setDefaultButton(true);
        logoutButtom.setPrefWidth(130);
        logoutButtom.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        logoutButtom.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(logoutButtom, 0, 4, 2, 1);
        GridPane.setHalignment(logoutButtom, HPos.RIGHT);
        GridPane.setMargin(logoutButtom, new Insets(20, 0,20,0));
        
        logoutButtom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Block223Controller.logout();
            	LoginPage loginPage = new LoginPage(stage); 
				loginPage.display();
              
            }
        });
        
    }
	
}
