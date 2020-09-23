
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entities.Admin;
import model.entities.Auth;
import model.entities.CostCenter;
import model.exceptions.DBException;

public class CostCenterDAO extends BaseDAO<CostCenter> {	
	public CostCenterDAO() {
		super("cost_center");
	}

	@Override
	public CostCenter entityFromDBSet(ResultSet DBSet) throws SQLException {
		return CostCenter.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(CostCenter object) {
		return String.format(
				"INSERT INTO %s "
						+ "(name, description) "
						+ "VALUES "
						+ "('%s', '%s')"
							, tableName
							, object.getName()
							, object.getDescription()
					);
	}

	@Override
	public String entityToDBupdateString(CostCenter object) {
		return String.format(
				"UPDATE %s "
						+ "SET "
						+ "name = '%s', "
						+ "description = '%s'"
						+ "WHERE id = '%d'"
							, tableName
							, object.getName()
							, object.getDescription()
							, object.getId()
					);
	}
	
}
