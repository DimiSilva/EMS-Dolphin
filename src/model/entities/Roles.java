package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Roles extends BaseEntity{
	private String name;
	private Float baseSalary;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	
	public Roles(int id, String name, Float baseSalary, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.name = name;
		this.baseSalary = baseSalary;
		this.createDate = sdf.parse(createDate);
		this.updateDate = sdf.parse(updateDate);
	}
	
	public static Roles fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		Float baseSalary = DBSet.getFloat("baseSalary");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Roles(id, name, baseSalary, createDate, updateDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Float baseSalary) {
		this.baseSalary = baseSalary;
	}
	
	
	
	
	
	
	
}