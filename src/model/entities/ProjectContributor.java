package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectContributor extends BaseEntity {
	private Project project;
	private Contributor contributor;
	
	public ProjectContributor(Project project, Contributor contributor) {
		this.project = project;
		this.contributor = contributor;
	}
	
	public ProjectContributor(Integer id, Project project, Contributor contributor, Date createDate, Date updateDate) {
		this.id = id;
		this.project = project;
		this.contributor = contributor;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static ProjectContributor fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
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
			DBSet.getDate("proejct_end_date"),
			DBSet.getDate("project_create_date"),
			DBSet.getDate("proejct_update_date")
		);
		
		Role contributorRole = new Role(DBSet.getInt("contributor_role_id"), DBSet.getString("contributor_role_name"), DBSet.getFloat("contributor_base_salary"), DBSet.getDate("contributor_create_date"), DBSet.getDate("contributor_role_update_date"));
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
		Date createDate = DBSet.getDate("contributor_create_date");
		Date updateDate = DBSet.getDate("contributor_update_date");
		
		return new ProjectContributor(id, project, contributor, createDate, updateDate);
	}
	
	public Integer getProjectId() {
		return this.project.getId();
	}
	
	public Integer getContributorId() {
		return this.contributor.getId();
	}
}
