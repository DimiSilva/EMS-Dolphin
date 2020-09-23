package controller.Admin;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.entities.Admin;
import model.entities.Auth;
import model.entities.CostCenter;
import model.entities.Role;
import model.enums.AccessTypes;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.exceptions.InvalidFieldException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AuthDAO;
import model.persistence.CostCenterDAO;
import model.persistence.DashboardDAO;
import model.persistence.RoleDAO;

public class RoleFormController implements Initializable {

	RoleDAO roleDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField baseSalaryInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingRoleId;
	@FXML
	private Role updatingRole;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roleDAO = new RoleDAO();
		
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("rolesList"));
	}
	
	@FXML
	public void register() {
		try {
			Role role = new Role(nameInput.getText(), Float.parseFloat(baseSalaryInput.getText()));
			roleDAO.insert(role);
			
			MainController.changeScene("rolesList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	@FXML
	public void update() {
		try {
			this.updatingRole.update(this.nameInput.getText(), Float.parseFloat(baseSalaryInput.getText()));
			
			roleDAO.update(this.updatingRole);
			
			MainController.changeScene("rolesList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	public void updatingRoleId(int updatingRoleId) {
		this.updatingRoleId = updatingRoleId;
	}
	
	public void reset() {
		this.updatingRole = null;
		this.nameInput.setText(null);
		this.baseSalaryInput.setText(null);
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar cargo");
	}
	public void loadUpdatingRoleById(int updatingRoleId) {
		this.updatingRoleId = updatingRoleId;
		
		try {
			this.updatingRole = roleDAO.getById(String.valueOf(this.updatingRoleId));
			this.nameInput.setText(this.updatingRole.getName());
			this.baseSalaryInput.setText(String.valueOf(this.updatingRole.getBaseSalary()));
			
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar cargo");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
