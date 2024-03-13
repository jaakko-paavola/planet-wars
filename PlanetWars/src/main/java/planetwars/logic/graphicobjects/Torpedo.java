/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic.graphicobjects;

import javafx.scene.shape.Polygon;

public class Torpedo extends Shape {

    public Torpedo(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }
}
