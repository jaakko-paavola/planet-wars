/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics;

import javafx.scene.shape.Shape;
import planetwars.database.Database;
import planetwars.database.Player;
import planetwars.database.PlayerDao;
import planetwars.logics.graphicobjects.*;
import planetwars.ui.Animation;
import planetwars.ui.PlanetWarsApplication;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import planetwars.logics.graphicobjects.BoundaryRectangle;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author jaakkpaa
 */
public class LogicInterface {
	private PlayerDao playerDao;
	private PlanetWarsApplication gui;
	private Game game;
	private GameArena gameArena;
	private Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwars.db");
	private Map<KeyCode, Boolean> keysPressed = new HashMap<>();
	private Player player;

	public LogicInterface(PlanetWarsApplication gui) throws Exception {
		playerDao = new PlayerDao(database);
		playerDao.createTable();
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

	public void saveGame(Player player) throws Exception {
		playerDao.saveOrUpdate(player);
	}

	public void createNewGame(int screenWidth, int screenHeight) {
		this.player = new Player(player.getUsername(), player.getPassword(), player.getPoints(), player.getLevel());
		this.gameArena = new GameArena(player.getLevel());
		this.game = new Game(screenWidth, screenHeight, gameArena, player.getPoints());
	}

	public MapLocator getMapLocator() {
		return game.getMapLocator();
	}

	public GameArena getGameArena() {
		return gameArena;
	}

	public Player.Rank getPlayerRank() {
		return player.getRank();
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

	public BoundaryRectangle getBoundaryRectangle (){
		return gameArena.getBoundaryRectangle();
	} 

	public long getPoints() {
		return game.getPoints();
	}
	
	public void handleArrowKeyPresses(Map<KeyCode, Boolean> keysPressed) {
		if (keysPressed.getOrDefault(KeyCode.LEFT, false)) {
			getPlayer1Ship().turnLeft(1);
		}
		if (keysPressed.getOrDefault(KeyCode.RIGHT, false)) {
			getPlayer1Ship().turnRight(1);
		}
		if (keysPressed.getOrDefault(KeyCode.DOWN, false)) {
			brakeShip();
		}
		if (keysPressed.getOrDefault(KeyCode.UP, false)) {
			accelerateShip();
		}
	}
	
	public Torpedo handleFiringTorpedoBySpaceKeyPress(Map<KeyCode, Boolean> keysPressed) {
		Torpedo torpedo = null;
		if (keysPressed.getOrDefault(KeyCode.SPACE, false) && System.currentTimeMillis()
				- game.getPreviousTorpedoFired() > 200) {
			torpedo = fireTorpedo();
		}
		return torpedo;
	}
	
	public Torpedo fireTorpedo() {
		Torpedo torpedo = new Torpedo((int) game.getPlayer1Ship().getShape().getTranslateX(),
				(int) game.getPlayer1Ship().getShape().getTranslateY());
		torpedo.getShape().setRotate(game.getPlayer1Ship().getShape().getRotate());
		game.getTorpedos().add(torpedo);
		torpedo.accelerateInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		torpedo.setMovement(torpedo.getMovement().normalize().multiply(9));
		game.setPreviousTorpedoFired(System.currentTimeMillis());
		brakeShip();
		return torpedo;
	}
	
	public boolean handleRunningOutOfTime() {
		if (game.getTimeLeft() == 0.0) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Planet> handleTorpedosHittingPlanets() {
		ArrayList<Planet> destroyedPlanets = new ArrayList<Planet>();
		game.getTorpedos().forEach(torpedo -> {
			gameArena.getPlanets().forEach(planet -> {
				if (torpedo.collide(planet)) {
					torpedo.setAlive(false);
					if (planet.isAlive()) {
						planet.setAlive(false);
						destroyedPlanets.add(planet);	
						if (planet.isConquered()) {
							game.setPoints(game.getPoints() - 500);
						} else {
							game.setPoints(game.getPoints() + 500);
							game.setPlanetsLeft(game.getPlanetsLeft() - 1);
						}
					}
				}
			});
		});
		return destroyedPlanets;
	}
	
	
	public void handleTorpedosHittingBoundary() {
		game.getTorpedos().forEach(torpedo -> {
			if (!torpedo.collide(getBoundaryRectangle())) {
				torpedo.setAlive(false);
			}
		});
	}

	public List<Torpedo> removeTorpedosHittingPlanetsOrBoundary() {
		List<Torpedo> torpedosToBeRemoved = game.getTorpedos().stream()
				.filter(torpedo -> !torpedo.isAlive()).collect(Collectors.toList());
		game.getTorpedos().removeAll(game.getTorpedos().stream()
						.filter(torpedo -> !torpedo.isAlive())
						.collect(java.util.stream.Collectors.toList()));
		return torpedosToBeRemoved;
	}
	/**
	 * When the game arena has no more planets left to be conquered or
	 * destroyed, the game ends, the player's level is increased by one, and he
	 * is granted points based on the time left in the timer. Finally, the
	 * player's new situation is saved in the database and a new game with a new
	 * game arena is started.
	 *
	 * @param timeLeft
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public boolean handleNoPlanetsLeft() {
		if (game.getPlanetsLeft() == 0) {
			player.setLevel(player.getLevel() + 1);
			player.setPoints((int) (game.getPoints() + Math.round(game.getTimeLeft() * 10) * 3));
			return true;
		}
		return false;
	}

	public void moveMovableGraphicObjects() {
		game.getTorpedos().forEach(torpedo -> torpedo.move());
		game.getMapLocator().move();
		gameArena.getBoundaryRectangle().move();
		gameArena.getPlanets().forEach(planet -> planet.move());
	}

	public int getPlayer1StartingXCoord() {
		return game.getPlayer1StartingXCoord();
	}

	public int getPlayer1StartingYCoord() {
		return game.getPlayer1StartingYCoord();
	}

	public ArrayList<Planet> handleShipHittingPlanets() {
		ArrayList<Planet> conqueredPlanets = new ArrayList<Planet>();
		gameArena.getPlanets().forEach(planet -> {
			if (game.getPlayer1Ship().collide(planet)) {
				if (planet.isAlive() && !planet.isConquered()) {
					game.setPoints(game.getPoints() + 1000);
					game.setPlanetsLeft(game.getPlanetsLeft() - 1);
					planet.setConquered(true);
					conqueredPlanets.add(planet);	
					planet.setConquered(false);
				}
			}
		});
		return conqueredPlanets;
	}

	public void accelerateShip() {
		game.getMapLocator().accelerateInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		gameArena.getBoundaryRectangle().accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());

		for (Planet planet : gameArena.getPlanets()) {
			planet.accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
			planet.getMapViewPlanet().accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		}
		for (Torpedo torpedo : game.getTorpedos()) {
			torpedo.accelerateToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		}
	}

	public void brakeShip() {
		game.getMapLocator().brakeInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		gameArena.getBoundaryRectangle().brakeToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());

		for (Planet planet : gameArena.getPlanets()) {
			planet.brakeToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
			planet.getMapViewPlanet().brakeToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		}
		for (Torpedo torpedo : game.getTorpedos()) {
			torpedo.brakeToOppositeDirectionInReferenceTo(game.getPlayer1Ship(), player.getPlayer1ShipAcceleration());
		}
	}

	public double getTimePerLevel() {
		return game.getTimePerLevel();
	}
	
	public void handleLevelTimerRefresh(long timeNow) {
		game.setStartTime(getStartTime() == 0 ? timeNow
				: getStartTime());
		game.setTimeLeft(round(game.getTimePerLevel()
				- (timeNow / 1000000000.0 - game.getStartTime() / 1000000000.0), 1));
	}
	
	public static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
	
	public boolean hasFlownOutOfBounds() throws InterruptedException, Exception {
		if (!game.getPlayer1Ship().collide(gameArena.getBoundaryRectangle())) {
			return true;
		}
		return false;
	}
}
