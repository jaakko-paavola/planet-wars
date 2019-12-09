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
	private ranks rank;
	private enum ranks {
		Airman,
		Cadet,
		Ensign,
		Lieutenant,
		Captain,
		Major,
		Colonel
	}

	public ranks getRank() {
		return rank;
	}
	private int points;
	private int level;

	public Player(String username, String password) {
		this.username = username;
		this.password = password;
		this.rank = ranks.Cadet;
		this.points = 0;
		this.level = 1;
	}

	public Player(String username, String password, int points, int level) {
		this.username = username;
		this.password = password;
		this.points = points;
		this.level = level;
		if (points > 36000) {
			this.rank = ranks.Colonel;
		} else if (points > 30000) {
			this.rank = ranks.Major;
		} else if (points > 24000) {
			this.rank = ranks.Captain;
		} else if (points > 18000) {
			this.rank = ranks.Lieutenant;
		} else if (points > 12000) {
			this.rank = rank.Ensign;
		} else if (points > 6000) {
			this.rank = rank.Cadet;
		} else {
			this.rank = rank.Airman;
		}
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
