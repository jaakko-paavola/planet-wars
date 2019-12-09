/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.ui;

import planetwars.logics.GameArena;
import java.util.stream.Collectors;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import planetwars.logics.Game;
import planetwars.logics.graphicobjects.Planet;
import planetwars.logics.graphicobjects.Torpedo;
import planetwars.ui.PlanetWarsApplication;
import planetwars.database.Player;
import planetwars.database.PlayerDao;

/**
 * The class animates movement and checks for eventualities, and in case they 
 * happen, handles the eventualities accordingly, such as torpedo hitting a planet
 * or the player's ship flying out of bounds.
 * 
 * @author jaakkpaa
 */

public class Animation extends javafx.animation.AnimationTimer {
	private GameArena gameArena;
	private Game game;
	private Player player;

	public Animation(GameArena gameArena, Game game, Player player) {
		this.gameArena = gameArena;
		this.game = game;
		this.player = player;
	}

	@Override
	public void handle(long timeNow) {
		game.setStartTime(game.getStartTime() == 0 ? timeNow : game.getStartTime());
		game.setTimeLeft(round(Game.timePerLevel - (timeNow / 1000000000.0 - game.getStartTime() / 1000000000.0), 1));
		try {
			handleRunningOutOfTime(game.getTimeLeft());
		} catch (Exception ex) {
			Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if (PlanetWarsApplication.keysPressed.getOrDefault(KeyCode.LEFT, false)) {
			game.getPlayer1Ship().turnLeft(1);
		}
		if (PlanetWarsApplication.keysPressed.getOrDefault(KeyCode.RIGHT, false)) {
			game.getPlayer1Ship().turnRight(1);
		}
		if (PlanetWarsApplication.keysPressed.getOrDefault(KeyCode.DOWN, false)) {
			brakeShip();
		} 
		if (PlanetWarsApplication.keysPressed.getOrDefault(KeyCode.UP, false)) {
			accelerateShip();
		}
		if (PlanetWarsApplication.keysPressed.getOrDefault(KeyCode.SPACE, false) 
						&& System.currentTimeMillis() - game.getPreviousTorpedoFired() > 200) {
			fireTorpedo();
		}                
		handleTorpedosHittingPlanets();
		handleTorpedosHittingBoundary();
		removeTorpedosHittingPlanetsOrBoundary();
		handleShipHittingPlanets();
		refreshGauges(game.getTimeLeft(), timeNow, game.getStartTime());
		moveMovableGraphicObjects();
		try {
			handleFlyingOutOfBounds();
		} catch (Exception ex) {
			Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			handleNoPlanetsLeft(game.getTimeLeft());
		} catch (Exception ex) {
			Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
/**
 * When the game arena has no more planets left to be conquered or destroyed, the game ends,
 * the player's level is increased by one, and he is granted points based on the time left
 * in the timer. Finally, the player's new situation is saved in the database and a new game
 * with a new game arena is started.
 * @param timeLeft
 * @throws NumberFormatException
 * @throws Exception 
 */
	private void handleNoPlanetsLeft(double timeLeft) throws NumberFormatException, Exception {
		if (game.getPlanetsLeft() == 0) {
			player.setLevel(player.getLevel() + 1);
			player.setPoints((int) (game.getPoints() + Math.round(timeLeft * 10) * 3));
			PlanetWarsApplication.playerDao.saveOrUpdate(player);
			try {
				newGame();
			} catch (InterruptedException ex) {
				Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void handleRunningOutOfTime(double timeLeft) throws InterruptedException, Exception {
		if (timeLeft == 0.0) {
			newGame();
		}
	}
	
	private void handleFlyingOutOfBounds() throws InterruptedException, Exception {
		if (!game.getPlayer1Ship().collide(gameArena.getBoundaryRectangle())) {
			newGame();
		}
	}

	private void handleTorpedosHittingBoundary() {
		game.getTorpedos().forEach(torpedo -> {
			if (!torpedo.collide(gameArena.getBoundaryRectangle())) {
				torpedo.setAlive(false);
			}
		});
	}

	private void newGame() throws InterruptedException, Exception {
		this.stop();
		PlanetWarsApplication.startOrRestartLevel(player.getUsername());
	}

	private static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}

	private void moveMovableGraphicObjects() {
		game.getTorpedos().forEach(torpedo -> torpedo.move());
		game.getMapLocator().move();
		gameArena.getBoundaryRectangle().move();
		gameArena.getPlanets().forEach(planet -> planet.move());
	}

	private void refreshGauges(double timeLeft, long timeNow, long startTime) {
		PlanetWarsApplication.textPoints.setText("Points: " + game.getPoints());
		PlanetWarsApplication.textSpeed.setText("Speed: " + round(Math.sqrt(
						Math.pow(gameArena.getBoundaryRectangle().getXSpeed(game.getPlayer1Ship()), 2)
						+ Math.pow(gameArena.getBoundaryRectangle().getYSpeed(game.getPlayer1Ship()), 2)) / 1000, 1));
		PlanetWarsApplication.textCoordinates.setText("Coordinates: " + (-gameArena.getBoundaryRectangle().getXCoord() + game.getPlayer1StartingXCoord())
						+ "." + (-gameArena.getBoundaryRectangle().getYCoord() + game.getPlayer1StartingYCoord()));
		PlanetWarsApplication.textTimer.setText("Time left: " + timeLeft);
	}

	private void handleShipHittingPlanets() {
		gameArena.getPlanets().forEach(planet -> {
			if (game.getPlayer1Ship().collide(planet)) {
				if (planet.isAlive() && !planet.isConquered()) {
					game.setPoints(game.getPoints() + 1000);
					game.setPlanetsLeft(game.getPlanetsLeft() - 1);
					planet.setConquered(true);
					makePlanetLookConquered(planet);
					planet.setConquered(false);
				}
			}
		});
	}

	private void makePlanetLookConquered(Planet planet) {
		PlanetWarsApplication.gameView.getChildren().remove(planet.getShape());
		PlanetWarsApplication.mapView.getChildren().remove(planet.getMapViewPlanet().getShape());
		planet.getShape().setStroke(Color.GOLD);
		planet.getShape().setStrokeWidth(5);
		planet.getMapViewPlanet().getShape().setFill(Color.GOLD);
		PlanetWarsApplication.gameView.getChildren().add(planet.getShape());
		PlanetWarsApplication.mapView.getChildren().add(planet.getMapViewPlanet().getShape());
	}

	private void handleTorpedosHittingPlanets() {
		game.getTorpedos().forEach(torpedo -> {
			gameArena.getPlanets().forEach(planet -> {
				if (torpedo.collide(planet)) {
					torpedo.setAlive(false);
					if (planet.isAlive()) {
						planet.setAlive(false);
						makePlanetLookDestroyed(planet);
						if (planet.isConquered()) {
							game.setPoints(game.getPoints() - 500);
						} else {
							game.setPoints(game.getPoints() + 500);
						}
						game.setPlanetsLeft(game.getPlanetsLeft() - 1);
					}
				}
			});
		});
	}

	private void removeTorpedosHittingPlanetsOrBoundary() {
		game.getTorpedos().stream()
						.filter(torpedo -> !torpedo.isAlive())
						.forEach(torpedo -> PlanetWarsApplication.gameView.getChildren().remove(torpedo.getShape()));
		game.getTorpedos().removeAll(game.getTorpedos().stream()
						.filter(torpedo -> !torpedo.isAlive())
						.collect(Collectors.toList()));
	}

	private void makePlanetLookDestroyed(Planet planet) {
		PlanetWarsApplication.gameView.getChildren().remove(planet.getShape());
		PlanetWarsApplication.mapView.getChildren().remove(planet.getMapViewPlanet().getShape());
		planet.getShape().setOpacity(0.2);
		planet.getMapViewPlanet().getShape().setFill(Color.BLACK);
		PlanetWarsApplication.gameView.getChildren().add(planet.getShape());
		PlanetWarsApplication.mapView.getChildren().add(planet.getMapViewPlanet().getShape());
	}

	private void fireTorpedo() {
		Torpedo torpedo = new Torpedo((int) game.getPlayer1Ship().getShape().getTranslateX(),
						(int) game.getPlayer1Ship().getShape().getTranslateY());
		torpedo.getShape().setRotate(game.getPlayer1Ship().getShape().getRotate());
		game.getTorpedos().add(torpedo);
		torpedo.accelerateInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		torpedo.setMovement(torpedo.getMovement().normalize().multiply(9));
		PlanetWarsApplication.gameView.getChildren().add(torpedo.getShape());
		game.setPreviousTorpedoFired(System.currentTimeMillis());
		brakeShip();
	}

	private void accelerateShip() {
		game.getMapLocator().accelerateInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		gameArena.getBoundaryRectangle().accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		
		for (Planet planet : gameArena.getPlanets()) {
			planet.accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
			planet.getMapViewPlanet().accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		}
		for (Torpedo torpedo : game.getTorpedos()) {
			torpedo.accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		}
	}

	private void brakeShip() {
		game.getMapLocator().brakeInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		gameArena.getBoundaryRectangle().brakeToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		
		for (Planet planet : gameArena.getPlanets()) {
			planet.brakeToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
			planet.getMapViewPlanet().brakeToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		}
	}
}
