/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import planetwars.database.PlayerDao;
import planetwars.logics.graphicobjects.MapLocator;
import planetwars.logics.graphicobjects.Ship;
import planetwars.logics.graphicobjects.Torpedo;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 * The class handles all information regarding the current game: the player's ship,
 * the torpedos, the map locator, the points, the time at the beginning and the time
 * left, and planets left.
 * @author jaakkpaa
 */
public class Game {
	private Ship player1Ship;
	private List<Torpedo> torpedos;	
	private MapLocator mapLocator;	
	private int points;
	private int timePerLevel = 60;

	public int getTimePerLevel() {
		return timePerLevel;
	}
	private double timeLeft;
	private int player1StartingXCoord;
	private int player1StartingYCoord;
	private GameArena gameArena;

	public int getPlayer1StartingXCoord() {
		return player1StartingXCoord;
	}

	public int getPlayer1StartingYCoord() {
		return player1StartingYCoord;
	}

	public void setPreviousTorpedoFired(long previousTorpedoFired) {
		this.previousTorpedoFired = previousTorpedoFired;
	}

	public long getPreviousTorpedoFired() {
		return previousTorpedoFired;
	}
	private long previousTorpedoFired;

	public void setTimeLeft(double timeLeft) {
		this.timeLeft = timeLeft;
	}

	public double getTimeLeft() {
		return timeLeft;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	private long startTime = 0;
	private int planetsLeft;

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	/**
	 * Sets the settings for the current game.
	 * @param screenWidth The width of the screen in pixels.
	 * @param screenHeight The height of the screen in pixels.
	 * @param gameArena The game arena created for the game.
	 * @param points The points in the current game.
	 */
	public Game(int screenWidth, int screenHeight, GameArena gameArena, int points) {
		this.gameArena = gameArena;
		this.planetsLeft = gameArena.getPlanets().size();
		this.player1StartingXCoord = screenWidth / 3; 
		this.player1StartingYCoord = screenHeight / 3; 
		this.player1Ship = new Ship(player1StartingXCoord, player1StartingYCoord);
		this.mapLocator = new MapLocator(player1Ship, gameArena);
        this.torpedos = new ArrayList<>();
		this.points = points;
		this.timeLeft = timePerLevel;
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

