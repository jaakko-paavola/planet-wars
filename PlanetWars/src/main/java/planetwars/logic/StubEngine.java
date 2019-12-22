/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import planetwars.database.Database;
import planetwars.database.PlayerDao;
import planetwars.logic.graphicobjects.Shape;
import planetwars.logic.graphicobjects.Ship;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */
public class StubEngine implements GameEngineInterface {

	private Player player;
	private Game game;
	private StubGameArena gameArena;
	
	public StubEngine() {
		this.player = new Player("mockUser", "mockPassword", 1000, 3);
		this.gameArena = new StubGameArena(player.getLevel(), this);
		this.game = new Game(800, 600, gameArena, player.getPoints());
	}

	public Player getPlayer() {
		return player;
	}

	public Game getGame() {
		return game;
	}

	@Override
	public StubGameArena getGameArena() {
		return gameArena;
	}

	@Override
	public Ship getPlayerShip() {
		return game.getPlayer1Ship();
	}

	@Override
	public Shape getBoundaryRectangle() {
		return gameArena.getBoundaryRectangle();
	}

	@Override
	public long getStartTime() {
		return game.getStartTime();
	}
}
