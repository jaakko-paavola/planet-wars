/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.graphicobjects.test;

import javafx.scene.shape.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logics.graphicobjects.MapLocator;
import planetwars.logics.graphicobjects.Ship;
import static planetwars.logics.graphicobjects.test.ShipTest.HEIGHT;
import static planetwars.logics.graphicobjects.test.ShipTest.WIDTH;
import planetwars.logics.GameArena;
import planetwars.ui.PlanetWarsApplication;
import static planetwars.ui.PlanetWarsApplication.mapHeight;
import static planetwars.ui.PlanetWarsApplication.mapWidth;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class MapLocatorTest {
	private Ship ship;	
	private MapLocator mapLocator;	
	private MapLocator mapLocatorReference;	
	
	public MapLocatorTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		ship = new Ship(Math.round(screenWidth/2), Math.round(screenHeight/2));
		mapLocator = new MapLocator(ship);	
		mapLocatorReference = new MapLocator(ship);	
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void mapLocatorHasCorrectDimensions() {
		assertEquals((int) Math.round(((Rectangle)(mapLocator.getShape())).getWidth()), 
				(int) Math.round((1.0*screenWidth/GameArena.spaceWidth)*mapWidth));
		assertEquals((int) Math.round(((Rectangle)(mapLocator.getShape())).getHeight()), 
				(int) Math.round((1.0*screenHeight/GameArena.spaceHeight)*mapHeight));
	}

    @Test
    public void mapLocatorFixedToPlayersLocationCorrectly() {
        assertEquals(mapLocator.getXCoord(), (ship.getXCoord()/GameArena.spaceWidth)*mapWidth);
        assertEquals(mapLocator.getYCoord(), (ship.getYCoord()/GameArena.spaceHeight)*mapHeight);
    }

	@Test
	public void accelerateInReferenceToShipMoveMapLocatorCorrectAmount() {
		mapLocator.accelerateInReferenceTo(ship, 1);
		assertEquals(mapLocatorReference.getMovement().getX() + 0.005 * 
			(1.0 * PlanetWarsApplication.mapWidth / GameArena.spaceWidth),
			mapLocator.getMovement().getX(), 1);
	}

	@Test
	public void brakeInReferenceToShipMoveMapLocatorCorrectAmount() {
		mapLocator.brakeInReferenceTo(ship, 1);
		assertEquals(mapLocatorReference.getMovement().getX() - 0.001 * 
			(1.0 * PlanetWarsApplication.mapWidth / GameArena.spaceWidth),
			mapLocator.getMovement().getX(), 1);		
	}	
}
