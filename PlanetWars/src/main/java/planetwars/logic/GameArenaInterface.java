/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic;

import java.util.ArrayList;
import java.util.List;

import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Shape;
import planetwars.logic.graphicobjects.Ship;
import planetwars.logic.graphicobjects.Torpedo;

/**
 *
 * @author jaakkpaa
 */
public interface GameArenaInterface {

	ArrayList<Planet> getPlanets();

	public int getSpaceWidth();

	public int getSpaceHeight();

	public Shape getBoundaryRectangle();

	public Ship getPlayerShip();

	public List<Torpedo> getTorpedos();

	public MapLocator getMapLocator();
	
}
