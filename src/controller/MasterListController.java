package controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.entities.Admin;
import model.interfaces.IStage;
import model.persistence.AdminDAO;
import model.stages.MasterStage;

public class MasterListController implements Initializable {
	
	private AdminDAO adminDAO;
	
	@FXML
	private Button registernewAdmin;
	
	@FXML
	private TableView<Admin> adminTable;
	
	
	@FXML
	private TableColumn<Admin, Integer> adminTableId;
	@FXML
	private TableColumn<Admin, String> adminTableName;
	@FXML
	private TableColumn<Admin, String> adminTableEmail;
	@FXML
	private TableColumn<Admin, String> adminTableCpf;
	@FXML
	private TableColumn<Admin, Date> adminTableCreateDate;
	@FXML
	private TableColumn<Admin, Date> adminTableUpdateDate;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	
	ObservableList<Admin> observableAdminList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		registernewAdmin.setOnMouseClicked(e -> MainController.changeScene("register"));
		
		adminDAO = new AdminDAO();
		
			try {
				observableAdminList = adminDAO.getAll();
				adminTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
				adminTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
				adminTableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
				adminTableCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
				adminTableCreateDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
				adminTableUpdateDate.setCellValueFactory(new PropertyValueFactory<>("updateDate"));
				
				adminTable.setItems(observableAdminList);
				
			} catch (ParseException e) {
				System.out.println("Parse Error:");
				e.printStackTrace();
			}
			
	}
	
	@FXML
	public void updateList() {
		
		try {
			observableAdminList.clear();
			observableAdminList = adminDAO.getAll();
			adminTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
			adminTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
			adminTableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			adminTableCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			adminTableCreateDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
			adminTableUpdateDate.setCellValueFactory(new PropertyValueFactory<>("updateDate"));
			
			adminTable.setItems(observableAdminList);
			
		} catch (ParseException e) {
			System.out.println("Parse Error:");
			e.printStackTrace();
		}
		MainController.changeScene("dashboard");
	}
	

}
