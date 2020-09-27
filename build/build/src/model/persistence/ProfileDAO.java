package model.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.exceptions.DBException;

public class ProfileDAO {
	protected Connection conn;
	
	public ProfileDAO() {
		this.conn = DBConnection.getConnection();
	}
	
	public List<ContributorsWorkedHoursInYearByMonth> getAllContributorsWorkedHoursInYearByMonth() throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT * FROM all_contributors_worked_hours_in_year_by_month_view"
						)
				);
			
			List<ContributorsWorkedHoursInYearByMonth> list = new ArrayList<ContributorsWorkedHoursInYearByMonth>();
			while(result.next()) { list.add(ContributorsWorkedHoursInYearByMonth.fromDBSet(result)); }
			
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
