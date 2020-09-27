package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.interfaces.IBaseUser;

public class Master extends BaseEntity implements IBaseUser {
	public String email;
	public Integer authId;

	public Master(Integer id, String email, Integer authId, Date createDate, Date updateDate) {
		this.id = id;
		this.email = email;
		this.authId = authId;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Master fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String email = DBSet.getString("email");
		Integer authId = DBSet.getInt("auth_id");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Master(id, email, authId, createDate, updateDate);
	}
	
	public String getEmail() {
		return email;
	}
	
	public Integer getAuthId() {
		return authId;
	}
}