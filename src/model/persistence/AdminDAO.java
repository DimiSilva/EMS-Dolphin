
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entities.Admin;
import model.exceptions.DBException;

public class AdminDAO extends BaseDAO<Admin> {	
	public AdminDAO() {
		super("admin");
	}

	@Override
	public Admin entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Admin.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(Admin object) {
		return String.format(
				"INSERT INTO %s "
						+ "(name, email, cpf, auth_id) "
						+ "VALUES "
						+ "('%s', '%s', '%s', '%s')"
							, tableName
							, object.getName()
							, object.getEmail()
							, object.getCpf()
							, object.getAuthId()
					);
	}

	@Override
	public String entityToDBupdateString(Admin object) {
		return String.format(
				"UPDATE %s "
						+ "SET "
						+ "name = '%s'"
						+ "WHERE id = '%d'"
							, tableName
							, object.getName()
							, object.getId()
					);
	}
	
	public Admin getByAuthId(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT * FROM %s "
							+ "WHERE auth_id = '%s'"
								, tableName, id
						)
					);
			
			if(!result.next()) return null;
			Admin instance = entityFromDBSet(result);
			
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
