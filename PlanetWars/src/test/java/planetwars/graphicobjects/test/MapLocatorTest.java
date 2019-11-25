/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.graphicobjects.test;

import javafx.scene.shape.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logics.graphicobjects.MapLocator;
import planetwars.logics.graphicobjects.Ship;
import static planetwars.graphicobjects.test.ShipTest.HEIGHT;
import static planetwars.graphicobjects.test.ShipTest.WIDTH;
import static planetwars.ui.PlanetWarsApplication.SPACE_HEIGHT;
import static planetwars.ui.PlanetWarsApplication.SPACE_WIDTH;
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
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void mapLocatorHasCorrectDimensions() {
		assertEquals((int) Math.round(((Rectangle)(mapLocator.getShape())).getWidth()), 
				(int) Math.round((1.0*screenWidth/SPACE_WIDTH)*mapWidth));
		assertEquals((int) Math.round(((Rectangle)(mapLocator.getShape())).getHeight()), 
				(int) Math.round((1.0*screenHeight/SPACE_HEIGHT)*mapHeight));
	}

    @Test
    public void mapLocatorFixedToPlayersLocationCorrectly() {
        assertEquals(mapLocator.getXCoord(), (ship.getXCoord()/SPACE_WIDTH)*mapWidth);
        assertEquals(mapLocator.getYCoord(), (ship.getYCoord()/SPACE_HEIGHT)*mapHeight);
    }

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
