package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.entities.Auth;
import model.enums.AccessTypes;
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
		
		closeButton.setOnMouseClicked(e -> closeApplication());
		enterButton.setOnMouseClicked(e -> login());
	}
	
	@FXML
	private void login() {
		System.out.println(AccessTypes.ADMIN);
		String validationResult = loginValidators();
		if(validationResult != null) {
			messageError.setText(validationResult);
			return;
		}
		
		Auth userAuth = authDAO.getByIdentifier(emailInput.getText());

		if(userAuth == null || passwordInput.getText().compareTo(userAuth.password) != 0){
			messageError.setText("Credenciais inv�lidas!");
			return;
		}
		
		switch (AccessTypes.valueOf(userAuth.accessType) ) {
			case ADMIN:
				System.out.println("Admin,  nao tem tela ainda");
				//MainController.changeStage("master");
			break;
			case CONTRIBUTOR:
				System.out.println("Contribuidor, nao tenho sua tela ainda");
				//MainController.changeStage("master");
			break;
			case MASTER:
				System.out.println("Master");
				MainController.changeStage("master");
			break;
	
		}
	}
	
	private String loginValidators() {
		String email = emailInput.getText();
		String password = passwordInput.getText();
		if(email == null || email.length() < 3 || !email.contains("@"))
			return "Email inv�lido!";
		
		if(password == null || password.length() < 6)
			return "Senha inv�lida!";
			
		return null;
	}
	
	@FXML
	private void closeApplication() {
		Platform.exit();
		System.exit(0);
	}
}
