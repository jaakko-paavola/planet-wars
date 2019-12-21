/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import planetwars.database.Database;
import planetwars.database.Player;
import planetwars.database.PlayerDao;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Ship;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */
public class GameEngine {
	private PlayerDao playerDao;
	private PlanetWarsApplication gui;
	private Player player;
	private Database database;
	private Game game;
	private GameArena gameArena;

	public Player getPlayer() {
		return player;
	}

	public Game getGame() {
		return game;
	}

	public GameArena getGameArena() {
		return gameArena;
	}
	
	public GameEngine(PlanetWarsApplication gui) throws Exception {
		this.database = new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwars.db");
		this.playerDao = new PlayerDao(database);
		this.gui = gui;
	}
	
	public void signUp(String textFieldUsername, String textFieldPassword) throws Exception {
		playerDao.saveOrUpdate(new Player(textFieldUsername, textFieldPassword));
		signIn(textFieldUsername, textFieldPassword);
	}

	public void signIn(String textFieldUsername, String textFieldPassword) throws Exception {
		this.player = playerDao.findOne(textFieldUsername);
		if (!player.getPassword().equals(textFieldPassword)) {
			gui.getPrimaryStage().setTitle("Wrong password");
		}
	}

	public Player getPlayer(String userName) throws Exception {
		return playerDao.findOne(userName);
	}
	public MapLocator getMapLocator() {
		return game.getMapLocator();
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

	public long getStartTime() {
		return game.getStartTime();
	}

	public double getTimeLeft() {
		return game.getTimeLeft();
	}

	public Ship getPlayer1Ship() {
		return game.getPlayer1Ship();
	}

	public BoundaryRectangle getBoundaryRectangle() {
		return gameArena.getBoundaryRectangle();
	}

	public long getPoints() {
		return game.getPoints();
	}

	public void saveGame(Player player) throws Exception {
		playerDao.saveOrUpdate(player);
	}

	public void newGame(int screenWidth, int screenHeight) {
		this.player = new Player(player.getUsername(), player.getPassword(), 
				player.getPoints(), player.getLevel());
		this.gameArena = new GameArena(player.getLevel());
		this.game = new Game(screenWidth, screenHeight, gameArena, player.getPoints());
	}	

	public Player.Rank getPlayerRank() {
		return player.getRank();
	}
	
	public int getPlayer1StartingXCoord() {
		return game.getPlayer1StartingXCoord();
	}

	public int getPlayer1StartingYCoord() {
		return game.getPlayer1StartingYCoord();
	}
}
