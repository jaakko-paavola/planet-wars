/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import planetwars.logic.graphicobjects.Shape;
import planetwars.logic.graphicobjects.Ship;

/**
 *
 * @author jaakkpaa
 */
public interface GameEngineInterface {

	Game getGame();

	GameArenaInterface getGameArena();

	Player getPlayer();

	public Ship getPlayerShip();

	public Shape getBoundaryRectangle();

	public long getStartTime();
	
}
