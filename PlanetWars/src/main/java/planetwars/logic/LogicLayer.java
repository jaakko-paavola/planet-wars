/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Ship;
import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Torpedo;
import javafx.scene.shape.Shape;
import planetwars.database.Database;
import planetwars.database.PlayerDao;
import planetwars.ui.Animation;
import planetwars.ui.PlanetWarsApplication;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class functions as the interface to the UI and caters mostly for the
 * animation class computing event based happenings in the game.
 * @author jaakkpaa
 */
public class LogicLayer {
	private GamePlay game;
	private GameArenaInterface gameArena;
	private GameEngineInterface gameEngine;
	private Player player;

	public LogicLayer(GameEngineInterface gameEngine) throws Exception {
		this.gameEngine = gameEngine;
		this.game = gameEngine.getGame();
		this.gameArena = gameEngine.getGameArena();
		this.player = gameEngine.getPlayer();
	}
	
	public GameEngineInterface getGameEngine() {
		return gameEngine;
	}

	/**
	 * Calls the appropriate action based on key presses, i.e. either accelerates,
	 * brakes or turns the player's ship.
	 * @param keysPressed The key presses in a map.
	 */
	public void handleArrowKeyPresses(Map<KeyCode, Boolean> keysPressed) {
		if (keysPressed.getOrDefault(KeyCode.LEFT, false)) {
			gameEngine.getPlayerShip().turnLeft(player.getPlayerShipRotationSpeed());
		}
		if (keysPressed.getOrDefault(KeyCode.RIGHT, false)) {
			gameEngine.getPlayerShip().turnRight(player.getPlayerShipRotationSpeed());
		}
		if (keysPressed.getOrDefault(KeyCode.DOWN, false)) {
			accelerateShip(player.getPlayerShipBraking());
		}
		if (keysPressed.getOrDefault(KeyCode.UP, false)) {
			accelerateShip(player.getPlayerShipAcceleration());
		}
	}
	
	/**
	 * If space key was presses, calls the appropriate action, i.e. firing
	 * a torpedo.
	 * @param keysPressed
	 * @return The torpedo that was fired.
	 */
	public Torpedo handleFiringTorpedoBySpaceKeyPress(Map<KeyCode, Boolean> keysPressed) {
		Torpedo torpedo = null;
		if (keysPressed.getOrDefault(KeyCode.SPACE, false) && System.currentTimeMillis()
						- game.getPreviousTorpedoFired() > 200) {
			torpedo = fireTorpedo();
		}
		return torpedo;
	}
	
	/**
	 * Creates a torpedo graphic object and sets it into motion with appropriate
	 * acceleration. The player's ship also brakes slightly as a result of firing
	 * a torpedo.
	 * @return The torpedo that was fired.
	 */
	private Torpedo fireTorpedo() {
		Torpedo torpedo = new Torpedo((int) gameArena.getPlayerShip().getShape().getTranslateX(),
						(int) gameArena.getPlayerShip().getShape().getTranslateY());
		torpedo.getShape().setRotate(gameArena.getPlayerShip().getShape().getRotate());
		gameArena.getTorpedos().add(torpedo);
		torpedo.accelerateInReferenceTo(gameArena.getPlayerShip(), player.getPlayerTorpedoAcceleration(), game.getAccelerationFactor(), gameEngine.getFrameRateForSpeedoMeter());
		torpedo.setMovement(torpedo.getMovement().multiply(player.getPlayerTorpedoSpeedMultiplier()), gameEngine.getFrameRateForSpeedoMeter());
		game.setPreviousTorpedoFired(System.currentTimeMillis());
		accelerateShip(player.getPlayerShipBraking());
		return torpedo;
	}
	
	/**
	 * Returns true in case the time for the level ran out.
	 * @return Time ran out or not.
	 */
	public boolean handleRunningOutOfTime() {
		if (game.getTimeLeft() == 0.0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if any torpedo hit any planet, and if so, marks the torpedo and
	 * planet appropriately as dead and destroyed and adds or reduces the player's points
	 * depending on whether the planet was conquered prior to the fact.
	 * @return The planets that were hit.
	 */
	public ArrayList<Planet> handleTorpedosHittingPlanets() {
		ArrayList<Planet> destroyedPlanets = new ArrayList<Planet>();
		gameArena.getTorpedos().forEach(torpedo -> {
			gameArena.getPlanets().forEach(planet -> {
				if (torpedo.collide(planet)) {
					torpedo.setAlive(false);
					if (planet.isAlive()) {
						planet.setAlive(false);
						destroyedPlanets.add(planet);	
						if (planet.isConquered()) {
							game.setPoints(game.getPoints() - 500);
						} else {
							game.setPoints(game.getPoints() + 500);
							game.setPlanetsLeft(game.getPlanetsLeft() - 1);
						}
					}
				}
			});
		});
		return destroyedPlanets;
	}
	
	/**
	 * Checks if any torpedo hits the game boundaries and if so, sets them as dead. 
	 */
	
	public void handleTorpedosHittingBoundary() {
		gameArena.getTorpedos().forEach(torpedo -> {
			if (!torpedo.collide(gameEngine.getBoundaryRectangle())) {
				torpedo.setAlive(false);
			}
		});
	}

	/**
	 * Removes all torpedos that have been marked dead, i.e. those that hit planets
	 * or those that hit the game boundaries.
	 * @return The torpedos that net the criteria.
	 */
	public List<Torpedo> removeTorpedosHittingPlanetsOrBoundary() {
		List<Torpedo> torpedosToBeRemoved = gameArena.getTorpedos().stream()
						.filter(torpedo -> !torpedo.isAlive()).collect(Collectors.toList());
		gameArena.getTorpedos().removeAll(gameArena.getTorpedos().stream()
						.filter(torpedo -> !torpedo.isAlive())
						.collect(java.util.stream.Collectors.toList()));
		return torpedosToBeRemoved;
	}
	/**
	 * When the game arena has no more planets left to be conquered or
	 * destroyed, the game ends, the player's level is increased by one, and he
	 * is granted points based on the time left in the timer. Finally, the
	 * player's new situation is saved in the database and a new game with a new
	 * game arena is started.
	 *
	 * @param timeLeft
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public boolean handleNoPlanetsLeft() {
		if (game.getPlanetsLeft() == 0) {
			player.setLevel(player.getLevel() + 1);
			player.setPoints((int) (game.getPoints() + Math.round(game.getTimeLeft() * 10) * 3));
			return true;
		}
		return false;
	}

	/**
	 * Moves the graphic objects in the scene.
	 */
	public void moveMovableGraphicObjects() {
		gameArena.getTorpedos().forEach(torpedo -> torpedo.move());
		gameArena.getMapLocator().move();
		gameArena.getBoundaryRectangle().move();
		gameArena.getPlanets().forEach(planet -> planet.move());
	}
	/**
	 * Check whether the player's ship hits any planets and if so,
	 * marks the planets as conquered.
	 * @return The conquered planets.
	 */
	public ArrayList<Planet> handleShipHittingPlanets() {
		ArrayList<Planet> conqueredPlanets = new ArrayList<Planet>();
		gameArena.getPlanets().forEach(planet -> {
			if (gameArena.getPlayerShip().collide(planet)) {
				if (planet.isAlive() && !planet.isConquered()) {
					game.setPoints(game.getPoints() + 1000);
					game.setPlanetsLeft(game.getPlanetsLeft() - 1);
					planet.setConquered(true);
					conqueredPlanets.add(planet);	
					planet.setConquered(false);
				}
			}
		});
		return conqueredPlanets;
	}

	/**
	 * Accelerates the ship, i.e. actually accelerates all the other graphic
	 * objects to an appropriate direction, because the ship must always be fixed
	 * to the center of the screen.
	 * @param acceleration The factor of acceleration.
	 */
	public void accelerateShip(int acceleration) {
		gameArena.getMapLocator().accelerateInReferenceTo(gameArena.getPlayerShip(), acceleration, game.getAccelerationFactor(), gameEngine.getFrameRateForSpeedoMeter());
		gameArena.getBoundaryRectangle().accelerateInReferenceTo(gameArena.getPlayerShip(), -acceleration, game.getAccelerationFactor(), gameEngine.getFrameRateForSpeedoMeter());

		for (Planet planet : gameArena.getPlanets()) {
			planet.accelerateInReferenceTo(gameArena.getPlayerShip(), -acceleration, game.getAccelerationFactor(), gameEngine.getFrameRateForSpeedoMeter());
			planet.getMapViewPlanet().accelerateInReferenceTo(gameArena.getPlayerShip(), -acceleration, game.getAccelerationFactor(), gameEngine.getFrameRateForSpeedoMeter());
		}
		for (Torpedo torpedo : gameArena.getTorpedos()) {
			torpedo.accelerateInReferenceTo(gameArena.getPlayerShip(), -acceleration, game.getAccelerationFactor(), gameEngine.getFrameRateForSpeedoMeter());
		}
	}
	/**
	 * Records the start time for the level and computes the time left.
	 * @param timeNow The system time in nanoseconds.
	 */
	public void handleLevelTimerRefresh(long timeNow) {
		game.setStartTime(gameEngine.getStartTime() == 0 ? timeNow
						: gameEngine.getStartTime());
		game.setTimeLeft(round(game.getTimePerLevel()
						- (timeNow / 1000000000.0 - game.getStartTime() / 1000000000.0), 1));
	}
	
	/**
	 * A helper function to help round the timer's time to tenths of a second.
	 * @param value The value to be rounded.
	 * @param precision The precision wanted.
	 * @return The rounded value.
	 */
	public static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
	
	/**
	 * Checks whether the player's ship has flown out of bounds.
	 * @return True if out, false if inside the bounds.
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public boolean hasFlownOutOfBounds() throws InterruptedException, Exception {
		if (!gameArena.getPlayerShip().collide(gameArena.getBoundaryRectangle())) {
			return true;
		}
		return false;
	}
}
