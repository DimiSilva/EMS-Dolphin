package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth extends BaseEntity {
	public String identifier;
	public String password;
	
	public String accessType;
	
	public String userId;
	
	public Admin admin;
	public Contributor contributor;
	public Master master;
	
	public Auth (int id, String identifier, String password, String accessType, String userId) {
		this.id = id;
		this.identifier = identifier;
		this.password = password;
		this.accessType = accessType;
		this.userId = userId;
	}
	
	public static Auth fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String identifier = DBSet.getString("identifier");
		String password = DBSet.getString("password");
		String accessType = DBSet.getString("access_type");
		String userId = DBSet.getString("userId");
		
		return new Auth(id, identifier, password, accessType, userId);
	}
}
