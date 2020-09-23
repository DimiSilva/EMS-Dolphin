package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Role extends BaseEntity{
	private String name;
	private Float baseSalary;
	
	public Role(String name, Float baseSalary) {
		this.name = name;
		this.baseSalary = baseSalary;
	}
	
	public Role(Integer id, String name, Float baseSalary, Date createDate, Date updateDate) {
		this.id = id;
		this.name = name;
		this.baseSalary = baseSalary;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Role fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		Float baseSalary = DBSet.getFloat("base_salary");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Role(id, name, baseSalary, createDate, updateDate);
	}
	
	public void update(String name, Float baseSalary) {
		this.name = name;
		this.baseSalary = baseSalary;
	}
	
	public String getName() {
		return name;
	}
	
	public Float getBaseSalary() {
		return baseSalary;
	}
}