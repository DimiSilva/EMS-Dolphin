package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.exceptions.InvalidFieldException;

public class Client extends BaseEntity{
	private String name;
	private String phone;
	private String email;
	private String cnpj;
	
	public Client(String name, String phone, String email, String cnpj) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.cnpj = cnpj;
	}

	public Client(Integer id, String name, String phone, String email, String cnpj, Date createDate, Date updateDate) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.cnpj = cnpj;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	
	public static Client fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String phone = DBSet.getString("phone");
		String email = DBSet.getString("email");
		String cnpj = DBSet.getString("cnpj");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		return new Client(id, name, phone, email, cnpj, createDate, updateDate);
	}
	
	public void update(String name, String email, String cnpj, String phone) throws InvalidFieldException {
		boolean valid = true;
		if(valid == true) {		
			this.name = name != null ? name : this.name;
			this.email = email != null ? email : this.email;
			this.cnpj = cnpj != null ? cnpj : this.cnpj;
			this.phone = phone != null ? phone : this.phone;
		}else {
			throw new InvalidFieldException();
		}
	}
	
	public String getName() {
		return	this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getCnpj() {
		return this.cnpj;
	}
	
	public String getPhone() {
		return this.phone;
	}
}
