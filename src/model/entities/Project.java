package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Project extends BaseEntity {
	private String name;
	private String description;
	private CostCenter costCenter;
	private Client client;
	private Date initDate;
	private Date endDate;
	
	public Project(String name, String description, CostCenter costCenter, Client client, Date initDate, Date endDate) {
		this.name = name;
		this.description = description;
		this.costCenter = costCenter;
		this.client = client;
		this.initDate = initDate;
		this.endDate = endDate;
	}
	
	public Project(Integer id, String name, String description, CostCenter costCenter, Client client, Date initDate, Date endDate, Date createDate, Date updateDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.costCenter = costCenter;
		this.client = client;
		this.initDate = initDate;
		this.endDate = endDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public static Project fromDBSet(ResultSet DBSet) throws SQLException {
		int id = DBSet.getInt("id");
		String name = DBSet.getString("name");
		String description = DBSet.getString("description");
		CostCenter costCenter = new CostCenter(
			DBSet.getInt("cost_center_id"), 
			DBSet.getString("cost_center_name"), 
			DBSet.getString("cost_center_description"), 
			DBSet.getDate("cost_center_create_date"),  
			DBSet.getDate("cost_center_update_date")	
		);
		
		Client client = new Client(
			DBSet.getInt("client_id"), 
			DBSet.getString("client_name"), 
			DBSet.getString("client_phone"), 
			DBSet.getString("client_email"), 
			DBSet.getString("client_cnpj"), 
			DBSet.getDate("client_create_date"),  
			DBSet.getDate("client_update_date")	
		);
		Date initDate = DBSet.getDate("init_date");
		Date endDate = DBSet.getDate("end_date");
		Date createDate = DBSet.getDate("create_date");
		Date updateDate = DBSet.getDate("update_date");
		
		return new Project(id, name, description, costCenter, client, initDate, endDate, createDate, updateDate);
	}
	
	public void update(String name, String description, Date initDate, Date endDate) {
		this.name = name;
		this.description = description;
		this.initDate = initDate;
		this.endDate = endDate;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public CostCenter getCostCenter() {
		return costCenter;
	}
	
	public Client getClient() {
		return client;
	}
	
	public Integer getCostCenterId() {
		return client.getId();
	}
	
	public String getCostCenterName() {
		return costCenter.getName();
	}
	
	public Integer getClientId() {
		return client.getId();
	}
	
	public String getClientName() {
		return client.getName();
	}
	
	public Date getInitDate() {
		return initDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
