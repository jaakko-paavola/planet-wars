/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.planetwars.test;

import java.awt.Dimension;
import java.awt.Toolkit;
import static jp.planetwars.PlanetWarsApplication.HEIGHT;
import static jp.planetwars.PlanetWarsApplication.WIDTH;
import jp.planetwars.Ship;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        player1Ship = new Ship(WIDTH/2, HEIGHT/2);
        assertEquals(WIDTH/2, (int)player1Ship.getShape().getTranslateX());
        assertEquals(HEIGHT/2, (int)player1Ship.getShape().getTranslateY());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
