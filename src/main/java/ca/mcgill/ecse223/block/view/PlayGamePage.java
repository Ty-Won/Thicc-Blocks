package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PlayGamePage implements IPage, Block223PlayModeInterface {
    
    
    private Stage stage;

    private Canvas canvas;
    private GraphicsContext gc;

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
		
        //Create an HBox to hold the top bar
        HBox topPane = new HBox();

        initializeCanvas();

        //Block223Controller.getCurrentPlayableGame();

        //Create a border pane which will hold the gridPane in the center of the screen and the HBox at the top
        BorderPane root = new BorderPane();
	    root.setPadding(new Insets(20)); // space between elements and window border
        root.setTop(topPane);
        root.setCenter(canvas);
	    
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

        startGame();
    }

	@Override
	public String takeInputs() {

        
		return null;
	}

	@Override
	public void refresh() {
    
        /*
        TO refresh:
            1. Score
            2. Blocks to render on the screen
            3. Paddle location
            4. Paddle size
            5. Ball locatiom
        */


        

	}

	@Override
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		
    }


    /**
     * Start the game (Blocking call!)
     */
    private void startGame() {
        try {
			Block223Controller.startGame(this);
		} catch (InvalidInputException e) {
            Components.showAlert(Alert.AlertType.ERROR, canvas.getScene().getWindow(), "Error", e.getMessage());
        }   
    }

    /** 
     * Create a new Canvas object. Assigns canvas and gc (Graphics Context) objects.
    */
    private void initializeCanvas() {
        this.canvas = new Canvas(Block223Controller.getPlayAreaSideLength(), Block223Controller.getPlayAreaSideLength());
        this.gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillRect(0, 0, Block223Controller.getPlayAreaSideLength(), Block223Controller.getPlayAreaSideLength());
        gc.fillRect(0, 0, 20, 20);

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


}
