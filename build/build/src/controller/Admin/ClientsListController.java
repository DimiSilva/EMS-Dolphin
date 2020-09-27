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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.entities.Client;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.ClientDAO;

public class ClientsListController implements Initializable {

	private ClientDAO clientDAO;

	
	private int currentPage = 1;
	private int perPage = 20;
	private int totalPages = 0;
	
	@FXML
	private Button goToRegisterScreenButton;
	@FXML
	private TableView<Client> table;
	
	@FXML
	private TableColumn<Client, Integer> idColumn;
	@FXML
	private TableColumn<Client, String> nameColumn;
	@FXML
	private TableColumn<Client, String> emailColumn;
	@FXML
	private TableColumn<Client, String> cnpjColumn;
	@FXML
	private TableColumn<Client, String> phoneColumn;	
	@FXML
	private TableColumn<Client, Date> createDateColumn;
	@FXML
	private TableColumn<Client, Date> updateDateColumn;
	@FXML
	private TableColumn<Client, HBox> actionsColumn;
	
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
	
	ObservableList<Client> tableItems = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientDAO = new ClientDAO();
		goToRegisterScreenButton.setOnMouseClicked(e -> goToRegister());
		firstPageButton.setOnMouseClicked(e -> this.updateTableData(1));
		backPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage - 1));
		lastPageButton.setOnMouseClicked(e -> this.updateTableData(this.totalPages));
		nextPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage + 1));
		
		this.fetchClients(); 
	}
	
	public void goToRegister() {
		MainController.changeScene("clientForm");
	} 
	
	public void updateTableData(Integer page) {
		if(page != null && page > 0 && page < this.totalPages) this.currentPage = page;
		this.fetchClients();
	}
	
	public void deleteClient(int id, String name) {	
		if(Utils.showConfirmAlert("Atenção", "Deseja mesmo apagar o cliente " + name , "Apagar", "Cancelar") == true) {
			try {
				clientDAO.remove(String.valueOf(id));
				fetchClients();
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
		}
	} 
	
	public void fetchClients() {
		try {
			List<Client> clients = clientDAO.getPaged(this.currentPage, this.perPage);
			int totalItems = clientDAO.count();
			
			tableItems = FXCollections.observableArrayList(clients);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
			cnpjColumn.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
			phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
			createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
			updateDateColumn.setCellValueFactory(new PropertyValueFactory<>("updateDate"));
		
			actionsColumn.setCellFactory(params -> new TableCell<Client, HBox>() {
				  @Override
				    protected void updateItem(HBox hbox, boolean empty) {
				       super.updateItem(hbox, empty);
				       if(getIndex() == -1 || tableItems.size() < getIndex() + 1) {
				    	   return;
				       }
				       Client client = tableItems.get(getIndex());
				       Button editButton = new Button();
				       ImageView iconEdit = new ImageView("assets/icons/edit_icon.png");
				       iconEdit.setFitHeight(16);
				       iconEdit.setFitWidth(16);
				       editButton.setCursor(Cursor.HAND);
				       editButton.getStyleClass().add("btn-list-actions");
				       editButton.setGraphic(iconEdit);
				     
				       editButton.setOnMouseClicked(e ->  MainController.changeScene("clientForm", client.getId()));
				      
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
