/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.ui;

import planetwars.logic.GameArena;
import java.util.stream.Collectors;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import planetwars.logic.Game;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Torpedo;
import planetwars.ui.PlanetWarsApplication;
import planetwars.logic.Player;
import planetwars.database.PlayerDao;
import planetwars.logic.GameEngine;
import planetwars.logic.LogicLayer;

/**
 * The class animates movement and checks for eventualities, and in case they 
 * happen, handles the eventualities accordingly, such as torpedo hitting a planet
 * or the player's ship flying out of bounds.
 * 
 * @author jaakkpaa
 */

public class Animation extends javafx.animation.AnimationTimer {
	private Map<KeyCode, Boolean> keysPressed;
	private LogicLayer logicInterface;
	private GameScene gameScene;
	private PlanetWarsApplication gui;
	private GameEngine gameEngine;

	public Animation(Map<KeyCode, Boolean> keysPressed, LogicLayer logicInterface, 
			GameScene gameScene, PlanetWarsApplication gui, GameEngine gameEngine) {
		this.keysPressed = keysPressed;
		this.logicInterface = logicInterface;
		this.gameScene = gameScene;
		this.gui = gui;
		this.gameEngine = gameEngine;
	}

	@Override
	public void handle(long timeNow) {
		logicInterface.handleLevelTimerRefresh(timeNow);
		if (logicInterface.handleRunningOutOfTime()) {
			try {
				newGame();
			} catch (Exception ex) {
				Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		logicInterface.handleArrowKeyPresses(keysPressed);
		Torpedo torpedo = logicInterface.handleFiringTorpedoBySpaceKeyPress(keysPressed);                
		if (torpedo != null) {
			gameScene.getGameView().getChildren().add(torpedo.getShape());
		}
		ArrayList<Planet> destroyedPlanets = logicInterface.handleTorpedosHittingPlanets();
		for (Planet destroyedPlanet : destroyedPlanets) {
			makePlanetLookDestroyed(destroyedPlanet);
		}
		logicInterface.handleTorpedosHittingBoundary();
		List<Torpedo> torpedosToBeRemoved = logicInterface.removeTorpedosHittingPlanetsOrBoundary();
		if (torpedosToBeRemoved.size() > 0) {
			torpedosToBeRemoved.forEach(torpedoToBeRemoved -> gameScene.getGameView()
					.getChildren().remove(torpedoToBeRemoved.getShape()));
		}
		ArrayList<Planet> conqueredPlanets = logicInterface.handleShipHittingPlanets();
		for (Planet conqueredPlanet : conqueredPlanets) {
			makePlanetLookConquered(conqueredPlanet);
		}
		
		refreshGauges(gameEngine.getTimeLeft(), timeNow, gameEngine.getStartTime());
		
		logicInterface.moveMovableGraphicObjects();
		
		try {
			if (logicInterface.hasFlownOutOfBounds()) {
				newGame();
			}
		} catch (Exception ex) {
			Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if(logicInterface.handleNoPlanetsLeft()) {
			try {
				newGame();
			} catch (Exception ex) {
				Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void makePlanetLookDestroyed(Planet planet) {
		gameScene.getGameView().getChildren().remove(planet.getShape());
		gameScene.getMapView().getChildren().remove(planet.getMapViewPlanet().getShape());
		planet.getShape().setOpacity(0.2);
		planet.getMapViewPlanet().getShape().setFill(Color.BLACK);
		gameScene.getGameView().getChildren().add(planet.getShape());
		gameScene.getMapView().getChildren().add(planet.getMapViewPlanet().getShape());
	}
	

	private void newGame() throws InterruptedException, Exception {
		this.stop();
		gui.initializeGameScene();
	}

	private void refreshGauges(double timeLeft, long timeNow, long startTime) {
		gameScene.setTextPoints("Points: " + gameEngine.getPoints());
		gameScene.setTextSpeed("Speed: " + logicInterface.round(Math.sqrt(
						Math.pow(gameEngine.getBoundaryRectangle().getXSpeed(gameEngine.getPlayerShip()), 2)
						+ Math.pow(gameEngine.getBoundaryRectangle().getYSpeed(gameEngine.getPlayerShip()), 2)) / 1000, 1));
		gameScene.setTextCoordinates("Coordinates: " + (-gameEngine.getBoundaryRectangle().getXCoord() + gameEngine.getPlayer1StartingXCoord())
						+ "." + (-gameEngine.getBoundaryRectangle().getYCoord() + gameEngine.getPlayer1StartingYCoord()));
		gameScene.setTextTimer("Time left: " + timeLeft);
	}

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
