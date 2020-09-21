package model.DTOs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContributorsWorkedHoursInYearByMonthData {
	public Integer month;
	public Integer hours;
	
	
	public ContributorsWorkedHoursInYearByMonthData(Integer month, Integer hours) {
		this.month = month;
		this.hours = hours;
	}
	
	public static ContributorsWorkedHoursInYearByMonthData fromDBSet(ResultSet DBSet) throws SQLException {
		Integer month = DBSet.getInt("month");
		Integer hours = DBSet.getInt("hours");		
		
		return new ContributorsWorkedHoursInYearByMonthData(month, hours);
	}
}