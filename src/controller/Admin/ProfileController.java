package controller.Admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.entities.Admin;
import model.exceptions.DBException;
import model.persistence.AdminDAO;
import model.stages.AuthStage;

public class ProfileController implements Initializable {
	private AdminDAO adminDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField emailInput;
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
	private int updatingUserId;
	@FXML
	private Admin updatingUser;
	@FXML
	private Admin loggedUser;
	@FXML
	private Labeled messageError;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		adminDAO = new AdminDAO();
		registerButton.setOnMouseClicked(e -> updateProfile());
		
	}
	
	public void loadProfile() {
		this.messageError.setText("");
		this.loggedUser = (Admin) AuthStage.loggedUser;
		
		if(this.loggedUser != null) {
			nameInput.setText(this.loggedUser.getName());
			cpfInput.setText(this.loggedUser.getCPF());
			emailInput.setText(this.loggedUser.getEmail());
		}
	}
	public void updateProfile() {
		AuthStage authStage = new AuthStage();
		
		if(this.validateForm() == true) {
			try {
				adminDAO.update(new Admin(
						this.loggedUser.getId(),
						this.nameInput.getText(),
						this.emailInput.getText(),
						this.cpfInput.getText(),
						this.loggedUser.getAuthId()
						));
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			authStage.updateLoggedUser(this.loggedUser.getAuth());
		}

	}
	public boolean validateForm() {
		
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
				
					if(this.cpfInput.getText().length() != 11) {
						this.messageError.setText("CPF inv�lido!");
						return false;
					}
					if(this.passwordInput.getText() != null) {
						if(this.passwordInput.getText().length() == 6) {
							this.messageError.setText("A senha deve ter no m�nimo 6 caracteres!");
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
}
