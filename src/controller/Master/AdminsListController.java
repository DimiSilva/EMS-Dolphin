package controller.Master;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
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
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.interfaces.IStage;
import model.persistence.AdminDAO;
import model.stages.MasterStage;

public class AdminsListController implements Initializable {
	
	private AdminDAO adminDAO;
	
	@FXML
	private Button goToRegisterScreenButton;
	
	@FXML
	private TableView<Admin> table;
	
	@FXML
	private TableColumn<Admin, Integer> idColumn;
	@FXML
	private TableColumn<Admin, String> nameColumn;
	@FXML
	private TableColumn<Admin, String> emailColumn;
	@FXML
	private TableColumn<Admin, String> cpfColumn;
	@FXML
	private TableColumn<Admin, Date> createDateColumn;
	@FXML
	private TableColumn<Admin, Date> updateDateColumn;
	
	ObservableList<Admin> observableAdminList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		goToRegisterScreenButton.setOnMouseClicked(e -> MainController.changeScene("adminsRegister"));
		adminDAO = new AdminDAO();
		
		this.fetchAdmins();
	}
	
	public void updateTableData() {
		this.fetchAdmins();
	}
	
	private void fetchAdmins() {
		try {
			List<Admin> admins = adminDAO.getAll();
			observableAdminList = FXCollections.observableArrayList(admins);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
			cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
			updateDateColumn.setCellValueFactory(new PropertyValueFactory<>("updateDate"));
				
			table.setItems(observableAdminList);
		} catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
