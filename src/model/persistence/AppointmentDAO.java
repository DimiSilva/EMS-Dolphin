
package model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entities.Appointment;
import model.entities.Contributor;
import model.exceptions.DBException;

public class AppointmentDAO extends BaseDAO<Appointment> {	
	public AppointmentDAO() {
		super("appointment");
	}

	@Override
	public Appointment entityFromDBSet(ResultSet DBSet) throws SQLException {
		return Appointment.fromDBSet(DBSet);
	}

	@Override
	public String entityToDBInsertString(Appointment object) {
		return String.format(
				"INSERT INTO %s "
						+ "(description, init_date, end_date, contributor_id, project_id) "
						+ "VALUES "
						+ "('%s', '%s', '%s', '%s', '%s')"
							, tableName
							, object.getDescription()
							, object.getInitDate()
							, object.getEndDate()
							, object.getContributor().getId()
							, object.getProject().getId()						
					);
	}


	@Override
	public String entityToDBupdateString(Appointment object) {
		return null;
	}
}
