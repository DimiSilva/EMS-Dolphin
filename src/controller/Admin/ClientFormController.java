package controller.Admin;

import java.net.URL;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.entities.Client;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.exceptions.InvalidFieldException;
import model.helpers.Utils;
import model.persistence.ClientDAO;

public class ClientFormController implements Initializable {
	private ClientDAO clientDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField emailInput;
	@FXML
	private TextField cnpjInput;
	@FXML
	private TextField phoneInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingClientrId;
	@FXML
	private Client updatingClient;
	
	@FXML
	private Labeled messageError;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientDAO = new ClientDAO();
		
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("clientsList"));
	}
	
	@FXML
	public void register() {
		if(this.validateForm() == true) {
			try {
				Client client = new Client(nameInput.getText(), phoneInput.getText(), emailInput.getText(), cnpjInput.getText());
				clientDAO.insert(client);
				
				MainController.changeScene("clientsList");
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
		}
	}
	
	@FXML
	public void update() {
		if(this.validateForm() == true) {
			try {
				this.updatingClient.update(this.nameInput.getText(), this.emailInput.getText(), this.cnpjInput.getText(), this.phoneInput.getText());
				
				clientDAO.update(this.updatingClient);			
				MainController.changeScene("clientsList");
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
		this.updatingClientrId = updatingClientrId;
	}
	
	public boolean validateForm() {
		if(
			this.nameInput.getText() != null && 
			this.emailInput.getText() != null && 
			this.cnpjInput.getText() != null && 
			this.phoneInput.getText() != null
		){
			if(
				this.nameInput.getText().length() > 0 && 
				this.emailInput.getText().length() > 0 && 
				this.cnpjInput.getText().length() > 0 && 
				this.phoneInput.getText().length() > 0 
				
			){
				if(this.cnpjInput.getText().length() != 14) {
					this.messageError.setText("CNPJ inválido!");
					return false;
				}
				if(this.phoneInput.getText().length() < 10) {
					this.messageError.setText("Telefone inválido!");
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
	public void reset() {
		this.updatingClient = null;
		this.nameInput.setText(null);
		this.emailInput.setText(null);
		this.cnpjInput.setText(null);
		this.phoneInput.setText(null);
		this.passwordContainer.setVisible(true);
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar Cliente");
		this.messageError.setText("");
	}
	public void loadUpdatingClientById(int updatingClientrId) {
		this.updatingClientrId = updatingClientrId;
		
		try {
			this.updatingClient = clientDAO.getById(String.valueOf(this.updatingClientrId));
			this.nameInput.setText(this.updatingClient.getName());
			this.emailInput.setText(this.updatingClient.getEmail());
			this.cnpjInput.setText(this.updatingClient.getCnpj());
			this.phoneInput.setText(this.updatingClient.getPhone());
	
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar Cliente");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
}
