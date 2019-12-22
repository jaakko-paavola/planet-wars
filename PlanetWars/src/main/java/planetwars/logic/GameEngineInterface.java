/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
