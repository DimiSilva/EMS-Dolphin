
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entities.Master;
import model.exceptions.DBException;

public class MasterDAO extends BaseDAO<Master> {	
	public MasterDAO() {
		super("master");
	}

	@Override
	public Master entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Master.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(Master object) {
		return String.format(
				"INSERT INTO %s "
						+ "(email, auth_id) "
						+ "VALUES "
						+ "('%s', '%s')"
							, tableName
							, object.getEmail()
							, object.getAuthId()
					);
	}

	@Override
	public String entityToDBupdateString(Master object) {
		return String.format(
				"UPDATE %s "
						+ "SET "
						+ "email = '%s'"
						+ "WHERE id = '%d'"
							, object.getEmail()
							, object.getId()
					);
	}
	
	public Master getByAuthId(String id) throws DBException {
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
			Master instance = entityFromDBSet(result);
			
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
