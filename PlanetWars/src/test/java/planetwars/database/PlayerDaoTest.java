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
 * For testing the PlayerDao class.
 * @author jaakkpaa
 */
public class PlayerDaoTest {
	Database database;
	PlayerDao playerDao;
	Player player;
	
	public PlayerDaoTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() throws Exception {
		database = new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwarsTest.db");
		playerDao = new PlayerDao(database);
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void creatingAndUpdatingPlayerWorksCorrectly() throws Exception {
		playerDao.createTableIfNotExist();
		
		player = new Player("testUsername1", "testPassword");
		playerDao.saveOrUpdate(player);
		Player playerFetched = playerDao.findOne("testUsername1");
		assertEquals("testUsername1", playerFetched.getUsername());
		assertEquals("testPassword", playerFetched.getPassword());
		assertEquals(0, playerFetched.getPoints());
		assertEquals(1, playerFetched.getLevel());
		
		player = new Player("testUsername1", "testPassword", 400, 3);
		playerDao.saveOrUpdate(player);
		playerFetched = playerDao.findOne("testUsername1");
		assertEquals("testUsername1", playerFetched.getUsername());
		assertEquals("testPassword", playerFetched.getPassword());
		assertEquals(400, playerFetched.getPoints());
		assertEquals(3, playerFetched.getLevel());
		
		player = new Player("testUsername1", "testPassword", 600, 4);
		playerDao.saveOrUpdate(player);
		playerFetched = playerDao.findOne("testUsername1");
		assertEquals("testUsername1", playerFetched.getUsername());
		assertEquals("testPassword", playerFetched.getPassword());
		assertEquals(600, playerFetched.getPoints());
		assertEquals(4, playerFetched.getLevel());
	}

	@Test
	public void fetchingNonExistingUserAndDeletingWorksAsExpected() throws Exception {	
		Player findOne = null;
		try {
			findOne = playerDao.findOne("nonExistingUserName");
		} catch (Exception e) {
			assertEquals(null, findOne);
		}

		playerDao.delete("testUsername1");
		try {
			findOne = playerDao.findOne("testUsername1");
		} catch (Exception e) {
			assertEquals(null, findOne);
		}		
	}
}
