
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entities.ProjectContributor;
import model.exceptions.DBException;

public class ProjectContributorDAO extends BaseDAO<ProjectContributor> {	
	public ProjectContributorDAO() {
		super("project_contributor");
	}

	@Override
	public ProjectContributor entityFromDBSet(ResultSet DBSet) throws SQLException {
		return ProjectContributor.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(ProjectContributor object) {
		return String.format(
				"INSERT INTO %s "
						+ "(project_id, contributor_id) "
						+ "VALUES "
						+ "('%d', '%d')"
							, tableName
							, object.getProjectId()
							, object.getContributorId()
					);
	}

	@Override
	public String entityToDBupdateString(ProjectContributor object) {
		return null;
	}
	
	@Override
	public List<ProjectContributor> getPaged(int page, int perPage) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT "
							+ "p.id as project_id, "
							+ "p.name as project_name, "
							+ "p.description as project_description, "
							+ "p.init_date as project_init_date, "
							+ "p.end_date as project_end_date, "
							+ "p.create_date as project_create_date, "
							+ "p.update_date as project_update_date, "
							+ "cc.id as project_cost_center_id, "
							+ "cc.name as project_cost_center_name, "
							+ "cc.description as project_cost_center_description, "
							+ "cc.create_date as project_cost_center_create_date, "
							+ "cc.update_date as project_cost_center_update_date, "
							+ "cl.id as project_client_id, "
							+ "cl.name as project_client_name, "
							+ "cl.phone as project_client_phone, "
							+ "cl.email as project_client_email, "
							+ "cl.cnpj as project_client_cnpj, "
							+ "cl.create_date as project_client_create_date, "
							+ "cl.update_date as project_client_update_date, "
							+ "c.id as contributor_id, "
							+ "c.name as contributor_name, "
							+ "c.email as contributor_email, "
							+ "c.cpf as contributor_cpf, "
							+ "c.phone as contributor_phone, "
							+ "c.address as contributor_address, "
							+ "c.birthdate as contributor_birthdate, "
							+ "c.auth_id as contributor_auth_id, "
							+ "c.create_date as contributor_create_date, "
							+ "c.update_date as contributor_update_date, "
							+ "r.id as contributor_role_id, "
							+ "r.name as contributor_role_name, "
							+ "r.base_salary as contributor_role_base_salary, "
							+ "r.create_date as contributor_role_create_date, "
							+ "r.update_date as contributor_role_update_date, "
							+ "ccc.id as contributor_cost_center_id, "
							+ "ccc.name as contributor_cost_center_name,"
							+ "ccc.description as contributor_cost_center_description, "
							+ "ccc.create_date as contributor_cost_center_create_date, "
							+ "ccc.update_date as contributor_cost_center_update_date "
							+ "FROM %s pc "
							+ "INNER JOIN project p "
							+ "ON p.id = pc.project_id "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = p.cost_center_id "
							+ "INNER JOIN client cl "
							+ "ON cl.id = p.client_id "
							+ "INNER JOIN contributor c "
							+ "ON c.id = pc.contributor_id "
							+ "INNER JOIN role r "
							+ "ON r.id = c.role_id "
							+ "INNER JOIN cost_center ccc "
							+ "ON ccc.id = c.cost_center_id"
							+ "LIMIT %d, %d"
								, tableName
								, page * perPage - perPage
								, perPage
						)
					);

			List<ProjectContributor> list = new ArrayList<ProjectContributor>();
			
			while(result.next()) { list.add(entityFromDBSet(result)); }
			
			result.close();
			statement.close();
			
			return list;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public ProjectContributor getByContributorId(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT pc.*, "
							+ "p.id as project_id, "
							+ "p.name as project_name, "
							+ "p.description as project_description, "
							+ "p.init_date as project_init_date, "
							+ "p.end_date as project_end_date, "
							+ "p.create_date as project_create_date, "
							+ "p.update_date as project_update_date, "
							+ "cc.id as project_cost_center_id, "
							+ "cc.name as project_cost_center_name, "
							+ "cc.description as project_cost_center_description, "
							+ "cc.create_date as project_cost_center_create_date, "
							+ "cc.update_date as project_cost_center_update_date, "
							+ "cl.id as project_client_id, "
							+ "cl.name as project_client_name, "
							+ "cl.phone as project_client_phone, "
							+ "cl.email as project_client_email, "
							+ "cl.cnpj as project_client_cnpj, "
							+ "cl.create_date as project_client_create_date, "
							+ "cl.update_date as project_client_update_date, "
							+ "c.id as contributor_id, "
							+ "c.name as contributor_name, "
							+ "c.email as contributor_email, "
							+ "c.cpf as contributor_cpf, "
							+ "c.phone as contributor_phone, "
							+ "c.address as contributor_address, "
							+ "c.birthdate as contributor_birthdate, "
							+ "c.auth_id as contributor_auth_id, "
							+ "c.create_date as contributor_create_date, "
							+ "c.update_date as contributor_update_date, "
							+ "r.id as contributor_role_id, "
							+ "r.name as contributor_role_name, "
							+ "r.base_salary as contributor_role_base_salary, "
							+ "r.create_date as contributor_role_create_date, "
							+ "r.update_date as contributor_role_update_date, "
							+ "ccc.id as contributor_cost_center_id, "
							+ "ccc.name as contributor_cost_center_name,"
							+ "ccc.description as contributor_cost_center_description, "
							+ "ccc.create_date as contributor_cost_center_create_date, "
							+ "ccc.update_date as contributor_cost_center_update_date "
							+ "FROM %s pc "
							+ "INNER JOIN project p "
							+ "ON p.id = pc.project_id "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = p.cost_center_id "
							+ "INNER JOIN client cl "
							+ "ON cl.id = p.client_id "
							+ "INNER JOIN contributor c "
							+ "ON c.id = pc.contributor_id "
							+ "INNER JOIN role r "
							+ "ON r.id = c.role_id "
							+ "INNER JOIN cost_center ccc "
							+ "ON ccc.id = c.cost_center_id "
							+ "WHERE pc.contributor_id = %d"
								, tableName
								, id
						)
					);
			
			if(!result.next()) return null;
			ProjectContributor instance = entityFromDBSet(result);
			
			result.close();
			statement.close();
			
			return instance;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public void removeAllFromProject(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
						String.format(
								"DELETE FROM %s "
								+ "WHERE project_id = '%s'"
									, tableName, id)
					);
			statement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
}
