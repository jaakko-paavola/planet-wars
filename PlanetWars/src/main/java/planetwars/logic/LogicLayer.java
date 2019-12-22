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
 *
 * @author jaakkpaa
 */
public class LogicLayer implements Logic {
	private Game game;
	private GameArena gameArena;
	private Engine gameEngine;

	public int getFrameRateForSpeedoMeter() {
		return frameRateForSpeedoMeter;
	}
	private final int frameRateForSpeedoMeter = 10000;

	@Override
	public Engine getGameEngine() {
		return gameEngine;
	}
	private Player player;

	public LogicLayer(Engine gameEngine) throws Exception {
		this.gameEngine = gameEngine;	
		this.game = gameEngine.getGame();
		this.gameArena = gameEngine.getGameArena();
		this.player = gameEngine.getPlayer();
	}

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
	
	public Torpedo handleFiringTorpedoBySpaceKeyPress(Map<KeyCode, Boolean> keysPressed) {
		Torpedo torpedo = null;
		if (keysPressed.getOrDefault(KeyCode.SPACE, false) && System.currentTimeMillis()
				- game.getPreviousTorpedoFired() > 200) {
			torpedo = fireTorpedo();
		}
		return torpedo;
	}
	
	private Torpedo fireTorpedo() {
		Torpedo torpedo = new Torpedo((int) game.getPlayer1Ship().getShape().getTranslateX(),
				(int) game.getPlayer1Ship().getShape().getTranslateY());
		torpedo.getShape().setRotate(game.getPlayer1Ship().getShape().getRotate());
		game.getTorpedos().add(torpedo);
		torpedo.accelerateInReferenceTo(game.getPlayer1Ship(), player.getPlayerTorpedoAcceleration(), game.getAccelerationFactor(), frameRateForSpeedoMeter);
		torpedo.setMovement(torpedo.getMovement().multiply(player.getPlayerTorpedoSpeedMultiplier()), frameRateForSpeedoMeter);
		game.setPreviousTorpedoFired(System.currentTimeMillis());
		accelerateShip(player.getPlayerShipBraking());
		return torpedo;
	}
	
	public boolean handleRunningOutOfTime() {
		if (game.getTimeLeft() == 0.0) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Planet> handleTorpedosHittingPlanets() {
		ArrayList<Planet> destroyedPlanets = new ArrayList<Planet>();
		game.getTorpedos().forEach(torpedo -> {
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
	
	
	public void handleTorpedosHittingBoundary() {
		game.getTorpedos().forEach(torpedo -> {
			if (!torpedo.collide(gameEngine.getBoundaryRectangle())) {
				torpedo.setAlive(false);
			}
		});
	}

	public List<Torpedo> removeTorpedosHittingPlanetsOrBoundary() {
		List<Torpedo> torpedosToBeRemoved = game.getTorpedos().stream()
				.filter(torpedo -> !torpedo.isAlive()).collect(Collectors.toList());
		game.getTorpedos().removeAll(game.getTorpedos().stream()
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

	public void moveMovableGraphicObjects() {
		game.getTorpedos().forEach(torpedo -> torpedo.move());
		game.getMapLocator().move();
		gameArena.getBoundaryRectangle().move();
		gameArena.getPlanets().forEach(planet -> planet.move());
	}



	public ArrayList<Planet> handleShipHittingPlanets() {
		ArrayList<Planet> conqueredPlanets = new ArrayList<Planet>();
		gameArena.getPlanets().forEach(planet -> {
			if (game.getPlayer1Ship().collide(planet)) {
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

	@Override
	public void accelerateShip(int acceleration) {
		game.getMapLocator().accelerateInReferenceTo(game.getPlayer1Ship(), acceleration, game.getAccelerationFactor(), frameRateForSpeedoMeter);
		gameArena.getBoundaryRectangle().accelerateInReferenceTo(game.getPlayer1Ship(), -acceleration, game.getAccelerationFactor(), frameRateForSpeedoMeter);

		for (Planet planet : gameArena.getPlanets()) {
			planet.accelerateInReferenceTo(game.getPlayer1Ship(), -acceleration, game.getAccelerationFactor(), frameRateForSpeedoMeter);
			planet.getMapViewPlanet().accelerateInReferenceTo(game.getPlayer1Ship(), -acceleration, game.getAccelerationFactor(), frameRateForSpeedoMeter);
		}
		for (Torpedo torpedo : game.getTorpedos()) {
			torpedo.accelerateInReferenceTo(game.getPlayer1Ship(), -acceleration, game.getAccelerationFactor(), frameRateForSpeedoMeter);
		}
	}

	public double getTimePerLevel() {
		return game.getTimePerLevel();
	}
	
	public void handleLevelTimerRefresh(long timeNow) {
		game.setStartTime(gameEngine.getStartTime() == 0 ? timeNow
				: gameEngine.getStartTime());
		game.setTimeLeft(round(game.getTimePerLevel()
				- (timeNow / 1000000000.0 - game.getStartTime() / 1000000000.0), 1));
	}
	
	public static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
	
	public boolean hasFlownOutOfBounds() throws InterruptedException, Exception {
		if (!game.getPlayer1Ship().collide(gameArena.getBoundaryRectangle())) {
			return true;
		}
		return false;
	}
}
