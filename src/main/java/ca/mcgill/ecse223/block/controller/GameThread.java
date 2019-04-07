package ca.mcgill.ecse223.block.controller;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;
import ca.mcgill.ecse223.block.view.Components;
import javafx.scene.control.Alert;

public class GameThread extends Thread {
	Block223PlayModeInterface ui;
	
	public GameThread(Block223PlayModeInterface ui) {
		this.ui = ui;
	}
	
	public void run() {
		
		
		// This weird logic below is required because the design of the game 
		// loop provided in the sequence diagram is bad. The game loop, 
		// which is within startGame() is finished once the game is paused. As a result,
		// we need to check if the game is paused after the call to startGame() and
		// wait for a space press to continue the game (by recalling start game).
		//
		// It would have been much better if the game loop design given to us
		// continued to loop even if the game is paused. In that case, we would
		// only need to handle the input in one place, and we wouldn't need to recall
		// startGame
        try {
        	PlayedGame game = Block223Application.getCurrentPlayableGame();
        	
        	while(game.getPlayStatus() != PlayStatus.GameOver) {
        		Block223Controller.startGame(ui);
        		
        		if(game.getPlayStatus() == PlayStatus.Paused) {
        			System.out.println("In GameThread Paused");
        			while(true) {
        				String userInputs = ui.takeInputs();
        				
        				
        				// I don't know why but if I remove this then
        				// the space pressed isn't detect...
        				// Normally I would look into this more but tbh
        				// it works so I'm just going to leave it
        				try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				if (userInputs.contains(" ")) {
        					game.play();
        					System.out.println("Play - GameThread");
        					break;
        				}
        			}
        		}
        	}
		} catch (InvalidInputException e) {
            Components.showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }   
	}
}
