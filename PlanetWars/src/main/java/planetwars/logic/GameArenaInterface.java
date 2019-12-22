/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import java.util.ArrayList;
import java.util.List;
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

	public Ship getPlayer1Ship();

	public List<Torpedo> getTorpedos();
	
}
