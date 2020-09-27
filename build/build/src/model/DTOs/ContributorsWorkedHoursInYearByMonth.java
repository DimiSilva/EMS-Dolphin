package model.DTOs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContributorsWorkedHoursInYearByMonth {
	public Integer month;
	public Integer hours;
	
	
	public ContributorsWorkedHoursInYearByMonth(Integer month, Integer hours) {
		this.month = month;
		this.hours = hours;
	}
	
	public static ContributorsWorkedHoursInYearByMonth fromDBSet(ResultSet DBSet) throws SQLException {
		Integer month = DBSet.getInt("month");
		Integer hours = DBSet.getInt("hours");		
		
		return new ContributorsWorkedHoursInYearByMonth(month, hours);
	}
}