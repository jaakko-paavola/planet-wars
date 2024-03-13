/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic.graphicobjects;

/**
 *
 * @author jaakkpaa
 */
import javafx.scene.shape.Polygon;

/**
 * Ship is a concrete manifestation of Shape. It represents the player's space ship.
 * @author jaakkpaa
 */
public class Ship extends Shape {

    public Ship(int x, int y) {
        super(new Polygon(-10, -10, 20, 0, -10, 10), x, y);
    }
}
