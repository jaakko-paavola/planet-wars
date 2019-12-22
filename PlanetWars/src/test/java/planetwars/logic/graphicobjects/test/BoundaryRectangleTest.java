/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic.graphicobjects.test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.GameArena;
import planetwars.logic.graphicobjects.Ship;
import static planetwars.ui.PlanetWarsApplication.mapHeight;
import static planetwars.ui.PlanetWarsApplication.mapWidth;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class BoundaryRectangleTest {
	private BoundaryRectangle boundaryRectangle;
	private GameArena gameArena;
	
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
		gameArena = new GameArena(1);
		boundaryRectangle = new BoundaryRectangle(Color.RED, gameArena.getSpaceWidth(), gameArena.getSpaceHeight());	
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void boundaryRectangleHasCorrectDimensions() {
		assertEquals(gameArena.getSpaceWidth(), Math.round(((Rectangle)boundaryRectangle.getShape()).getWidth()));
		assertEquals(gameArena.getSpaceHeight(), Math.round(((Rectangle)boundaryRectangle.getShape()).getHeight()));
	}

    @Test
    public void boundaryRectangleInRightCoordinates() {
        assertEquals(0, boundaryRectangle.getXCoord());
        assertEquals(0, boundaryRectangle.getYCoord());
    }
}
