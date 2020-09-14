package controller.Master;

import java.net.URL;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Admin;
import model.entities.Auth;
import model.enums.AccessTypes;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AuthDAO;

public class AdminsRegisterController implements Initializable {
	private AdminDAO adminDAO;
	private AuthDAO authDAO;
	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		adminDAO = new AdminDAO();
		authDAO = new AuthDAO();
		
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("adminsList"));
	}
	
	@FXML
	public void register() {
		try {
			Auth auth = new Auth(emailInput.getText(), passwordInput.getText(), AccessTypes.ADMIN.getText());
			int id = authDAO.insert(auth);
			 
			Admin admin = new Admin(nameInput.getText(), emailInput.getText(), cpfInput.getText(), id);
			adminDAO.insert(admin);
			
			MainController.changeScene("adminsList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
