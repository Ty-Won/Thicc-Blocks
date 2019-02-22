package ca.mcgill.ecse223.block.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.*;
import ca.mcgill.ecse223.block.controller.*;

public class Block223Application extends Application {

	private static Block223 block223;
	private static UserRole currentUserRole;
	private static Game currentGame;
	
    @Override
    public void start(Stage stage) {
        Label l = new Label("Thicc Blocks");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { 
    	// temp, remove once controller methods are implemented
    	Block223 block223 = getBlock223();
    	Block223Persistence.save(block223);
    	
        launch();
    }

    public static Block223 getBlock223() {
    	if(block223 == null) {
    		block223 = Block223Persistence.load();
    	}
    	
    	return block223;
    }
    
    public static UserRole getCurrentUserRole() {
    	return currentUserRole;
    }
    
    public static void setCurrentUserRole(UserRole role) {
    	currentUserRole = role;
    }
    
    public static Block223 resetBlock223() {
    	block223 = Block223Persistence.load();
    	return block223;
    }
    
    public static Game getCurrentGame() {
    	return currentGame;
    }
    
    public static void setCurrentGame(Game game) {
    	currentGame = game;
    }
    
}