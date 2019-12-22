/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import planetwars.database.Database;
import planetwars.database.PlayerDao;
import planetwars.logic.Player;
import planetwars.logic.LogicLayer;
import planetwars.logic.Game;
import planetwars.logic.GameEngine;

/**
 *
 * @author jaakkpaa
 */
public class LoginScene {
	
	private PlanetWarsApplication application;
	private GameEngine gameEngine;
	
	public LoginScene(PlanetWarsApplication application, 
			GameEngine gameEngine) throws Exception {
		this.application = application;
		this.gameEngine = gameEngine;
	}
	
	public Scene createAndReturnScene() throws Exception {
		GridPane paneSignIn = new GridPane();
		Text textUsername = new Text("Username");	
		TextField textFieldUsername = new TextField();	
		Text textPassword = new Text("Password");	
		TextField textFieldPassword = new TextField();	
		Button buttonSignIn = new Button("Sign in");
		Button buttonSignUp = new Button("Sign up");

		paneSignIn.add(textUsername, 1, 1);
		paneSignIn.add(textFieldUsername, 2, 1);
		paneSignIn.add(textPassword, 1, 2);
		paneSignIn.add(textFieldPassword, 2, 2);
		paneSignIn.add(buttonSignIn, 1, 3);
		paneSignIn.add(buttonSignUp, 3, 3);

		gameEngine.initializeDatabase();

		buttonSignUp.setOnAction(click -> {
 			try {
				gameEngine.signUp(textFieldUsername.getText(), 
						textFieldPassword.getText());
			} catch (Exception ex) {
				application.getPrimaryStage().setTitle(ex.getMessage());
				return;
			}
			try {
				application.initializeGameScene();
			} catch (Exception ex) {
				Logger.getLogger(LoginScene.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		
		buttonSignIn.setOnAction((click) -> {
 			try {
				gameEngine.signIn(textFieldUsername.getText(), 
						textFieldPassword.getText());
				try {
					application.initializeGameScene();
				} catch (Exception ex) {
					Logger.getLogger(LoginScene.class.getName()).log(Level.SEVERE, null, ex);
				}
			} catch (Exception ex) {
				application.getPrimaryStage().setTitle(ex.getMessage());
			}
		});	
		application.getPrimaryStage().setTitle("Planet Wars login");
		return new Scene(paneSignIn);
	}
}
