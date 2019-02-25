package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
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

	/**
	 * Sets the new attributes for a game
	 * 
	 * @param nrLevels - number of levels 
	 * @param nrBlocksPerLevel - number of blocks per level (must be greater than 0)
	 * @param minBallSpeedX - minimum horizontal speed of the ball (must be greater than 0)
	 * @param minBallSpeedY - minimum vertical speed of the ball (must be greater than 0)
	 * @param ballSpeedIncreaseFactor - ball speed increase factor (must be greater than 0)
	 * @param maxPaddleLength - maximum length of the paddle (must be between 0 and 400)
	 * @param minPaddleLength - minimum length of the paddle (must be between 0 and 400)
	 * 
	 * @throws InvalidInputException
	 */
	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		
		// Retrieve user role and current game
		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();
		
		// Check to ensure user has admin privileges
		if(!(userRole instanceof Admin)) {
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
		if (nrLevels > 0 && nrLevels < 100) {
			throw new InvalidInputException("The number of levels must be between 1 and 99");
		}
		
		// Check to ensure the minimum paddle length is less than or equal to the maximum
		if (minPaddleLength > maxPaddleLength) {
			throw new InvalidInputException("The minimum paddle length must be less than the maximum paddle length");
		}
		
		// Set number of blocks per level
		try {
			game.setNrBlocksPerLevel(nrBlocksPerLevel);
		} catch (Exception e) {
			throw new InvalidInputException("The number of blocks per level must be greater than zero");
		}
		
		// Set ball attributes
		Ball ball = game.getBall();
		try {
			ball.setMinBallSpeedX(minBallSpeedX);
			ball.setMinBallSpeedY(minBallSpeedY);
			ball.setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
		} catch (Exception e) {
			throw new InvalidInputException("The minimum speed of the ball and the ball speed increase factor must be greater than zero.");
		}
		
		// Set paddle attributes
		Paddle paddle = game.getPaddle();
		try {
			paddle.setMaxPaddleLength(maxPaddleLength);
			paddle.setMinPaddleLength(minPaddleLength);
		} catch (Exception e) {
			throw new InvalidInputException("The maximum length of the paddle must be greater than 0 and less than 400.");
		}
		
		// Set level attributes
		List<Level> levels = game.getLevels();
		int levelSize = levels.size();
		
		// If nrLevels is greater than current level size, then add sufficient number of levels
		for (int i=levelSize-1; i<nrLevels; i++) {
			levels.add(new Level(game));
		}
		
		// If current level size is greater than nrLevels, then delete sufficient number of levels
		for (int i=levelSize-1; i>=nrLevels; i--) {
			levels.remove(i);
		}
		
	}

	public static void deleteGame(String name) throws InvalidInputException {
	}

	public static void selectGame(String name) throws InvalidInputException {
	}

	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();
		
		//Beginning of method input checks
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to add a block.");
		}
		
		if(game == null) {
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
		
		//Creating the block and catching exceptions
		Block newBlock;
		try {
			newBlock = new Block(red, green, blue, points, game);
		}
		catch(RuntimeException e) {
			String msg;
			if (e.getMessage().contentEquals("Red invalid")) msg = "Red must be between 0 and 255";
			else if (e.getMessage().contentEquals("Green invalid")) msg = "Green must be between 0 and 255";
			else if (e.getMessage().contentEquals("Blue invalid")) msg = "Blue must be between 0 and 255";
			else if (e.getMessage().contentEquals("Points invalid")) msg = "Points must be between 1 and 1000";
			else msg = "Unable to create block due to game.";
			
			throw new InvalidInputException(msg);
			
		}
	}

	public static void deleteBlock(int id) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();
		
		//Beginning of method input checks
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a block.");
		}
		
		if(game == null) {
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
	}


    /**
     * Places a new block within the grid at a specified position where there is not already an existing block
     *
     * @param id - The block id to specify which block type to place
     * @param level - The level that a block will be added to
     * @param gridHorizontalPosition - The horizontal coordinate where the block will be placed in the grid
     * @param gridVerticalPosition - The vertical coordinate where the block will be placed in the grid
     * @throws InvalidInputException
     */
	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {

    	Game game = Block223Application.getCurrentGame();
        UserRole userRole = Block223Application.getCurrentUserRole();

        if (!(userRole instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to access game information.");
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


        Level currentLevel = game.getLevel(level);
        if (currentLevel.numberOfBlockAssignments() == game.getNrBlocksPerLevel()) {
            throw new InvalidInputException("The number of blocks has reached the maximum number (" +
                    +game.getNrBlocksPerLevel() + ") allowed for this game.");
        }

		BlockAssignment levelBlockAssignment = findBlockAssignment(currentLevel,gridHorizontalPosition,gridVerticalPosition);
		if(levelBlockAssignment != null){
			throw new InvalidInputException("A block already exists at location" + gridHorizontalPosition + "/"+ gridVerticalPosition + ".");
		}



        Block block = game.findBlock(id);
        if (block == null) {
            throw new InvalidInputException("The Block does not exist.");
        }

		// x_y_capacity contains the max blocks available in x (array position 0) and y (array position 1)
		int [] x_y_capacity = getMaxBlockCapacity();
        int maxHorizontalBlocks = x_y_capacity[0];
        int maxVerticalBlocks = x_y_capacity[1];

        if(gridHorizontalPosition>0 && gridHorizontalPosition<maxHorizontalBlocks
            && gridVerticalPosition>0 && gridVerticalPosition<maxVerticalBlocks){

            BlockAssignment newBlockAssignment = currentLevel.addBlockAssignment(gridHorizontalPosition,
                                                                                gridVerticalPosition, block, game);

            currentLevel.addBlockAssignment(newBlockAssignment);

        }else{
            throw new InvalidInputException("The horizontal position must be between 1 and " + maxHorizontalBlocks +
                    " and the Vertical position must be be between 1 and" + maxVerticalBlocks);
        }
	}

	/**
	 * Moves an existing block assignment from one position to another within a level
	 * 
	 * @param level - the target level to perform the move
	 * @param oldGridHorizontalPosition - the existing horizontal position of the block assignment
	 * @param oldGridVerticalPosition - the existing vertical position of the block assignment
	 * @param newGridHorizontalPosition - the new horizontal position to move the block assignment to (existing block assignment must not exist)
	 * @param newGridVerticalPosition - the new vertical position to move the block assignment to (existing block assignment must not exist)
	 * 
	 * @throws InvalidInputException
	 */
	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
		
		// Retrieve user role and current game
		UserRole userRole = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();
		
		// Check to ensure user has admin privileges
		if(!(userRole instanceof Admin)) {
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
			targetLevel = game.getLevel(level);
		} catch (Exception e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game");
		}
		
		// Check to ensure existing block doesn't already exist in new position
		BlockAssignment newBlockAssignment = findBlockAssignment(targetLevel, newGridHorizontalPosition, newGridVerticalPosition);
		if (newBlockAssignment != null) {
			throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition + ", " 
					+ newGridVerticalPosition + "." );
		}
		
		// Find existing block assignment
		BlockAssignment blockAssignment = findBlockAssignment(targetLevel, oldGridHorizontalPosition, oldGridVerticalPosition);
		if (blockAssignment == null) {
			throw new InvalidInputException("A block does not exist at location " + oldGridHorizontalPosition + ", " 
					+ oldGridVerticalPosition + "." );
		}
		
		// Set new block position
		// x_y_capacity contains the max blocks available in x (array position 0) and y (array position 1)
		int [] x_y_capacity = getMaxBlockCapacity();
		int maxHorizontalBlocks = x_y_capacity[0];
		int maxVerticalBlocks = x_y_capacity[1];
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
	}

	/**
     * Saves the game (by saving the entire state of the block223 application)
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
            throw new InvalidInputException("Only the admin who created the game can position a block.");
        }

        try{
            Block223 block223= Block223Application.getBlock223();
            Block223Persistence.save(block223);
        }catch(RuntimeException e){
            throw new InvalidInputException("Error saving block 223 to persistence layer");
        }


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
			
			if(rolePassword.equals(password)) {
				Block223Application.setCurrentUser(user);
				Block223Application.setCurrentUserRole(role);
				Block223Application.resetBlock223();
				
				return;
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
	 * @param game - the corresponding game to be measured
	 * @param block - the corresponding block to be used as a measurement
	 * 
	 * @return int [] x_y_capacity array with 2 values, the first being the horizontal capacity and the second being vertical
	 * 
	 */
	private static int[] getMaxBlockCapacity(){
			int[] x_y_capacity = { (Game.PLAY_AREA_SIDE - 2 * Game.WALL_PADDING) / (Block.SIZE + Game.COLUMNS_PADDING) + 1,
				((Game.PLAY_AREA_SIDE - 2 * Game.WALL_PADDING) / (Block.SIZE + Game.ROW_PADDING) + 1) };
			return x_y_capacity;
	}

}