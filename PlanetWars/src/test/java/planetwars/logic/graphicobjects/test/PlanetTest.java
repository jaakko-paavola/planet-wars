/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic.graphicobjects.test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logic.GameArena;
import planetwars.logic.graphicobjects.Planet;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */
public class PlanetTest {
	
	public PlanetTest() {
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
	public void creatingPlanetCreatesPlanetAndMapViewPlanet() {
		Planet testPlanet = new Planet(30, 40, 50, Color.CORAL, 10000, 10000);
		assertEquals(30, testPlanet.getXCoord());
		assertEquals(40, testPlanet.getYCoord());
		assertEquals(50, Math.round(((Circle)testPlanet.getShape()).getRadius()));
		assertEquals((int) Math.round(testPlanet.getXCoord() * (1.0 * PlanetWarsApplication.mapWidth
				/ 10000)), testPlanet.getMapViewPlanet().getXCoord());
		assertEquals((int) Math.round(testPlanet.getYCoord() * (1.0 * PlanetWarsApplication.mapHeight
				/ 10000)), testPlanet.getMapViewPlanet().getYCoord());
		assertEquals(Math.round(((Circle) testPlanet.getShape()).getRadius() / 10), 
				Math.round(((Circle) testPlanet.getMapViewPlanet().getShape()).getRadius()));
	}

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
