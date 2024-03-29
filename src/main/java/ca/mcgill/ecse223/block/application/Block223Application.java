package ca.mcgill.ecse223.block.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.*;
import ca.mcgill.ecse223.block.view.AvailableGamesAdmin;
import ca.mcgill.ecse223.block.view.AvailableGamesPlayer;
import ca.mcgill.ecse223.block.view.AvailableLevelsPage;
import ca.mcgill.ecse223.block.view.AvailableBlocksPage;
import ca.mcgill.ecse223.block.view.Components;
import ca.mcgill.ecse223.block.view.CreateBlockPage;
import ca.mcgill.ecse223.block.view.CreateGamePage;
import ca.mcgill.ecse223.block.view.EditLevelPage;
import ca.mcgill.ecse223.block.view.IPage;
import ca.mcgill.ecse223.block.view.LoginPage;
import ca.mcgill.ecse223.block.view.RegisterPage;
import ca.mcgill.ecse223.block.view.UpdateBlockPage;
import ca.mcgill.ecse223.block.view.UpdateGamePage;
import ca.mcgill.ecse223.block.view.WelcomePage;
import ca.mcgill.ecse223.block.view.PlayGamePage;
import ca.mcgill.ecse223.block.view.PauseGamePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.mcgill.ecse223.block.controller.*;

public class Block223Application extends Application {

	public static final int APPLICATION_WIDTH = 1000;
	public static final int APPLICATION_HEIGHT = 800;
	
	public enum Pages {
		Login, 
		Welcome, 
		AvaliableGamesAdmin, 
		AvaliableGamesPlayer,  
		CreateGame, 
		UpdateGame, 
		EditLevel, 
		AvailableLevels, 
		Register, 
		UpdateBlock, 
		AvailableBlocks, 
		CreateBlock,
		PlayGame,
		PauseGame
	}

	private static HashMap<Pages, IPage> pageMap;
	private static Stage stage;
	private static Block223 block223;
	private static User currentUser;
	private static UserRole currentUserRole;
	private static Game currentGame;
	private static PlayedGame playedGame;

	@Override
	public void start(Stage stage) {
		Block223Application.stage = stage;

		
//		try {
////			Block223Controller.register("test", "test", "test123");
//			Block223Controller.login("test", "test");
//
//			// //Block223Controller.createGame("Game 1");
//			// //Block223Controller.createGame("ANother one");
//			//Block223Controller.selectGame("wow");
////			currentGame.addBlock(1, 2, 3, 3);
//			Block223Controller.selectPlayableGame("Test game name 1", 0);
//		} catch (InvalidInputException e) {
//			Components.showAlert(AlertType.INFORMATION, stage.getOwner(), "", "throw in start:\n" + e.getMessage());
//		}
		
		
		// try {
		// 	Block223Controller.register("user", "player", "admin");
		// 	Block223Controller.login("user", "admin");
		// 	Block223Controller.createGame("Game 1");
		// 	Block223Controller.selectGame("Game 1");
		// 	Block223Controller.setGameDetails(4, 5, 4, 4, 4, 10, 4);
		// 	Block223Controller.publishGame();
		// 	Block223Controller.logout();
		// 	Block223Controller.login("user", "player");
		// } catch (InvalidInputException e1) {
		// 	// TODO Auto-generated catch block
		// 	e1.printStackTrace();
		// }
		

		IPage login = Block223Application.getPage(Pages.Login);
		login.display();

		

	}

	public static IPage getPage(Pages page) {
		if (!pageMap.containsKey(page)) {
			IPage newPage = null;
			switch (page) {
			case Login:
				newPage = new LoginPage(Block223Application.stage);
				break;
			case Welcome:
				newPage = new WelcomePage(Block223Application.stage);
				break;
			case AvaliableGamesAdmin:
				newPage = new AvailableGamesAdmin(Block223Application.stage);
				break;
			case AvaliableGamesPlayer:
				newPage = new AvailableGamesPlayer(Block223Application.stage);
				break;
			case CreateGame:
				newPage = new CreateGamePage(Block223Application.stage);
				break;
			case UpdateGame:
				newPage = new UpdateGamePage(Block223Application.stage);
				break;
			case UpdateBlock:
				newPage = new UpdateBlockPage(Block223Application.stage);
				break;
			case EditLevel:
				newPage = new EditLevelPage(Block223Application.stage);
				break;
			case AvailableLevels:
				newPage = new AvailableLevelsPage(Block223Application.stage);
				break;
			case Register:
				newPage = new RegisterPage(Block223Application.stage);
				break;
			case AvailableBlocks:
				newPage = new AvailableBlocksPage(Block223Application.stage);
				break;
			case CreateBlock:
				newPage = new CreateBlockPage(Block223Application.stage);
				break;
			case PlayGame:
				newPage = new PlayGamePage(Block223Application.stage);
				break;
			case PauseGame:
				newPage = new PauseGamePage(Block223Application.stage);
				break;
			}
			pageMap.put(page, newPage);
			return newPage;
		} else {
			return pageMap.get(page);
		}
	}
		

	public void initPages(Stage stage) {
		pageMap = new HashMap<Pages, IPage>();
		pageMap.put(Pages.Login, new LoginPage(stage));
		pageMap.put(Pages.Welcome, new WelcomePage(stage));
		pageMap.put(Pages.AvaliableGamesAdmin, new AvailableGamesAdmin(stage));
		pageMap.put(Pages.AvaliableGamesPlayer, new AvailableGamesPlayer(stage));
		pageMap.put(Pages.CreateGame, new CreateGamePage(stage));
		pageMap.put(Pages.UpdateGame, new UpdateGamePage(stage));
		pageMap.put(Pages.EditLevel, new EditLevelPage(stage));
		pageMap.put(Pages.Register, new RegisterPage(stage));
		pageMap.put(Pages.UpdateBlock, new UpdateBlockPage(stage));
		pageMap.put(Pages.AvailableBlocks, new AvailableBlocksPage(stage));
		pageMap.put(Pages.PlayGame, new PlayGamePage(stage));
		pageMap.put(Pages.PauseGame, new PauseGamePage(stage));
	}

	public static void main(String[] args) {
		pageMap = new HashMap<Pages, IPage>();

		// temp, remove once controller methods are implemented
		Block223 block223 = getBlock223();
		Block223Persistence.save(block223);

		launch();
	}

	public static Block223 getBlock223() {
		if (block223 == null) {
			block223 = Block223Persistence.load();
		}
		return block223;
	}

	public static Block223 resetBlock223() {
		block223.delete();
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
	
	public static PlayedGame getCurrentPlayableGame() {
		return playedGame;
	}

	public static void setCurrentPlayableGame(PlayedGame aGame) {
		playedGame = aGame;
	}

}