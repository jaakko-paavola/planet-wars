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
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.*;
import planetwars.logics.graphicobjects.MapLocator;
import planetwars.logics.graphicobjects.Planet;
import planetwars.logics.graphicobjects.Ship;
import planetwars.logics.graphicobjects.Torpedo;
import planetwars.logics.graphicobjects.Shape;

/**
 *
 * @author jaakkpaa
 */
public class PlanetWarsApplication extends Application{
    
	public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = (int) resolution.getWidth();
	public static int screenHeight = (int) resolution.getHeight(); 
	public static int mapWidth = 300;
	public static int mapHeight = 300;
	public static int SPACE_WIDTH = 10000;
	public static int SPACE_HEIGHT = 10000;
	
    private Map<KeyCode, Boolean> keysPressed = new HashMap<>();  
    private Point2D movement = new Point2D(1, 0) {};

	private Text textCoordinates = new Text(10, 20, "Coordinates: ");
	private Text textSpeed = new Text(10, 20, "Speed: ");

    private Ship player1Ship;
    private List<Torpedo> torpedos;
    private ArrayList<Planet> planets;
	private MapLocator mapLocator;
	
	// These are here temporarily for testing planets until map creation will be
	// moved to its own class.
	public static int planet1XCoord = screenWidth/8;
	public static int planet2XCoord = screenWidth/4;
	public static int planet3XCoord = screenWidth/2;
	public static int planet4XCoord = screenWidth/5;

	public static int planet1YCoord = screenHeight/4;
	public static int planet2YCoord = screenHeight/9;
	public static int planet3YCoord = screenHeight/5;
	public static int planet4YCoord = screenHeight/2;
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 *
	 * @param primaryStage
	 * @throws Exception
	 */
	@Override
    public void start(Stage primaryStage) throws Exception {
		AnchorPane rootPane = new AnchorPane();
        rootPane.setPrefSize(SPACE_WIDTH, SPACE_HEIGHT);
		GridPane gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setStyle("-fx-background-color: black; -fx-border-color: red");		
		gridPane.setMinWidth(screenWidth);

		Pane gameView = new Pane();
		gameView.setStyle("-fx-background-color: black");
		gameView.setPrefSize(screenWidth, screenHeight);
		textCoordinates.setFill(Color.WHITE);
		textSpeed.setFill(Color.WHITE);

		gridPane.add(textCoordinates, 2, 1);
		gridPane.add(textSpeed, 3, 1);
		gridPane.setAlignment(Pos.CENTER);

		Pane mapView = new Pane();
		mapView.setPrefSize(mapWidth, mapHeight);
		mapView.setStyle("-fx-background-color: black; -fx-border-color: green");
		rootPane.getChildren().addAll(gameView, gridPane, mapView);
		
        player1Ship = new Ship(Math.round(screenWidth/2), Math.round(screenHeight/2));
        gameView.getChildren().add(player1Ship.getShape());
		mapLocator = new MapLocator(player1Ship);
		mapView.getChildren().add(mapLocator.getShape());

        torpedos = new ArrayList<>();

        planets = new ArrayList<>();
        planets.add(new Planet(planet1XCoord, planet1YCoord, 10, Color.GREEN, false));
        planets.add(new Planet(planet2XCoord, planet2YCoord, 40, Color.RED, false));
        planets.add(new Planet(planet3XCoord, planet3YCoord, 60, Color.BLUE, false));
        planets.add(new Planet(planet4XCoord, planet4YCoord, 20, Color.YELLOW, false));

        for (Planet planet : planets) {
            gameView.getChildren().add(planet.getShape());
			mapView.getChildren().add(planet.getMapViewPlanet().getShape());
        }

        Scene scene = new Scene(rootPane);
        scene.setOnKeyPressed(event -> {
            keysPressed.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            keysPressed.put(event.getCode(), Boolean.FALSE);
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long timeNow) {
                if(keysPressed.getOrDefault(KeyCode.LEFT, false)) {
                    player1Ship.turnLeft();
                }

                if(keysPressed.getOrDefault(KeyCode.RIGHT, false)) {
                    player1Ship.turnRight();
                }

			   if(keysPressed.getOrDefault(KeyCode.DOWN, false)) {
					mapLocator.brakeInReferenceTo(player1Ship);
                    for (Planet planet : planets) {
                        planet.brakeToOppositeDirectionInReferenceTo(player1Ship);
                        planet.getMapViewPlanet().brakeToOppositeDirectionInReferenceTo(player1Ship);
                    }
                } 
			   
                if(keysPressed.getOrDefault(KeyCode.UP, false)) {
					mapLocator.accelerateInReferenceTo(player1Ship);
                    for (Planet planet : planets) {
                        planet.accelerateToOppositeDirectionInReferenceTo(player1Ship);
                        planet.getMapViewPlanet().accelerateToOppositeDirectionInReferenceTo(player1Ship);
                    }
                }
                
                if (keysPressed.getOrDefault(KeyCode.SPACE, false) && torpedos.size() < 3) {
                    Torpedo torpedo = new Torpedo((int) player1Ship.getShape().getTranslateX(), 
                            (int) player1Ship.getShape().getTranslateY());
                    torpedo.getShape().setRotate(player1Ship.getShape().getRotate());
                    torpedos.add(torpedo);
                    torpedo.accelerateInReferenceTo(player1Ship);
                    torpedo.setMovement(torpedo.getMovement().normalize().multiply(2));
                    gameView.getChildren().add(torpedo.getShape());
                }                
				
				textSpeed.setText("Speed: " + mapLocator.getXSpeed(player1Ship) + "." + mapLocator.getYSpeed(player1Ship));
				textCoordinates.setText("Coordinates: " + mapLocator.getXCoord() + "." + mapLocator.getYCoord());

				mapLocator.move();

				if (mapLocator.outOfGameArea()) {
					Text textMessage = new Text(10, 20, "You flew out of the game area");
					textMessage.setFill(Color.RED);
					gridPane.add(textMessage, 1, 1);
					stop();
				}				
                for (Planet planet : planets) {
                    planet.move();
                }
 
                torpedos.forEach(torpedo -> torpedo.move());
            }
        }.start();
        
        primaryStage.setTitle("Planet Wars");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();    
    }
}
