package model.entities.TableEntities;

import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.interfaces.IBaseEntity;

public abstract class TableBaseEntity implements IBaseEntity {
	protected SimpleIntegerProperty id;
	protected SimpleObjectProperty createDate;
	protected SimpleObjectProperty updateDate;
}
