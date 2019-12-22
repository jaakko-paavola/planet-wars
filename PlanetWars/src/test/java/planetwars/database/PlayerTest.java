/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.database;

import planetwars.logic.Player;
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
public class PlayerTest {
	
	public PlayerTest() {
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
	public void playerCreatedRight() {
		Player player = new Player("aaa", "bbb");
		Player player2 = new Player("aaa", "bbb", 400, 3);
		assertEquals("aaa", player.getUsername());
		assertEquals("bbb", player.getPassword());
		assertEquals(400, player2.getPoints());
		assertEquals(3, player2.getLevel());
	}
}
