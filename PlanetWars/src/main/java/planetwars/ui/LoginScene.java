/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import planetwars.logic.GameEngine;

/**
 *
 * @author jaakkpaa
 */
public class LoginScene {
	
	private PlanetWarsApplication application;
	private GameEngine gameEngine;
	
	/**
	 * The class creates the login scene, i.e. the little window at the start
	 * of the application for logging in or signing up a new user.
	 * @param application The main application.
	 * @param gameEngine The application logic interface.
	 * @throws Exception 
	 */
	public LoginScene(PlanetWarsApplication application, 
					GameEngine gameEngine) throws Exception {
		this.application = application;
		this.gameEngine = gameEngine;
	}
	
	/**
	 * Creates the login scene and returns it to the main application.
	 * @return The login scene.
	 * @throws Exception 
	 */
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
