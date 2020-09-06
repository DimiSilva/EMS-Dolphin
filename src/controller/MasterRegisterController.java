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
import model.persistence.AuthDAO;
import model.stages.MasterStage;

public class MasterRegisterController implements Initializable {
	
	// DAOs:
	private AdminDAO adminDAO;
	
	private AuthDAO authDao;
	
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
	private Button backToListAdminsBtn;

	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		adminDAO = new AdminDAO();
		
		adminSaveButtonRegister.setOnMouseClicked(e -> register());
		backToListAdminsBtn.setOnMouseClicked(e -> MainController.changeScene("list"));
		
	}
	
	@FXML
	public void register() {
		// Não tem validações ainda
		String adminName = adminNameInput.getText();
		String adminEmail = adminEmailInput.getText();
		String adminCpf = adminCpfInput.getText();
		System.out.println(adminName);
		System.out.println(adminEmail);
		System.out.println(adminCpf);
		Number authId = authDao.insert(adminEmail, "123456", "ADMIN");
		adminDAO.toDBSet(adminName, adminEmail, adminCpf, authId);
		MainController.changeScene("list");
	}
}
