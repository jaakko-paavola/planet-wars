/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.database;

import planetwars.logic.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sun.security.util.Password;

/**
 * The class provides database access functionality for the Player class. 
 * @author jaakkpaa
 */
public class PlayerDao implements Dao<Player, String> {
	private Database database;
			
	/**
	 * Sets up a PlayerDao object with regards to the given database.
	 * @param database 
	 */
	public PlayerDao(Database database) {
		this.database = database;
	}
		
	/**
	 * Finds the user with the username from database. If does not find, throws
	 * and exception.
	 * @param username The username to be looked for.
	 * @return A player object with the data from the database for given username.
	 * @throws SQLException
	 * @throws Exception 
	 */
	@Override
    public Player findOne(String username) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
							"Select * FROM Player WHERE username = ?");
			stmt.setString(1, username);
			ResultSet result = stmt.executeQuery();
			if (!result.next()) {
				throw new Exception("Wrong username.");
			}
			return new Player(result.getString("username"), result.getString("password"),
					result.getInt("points"), result.getInt("level"));
		}
    }

	/**
	 * Saves player's information into the database as a new record or updates
	 * an existing record in the database.
	 * @param player The Player object with the player's information.
	 * @throws SQLException
	 * @throws Exception 
	 */
	@Override
    public void saveOrUpdate(Player player) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
							"SELECT * FROM Player WHERE username = ?");
			stmt.setString(1, player.getUsername());
			ResultSet result = stmt.executeQuery();
			if (!result.next()) {
				stmt = conn.prepareStatement(
					"INSERT INTO Player (username, password, points, level) VALUES (?, ?, ?, ?)");
				stmt.setString(1, player.getUsername());
				stmt.setString(2, player.getPassword());
				stmt.setInt(3, player.getPoints());
				stmt.setInt(4, player.getLevel());
				stmt.executeUpdate();
			} else {
				stmt = conn.prepareStatement(
						"UPDATE Player SET points = ?, level = ? WHERE username = ?");
				stmt.setInt(1, player.getPoints());
				stmt.setInt(2, player.getLevel());
				stmt.setString(3, player.getUsername());
				stmt.executeUpdate();
			}
        }
    }
	
	@Override
	public void createTableIfNotExist() {
		try (Connection conn = database.getConnection()) {

			conn.prepareStatement("CREATE TABLE IF NOT EXISTS Player ("
					+ " id IDENTITY,"
					+ " username VARCHAR(100) UNIQUE,"
					+ " password VARCHAR(100),"
					+ " points INTEGER,"
					+ " level INTEGER,"
					+ " PRIMARY KEY(id)"
					+ ");").executeUpdate();

			conn.prepareStatement("CREATE INDEX IF NOT EXISTS player_idx"
					+ " ON Player (id);").executeUpdate();

			conn.close();
		} catch (Exception ex) {
			Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void delete(String username) throws SQLException, Exception {
		try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
					"Delete FROM Player WHERE username = ?");
			stmt.setString(1, username);
			stmt.executeUpdate();
		}		
	}

	/**
	 * Checks if Player table exists and creates it if it does not.
	 * @throws SQLException
	 * @throws Exception 
	 */
//	@Override
//	public void createTable() throws SQLException, Exception {
//		Connection conn = database.getConnection();
//		try {
//			PreparedStatement stmt = conn.prepareStatement(
//							"SELECT * FROM Player");
//			ResultSet executeQuery = stmt.executeQuery();
//		} catch (Exception e) {
//			PreparedStatement stmt = conn.prepareStatement(
//							"CREATE TABLE Player (username varchar, password varchar, "
//							+ "points integer, level integer)");
//			stmt.executeUpdate();
//		}
//		conn.close();
//	}
}
