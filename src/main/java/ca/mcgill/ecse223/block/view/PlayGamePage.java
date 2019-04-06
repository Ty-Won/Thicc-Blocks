package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PlayGamePage implements IPage, Block223PlayModeInterface {
	Stage stage;
    

    private static final char PAUSE_CHAR = ' ';
    private static final char LEFT_CHAR = 'l';
    private static final char RIGHT_CHAR = 'r';

    // Stores the user's inputs
    private StringBuilder inputQueue = new StringBuilder();
    
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

        //Create a border pane which will hold the gridPane in the center of the screen and the HBox at the top
        BorderPane root = new BorderPane();
	    root.setPadding(new Insets(20)); // space between elements and window border
	    root.setCenter(gridPane);
	    root.setTop(hbButtons);
	    
        // Create the scene with borderPane as the root node (since it contains everything else)
        Scene scene = new Scene(root, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT);

        // Handle key events
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> 
        {
            // Pause
            if(key.getCode() == KeyCode.SPACE) {
                inputQueue.append(PAUSE_CHAR);
            }
            
            // Left paddle
            else if (key.getCode() == KeyCode.LEFT) {
                inputQueue.append(LEFT_CHAR);
            }

            // Right paddle
            else if (key.getCode() == KeyCode.RIGHT) {
                inputQueue.append(RIGHT_CHAR);
            }            
        });

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

	@Override
	public String takeInputs() {

        
		return null;
	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		
    }
    
    

}
