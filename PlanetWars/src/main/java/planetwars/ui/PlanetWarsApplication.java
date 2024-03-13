/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.ui;
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.stage.Stage;
import planetwars.logic.GameEngine;
import planetwars.logic.LogicLayer;
import planetwars.logic.graphicobjects.Planet;

/**
 * The main class of Planet Wars, providing the graphical user interface.
 * @author jaakkpaa
 */

public class PlanetWarsApplication extends Application{
    
	public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = (int) resolution.getWidth();
	public static int screenHeight = (int) resolution.getHeight(); 
	public static int mapWidth = 300;
	public static int mapHeight = 300;

	private Stage primaryStage;

	private LoginScene loginScene;
	private GameScene gameScene;
	private Animation animation;
	private GameEngine gameEngine;
	private LogicLayer logicLayer;

	public LoginScene getLoginScene() {
		return loginScene;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Only to launch the graphical user interface.
	 * @param args 
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * The starting point of the application.
	 * @param primaryStage The stage where the stages created are displayed one-
	 * by-one.
	 * @throws Exception 
	 */
	@Override
    public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.gameEngine = new GameEngine(this);
		this.loginScene = new LoginScene(this, gameEngine);
		primaryStage.setScene(loginScene.createAndReturnScene());
		primaryStage.show();
    }

	/**
	 * The method resets and sets up the graphic elements and creates a new game
	 * on the level the player is in.
	 * @param username A string containing the player's username, which can be
	 * used as a key to fetch the player's information from the database.
	 */
	public void initializeGameScene() throws Exception {
		gameEngine.newGame(screenWidth, screenHeight);
		this.logicLayer = new LogicLayer(gameEngine);
		gameScene = new GameScene(gameEngine);
		this.animation = new Animation(gameScene.getKeysPressed(), logicLayer, 
				gameScene, this, gameEngine);
		gameScene.getGameView().getChildren().add(gameEngine.getPlayerShipShape());
		gameScene.getMapView().getChildren().add(gameEngine.getMapLocatorShape());
		for (Planet planet : gameEngine.getPlanets()) {
			gameScene.getGameView().getChildren().add(planet.getShape());
			gameScene.getMapView().getChildren().add(planet.getMapViewPlanet().getShape());
		}
		gameScene.getGameView().getChildren().add(gameEngine.getBoundaryRectangleShape());
		animation.start();
		primaryStage.setScene(gameScene.createAndReturnScene());
		primaryStage.setTitle("Planet Wars");
		primaryStage.setFullScreen(true);
	}
}
