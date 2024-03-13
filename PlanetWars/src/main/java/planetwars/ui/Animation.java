/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import planetwars.logic.GameEngine;
import planetwars.logic.LogicLayer;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Torpedo;

/**
 * The class animates movement and checks for eventualities, and in case they 
 * happen, handles the eventualities accordingly, such as torpedo hitting a planet
 * or the player's ship flying out of bounds. Calls application logic layer for
 * computation or other logic needed.
 * 
 * @author jaakkpaa
 */

public class Animation extends javafx.animation.AnimationTimer {
	private LogicLayer logicLayer;
	private GameScene gameScene;
	private PlanetWarsApplication gui;
	private GameEngine gameEngine;
	
	private Map<KeyCode, Boolean> keysPressed;

	public Animation(Map<KeyCode, Boolean> keysPressed, LogicLayer logicInterface, 
					GameScene gameScene, PlanetWarsApplication gui, GameEngine gameEngine) {
		this.keysPressed = keysPressed;
		this.logicLayer = logicInterface;
		this.gameScene = gameScene;
		this.gui = gui;
		this.gameEngine = gameEngine;
	}

	/**
	 * The method is called in every frame, making possible animation of events 
	 * on the screen.
	 * @param timeNow 
	 */
	@Override
	public void handle(long timeNow) {
		logicLayer.handleLevelTimerRefresh(timeNow);
		if (logicLayer.handleRunningOutOfTime()) {
			try {
				newGame();
			} catch (Exception ex) {
				Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		logicLayer.handleArrowKeyPresses(keysPressed);
		Torpedo torpedo = logicLayer.handleFiringTorpedoBySpaceKeyPress(keysPressed);                
		if (torpedo != null) {
			gameScene.getGameView().getChildren().add(torpedo.getShape());
		}
		ArrayList<Planet> destroyedPlanets = logicLayer.handleTorpedosHittingPlanets();
		for (Planet destroyedPlanet : destroyedPlanets) {
			makePlanetLookDestroyed(destroyedPlanet);
		}
		logicLayer.handleTorpedosHittingBoundary();
		List<Torpedo> torpedosToBeRemoved = logicLayer.removeTorpedosHittingPlanetsOrBoundary();
		if (torpedosToBeRemoved.size() > 0) {
			torpedosToBeRemoved.forEach(torpedoToBeRemoved -> gameScene.getGameView()
							.getChildren().remove(torpedoToBeRemoved.getShape()));
		}
		ArrayList<Planet> conqueredPlanets = logicLayer.handleShipHittingPlanets();
		for (Planet conqueredPlanet : conqueredPlanets) {
			makePlanetLookConquered(conqueredPlanet);
		}
		
		refreshGauges(gameEngine.getTimeLeft(), timeNow, gameEngine.getStartTime());
		
		logicLayer.moveMovableGraphicObjects();
		
		try {
			if (logicLayer.hasFlownOutOfBounds()) {
				newGame();
			}
		} catch (Exception ex) {
			Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if (logicLayer.handleNoPlanetsLeft()) {
			try {
				newGame();
			} catch (Exception ex) {
				Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Makes the given planet look darker in the gameview and hollow in the map view.
	 * @param planet The planet that needs to be altered.
	 */
	private void makePlanetLookDestroyed(Planet planet) {
		gameScene.getGameView().getChildren().remove(planet.getShape());
		gameScene.getMapView().getChildren().remove(planet.getMapViewPlanet().getShape());
		planet.getShape().setOpacity(0.2);
		planet.getMapViewPlanet().getShape().setFill(Color.BLACK);
		gameScene.getGameView().getChildren().add(planet.getShape());
		gameScene.getMapView().getChildren().add(planet.getMapViewPlanet().getShape());
	}
	
	/**
	 * Stops the current animation and starts a new game.
	 * @throws InterruptedException
	 * @throws Exception 
	 */
	private void newGame() throws InterruptedException, Exception {
		this.stop();
		gui.initializeGameScene();
	}

	/**
	 * Refreshes the information in the gauge panel 
	 * @param timeLeft Time left for the level.
	 * @param timeNow The system time.
	 * @param startTime The start time of the level.
	 */
	private void refreshGauges(double timeLeft, long timeNow, long startTime) {
		gameScene.setTextPoints("Points: " + gameEngine.getPoints());
		gameScene.setTextSpeed("Speed: " + logicLayer.round(Math.sqrt(
						Math.pow(gameEngine.getBoundaryRectangle().getXSpeed(gameEngine.getPlayerShip()), 2)
						+ Math.pow(gameEngine.getBoundaryRectangle().getYSpeed(gameEngine.getPlayerShip()), 2)) / 1000, 1));
		gameScene.setTextTimer("Time left: " + timeLeft);
	}

	/**
	 * Makes the given planet look different in the game view and the map view
	 * to indicate it has been conquered.
	 * @param planet The planet to be altered.
	 */
	private void makePlanetLookConquered(Planet planet) {
		gameScene.getGameView().getChildren().remove(planet.getShape());
		gameScene.getMapView().getChildren().remove(planet.getMapViewPlanet().getShape());
		planet.getShape().setStroke(Color.GOLD);
		planet.getShape().setStrokeWidth(5);
		planet.getMapViewPlanet().getShape().setFill(Color.GOLD);
		gameScene.getGameView().getChildren().add(planet.getShape());
		gameScene.getMapView().getChildren().add(planet.getMapViewPlanet().getShape());
	}
}
