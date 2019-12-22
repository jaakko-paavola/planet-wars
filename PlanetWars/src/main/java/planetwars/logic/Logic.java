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
public interface Logic {

	void accelerateShip(int acceleration);

	Engine getGameEngine();

	public void handleArrowKeyPresses(Map<KeyCode, Boolean> eq);
	
}
