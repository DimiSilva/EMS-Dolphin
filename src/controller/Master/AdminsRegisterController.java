package controller.Master;

import java.net.URL;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.entities.Admin;
import model.entities.Auth;
import model.enums.AccessTypes;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.exceptions.InvalidFieldException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AuthDAO;

public class AdminsRegisterController implements Initializable {
	private AdminDAO adminDAO;
	private AuthDAO authDAO;
	
	
	
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
	
	@FXML
	public void update() {
		try {
			this.updatingUser.update(this.nameInput.getText(), this.emailInput.getText(), this.cpfInput.getText());
			
			adminDAO.update(this.updatingUser);
			
			MainController.changeScene("adminsList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
		catch(InvalidFieldException e) {
			Utils.showErrorAlert("Erro!", e.message, null);
		}
	}


	public void setUpdatingUserId(int updatingUserId) {
		this.updatingUserId = updatingUserId;
	}
	
	public void reset() {
		this.updatingUser = null;
		this.nameInput.setText(null);
		this.emailInput.setText(null);
		this.cpfInput.setText(null);
		this.passwordInput.setText(null);
		this.passwordContainer.setVisible(true);
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar Administrador");
	}
	public void loadUpdatingUserById(int updatingUserId) {
	
		this.updatingUserId = updatingUserId;
		
		try {
			this.updatingUser = adminDAO.getById(String.valueOf(this.updatingUserId));
			this.nameInput.setText(this.updatingUser.getName());
			this.emailInput.setText(this.updatingUser.getEmail());
			this.cpfInput.setText(this.updatingUser.getCpf());
			this.passwordContainer.setVisible(false);
			
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar Administrador");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
