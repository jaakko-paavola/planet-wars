/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.graphicobjects.test; 

import java.awt.Dimension;
import java.awt.Toolkit;
import planetwars.logics.graphicobjects.Ship;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class ShipTest {
    private Ship player1Ship; 
    public static int WIDTH = 800;
    public static int HEIGHT = 600;     
    
    public ShipTest() {
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
    public void creatingShipInTheCenterOfTheScreen() {
        player1Ship = new Ship(Math.round(screenWidth/2), Math.round(screenHeight/2));
        assertEquals(Math.round(screenWidth/2), (int)player1Ship.getShape().getTranslateX());
        assertEquals(Math.round(screenHeight/2), (int)player1Ship.getShape().getTranslateY());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
