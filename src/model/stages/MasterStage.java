package model.stages;

import java.io.IOException;

import controller.MainController;
import controller.Admin.DashboardController;
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
	
	private VBox adminsListView;
	private HBox adminsRegisterView;
	//private VBox listView;
	
	public MasterStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayoutView = FXMLLoader.load(getClass().getResource("/view/BaseLayout/View.fxml"));
			
			adminsListView = (VBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/Admins/ListView.fxml"))).getChildren().get(0);
			adminsRegisterView = (HBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/Admins/RegisterView.fxml"))).getChildren().get(0);
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
	    this.renderMenu();
	}
	
	private void renderMenu () {
		VBox menu = (VBox)baseLayoutView.lookup("#menu");
			
		Button listAdminMastersBtn = new Button("Lista de Administradores");
		listAdminMastersBtn.setId("listAdminMastersBtn");
		listAdminMastersBtn.setOnMouseClicked(e -> loadScene("adminsList"));
		listAdminMastersBtn.setPrefHeight(25.0);
		listAdminMastersBtn.setPrefWidth(299.0);
		listAdminMastersBtn.getStyleClass().add("ems-btn");
		
		Button logoutBtn = new Button("Sair");
		logoutBtn.setOnMouseClicked(e -> MainController.closeApplication());
		logoutBtn.setId("logoutBtn");			
		logoutBtn.setPrefHeight(25.0);
		logoutBtn.setPrefWidth(299.0);
		logoutBtn.getStyleClass().add("ems-btn");
			
		menu.getChildren().addAll(listAdminMastersBtn,logoutBtn );
		VBox.setMargin(listAdminMastersBtn, new Insets(16, 0, 0, 0));
		VBox.setMargin(logoutBtn, new Insets(16, 0, 0, 0));
	}
	
	@Override
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
					stage.show();
				break;
			case "adminsRegister":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
					baseLayoutView.getChildren().add(adminsRegisterView);
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
