/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import planetwars.logics.graphicobjects.BoundaryRectangle;
import planetwars.logics.graphicobjects.Planet;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class GameArena {
	public static int spaceWidth = 10000;
	public static int spaceHeight = 10000;	
	
	public static int planet1XCoord = screenWidth / 8;
	public static int planet2XCoord = screenWidth / 4;
	public static int planet3XCoord = screenWidth / 2;
	public static int planet4XCoord = screenWidth / 5;

	public static int planet1YCoord = screenHeight / 4;
	public static int planet2YCoord = screenHeight / 9;
	public static int planet3YCoord = screenHeight / 5;
	public static int planet4YCoord = screenHeight / 2;
	
	private ArrayList<Planet> planets;
	private BoundaryRectangle boundaryRectangle;

	public GameArena() {
		planets = new ArrayList<>();
		planets.add(new Planet(planet1XCoord, planet1YCoord, 10, Color.GREEN));
		planets.add(new Planet(planet2XCoord, planet2YCoord, 40, Color.RED));
		planets.add(new Planet(planet3XCoord, planet3YCoord, 60, Color.BLUE));
		planets.add(new Planet(planet4XCoord, planet4YCoord, 20, Color.YELLOW));
		boundaryRectangle = new BoundaryRectangle(Color.RED);
		boundaryRectangle.getShape().setFill(Color.TRANSPARENT);
	}

	public BoundaryRectangle getBoundaryRectangle() {
		return boundaryRectangle;
	}
	
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	// These are here temporarily for testing planets until map creation will be
	// moved to its own class.




}