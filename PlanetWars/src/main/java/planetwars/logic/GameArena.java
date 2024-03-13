/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic;

import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Ship;
import planetwars.logic.graphicobjects.Torpedo;

/**
 * The class handles the current game arena with its planets and boundaries and
 * creates a random game arena with the number of planets equal to the level
 * the player is on.
 * @author jaakkpaa
 */
public class GameArena implements GameArenaInterface {
	private ArrayList<Planet> planets;
	private BoundaryRectangle boundaryRectangle;
	private Ship player1Ship;
	private List<Torpedo> torpedos;	
	private MapLocator mapLocator;	
	
	private int spaceWidth = 10000;
	private int spaceHeight = 10000;	
	private int player1StartingXCoord = screenWidth / 2;
	private int player1StartingYCoord = screenHeight / 2;
	
	/**
	 * Creates a new game arena with the number of planets decided by the
	 * player's level. The planets are placed in random locations in the game
	 * arena having random sizes and colors.
	 *
	 * @param level The player's level.
	 */	
	public GameArena(int level) {
		planets = new ArrayList<>();
		this.player1Ship = new Ship(player1StartingXCoord, player1StartingYCoord);
		this.torpedos = new ArrayList<>();
		this.mapLocator = new MapLocator(getPlayerShip(), this);

		for (int i = 0; i < level; i++) {
			planets.add(new Planet(new Random().nextInt(spaceWidth / 2 - 100) + 50,
							new Random().nextInt(spaceHeight / 2 - 100) + 50,
							new Random().nextInt(80) + 20,
							Color.rgb(new Random().nextInt(200) + 56,
							new Random().nextInt(200) + 56,
							new Random().nextInt(200) + 56), spaceWidth, spaceHeight));
		}

		boundaryRectangle = new BoundaryRectangle(Color.RED, spaceWidth, spaceHeight);
		boundaryRectangle.getShape().setFill(Color.TRANSPARENT);
	}

	public MapLocator getMapLocator() {
		return mapLocator;
	}
	
	public int getSpaceWidth() {
		return spaceWidth;
	}

	public int getSpaceHeight() {
		return spaceHeight;
	}

	public BoundaryRectangle getBoundaryRectangle() {
		return boundaryRectangle;
	}
	
	@Override
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	
	public int getPlayerStartingXCoord() {
		return player1StartingXCoord;
	}

	public int getPlayerStartingYCoord() {
		return player1StartingYCoord;
	}
	
	public Ship getPlayerShip() {
		return player1Ship;
	}	
	
	public List<Torpedo> getTorpedos() {
		return torpedos;
	}
}