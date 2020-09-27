package controller.Contributor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.entities.Appointment;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.AppointmentDAO;
import model.stages.AuthStage;

public class AppointmentsListController implements Initializable {

	private AppointmentDAO appointmentDAO;
	
	private int currentPage = 1;
	private int perPage = 20;
	private int totalPages = 0;
	
	@FXML
	private Button goToRegisterScreenButton;
	@FXML
	private TableView<Appointment> table;
	
	@FXML
	private TableColumn<Appointment, Integer> idColumn;
	@FXML
	private TableColumn<Appointment, String> hoursColumn;
	@FXML
	private TableColumn<Appointment, String> clientColumn;
	@FXML
	private TableColumn<Appointment, String> projectColumn;
	@FXML
	private TableColumn<Appointment, String> initDateColumn;
	@FXML
	private TableColumn<Appointment, String> endDateColumn;
	@FXML
	private TableColumn<Appointment, HBox> actionsColumn;
	
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
	
	ObservableList<Appointment> tableItems = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		appointmentDAO = new AppointmentDAO();
		
		goToRegisterScreenButton.setOnMouseClicked(e -> goToRegister());
		firstPageButton.setOnMouseClicked(e -> this.updateTableData(1));
		backPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage - 1));
		lastPageButton.setOnMouseClicked(e -> this.updateTableData(this.totalPages));
		nextPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage + 1));
		
		this.fetchAppoitments(); 
	} 
	
	public void updateTableData(Integer page) {
		if(page != null && page > 0 && page < this.totalPages) this.currentPage = page;
		this.fetchAppoitments();
	}  
	
	public void goToRegister() {
		MainController.changeScene("appointmentForm");
	} 
	
	public void fetchAppoitments() {
		try {
			if(AuthStage.loggedUser == null) return;
			List<Appointment> appointments = appointmentDAO.getPagedForContributor(String.valueOf(AuthStage.loggedUser.getId()) ,this.currentPage, this.perPage);
			int totalItems = appointmentDAO.countForContributor(String.valueOf(AuthStage.loggedUser.getId()));
			
			tableItems = FXCollections.observableArrayList(appointments);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			hoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
			clientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
			projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
			initDateColumn.setCellValueFactory(new PropertyValueFactory<>("initDate"));
			endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
			
			actionsColumn.setCellFactory(params -> new TableCell<Appointment, HBox>() {
				  @Override
				    protected void updateItem(HBox hbox, boolean empty) { 
				       super.updateItem(hbox, empty);
				       if(getIndex() == -1 || tableItems.size() < getIndex() + 1) {
				    	   return;
				       }
				       Appointment appointment = tableItems.get(getIndex());
				       Button editButton = new Button();
				       ImageView iconEdit = new ImageView("assets/icons/edit_icon.png");
				       iconEdit.setFitHeight(16);
				       iconEdit.setFitWidth(16);
				       editButton.setCursor(Cursor.HAND);
				       editButton.getStyleClass().add("btn-list-actions");
				       editButton.setGraphic(iconEdit);
				     
				       editButton.setOnMouseClicked(e ->  MainController.changeScene("appointmentForm", appointment.getId()));
				      
				       Button deleteButton = new Button();
				       ImageView iconDelete = new ImageView("assets/icons/delete_icon.png");
				       iconDelete.setFitHeight(16);
				       iconDelete.setFitWidth(16);
				       deleteButton.setCursor(Cursor.HAND);
				       deleteButton.setGraphic(iconDelete);
				    
				       deleteButton.getStyleClass().add("btn-list-actions");
				       deleteButton.setOnMouseClicked(e -> deleteAppointment(appointment.getId()));
				       
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
	
	public void deleteAppointment(int id) {	
		if(Utils.showConfirmAlert("Atenção", "Deseja mesmo apagar o apontamento ?", "Apagar", "Cancelar") == true) {
			try {
				appointmentDAO.remove(String.valueOf(id));
				fetchAppoitments();
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
		}
	}
}
