package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Client extends BaseEntity{
	private String name;
	private String phone;
	private String email;
	private String company;

	public Client(Integer id, String name, String phone, String email, String company, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.company = company;
	}
	
	public static Client fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String phone = DBSet.getString("phone");
		String email = DBSet.getString("email");
		String company = DBSet.getString("company");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Client(id, name, phone, email, company, createDate, updateDate);
	}
}
