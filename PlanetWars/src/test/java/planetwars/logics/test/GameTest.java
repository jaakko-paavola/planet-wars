/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.test;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logics.Game;
import planetwars.logics.graphicobjects.Ship;

/**
 *
 * @author jaakkpaa
 */
public class GameTest {
	public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = (int) resolution.getWidth();
	public static int screenHeight = (int) resolution.getHeight(); 	
	public static Game game;
	
	public GameTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {

	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		game = new Game(screenWidth, screenHeight);
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void creatingNewGamePlacesShipInRightCoordinates() {
		assertEquals(screenWidth/2, game.getPlayer1Ship().getXCoord());
		assertEquals(screenHeight/2, game.getPlayer1Ship().getYCoord());
	}

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
