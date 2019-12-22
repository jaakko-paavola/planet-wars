/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic.graphicobjects.test;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logic.Game;
import planetwars.logic.GameArena;
import planetwars.logic.graphicobjects.Shape;
import planetwars.logic.graphicobjects.Torpedo;

/**
 *
 * @author jaakkpaa
 */
public class TorpedoTest {
	Game game;
	GameArena gameArena;
	public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = (int) resolution.getWidth();
	public static int screenHeight = (int) resolution.getHeight(); 
	
	public TorpedoTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		gameArena = new GameArena(1);
		game = new Game(screenWidth, screenHeight, gameArena, 0);
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void creatingTorpedoMakes() {
		Torpedo torpedo = new Torpedo((int) gameArena.getPlayer1Ship().getShape().getTranslateX(),
				(int) gameArena.getPlayer1Ship().getShape().getTranslateY());
		assertEquals(gameArena.getPlayer1Ship().getXCoord(), torpedo.getXCoord());
		assertEquals(gameArena.getPlayer1Ship().getYCoord(), torpedo.getYCoord());
		
	}

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
