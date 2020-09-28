package controller.Admin;

import java.net.URL;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.entities.CostCenter;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.CostCenterDAO;

public class CostCenterFormController implements Initializable {

	CostCenterDAO costCenterDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextArea descriptionInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingCostCenterId;
	@FXML
	private CostCenter updatingCostCenter;
	
	@FXML
	private Labeled messageError;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		costCenterDAO = new CostCenterDAO();
		
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("costCentersList"));
	}
	
	@FXML
	public void register() {
		if(this.validateForm() == true) {
			try {
				CostCenter costCenter = new CostCenter(nameInput.getText(), descriptionInput.getText());
				costCenterDAO.insert(costCenter);
				
				MainController.changeScene("costCentersList");
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
				this.updatingCostCenter.update(this.nameInput.getText(), this.descriptionInput.getText());
				
				costCenterDAO.update(this.updatingCostCenter);
				
				MainController.changeScene("costCentersList");
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
		}
	}
	
	public void updatingCostCenterId(int updatingCostCenterId) {
		this.updatingCostCenterId = updatingCostCenterId;
	}
	
	public boolean validateForm() {

		if(
			this.nameInput.getText() != null && 
			this.descriptionInput.getText() != null
		){
			if(
				this.nameInput.getText().length() > 0 && 
				this.descriptionInput.getText().length() > 0
			){
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
		this.updatingCostCenter = null;
		this.nameInput.setText(null);
		this.descriptionInput.setText(null);
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar centro de custo");
		this.messageError.setText("");
	}
	
	public void loadUpdatingCostCenterById(int updatingCostCenterId) {
		this.updatingCostCenterId = updatingCostCenterId;
		
		try {
			this.updatingCostCenter = costCenterDAO.getById(String.valueOf(this.updatingCostCenterId));
			this.nameInput.setText(this.updatingCostCenter.getName());
			this.descriptionInput.setText(this.updatingCostCenter.getDescription());
			
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar centro de custo");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
