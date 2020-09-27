package model.DTOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ContributorRunningProject {
	public String projectName;
	public Date initDate;
	public Date endDate;
	
	
	public ContributorRunningProject(String projectName, Date initDate, Date endDate) {
		this.projectName = projectName;
		this.initDate = initDate;
		this.endDate = endDate;
	}
	
	public static ContributorRunningProject fromDBSet(ResultSet DBSet) throws SQLException {
		String projectName = DBSet.getString("name");
		Date initDate = DBSet.getDate("init_date");
		Date endDate = DBSet.getDate("end_date");
		
		return new ContributorRunningProject(projectName, initDate, endDate);
	}
}