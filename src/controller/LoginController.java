package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable {

	@FXML
	private VBox vboxLoginContainer;
	private double vboxLoginContainerXOffset;
	private double vboxLoginContainerYOffset;
	
	
	@FXML
	private AnchorPane anchorPaneTopBar;
	
	@FXML
	private ImageView imageViewClose;
	
	
	@FXML
	public void anchorPaneTopBarOnMousePressed() {
		System.out.println("teste");
	}
	
	@FXML
	void anchorPaneTopBarOnDragDropped () {
		
	}
	
	@FXML
	public void imageViewCloseOnClick() {
		Platform.exit();
		System.exit(0);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
