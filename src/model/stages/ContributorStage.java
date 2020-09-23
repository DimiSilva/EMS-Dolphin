package model.stages;

import java.io.IOException;

import controller.MainController;
import controller.Contributor.ContributorAppointmentFormController;
import controller.Contributor.ContributorAppointmentsListController;
import controller.Contributor.ContributorCompanyController;
import controller.Contributor.ContributorProfileController;
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
	
	private FXMLLoader contributorProfile;
	private VBox contributorProfileView;
	
	private FXMLLoader dashboard;
	private VBox dashboardView;
	
	private FXMLLoader contributorAppointmentsList;
	private VBox contributorAppointmentsListView;
	
	private FXMLLoader contributorAppointmentForm;
	private VBox contributorAppointmentFormView;
	
	private FXMLLoader contributorCompany;
	private VBox contributorCompanyView;
	
	public ContributorStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayout = new FXMLLoader(getClass().getResource("/view/BaseLayout/View.fxml"));
			baseLayoutView = baseLayout.load();
			
			contributorProfile = new FXMLLoader(getClass().getResource("/view/Contributor/Profile/View.fxml"));
			contributorProfileView = (VBox)((HBox)contributorProfile.load()).getChildren().get(0);
			
			dashboard = new FXMLLoader(getClass().getResource("/view/Contributor/Dashboard/View.fxml"));
			dashboardView = (VBox)((HBox)dashboard.load()).getChildren().get(0);
			
			contributorAppointmentsList = new FXMLLoader(getClass().getResource("/view/Contributor/AppointmentsList/View.fxml"));
			contributorAppointmentsListView = (VBox)((HBox)contributorAppointmentsList.load()).getChildren().get(0);
			
			contributorAppointmentForm = new FXMLLoader(getClass().getResource("/view/Contributor/AppointmentForm/View.fxml"));
			contributorAppointmentFormView = (VBox)((HBox)contributorAppointmentForm.load()).getChildren().get(0);
			
			contributorCompany = new FXMLLoader(getClass().getResource("/view/Contributor/Company/View.fxml"));
			contributorCompanyView = (VBox)((HBox)contributorCompany.load()).getChildren().get(0);
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
		
		Button profileBtn = new Button("Meu Perfil");
		profileBtn.setOnMouseClicked(e -> loadScene("contributorProfile"));
		profileBtn.setPrefHeight(25.0);
		profileBtn.setPrefWidth(299.0);
		profileBtn.getStyleClass().add("ems-btn");
		
		Button dashboardBtn = new Button("Home");
		dashboardBtn.setId("dashboardBtn");
		dashboardBtn.setOnMouseClicked(e -> loadScene("home"));
		dashboardBtn.setPrefHeight(25.0);
		dashboardBtn.setPrefWidth(299.0);
		dashboardBtn.getStyleClass().add("ems-btn");
		
		Button contributorAppointmentsBtn = new Button("Meus Apontamentos");
		contributorAppointmentsBtn.setOnMouseClicked(e -> loadScene("contributorAppointmentsList"));
		contributorAppointmentsBtn.setPrefHeight(25.0);
		contributorAppointmentsBtn.setPrefWidth(299.0);
		contributorAppointmentsBtn.getStyleClass().add("ems-btn");
		
		Button companyBtn = new Button("Empresa");
		companyBtn.setOnMouseClicked(e -> loadScene("company"));
		companyBtn.setPrefHeight(25.0);
		companyBtn.setPrefWidth(299.0);
		companyBtn.getStyleClass().add("ems-btn");
		
		Button logoutBtn = new Button("Sair");
		logoutBtn.setOnMouseClicked(e -> MainController.closeApplication());
		logoutBtn.setId("logoutBtn");		
		logoutBtn.setPrefHeight(25.0);
		logoutBtn.setPrefWidth(299.0);
		logoutBtn.getStyleClass().add("ems-btn");
			
		menu.getChildren().addAll(profileBtn, dashboardBtn,contributorAppointmentsBtn, companyBtn, logoutBtn);
		VBox.setMargin(profileBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(dashboardBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(contributorAppointmentsBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(companyBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(logoutBtn, new Insets(16, 0, 0, 0));
	}
	
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}
	
	public void loadScene(String sceneName) {
		switch (sceneName) {
		case "contributorProfile":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorProfileView);
			ContributorProfileController contributorProfileController = contributorProfile.getController();
			contributorProfileController.loadProfile();
			stage.show();
			break;
		case "dashboard":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(dashboardView);
			DashboardController dashboardController = dashboard.getController();
			dashboardController.loadDashboardData();
			stage.show();
			break;
		case "contributorAppointmentsList":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorAppointmentsListView);
			ContributorAppointmentsListController contributorAppointmentsListController = contributorAppointmentsList.getController();
			contributorAppointmentsListController.loadDashboardData();
			stage.show();
			break;
		case "contributorAppointmentForm":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorAppointmentFormView);
			ContributorAppointmentFormController contributorAppointmentFormController = contributorAppointmentForm.getController();
			contributorAppointmentFormController.loadDashboardData();
			stage.show();
			break;
		case "contributorCompany":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorCompanyView);
			ContributorCompanyController contributorCompanyController = contributorCompany.getController();
			contributorCompanyController.loadDashboardData();
			stage.show();
			break;
		}

	}
	
	public void loadScene(String sceneName, int id) {
		switch (sceneName) {
		case "contributorProfile":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorProfileView);
			ContributorProfileController contributorProfileController = contributorProfile.getController();
			contributorProfileController.loadProfile();
			stage.show();
			break;
		case "dashboard":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(dashboardView);
			DashboardController dashboardController = dashboard.getController();
			dashboardController.loadDashboardData();
			stage.show();
			break;
		case "contributorAppointmentsList":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorAppointmentsListView);
			ContributorAppointmentsListController contributorAppointmentsListController = contributorAppointmentsList.getController();
			contributorAppointmentsListController.loadDashboardData();
			stage.show();
			break;
		case "contributorAppointmentForm":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorAppointmentFormView);
			ContributorAppointmentFormController contributorAppointmentFormController = contributorAppointmentForm.getController();
			contributorAppointmentFormController.loadDashboardData();
			stage.show();
			break;
		case "contributorCompany":
			if(baseLayoutView.getChildren().toArray().length == 2)
				baseLayoutView.getChildren().remove(1);
			baseLayoutView.getChildren().add(contributorCompanyView);
			ContributorCompanyController contributorCompanyController = contributorCompany.getController();
			contributorCompanyController.loadDashboardData();
			stage.show();
			break;
		}
	}
}
