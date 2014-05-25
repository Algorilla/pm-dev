package DatabaseConnect;

import java.sql.*;
import javax.swing.*;
/**
 * A class used to connect with sqlite db.
 * @author COMP354 Team B
 * @version 1.0
 *
 */

public class SQLiteDBConnection {
	
	static Connection conn = null;
   
	public static Connection ConnecrDb(){
		
	    try {
	        Class.forName("org.sqlite.JDBC");
	        // change the path of the database file you saved in you pc.
	        conn = DriverManager.getConnection("jdbc:sqlite:./resources/db/ProjectManagerDB.sqlite");
	        // JOptionPane.showMessageDialog(null, "DATABASE Connection Established!");
	        return conn;
	    } catch ( Exception e ) {
	        JOptionPane.showMessageDialog(null, e+"inrrcd");
	      }	      
	      return null;				
	}
	
}
