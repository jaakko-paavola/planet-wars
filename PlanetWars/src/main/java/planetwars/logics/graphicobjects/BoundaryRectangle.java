/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.graphicobjects;

import planetwars.logics.GameArena;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */
public class BoundaryRectangle extends Shape {
	public BoundaryRectangle(Color color) {
		super(new Rectangle(0, 0, GameArena.spaceWidth, GameArena.spaceHeight), color);
	}
}
