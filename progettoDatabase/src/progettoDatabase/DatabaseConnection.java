package progettoDatabase;
import java.sql.*;
import java.util.*;

public class DatabaseConnection {

	private static final String url = "jdbc:mysql://localhost:3306/palestra"
			+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String user = "root";
	private static final String password = "Domenico00!";
	static Connection conn = null;
	
	public static Connection getConnection(){
		try {
			conn = DriverManager.getConnection(url, user, password);
			//System.out.println("CONNESSIONE AL DATABASE AVVENUTA CON SUCCESSO!");
		}catch(Exception e) {
			System.out.println("CONNESSIONE AL DATABASE FALLITA!");
		}
		return conn;
		
	}
	

}
