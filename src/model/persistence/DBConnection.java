package model.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javafx.application.Platform;

public class DBConnection {
	private static Connection conn = null;
	
	public static Connection getConnection () {
		if(conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);				
			}
			catch (SQLException e) {
				System.out.println("An exception occurred when connecting to the db, the application will be closed");
				System.out.println(e.getMessage());
				e.printStackTrace();
				Platform.exit();
				System.exit(0);
			}
			catch (IOException e) {
				System.out.println("An exception occurred when getting application properties, the application will be closed");
				System.out.println(e.getMessage());
				e.printStackTrace();
				Platform.exit();
				System.exit(0);
			}
		}
		
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				System.out.println("An exception occurred when closing the db connection, the application will be closed");
				Platform.exit();
				System.exit(0);
			}
		}
	}
	
	private static Properties loadProperties() throws IOException {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e) {
			throw e;
		}
	}
}
