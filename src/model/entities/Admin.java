package model.entities;

import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Admin extends BaseEntity{
	private String name;
	private String email;
	private String cpf;
	private Integer authId;
	
	public Admin(String name, String email, String cpf, Integer authId) {
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.authId = authId;
	}
	
	public Admin(Integer id, String name, String email, String cpf, Integer authId, Date createDate, Date updateDate) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.authId = authId;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Admin fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String email = DBSet.getString("email");
		String cpf = DBSet.getString("cpf");
		Integer authId = DBSet.getInt("auth_id");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Admin(id, name, email, cpf, authId, createDate, updateDate);
	}
	
	public String getName() {
		return	this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getCPF() {
		return this.cpf;
	}
	
	public Integer getAuthId() {
		return this.authId;
	}
}
