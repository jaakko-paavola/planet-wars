/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.graphicobjects.test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logics.graphicobjects.BoundaryRectangle;
import planetwars.logics.graphicobjects.GameArena;
import planetwars.logics.graphicobjects.Ship;
import static planetwars.ui.PlanetWarsApplication.mapHeight;
import static planetwars.ui.PlanetWarsApplication.mapWidth;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class BoundaryRectangleTest {
	private Ship ship;
	private BoundaryRectangle boundaryRectangle;
	
	public BoundaryRectangleTest() {
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
//		boundaryRectangle = new BoundaryRectangle(rectangle, Color.CORAL);	
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void mapLocatorHasCorrectDimensions() {
		assertEquals((int) Math.round(((Rectangle)(boundaryRectangle.getShape())).getWidth()), 
				(int) Math.round((1.0*screenWidth/GameArena.SPACE_WIDTH)*mapWidth));
		assertEquals((int) Math.round(((Rectangle)(boundaryRectangle.getShape())).getHeight()), 
				(int) Math.round((1.0*screenHeight/GameArena.SPACE_HEIGHT)*mapHeight));
	}

    @Test
    public void mapLocatorFixedToPlayersLocationCorrectly() {
        assertEquals(boundaryRectangle.getXCoord(), (ship.getXCoord()/GameArena.SPACE_WIDTH)*mapWidth);
        assertEquals(boundaryRectangle.getYCoord(), (ship.getYCoord()/GameArena.SPACE_HEIGHT)*mapHeight);
    }
}
