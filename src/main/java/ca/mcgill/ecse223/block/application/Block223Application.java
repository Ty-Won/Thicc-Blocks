package ca.mcgill.ecse223.block.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.*;
import ca.mcgill.ecse223.block.view.CreateGamePage;
import ca.mcgill.ecse223.block.view.LoginPage;
import ca.mcgill.ecse223.block.view.UpdateGamePage;
import ca.mcgill.ecse223.block.controller.*;

public class Block223Application extends Application {
	
	public static final int APPLICATION_WIDTH = 1000;
	public static final int APPLICATION_HEIGHT = 600;

	private static Block223 block223;
	private static User currentUser;
	private static UserRole currentUserRole;
	private static Game currentGame;
	
    @Override
    public void start(Stage stage) {
    	
    	LoginPage loginPage = new LoginPage(stage); 
    	loginPage.display();
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
    
    public static Block223 resetBlock223() {
    	block223 = Block223Persistence.load();
    	return block223;
    }
    
    public static User getCurrentUser() {
    	return currentUser;
    }
    
    public static void setCurrentUser(User user) {
    	currentUser = user;
    }
    
    public static UserRole getCurrentUserRole() {
    	return currentUserRole;
    }
    
    public static void setCurrentUserRole(UserRole role) {
    	currentUserRole = role;
    }
    
    public static Game getCurrentGame() {
    	return currentGame;
    }
    
    public static void setCurrentGame(Game game) {
    	currentGame = game;
    }
 
   
}