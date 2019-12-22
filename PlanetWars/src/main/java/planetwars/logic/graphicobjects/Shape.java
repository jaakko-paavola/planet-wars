/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package planetwars.logic.graphicobjects;

/**
 * The class provides features and functionality to all graphical objects in the
 * game.
 * @author jaakkpaa
 */
import planetwars.ui.PlanetWarsApplication;
import java.util.Timer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import static planetwars.ui.PlanetWarsApplication.mapWidth;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 * All the graphic objects in the game inherit Shape, which contains the basic
 * qualities and functions of all graphic objects.
 * @author jaakkpaa
 */
public abstract class Shape {
	
	private javafx.scene.shape.Shape shape;
	private Point2D movement;
	private Point2D previous;
	private int visits = 0;
	private boolean alive = true;

	public Shape(Polygon polygon, double x, double y) {
		this.shape = polygon;
		this.shape.setTranslateX(x);
		this.shape.setTranslateY(y);
		this.shape.setFill(Color.WHITE);
		
		this.movement = new Point2D(0, 0);
		this.previous = new Point2D(0, 0);
	}
	
	public Shape(Circle circle, Color color) {
		this.shape = circle;
		this.shape.setTranslateX(circle.getCenterX());
		this.shape.setTranslateY(circle.getCenterY());
		this.shape.setFill(color);
		this.shape.setStroke(color);
		this.shape.setStrokeWidth(1);
		
		this.movement = new Point2D(0, 0);
		this.previous = new Point2D(0, 0);
	}
	
	public Shape(Rectangle rectangle, Color color) {
		this.shape = rectangle;
		this.shape.setTranslateX(rectangle.getX());
		this.shape.setTranslateY(rectangle.getY());
		this.shape.setStroke(color);
		
		this.movement = new Point2D(0, 0);
		this.previous = new Point2D(0, 0);
	}
	
	public javafx.scene.shape.Shape getShape() {
		return shape;
	}

	public void setShape(javafx.scene.shape.Shape shape) {
		this.shape = shape;
	}
	
	/**
	 * Turn the object to the left.
	 * @param playerRotationFactor 
	 */
	public void turnLeft(int playerRotationFactor) {
		this.shape.setRotate(this.shape.getRotate() - playerRotationFactor);
	}
	
	/**
	 * Turn the object to the right.
	 * @param playerRotationFactor 
	 */
	public void turnRight(int playerRotationFactor) {
		this.shape.setRotate(this.shape.getRotate() + playerRotationFactor);
	}

	/**
	 * Sets the object into movement in reference to the player's ship.
	 * @param reference The player's ship.
	 * @param playerAccelerationFactor
	 * @param accelerationFactor
	 * @param frameRateForSpeedoMeter 
	 */
	public void accelerateInReferenceTo(Shape reference, int playerAccelerationFactor, 
					double accelerationFactor, int frameRateForSpeedoMeter) {
		double changeX = Math.cos(Math.toRadians(reference.getShape().getRotate()));
		double changeY = Math.sin(Math.toRadians(reference.getShape().getRotate()));
		
		changeX *= accelerationFactor * playerAccelerationFactor;
		changeY *= accelerationFactor * playerAccelerationFactor;
		
		this.setMovement(this.getMovement().add(changeX, changeY), frameRateForSpeedoMeter);
	}
	
	/**
	 * Move the object.
	 */
	public void move() {
		this.shape.setTranslateX(this.shape.getTranslateX() + this.getMovement().getX());
		this.shape.setTranslateY(this.shape.getTranslateY() + this.getMovement().getY());
	}
	
	public int getXCoord() {
		return (int) Math.round(this.shape.getTranslateX());
	}
	
	public int getYCoord() {
		return (int) Math.round(this.shape.getTranslateY());
	}
	
	public int getXSpeed(Shape reference) {
		return (int) Math.round((this.movement.getX() - this.previous.getX()) * 1000);
	}
	
	public int getYSpeed(Shape reference) {
		return (int) Math.round((this.movement.getY() - this.previous.getY()) * 1000);
	}
	
	public Point2D getMovement() {
		return movement;
	}
	
	/**
	 * Set the objects movement and enable the functioning of the speed-o-meter.
	 * @param movement
	 * @param frameRateForSpeedoMeter 
	 */
	public void setMovement(Point2D movement, int frameRateForSpeedoMeter) {
		if (visits == frameRateForSpeedoMeter) {
			this.previous = this.movement;
			visits = 0;
		}
		this.movement = movement;
		visits++;
	}
	
	/**
	 * Check if the given object is overlapping with the one given as a parameter.
	 * @param counterparty
	 * @return 
	 */
    public boolean collide(Shape counterparty) {
        javafx.scene.shape.Shape collisionArea = javafx.scene.shape.Shape.intersect(this.shape, counterparty.getShape());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}