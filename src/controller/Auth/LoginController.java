package controller.Auth;

import java.net.URL;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.entities.Auth;
import model.enums.AccessTypes;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.interfaces.IDAO;
import model.persistence.AuthDAO;

public class LoginController implements Initializable {
	
	private AuthDAO authDAO;
	
	@FXML
	private Label closeButton;
	@FXML
	private Label messageError;
	@FXML 
	private TextField emailInput;
	@FXML 
	private PasswordField passwordInput;
	@FXML
	private Button enterButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		authDAO = new AuthDAO();
		
		closeButton.setOnMouseClicked(e -> MainController.closeApplication());
		enterButton.setOnMouseClicked(e -> login());
	}
	
	@FXML
	private void login() {

		emailInput.setText("admin@admin.com");

		passwordInput.setText("123456");
		String validationResult = loginValidators();
		if(validationResult != null) {
			messageError.setText(validationResult);
			return;
		}

		try {
			Auth userAuth = authDAO.getByIdentifier(emailInput.getText());

			if(userAuth == null || passwordInput.getText().compareTo(userAuth.getPassword()) != 0){
				messageError.setText("Credenciais inválidas!");
				return;
			}
			
			switch (AccessTypes.valueOf(userAuth.getAcessType())) {
				case ADMIN:
					MainController.changeStage("admin");
				break;
				case CONTRIBUTOR:
					MainController.changeStage("contributor");
				break;
				case MASTER:
					MainController.changeStage("master");
				break;
		
			}
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	private String loginValidators() {
		String email = emailInput.getText();
		String password = passwordInput.getText();
		if(email == null || email.length() < 3 || !email.contains("@"))
			return "Email inválido!";
		
		if(password == null || password.length() < 6)
			return "Senha inválida!";
			
		return null;
	}
}
