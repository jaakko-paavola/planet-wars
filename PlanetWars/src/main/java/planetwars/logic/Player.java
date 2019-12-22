/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

/**
 * The class handles all information regarding the player: username, password,
 * rank, points and level.
 * 
 * @author jaakkpaa
 */
public class Player {
	private String username;
	private String password;
	private Rank rank;
	public enum Rank {
		Airman,
		Cadet,
		Ensign,
		Lieutenant,
		Captain,
		Major,
		Colonel
	}

	private int playerShipAcceleration = 3;
	private int playerTorpedoAcceleration = 3;
	private int playerTorpedoSpeedMultiplier = 1000;

	public int getPlayerTorpedoAcceleration() {
		return playerTorpedoAcceleration;
	}

	public int getPlayerTorpedoSpeedMultiplier() {
		return playerTorpedoSpeedMultiplier;
	}
	private int playerShipBraking = -1;

	public int getPlayerShipBraking() {
		return playerShipBraking;
	}
	private int playerShipRotationSpeed = 1;

	public int getPlayerShipRotationSpeed() {
		return playerShipRotationSpeed;
	}

	public int getPlayerShipAcceleration() {
		return playerShipAcceleration;
	}
	public Rank getRank() {
		return rank;
	}
	private int points;
	private int level;

	/**
	 * Sets up a fresh player starting from zero.
	 * @param username Player's username.
	 * @param password Player's password.
	 */
	public Player(String username, String password) {
		this.username = username;
		this.password = password;
		this.rank = Rank.Airman;
		this.points = 0;
		this.level = 1;
	}

	/**
	 * Creates a new, updated player profile with updated points, level and rank.
	 * @param username Player's username.
	 * @param password Player's password.
	 * @param points Player's new points.
	 * @param level Player's new level.
	 */
	public Player(String username, String password, int points, int level) {
		this.username = username;
		this.password = password;
		this.points = points;
		this.level = level;
		if (points > 36000) {
			this.rank = Rank.Colonel;
		} else if (points > 30000) {
			this.rank = Rank.Major;
		} else if (points > 24000) {
			this.rank = Rank.Captain;
		} else if (points > 18000) {
			this.rank = Rank.Lieutenant;
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
