package ca.mcgill.ecse223.block.statemachine.tests.p01;

import static ca.mcgill.ecse223.block.tests.util.Block223TestConstants.BALL_SPEED_INCREASE_FACTOR;
import static ca.mcgill.ecse223.block.tests.util.Block223TestConstants.LEVELS;
import static ca.mcgill.ecse223.block.tests.util.Block223TestConstants.TEST_GAME_NAME_1;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.statemachine.tests.Block223PlayModeTest;
import ca.mcgill.ecse223.block.tests.util.Block223TestUtil;

public class StartPauseResumeTests {

	private Game game;
	private Block223 block223;
	private Admin admin;
	private Player player;
	private Block block;
	private PlayedGame playedGame;

	@Before
	public void createGame() {
		block223 = Block223TestUtil.initializeTestBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = new Game(TEST_GAME_NAME_1, 1, admin, 0, 1, BALL_SPEED_INCREASE_FACTOR, 20, 10, block223);
		for (int i = 0; i < LEVELS; i++) {
			game.addLevel();
		}
		Block223Application.setCurrentGame(game);
		block = new Block(1, 1, 1, 1, game);
		new BlockAssignment(1, 1, game.getLevel(0), block, game);
		game.setPublished(true);
		playedGame = new PlayedGame("TestPlayer", game, block223);
		playedGame.setPlayer(player);
		playedGame.setWaitTime(0);
		Block223Application.setCurrentUserRole(player);
		Block223Application.setCurrentPlayableGame(playedGame);
	}

	// startGame
	@Test
	public void testStartGameHitPaddleAndMovePaddleLeft() throws InvalidInputException {

		// Initializing fake user inputs
		Map<Integer, String> inputs = new HashMap<Integer, String>();
		// Create input to move the paddle
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			builder.append("l");
		}
		// The input at iteration 0 is used up by the takeInputs() call before the game
		// loop in the startGame() method. The ball should just touch the paddle after
		// moving down 155 pixels (iterations 1-155). After moving one more pixel (i.e.,
		// iteration 156), the ball should bounce back. In the next iteration (i.e.,
		// iteration 157), the paddle is moved.
		inputs.put(157, builder.toString());

		// Provide the inputs to the test object substituting the actual UI object
		Block223PlayModeTest testInputProvider = new Block223PlayModeTest(inputs);

		// Save the initial number of lives
		int livesBefore = playedGame.getLives();

		Block223Controller.startGame(testInputProvider);

		// Since the ball is expected to have fallen down, there should be one life less
		assertEquals(livesBefore - 1, playedGame.getLives());

		// Furthermore, we check if the ball and paddle have been reset
		// 1. Ball
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.01);
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallY(), 0.01);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.01);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.01);
		// 2. Paddle
		int currentPaddleLength = game.getPaddle().getMaxPaddleLength();
		assertEquals(currentPaddleLength, playedGame.getCurrentPaddleLength(), 0.01);
		assertEquals((Game.PLAY_AREA_SIDE - currentPaddleLength) / 2, playedGame.getCurrentPaddleX(), 0.01);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.01);
	}

	// TODO further test cases

}
