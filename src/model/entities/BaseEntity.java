package model.entities;

import java.util.Date;

import model.interfaces.IBaseEntity;

public class BaseEntity implements IBaseEntity {
	public int id;
	public Date createDate;
	public Date updateDate;
}
