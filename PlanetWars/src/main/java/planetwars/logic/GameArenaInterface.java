/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import java.util.ArrayList;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Shape;

/**
 *
 * @author jaakkpaa
 */
public interface GameArenaInterface {

	ArrayList<Planet> getPlanets();

	public int getSpaceWidth();

	public int getSpaceHeight();

	public Shape getBoundaryRectangle();
	
}
