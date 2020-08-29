package model.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	//toDBSet
	// Cada view tem um controller
	// Alterar a lógica de loadScene dos estágios semelhante ao MasterStage
}
