
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
						+ "name = '%s', "
						+ "email = '%s', "
						+ "address = '%s', "
						+ "birthdate = '%s', "
						+ "role_id = '%s', "
						+ "cost_center_id = '%s', "
						+ "phone = '%s' "
						+ "WHERE id = '%d'"
							, tableName
							, object.getName()
							, object.getEmail()
							, object.getAddress()
							, object.getBirthDate()
							, object.getRole().getId()
							, object.getCostCenter().getId()
							, object.getPhone()
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
