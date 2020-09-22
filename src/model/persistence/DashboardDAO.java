package model.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DTOs.ContributorRunningProject;
import model.DTOs.ContributorWorkedHoursInYearByMonth;
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.exceptions.DBException;

public class DashboardDAO {
	protected Connection conn;
	
	public DashboardDAO() {
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
	
	public ContributorWorkedHoursInYearByMonth getContributorWorkedHoursInYearByMonth(Integer contributorId) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"CALL get_contributor_worked_hours_in_year_by_month(%d)"
								, contributorId
						)
				);
			
			if(!result.next()) return null;
			ContributorWorkedHoursInYearByMonth instance = ContributorWorkedHoursInYearByMonth.fromDBSet(result);

			statement.close();
			result.close();
			
			return instance;
		}
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DBException();
		}
	}
	
	public List<ProjectsWorkedHours> getContributorWorkedHoursByProject(Integer contributorId) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"CALL get_contributor_worked_hours_by_project(%d)"
								, contributorId
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
	
	public List<ContributorRunningProject> getContributorRunningProjects(Integer contributorId) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"CALL get_contributor_running_projects(%d)"
								, contributorId
						)
				);
			
			List<ContributorRunningProject> list = new ArrayList<ContributorRunningProject>();
			while(result.next()) { list.add(ContributorRunningProject.fromDBSet(result)); }

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
