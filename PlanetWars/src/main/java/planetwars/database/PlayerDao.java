/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import sun.security.util.Password;

/**
 * The class provides database access functionality for the Player class. 
 * @author jaakkpaa
 */
public class PlayerDao implements Dao<Player, String> {
	private Database database;
			
	public PlayerDao(Database database) {
		this.database = database;
	}
		
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
	public void createTable() throws SQLException, Exception {
		Connection conn = database.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(
							"SELECT * FROM Player");
			ResultSet executeQuery = stmt.executeQuery();
		} catch (Exception e) {
			PreparedStatement stmt = conn.prepareStatement(
							"CREATE TABLE Player (username varchar, password varchar, "
							+ "points integer, level integer)");
			stmt.executeUpdate();
		}
		conn.close();
	}
}
