package ca.mcgill.ecse223.block.view;
import ca.mcgill.ecse223.block.view.IPage;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.GameThread;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PauseGamePage implements IPage{
	Stage stage;
	public PauseGamePage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void display() {
		 
		GridPane gridPane = createGridPane();
        // Create the scene with gridPane as the root node
        Scene scene = new Scene(gridPane, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT,
                Color.WHITE);
        
        try {
            addUIComponents(gridPane);
        } catch (FileNotFoundException e) {
            Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error loading page:",
                    e.getMessage());
        }

        
		//Setting title to the Stage 
		stage.setTitle("Pause Game");  
        
		//Adding scene to the stage 
		stage.setScene(scene);  
        
		//Displaying the contents of the stage 
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
        

        Label headerLabel = new Label("Pause Game");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 1, 0, 1, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));


		//A button with the specified text caption and icon.
    	FileInputStream playImageInput = new FileInputStream("Images/play.png");
    	Image playImage = new Image(playImageInput); 
        ImageView playImageView = new ImageView(playImage); 
        playImageView.setFitHeight(120);
        playImageView.setFitWidth(120);
        Button playButton = new Button("", playImageView);
        

        playButton.setDefaultButton(true);
        playButton.maxHeight(120);
        playButton.maxWidth(120);
        playButton.setStyle("-fx-background-color: #FFF;-fx-text-fill: #fff;");
        playButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        gridPane.add(playButton, 1, 3, 1, 1);
        GridPane.setHalignment(playButton, HPos.CENTER);
        GridPane.setMargin(playButton, new Insets(20, 0,20,0));



        //EDIT
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("hello");
            }
        });

    }


}
