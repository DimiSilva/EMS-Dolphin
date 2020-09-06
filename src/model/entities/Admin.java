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

	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	public Admin(int id, String name, String email, String cpf, Date createDate, Date updateDate) throws ParseException {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Admin fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String email = DBSet.getString("email");
		String cpf = DBSet.getString("cpf");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Admin(id, name, email, cpf, createDate, updateDate);
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	
}
