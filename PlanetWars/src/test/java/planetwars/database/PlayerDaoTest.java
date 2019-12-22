/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.database;

import planetwars.logic.Player;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jaakkpaa
 */
public class PlayerDaoTest {
	
	public PlayerDaoTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void savingAndUpdatingPlayerWorksCorrectly() throws Exception {
		Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwars.db");
		PlayerDao playerDao = new PlayerDao(database);

		Player player = new Player("testUsername", "testPassword", 400, 3);
		playerDao.saveOrUpdate(player);
		Player playerFetched = playerDao.findOne("testUsername");
		assertEquals("testUsername", playerFetched.getUsername());
		assertEquals("testPassword", playerFetched.getPassword());
		assertEquals(400, playerFetched.getPoints());
		assertEquals(3, playerFetched.getLevel());
		
		player = new Player("testUsername", "testPassword", 600, 4);
		playerDao.saveOrUpdate(player);
		playerFetched = playerDao.findOne("testUsername");
		assertEquals("testUsername", playerFetched.getUsername());
		assertEquals("testPassword", playerFetched.getPassword());
		assertEquals(600, playerFetched.getPoints());
		assertEquals(4, playerFetched.getLevel());

		int nextInt = new Random().nextInt(1000);
		player = new Player("testUsername" + nextInt, "testPassword");
		playerDao.saveOrUpdate(player);
		playerFetched = playerDao.findOne("testUsername" + nextInt);
		assertEquals("testUsername" + nextInt, playerFetched.getUsername());
		assertEquals("testPassword", playerFetched.getPassword());
		assertEquals(0, playerFetched.getPoints());
		assertEquals(1, playerFetched.getLevel());
	}

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
