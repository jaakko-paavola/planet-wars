/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic;

import java.util.List;

import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Shape;
import planetwars.logic.graphicobjects.Ship;

/**
 *
 * @author jaakkpaa
 */
public class StubEngine implements GameEngineInterface {

	private Player player;
	private GamePlay game;
	private StubGameArena gameArena;
	
	public StubEngine() {
		this.player = new Player("mockUser", "mockPassword", 1000, 3);
		this.gameArena = new StubGameArena(player.getLevel());
		this.game = new GamePlay(800, 600, gameArena, player.getPoints());
	}

	public Player getPlayer() {
		return player;
	}

	public GamePlay getGame() {
		return game;
	}

	@Override
	public List<Planet> getPlanets() {
		return gameArena.getPlanets();
	}

	@Override
	public Ship getPlayerShip() {
		return gameArena.getPlayerShip();
	}

	@Override
	public Shape getBoundaryRectangle() {
		return gameArena.getBoundaryRectangle();
	}

	@Override
	public long getStartTime() {
		return game.getStartTime();
	}

	@Override
	public int getFrameRateForSpeedoMeter() {
		return 10000;
	}

	@Override
	public GameArenaInterface getGameArena() {
		return gameArena;
	}
}
