
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entities.Contributor;
import model.exceptions.DBException;

public class ContributorDAO extends BaseDAO<Contributor> {	
	public ContributorDAO() {
		super("contributor");
	}

	@Override
	public Contributor entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Contributor.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(Contributor object) {
		return String.format(
				"INSERT INTO %s "
						+ "(name, email, cpf, phone, address, birthdate, auth_id, role_id, cost_center_id) "
						+ "VALUES "
						+ "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')"
							, tableName
							, object.getName()
							, object.getEmail()
							, object.getCpf()
							, object.getPhone()
							, object.getAddress()
							, object.getBirthDate()
							, object.getAuthId()
							, object.getRole().getId()
							, object.getCostCenter().getId()						
					);
	}


	@Override
	public String entityToDBupdateString(Contributor object) {
		return String.format(
				"UPDATE %s "
						+ "SET "
						+ ""
						+ "WHERE id = '%d'"
							, object.getId()
					);
	}

	@Override
	public List<Contributor> getAll() throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT c.*, "
							+ "r.name as role_name, "
							+ "r.base_salary as role_base_salary, "
							+ "r.create_date as role_create_date, "
							+ "r.update_date as role_update_date, "
							+ "cc.name as cost_center_name,"
							+ "cc.description as cost_center_description, "
							+ "cc.create_date as cost_center_create_date, "
							+ "cc.update_date as cost_center_update_date "
							+ "FROM %s c "
							+ "INNER JOIN role r "
							+ "ON r.id = role_id "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = cost_center_id"
								, tableName
						)
					);

			List<Contributor> list = new ArrayList<Contributor>();
			
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
	public List<Contributor> getPaged(int page, int perPage) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT c.*, "
							+ "r.name as role_name, "
							+ "r.base_salary as role_base_salary, "
							+ "r.create_date as role_create_date, "
							+ "r.update_date as role_update_date, "
							+ "cc.name as cost_center_name,"
							+ "cc.description as cost_center_description, "
							+ "cc.create_date as cost_center_create_date, "
							+ "cc.update_date as cost_center_update_date "
							+ "FROM %s c "
							+ "INNER JOIN role r "
							+ "ON r.id = role_id "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = cost_center_id "
							+ "LIMIT %d, %d"
								, tableName
								, page * perPage - perPage
								, perPage
						)
					);
			
			List<Contributor> list = new ArrayList<Contributor>();
			
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
	public Contributor getById(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT c.*, "
							+ "r.name as role_name, "
							+ "r.base_salary as role_base_salary, "
							+ "r.create_date as role_create_date, "
							+ "r.update_date as role_update_date, "
							+ "cc.name as cost_center_name,"
							+ "cc.description as cost_center_description, "
							+ "cc.create_date as cost_center_create_date, "
							+ "cc.update_date as cost_center_update_date "
							+ "FROM %s c "
							+ "INNER JOIN role r "
							+ "ON r.id = role_id "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = cost_center_id "
							+ "WHERE c.id = '%s'"
								, tableName, id
						)
					);
			
			if(!result.next()) return null;
			Contributor instance = entityFromDBSet(result);
			
			result.close();
			statement.close();
			
			return instance;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public Contributor getByAuthId(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT c.*, "
							+ "r.name as role_name, "
							+ "r.base_salary as role_base_salary, "
							+ "r.create_date as role_create_date, "
							+ "r.update_date as role_update_date, "
							+ "cc.name as cost_center_name,"
							+ "cc.description as cost_center_description, "
							+ "cc.create_date as cost_center_create_date, "
							+ "cc.update_date as cost_center_update_date "
							+ "FROM %s c "
							+ "INNER JOIN role r "
							+ "ON r.id = role_id "
							+ "INNER JOIN cost_center cc "
							+ "ON cc.id = cost_center_id "
							+ "WHERE c.auth_id = '%s'"
								, tableName, id
						)
					);
			
			if(!result.next()) return null;
			Contributor instance = entityFromDBSet(result);
			
			result.close();
			statement.close();
			
			return instance;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public List<Contributor> getInProject(String projectId) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
				String.format(
						"SELECT c.*, "
						+ "r.name as role_name, "
						+ "r.base_salary as role_base_salary, "
						+ "r.create_date as role_create_date, "
						+ "r.update_date as role_update_date, "
						+ "cc.name as cost_center_name,"
						+ "cc.description as cost_center_description, "
						+ "cc.create_date as cost_center_create_date, "
						+ "cc.update_date as cost_center_update_date "
						+ "FROM project_contributor pc "
						+ "INNER JOIN project p "
						+ "ON p.id = pc.project_id "
						+ "INNER JOIN contributor c "
						+ "ON c.id = pc.contributor_id "
						+ "INNER JOIN role r "
						+ "ON r.id = c.role_id "
						+ "INNER JOIN cost_center cc "
						+ "ON cc.id = c.cost_center_id "
						+ "WHERE pc.project_id = '%s'"
							, projectId
				)
			);

			List<Contributor> list = new ArrayList<Contributor>();
			
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
	
	public List<Contributor> getOutOfProjectFiltered(List<Contributor> projectParticipants, String searchText) throws DBException {
		try {
			final StringBuilder participantsWhereBulder = new StringBuilder();
			projectParticipants.forEach(item -> participantsWhereBulder.append(String.format("c.id != %d AND ", item.getId())));
			String participantsWhere = participantsWhereBulder.toString();
			
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
				String.format(
						"SELECT c.*, "
						+ "r.name as role_name, "
						+ "r.base_salary as role_base_salary, "
						+ "r.create_date as role_create_date, "
						+ "r.update_date as role_update_date, "
						+ "cc.name as cost_center_name,"
						+ "cc.description as cost_center_description, "
						+ "cc.create_date as cost_center_create_date, "
						+ "cc.update_date as cost_center_update_date "
						+ "FROM %s c "
						+ "INNER JOIN role r "
						+ "ON r.id = role_id "
						+ "INNER JOIN cost_center cc "
						+ "ON cc.id = cost_center_id "
						+ "WHERE "
						+ participantsWhere
						+ "(c.name LIKE '%%%s%%' OR c.email LIKE '%%%s%%' OR c.cpf LIKE '%%%s%%')"
							, tableName
							, searchText
							, searchText
							, searchText
				)
			);

			List<Contributor> list = new ArrayList<Contributor>();
			
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
}
