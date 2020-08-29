package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Clients extends BaseEntity{
	private String name;
	private String phone;
	private String email;
	private String company;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	
	public Clients(int id, String name, String phone, String email, String company, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.company = company;
		this.createDate = sdf.parse(createDate);
		this.updateDate = sdf.parse(updateDate);
	}
	
	public static Clients fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String phone = DBSet.getString("phone");
		String email = DBSet.getString("email");
		String company = DBSet.getString("company");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Clients(id, name, phone, email, company, createDate, updateDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
	
}
