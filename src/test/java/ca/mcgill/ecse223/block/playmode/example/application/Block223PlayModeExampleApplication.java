package ca.mcgill.ecse223.block.playmode.example.application;

import ca.mcgill.ecse223.block.playmode.example.view.Block223PlayModeExampleView;

public class Block223PlayModeExampleApplication {

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Block223PlayModeExampleView();
			}
		});
	}
}