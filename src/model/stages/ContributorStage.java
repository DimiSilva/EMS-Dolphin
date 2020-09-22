package model.stages;

import java.io.IOException;

import controller.MainController;
import controller.Contributor.DashboardController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.interfaces.IStage;

public class ContributorStage implements IStage {
	Stage stage;
	
	private FXMLLoader baseLayout;
	private HBox baseLayoutView;
	
	private FXMLLoader dashboard;
	private VBox dashboardView;
	
	public ContributorStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayout = new FXMLLoader(getClass().getResource("/view/BaseLayout/View.fxml"));
			baseLayoutView = baseLayout.load();
			
			dashboard = new FXMLLoader(getClass().getResource("/view/Contributor/Dashboard/View.fxml"));
			dashboardView = (VBox)((HBox)dashboard.load()).getChildren().get(0);
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			e.printStackTrace();
		}
	}
	
	public void load() {
		baseLayoutView.getChildren().add(dashboardView);
		Scene baseLayout =  new Scene(baseLayoutView);
	    stage.setScene(baseLayout);
	    stage.show();
		DashboardController dashboardController = dashboard.getController();
		dashboardController.loadDashboardData();
	    this.renderMenu();
	}
	
	private void renderMenu () {
		VBox menu = (VBox)baseLayoutView.lookup("#menu");
			
		Button dashboardBtn = new Button("Dashboard");
		dashboardBtn.setId("dashboardBtn");
		dashboardBtn.setOnMouseClicked(e -> loadScene("dashboard"));
		dashboardBtn.setPrefHeight(25.0);
		dashboardBtn.setPrefWidth(299.0);
		dashboardBtn.getStyleClass().add("ems-btn");
		
		Button logoutBtn = new Button("Sair");
		logoutBtn.setOnMouseClicked(e -> MainController.closeApplication());
		logoutBtn.setId("logoutBtn");		
		logoutBtn.setPrefHeight(25.0);
		logoutBtn.setPrefWidth(299.0);
		logoutBtn.getStyleClass().add("ems-btn");
			
		menu.getChildren().addAll(dashboardBtn, logoutBtn);
		VBox.setMargin(dashboardBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(logoutBtn, new Insets(16, 0, 0, 0));
	}

	
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}
	
	public void loadScene(String sceneName) {
		switch (sceneName) {
		case "dashboard":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(dashboardView);
			DashboardController dashboardController = dashboard.getController();
			dashboardController.loadDashboardData();
			stage.show();
			break;
		}
	}
	
	public void loadScene(String sceneName, int id) {
		switch (sceneName) {
		case "dashboard":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(dashboardView);
			DashboardController dashboardController = dashboard.getController();
			dashboardController.loadDashboardData();
			stage.show();
			break;
		}
	}
}
