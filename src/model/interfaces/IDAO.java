package model.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.exceptions.DBException;

public interface IDAO<T> {
	public abstract T entityFromDBSet(ResultSet DBSset) throws SQLException;
	public abstract String entityToDBInsertString(T object);
	public abstract String entityToDBupdateString(T object);
	
	public List<T> getAll() throws DBException;
	public List<T> getPaged(int page, int perPage) throws DBException;
	public T getById(String id) throws DBException;
	public int count() throws DBException;
	public int insert(T object) throws DBException;
	public void update(T object) throws DBException;
	public void remove(String id) throws DBException;
}
