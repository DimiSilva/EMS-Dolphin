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
import model.interfaces.IDAO;
import model.persistence.AuthDAO;

public class LoginController implements Initializable {
	
	private AuthDAO authDAO;
	
	@FXML
	private Label closeButton;
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
		String validationResult = loginValidators();
		if(validationResult != null) {
			// interação com o usuário aqui
			return;
		}
		
		Auth userAuth = authDAO.getByIdentifier(emailInput.getText());
		System.out.println(userAuth.identifier);
		
		MainController.changeStage("master");
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
	
	@FXML
	private void closeApplication() {
		Platform.exit();
		System.exit(0);
	}
}
