package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment extends BaseEntity {
	private Integer initHour;
	private Integer endHour;
	private Date initDate;
	private Date endDate;
	
	public Appointment(Integer id, Integer initHour, Integer endHour, String initDate, String endDate, String createDate, String updateDate) throws ParseException {
		this.id = id;
		this.initHour = initHour;
		this.endHour = endHour;
	}
	
	public static Appointment fromDBSet(ResultSet DBSet) throws SQLException, ParseException {
		int id = DBSet.getInt("id");
		Integer initHour = DBSet.getInt("initHour");
		Integer endHour = DBSet.getInt("endHour");
		String initDate = DBSet.getString("initDate");
		String endDate = DBSet.getString("endDate");
		String createDate = DBSet.getString("createDate");
		String updateDate = DBSet.getString("updateDate");
		
		return new Appointment(id, initHour, endHour, initDate, endDate, createDate, updateDate);
	}	
}
