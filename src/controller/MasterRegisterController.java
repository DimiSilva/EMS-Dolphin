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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.interfaces.IStage;
import model.persistence.AdminDAO;
import model.stages.MasterStage;

public class MasterRegisterController implements Initializable {
	
	// DAOs:
	private AdminDAO adminDAO;
	
	
	
	// TextFields:
	
	@FXML
	private TextField adminNameInput;
	@FXML
	private TextField adminEmailInput;
	@FXML
	private TextField adminCpfInput;
	
	// Buttons:
	
	@FXML
	private Button adminSaveButtonRegister;
	@FXML
	private Button adminBackButtonRegister;

	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		adminDAO = new AdminDAO();
		
		adminSaveButtonRegister.setOnMouseClicked(e -> register());
		adminBackButtonRegister.setOnMouseClicked(e -> MainController.changeScene("dashboard"));
		
	}
	
	@FXML
	public void register() {
		// N�o tem valida��es ainda
		String adminName = adminNameInput.getText();
		String adminEmail = adminEmailInput.getText();
		String adminCpf = adminCpfInput.getText();
		System.out.println(adminName);
		System.out.println(adminEmail);
		System.out.println(adminCpf);
		adminDAO.toDBSet(adminName, adminEmail, adminCpf);
		MainController.changeScene("dashboard");
	}
}
