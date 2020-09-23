package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CostCenter extends BaseEntity {
	private String name;
	private String description;
	
	public CostCenter(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public CostCenter(Integer id, String name, String description, Date createDate, Date updateDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static CostCenter fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String description = DBSet.getString("description");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new CostCenter(id, name, description, createDate, updateDate);
	}
	
	public void update (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
}
