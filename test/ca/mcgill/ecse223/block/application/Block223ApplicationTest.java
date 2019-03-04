package ca.mcgill.ecse223.block.application;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class Block223ApplicationTest {

	private static String filename = "testdata.thicc";
	
	@BeforeClass
	public static void setUpOnce() {
		Block223Persistence.setFilename(filename);		
	}
	
	@Before
	public void setUp() {
		// remove test file
		File f = new File(filename);
		f.delete();
		// clear all data
		Block223 block223 = Block223Application.getBlock223();
		block223.delete();
	}
	
	@Test
	public void testPersistence() {
		Block223 block223 = Block223Application.getBlock223();
		
		assertEquals(0, block223.getGames().size());
		assertEquals(0, block223.getRoles().size());
		assertEquals(0, block223.getUsers().size());
		
		try {
			Block223Controller.register("Michael", "abc", "123");
			Block223Controller.login("Michael", "123");
			Block223Controller.createGame("Game 1");
			Block223Controller.createGame("ANother one");
			
		} catch (InvalidInputException e) {
			fail(e.getMessage());
		}
		
		block223 = Block223Application.getBlock223();
		assertEquals(1, block223.getUsers().size());
		assertEquals(2, block223.getGames().size());
		
		Admin cur_admin = (Admin) Block223Application.getCurrentUserRole();
		for(Game game : block223.getGames()) {
			assertEquals(game.getAdmin(), cur_admin);
		}
		assertEquals(2, cur_admin.getGames().size());
		
		Block223 block223_2 = Block223Persistence.load();
		for(Game game : block223.getGames()) {
			assertEquals(game.getAdmin(), cur_admin);
		}
		assertEquals(2, cur_admin.getGames().size());
		assertEquals(1, block223_2.getUsers().size());
		assertEquals(2, block223_2.getGames().size());
		
	}

}
