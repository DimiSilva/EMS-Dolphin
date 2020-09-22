package model.stages;

import java.io.IOException;

import controller.Auth.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.entities.Auth;
import model.enums.AccessTypes;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.interfaces.IBaseEntity;
import model.interfaces.IBaseUser;
import model.interfaces.IStage;
import model.persistence.AdminDAO;
import model.persistence.ContributorDAO;
import model.persistence.MasterDAO;

public class AuthStage implements IStage {
	private AdminDAO adminDAO;
	private ContributorDAO contributorDAO;
	private MasterDAO masterDAO;
	
	private Stage stage;
	
	private Scene loginScene;
	
	public static IBaseUser loggedUser;
	private static String loggedUserAccessType;
	
	public AuthStage() {
		adminDAO = new AdminDAO();
		contributorDAO = new ContributorDAO();
		masterDAO = new MasterDAO();
		
		stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
	    stage.setTitle("EMS Dolphin Auth");
	    stage.hide();
		
		try {
			FXMLLoader login = new FXMLLoader(getClass().getResource("/view/Auth/Login/View.fxml"));
			loginScene =  new Scene(login.load());
			LoginController loginController = login.getController();
			loginController.setStage(this);
		    loginScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			e.printStackTrace();
		}
	}
	
	public void updateLoggedUser(Auth loggedUserAuth) {
		AuthStage.loggedUserAccessType = loggedUserAuth.getAccessType();
		try {
			switch(AccessTypes.valueOf(loggedUserAuth.getAccessType())) {
			case ADMIN:
				AuthStage.loggedUser = adminDAO.getByAuthId(String.valueOf(loggedUserAuth.getId()));
				break;
			case CONTRIBUTOR:
				AuthStage.loggedUser = contributorDAO.getByAuthId(String.valueOf(loggedUserAuth.getId()));
				break;
			case MASTER:
				AuthStage.loggedUser = masterDAO.getByAuthId(String.valueOf(loggedUserAuth.getId()));
				break;
			}			
		}
		catch (DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	public void updateLoggedUser() {
		if(AuthStage.loggedUser == null) return;
		
		try {
			switch(AccessTypes.valueOf(AuthStage.loggedUserAccessType)) {
			case ADMIN:
				AuthStage.loggedUser = adminDAO.getById(String.valueOf(AuthStage.loggedUser.getId()));
				break;
			case CONTRIBUTOR:
				AuthStage.loggedUser = contributorDAO.getById(String.valueOf(AuthStage.loggedUser.getId()));
				break;
			case MASTER:
				AuthStage.loggedUser = masterDAO.getById(String.valueOf(AuthStage.loggedUser.getId()));
				break;
			}			
		}
		catch (DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
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
