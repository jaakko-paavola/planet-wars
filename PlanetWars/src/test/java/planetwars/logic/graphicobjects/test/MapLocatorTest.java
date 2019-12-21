/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic.graphicobjects.test;

import javafx.scene.shape.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Ship;
import static planetwars.logic.graphicobjects.test.ShipTest.HEIGHT;
import static planetwars.logic.graphicobjects.test.ShipTest.WIDTH;
import planetwars.logic.GameArena;
import planetwars.ui.PlanetWarsApplication;
import static planetwars.ui.PlanetWarsApplication.mapHeight;
import static planetwars.ui.PlanetWarsApplication.mapWidth;

/**
 *
 * @author jaakkpaa
 */
public class MapLocatorTest {
	private Ship ship;	
	private MapLocator mapLocator;	
	private MapLocator mapLocatorReference;	
	private GameArena gameArena;
	
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
		ship = new Ship(Math.round(PlanetWarsApplication.screenWidth/2), Math.round(PlanetWarsApplication.screenHeight/2));
		gameArena = new GameArena(5);
		mapLocator = new MapLocator(ship, gameArena);	
		mapLocatorReference = new MapLocator(ship, new GameArena(5));	
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void mapLocatorHasCorrectDimensions() {
		assertEquals((int) Math.round(((Rectangle)(mapLocator.getShape())).getWidth()), 
				(int) Math.round((1.0*PlanetWarsApplication.screenWidth/gameArena.getSpaceWidth())*mapWidth));
		assertEquals((int) Math.round(((Rectangle)(mapLocator.getShape())).getHeight()), 
				(int) Math.round((1.0*PlanetWarsApplication.screenHeight/gameArena.getSpaceHeight())*mapHeight));
	}

    @Test
    public void mapLocatorFixedToPlayersLocationCorrectly() {
        assertEquals(mapLocator.getXCoord(), (ship.getXCoord()/gameArena.getSpaceWidth())*mapWidth);
        assertEquals(mapLocator.getYCoord(), (ship.getYCoord()/gameArena.getSpaceHeight())*mapHeight);
    }

	@Test
	public void accelerateInReferenceToShipMoveMapLocatorCorrectAmount() {
		mapLocator.accelerateInReferenceTo(ship, 1);
		assertEquals(mapLocatorReference.getMovement().getX() + 0.005 * 
			(1.0 * PlanetWarsApplication.mapWidth / gameArena.getSpaceWidth()),
			mapLocator.getMovement().getX(), 1);
	}

	@Test
	public void brakeInReferenceToShipMoveMapLocatorCorrectAmount() {
		mapLocator.brakeInReferenceTo(ship, 1);
		assertEquals(mapLocatorReference.getMovement().getX() - 0.001 * 
			(1.0 * PlanetWarsApplication.mapWidth / gameArena.getSpaceWidth()),
			mapLocator.getMovement().getX(), 1);		
	}	
}
