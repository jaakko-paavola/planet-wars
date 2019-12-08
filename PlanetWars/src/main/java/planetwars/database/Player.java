/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.database;

/**
 *
 * @author jaakkpaa
 */
public class Player {
	private String username;
	private String password;
	private int points;
	private int level;

	public Player(String username, String password) {
		this.username = username;
		this.password = password;
		this.points = 0;
		this.level = 1;
	}

	public Player(String username, String password, int points, int level) {
		this.username = username;
		this.password = password;
		this.points = points;
		this.level = level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getPoints() {
		return points;
	}
}
