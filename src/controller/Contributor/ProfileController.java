package controller.Contributor;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.entities.Contributor;
import model.exceptions.DBException;
import model.persistence.ContributorDAO;
import model.stages.AuthStage;

public class ProfileController implements Initializable {
	private ContributorDAO contributorDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField phoneInput;
	@FXML
	private TextField emailInput;
	@FXML
	private TextField cpfInput;
	@FXML
	private TextField addressInput;
	@FXML
	private DatePicker birthdateInput;
	@FXML
	private PasswordField passwordInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingUserId;
	@FXML
	private Contributor updatingUser;
	@FXML
	private Contributor loggedUser;
	@FXML
	private Labeled messageError;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contributorDAO = new ContributorDAO();
		registerButton.setOnMouseClicked(e -> updateProfile());
		
	}
	
	public void loadProfile() {

		this.loggedUser = (Contributor) AuthStage.loggedUser;
		
		if(this.loggedUser != null) {
			System.out.println("12");
			nameInput.setText(this.loggedUser.getName());
			cpfInput.setText(this.loggedUser.getCpf());
			emailInput.setText(this.loggedUser.getEmail());
			phoneInput.setText(this.loggedUser.getPhone());
			addressInput.setText(this.loggedUser.getAddress());
			birthdateInput.setValue(LocalDate.parse(this.loggedUser.getBirthDate().toString()));
		}
		this.messageError.setText("");
	}
	public boolean validateForm() {
		
		if(
			this.nameInput.getText() != null && 
			this.cpfInput.getText() != null && 
			this.phoneInput.getText() != null && 
			this.addressInput.getText() != null && 
			this.birthdateInput.getValue() != null && 
			this.emailInput.getText() != null
		){
			if(
				this.nameInput.getText().length() > 0 && 
				this.cpfInput.getText().length() > 0 && 
				this.phoneInput.getText().length() > 0 && 
				this.addressInput.getText().length() > 0 && 
				this.emailInput.getText().length() > 0
			){
				System.out.println(this.nameInput.getText().length());
				if(this.cpfInput.getText().length() != 11) {
					this.messageError.setText("CPF inválido!");
					return false;
				}
				if(this.phoneInput.getText().length() < 10) {
					this.messageError.setText("Telefone inválido!");
					return false;
				}
				
				if(this.passwordInput.getText() != null) {
					if(this.passwordInput.getText().length() == 6) {
						this.messageError.setText("A senha deve ter no mínimo 6 caracteres!");
						return false;
					}
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
	public void updateProfile() {
		AuthStage authStage = new AuthStage();
		
		if(this.validateForm() == true) {
			try {
				contributorDAO.update(new Contributor(
						this.loggedUser.getId(),
						this.nameInput.getText(),
						this.phoneInput.getText(),
						this.addressInput.getText(),
						this.emailInput.getText(),
						Date.valueOf(this.birthdateInput.getValue()),
						this.cpfInput.getText(),
						this.loggedUser.getAuthId(),
						this.loggedUser.getRole(),
						this.loggedUser.getCostCenter(),
						this.loggedUser.getCreateDate(),
						this.loggedUser.getUpdateDate()
						));
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			authStage.updateLoggedUser(this.loggedUser.getAuth());
	
		}
	
	}
}
