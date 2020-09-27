
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.entities.Client;

public class ClientDAO extends BaseDAO<Client> {	
	public ClientDAO() {
		super("client");
	}

	@Override
	public Client entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Client.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(Client object) {
		return String.format(
				"INSERT INTO %s "
						+ "(name, email, cnpj, phone) "
						+ "VALUES "
						+ "('%s', '%s', '%s', '%s')"
							, tableName
							, object.getName()
							, object.getEmail()
							, object.getCnpj()
							, object.getPhone()
					);
	}

	@Override
	public String entityToDBupdateString(Client object) {
		return String.format(
				"UPDATE %s "
						+ "SET "
						+ "name = '%s', "
						+ "email = '%s', "
						+ "phone = '%s' "
						+ "WHERE id = '%d'"
							, tableName
							, object.getName()
							, object.getEmail()
							, object.getPhone()
							, object.getId()
					);
	}
}
