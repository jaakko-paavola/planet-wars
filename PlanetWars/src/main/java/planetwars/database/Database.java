package planetwars.database;

import java.sql.*;
import java.util.*;

/**
 * The class provides database connection functionality.
 * @author jaakkpaa
 */
public class Database {

    private String address;

	/**
	 * Sets up the database with the given parameters for the driver and address.
	 * @param driver
	 * @param address
	 * @throws Exception 
	 */
    public Database(String driver, String address) throws Exception {
        Class.forName(driver);
        this.address = address;
    }

	/**
	 * Tries to connect the driver to a database in the given address.
	 * @return
	 * @throws Exception 
	 */

	public Connection getConnection() throws Exception {
        return DriverManager.getConnection(this.address);
	}
}
