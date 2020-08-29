package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Apointments extends BaseEntity {
	private Integer initHour;
	private Integer endHour;
	private Date initDate;
	private Date endDate;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
	
	public Apointments(int id, Integer initHour, Integer endHour, String initDate, String endDate, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.initHour = initHour;
		this.endHour = endHour;
		this.initDate = sdf2.parse(initDate);
		this.endDate = sdf2.parse(endDate);
		this.createDate = sdf.parse(createDate);
		this.updateDate = sdf.parse(updateDate);
	}
	
	public static Apointments fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		Integer initHour = DBSet.getInt("initHour");
		Integer endHour = DBSet.getInt("endHour");
		String initDate = DBSet.getString("initDate");
		String endDate = DBSet.getString("endDate");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Apointments(id, initHour, endHour, initDate, endDate, createDate, updateDate);
	}

	public Integer getInitHour() {
		return initHour;
	}

	public void setInitHour(Integer initHour) {
		this.initHour = initHour;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
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
