package controller.Admin;

import java.net.URL;
import java.text.ParseException;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.entities.Admin;
import model.entities.Auth;
import model.entities.Client;
import model.enums.AccessTypes;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.exceptions.InvalidFieldException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AuthDAO;
import model.persistence.ClientDAO;
import model.persistence.DashboardDAO;

public class ClientFormController implements Initializable {
	private ClientDAO clientDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField emailInput;
	@FXML
	private TextField cnpjInput;
	@FXML
	private TextField phoneInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingClientrId;
	@FXML
	private Client updatingClient;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientDAO = new ClientDAO();
		
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("clientsList"));
	}
	
	@FXML
	public void register() {
		try {
			Client client = new Client(nameInput.getText(), phoneInput.getText(), emailInput.getText(), cnpjInput.getText());
			clientDAO.insert(client);
			
			MainController.changeScene("clientsList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	@FXML
	public void update() {
		try {
			this.updatingClient.update(this.nameInput.getText(), this.emailInput.getText(), this.cnpjInput.getText(), this.phoneInput.getText());
			
			clientDAO.update(this.updatingClient);			
			MainController.changeScene("clientsList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
		catch(InvalidFieldException e) {
			Utils.showErrorAlert("Erro!", e.message, null);
		}
	}

	public void setUpdatingUserId(int updatingClientrId) {
		this.updatingClientrId = updatingClientrId;
	}
	
	public void reset() {
		this.updatingClient = null;
		this.nameInput.setText(null);
		this.emailInput.setText(null);
		this.cnpjInput.setText(null);
		this.phoneInput.setText(null);
		this.passwordContainer.setVisible(true);
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar Cliente");
	}
	public void loadUpdatingClientById(int updatingClientrId) {
		this.updatingClientrId = updatingClientrId;
		
		try {
			this.updatingClient = clientDAO.getById(String.valueOf(this.updatingClientrId));
			this.nameInput.setText(this.updatingClient.getName());
			this.emailInput.setText(this.updatingClient.getEmail());
			this.cnpjInput.setText(this.updatingClient.getCnpj());
			this.phoneInput.setText(this.updatingClient.getPhone());
	
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar Cliente");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
}
