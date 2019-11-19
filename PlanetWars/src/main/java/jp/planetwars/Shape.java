/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.planetwars;

/**
 *
 * @author jaakkpaa
 */
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public abstract class Shape {

    private javafx.scene.shape.Shape shape;
    private Point2D movement;

    public Shape(Polygon polygon, int x, int y) {
        this.shape = polygon;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);

        this.movement = new Point2D(0, 0);
    }

    public Shape(Circle circle) {
        this.shape = circle;
        this.shape.setTranslateX(circle.getCenterX());
        this.shape.setTranslateY(circle.getCenterY());

        this.movement = new Point2D(0, 0);
    }

    public javafx.scene.shape.Shape getShape() {
        return shape;
    }
    
    public void turnLeft() {
        this.shape.setRotate(this.shape.getRotate() - 2);
    }

    public void turnRight() {
        this.shape.setRotate(this.shape.getRotate() + 2);
    }

    public void move() {
        this.shape.setTranslateX(this.shape.getTranslateX() + this.getMovement().getX());
        this.shape.setTranslateY(this.shape.getTranslateY() + this.getMovement().getY());
    }

    public void accelerate(Shape reference) {
        double changeX = Math.cos(Math.toRadians(reference.getShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(reference.getShape().getRotate()));

        changeX *= -0.005;
        changeY *= -0.005;

        this.setMovement(this.getMovement().add(changeX, changeY));
    }

    public Point2D getMovement() {
        return movement;
    }

    
    public void setMovement(Point2D movement) {
        this.movement = movement;
    }
}