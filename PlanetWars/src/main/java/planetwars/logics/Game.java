/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import planetwars.logics.graphicobjects.MapLocator;
import planetwars.logics.graphicobjects.Ship;
import planetwars.logics.graphicobjects.Torpedo;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class Game {
	private Ship player1Ship;
	private List<Torpedo> torpedos;	
	private MapLocator mapLocator;	
	private int points;
	public static int timePerLevel = 60; 
	private int planetsLeft;

	public static int player1StartingXCoord;
	public static int player1StartingYCoord;	

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public Game(int screenWidth, int screenHeight, GameArena gameArena) {
		this.planetsLeft = gameArena.getPlanets().size();
		player1StartingXCoord = screenWidth / 2; 
		player1StartingYCoord = screenHeight / 2; 
		this.player1Ship = new Ship(player1StartingXCoord, player1StartingYCoord);
		this.mapLocator = new MapLocator(player1Ship);
		this.player1Ship = player1Ship;
        this.torpedos = new ArrayList<>();
		this.points = 0;
	}

	public void setPlanetsLeft(int planetsLeft) {
		this.planetsLeft = planetsLeft;
	}

	public int getPlanetsLeft() {
		return planetsLeft;
	}
	
	public Ship getPlayer1Ship() {
		return player1Ship;
	}	

	public MapLocator getMapLocator() {
		return mapLocator;
	}

	public List<Torpedo> getTorpedos() {
		return torpedos;
	}
	
}

