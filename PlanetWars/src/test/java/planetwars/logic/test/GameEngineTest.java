/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import planetwars.database.Database;
import planetwars.database.PlayerDao;
import planetwars.logic.GameEngine;
import planetwars.logic.Player;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */
public class GameEngineTest {
	private GameEngine gameEngine;
	
	public GameEngineTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() throws Exception {
		gameEngine = new GameEngine(new PlanetWarsApplication());
		gameEngine.saveGame(new Player("mockUsername", "mockPassword", 1000, 4));
		gameEngine.signIn("mockUsername", "mockPassword");
	}
	
	@After
	public void tearDown() throws Exception {
		PlayerDao playerDao = new PlayerDao(new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwars.db"));
		playerDao.delete("mockUsername");
	}

	@Test
	public void newGame() {
		gameEngine.newGame(800, 600);
		assertEquals("mockUsername", gameEngine.getPlayer().getUsername());
		assertEquals("mockPassword", gameEngine.getPlayer().getPassword());
		assertEquals(1000, gameEngine.getPlayer().getPoints());
		assertEquals(4, gameEngine.getPlayer().getLevel());
		assertEquals(4, gameEngine.getPlanets().size());
		assertEquals(1000, gameEngine.getGame().getPoints());
	} 
}
