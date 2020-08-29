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
import model.stages.MasterStage;

public class MasterDashboardController implements Initializable {
	
	@FXML
	private Button buttonAdminRegister;
	
	@FXML
	private Button buttonAdminList;
	
	@FXML
	private Button buttonAdminUpdate;
	
	@FXML
	private Button buttonAdminDelete;
	
	@FXML
	private AnchorPane masterDashBoardAnchorPane;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttonAdminRegister.setOnMouseClicked(e -> renderRegister());
		buttonAdminList.setOnMouseClicked(e -> renderList());
	}
	
	@FXML
	public void renderRegister() {
		MainController.changeScene("register");
	}
	
	@FXML
	public void renderList() {
		MainController.changeScene("list");
	}
	

}
