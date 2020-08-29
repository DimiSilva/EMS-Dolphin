package model.entities.TableEntities;

import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.entities.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TableAdmin extends TableBaseEntity{
	private SimpleStringProperty name;
	private SimpleStringProperty email;
	private SimpleStringProperty cpf;
	
	private Admin admin;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	public TableAdmin(Integer id, String name, String email, String cpf, Date createDate, Date updateDate) throws ParseException {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.email = new SimpleStringProperty(email);
		this.cpf = new SimpleStringProperty(cpf);
		this.createDate = new SimpleObjectProperty(createDate);
		this.updateDate = new SimpleObjectProperty(updateDate);
	}
	
	public TableAdmin(Admin admin) throws ParseException {
		this.admin = admin;
		this.id = new SimpleIntegerProperty(admin.getId());
		this.name = new SimpleStringProperty(admin.getName());
		this.email = new SimpleStringProperty(admin.getEmail());
		this.cpf = new SimpleStringProperty(admin.getCpf());
		this.createDate = new SimpleObjectProperty(admin.getCreateDate());
		this.updateDate = new SimpleObjectProperty(admin.getUpdateDate());
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public SimpleStringProperty getEmail() {
		return email;
	}

	public SimpleStringProperty getCpf() {
		return cpf;
	}

	public Admin getAdmin() {
		return admin;
	}
	
	
}
