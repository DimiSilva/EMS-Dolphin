
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entities.Admin;
import model.entities.Auth;
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
							, object.getCPF()
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
							, object.getName()
							, object.getId()
					);
	}
	
}
