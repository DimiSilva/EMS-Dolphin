package controller.Admin;

import java.net.URL;
import java.time.ZoneId;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.entities.Admin;
import model.entities.Client;
import model.entities.Contributor;
import model.entities.CostCenter;
import model.entities.Role;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.exceptions.InvalidFieldException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AuthDAO;
import model.persistence.ContributorDAO;
import model.persistence.CostCenterDAO;
import model.persistence.DashboardDAO;
import model.persistence.RoleDAO;

public class ContributorFormController implements Initializable {

	private ContributorDAO contributorDAO;
	private RoleDAO roleDAO;
	private CostCenterDAO costCenterDAO;
	private AuthDAO authDAO;
	
	
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField emailInput;
	@FXML
	private TextField addressInput;
	@FXML
	private TextField phoneInput;
	@FXML
	private DatePicker birthdateInput;
	@FXML
	private TextField cpfInput;
	@FXML
	private PasswordField passwordInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingContributorId;
	@FXML
	private Admin updatingContributor;
	@FXML
	private ComboBox<Role> roleInput;	
	@FXML
	private ComboBox<CostCenter> costCenterInput;	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contributorDAO = new ContributorDAO();
		roleDAO = new RoleDAO();
		costCenterDAO = new CostCenterDAO();
		authDAO = new AuthDAO();
		List<Role> roleList  =  null;
		try {
			roleList = roleDAO.getAll();
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<CostCenter> costCenterList  =  null;
		try {
			costCenterList = costCenterDAO.getAll();
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		costCenterInput.getItems().addAll(costCenterList);
	
		
		registerButton.setOnMouseClicked(e ->  register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("contributorsList"));
	}
	

	@FXML
	public void register() {

		try {
			Contributor contributor = new Contributor(nameInput.getText(), phoneInput.getText(), emailInput.getText(), cpfInput.getText(), addressInput.getText(), 
					Date.from(birthdateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), 
					roleInput.getValue(), costCenterInput.getValue());
			contributorDAO.insert(contributor);
			
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
