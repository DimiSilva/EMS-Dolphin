package model.stages;

import java.io.IOException;

import controller.MainController;
import controller.Master.AdminsListController;
import controller.Master.AdminsRegisterController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.interfaces.IStage;

public class MasterStage implements IStage {
	Stage stage;
	
	private FXMLLoader baseLayout;
	private HBox baseLayoutView;
	
	private FXMLLoader adminsList;
	private VBox adminsListView;
	
	private FXMLLoader adminsRegister;
	private VBox adminsRegisterView;
	
	public MasterStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayout = new FXMLLoader(getClass().getResource("/view/BaseLayout/View.fxml"));
			baseLayoutView = baseLayout.load();
			
			adminsList = new FXMLLoader(getClass().getResource("/view/Master/Admins/ListView.fxml"));
			adminsListView = (VBox)((HBox)adminsList.load()).getChildren().get(0);
			
			adminsRegister = new FXMLLoader(getClass().getResource("/view/Master/Admins/RegisterView.fxml"));
			adminsRegisterView = (VBox)((HBox)adminsRegister.load()).getChildren().get(0);
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			e.printStackTrace();
		}
	}
	
	public void load() {
		baseLayoutView.getChildren().add(adminsListView);
		Scene baseLayout =  new Scene(baseLayoutView);
	    stage.setScene(baseLayout);
	    stage.show();
	    this.renderMenu();
	}
	
	private void renderMenu () {
		VBox menu = (VBox)baseLayoutView.lookup("#menu");
			
		Button adminsBtn = new Button("Administradores");
		adminsBtn.setId("listAdminMastersBtn");
		adminsBtn.setOnMouseClicked(e -> loadScene("adminsList"));
		adminsBtn.setPrefHeight(25.0);
		adminsBtn.setPrefWidth(299.0);
		adminsBtn.getStyleClass().add("ems-btn");
		
		Button logoutBtn = new Button("Sair");
		logoutBtn.setOnMouseClicked(e -> MainController.closeApplication());
		logoutBtn.setId("logoutBtn");			
		logoutBtn.setPrefHeight(25.0);
		logoutBtn.setPrefWidth(299.0);
		logoutBtn.getStyleClass().add("ems-btn");
			
		menu.getChildren().addAll(adminsBtn, logoutBtn);
		VBox.setMargin(adminsBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(logoutBtn, new Insets(16, 0, 0, 0));
	}
	
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}

	public void loadScene(String sceneName) {
		switch (sceneName) {
			case "adminsList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(adminsListView);
					AdminsListController adminsListController = adminsList.getController();
					adminsListController.fetchAdmins();
					stage.show();
				break;
			case "adminsRegister":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(adminsRegisterView);
					AdminsRegisterController adminsRegisterController = adminsRegister.getController();
					adminsRegisterController.reset();
					stage.show();
				break;
			default:
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(adminsListView);
					stage.show();
				break;
		}
	}
	
	public void loadScene(String sceneName, int id) {
	
		switch (sceneName) {
			case "adminsRegister":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(adminsRegisterView);
					AdminsRegisterController adminsRegisterController = adminsRegister.getController();
					adminsRegisterController.loadUpdatingUserById(id);
					stage.show();
				break;
		}
	}

}
