package model.stages;

import java.io.IOException;

import controller.MainController;
import controller.Contributor.AppointmentFormController;
import controller.Contributor.AppointmentsListController;
import controller.Contributor.DashboardController;
import controller.Contributor.ProfileController;
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
	
	private FXMLLoader profile;
	private VBox profileView;
	
	private FXMLLoader dashboard;
	private VBox dashboardView;
	
	private FXMLLoader appointmentsList;
	private VBox appointmentsListView;
	
	private FXMLLoader appointmentForm;
	private VBox appointmentFormView;
	
	private FXMLLoader company;
	private VBox companyView;
	
	public ContributorStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayout = new FXMLLoader(getClass().getResource("/view/BaseLayout/View.fxml"));
			baseLayoutView = baseLayout.load();
			
			profile = new FXMLLoader(getClass().getResource("/view/Contributor/Profile/View.fxml"));
			profileView = (VBox)((HBox)profile.load()).getChildren().get(0);
			
			dashboard = new FXMLLoader(getClass().getResource("/view/Contributor/Dashboard/View.fxml"));
			dashboardView = (VBox)((HBox)dashboard.load()).getChildren().get(0);
			
			appointmentsList = new FXMLLoader(getClass().getResource("/view/Contributor/AppointmentsList/View.fxml"));
			appointmentsListView = (VBox)((HBox)appointmentsList.load()).getChildren().get(0);
			
			appointmentForm = new FXMLLoader(getClass().getResource("/view/Contributor/AppointmentForm/View.fxml"));
			appointmentFormView = (VBox)((HBox)appointmentForm.load()).getChildren().get(0);
			
			company = new FXMLLoader(getClass().getResource("/view/Contributor/Company/View.fxml"));
			companyView = (VBox)((HBox)company.load()).getChildren().get(0);
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
		profileBtn.setOnMouseClicked(e -> loadScene("profile"));
		profileBtn.setPrefHeight(25.0);
		profileBtn.setPrefWidth(299.0);
		profileBtn.getStyleClass().add("ems-btn");
		
		Button dashboardBtn = new Button("Home");
		dashboardBtn.setId("dashboardBtn");
		dashboardBtn.setOnMouseClicked(e -> loadScene("dashboard"));
		dashboardBtn.setPrefHeight(25.0);
		dashboardBtn.setPrefWidth(299.0);
		dashboardBtn.getStyleClass().add("ems-btn");
		
		Button appointmentsBtn = new Button("Meus Apontamentos");
		appointmentsBtn.setOnMouseClicked(e -> loadScene("appointmentsList"));
		appointmentsBtn.setPrefHeight(25.0);
		appointmentsBtn.setPrefWidth(299.0);
		appointmentsBtn.getStyleClass().add("ems-btn");
		
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
			
		menu.getChildren().addAll(profileBtn, dashboardBtn, appointmentsBtn, companyBtn, logoutBtn);
		VBox.setMargin(profileBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(dashboardBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(appointmentsBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(companyBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(logoutBtn, new Insets(16, 0, 0, 0));
	}
	
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}
	
	public void loadScene(String sceneName) {
		switch (sceneName) {
			case "profile":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(profileView);
				ProfileController profileController = profile.getController();
				profileController.loadProfile();
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
			case "appointmentsList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(appointmentsListView);
				AppointmentsListController appointmentsListController = appointmentsList.getController();
				appointmentsListController.fetchAppoitments();
				stage.show();
				break;
			case "appointmentForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(appointmentFormView);
				AppointmentFormController appointmentFormController = appointmentForm.getController();
				appointmentFormController.reset();
				stage.show();
				break;
			case "company":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(companyView);
				stage.show();
				break;
		}
	}
	
	public void loadScene(String sceneName, int id) {
		switch (sceneName) {
			case "appointmentForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(appointmentFormView);
				AppointmentFormController appointmentFormController = appointmentForm.getController();
				appointmentFormController.reset();
				appointmentFormController.loadUpdatingAppointment(id);
				stage.show();
				break;
		}
	}
}
