package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CostCenter extends BaseEntity {
	private Float costByHour;
	private String description;
	
	public CostCenter(Integer id, Float costByHour, String description, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.costByHour = costByHour;
		this.description = description;
	}
	
	public static CostCenter fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		Float costByHour = DBSet.getFloat("costByHour");
		String description = DBSet.getString("description");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new CostCenter(id, costByHour, description, createDate, updateDate);
	}
}
