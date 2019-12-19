/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.ui;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.*;
import javax.swing.plaf.RootPaneUI;
import planetwars.database.Database;
import planetwars.database.Player;
import planetwars.database.PlayerDao;
import planetwars.logics.graphicobjects.BoundaryRectangle;
import planetwars.logics.Game;
import planetwars.logics.GameArena;
import planetwars.logics.LogicInterface;
import planetwars.logics.graphicobjects.MapLocator;
import planetwars.logics.graphicobjects.Planet;
import planetwars.logics.graphicobjects.Ship;
import planetwars.logics.graphicobjects.Torpedo;
import planetwars.logics.graphicobjects.Shape;

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

	private LogicInterface logicInterface;

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

	@Override
    public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.logicInterface = new LogicInterface(this);
		loginScene = new LoginScene(this, logicInterface);
		primaryStage.setScene(loginScene.createAndReturnScene());
		primaryStage.show();
//		logicInterface.signIn("abc", "abc");
//		initializeGameScene();
    }

	/**
	 * The method resets and sets up the graphic elements and creates a new game
	 * on the level the player is in.
	 * @param username A string containing the player's username, which can be
	 * used as a key to fetch the player's information from the database.
	 */
	public void initializeGameScene() throws Exception {
		logicInterface.createNewGame(screenWidth, screenHeight);
		gameScene = new GameScene(logicInterface);
		this.animation = new Animation(gameScene.getKeysPressed(), logicInterface, gameScene, this);
		
		gameScene.getGameView().getChildren().add(logicInterface.getPlayer1Ship()
				.getShape());
		gameScene.getMapView().getChildren().add(logicInterface.getMapLocator().getShape());
		for (Planet planet : logicInterface.getGameArena().getPlanets()) {
			gameScene.getGameView().getChildren().add(planet.getShape());
			gameScene.getMapView().getChildren().add(planet.getMapViewPlanet().getShape());
		}
		gameScene.getGameView().getChildren().add(logicInterface.getGameArena()
				.getBoundaryRectangle().getShape());

//		primaryStage.close();
		animation.start();
		primaryStage.setScene(gameScene.createAndReturnScene());
		primaryStage.setTitle("Planet Wars");
		primaryStage.setFullScreen(true);
//		primaryStage.show();		
	}
}
