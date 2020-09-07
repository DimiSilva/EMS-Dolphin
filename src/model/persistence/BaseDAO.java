package model.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.exceptions.DBException;
import model.interfaces.IDAO;

public abstract class BaseDAO<T> implements IDAO<T> {
	protected Connection conn;
	protected String tableName;
	
	public BaseDAO(String tableName) {
		this.conn = DBConnection.getConnection();
		this.tableName = tableName;
	}

	public List<T> getAll() throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(String.format("SELECT * FROM %s", tableName));

			List<T> list = new ArrayList<T>();
			
			while(result.next()) { list.add(entityFromDBSet(result)); }
			
			result.close();
			statement.close();
			
			return list;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public List<T> getPaged(int page, int perPage) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT * FROM %s "
									+ "LIMIT %d, %d"
										, tableName
										, page * perPage - perPage
										, perPage
						)
					);

			List<T> list = new ArrayList<T>();
			
			while(result.next()) { list.add(entityFromDBSet(result)); }
			
			result.close();
			statement.close();
			
			return list;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public T getById(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(
					String.format(
							"SELECT * FROM %s "
									+ "WHERE id = '%s'"
							, tableName, id
						)
					);
			
			T instance = entityFromDBSet(result);
			
			result.close();
			statement.close();
			
			return instance;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public int count() throws DBException {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(String.format("SELECT COUNT(*) FROM %s", tableName));

			result.first();
			int total = result.getInt("count");
			
			result.close();
			statement.close();
			
			return total;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public int insert(T object) throws DBException {
		try {
			Statement statement = conn.createStatement();
			int id = statement.executeUpdate(entityToDBInsertString(object), Statement.RETURN_GENERATED_KEYS);
			statement.close();
			
			return id;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public void update(T object) throws DBException  {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(entityToDBupdateString(object));
			statement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	public void remove(String id) throws DBException {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
						String.format(
								"DELETE FROM %s "
										+ "WHERE id = '%s'"
								, tableName, id)
					);
			statement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
}
