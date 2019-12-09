/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.graphicobjects;

/**
 *
 * @author jaakkpaa
 */
import javafx.scene.shape.Polygon;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 * Ship is a concrete manifestation of Shape. It represents the player's space ship.
 * @author jaakkpaa
 */
public class Ship extends Shape {

    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }
}
