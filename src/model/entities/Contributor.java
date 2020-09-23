package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.exceptions.DBException;
import model.interfaces.IBaseUser;
import model.persistence.CostCenterDAO;
import model.persistence.RoleDAO;

public class Contributor extends BaseEntity implements IBaseUser {
	private String name;
	private String phone;
	private String address;
	private String email;
	private Date birthDate;
	private String cpf;
	private Integer authId;
	private Role role;
	private CostCenter costCenter; 
	
	public Contributor(Integer authId, String name, String phone, String email, String cpf, String address, Date birthDate, Role role, CostCenter costCenter) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.cpf = cpf;
		this.role = role;
		this.costCenter = costCenter;
		this.address = address;
		this.birthDate = birthDate;
		this.authId = authId;
	}
	public Contributor(Integer id, String name, String phone, String address, String email, Date birthDate, String cpf, Integer authId, Role role, CostCenter costCenter, Date createDate, Date updateDate) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.authId = authId;
		this.role = role;
		this.costCenter = costCenter;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Contributor fromDBSet(ResultSet DBSet) throws SQLException {
		Integer id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String phone = DBSet.getString("phone");
		String address = DBSet.getString("address");
		String email = DBSet.getString("email");
		Date birthDate = DBSet.getDate("birthdate");
		String cpf = DBSet.getString("cpf");
		Integer authId = DBSet.getInt("auth_id");
		System.out.println(DBSet.getString("cpf"));
		System.out.println(DBSet.getString("name"));
		RoleDAO roleDAO = new RoleDAO();
		Role role = null;
		try {
			role = roleDAO.getById(String.valueOf(DBSet.getInt("role_id")));
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CostCenterDAO costCenterDAO = new CostCenterDAO();
		CostCenter costCenter = null;
		try {
			costCenter = costCenterDAO.getById(String.valueOf(DBSet.getInt("cost_center_id")));
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Role role = new Role(DBSet.getInt("role_id"), DBSet.getString("role_name"), DBSet.getFloat("base_salary"), DBSet.getDate("create_date"), DBSet.getDate("role_update_date"));
		//CostCenter costCenter = new CostCenter(DBSet.getInt("cost_center_id"), DBSet.getString("cost_center_name"), DBSet.getString("cost_center_description"), DBSet.getDate("cost_center_create_date"), DBSet.getDate("cost_center_update_date"));
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Contributor(id, name, phone, address, email, birthDate, cpf, authId, role, costCenter,  createDate, updateDate);
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public Integer getAuthId() {
		return authId;
	}
	
	public Role getRole() {
		return role;
	}
	
	public CostCenter getCostCenter() {
		return costCenter;
	}
}
