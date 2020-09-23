package controller.Admin;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;

import javafx.scene.text.Text;

import model.entities.Admin;
import model.entities.Project;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AuthDAO;
import model.persistence.ProjectDAO;


public class ProjectsListController implements Initializable {
	private ProjectDAO projectDAO;
	
	private int currentPage = 1;
	private int perPage = 20;
	private int totalPages = 0;
	
	@FXML
	private Button goToRegisterScreenButton;
	@FXML
	private TableView<Project> table;
	
	@FXML
	private TableColumn<Project, Integer> idColumn;
	@FXML
	private TableColumn<Project, String> nameColumn;
	@FXML
	private TableColumn<Project, String> descriptionColumn;
	@FXML
	private TableColumn<Project, String> costCenterColumn;
	@FXML
	private TableColumn<Project, String> clientColumn;
	@FXML
	private TableColumn<Project, Date> initDateColumn;
	@FXML
	private TableColumn<Project, Date> endDateColumn;
	@FXML
	private TableColumn<Project, Date> createDateColumn;
	@FXML
	private TableColumn<Project, Date> updateDateColumn;
	@FXML
	private TableColumn<Project, HBox> actionsColumn;
	
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
	
	ObservableList<Project> tableItems = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		projectDAO = new ProjectDAO();
		goToRegisterScreenButton.setOnMouseClicked(e -> goToRegister());
		firstPageButton.setOnMouseClicked(e -> this.updateTableData(1));
		backPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage - 1));
		lastPageButton.setOnMouseClicked(e -> this.updateTableData(this.totalPages));
		nextPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage + 1));
		
		this.fetchProjects(); 
	}
	
	public void goToRegister() {
		MainController.changeScene("projectForm");
	} 
	
	public void updateTableData(Integer page) {
		if(page != null && page > 0 && page < this.totalPages) this.currentPage = page;
		this.fetchProjects();
	} 
	
	public void fetchProjects() {
		try {
			List<Project> projects = projectDAO.getPaged(this.currentPage, this.perPage);
			int totalItems = projectDAO.count();
			
			tableItems = FXCollections.observableArrayList(projects);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
			costCenterColumn.setCellValueFactory(new PropertyValueFactory<>("costCenterName"));
			clientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
			initDateColumn.setCellValueFactory(new PropertyValueFactory<>("initDate"));
			endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
			createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
			updateDateColumn.setCellValueFactory(new PropertyValueFactory<>("updateDate"));
		
			actionsColumn.setCellFactory(params -> new TableCell<Project, HBox>() {
				  @Override
				    protected void updateItem(HBox hbox, boolean empty) {
				       super.updateItem(hbox, empty);
				       if(getIndex() == -1 || tableItems.size() < getIndex() + 1) {
				    	   return;
				       }
				       Project project = tableItems.get(getIndex());
				       Button editButton = new Button();
				       ImageView iconEdit = new ImageView("assets/icons/edit_icon.png");
				       iconEdit.setFitHeight(16);
				       iconEdit.setFitWidth(16);
				       editButton.getStyleClass().add("btn-list-actions");
				       editButton.setGraphic(iconEdit);
				       editButton.setOnMouseClicked(e ->  MainController.changeScene("projectForm", project.getId()));
				    
				        HBox pane = new HBox(editButton);
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
