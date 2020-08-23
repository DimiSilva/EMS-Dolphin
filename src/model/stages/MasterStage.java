package model.stages;

import java.io.IOException;

import controller.AdminDashboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	private Parent dashboardView;
	
	public MasterStage() {
		stage = new Stage();
		stage.setMaximized(true);
		stage.setResizable(false);;
	    stage.setTitle("EMS Dolphin");
	    stage.hide();
	    
		try {
			baseLayoutView = FXMLLoader.load(getClass().getResource("/view/BaseLayout/BaseLayoutView.fxml"));
			menu = (VBox)baseLayoutView.lookup("#menu");
			
			dashboardView = FXMLLoader.load(getClass().getResource("/view/Contributor/ContributorDashboard/ContributorDashboardView.fxml"));
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() { 
		this.loadScene("dashboard");
		stage.show();
	}
	
	@Override
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}
	
	
	public void loadScene(String sceneName) {
		switch (sceneName) {
			case "dashboard":
				baseLayoutView.getChildren().set(1, dashboardView);
				Scene baseLayout =  new Scene(baseLayoutView);
			    stage.setScene(baseLayout);
				break;
		}
	}
}
