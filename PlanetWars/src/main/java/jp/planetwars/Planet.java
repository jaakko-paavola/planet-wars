/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.planetwars;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 *
 * @author jaakkpaa
 */

public class Planet extends Shape {
       public Planet(int x, int y, int size) {
           super(new Circle(x, y, size));
    } 
}
