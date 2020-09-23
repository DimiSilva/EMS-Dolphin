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

public class CostCenterFormController implements Initializable {

	CostCenterDAO costCenterDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextArea descriptionInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingCostCenterId;
	@FXML
	private CostCenter updatingCostCenter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		costCenterDAO = new CostCenterDAO();
		
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("costCentersList"));
	}
	
	@FXML
	public void register() {
		try {
			CostCenter costCenter = new CostCenter(nameInput.getText(), descriptionInput.getText());
			int id = costCenterDAO.insert(costCenter);
			
			MainController.changeScene("costCentersList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	@FXML
	public void update() {
		try {
			this.updatingCostCenter.update(this.nameInput.getText(), this.descriptionInput.getText());
			
			costCenterDAO.update(this.updatingCostCenter);
			
			MainController.changeScene("costCentersList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	public void updatingCostCenterId(int updatingCostCenterId) {
		this.updatingCostCenterId = updatingCostCenterId;
	}
	
	public void reset() {
		this.updatingCostCenter = null;
		this.nameInput.setText(null);
		this.descriptionInput.setText(null);
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar centro de custo");
	}
	public void loadUpdatingCostCenterById(int updatingCostCenterId) {
		this.updatingCostCenterId = updatingCostCenterId;
		
		try {
			this.updatingCostCenter = costCenterDAO.getById(String.valueOf(this.updatingCostCenterId));
			this.nameInput.setText(this.updatingCostCenter.getName());
			this.descriptionInput.setText(this.updatingCostCenter.getDescription());
			
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar Administrador");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
