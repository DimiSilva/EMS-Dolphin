package controller.Admin;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.entities.Auth;
import model.entities.Contributor;
import model.entities.CostCenter;
import model.entities.Role;
import model.enums.AccessTypes;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.exceptions.InvalidFieldException;
import model.helpers.Utils;
import model.persistence.AuthDAO;
import model.persistence.ContributorDAO;
import model.persistence.CostCenterDAO;
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
	private Contributor updatingContributor;
	@FXML
	private ComboBox<Role> roleInput;	
	@FXML
	private ComboBox<CostCenter> costCenterInput;	
	
	@FXML
	private Labeled messageError;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contributorDAO = new ContributorDAO();
		roleDAO = new RoleDAO();
		costCenterDAO = new CostCenterDAO();
		authDAO = new AuthDAO();
		List<Role> roleList  =  null;
		List<CostCenter> costCenterList  =  null;
		try {
			roleList = roleDAO.getAll();
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		roleInput.getItems().addAll(roleList);
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
		if(this.validateForm("register") == true) {
			try {
				Auth auth = new Auth(emailInput.getText(), passwordInput.getText(), AccessTypes.CONTRIBUTOR.getText());
				int id = authDAO.insert(auth);
			
				Contributor contributor = new Contributor(id, nameInput.getText(), phoneInput.getText(), emailInput.getText(), cpfInput.getText(), addressInput.getText(), 
					Date.valueOf(birthdateInput.getValue()), 
					roleInput.getValue(), costCenterInput.getValue());
			
				contributorDAO.insert(contributor);
			
				MainController.changeScene("clientsList");
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
		}
	}
	
	@FXML
	public void update() {
		
		if(this.validateForm("update") == true) {
			try {
				this.updatingContributor.update(
					this.updatingContributor.getAuthId(), 
					this.nameInput.getText(), 
					this.emailInput.getText(), 
					this.cpfInput.getText(), 
					this.phoneInput.getText(),
					this.addressInput.getText(),
					Date.valueOf(birthdateInput.getValue()),
					this.roleInput.getValue(),
					this.costCenterInput.getValue()
				);
			
				contributorDAO.update(this.updatingContributor);			
				MainController.changeScene("contributorsList");
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
			catch(InvalidFieldException e) {
				Utils.showErrorAlert("Erro!", e.message, null);
			}
		}
	}

	public void setUpdatingUserId(int updatingClientrId) {
		/*this.updatingClientrId = updatingClientrId;*/
	}
	
	public boolean validateForm(String type) {
		
		if(type == "register") {
			if(
				this.nameInput.getText() != null && 
				this.cpfInput.getText() != null && 
				this.phoneInput.getText() != null &&
				this.addressInput.getText() != null &&
				this.passwordInput.getText() != null && 
				this.emailInput.getText() != null &&
				this.birthdateInput.getValue() != null &&
				this.roleInput.getValue() != null &&
				this.costCenterInput.getValue() != null
			){
				if(
					this.nameInput.getText().length() > 0 && 
					this.cpfInput.getText().length() > 0 && 
					this.passwordInput.getText().length() > 0 && 
					this.addressInput.getText().length() > 0 && 
					this.phoneInput.getText().length() > 0 && 
					this.emailInput.getText().length() > 0
				){
				
					if(this.cpfInput.getText().length() != 11) {
						this.messageError.setText("CPF inválido!");
						return false;
					}
					if(this.phoneInput.getText().length() < 10) {
						this.messageError.setText("Telefone inválido!");
						return false;
					}
					
					if(this.passwordInput.getText().length() < 6) {
						this.messageError.setText("A senha deve ter no mínimo 6 caracteres!");
						return false;
					}

					this.messageError.setText("");
					return true;
					
				}else {
					this.messageError.setText("Preencha todos os campos!");
					return false;
				}
			}else {
				this.messageError.setText("Preencha todos os campos!");
				return false;			
			}
		}else {
			if(
				this.nameInput.getText() != null && 
				this.cpfInput.getText() != null && 
				this.emailInput.getText() != null
			){
				if(
					this.nameInput.getText().length() > 0 && 
					this.cpfInput.getText().length() > 0 && 
					this.emailInput.getText().length() > 0
				){
					if(this.phoneInput.getText().length() < 10) {
						this.messageError.setText("Telefone inválido!");
						return false;
					}
					if(this.passwordInput.getText() != null) {
						if(this.passwordInput.getText().length() < 6) {
							this.messageError.setText("A senha deve ter no mínimo 6 caracteres!");
							return false;
						}
					}
					
					if(this.cpfInput.getText().length() != 11) {
						this.messageError.setText("CPF inválido!");
						return false;
					}

					this.messageError.setText("");
					return true;
						
				}else {
						this.messageError.setText("Preencha todos os campos!");
					return false;
				}
			}else {
				this.messageError.setText("Preencha todos os campos!");
				return false;			
			}
		}
	}

	public void reset() {
		this.updatingContributor = null;
		this.nameInput.setText(null);
		this.emailInput.setText(null);
		this.cpfInput.setText(null);
		this.phoneInput.setText(null);
		this.addressInput.setText(null);
		this.passwordInput.setText(null);
		this.birthdateInput.setValue(null);
		this.costCenterInput.setValue(null);
		this.roleInput.setValue(null);
		this.passwordContainer.setVisible(true);
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar Cliente");
		this.messageError.setText("");
	}
	public void loadUpdatingContributorById(int updatingContributorId) {
		this.updatingContributorId = updatingContributorId;
		
		try {
			this.updatingContributor = contributorDAO.getById(String.valueOf(this.updatingContributorId));
			this.nameInput.setText(this.updatingContributor.getName());
			this.emailInput.setText(this.updatingContributor.getEmail());
			this.cpfInput.setText(this.updatingContributor.getCpf());
			this.phoneInput.setText(this.updatingContributor.getPhone());
			this.addressInput.setText(this.updatingContributor.getAddress());
			
		
			this.birthdateInput.setValue(LocalDate.parse(this.updatingContributor.getBirthDate().toString()));
			this.costCenterInput.setValue(this.updatingContributor.getCostCenter());
			this.roleInput.setValue(this.updatingContributor.getRole());
			this.passwordContainer.setVisible(false);
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar Colaborador");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
