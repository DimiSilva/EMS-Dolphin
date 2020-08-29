package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Projects extends BaseEntity {
	private String name;
	private String description;
	private Date initDate;
	private Date endDate;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
	
	public Projects(int id, String name, String description, String initDate, String endDate, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.name = name;
		this.description = description;
		this.initDate = sdf2.parse(initDate);
		this.endDate = sdf2.parse(endDate);
		this.createDate = sdf.parse(createDate);
		this.updateDate = sdf.parse(updateDate);	
	}
	
	public static Projects fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String description = DBSet.getString("description");
		String initDate = DBSet.getString("initDate");
		String endDate = DBSet.getString("endDate");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Projects(id, name, description, initDate, endDate, createDate, updateDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
