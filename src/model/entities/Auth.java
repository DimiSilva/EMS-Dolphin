package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Auth extends BaseEntity {
	private String identifier;
	private String password;
	private String accessType;
	
	public Auth (String identifier, String password, String accessType) {
		this.identifier = identifier;
		this.password = password;
		this.accessType = accessType;
	}
	
	public Auth (Integer id, String identifier, String password, String accessType, Date createDate, Date updateDate) {
		this.id = id;
		this.identifier = identifier;
		this.password = password;
		this.accessType = accessType;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Auth fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String identifier = DBSet.getString("identifier");
		String password = DBSet.getString("password");
		String accessType = DBSet.getString("access_type");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Auth(id, identifier, password, accessType, createDate, updateDate);
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getAcessType() {
		return this.accessType;
	}
}
