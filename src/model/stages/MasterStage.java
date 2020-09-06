package model.stages;

import java.io.IOException;

import controller.AdminDashboardController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.interfaces.IStage;

public class MasterStage implements IStage {
	Stage stage;
	
	private HBox baseLayoutView;
	private VBox menu;
	
	private VBox adminsListView;
	private VBox registerAdminView;
	//private VBox listView;
	
	public MasterStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayoutView = FXMLLoader.load(getClass().getResource("/view/BaseLayout/BaseLayoutView.fxml"));
			
			adminsListView = (VBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/MasterAdminsListView.fxml"))).getChildren().get(0);
			registerAdminView = (VBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/RegisterAdminView.fxml"))).getChildren().get(0);
			//listView = (VBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/Lists/ListView.fxml"))).getChildren().get(0);
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		baseLayoutView.getChildren().add(adminsListView);
		Scene baseLayout =  new Scene(baseLayoutView);
	    stage.setScene(baseLayout);
	    stage.show();
		menu = (VBox)baseLayoutView.lookup("#menu");
		
		Button listAdminMastersBtn = new Button("Lista de Administradores");
		//Button newAdminMasterBtn = new Button("Cadastrar Novo Administrador");
		Button logoutBtn = new Button("Sair");
		
		//newAdminMasterBtn.setId("newAdminMasterBtn");
		logoutBtn.setId("logoutBtn");
		listAdminMastersBtn.setId("listAdminMastersBtn");
		listAdminMastersBtn.setOnMouseClicked(e -> loadScene("register"));
		//newAdminMasterBtn.setOnMouseClicked(e -> loadScene("register"));
		logoutBtn.setId("logoutBtn");
		listAdminMastersBtn.setId("listAdminMastersBtn");
		
		//newAdminMasterBtn.setPrefHeight(25.0);
		logoutBtn.setPrefHeight(25.0);
		listAdminMastersBtn.setPrefHeight(25.0);
		
		//newAdminMasterBtn.setPrefWidth(299.0);
		logoutBtn.setPrefWidth(299.0);
		listAdminMastersBtn.setPrefWidth(299.0);
		
		
		//newAdminMasterBtn.getStyleClass().add("ems-btn");
		logoutBtn.getStyleClass().add("ems-btn");
		listAdminMastersBtn.getStyleClass().add("ems-btn");
		
		
		
		menu.getStylesheets().add(0, "/view/Master/MasterView.css");
		menu.getChildren().addAll(listAdminMastersBtn,logoutBtn );
		VBox.setMargin(listAdminMastersBtn, new Insets(16, 0, 0, 0));
		//VBox.setMargin(newAdminMasterBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(logoutBtn, new Insets(16, 0, 0, 0));
	}
	
	@Override
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}
	
	// pode receber um id
	public void loadScene(String sceneName) {
		switch (sceneName) {
			case "list":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(adminsListView);
					stage.show();
				break;
			case "register":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(registerAdminView);
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
}
