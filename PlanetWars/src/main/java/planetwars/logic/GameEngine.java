/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import planetwars.database.Database;
import planetwars.database.PlayerDao;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Ship;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */
public class GameEngine implements GameEngineInterface {
	private PlayerDao playerDao;
	private PlanetWarsApplication gui;
	private Player player;
	private Database database;
	private Game game;
	private GameArena gameArena;
	
	public GameEngine(PlanetWarsApplication gui) throws Exception {
		this.database = new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwars.db");
		this.playerDao = new PlayerDao(database);
		this.gui = gui;
	}
	
	public void initializeDatabase() {
		playerDao.createTableIfNotExist();
	}

	public Player getPlayerFromDb(String userName) throws Exception {
		return playerDao.findOne(userName);
	}	

	public void saveGame(Player player) throws Exception {
		playerDao.saveOrUpdate(player);
	}	
	
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

	public void signIn(String textFieldUsername, String textFieldPassword) throws Exception {
		this.player = playerDao.findOne(textFieldUsername);
		if (!player.getPassword().equals(textFieldPassword)) {
			throw new Exception("Wrong password");
		}
	}

	public void newGame(int screenWidth, int screenHeight) {
		this.player = new Player(player.getUsername(), player.getPassword(),
				player.getPoints(), player.getLevel());
		this.gameArena = new GameArena(player.getLevel());
		this.game = new Game(screenWidth, screenHeight, gameArena, player.getPoints());
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
	public GameArena getGameArena() {
		return gameArena;
	}

	public Ship getPlayerShip() {
		return gameArena.getPlayer1Ship();
	}

	public BoundaryRectangle getBoundaryRectangle() {
		return gameArena.getBoundaryRectangle();
	}

	
	public int getPlayer1StartingXCoord() {
		return gameArena.getPlayer1StartingXCoord();
	}

	public int getPlayer1StartingYCoord() {
		return gameArena.getPlayer1StartingYCoord();
	}
	
	@Override
	public Game getGame() {
		return game;
	}

	public long getStartTime() {
		return game.getStartTime();
	}

	public double getTimeLeft() {
		return game.getTimeLeft();
	}
	
	public MapLocator getMapLocator() {
		return game.getMapLocator();
	}

	public long getPoints() {
		return game.getPoints();
	}
}
