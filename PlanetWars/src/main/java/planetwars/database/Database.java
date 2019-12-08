package planetwars.database;

import java.sql.*;
import java.util.*;

public class Database {

    private String address;

    public Database(String driver, String address) throws Exception {
        Class.forName(driver);
        this.address = address;
    }

	public Connection getConnection() throws Exception {
        return DriverManager.getConnection(this.address);
	}
}
