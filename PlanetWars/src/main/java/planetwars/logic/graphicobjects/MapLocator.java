/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic.graphicobjects;

import static planetwars.ui.PlanetWarsApplication.mapHeight;
import static planetwars.ui.PlanetWarsApplication.mapWidth;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import planetwars.logic.GameArenaInterface;
import planetwars.ui.PlanetWarsApplication;

/**
 *
 * @author jaakkpaa
 */
public class MapLocator extends Shape {
	private GameArenaInterface gameArena;
	
    public MapLocator(Ship player1Ship, GameArenaInterface gameArena) {
	    super(new Rectangle((player1Ship.getXCoord() / gameArena.getSpaceWidth()) * mapWidth, 
			(player1Ship.getYCoord() / gameArena.getSpaceHeight()) * mapHeight, 
			(int) Math.round((1.0 * screenWidth / gameArena.getSpaceWidth()) * mapWidth), 
			(int) Math.round((1.0 * screenHeight / gameArena.getSpaceHeight()) * mapHeight))
			, Color.WHITE);
		this.gameArena = gameArena;
    } 	
	
	/**
	 * Sets the movement for the map locator rectangle appropriately in relation 
	 * to the reference, the players ship.
	 * @param reference The player's ship.
	 * @param playerAccelerationFactor
	 * @param accelerationFactor
	 * @param frameRateForSpeedoMeter 
	 */
	@Override
    public void accelerateInReferenceTo(Shape reference, int playerAccelerationFactor, double accelerationFactor, int frameRateForSpeedoMeter) {
        double changeX = Math.cos(Math.toRadians(reference.getShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(reference.getShape().getRotate()));

        changeX *= accelerationFactor * playerAccelerationFactor * (1.0 * PlanetWarsApplication.mapWidth /
				gameArena.getSpaceWidth());
        changeY *= accelerationFactor * playerAccelerationFactor * (1.0 * PlanetWarsApplication.mapHeight /
				gameArena.getSpaceHeight());

        this.setMovement(this.getMovement().add(changeX, changeY), frameRateForSpeedoMeter);
    }
}
