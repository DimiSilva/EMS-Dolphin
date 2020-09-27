package model.DTOs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectsWorkedHours {
	public String projectName;
	public Integer hours;
	
	
	public ProjectsWorkedHours(String projectName, Integer hours) {
		this.projectName = projectName;
		this.hours = hours;
	}
	
	public static ProjectsWorkedHours fromDBSet(ResultSet DBSet) throws SQLException {
		String projectName = DBSet.getString("name");
		Integer hours = DBSet.getInt("hours");		
		
		return new ProjectsWorkedHours(projectName, hours);
	}
}