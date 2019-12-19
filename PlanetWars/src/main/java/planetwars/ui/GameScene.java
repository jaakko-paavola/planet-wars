/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.ui;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import planetwars.database.Player;
import planetwars.logics.*;
import static planetwars.ui.PlanetWarsApplication.mapHeight;
import static planetwars.ui.PlanetWarsApplication.mapWidth;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class GameScene {
	private Text textMessage = new Text(10, 20, "");
	private Text textCoordinates = new Text(10, 20, "Coordinates: 0.0");
	private Text textSpeed = new Text(10, 20, "Speed: 0.0");
	private Text textPoints = new Text(10, 20, "Points: 0");
	private Text textTimer = new Text(10, 20, "Time left: 0");
	private Text textPlayerName = new Text(10, 20, "Pilot name: ");
	private Text textLevel = new Text(10, 20, "Pilot rank: ");
	private Map<KeyCode, Boolean> keysPressed;

	public Map<KeyCode, Boolean> getKeysPressed() {
		return keysPressed;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage.setText(textMessage);
	}

	public void setTextCoordinates(String textCoordinates) {
		this.textCoordinates.setText(textCoordinates);
	}

	public void setTextSpeed(String textSpeed) {
		this.textSpeed.setText(textSpeed);
	}

	public void setTextPoints(String textPoints) {
		this.textPoints.setText(textPoints);
	}

	public void setTextTimer(String textTimer) {
		this.textTimer.setText(textTimer);
	}

	public void setTextPlayerName(String textPlayerName) {
		this.textPlayerName.setText(textPlayerName);
	}

	public void setTextLevel(String textLevel) {
		this.textLevel.setText(textLevel);
	}

	private planetwars.logics.LogicInterface logicInterface;
	private Pane gameView;
	private Pane mapView;
	private GridPane gridPane;

	public GameScene(planetwars.logics.LogicInterface logicInterface) {
		this.logicInterface = logicInterface;
		gameView = new Pane();
		mapView = new Pane();
		gridPane = new GridPane();
		this.keysPressed = new HashMap<KeyCode, Boolean>(); 
	}

	public Scene createAndReturnScene() {
		textMessage.setFill(Color.RED);
		textMessage.setText("Radio: \"" + logicInterface.getPlayerRank()
				+ ", your mission is to conquer or destroy the "
				+ (logicInterface.getPlayerLevel() == 1 ? "planet" : 
						logicInterface.getPlayerLevel() + " planets")
				+ " in this solar system.\"");
		textPlayerName.setText("Pilot: " + logicInterface.getPlayerUsername());
		textPlayerName.setFill(Color.WHITE);
		textLevel.setText("Level: " + Integer.toString(logicInterface.getPlayerLevel()));
		textLevel.setFill(Color.WHITE);
		textCoordinates.setFill(Color.WHITE);
		textSpeed.setFill(Color.WHITE);
		textPoints.setFill(Color.WHITE);
		textPoints.setText("Points: " + logicInterface.getPlayerPoints());
		textTimer.setFill(Color.WHITE);

		gridPane.setHgap(20);
		gridPane.setStyle("-fx-background-color: black; -fx-border-color: red");
		gridPane.setMinWidth(screenWidth);
		gridPane.add(textMessage, 1, 1);
		gridPane.add(textPlayerName, 2, 1);
		gridPane.add(textLevel, 3, 1);
		gridPane.add(textCoordinates, 4, 1);
		gridPane.add(textSpeed, 5, 1);
		gridPane.add(textPoints, 6, 1);
		gridPane.add(textTimer, 7, 1);
		gridPane.setAlignment(Pos.CENTER);
		gameView.setPrefSize(screenWidth, screenHeight);
		gameView.setStyle("-fx-background-color: black");
		mapView.setPrefSize(mapWidth, mapHeight);
		mapView.setStyle("-fx-background-color: black; -fx-border-color: green");
		AnchorPane rootPane = new AnchorPane();
		rootPane.setPrefSize(logicInterface.getGameArena().getSpaceWidth(), 
				logicInterface.getGameArena().getSpaceHeight());
		rootPane.getChildren().addAll(gameView, gridPane, mapView);
		Scene gameScene = new Scene(rootPane);
		
		gameScene.setOnKeyPressed(key -> {
			onKeyPressed(key);
		});

		gameScene.setOnKeyReleased(key -> {
			onKeyReleased(key);
		});

		return gameScene;
	}

	public Pane getGameView() {
		return gameView;
	}

	public Pane getMapView() {
		return mapView;
	}
	
	public void onKeyPressed(KeyEvent key) {
		keysPressed.put(key.getCode(), Boolean.TRUE);
	}

	public void onKeyReleased(KeyEvent key) {
		keysPressed.put(key.getCode(), Boolean.FALSE);
	}
}
