package ca.mcgill.ecse223.block.controller;

import java.util.List;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.application.*;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;

public class Block223Controller {

	// ****************************
	// Modifier methods
	// ****************************
	public static void createGame(String name) throws InvalidInputException {
	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
	}

	public static void deleteGame(String name) throws InvalidInputException {
	}

	public static void selectGame(String name) throws InvalidInputException {
	}

	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
	}

	public static void deleteBlock(int id) throws InvalidInputException {
	}

	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
	}

	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
	}

	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
	}

	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
	}

	public static void saveGame() throws InvalidInputException {
	}

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		if(Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot register a new user while a user is logged in");
		}
		if(playerPassword.equals(adminPassword)) {
			throw new InvalidInputException("Player and Admin password have to be different.");
		}
		
		Block223 block223 = Block223Application.getBlock223();
		Player player;
		try {
			player = new Player(playerPassword, block223);			
		}
		catch (RuntimeException e) {
			throw new InvalidInputException("The player password needs to be specified.");
		}
		
		User user;
		try {
			user = new User(username, block223, player);			
		}
		catch (RuntimeException e) {
			player.delete();
			
			String msg;
			if(e.getMessage().equals("Cannot create due to duplicate username")) msg = "The username has already been taken";
			else msg = "The username must be specificed";
			
			throw new InvalidInputException(msg);
		}
		
		if(adminPassword != null && !adminPassword.equals("")) {
			Admin admin;
			try {
				admin = new Admin(adminPassword, block223);	
				user.addRole(admin);
			}
			catch (RuntimeException e) {
				throw new InvalidInputException("The player password needs to be specified.");
			}
		}
		
		Block223Persistence.save(block223);
	}

	public static void login(String username, String password) throws InvalidInputException {
		if(Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		}
		User user = User.getWithUsername(username);
		if(user == null) {
			throw new InvalidInputException("The username and password do not match.");
		}
		
		List<UserRole> roles = user.getRoles();
		for(UserRole role : roles) {
			String rolePassword = role.getPassword();
			
			if(rolePassword == password) {
				Block223Application.setCurrentUserRole(role);
				Block223Application.resetBlock223();
				
				break;
			}
		}
		
		throw new InvalidInputException("The username and password do not match.");
	}

	public static void logout() {
		Block223Application.setCurrentUserRole(null);
	}

	// ****************************
	// Query methods
	// ****************************
	public static List<TOGame> getDesignableGames() {
		return null;
	}

	public static TOGame getCurrentDesignableGame() {
		return null;
	}

	public static List<TOBlock> getBlocksOfCurrentDesignableGame() {
		return null;
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
		return null;
	}

	public List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		return null;
	}

	public static TOUserMode getUserMode() {
		TOUserMode mode;
		
		UserRole role = Block223Application.getCurrentUserRole();
		if(role == null) mode = new TOUserMode(Mode.None);
		else if(role instanceof Player) mode = new TOUserMode(Mode.Play);
		else if(role instanceof Admin) mode = new TOUserMode(Mode.Design);
		else mode = null;
		
		return mode;
	}

}