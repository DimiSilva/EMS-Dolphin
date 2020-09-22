package model.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DTOs.ContributorsWorkedHoursInYearByMonthData;
import model.DTOs.ProjectsWorkedHours;
import model.exceptions.DBException;

public class DashboardDAO {
	protected Connection conn;
	
	public DashboardDAO() {
		this.conn = DBConnection.getConnection();
	}
	
	public List<ContributorsWorkedHoursInYearByMonthData> getAllContributorsWorkedHoursInYearByMonth() throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT * FROM all_contributors_worked_hours_in_year_by_month_view"
						)
				);
			
			List<ContributorsWorkedHoursInYearByMonthData> list = new ArrayList<ContributorsWorkedHoursInYearByMonthData>();
			while(result.next()) { list.add(ContributorsWorkedHoursInYearByMonthData.fromDBSet(result)); }
			
			statement.close();
			result.close();
			
			return list ;
		}
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DBException();
		}
	}
	
	public List<ProjectsWorkedHours> getAllProjectsWorkedHours() throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT * FROM all_projects_worked_hours_view"
						)
				);
			
			List<ProjectsWorkedHours> list = new ArrayList<ProjectsWorkedHours>();
			while(result.next()) { list.add(ProjectsWorkedHours.fromDBSet(result)); }

			statement.close();
			result.close();
			
			return list;
		}
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DBException();
		}
	}
}
