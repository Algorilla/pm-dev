package Controller;

import java.sql.*;
import javax.swing.*;

/**
 * A class used to connect with sqlite db.
 * 
 * @author Jun Hui Chen
 */

public class SQLiteDBConnection {

	static Connection conn = null;

	/**
	 * Connection to SQLite DB file
	 * 
	 * @return A database connection
	 */
	public static Connection ConnectDb() {

		try {
			Class.forName("org.sqlite.JDBC");
			// change the path of the database file you saved in you pc.
			conn = DriverManager
					.getConnection("jdbc:sqlite:./resources/db/pm-DB-NEW.sqlite");
			// JOptionPane.showMessageDialog(null,
			// "DATABASE Connection Established!");
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "inrrcd");
		}
		return null;
	}

}
