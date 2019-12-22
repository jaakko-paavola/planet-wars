/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import java.util.Map;
import javafx.scene.input.KeyCode;

/**
 *
 * @author jaakkpaa
 */
public class StubLogic implements Logic {

	private StubEngine gameEngine;
	
	public StubLogic() {
		gameEngine = new StubEngine();
	}

	@Override
	public void accelerateShip(int acceleration) {
	}

	@Override
	public Engine getGameEngine() {
		return gameEngine;
	}

	@Override
	public void handleArrowKeyPresses(Map<KeyCode, Boolean> eq) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
