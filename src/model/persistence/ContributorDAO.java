
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entities.Admin;
import model.entities.Auth;
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
						+ "() "
						+ "VALUES "
						+ "()"
							, tableName
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
}
