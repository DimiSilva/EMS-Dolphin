package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth extends BaseEntity {
	public String identifier;
	public String password;
	
	public String accessType;
	

	
	public Admin admin;
	public Contributor contributor;
	public Master master;
	
	public Auth (int id, String identifier, String password, String accessType) {
		this.id = id;
		this.identifier = identifier;
		this.password = password;
		this.accessType = accessType;

	}
	
	public static Auth fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String identifier = DBSet.getString("identifier");
		String password = DBSet.getString("password");
		String accessType = DBSet.getString("access_type");
		
		return new Auth(id, identifier, password, accessType);
	}
}
