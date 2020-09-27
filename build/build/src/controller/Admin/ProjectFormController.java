package controller.Admin;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.entities.Client;
import model.entities.Contributor;
import model.entities.CostCenter;
import model.entities.Project;
import model.entities.ProjectContributor;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.ClientDAO;
import model.persistence.ContributorDAO;
import model.persistence.CostCenterDAO;
import model.persistence.ProjectContributorDAO;
import model.persistence.ProjectDAO;

public class ProjectFormController implements Initializable {

	ProjectDAO projectDAO;
	ProjectContributorDAO projectContributorDAO;
	CostCenterDAO costCenterDAO;
	ClientDAO clientDAO;
	ContributorDAO contributorDAO;
	
	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextArea descriptionInput;
	@FXML
	private DatePicker initDateInput;
	@FXML
	private DatePicker endDateInput;
	@FXML
	private ComboBox<CostCenter> costCenterInput;
	@FXML
	private ComboBox<Client> clientInput;
	@FXML
	private TextField searchParticipantInput;	
	@FXML
	private Button searchParticipantsButton;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox participantsToAddContainer;
	@FXML
	private VBox participantsContainer;
	
	private List<Contributor> participantsToAdd;
	
	private List<Contributor> participants;
	@FXML
	private Integer updatingProjectId;
	@FXML
	private Project updatingProject; 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		projectDAO = new ProjectDAO();
		projectContributorDAO = new ProjectContributorDAO();
		costCenterDAO = new CostCenterDAO();
		clientDAO = new ClientDAO();
		contributorDAO = new ContributorDAO();
		
		participants = new ArrayList<Contributor>();
		participantsToAdd = new ArrayList<Contributor>();
		
		searchParticipantsButton.setOnMouseClicked(e -> fetchParticipantsToAdd());
		registerButton.setOnMouseClicked(e -> register());
		backButton.setOnMouseClicked(e -> MainController.changeScene("projectsList"));
		
		loadCostCenters();
		loadClients();
	}
	
	@FXML
	private void register() {
		try {
			Project project = new Project(
				nameInput.getText(), 
				descriptionInput.getText(), 
				costCenterInput.getValue(), 
				clientInput.getValue(), 
				Date.from(initDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), 
				Date.from(endDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
			);
			int id = projectDAO.insert(project);
			
			final Project insertedProject = projectDAO.getById(String.valueOf(id));
			
			participants.forEach(item -> {
					try {
						projectContributorDAO.insert(new ProjectContributor(insertedProject, item));
					}
					catch(DBException e) {						
						e.printStackTrace();
						Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
					}
				}
			);
			
			MainController.changeScene("projectsList");
		}
		catch(DBException e) {
			e.printStackTrace();
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	
	@FXML
	public void update() {
		try {
			this.updatingProject.update(
				nameInput.getText(), 
				descriptionInput.getText(),
				Date.from(initDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), 
				Date.from(endDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())		
			);
			
			projectDAO.update(this.updatingProject);			
			projectContributorDAO.removeAllFromProject(String.valueOf(this.updatingProject.getId()));
			
			participants.forEach(item -> {
					try {
						projectContributorDAO.insert(new ProjectContributor(this.updatingProject, item));
					}
					catch(DBException e) {						
						e.printStackTrace();
						Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
					}
				}
			);
			
			MainController.changeScene("projectsList");
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	public void reset() {
		updatingProjectId = null;
		updatingProject = null;
		
		nameInput.setText(null);
		descriptionInput.setText(null);
		initDateInput.setValue(null);
		endDateInput.setValue(null);
		costCenterInput.setValue(null);
		costCenterInput.getItems().clear();
		costCenterInput.setDisable(false);
		loadCostCenters();
		clientInput.setValue(null);
		clientInput.getItems().clear();
		clientInput.setDisable(false);
		loadClients();
		searchParticipantInput.setText("");

		participantsToAddContainer.getChildren().clear();
		participantsContainer.getChildren().clear();
		
		participantsToAdd.clear();
		participants.clear();
		
		registerButton.setText("Cadastrar");
		registerButton.setOnMouseClicked(e -> register());
		titlePage.setText("Cadastrar projeto");
	}
	
	public void loadUpdatingProjectById(int updatingProjectId) {
		this.updatingProjectId = updatingProjectId;
		
		try {
			this.updatingProject = projectDAO.getById(String.valueOf(this.updatingProjectId));
			this.nameInput.setText(this.updatingProject.getName());
			this.descriptionInput.setText(this.updatingProject.getDescription());
			this.initDateInput.setValue(new Date(this.updatingProject.getInitDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			this.endDateInput.setValue(new Date(this.updatingProject.getEndDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			this.costCenterInput.setValue(this.updatingProject.getCostCenter());
			this.costCenterInput.setDisable(true);
			this.clientInput.setValue(this.updatingProject.getClient());
			this.clientInput.setDisable(true);
			
			participants = contributorDAO.getInProject(String.valueOf(updatingProjectId));
			renderParticipants();
			
			registerButton.setText("Salvar");
			registerButton.setOnMouseClicked(e -> update());
			titlePage.setText("Editar projeto");
		}
		catch(DBException e){
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
	
	private void loadCostCenters() {
		try {
			List<CostCenter> costCenters = costCenterDAO.getAll();
			costCenters.forEach(item -> costCenterInput.getItems().add(item));
		}
		catch (DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			e.printStackTrace();
			MainController.changeScene("projectsList");
		}
	}
	
	private void loadClients() {
		try {
			List<Client> clients = clientDAO.getAll();
			clients.forEach(item -> clientInput.getItems().add(item));
		}
		catch (DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			e.printStackTrace();
			MainController.changeScene("projectsList");
		}
	}
	
	private void fetchParticipantsToAdd() {
		try {
			participantsToAdd = contributorDAO.getOutOfProjectFiltered(participants, searchParticipantInput.getText());
			renderParticipantsToAdd();
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			e.printStackTrace();
			MainController.changeScene("projectsList");
		}
	}
	
	private void renderParticipantsToAdd() {
		participantsToAddContainer.getChildren().clear();
		participantsToAdd.forEach(item -> {
			Label nameLabel = new Label(item.getName());
			HBox nameContainer = new HBox(nameLabel);
			HBox.setHgrow(nameContainer, Priority.ALWAYS);
			nameContainer.setPadding(new Insets(0, 0, 8, 0));
			nameContainer.setAlignment(Pos.CENTER);
			
			Label emailLabel = new Label(item.getEmail());
			HBox emailContainer = new HBox(emailLabel);
			HBox.setHgrow(emailContainer, Priority.ALWAYS);
			emailContainer.setPadding(new Insets(0, 0, 8, 0));
			emailContainer.setAlignment(Pos.CENTER);
			
			Label cpfLabel = new Label(item.getCpf());
			HBox cpfContainer = new HBox(cpfLabel);
			HBox.setHgrow(cpfContainer, Priority.ALWAYS);
			cpfContainer.setPadding(new Insets(0, 0, 8, 0));
			cpfContainer.setAlignment(Pos.CENTER);
			
			Button addButton = new Button("Adicionar");
			addButton.getStyleClass().add("ems-btn");
			addButton.setOnMouseClicked(e -> addParticipant(item));
			HBox actionsContainer = new HBox(addButton);
			HBox.setHgrow(actionsContainer, Priority.ALWAYS);
			actionsContainer.setPadding(new Insets(0, 0, 8, 0));
			actionsContainer.setAlignment(Pos.CENTER);
			
			HBox row = new HBox();
			row.getChildren().add(nameContainer);
			row.getChildren().add(emailContainer);
			row.getChildren().add(cpfContainer);
			row.getChildren().add(actionsContainer);
			participantsToAddContainer.getChildren().add(row);
		});
	}
	
	private void addParticipant(Contributor participant) {
		participants.add(participant);
		renderParticipants();
		fetchParticipantsToAdd();
	}
	
	private void renderParticipants() {
		participantsContainer.getChildren().clear();
		participants.forEach(item -> {
			Label nameLabel = new Label(item.getName());
			HBox nameContainer = new HBox(nameLabel);
			HBox.setHgrow(nameContainer, Priority.ALWAYS);
			nameContainer.setPadding(new Insets(0, 0, 8, 0));
			nameContainer.setAlignment(Pos.CENTER);
			
			Label emailLabel = new Label(item.getEmail());
			HBox emailContainer = new HBox(emailLabel);
			HBox.setHgrow(emailContainer, Priority.ALWAYS);
			emailContainer.setPadding(new Insets(0, 0, 8, 0));
			emailContainer.setAlignment(Pos.CENTER);
			
			Label cpfLabel = new Label(item.getCpf());
			HBox cpfContainer = new HBox(cpfLabel);
			HBox.setHgrow(cpfContainer, Priority.ALWAYS);
			cpfContainer.setPadding(new Insets(0, 0, 8, 0));
			cpfContainer.setAlignment(Pos.CENTER);
			
			Button removeButton = new Button("Remover");
			removeButton.getStyleClass().add("ems-btn");
			removeButton.setOnMouseClicked(e -> removeParticipant(item));
			HBox actionsContainer = new HBox(removeButton);
			HBox.setHgrow(actionsContainer, Priority.ALWAYS);;
			actionsContainer.setPadding(new Insets(0, 0, 8, 0));
			actionsContainer.setAlignment(Pos.CENTER);
			
			HBox row = new HBox();
			row.getChildren().add(nameContainer);
			row.getChildren().add(emailContainer);
			row.getChildren().add(cpfContainer);
			row.getChildren().add(actionsContainer);
			participantsContainer.getChildren().add(row);
		});
	}
	
	private void removeParticipant(Contributor participant) {
		participants.remove(participant);
		renderParticipants();
		fetchParticipantsToAdd();
	}
}
