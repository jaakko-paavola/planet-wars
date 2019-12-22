/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Shape;

/**
 *
 * @author jaakkpaa
 */
public class StubGameArena implements GameArenaInterface {
	private ArrayList<Planet> planets;
	private int spaceWidth = 10000;
	private int spaceHeight = 10000;
	private BoundaryRectangle boundaryRectangle;
	
	public StubGameArena(int level, GameEngineInterface gameEngine) {
		boundaryRectangle = new BoundaryRectangle(Color.RED, spaceWidth, spaceHeight);
		boundaryRectangle.getShape().setFill(Color.TRANSPARENT);
		planets = new ArrayList<Planet>();	
		planets.add(new Planet(Game.getPlayer1StartingXCoord(), Game.getPlayer1StartingYCoord(), 1000, Color.CORAL, 10000, 10000));
		planets.get(0).setAlive(false);
		planets.get(0).setConquered(true);
	}

	@Override
	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	public int getSpaceWidth() {
		return spaceWidth;
	}

	public int getSpaceHeight() {
		return spaceHeight;
	}

	@Override
	public Shape getBoundaryRectangle() {
		return boundaryRectangle;
	}

}
