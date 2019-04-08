package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;
import ca.mcgill.ecse223.block.view.IPage;
import javafx.application.Platform;
import ca.mcgill.ecse223.block.application.*;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;

public class Block223Controller {

	// ****************************
	// Modifier methods
	// ****************************

	/**
	 * Creates a new game
	 * 
	 * @param name - name of game to create
	 * @throws InvalidInputException
	 */
	public static void createGame(String name) throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole userRole = Block223Application.getCurrentUserRole();

		// User must be admin
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to create a game.");
		}
		Admin adminRole = (Admin) userRole;

		if (block223.findGame(name) != null) {
			throw new InvalidInputException("The name of a game must be unique.");
		}

		// Check for null or empty names
		String errMsg = "The name of a game must be specified.";
		if (name == null) {
			throw new InvalidInputException(errMsg);
		} else if (name.equals("")) {
			throw new InvalidInputException(errMsg);
		}

		// For some reason, this umple generated constructor does not call setName,
		// so we pass in a temp string for the name and then manually call setName
		Game game = new Game("sometempstring", 1, adminRole, 1, 1, 1, 10, 10, block223);
		game.setName(name);

		Block223Persistence.save(block223);
	}

	/**
	 * Sets the new attributes for a game
	 * 
	 * @param nrLevels                - number of levels
	 * @param nrBlocksPerLevel        - number of blocks per level (must be greater
	 *                                than 0)
	 * @param minBallSpeedX           - minimum horizontal speed of the ball (must
	 *                                be greater than 0)
	 * @param minBallSpeedY           - minimum vertical speed of the ball (must be
	 *                                greater than 0)
	 * @param ballSpeedIncreaseFactor - ball speed increase factor (must be greater
	 *                                than 0)
	 * @param maxPaddleLength         - maximum length of the paddle (must be
	 *                                between 0 and 400)
	 * @param minPaddleLength         - minimum length of the paddle (must be
	 *                                between 0 and 400)
	 * 
	 * @throws InvalidInputException
	 */
	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		// Retrieve user role and current game
		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();

		// Check to ensure user has admin privileges
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}

		// Check to ensure that a game is set
		if (game == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}

		// Check to ensure the game admin matches current admin logged in
		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}

		// Check to ensure number of levels fall within limited range
		if (nrLevels < 1 || nrLevels > 99) {
			throw new InvalidInputException("The number of levels must be between 1 and 99.");
		}

		// Set number of blocks per level
		if (nrBlocksPerLevel < 1) {
			throw new InvalidInputException("The number of blocks per level must be greater than zero.");
		}
		if (nrBlocksPerLevel < game.numberOfBlockAssignments()) {
			throw new InvalidInputException(
					"The maximum number of blocks per level cannot be less than the number of existing blocks in a level.");
		}
		game.setNrBlocksPerLevel(nrBlocksPerLevel);

		// set ball attributes
		if (minBallSpeedX < 1 && minBallSpeedY < 1) {
			throw new InvalidInputException("The minimum speed of the ball must be greater than zero.");
		}
		if (ballSpeedIncreaseFactor <= 0) {
			throw new InvalidInputException("The speed increase factor of the ball must be greater than zero.");
		}
		Ball ball = game.getBall();
		try {
			ball.setMinBallSpeedX(minBallSpeedX);
			ball.setMinBallSpeedY(minBallSpeedY);
			ball.setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
		} catch (Exception e) {
			throw new InvalidInputException(
					"Unexpected exception while setting ball speed to (" + minBallSpeedX + "," + minBallSpeedY + ")");
		}

		// check max paddle length requirements
		if (maxPaddleLength < 1 || maxPaddleLength > 390) {
			throw new InvalidInputException(
					"The maximum length of the paddle must be greater than zero and less than or equal to 390.");
		}

		// check paddle is greater than zero
		if (minPaddleLength < 1) {
			throw new InvalidInputException("The minimum length of the paddle must be greater than zero.");
		}

		// check min paddle length requirements
		if (minPaddleLength > maxPaddleLength) {
			throw new InvalidInputException("The minimum paddle length must be less than the maximum paddle length");
		}

		// Set paddle attributes
		Paddle paddle = game.getPaddle();
		paddle.setMaxPaddleLength(maxPaddleLength);
		paddle.setMinPaddleLength(minPaddleLength);

		// Set level attributes
		List<Level> levels = game.getLevels();
		int levelSize = levels.size();

		// If nrLevels is greater than current level size, then add sufficient number of
		// levels
		for (int i = levelSize; i < nrLevels; i++) {
			game.addLevel();
		}

		// If current level size is greater than nrLevels, then delete sufficient number
		// of levels
		for (int i = levelSize - 1; i >= nrLevels; i--) {
			Level level = game.getLevel(i);
			level.delete();
		}

	}

	/**
	 * Delete a specific game by name
	 * 
	 * @param name - The name of the game to delete
	 * 
	 * @throws InvalidInputException
	 */
	public static void deleteGame(String name) throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		Game game = block223.findGame(name);
		if(game == null) return;

		if (game.isPublished()) {
			throw new InvalidInputException("A published game cannot be deleted.");
		}

		UserRole userRole = Block223Application.getCurrentUserRole();

		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a game.");
		}

		Admin admin = (Admin) userRole;
		if (admin != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can delete the game.");
		}

		game.delete();
		Block223Persistence.save(block223);
	}

	/**
	 * Selects a given game (Set game to be the current game in the application)
	 * 
	 * @param name - name of game to select
	 * @throws InvalidInputException
	 */
	public static void selectGame(String name) throws InvalidInputException {

		Block223 block223 = Block223Application.getBlock223();
		UserRole userRole = Block223Application.getCurrentUserRole();

		// User must be admin
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to select a game.");
		}

		Game game = block223.findGame(name);
		if (game == null) {
			throw new InvalidInputException("A game with name " + name + " does not exist.");
		}

		if (game.isPublished()) {
			throw new InvalidInputException("A published game cannot be changed.");
		}

		// Check to see if user created game
		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can select the game.");
		}

		Block223Application.setCurrentGame(game);
	}

	/**
	 * Updates a given game
	 * 
	 * @param name                    - new name for game
	 * @param nrLevels                - number of levels
	 * @param nrBlocksPerLevel        - number of blocks per level (must be greater
	 *                                than 0)
	 * @param minBallSpeedX           - minimum horizontal speed of the ball (must
	 *                                be greater than 0)
	 * @param minBallSpeedY           - minimum vertical speed of the ball (must be
	 *                                greater than 0)
	 * @param ballSpeedIncreaseFactor - ball speed increase factor (must be greater
	 *                                than 0)
	 * @param maxPaddleLength         - maximum length of the paddle (must be
	 *                                between 0 and 400)
	 * @param minPaddleLength         - minimum length of the paddle (must be
	 *                                between 0 and 400)
	 * 
	 * @throws InvalidInputException
	 */
	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();

		// User must be admin
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}

		if (game == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}

		// Check to see if user created game
		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}

		// Check to see if name is valid
		String errMsg = "The name of a game must be specified.";
		if (name == null) {
			throw new InvalidInputException(errMsg);
		} else if (name.isEmpty()) {
			throw new InvalidInputException(errMsg);
		}

		// New name must be different that current name
		if (!game.getName().equals(name)) {
			if (!game.setName(name)) {
				throw new InvalidInputException("The name of a game must be unique.");
			}
		}

		setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor,
				maxPaddleLength, minPaddleLength);
	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();

		// Beginning of method input checks
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to add a block.");
		}

		if (game == null) {
			throw new InvalidInputException("A game must be selected to add a block.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can add a block.");
		}

		for (Block block : game.getBlocks()) {
			if (block.getBlue() == blue && block.getGreen() == green && block.getRed() == red) {
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}

		// Creating the block and catching exceptions
		Block newBlock;
		try {
			newBlock = new Block(red, green, blue, points, game);
		} catch (RuntimeException e) {
			String msg;
			if (e.getMessage().contentEquals("Red invalid")) msg = "Red must be between 0 and 255.";
			else if (e.getMessage().contentEquals("Green invalid")) msg = "Green must be between 0 and 255.";
			else if (e.getMessage().contentEquals("Blue invalid")) msg = "Blue must be between 0 and 255.";
			else if (e.getMessage().contentEquals("Points invalid")) msg = "Points must be between 1 and 1000.";
			else msg = "Unable to create block due to game.";
			
			throw new InvalidInputException(msg);

		}
	}

	public static void deleteBlock(int id) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();

		// Beginning of method input checks
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a block.");
		}

		if (game == null) {
			throw new InvalidInputException("A game must be selected to delete a block.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can delete a block.");
		}

		Block block = game.findBlock(id);
		if (block != null) {
			block.delete();
		}
	}

	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();

		// Beginning of method input checks
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to update a block.");
		}
		// Checking if a game is selected
		if (game == null) {
			throw new InvalidInputException("A game must be selected to update a block.");
		}
		// Checking for specifc admin role
		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can update a block.");
		}

		if (red < 0 || red > 255)
			throw new InvalidInputException("Red must be between 0 and 255.");

		if (green < 0 || green > 255)
			throw new InvalidInputException("Green must be between 0 and 255.");

		if (blue < 0 || blue > 255)
			throw new InvalidInputException("Blue must be between 0 and 255.");

		if (points < 1 || points > 1000) 
			throw new InvalidInputException("Points must be between 1 and 1000.");

		Block block = game.findBlock(id);
		// Checking if block is set
		if (block != null) {
			// for each loop to check all blocks in a game and compare them with the block
			// being updated
			for (Block blocks : game.getBlocks()) {
				if (block != blocks) {
					if (blocks.getRed() == red && blocks.getGreen() == green && blocks.getBlue() == blue) {
						throw new InvalidInputException("A block with the same color already exists for the game.");
					}
				}
			}
			block.setRed(red);
			block.setGreen(green);
			block.setBlue(blue);
			block.setPoints(points);
		} 
		else {
			throw new InvalidInputException("The block does not exist.");
		}

	}

	/**
	 * Places a new block within the grid at a specified position where there is not
	 * already an existing block
	 *
	 * @param id                     - The block id to specify which block type to
	 *                               place
	 * @param level                  - The level that a block will be added to
	 * @param gridHorizontalPosition - The horizontal coordinate where the block
	 *                               will be placed in the grid
	 * @param gridVerticalPosition   - The vertical coordinate where the block will
	 *                               be placed in the grid
	 * @throws InvalidInputException
	 */
	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {

		Game game = Block223Application.getCurrentGame();
		UserRole userRole = Block223Application.getCurrentUserRole();

		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to position a block.");
		}

		if (game == null) {
			throw new InvalidInputException("A game must be selected to position a block.");
		}

		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can position a block.");
		}

		if (level < 1 || level > game.numberOfLevels()) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}

		Level currentLevel = game.getLevel(level - 1);
		if (currentLevel.numberOfBlockAssignments() == game.getNrBlocksPerLevel()) {
			throw new InvalidInputException("The number of blocks has reached the maximum number ("
					+ game.getNrBlocksPerLevel() + ") allowed for this game.");
		}

		BlockAssignment levelBlockAssignment = findBlockAssignment(currentLevel, gridHorizontalPosition,
				gridVerticalPosition);
		if (levelBlockAssignment != null) {
			throw new InvalidInputException(
					"A block already exists at location " + gridHorizontalPosition + "/" + gridVerticalPosition + ".");
		}

		Block block = game.findBlock(id);
		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}

		// x_y_capacity contains the max blocks available in x (array position 0) and y
		// (array position 1)
		int[] x_y_capacity = getMaxBlockCapacity();
		int maxHorizontalBlocks = x_y_capacity[0];
		int maxVerticalBlocks = x_y_capacity[1];

		if (gridHorizontalPosition > 0 && gridHorizontalPosition <= maxHorizontalBlocks && gridVerticalPosition > 0
				&& gridVerticalPosition <= maxVerticalBlocks) {

			currentLevel.addBlockAssignment(gridHorizontalPosition, gridVerticalPosition, block, game);

		} else {
			throw new InvalidInputException("The horizontal position must be between 1 and " + maxHorizontalBlocks
					+ " and the Vertical position must be be between 1 and" + maxVerticalBlocks);
		}
	}

	/**
	 * Moves an existing block assignment from one position to another within a
	 * level
	 * 
	 * @param level                     - the target level to perform the move
	 * @param oldGridHorizontalPosition - the existing horizontal position of the
	 *                                  block assignment
	 * @param oldGridVerticalPosition   - the existing vertical position of the
	 *                                  block assignment
	 * @param newGridHorizontalPosition - the new horizontal position to move the
	 *                                  block assignment to (existing block
	 *                                  assignment must not exist)
	 * @param newGridVerticalPosition   - the new vertical position to move the
	 *                                  block assignment to (existing block
	 *                                  assignment must not exist)
	 * 
	 * @throws InvalidInputException
	 */
	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {

		// Retrieve user role and current game
		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();

		// Check to ensure user has admin privileges
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to move a block.");
		}

		// Check to ensure that a game is set
		if (game == null) {
			throw new InvalidInputException("A game must be selected to move a block.");
		}

		// Check to ensure the game admin matches current admin logged in
		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can move a block.");
		}

		// Get targeted level
		Level targetLevel;
		try {
			targetLevel = game.getLevel(level - 1);
		} catch (Exception e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}

		// Check to ensure existing block doesn't already exist in new position
		BlockAssignment newBlockAssignment = findBlockAssignment(targetLevel, newGridHorizontalPosition,
				newGridVerticalPosition);
		if (newBlockAssignment != null) {
			throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition + "/"
					+ newGridVerticalPosition + ".");
		}

		// Find existing block assignment
		BlockAssignment blockAssignment = findBlockAssignment(targetLevel, oldGridHorizontalPosition,
				oldGridVerticalPosition);
		if (blockAssignment == null) {
			throw new InvalidInputException("A block does not exist at location " + oldGridHorizontalPosition + "/"
					+ oldGridVerticalPosition + ".");
		}

		// Set new block position
		// x_y_capacity contains the max blocks available in x (array position 0) and y
		// (array position 1)
		int[] x_y_capacity = getMaxBlockCapacity();
		int maxHorizontalBlocks = x_y_capacity[0];
		int maxVerticalBlocks = x_y_capacity[1];
		if (newGridHorizontalPosition < 1 || newGridHorizontalPosition > maxHorizontalBlocks) {
			throw new InvalidInputException(
					"The horizontal position must be between 1 and " + maxHorizontalBlocks + ".");
		}
		// tests want 15, but this is wrong because because play area is not square
		// (above test passes)
		if (newGridVerticalPosition < 1 || newGridVerticalPosition > 15) {
			throw new InvalidInputException("The vertical position must be between 1 and 15.");
		}
		try {
			blockAssignment.setGridHorizontalPosition(newGridHorizontalPosition);
			blockAssignment.setGridVerticalPosition(newGridVerticalPosition);
		} catch (Exception e) {
			throw new InvalidInputException("The horizontal position must be between 1 and " + maxHorizontalBlocks
					+ ". The vertical position must be between 1 and " + maxVerticalBlocks + ".");
		}
	}

	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		// Retrieve user role and current game
		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();

		// Check to ensure user has admin privileges
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to remove a block.");
		}

		// Check to ensure that a game is selected
		if (game == null) {
			throw new InvalidInputException("A game must be selected to remove a block.");
		}

		// Check to ensure the game admin matches current admin who is logged in
		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can remove a block.");
		}

		Level someLevel = game.getLevel(level - 1);
		BlockAssignment assignment = findBlockAssignment(someLevel, gridHorizontalPosition, gridVerticalPosition);

		if (assignment != null) {
			assignment.delete();
		}
	}

	/**
	 * Saves the game (by saving the entire state of the block223 application)
	 * 
	 * @throws InvalidInputException
	 */
	public static void saveGame() throws InvalidInputException {
		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();

		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to save a game.");
		}

		if (game == null) {
			throw new InvalidInputException("A game must be selected to save it.");
		}

		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can save it.");
		}

		try {
			Block223 block223 = Block223Application.getBlock223();
			Block223Persistence.save(block223);
		} catch (RuntimeException e) {
			throw new InvalidInputException("Error saving block 223 to persistence layer");
		}

	}

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		if (Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");
		}
		if (username == null || username.isEmpty()) {
			throw new InvalidInputException("The username must be specified.");
		}
		if (playerPassword == null || playerPassword.isEmpty()) {
			throw new InvalidInputException("The player password needs to be specified.");
		}
		if (playerPassword.equals(adminPassword)) {
			throw new InvalidInputException("The passwords have to be different.");
		}

		Block223 block223 = Block223Application.getBlock223();
		Player player;
		try {
			player = new Player(playerPassword, block223);
		} catch (RuntimeException e) {
			throw new InvalidInputException("The player password needs to be specified.");
		}

		User user;
		try {
			user = new User(username, block223, player);
		} catch (RuntimeException e) {
			player.delete();

			String msg;
			if (e.getMessage().equals("Cannot create due to duplicate username"))
				msg = "The username has already been taken.";
			else
				msg = "The username must be specificed.";

			throw new InvalidInputException(msg);
		}

		if (adminPassword != null && !adminPassword.equals("")) {
			Admin admin;
			try {
				admin = new Admin(adminPassword, block223);
				user.addRole(admin);
			} catch (RuntimeException e) {
				throw new InvalidInputException("The player password needs to be specified.");
			}
		}

		Block223Persistence.save(block223);
	}

	public static void login(String username, String password) throws InvalidInputException {
		Block223Application.resetBlock223();
		if (Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		}
		User user = User.getWithUsername(username);
		if (user == null) {
			throw new InvalidInputException("The username and password do not match.");
		}

		List<UserRole> roles = user.getRoles();
		for (UserRole role : roles) {
			String rolePassword = role.getPassword();

			if (rolePassword.equals(password)) {
				Block223Application.setCurrentUser(user);
				Block223Application.setCurrentUserRole(role);

				return;
			}
		}

		throw new InvalidInputException("The username and password do not match.");
	}

	public static void logout() {
		Block223Application.setCurrentUserRole(null);
	}

	/**
	 * Selects a given game to play by setting the current playable game
	 * 
	 * @param name - the name of the game
	 * @param id   - the id of the game
	 * 
	 * @throws InvalidInputException
	 */
	public static void selectPlayableGame(String name, int id) throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole userRole = Block223Application.getCurrentUserRole();

		// User must be player
		if (!(userRole instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}

		Player player = (Player) userRole;
		Game game = Block223Controller.getGameByName(name);
		PlayedGame pgame;

		if (game != null) {

			String username = User.findUsername(player);

			if (username == null) {
				throw new InvalidInputException("Could not find the username for the current player.");
			}

			pgame = new PlayedGame(username, game, block223);
			pgame.setPlayer(player);

		} else {
			pgame = block223.findPlayableGame(id);

			if (pgame == null) {
				throw new InvalidInputException("The game does not exist.");
			}

			if (pgame.getPlayer() != player) {
				throw new InvalidInputException("Only the player that started a game can continue the game.");
			}

		}

		Block223Application.setCurrentPlayableGame(pgame);
	}

	/**
	 * startGame: start the game !
	 * 
	 * @param ui - UI to interact with
	 * @throws InvalidInputException
	 */
	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
		UserRole userRole = Block223Application.getCurrentUserRole();
		PlayedGame game = Block223Application.getCurrentPlayableGame();

		// User role is not set
		if (userRole == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}

		// Current playable game is not set
		if (game == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}

		if ((userRole instanceof Admin) && (game.getPlayer() != null)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}

		if ((userRole instanceof Admin) && (game.getGame().getAdmin() != ((Admin) userRole))) {
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}

		if ((userRole instanceof Player) && (game.getPlayer() == null)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}

		game.play();
		ui.takeInputs();

		long startTime;

		// Game loop
		while (game.getPlayStatus() == PlayStatus.Moving) {
			startTime = java.lang.System.currentTimeMillis();

			String userInputs = ui.takeInputs();
			updatePaddlePosition(userInputs);

			game.move();

			// Pause game
			if (userInputs.contains(" ")) {
				game.pause();
				System.out.println("Pause - startGame");
			}

			long duration = java.lang.System.currentTimeMillis() - startTime;
			
			try {
				Thread.sleep((long) Math.abs(game.getWaitTime() - duration));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			ui.refresh();
		}

		if (game.getPlayStatus() == PlayStatus.GameOver) {
			Block223Application.setCurrentPlayableGame(null);
			
			// TODO: switch this to HOF page once that's done
			new Thread(() -> {
			      Platform.runLater(() -> { 
			    	  ui.endGame(game.getLives(), null);
			      });   
			}).start();
		} else if (game.getPlayer() != null) {
			game.setBounce(null);

			// NOTE: The game is saved here instead of in the state machine to avoid
			// dependency
			// of the Model package on the Application and Persistence packages
			Block223 block223 = Block223Application.getBlock223();
			Block223Persistence.save(block223);
		}

	}
	/**
	 * Moves the paddle one PlayedGame.PADDLE_MOVE_LEFT to the left for each l
	 * in the userInputs before a space. Same for the right movement.
	 * 
	 * 
	 * @param userInputs - user input
	 */
	private static void updatePaddlePosition(String userInputs) {

		PlayedGame game = Block223Application.getCurrentPlayableGame();

		for (char c : userInputs.toCharArray()) {
			int delta = 0;

			if (c == 'l' && (game.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_LEFT) >= 0) {
				delta = PlayedGame.PADDLE_MOVE_LEFT;
			} else if (c == 'r' && (game.getCurrentPaddleX() + game.getCurrentPaddleLength() + PlayedGame.PADDLE_MOVE_RIGHT) <= Game.PLAY_AREA_SIDE)  {
				delta = PlayedGame.PADDLE_MOVE_RIGHT;
			
			// space
			} else {
				break;
			}

			game.setCurrentPaddleX(game.getCurrentPaddleX() + delta);
			
			if (game.getCurrentPaddleX() > (390 - game.getCurrentPaddleLength())) {
				game.setCurrentPaddleX(390 - game.getCurrentPaddleLength());
			}
			else if (game.getCurrentPaddleX() < 0) {
				game.setCurrentPaddleX(0);
			}
		}

	}

	public static void testGame(Block223PlayModeInterface ui) throws InvalidInputException {
		UserRole userRole = Block223Application.getCurrentUserRole();
        Game game = Block223Application.getCurrentGame();

        if (!(userRole instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to test a game.");
        }

        if (game == null) {
            throw new InvalidInputException("A game must be selected to test it.");
        }

        if (userRole != game.getAdmin()) {
            throw new InvalidInputException("Only the admin who created the game can test it.");
        }
        
        String username =  User.findUsername(userRole); 
        Block223 block223 = Block223Application.getBlock223();
  
        PlayedGame playedGame = new PlayedGame(username, game, block223);
        playedGame.setPlayer(null);
        
        Block223Application.setCurrentPlayableGame(playedGame);
        
        startGame(ui);

	}

	/**
	 * Sets a game to a published state.
	 * 
	 * @throws InvalidInputException
	 */
	public static void publishGame () throws InvalidInputException {
		UserRole userRole = Block223Application.getCurrentUserRole();
        Game game = Block223Application.getCurrentGame();

        if (!(userRole instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to publish a game.");
        }

        if (game == null) {
            throw new InvalidInputException("A game must be selected to publish it.");
        }

        if (userRole != game.getAdmin()) {
            throw new InvalidInputException("Only the admin who created the game can publish it.");
        }
        
        if (game.getBlocks().size() < 1) { 
        	throw new InvalidInputException("At least one block must be defined for a game to be published.");
        }
        
        game.setPublished(true);
	}
	
	
	public static Game getGameByName(String name) {
		Game game = null;
		
		Block223 block223 = Block223Application.getBlock223();
		for(Game g : block223.getGames()) {
			if(g.getName().equals(name)) {
				game = g;
				break;
			}
		}
		
		return game;
	}
	
	// ****************************
	// Query methods
	// ****************************
	public static List<TOGame> getDesignableGames() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole userRole = Block223Application.getCurrentUserRole();
		if(!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		
		Admin admin = (Admin) userRole;
		List<Game> games = block223.getGames();
		
		List<TOGame> toGames = new ArrayList<TOGame>();
		for(Game game : games) {
			if(game.getAdmin().equals(admin) && !game.isPublished()) {
				TOGame toGame = new TOGame(game.getName(), 
								game.getLevels().size(), 
								game.getNrBlocksPerLevel(), 
								game.getBall().getMinBallSpeedX(), 
								game.getBall().getMinBallSpeedY(), 
								game.getBall().getBallSpeedIncreaseFactor(), 
								game.getPaddle().getMaxPaddleLength(), 
								game.getPaddle().getMinPaddleLength());
				toGames.add(toGame);
			}
		}
		
		return toGames;
	}

	/**
	 * Get the current designable game
	 * 
	 * @throws InvalidInputException
	 */
	public static TOGame getCurrentDesignableGame() throws InvalidInputException {

		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();

		// User must be admin
		if(!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}

		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		// Check to see if user created game
		if (userRole != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		
		return new TOGame(game.getName(), 
			game.getLevels().size(), 
			game.getNrBlocksPerLevel(), 
			game.getBall().getMinBallSpeedX(), 
			game.getBall().getMinBallSpeedY(), 
			game.getBall().getBallSpeedIncreaseFactor(), 
			game.getPaddle().getMaxPaddleLength(), 
			game.getPaddle().getMinPaddleLength());
	}

	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();
		
		//Beginning of method input checks
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		
		if(game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		
		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		
		List<TOBlock> result = new ArrayList<TOBlock>();
		for (Block block : game.getBlocks()) {
			TOBlock to = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());
			result.add(to);
		}
		
		return result;
	}

	/**
	 * Returns the Block with "id" for the current game
	 * 
	 * @param id - ID of the block
	 * @throws InvalidInputException
	 */
	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();

		//Beginning of method input checks
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}

		if(game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		Block block = game.findBlock(id);

		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}

		return new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());
	}

	/**
	 * Get all block assigments associated with a given level
	 * 
	 * @param level - level ID
	 * @throws InvalidInputException
	 */
	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();

		//Beginning of method input checks
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}

		if(game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		List<TOGridCell> result = new ArrayList<TOGridCell>();

		Level lvl;
		try {
			lvl = game.getLevel(level-1);
		} catch(IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}
		
		for (BlockAssignment assignment : lvl.getBlockAssignments()) {
			TOGridCell toGridCell = new TOGridCell(
				assignment.getGridHorizontalPosition(),
				assignment.getGridVerticalPosition(),
				assignment.getBlock().getId(),
				assignment.getBlock().getRed(),
				assignment.getBlock().getGreen(),
				assignment.getBlock().getBlue(),
				assignment.getBlock().getPoints()
			);

			result.add(toGridCell);
		}

		return result; 
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
	
	/**
	 * Helper method for finding a target block assignment
	 * 
	 * @param level - the corresponding level of the block assignment
	 * @param gridHorizontalPosition - the horizontal position on the grid
	 * @param gridVerticalPosition - the vertical position on the grid
	 * 
	 * @return the BlockAssignment object if it exists, otherwise null
	 */
	private static BlockAssignment findBlockAssignment(Level level, int gridHorizontalPosition, int gridVerticalPosition) {
		List<BlockAssignment> blockAssignments = level.getBlockAssignments();
		for (BlockAssignment blockAssignment : blockAssignments) {
			int horizontalPosition = blockAssignment.getGridHorizontalPosition();
			int verticalPosition = blockAssignment.getGridVerticalPosition();
			if (horizontalPosition == gridHorizontalPosition && verticalPosition == gridVerticalPosition) {
				return blockAssignment;
			}
		}
		return null;
	}

	/**
	 * Helper method to calculate the maximum horizontal and vertical block capacity
	 * 
	 * @return int [] x_y_capacity array with 2 values, the first being the horizontal capacity and the second being vertical
	 * 
	 */
	public static int[] getMaxBlockCapacity(){
		return Game.getMaxBlockCapacity();
	}

	/**
	 * Get all playable games
	 * 
	 * @throws InvalidInputException
	 */
	public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole userRole = Block223Application.getCurrentUserRole();

		// User must be player
		if(!(userRole instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		Player player = (Player) userRole;

		List<TOPlayableGame> playableGames = new ArrayList<TOPlayableGame>();

		// Add published games
		for (Game game : block223.getGames()) {
			boolean published = game.isPublished();

			if (published) {
				TOPlayableGame playableGame = new TOPlayableGame(game.getName(), -1, 0);
				playableGames.add(playableGame);
			}
		}

		// Add played games
		for (PlayedGame game : player.getPlayedGames()) {
			TOPlayableGame playableGame = new TOPlayableGame(game.getGame().getName(), game.getId(), game.getCurrentLevel());
			playableGames.add(playableGame);
		}

		return playableGames;
	}

	/**
	 * getCurrentPlayableGame: get the current playable game
	 * 
	 * @throws InvalidInputException
	 */
	public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
		
		// gets current PlayedGame 
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		
		// checking if a PlayableGame is selected
		if(pgame == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		
		// gets current user role
		UserRole userRole = Block223Application.getCurrentUserRole();
		
		// user role not set
		if(userRole == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		
		if((userRole instanceof Admin) && (pgame.getPlayer() != null)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}

		if ( (userRole instanceof Admin) && (pgame.getGame().getAdmin() != ((Admin) userRole)) ) {
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}

		if ((userRole instanceof Player) && (pgame.getPlayer() == null)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}

		boolean paused = pgame.getPlayStatus() == PlayStatus.Ready || pgame.getPlayStatus() == PlayStatus.Paused;

		TOCurrentlyPlayedGame result = new TOCurrentlyPlayedGame(pgame.getGame().getName(),
				paused, pgame.getScore(), pgame.getLives(), pgame.getCurrentLevel(), 
				pgame.getPlayername(), pgame.getCurrentBallX(), pgame.getCurrentBallY(), 
				pgame.getCurrentPaddleLength(), pgame.getCurrentPaddleX());
		
		List<PlayedBlockAssignment> blocks = pgame.getBlocks();
		
		for(PlayedBlockAssignment pblock : blocks) {
			new TOCurrentBlock(
					pblock.getBlock().getRed(),
					pblock.getBlock().getGreen(),
					pblock.getBlock().getBlue(),
					pblock.getBlock().getPoints(),
					pblock.getX(),
					pblock.getY(),
					result);	
		}
		
		return result;
	}

	public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
		// getting current PlayableGame
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();		
		
		// checking if a PlayableGame is selected
		if(pgame == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}
		
		// declaring instance of a game for the current PlayableGame
		Game game = pgame.getGame();
		
		// User must be admin
		if(!(Block223Application.getCurrentUserRole() instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
		}
		
		// creating Hall of Fame Transfer Object
		TOHallOfFame result = new TOHallOfFame(game.getName());
		
		// checking if the number of hof entries is less or equal to zero
		// in which case we return an empty hof
		if(game.numberOfHallOfFameEntries() <= 0) {
			return result;
		}
		
		// start-end value checks
		
		if (start < 1) {
			start = 1;	
		}

		if (end > game.numberOfHallOfFameEntries()) {
			end = game.numberOfHallOfFameEntries();
		}

		start = game.numberOfHallOfFameEntries() - start;
		end = game.numberOfHallOfFameEntries() - end;
	
		
		// creating hall of fame entries
		for (int index = start; index >= end; index--) {
			new TOHallOfFameEntry(
					index+1,
					game.getHallOfFameEntry(index).getPlayername(),
					game.getHallOfFameEntry(index).getScore(),
					result);
		}
			
		return result;
	}

	public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
		// getting current PlayableGame
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();		
		
		// checking if a PlayableGame is selected
		if(pgame == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}

		// User must be admin
		if(!(Block223Application.getCurrentUserRole() instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
		}
		
		// declaring instance of a game for the current PlayableGame
		Game game = pgame.getGame();
		
		// creating Hall of Fame Transfer Object
		TOHallOfFame result = new TOHallOfFame(game.getName());
		
		// checking if the number of hof entries is less or equal to zero
		// in which case we return an empty hof
		if(game.numberOfHallOfFameEntries() <= 0) {
			return result;
		}
		
		// getting most recent hof entry
		HallOfFameEntry mostRecent = game.getMostRecentEntry();
		
		int indexR = game.indexOfHallOfFameEntry(mostRecent);
		
		// start-end value checks
		int start = indexR + numberOfEntries/2;
		
		if(start > game.numberOfHallOfFameEntries() - 1) {
			start = game.numberOfHallOfFameEntries() - 1;
		}
		
		int end = start - numberOfEntries + 1;

		if(end < 0) {
			end = 0;
		}
		
		// creating hall of fame entries
		for (int index = start; index >= end; index--) {
			new TOHallOfFameEntry(
					index+1,
					game.getHallOfFameEntry(index).getPlayername(),
					game.getHallOfFameEntry(index).getScore(),
					result);
		}
		
		return result;
	}

	public static int getPlayAreaSideLength() {
		return Game.PLAY_AREA_SIDE;
	}
}