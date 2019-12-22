/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import java.util.List;
import javafx.scene.shape.Shape;
import planetwars.database.Database;
import planetwars.database.PlayerDao;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Ship;
import planetwars.ui.PlanetWarsApplication;

/**
 * The class functions as an interface of the application logic. It controls the
 * communication to the database and communicates information related to the game
 * play, the game areana and the player.
 * @author jaakkpaa
 */
public class GameEngine implements GameEngineInterface {
	private PlayerDao playerDao;
	private PlanetWarsApplication gui;
	private Player player;
	private Database database;
	private GamePlay game;
	private GameArena gameArena;
	
	private final int frameRateForSpeedoMeter = 10000;
	
	public int getFrameRateForSpeedoMeter() {
		return frameRateForSpeedoMeter;
	}
	
	public GameEngine(PlanetWarsApplication gui) throws Exception {
		this.database = new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwars.db");
		this.playerDao = new PlayerDao(database);
		this.gui = gui;
	}
	
	/**
	 * Makes sure the database has the needed table.
	 */
	public void initializeDatabase() {
		playerDao.createTableIfNotExist();
	}

	/**
	 * Communicates the given player from the database.
	 * @param userName The key to find the player.
	 * @return The player founds.
	 * @throws Exception 
	 */
	public Player getPlayerFromDb(String userName) throws Exception {
		return playerDao.findOne(userName);
	}	

	/**
	 * Saves the current player's situation to the database.
	 * @param player
	 * @throws Exception 
	 */
	public void saveGame(Player player) throws Exception {
		playerDao.saveOrUpdate(player);
	}	
	
	/**
	 * Checks if there's already a player with the given username and if not,
	 * creates the username and signs the player in.
	 * @param textFieldUsername The username.
	 * @param textFieldPassword The password.
	 * @throws Exception 
	 */
	public void signUp(String textFieldUsername, String textFieldPassword) throws Exception {
		Player findOne;
		try {
			findOne = playerDao.findOne(textFieldUsername);
		} catch (Exception e) {
			playerDao.saveOrUpdate(new Player(textFieldUsername, textFieldPassword));
			signIn(textFieldUsername, textFieldPassword);
			return;
		}
		throw new Exception("Username already taken.");
	}

	/**
	 * Check if can find a user with the given username and if so, if the password
	 * given matches the one in the database.
	 * @param textFieldUsername The username.
	 * @param textFieldPassword The password.
	 * @throws Exception 
	 */
	public void signIn(String textFieldUsername, String textFieldPassword) throws Exception {
		this.player = playerDao.findOne(textFieldUsername);
		if (!player.getPassword().equals(textFieldPassword)) {
			throw new Exception("Wrong password");
		}
	}

	/**
	 * Starts a new game for the player in an appropriate level.
	 * @param screenWidth
	 * @param screenHeight 
	 */
	public void newGame(int screenWidth, int screenHeight) {
		this.player = new Player(player.getUsername(), player.getPassword(),
				player.getPoints(), player.getLevel());
		this.gameArena = new GameArena(player.getLevel());
		this.game = new GamePlay(screenWidth, screenHeight, gameArena, player.getPoints());
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	public int getPlayerLevel() {
		return player.getLevel();
	}

	public String getPlayerUsername() {
		return player.getUsername();
	}

	public int getPlayerPoints() {
		return player.getPoints();
	}
	
	public Player.Rank getPlayerRank() {
		return player.getRank();
	}

	@Override
	public List<Planet> getPlanets() {
		return gameArena.getPlanets();
	}

	public Ship getPlayerShip() {
		return gameArena.getPlayerShip();
	}
	
	public Shape getPlayerShipShape() {
		return gameArena.getPlayerShip().getShape();
	}

	public BoundaryRectangle getBoundaryRectangle() {
		return gameArena.getBoundaryRectangle();
	}
	
	public Shape getBoundaryRectangleShape() {
		return gameArena.getBoundaryRectangle().getShape();
	}	

	public int getPlayerStartingXCoord() {
		return gameArena.getPlayerStartingXCoord();
	}

	public int getPlayerStartingYCoord() {
		return gameArena.getPlayerStartingYCoord();
	}

	public Shape getMapLocatorShape() {
		return gameArena.getMapLocator().getShape();
	}	
	
	@Override
	public GamePlay getGame() {
		return game;
	}

	public GameArena getGameArena() {
		return gameArena;
	}

	public int getSpaceWidth() {
		return gameArena.getSpaceWidth();
	}

	public int getSpaceHeight() {
		return gameArena.getSpaceHeight();
	}

	public long getStartTime() {
		return game.getStartTime();
	}

	public double getTimeLeft() {
		return game.getTimeLeft();
	}

	public long getPoints() {
		return game.getPoints();
	}
}
