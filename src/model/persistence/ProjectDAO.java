
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.entities.Project;
import model.exceptions.DBException;

public class ProjectDAO extends BaseDAO<Project> {	
	public ProjectDAO() {
		super("project");
	}

	@Override
	public Project entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Project.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(Project object) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return String.format(
				"INSERT INTO %s "
						+ "(name, description, cost_center_id, client_id, init_date, end_date) "
						+ "VALUES "
						+ "('%s', '%s', '%d', '%d', '%s', '%s')"
							, tableName
							, object.getName()
							, object.getDescription()
							, object.getCostCenterId()
							, object.getClientId()
							, formatter.format(object.getInitDate())
							, formatter.format(object.getEndDate())
					);
	}

	@Override
	public String entityToDBupdateString(Project object) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return String.format(
				"UPDATE %s "
						+ "SET "
						+ "name = '%s', "
						+ "description = '%s', "
						+ "init_date = '%s', "
						+ "end_date = '%s' "
						+ "WHERE id = '%d'"
							, tableName
							, object.getName()
							, object.getDescription()
							, formatter.format(object.getInitDate())
							, formatter.format(object.getEndDate())
							, object.getId()
					);
	}
	
	@Override
	public List<Project> getPaged(int page, int perPage) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT p.*, "
							+ "cc.id as cost_center_id, "
							+ "cc.name as cost_center_name, "
							+ "cc.description as cost_center_description, "
							+ "cc.create_date as cost_center_create_date, "
							+ "cc.update_date as cost_center_update_date, "
							+ "c.id as client_id, "
							+ "c.name as client_name, "
							+ "c.phone as client_phone, "
							+ "c.email as client_email, "
							+ "c.cnpj as client_cnpj, "
							+ "c.create_date as client_create_date, "
							+ "c.update_date as client_update_date "
							+ "FROM %s p "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = p.cost_center_id "
							+ "INNER JOIN client c "
							+ "ON c.id = p.client_id "
							+ "LIMIT %d, %d"
								, tableName
								, page * perPage - perPage
								, perPage
						)
					);

			List<Project> list = new ArrayList<Project>();
			
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
	
	public List<Project> getAllOfContributorPaticipating(String contributorId) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT pc.*, "
							+ "p.*, "
							+ "cc.id as cost_center_id, "
							+ "cc.name as cost_center_name, "
							+ "cc.description as cost_center_description, "
							+ "cc.create_date as cost_center_create_date, "
							+ "cc.update_date as cost_center_update_date, "
							+ "c.id as client_id, "
							+ "c.name as client_name, "
							+ "c.phone as client_phone, "
							+ "c.email as client_email, "
							+ "c.cnpj as client_cnpj, "
							+ "c.create_date as client_create_date, "
							+ "c.update_date as client_update_date "
							+ "FROM project_contributor pc "
							+ "INNER JOIN project p "
							+ "ON p.id = pc.project_id "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = p.cost_center_id "
							+ "INNER JOIN client c "
							+ "ON c.id = p.client_id "
							+ "WHERE pc.contributor_id = '%s' AND p.init_date <= CURDATE() AND p.end_date > CURDATE()"
								, contributorId
						)
					);

			List<Project> list = new ArrayList<Project>();
			
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
	
	@Override
	public Project getById(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT p.*, "
							+ "cc.id as cost_center_id, "
							+ "cc.name as cost_center_name, "
							+ "cc.description as cost_center_description, "
							+ "cc.create_date as cost_center_create_date, "
							+ "cc.update_date as cost_center_update_date, "
							+ "c.id as client_id, "
							+ "c.name as client_name, "
							+ "c.phone as client_phone, "
							+ "c.email as client_email, "
							+ "c.cnpj as client_cnpj, "
							+ "c.create_date as client_create_date, "
							+ "c.update_date as client_update_date "
							+ "FROM %s p "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = p.cost_center_id "
							+ "INNER JOIN client c "
							+ "ON c.id = p.client_id "
							+ "WHERE p.id = '%s'"
								, tableName
								, id
						)
					);
			
			if(!result.next()) return null;
			Project instance = entityFromDBSet(result);
			
			result.close();
			statement.close();
			
			return instance;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
}
