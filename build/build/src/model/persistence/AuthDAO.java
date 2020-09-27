package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entities.Auth;
import model.exceptions.DBException;

public class AuthDAO extends BaseDAO<Auth> {
	public AuthDAO () {
		super("auth");
	}
	
	@Override
	public Auth entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Auth.fromDBSet(DBSet);
	}
	
	@Override
	public String entityToDBInsertString(Auth object) {
		return String.format(
				"INSERT INTO %s "
						+ "(identifier, password, access_type) "
						+ "VALUES "
						+ "('%s', '%s', '%s')"
							, tableName
							, object.getIdentifier()
							, object.getPassword()
							, object.getAccessType()
					);
	}

	@Override
	public String entityToDBupdateString(Auth object) {
		return String.format(
				"UPDATE %s "
						+ "SET "
						+ "password = '%s'"
						+ "WHERE id = '%d'"
							, object.getPassword()
							, object.getId()
					);
	}
	
	public Auth getByIdentifier(String identifier) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT * FROM %s "
									+ "WHERE identifier = '%s'"
										, tableName, identifier
						)
				);
			if(!result.next()) return null;
			Auth auth = entityFromDBSet(result);

			statement.close();
			result.close();
			
			return auth;
		}
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DBException();
		}
	}
}
