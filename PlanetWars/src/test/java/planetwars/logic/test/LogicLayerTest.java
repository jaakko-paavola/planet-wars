/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import planetwars.logic.Game;
import planetwars.logic.LogicLayer;
import planetwars.logic.StubEngine;
import planetwars.logic.GameEngine;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Torpedo;
/**
 *
 * @author jaakkpaa
 */
public class LogicLayerTest {
	private LogicLayer logicLayer;
	private Map<KeyCode, Boolean> keysPressed;
	
	public LogicLayerTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() throws Exception {
		keysPressed = new HashMap<>();
		logicLayer = new LogicLayer(new StubEngine());
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void oneArrowKeyUpPressAcceleratesTheRightAmount() {
		keysPressed.put(KeyCode.UP, Boolean.TRUE);
		logicLayer.handleArrowKeyPresses(keysPressed);
		Assert.assertEquals(-logicLayer.getGameEngine().getGame().getAccelerationFactor()
				* logicLayer.getGameEngine().getPlayer().getPlayerShipAcceleration(), 
				logicLayer.getGameEngine().getGameArena().getPlanets().get(0).getMovement().magnitude(), 0.1);
	}	

	@Test
	public void oneArrowKeyDownPressBrakesTheRightAmount() {
		keysPressed.put(KeyCode.DOWN, Boolean.TRUE);
		logicLayer.handleArrowKeyPresses(keysPressed);
		Assert.assertEquals(logicLayer.getGameEngine().getGame().getBrakingFactor()
				* logicLayer.getGameEngine().getPlayer().getPlayerShipBraking(), 
				logicLayer.getGameEngine().getGameArena().getPlanets().get(0).getMovement().magnitude(), 0.1);
	}	

	@Test
	public void oneArrowKeyLeftPressTurnsTheRightAmount() {
		keysPressed.put(KeyCode.LEFT, Boolean.TRUE);
		logicLayer.handleArrowKeyPresses(keysPressed);
		Assert.assertEquals(-logicLayer.getGameEngine().getPlayer().getPlayerShipRotationSpeed(), 
				logicLayer.getGameEngine().getGame().getPlayer1Ship().getShape().getRotate(), 0.1);
	}
	
	@Test
	public void oneArrowKeyRightPressTurnsTheRightAmount() {
		keysPressed.put(KeyCode.RIGHT, Boolean.TRUE);
		logicLayer.handleArrowKeyPresses(keysPressed);
		Assert.assertEquals(logicLayer.getGameEngine().getPlayer().getPlayerShipRotationSpeed(),
				logicLayer.getGameEngine().getGame().getPlayer1Ship().getShape().getRotate(), 0.1);
	}

	@Test
	public void oneSpaceKeyPressFiresTorpedoAndAcceleratesRightAmount() {
		keysPressed.put(KeyCode.SPACE, Boolean.TRUE);
		Torpedo torpedo = logicLayer.handleFiringTorpedoBySpaceKeyPress(keysPressed);
		Assert.assertEquals(logicLayer.getGameEngine().getGame().getAccelerationFactor()
				* logicLayer.getGameEngine().getPlayer().getPlayerTorpedoAcceleration()
				* logicLayer.getGameEngine().getPlayer().getPlayerTorpedoSpeedMultiplier(),
				torpedo.getMovement().magnitude(), 0.1);
	}

	@Test
	public void	torpodeHittingPlanetDiesAndPlanetGetsDestoyed() {
		keysPressed.put(KeyCode.SPACE, Boolean.TRUE);
		Torpedo torpedo = logicLayer.handleFiringTorpedoBySpaceKeyPress(keysPressed);	
		torpedo.getShape().setTranslateX(logicLayer.getGameEngine().getGameArena().getPlanets().get(0).getXCoord());
		torpedo.getShape().setTranslateY(logicLayer.getGameEngine().getGameArena().getPlanets().get(0).getYCoord());
		logicLayer.handleTorpedosHittingPlanets();
		Assert.assertTrue(torpedo.isAlive());
		Planet planet = logicLayer.getGameEngine().getGameArena().getPlanets().get(0);
		Assert.assertTrue(!planet.isAlive());
	}

	@Test
	public void planetHitByShipGetsConquered() {
		Game game = logicLayer.getGameEngine().getGame();
		Planet planet = logicLayer.getGameEngine().getGameArena().getPlanets().get(0);
		planet.getShape().setTranslateX(game.getPlayer1Ship().getXCoord());
		planet.getShape().setTranslateY(game.getPlayer1Ship().getYCoord());
		logicLayer.handleShipHittingPlanets();
		Assert.assertTrue(logicLayer.getGameEngine().getGameArena().getPlanets().get(0).isConquered());
	}
}
