package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Project extends BaseEntity {
	private String name;
	private String description;
	private Date initDate;
	private Date endDate;
	
	public Project(Integer id, String name, String description, String initDate, String endDate, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public static Project fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String description = DBSet.getString("description");
		String initDate = DBSet.getString("initDate");
		String endDate = DBSet.getString("endDate");
		String createDate = DBSet.getString("create_date");
		String updateDate = DBSet.getString("update_date");
		
		return new Project(id, name, description, initDate, endDate, createDate, updateDate);
	}
}
