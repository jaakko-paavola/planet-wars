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
public interface GameEngineInterface {

	GameArenaInterface getGameArena();
	
	GamePlay getGame();

	Player getPlayer();

	public Ship getPlayerShip();

	public Shape getBoundaryRectangle();

	public long getStartTime();

	public int getFrameRateForSpeedoMeter();

	public List<Planet> getPlanets();
	
}
