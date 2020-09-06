package model.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.entities.Auth;
import model.interfaces.IDAO;

public class AuthDAO extends IDAO {
	public Auth getByIdentifier(String identifier) {
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					"SELECT * FROM auth "
					+ "WHERE identifier = " 
							+ "'" + identifier + "'"
				);
			result.first();
			
			return Auth.fromDBSet(result);
		}
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	// Inserir o auth e retornar o id
	public int insert(String identifier,String password,String accessType) {
		int resultSet = 0;
		try {
			Connection conn = DBConnection.getConnection();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			Statement statement = conn.createStatement();
			resultSet = statement.executeUpdate(
					"INSERT INTO admin (identifier, password, accessType, create_date, update_date)"
					+ "VALUES('" + identifier 
					+ "', '" + password 
					+ "', '" + accessType  
					+ "', '" + dateFormat.format(new Date())
					+ "', '" + dateFormat.format(new Date())
					+ "')");
			
			
			
		}
		catch (SQLException e) {
			System.out.println("Insert ERROR:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return resultSet;
	}
	//toDBSet
	// Cada view tem um controller
	// Alterar a lógica de loadScene dos estágios semelhante ao MasterStage
}
