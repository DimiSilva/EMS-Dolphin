package model.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Appointment extends BaseEntity {

	private LocalDateTime initDate;
	private LocalDateTime endDate;
	private String description;
	private Contributor contributor;
	private Project project;
	
	public Appointment(String description, LocalDateTime initDate, LocalDateTime endDate, Contributor contributor, Project project) {
		this.description = description;
		this.initDate = initDate;
		this.endDate = endDate;
		this.contributor = contributor;
		this.project = project;
	}
	
	public Appointment(Integer id, String description, LocalDateTime initDate, LocalDateTime endDate, Contributor contributor, Project project, Date createDate, Date updateDate) {
		this.id = id;
		this.description = description;
		this.initDate = initDate;
		this.endDate = endDate;
		this.contributor = contributor;
		this.project = project;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Appointment fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String description = DBSet.getString("description");
		LocalDateTime initDate = DBSet.getTimestamp("init_date").toLocalDateTime();
		LocalDateTime endDate = DBSet.getTimestamp("end_date").toLocalDateTime();
		CostCenter projectCostCenter = new CostCenter(
			DBSet.getInt("project_cost_center_id"),
			DBSet.getString("project_cost_center_name"),
			DBSet.getString("project_cost_center_description"),
			DBSet.getDate("project_cost_center_create_date"),
			DBSet.getDate("project_cost_center_update_date")
		);
		Client projectClient = new Client(
			DBSet.getInt("project_client_id"),
			DBSet.getString("project_client_name"),
			DBSet.getString("project_client_phone"),
			DBSet.getString("project_client_email"),
			DBSet.getString("project_client_cnpj"),
			DBSet.getDate("project_client_create_date"),
			DBSet.getDate("project_client_update_date")
		);
		Project project = new Project(
			DBSet.getInt("project_id"),
			DBSet.getString("project_name"),
			DBSet.getString("project_description"),
			projectCostCenter,
			projectClient,
			DBSet.getDate("project_init_date"),
			DBSet.getDate("project_end_date"),
			DBSet.getDate("project_create_date"),
			DBSet.getDate("project_update_date")
		);
			
		Role contributorRole = new Role(DBSet.getInt("contributor_role_id"), DBSet.getString("contributor_role_name"), DBSet.getFloat("contributor_role_base_salary"), DBSet.getDate("contributor_create_date"), DBSet.getDate("contributor_role_update_date"));
		CostCenter contributorCostCenter = new CostCenter(DBSet.getInt("contributor_cost_center_id"), DBSet.getString("contributor_cost_center_name"), DBSet.getString("contributor_cost_center_description"), DBSet.getDate("contributor_cost_center_create_date"), DBSet.getDate("contributor_cost_center_update_date"));
		Contributor contributor = new Contributor(
			DBSet.getInt("contributor_id"),
			DBSet.getString("contributor_name"),
			DBSet.getString("contributor_phone"),
			DBSet.getString("contributor_address"),
			DBSet.getString("contributor_email"),
			DBSet.getDate("contributor_birthdate"),
			DBSet.getString("contributor_cpf"),
			DBSet.getInt("contributor_auth_id"),
			contributorRole,
			contributorCostCenter,
			DBSet.getDate("contributor_create_date"),
			DBSet.getDate("contributor_update_date")
		);
		
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Appointment(id, description,  initDate, endDate, contributor, project, createDate, updateDate);
	}	
	
	public void update(String description, LocalDateTime initDate, LocalDateTime endDate, Contributor contributor, Project project) {
		this.description = description;
		this.initDate = initDate;
		this.endDate = endDate;
		this.contributor = contributor;
		this.project = project;
	}
	
	public Integer getId() {
		return	this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public LocalDateTime getInitDate() {
		return this.initDate;
	}
	
	public LocalDateTime getEndDate() {
		return this.endDate;
	}
	
	public String getClientName() {
		return this.project.getClient().getName();
	}
	
	public Project getProject() {
		return this.project;
	}
	
	public String getProjectName() {
		return this.project.getName();
	}
	
	public Contributor getContributor() {
		return this.contributor;
	}
	
	public String getContributorName() {
		return this.contributor.getName();
	}
	
	public Long getTotalHours() {
		return ChronoUnit.HOURS.between(initDate, endDate);
	}
}
