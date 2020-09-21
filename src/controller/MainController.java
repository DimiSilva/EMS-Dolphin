package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.interfaces.IStage;
import model.stages.AuthStage;
import model.stages.MasterStage;
import model.stages.AdminStage;

public class MainController extends Application {

	private static IStage authStage;
	private static IStage adminStage;
	private static IStage masterStage;
	
	public static IStage currentStage;
	
	
    @Override
    public void start(Stage primaryStage) {
    	authStage = new AuthStage();
    	adminStage = new AdminStage();
    	masterStage = new MasterStage();
    	
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
    		case "admin":
    			if(currentStage != null)
    				currentStage.hide();
    			currentStage = adminStage;
    			adminStage.load();
    			break;
    		case "master":
    			if(currentStage != null)
    				currentStage.hide();
    			currentStage = masterStage;
    			masterStage.load();
    			break;
    		default:
    			System.out.println("Stage not found");
    	}
    }
    
    public static void changeScene(String sceneName) {
    	currentStage.loadScene(sceneName);
    }
    
    public static void changeScene(String sceneName, int id) {
    	System.out.println("changeScene " + id);
    	currentStage.loadScene(sceneName, id);
    }
    
	public static void closeApplication() {
		Platform.exit();
		System.exit(0);
	}
}