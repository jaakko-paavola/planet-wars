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
import planetwars.logics.LogicInterface;

/**
 * The class animates movement and checks for eventualities, and in case they 
 * happen, handles the eventualities accordingly, such as torpedo hitting a planet
 * or the player's ship flying out of bounds.
 * 
 * @author jaakkpaa
 */

public class Animation extends javafx.animation.AnimationTimer {
	private Map<KeyCode, Boolean> keysPressed;
	private LogicInterface logicInterface;
	private GameScene gameScene;
	private PlanetWarsApplication gui;

	public Animation(Map<KeyCode, Boolean> keysPressed, LogicInterface logicInterface, 
			GameScene gameScene, PlanetWarsApplication gui) {
		this.keysPressed = keysPressed;
		this.logicInterface = logicInterface;
		this.gameScene = gameScene;
		this.gui = gui;
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
		
		refreshGauges(logicInterface.getTimeLeft(), timeNow, logicInterface.getStartTime());
		
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
		gameScene.setTextPoints("Points: " + logicInterface.getPoints());
		gameScene.setTextSpeed("Speed: " + logicInterface.round(Math.sqrt(
						Math.pow(logicInterface.getBoundaryRectangle().getXSpeed(logicInterface.getPlayer1Ship()), 2)
						+ Math.pow(logicInterface.getBoundaryRectangle().getYSpeed(logicInterface.getPlayer1Ship()), 2)) / 1000, 1));
		gameScene.setTextCoordinates("Coordinates: " + (-logicInterface.getBoundaryRectangle().getXCoord() + logicInterface.getPlayer1StartingXCoord())
						+ "." + (-logicInterface.getBoundaryRectangle().getYCoord() + logicInterface.getPlayer1StartingYCoord()));
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
