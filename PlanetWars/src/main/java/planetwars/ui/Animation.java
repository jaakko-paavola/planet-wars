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
import planetwars.logic.GamePlay;
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
		
		if(logicLayer.handleNoPlanetsLeft()) {
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
		gameScene.setTextSpeed("Speed: " + logicLayer.round(Math.sqrt(
						Math.pow(gameEngine.getBoundaryRectangle().getXSpeed(gameEngine.getPlayerShip()), 2)
						+ Math.pow(gameEngine.getBoundaryRectangle().getYSpeed(gameEngine.getPlayerShip()), 2)) / 1000, 1));
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
