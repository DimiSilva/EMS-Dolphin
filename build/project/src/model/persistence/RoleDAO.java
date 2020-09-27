
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import model.entities.Role;

public class RoleDAO extends BaseDAO<Role> {	
	public RoleDAO() {
		super("role");
	}

	@Override
	public Role entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Role.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(Role object) {
		return String.format(
				"INSERT INTO %s "
						+ "(name, description) "
						+ "VALUES "
						+ "('%s', '%s')"
							, tableName
							, object.getName()
							, object.getBaseSalary()
					);
	}

	@Override
	public String entityToDBupdateString(Role object) {
		return String.format(
				Locale.ENGLISH,
				"UPDATE %s "
						+ "SET "
						+ "name = '%s', "
						+ "base_salary = %f "
						+ "WHERE id = '%d'"
							, tableName
							, object.getName()
							, object.getBaseSalary()
							, object.getId()
					);
	}
	
}
