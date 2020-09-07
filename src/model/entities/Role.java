package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Role extends BaseEntity{
	private String name;
	private Float baseSalary;
	
	public Role(Integer id, String name, Float baseSalary, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.name = name;
		this.baseSalary = baseSalary;
	}
	
	public static Role fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		Float baseSalary = DBSet.getFloat("baseSalary");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Role(id, name, baseSalary, createDate, updateDate);
	}
}