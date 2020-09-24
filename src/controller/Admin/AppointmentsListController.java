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
import javafx.scene.Cursor;
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
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.entities.Admin;
import model.entities.Appointment;
import model.entities.Client;
import model.entities.Contributor;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.AdminDAO;
import model.persistence.AppointmentDAO;
import model.persistence.AuthDAO;
import model.persistence.ContributorDAO;
import model.persistence.DashboardDAO;

public class AppointmentsListController implements Initializable {

	private AppointmentDAO appointmentDAO;
	
	private int currentPage = 1;
	private int perPage = 20;
	private int totalPages = 0;
	
	@FXML
	private TableView<Appointment> table;
	
	@FXML
	private TableColumn<Appointment, Integer> idColumn;
	@FXML
	private TableColumn<Appointment, String> hoursColumn;
	@FXML
	private TableColumn<Appointment, String> contributorColumn;	
	@FXML
	private TableColumn<Appointment, String> activityColumn;
	@FXML
	private TableColumn<Appointment, String> clientColumn;
	@FXML
	private TableColumn<Appointment, String> projectColumn;
	@FXML
	private TableColumn<Appointment, String> initDateColumn;
	@FXML
	private TableColumn<Appointment, String> endDateColumn;

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
	
	
	public void fetchAppoitments() {
		try {
			List<Appointment> appointments = appointmentDAO.getPaged(this.currentPage, this.perPage);
			int totalItems = appointmentDAO.count();
			
			tableItems = FXCollections.observableArrayList(appointments);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			hoursColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			activityColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
			clientColumn.setCellFactory(params -> new TableCell<Appointment, String>() {
				  @Override
				    protected void updateItem(String client, boolean empty) {
				       super.updateItem(client, empty);
				       if(getIndex() == -1 || tableItems.size() < getIndex() + 1) {
				    	   return;
				       }
				       Appointment appointment = tableItems.get(getIndex());
				    
				      // setText(appointment.getProject().getClientName());
				       setText("null");
;				       
				    }
			});
			projectColumn.setCellFactory(params -> new TableCell<Appointment, String>() {
				  @Override
				    protected void updateItem(String project, boolean empty) {
				       super.updateItem(project, empty);
				       if(getIndex() == -1 || tableItems.size() < getIndex() + 1) {
				    	   return;
				       }
				       Appointment appointment = tableItems.get(getIndex());
				    
				       //setText(appointment.getProject().getName());
				       setText("null");
;				       
				    }
			});
			contributorColumn.setCellFactory(params -> new TableCell<Appointment, String>() {
				  @Override
				    protected void updateItem(String contributor, boolean empty) {
				       super.updateItem(contributor, empty);
				       if(getIndex() == -1 || tableItems.size() < getIndex() + 1) {
				    	   return;
				       }
				       Appointment appointment = tableItems.get(getIndex());
				    
				       setText(appointment.getContributor().getName());
				      
;				       
				    }
			});
			initDateColumn.setCellValueFactory(new PropertyValueFactory<>("initDate"));
			endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
			
				
			table.setItems(tableItems);
			
			this.totalPages = (int)Math.ceil((float)totalItems / this.perPage);
			this.currentPageText.setText(String.valueOf(this.currentPage));
			this.pagesText.setText(String.valueOf(this.totalPages));
		} catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
