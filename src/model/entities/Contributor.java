package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Contributor extends BaseEntity{
	private String name;
	private String phone;
	private String address;
	private String email;
	private Date birthdate;
	private String cpf;

	public Contributor(Integer id, String name, String phone, String address, String email, String birthDate, String cpf, String createDate, String updateDate) throws ParseException{
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.cpf = cpf;
	}
	
	public static Contributor fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String phone = DBSet.getString("phone");
		String address = DBSet.getString("address");
		String email = DBSet.getString("email");
		String birthDate = DBSet.getString("birthDate");
		String cpf = DBSet.getString("cpf");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Contributor(id, name, phone, address, email, birthDate, cpf, createDate, updateDate);
	}
}
