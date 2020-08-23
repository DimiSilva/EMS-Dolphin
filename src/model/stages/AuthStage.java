package model.stages;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.interfaces.IStage;

public class AuthStage implements IStage {
	private Stage stage;
	
	private Scene loginScene;
	
	public AuthStage() {
		stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
	    stage.setTitle("EMS Dolphin Auth");
	    stage.hide();
		
		try {
			loginScene =  new Scene(FXMLLoader.load(getClass().getResource("/view/Login/LoginView.fxml")));
		    loginScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
	    stage.setScene(loginScene);
	    stage.show();
	}
	
	@Override
	public void hide() {
		if(stage.isShowing())
			stage.hide();
	}
	
	public void loadScene(String sceneName) {
		switch (sceneName) {
			case "login":
			
				break;
		}
	}
}
