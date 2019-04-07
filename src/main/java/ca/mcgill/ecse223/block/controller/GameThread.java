package ca.mcgill.ecse223.block.controller;

import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;
import ca.mcgill.ecse223.block.view.Components;
import javafx.scene.control.Alert;

public class GameThread extends Thread {
	Block223PlayModeInterface ui;
	
	public GameThread(Block223PlayModeInterface ui) {
		this.ui = ui;
	}
	
	public void run() {
        try {
			Block223Controller.startGame(ui);
		} catch (InvalidInputException e) {
            Components.showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }   
	}
}
