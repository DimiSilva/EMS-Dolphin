
package model.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.entities.Admin;
import model.interfaces.IDAO;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminDAO extends IDAO {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	
	ObservableList<Admin> observableAdminList = FXCollections.observableArrayList();
	
	public Admin getByIdentifier(String identifier) throws ParseException {
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					"SELECT * FROM admin "
					+ "WHERE identifier = " 
							+ "'" + identifier + "'"
				);
			
			result.first();
			
			return Admin.fromDBSet(result);
		}
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void toDBSet(String name, String email, String cpf) {
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement statement = conn.createStatement();
			statement.executeUpdate(
					"INSERT INTO admin (name, email, cpf, createDate, updateDate)"
					+ "VALUES('" + name 
					+ "', '" + email 
					+ "', '" + cpf 
					+ "', '" + sdf.format(new Date()) 
					+ "', '" + sdf.format(new Date()) 
					+ "')");
			
		}
		catch (SQLException e) {
			System.out.println("Insert ERROR:");
			System.out.println(e.getMessage());
		}
	}
	
	public ObservableList<Admin> getAll() throws ParseException {
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery("SELECT * FROM admin");
			
			while(result.next()) {
				observableAdminList.add(new Admin(result.getInt("id"), 
						result.getString("name"), 
						result.getString("email"),
						result.getString("cpf"),
						result.getString("createDate"),
						result.getString("updateDate")));
			}
			
			
			return observableAdminList;
			
		}
		catch (SQLException e) {
			System.out.println("Insert ERROR:");
			System.out.println(e.getMessage());
			return null;
		}
	}
	//toDBSet
	// Cada view tem um controller
	// Alterar a lógica de loadScene dos estágios semelhante ao MasterStage
}
