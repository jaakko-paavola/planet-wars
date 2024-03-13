/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic.graphicobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author jaakkpaa
 */
public class BoundaryRectangle extends Shape {
	public BoundaryRectangle(Color color, int spaceWidth, int spaceHeight) {
		super(new Rectangle(0, 0, spaceWidth, spaceHeight), color);
	}
}
