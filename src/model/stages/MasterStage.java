package model.stages;

import java.io.IOException;

import controller.AdminDashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.interfaces.IStage;

public class MasterStage implements IStage {
	Stage stage;
	
	private HBox baseLayoutView;
	private VBox menu;
	
	private VBox dashboardView;
	private VBox registerView;
	private VBox listView;
	
	public MasterStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayoutView = FXMLLoader.load(getClass().getResource("/view/BaseLayout/BaseLayoutView.fxml"));
			menu = (VBox)baseLayoutView.lookup("#menu");
			
			dashboardView = (VBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/MasterDashboard/MasterDashboardView.fxml"))).getChildren().get(0);
			registerView = (VBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/Registers/RegisterView.fxml"))).getChildren().get(0);
			listView = (VBox)((HBox)FXMLLoader.load(getClass().getResource("/view/Master/Lists/ListView.fxml"))).getChildren().get(0);
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		baseLayoutView.getChildren().add(dashboardView);
		Scene baseLayout =  new Scene(baseLayoutView);
	    stage.setScene(baseLayout);
	    stage.show();
	}
	
	@Override
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}
	
	// pode receber um id
	public void loadScene(String sceneName) {
		switch (sceneName) {
			case "dashboard":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(dashboardView);
				stage.show();
				break;
			case "register":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(registerView);
				stage.show();
				break;
			case "list":
				if(baseLayoutView.getChildren().toArray().length == 2)
					baseLayoutView.getChildren().remove(1);
				baseLayoutView.getChildren().add(listView);
				stage.show();
				break;
		}
	}
}
