/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import planetwars.logics.graphicobjects.BoundaryRectangle;
import planetwars.logics.graphicobjects.Planet;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 * The class handles the current game arena with its planets and boundaries and
 * creates a random game arena with the number of planets equal to the level
 * the player is on.
 * @author jaakkpaa
 */
public class GameArena {
	public static int spaceWidth = 10000;
	public static int spaceHeight = 10000;	
	
	private ArrayList<Planet> planets;
	private BoundaryRectangle boundaryRectangle;

	public GameArena(int level) {
		planets = new ArrayList<>();
		
		for (int i = 0; i < level; i++) {
			planets.add(new Planet(new Random().nextInt(spaceWidth / 2 - 100) + 50, 
							new Random().nextInt(spaceHeight / 2 - 100) + 50, 
							new Random().nextInt(80) + 20, 
							Color.rgb(new Random().nextInt(200) + 56,
									new Random().nextInt(200) + 56,
									new Random().nextInt(200) + 56)));	
		}
		
		boundaryRectangle = new BoundaryRectangle(Color.RED);
		boundaryRectangle.getShape().setFill(Color.TRANSPARENT);
	}

	public BoundaryRectangle getBoundaryRectangle() {
		return boundaryRectangle;
	}
	
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
}