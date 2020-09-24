package model.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.exceptions.DBException;

import model.persistence.ContributorDAO;
import model.persistence.ProjectDAO;

public class Appointment extends BaseEntity {

	private String initDate;
	private String endDate;
	private String description;
	private Contributor contributor;
	private Project project;
	
	public Appointment(Integer id, String description, String initDate, String endDate, Contributor contributor, Project project, String createDate, String updateDate) {
		this.id = id;
		this.description = description;
		this.initDate = initDate;
		this.endDate = endDate;
		this.contributor = contributor;
		this.project = project;
	}
	
	public static Appointment fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		System.out.println(id);
		String description = DBSet.getString("description");
		String initDate = DBSet.getString("init_date");
		String endDate = DBSet.getString("end_date");
		
		String contributorId = DBSet.getString("contributor_id");
		String projectId = DBSet.getString("project_id");
		
		Contributor contributor = null;
		Project project = null;
		
		ContributorDAO contributorDAO = new ContributorDAO();
		try {
			contributor = contributorDAO.getById(contributorId);
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*ProjectDAO projectDAO = new ProjectDAO();
		try {
			project = projectDAO.getById(projectId);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
		String createDate = DBSet.getString("create_date");
		String updateDate = DBSet.getString("update_date");
		
		return new Appointment(id, description,  initDate, endDate, contributor, project, createDate, updateDate);
	}	
	
	
	
	public Integer getId() {
		return	this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getInitDate() {
		return this.initDate;
	}
	
	public String getEndDate() {
		return this.endDate;
	}
	public Contributor getContributor() {
		return this.contributor;
	}
	public Project getProject() {
		return this.project;
	}
}
