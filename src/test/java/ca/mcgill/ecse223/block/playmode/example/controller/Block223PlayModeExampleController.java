package ca.mcgill.ecse223.block.playmode.example.controller;

import ca.mcgill.ecse223.block.playmode.example.view.Block223PlayModeInterface;



public class Block223PlayModeExampleController {

	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
		System.out.println(" *********** STARTING THE GAME *********** ");

		ui.takeInputs();
		while (true) {
			String input = ui.takeInputs();
			
			if(input.contains(" ")) {
				String inputBeforeSpace = input.substring(0, (input.indexOf(" ")));
				System.out.println(" Input received by CONTROLLER: " + inputBeforeSpace + " then paused");
				break;
			}
				
			System.out.println(" Input received by CONTROLLER: " + input);
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ui.refresh();
		}
			

	}
}