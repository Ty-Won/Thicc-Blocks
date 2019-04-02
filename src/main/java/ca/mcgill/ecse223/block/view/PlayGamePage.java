package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PlayGamePage implements IPage {
	Stage stage;
	
	public PlayGamePage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
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

	private void addUIComponents(GridPane gridPane) {
		// TODO Auto-generated method stub
		
	}

	private GridPane createGridPane() {
		// TODO Auto-generated method stub
		return null;
	}

}
