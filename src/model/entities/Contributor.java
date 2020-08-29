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
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
	
	public Contributor(int id, String name, String phone, String address, String email, String birthDate, String cpf, String createDate, String updateDate) throws ParseException{
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.birthdate = sdf2.parse(birthDate);
		this.cpf = cpf;
		this.createDate = sdf.parse(createDate);
		this.updateDate = sdf.parse(updateDate);
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
}
