package model.entities;

import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import model.interfaces.IBaseEntity;

public abstract class BaseEntity implements IBaseEntity {
	protected Integer id;
	protected Date createDate;
	protected Date updateDate;
	
	public Integer getId() { return this.id; }
	
	public Date getCreateDate() { return this.createDate; }
	
	public Date getUpdateDate() { return this.updateDate; }
}
