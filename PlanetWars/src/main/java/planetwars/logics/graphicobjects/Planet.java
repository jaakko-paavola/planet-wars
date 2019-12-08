/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.graphicobjects;

import planetwars.logics.GameArena;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */

public class Planet extends Shape {
	private Planet mapViewPlanet;
	private boolean conquered;

	public Planet getMapViewPlanet() {
		return mapViewPlanet;
	}
	
    public Planet(int x, int y, int size, Color color) {
		super(new Circle(x, y, size), color);
		createMapViewPlanet(x, y, size, color);
	}
	
    public Planet(int x, int y, int size, Color color, boolean mapViewPlanet) {
		super(new Circle(x, y, size), color);
	}

	private void createMapViewPlanet(double x, double y, int size, Color color) {
		mapViewPlanet = new Planet((int) Math.round(x * (1.0 * PlanetWarsApplication.mapWidth / 
				GameArena.spaceWidth)), 
				(int) Math.round(y * (1.0 * PlanetWarsApplication.mapHeight /
					GameArena.spaceHeight)), size / 10, color, true);
	}

	public void setConquered(boolean conquered) {
		this.conquered = true;
	}

	public boolean isConquered() {
		return conquered;
	}
}
