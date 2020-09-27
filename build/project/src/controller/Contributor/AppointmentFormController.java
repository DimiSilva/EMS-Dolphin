package controller.Contributor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import jfxtras.scene.control.LocalDateTimeTextField;
import model.entities.Appointment;
import model.entities.Contributor;
import model.entities.Project;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.AppointmentDAO;
import model.persistence.ContributorDAO;
import model.persistence.ProjectContributorDAO;
import model.persistence.ProjectDAO;
import model.stages.AuthStage;

public class AppointmentFormController implements Initializable {

	AppointmentDAO appointmentDAO;
	ProjectDAO projectDAO;
	ProjectContributorDAO projectContributorDAO;
	ContributorDAO contributorDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextArea descriptionInput;
	@FXML
	private LocalDateTimeTextField initDateInput;
	@FXML
	private LocalDateTimeTextField endDateInput;
	@FXML
	private ComboBox<Project> projectInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private Integer updatingAppointmentId;
	@FXML
	private Appointment updatingAppointment;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		appointmentDAO = new AppointmentDAO();
		projectDAO = new ProjectDAO();
		projectContributorDAO = new ProjectContributorDAO();
		contributorDAO = new ContributorDAO();
		
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("appointmentsList"));
		
		loadProjects();
	}
	
	@FXML
	private void register() {
		try {
			Appointment appointment = new Appointment(
				descriptionInput.getText(), 
				initDateInput.getLocalDateTime(), 
				endDateInput.getLocalDateTime(),
				(Contributor)AuthStage.loggedUser,
				projectInput.getValue()
			);
			appointmentDAO.insert(appointment);
			
			MainController.changeScene("appointmentsList");
		}
		catch(DBException e) {
			e.printStackTrace();
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	
	@FXML
	public void update() {
		try {
			this.updatingAppointment.update(
				descriptionInput.getText(), 
				initDateInput.getLocalDateTime(), 
				endDateInput.getLocalDateTime(),
				(Contributor)AuthStage.loggedUser,
				projectInput.getValue()
			);
			
			appointmentDAO.update(this.updatingAppointment);
			
			MainController.changeScene("appointmentsList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	public void reset() {
		updatingAppointmentId = null;
		updatingAppointment = null;
		
		descriptionInput.setText(null);
		initDateInput.setLocalDateTime(null);
		endDateInput.setLocalDateTime(null);
		
		projectInput.getItems().clear();
		projectInput.setValue(null);
		loadProjects();

		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar projeto");
	}
	
	public void loadUpdatingAppointment(int updatingAppointmentId) {
		this.updatingAppointmentId = updatingAppointmentId;
		
		try {
			this.updatingAppointment = appointmentDAO.getById(String.valueOf(this.updatingAppointmentId));
			this.descriptionInput.setText(this.updatingAppointment.getDescription());
			this.initDateInput.setLocalDateTime(this.updatingAppointment.getInitDate());
			this.endDateInput.setLocalDateTime(this.updatingAppointment.getEndDate());
			this.projectInput.setValue(this.updatingAppointment.getProject());
			
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar apontamento");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	private void loadProjects() {
		try {
			if(AuthStage.loggedUser == null) return;
			List<Project> projects = projectDAO.getAllOfContributorPaticipating(String.valueOf(AuthStage.loggedUser.getId()));
			projects.forEach(item -> projectInput.getItems().add(item));
		}
		catch (DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			e.printStackTrace();
			MainController.changeScene("projectsList");
		}
	}
}
