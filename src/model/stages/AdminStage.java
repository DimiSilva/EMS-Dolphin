package model.stages;

import java.io.IOException;

import controller.MainController;
import controller.Admin.ClientFormController;
import controller.Admin.ClientsListController;
import controller.Admin.ContributorFormController;
import controller.Admin.ContributorsListController;
import controller.Admin.CostCenterFormController;
import controller.Admin.CostCentersListController;
import controller.Admin.ProfileController;
import controller.Admin.ProjectFormController;
import controller.Admin.RoleFormController;
import controller.Admin.RolesListController;
import controller.Master.AdminsRegisterController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.interfaces.IStage;

public class AdminStage implements IStage {
	Stage stage;
	
	private FXMLLoader baseLayout;
	private HBox baseLayoutView;
	
	private FXMLLoader profile;
	private VBox profileView;
	
	private FXMLLoader dashboard;
	private VBox dashboardView;
	
	private FXMLLoader projectsList;
	private VBox projectsListView;
	
	private FXMLLoader projectForm;
	private VBox projectFormView;
	
	private FXMLLoader contributorsList;
	private VBox contributorsListView;
	
	private FXMLLoader contributorForm;
	private VBox contributorFormView;
	
	private FXMLLoader clientsList;
	private VBox clientsListView;
	
	private FXMLLoader clientForm;
	private VBox clientFormView;
	
	private FXMLLoader costCentersList;
	private VBox costCentersListView;
	
	private FXMLLoader appointmentsList;
	private VBox appointmentsListView;
	
	private FXMLLoader costCenterForm;
	private VBox costCenterFormView;
	
	private FXMLLoader rolesList;
	private VBox rolesListView;
	
	private FXMLLoader roleForm;
	private VBox roleFormView;
	
	public AdminStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayout = new FXMLLoader(getClass().getResource("/view/BaseLayout/View.fxml"));
			baseLayoutView = baseLayout.load();
			
			profile = new FXMLLoader(getClass().getResource("/view/Admin/Profile/View.fxml"));
			profileView = (VBox)((HBox)profile.load()).getChildren().get(0);
			
			dashboard = new FXMLLoader(getClass().getResource("/view/Admin/Dashboard/View.fxml"));
			dashboardView = (VBox)((HBox)dashboard.load()).getChildren().get(0);
			
			appointmentsList = new FXMLLoader(getClass().getResource("/view/Admin/AppointmentsList/View.fxml"));
			appointmentsListView = (VBox)((HBox)appointmentsList.load()).getChildren().get(0);
			
			projectsList = new FXMLLoader(getClass().getResource("/view/Admin/ProjectsList/View.fxml"));
			projectsListView = (VBox)((HBox)projectsList.load()).getChildren().get(0);
			
			projectForm = new FXMLLoader(getClass().getResource("/view/Admin/ProjectForm/View.fxml"));
			projectFormView = (VBox)((HBox)projectForm.load()).getChildren().get(0);
			
			contributorsList = new FXMLLoader(getClass().getResource("/view/Admin/ContributorsList/View.fxml"));
			contributorsListView = (VBox)((HBox)contributorsList.load()).getChildren().get(0);
			
			contributorForm = new FXMLLoader(getClass().getResource("/view/Admin/ContributorForm/View.fxml"));
			contributorFormView = (VBox)((HBox)contributorForm.load()).getChildren().get(0);
			
			clientsList = new FXMLLoader(getClass().getResource("/view/Admin/ClientsList/View.fxml"));
			clientsListView = (VBox)((HBox)clientsList.load()).getChildren().get(0);
			
			clientForm = new FXMLLoader(getClass().getResource("/view/Admin/ClientForm/View.fxml"));
			clientFormView = (VBox)((HBox)clientForm.load()).getChildren().get(0);
			
			costCentersList = new FXMLLoader(getClass().getResource("/view/Admin/CostCentersList/View.fxml"));
			costCentersListView = (VBox)((HBox)costCentersList.load()).getChildren().get(0);
			
			costCenterForm = new FXMLLoader(getClass().getResource("/view/Admin/CostCenterForm/View.fxml"));
			costCenterFormView = (VBox)((HBox)costCenterForm.load()).getChildren().get(0);
			
			rolesList = new FXMLLoader(getClass().getResource("/view/Admin/RolesList/View.fxml"));
			rolesListView = (VBox)((HBox)rolesList.load()).getChildren().get(0);
			
			roleForm = new FXMLLoader(getClass().getResource("/view/Admin/RoleForm/View.fxml"));
			roleFormView = (VBox)((HBox)roleForm.load()).getChildren().get(0);
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
	    this.renderMenu();
	}
	
	private void renderMenu () {
		VBox menu = (VBox)baseLayoutView.lookup("#menu");
			
		Button profileBtn = new Button("Meu Perfil");
		profileBtn.setOnMouseClicked(e -> loadScene("profile"));
		profileBtn.setPrefHeight(25.0);
		profileBtn.setPrefWidth(299.0);
		profileBtn.getStyleClass().add("ems-btn");
		
		Button dashboardBtn = new Button("Dashboard");
		dashboardBtn.setOnMouseClicked(e -> loadScene("dashboard"));
		dashboardBtn.setPrefHeight(25.0);
		dashboardBtn.setPrefWidth(299.0);
		dashboardBtn.getStyleClass().add("ems-btn");
		
		Button appointmentsBtn = new Button("Apontamentos");
		appointmentsBtn.setOnMouseClicked(e -> loadScene("appointmentsList"));
		appointmentsBtn.setPrefHeight(25.0);
		appointmentsBtn.setPrefWidth(299.0);
		appointmentsBtn.getStyleClass().add("ems-btn");
		
		
		Button projectsBtn = new Button("Projetos");
		projectsBtn.setOnMouseClicked(e -> loadScene("projectsList"));
		projectsBtn.setPrefHeight(25.0);
		projectsBtn.setPrefWidth(299.0);
		projectsBtn.getStyleClass().add("ems-btn");
		
		Button contributorsBtn = new Button("Colaboradores");
		contributorsBtn.setOnMouseClicked(e -> loadScene("contributorsList"));
		contributorsBtn.setPrefHeight(25.0);
		contributorsBtn.setPrefWidth(299.0);
		contributorsBtn.getStyleClass().add("ems-btn");
		
		Button clientsBtn = new Button("Clientes");
		clientsBtn.setOnMouseClicked(e -> loadScene("clientsList"));
		clientsBtn.setPrefHeight(25.0);
		clientsBtn.setPrefWidth(299.0);
		clientsBtn.getStyleClass().add("ems-btn");
		
		Button costsCenterBtn = new Button("Centros de Custo");
		costsCenterBtn.setOnMouseClicked(e -> loadScene("costCentersList"));
		costsCenterBtn.setPrefHeight(25.0);
		costsCenterBtn.setPrefWidth(299.0);
		costsCenterBtn.getStyleClass().add("ems-btn");
		
		Button rolesBtn = new Button("Cargos");
		rolesBtn.setOnMouseClicked(e -> loadScene("rolesList"));
		rolesBtn.setPrefHeight(25.0);
		rolesBtn.setPrefWidth(299.0);
		rolesBtn.getStyleClass().add("ems-btn");
		
		Button logoutBtn = new Button("Sair");
		logoutBtn.setOnMouseClicked(e -> MainController.closeApplication());	
		logoutBtn.setPrefHeight(25.0);
		logoutBtn.setPrefWidth(299.0);
		logoutBtn.getStyleClass().add("ems-btn");
			
		menu.getChildren().addAll(profileBtn, dashboardBtn, appointmentsBtn, projectsBtn, contributorsBtn, clientsBtn, costsCenterBtn, rolesBtn, logoutBtn);
		VBox.setMargin(profileBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(dashboardBtn, new Insets(24, 0, 0, 0));
		VBox.setMargin(appointmentsBtn, new Insets(24, 0, 0, 0));
		VBox.setMargin(projectsBtn, new Insets(24, 0, 0, 0));
		VBox.setMargin(contributorsBtn, new Insets(24, 0, 0, 0));
		VBox.setMargin(clientsBtn, new Insets(24, 0, 0, 0));
		VBox.setMargin(costsCenterBtn, new Insets(24, 0, 0, 0));
		VBox.setMargin(rolesBtn, new Insets(24, 0, 0, 0));
		VBox.setMargin(logoutBtn, new Insets(24, 0, 0, 0));
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
					stage.show();
				break;
			case "appointmentsList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(appointmentsListView);
					stage.show();
				break;
			case "projectsList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(projectsListView);
					stage.show();
				break;
			case "projectForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(projectFormView);
					ProjectFormController projectFormController = projectForm.getController();
					projectFormController.reset();
					stage.show();
				break;
			case "contributorsList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(contributorsListView);
					ContributorsListController contributorsListController = contributorsList.getController();
					contributorsListController.fetchContributors();
					stage.show();
				break;
			case "contributorForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(contributorFormView);
					ContributorFormController contributorFormController = contributorForm.getController();
					contributorFormController.reset();	
					stage.show();
				break;
			case "clientsList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(clientsListView);
					ClientsListController clientsListController = clientsList.getController();
					clientsListController.fetchClients();
					stage.show();
				break;
			case "clientForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(clientFormView);
					ClientFormController clientFormController = clientForm.getController();
					clientFormController.reset();
					stage.show();
				break;
			case "costCentersList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(costCentersListView);
					CostCentersListController costCentersListController = costCentersList.getController();
					costCentersListController.fetchCostCenters();
					stage.show();
				break;
			case "costCenterForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(costCenterFormView);
					CostCenterFormController costCenterFormController = costCenterForm.getController();
					costCenterFormController.reset();
					stage.show();
				break;
			case "rolesList":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(rolesListView);
					RolesListController rolesListController = rolesList.getController();
					rolesListController.fetchRoles();
					stage.show();
				break;
			case "roleForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(roleFormView);
					RoleFormController roleFormController = roleForm.getController();
					roleFormController.reset();
					stage.show();
				break;
		}
	}
	
	public void loadScene(String sceneName, int id) {
		switch (sceneName) {
			case "projectForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(projectFormView);
					ProjectFormController projectFormController = projectForm.getController();
					projectFormController.reset();
					projectFormController.loadUpdatingProjectById(id);
					stage.show();
				break;
			case "contributorForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(contributorFormView);
					ContributorFormController contributorFormController = contributorForm.getController();
					contributorFormController.loadUpdatingContributorById(id);
					stage.show();
				break;
			case "clientForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(clientFormView);
					ClientFormController clientFormController = clientForm.getController();
    				clientFormController.reset();
					clientFormController.loadUpdatingClientById(id);
					stage.show();
				break;
			case "costCenterForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(costCenterFormView);
					CostCenterFormController costCenterFormController = costCenterForm.getController();
					costCenterFormController.reset();
					costCenterFormController.loadUpdatingCostCenterById(id);
					stage.show();
				break;
			case "roleForm":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(roleFormView);
					RoleFormController roleFormController = roleForm.getController();
					roleFormController.reset();
					roleFormController.loadUpdatingRoleById(id);
					stage.show();
				break;
		}
	}
}
