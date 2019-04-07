package ca.mcgill.ecse223.block.view;

import java.util.Random;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.GameThread;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
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
                System.out.println("Space");
                IPage pauseGame = Block223Application.getPage(Pages.PauseGame);
            	pauseGame.display();
            }
            
            // Left paddle
            else if (key.getCode() == KeyCode.LEFT) {
                inputQueue.append(LEFT_CHAR);
                System.out.println("left");
            }

            // Right paddle
            else if (key.getCode() == KeyCode.RIGHT) {
                inputQueue.append(RIGHT_CHAR);
                System.out.println("right");
            }            
        });

         // Ensure we can get the current playable game before starting
         try {
            Block223Controller.getCurrentPlayableGame();
		} catch (InvalidInputException e) {
			Components.showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
			return;
		}

		GameThread gameThread = new GameThread(this);
		gameThread.setDaemon(true);
		gameThread.start();

		// Set the scene and display it
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public String takeInputs() {

		String input = inputQueue.toString();

		// Clear the queue
		inputQueue.setLength(0);

		return input;
	}

	@Override
	public void refresh() {

		/*
		 * TO refresh: 1. Score 2. Blocks to render on the screen 3. Paddle location 4.
		 * Paddle size 5. Ball locatiom
		 */

		clearCanvas();

		TOCurrentlyPlayedGame game;

		try {
			game = Block223Controller.getCurrentPlayableGame();
		} catch (InvalidInputException e) {
			e.printStackTrace();
			return;
		}

		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);

		Random rand = new Random();
		for (TOCurrentBlock block : game.getBlocks()) {
			gc.fillRect(block.getX(), block.getY(), 20, 20);
		}

		gc.fillRect(game.getCurrentPaddleX(), Block223Controller.getPlayAreaSideLength() - 30, 20, 5);

		gc.fillOval(game.getCurrentBallX(), game.getCurrentBallY(), 5.0, 5.0);

		//System.out.println("refresh!");
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
			Components.showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
		}
	}

	/**
	 * Create a new Canvas object. Assigns canvas and gc (Graphics Context) objects.
	 */
	private void initializeCanvas() {
		this.canvas = new Canvas(Block223Controller.getPlayAreaSideLength(),
				Block223Controller.getPlayAreaSideLength());
		this.gc = canvas.getGraphicsContext2D();

		TOCurrentlyPlayedGame game;
		try {
			game = Block223Controller.getCurrentPlayableGame();
		} catch (InvalidInputException e) {
			Components.showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
			return;
		}

		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);

		for (TOCurrentBlock block : game.getBlocks()) {
			gc.fillRect(block.getX(), block.getY(), 20, 20);
		}

	}

	/**
	 * Clear entire canvas with a transparent value
	 */
	private void clearCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}
