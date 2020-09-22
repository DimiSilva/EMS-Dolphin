package controller.Admin;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DTOs.ContributorsWorkedHoursInYearByMonthData;
import model.DTOs.ProjectsWorkedHours;
import model.entities.Admin;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AuthDAO;
import model.persistence.DashboardDAO;

public class CostCentersListController implements Initializable {

	private AdminDAO adminDAO;
	private AuthDAO authDAO;
	
	private int currentPage = 1;
	private int perPage = 20;
	private int totalPages = 0;
	
	@FXML
	private Button goToRegisterScreenButton;
	@FXML
	private TableView<Admin> table;
	
	@FXML
	private TableColumn<Admin, Integer> idColumn;
	@FXML
	private TableColumn<Admin, String> nameColumn;
	@FXML
	private TableColumn<Admin, String> descriptionColumn;
	@FXML
	private TableColumn<Admin, Date> createDateColumn;
	@FXML
	private TableColumn<Admin, Date> updateDateColumn;
	@FXML
	private TableColumn<Admin, HBox> actionsColumn;
	
	@FXML
	private Text currentPageText;
	@FXML
	private Text pagesText;
	@FXML
	private Button firstPageButton;
	@FXML
	private Button backPageButton; 
	@FXML
	private Button nextPageButton;
	@FXML
	private Button lastPageButton;
	
	ObservableList<Admin> tableItems = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		adminDAO = new AdminDAO();
		authDAO = new AuthDAO();
		goToRegisterScreenButton.setOnMouseClicked(e -> goToRegister());
		firstPageButton.setOnMouseClicked(e -> this.updateTableData(1));
		backPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage - 1));
		lastPageButton.setOnMouseClicked(e -> this.updateTableData(this.totalPages));
		nextPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage + 1));
		
		
		this.fetchCostCenters(); 
	}
	
	public void goToRegister() {
		MainController.changeScene("costCenterForm");
	} 
	
	public void updateTableData(Integer page) {
		if(page != null && page > 0 && page < this.totalPages) this.currentPage = page;
		this.fetchCostCenters();
	} 
	public void deleteCostCenters(int id, int authId, String name) {	
		if(Utils.showConfirmAlert("Atenção", "Deseja mesmo apagar o usuário " + name , "Apagar", "Cancelar") == true) {
			try {
				adminDAO.remove(String.valueOf(id));
				authDAO.remove(String.valueOf(authId));
				fetchCostCenters();
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
		}

	} 
	
	
	public void fetchCostCenters() {
		try {
			List<Admin> admins = adminDAO.getPaged(this.currentPage, this.perPage);
			int totalItems = adminDAO.count();
			
			tableItems = FXCollections.observableArrayList(admins);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		//	emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		//	cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
			updateDateColumn.setCellValueFactory(new PropertyValueFactory<>("updateDate"));
		
			actionsColumn.setCellFactory(params -> new TableCell<Admin, HBox>() {
				  @Override
				    protected void updateItem(HBox hbox, boolean empty) {
				       super.updateItem(hbox, empty);
				       if(getIndex() == -1 || tableItems.size() < getIndex() + 1) {
				    	   return;
				       }
				       Admin admin = tableItems.get(getIndex());
				       Button editButton = new Button();
				       ImageView iconEdit = new ImageView("assets/icons/edit_icon.png");
				       iconEdit.setFitHeight(16);
				       iconEdit.setFitWidth(16);
				       editButton.getStyleClass().add("btn-list-actions");
				       editButton.setGraphic(iconEdit);
				       editButton.setOnMouseClicked(e ->  MainController.changeScene("adminsRegister", admin.getId()));
				       Button deleteButton = new Button();
				       ImageView iconDelete = new ImageView("assets/icons/delete_icon.png");
				       iconDelete.setFitHeight(16);
				       iconDelete.setFitWidth(16);
				       deleteButton.setGraphic(iconDelete);
				    
				       deleteButton.getStyleClass().add("btn-list-actions");
				       deleteButton.setOnMouseClicked(e -> deleteCostCenters(admin.getId(), admin.getAuthId(), admin.getName()));
				      
				        HBox pane = new HBox(editButton, deleteButton);
				        setGraphic(pane);
				    }
			});
				
			table.setItems(tableItems);
			
			this.totalPages = (int)Math.ceil((float)totalItems / this.perPage);
			this.currentPageText.setText(String.valueOf(this.currentPage));
			this.pagesText.setText(String.valueOf(this.totalPages));
		} catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
