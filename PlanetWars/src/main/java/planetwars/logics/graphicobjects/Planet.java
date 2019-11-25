/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.graphicobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import planetwars.ui.PlanetWarsApplication;
import static planetwars.ui.PlanetWarsApplication.planet2XCoord;
import static planetwars.ui.PlanetWarsApplication.planet2YCoord;

/**
 *
 * @author jaakkpaa
 */

public class Planet extends Shape {
	private Planet mapViewPlanet;

	public Planet getMapViewPlanet() {
		return mapViewPlanet;
	}
	
    public Planet(int x, int y, int size, Color color, boolean isMapViewPlanet) {
		super(new Circle(x, y, size), color);
		if (!isMapViewPlanet) {
			mapViewPlanet = new Planet((int) Math.round(x * (1.0 * PlanetWarsApplication.mapWidth / 
					PlanetWarsApplication.SPACE_WIDTH)), 
					(int) Math.round(y * (1.0 * PlanetWarsApplication.mapWidth /
						PlanetWarsApplication.SPACE_WIDTH)), size / 10, color, true);
		}
	}
}
