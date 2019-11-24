/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars;

/**
 *
 * @author jaakkpaa
 */
import javafx.scene.shape.Polygon;

public class Torpedo extends Shape {

    public Torpedo(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }

}
