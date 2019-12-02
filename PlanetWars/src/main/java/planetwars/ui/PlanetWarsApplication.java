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
import javafx.scene.effect.Effect;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.*;
import planetwars.logics.graphicobjects.BoundaryRectangle;
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
	private int points = 0;
	private Text textPoints = new Text(10, 20, "Points: " + points);

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
		textPoints.setFill(Color.WHITE);

		gridPane.add(textCoordinates, 2, 1);
		gridPane.add(textSpeed, 3, 1);
		gridPane.add(textPoints, 4, 1);
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
        planets.add(new Planet(planet1XCoord, planet1YCoord, 10, Color.GREEN));
        planets.add(new Planet(planet2XCoord, planet2YCoord, 40, Color.RED));
        planets.add(new Planet(planet3XCoord, planet3YCoord, 60, Color.BLUE));
        planets.add(new Planet(planet4XCoord, planet4YCoord, 20, Color.YELLOW));
		
        for (Planet planet : planets) {
            gameView.getChildren().add(planet.getShape());
			mapView.getChildren().add(planet.getMapViewPlanet().getShape());
        }
		
		Rectangle rectangle = new Rectangle(0, 0, SPACE_WIDTH, SPACE_HEIGHT);
		BoundaryRectangle boundaryRectangle = new BoundaryRectangle(rectangle, Color.RED);
		boundaryRectangle.getShape().setFill(Color.TRANSPARENT);
		gameView.getChildren().add(boundaryRectangle.getShape());		

        Scene scene = new Scene(rootPane);
        scene.setOnKeyPressed(event -> {
            keysPressed.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            keysPressed.put(event.getCode(), Boolean.FALSE);
        });
        
        new AnimationTimer() {
			private long previousTorpedoFired;
            @Override
            public void handle(long timeNow) {
                if(keysPressed.getOrDefault(KeyCode.LEFT, false)) {
                    player1Ship.turnLeft();
                }

                if(keysPressed.getOrDefault(KeyCode.RIGHT, false)) {
                    player1Ship.turnRight();
                }

			   if(keysPressed.getOrDefault(KeyCode.DOWN, false)) {
					mapLocator.brakeInReferenceTo(player1Ship, 1);
					boundaryRectangle.brakeToOppositeDirectionInReferenceTo(player1Ship, 1);
					
                    for (Planet planet : planets) {
                        planet.brakeToOppositeDirectionInReferenceTo(player1Ship, 1);
                        planet.getMapViewPlanet().brakeToOppositeDirectionInReferenceTo(player1Ship, 1);
                    }
                } 
			   
                if(keysPressed.getOrDefault(KeyCode.UP, false)) {
					mapLocator.accelerateInReferenceTo(player1Ship, 100);
					boundaryRectangle.accelerateToOppositeDirectionInReferenceTo(player1Ship, 100);
					
                    for (Planet planet : planets) {
                        planet.accelerateToOppositeDirectionInReferenceTo(player1Ship, 100);
                        planet.getMapViewPlanet().accelerateToOppositeDirectionInReferenceTo(player1Ship, 100);
                    }
                }

                if (keysPressed.getOrDefault(KeyCode.SPACE, false) && System.currentTimeMillis() - previousTorpedoFired > 1000) {
                    Torpedo torpedo = new Torpedo((int) player1Ship.getShape().getTranslateX(), 
                            (int) player1Ship.getShape().getTranslateY());
                    torpedo.getShape().setRotate(player1Ship.getShape().getRotate());
                    torpedos.add(torpedo);
                    torpedo.accelerateInReferenceTo(player1Ship, 1);
                    torpedo.setMovement(torpedo.getMovement().normalize().multiply(2));
                    gameView.getChildren().add(torpedo.getShape());
					this.previousTorpedoFired = System.currentTimeMillis();
					
					mapLocator.brakeInReferenceTo(player1Ship, 100);
					boundaryRectangle.brakeToOppositeDirectionInReferenceTo(player1Ship, 100);
                    for (Planet planet : planets) {
                        planet.brakeToOppositeDirectionInReferenceTo(player1Ship, 100);
                        planet.getMapViewPlanet().brakeToOppositeDirectionInReferenceTo(player1Ship, 100);
                    }
                }                
                torpedos.forEach(torpedo -> torpedo.move());
                torpedos.forEach(torpedo -> {
                    planets.forEach(planet -> {
                        if(torpedo.collide(planet)) {
							if(planet.isAlive()) {
								torpedo.setAlive(false); planet.setAlive(false);
								gameView.getChildren().remove(planet.getShape());
								mapView.getChildren().remove(planet.getMapViewPlanet().getShape());
								planet.getShape().setOpacity(0.2);
								planet.getMapViewPlanet().getShape().setOpacity(0.2);
								gameView.getChildren().add(planet.getShape());
								mapView.getChildren().add(planet.getMapViewPlanet().getShape());							
								if(planet.isConquered())
									points -= 800;
								else
									points += 800;
							}
                        }
                    });
                });				
                torpedos.stream()
                    .filter(torpedo -> !torpedo.isAlive())
                    .forEach(torpedo -> gameView.getChildren().remove(torpedo.getShape()));
                torpedos.removeAll(torpedos.stream()
                    .filter(torpedo -> !torpedo.isAlive())
                    .collect(Collectors.toList()));

				planets.forEach(planet -> {
					if(player1Ship.collide(planet)) {
						if(planet.isAlive() && !planet.isConquered()) {
							points += 1000;
							planet.setConquered(true);
							gameView.getChildren().remove(planet.getShape());
							mapView.getChildren().remove(planet.getMapViewPlanet().getShape());
							planet.getShape().setStroke(Color.GOLD);
							planet.getShape().setStrokeWidth(5);
							planet.getMapViewPlanet().getShape().setStroke(Color.GOLD);
							planet.getMapViewPlanet().getShape().setStrokeWidth(1);
							gameView.getChildren().add(planet.getShape());
							mapView.getChildren().add(planet.getMapViewPlanet().getShape());
							planet.setConquered(false);					
						}
					}
				});

				textPoints.setText("Points: " + points);
				textSpeed.setText("Speed: " + mapLocator.getXSpeed(player1Ship) + "." + mapLocator.getYSpeed(player1Ship));
				textCoordinates.setText("Coordinates: " + mapLocator.getXCoord() + "." + mapLocator.getYCoord());

				mapLocator.move();
				boundaryRectangle.move();

				if (mapLocator.outOfGameArea()) {
					Text textMessage = new Text(10, 20, "You flew out of the game area");
					textMessage.setFill(Color.RED);
					gridPane.add(textMessage, 1, 1);
					stop();
				}				
                for (Planet planet : planets) {
                    planet.move();
                }
            }
        }.start();
        
        primaryStage.setTitle("Planet Wars");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();    
    }
}
