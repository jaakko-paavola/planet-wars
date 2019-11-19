/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.planetwars;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.*;

/**
 *
 * @author jaakkpaa
 */
public class PlanetWarsApplication extends Application{
    
    public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = (int) resolution.getWidth();
    public static int height = (int) resolution.getHeight(); 
    public static int WIDTH = width;
    public static int HEIGHT = height;
    private Map<KeyCode, Boolean> keysPressed = new HashMap<>();  
    private Point2D movement = new Point2D(1, 0) {};
    private Ship player1Ship;
    List<Torpedo> torpedos;
    ArrayList<Planet> planets;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        
        player1Ship = new Ship(WIDTH/2, HEIGHT/2);
        torpedos = new ArrayList<>();
        planets = new ArrayList<>();
        planets.add(new Planet(WIDTH/8, HEIGHT/4, 10));
        planets.add(new Planet(WIDTH/4, HEIGHT/9, 40));
        planets.add(new Planet(WIDTH/2, HEIGHT/5, 60));
        planets.add(new Planet(WIDTH/5, HEIGHT/2, 20));
        
        pane.getChildren().add(player1Ship.getShape());
        for (Planet planet : planets) {
            pane.getChildren().add(planet.getShape());
        }
        Scene scene = new Scene(pane);
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
                
                if(keysPressed.getOrDefault(KeyCode.UP, false)) {
                    for (Planet planet : planets) {
                        planet.accelerate(player1Ship);
                    }
                }
                
                if (keysPressed.getOrDefault(KeyCode.SPACE, false) && torpedos.size() < 3) {
                    Torpedo torpedo = new Torpedo((int) player1Ship.getShape().getTranslateX(), 
                            (int) player1Ship.getShape().getTranslateY());
                    torpedo.getShape().setRotate(player1Ship.getShape().getRotate());
                    torpedos.add(torpedo);
                    torpedo.accelerate(player1Ship);
                    torpedo.setMovement(torpedo.getMovement().normalize().multiply(2));
                    pane.getChildren().add(torpedo.getShape());
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
