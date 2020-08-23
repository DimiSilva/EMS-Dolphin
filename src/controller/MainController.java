package controller;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.interfaces.IStage;
import model.stages.AuthStage;
import model.stages.AdminStage;

public class MainController extends Application {

	private static IStage authStage;
	private static IStage mainStage;
	
	public static IStage currentStage;
	
    @Override
    public void start(Stage primaryStage) {
    	authStage = new AuthStage();
    	mainStage = new AdminStage();
    	
    	MainController.changeStage("auth");
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static void changeStage(String stageName) {
    	stageName.toLowerCase();
    	
    	switch (stageName) {
    		case "auth":
    			if(currentStage != null)
    				currentStage.hide();
    			currentStage = authStage;
    			authStage.load();
    			break;
    		case "main":
    			if(currentStage != null)
    				currentStage.hide();
    			currentStage = mainStage;
    			mainStage.load();
    			break;
    		default:
    			System.out.println("Stage not found");
    	}
    }
}