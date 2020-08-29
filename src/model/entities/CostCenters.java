package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CostCenters extends BaseEntity {
	private Float costByHour;
	private String description;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	
	public CostCenters(int id, Float costByHour, String description, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.costByHour = costByHour;
		this.description = description;
		this.createDate = sdf.parse(createDate);
		this.updateDate = sdf.parse(updateDate);
	}
	
	public static CostCenters fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		Float costByHour = DBSet.getFloat("costByHour");
		String description = DBSet.getString("description");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new CostCenters(id, costByHour, description, createDate, updateDate);
	}

	public Float getCostByHour() {
		return costByHour;
	}

	public void setCostByHour(Float costByHour) {
		this.costByHour = costByHour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
