package controller.Contributor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.entities.Contributor;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.AuthDAO;
import model.persistence.ContributorDAO;

public class CompanyController implements Initializable {

	private ContributorDAO contributorDAO;
	private AuthDAO authDAO;
	
	private int currentPage = 1;
	private int perPage = 20;
	private int totalPages = 0;
	
	@FXML
	private TableView<Contributor> table;
	@FXML
	private TableColumn<Contributor, String> nameColumn;
	@FXML
	private TableColumn<Contributor, String> emailColumn;
	@FXML
	private TableColumn<Contributor, String> roleColumn;
	@FXML
	private TableColumn<Contributor, String> phoneColumn;

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
	
	ObservableList<Contributor> tableItems = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contributorDAO = new ContributorDAO();
		authDAO = new AuthDAO();

		firstPageButton.setOnMouseClicked(e -> this.updateTableData(1));
		backPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage - 1));
		lastPageButton.setOnMouseClicked(e -> this.updateTableData(this.totalPages));
		nextPageButton.setOnMouseClicked(e -> this.updateTableData(this.currentPage + 1));
		
		
		this.fetchContributors(); 
	}
	public void goToRegister() {
		MainController.changeScene("contributorForm");
	} 
	
	public void updateTableData(Integer page) {
		if(page != null && page > 0 && page < this.totalPages) this.currentPage = page;
		this.fetchContributors();
	} 
	public void deleteContributor(int id, int authId, String name) {	
		if(Utils.showConfirmAlert("Atenção", "Deseja mesmo apagar o contribuidor " + name , "Apagar", "Cancelar") == true) {
			try {
				contributorDAO.remove(String.valueOf(id));
				authDAO.remove(String.valueOf(authId));
				fetchContributors();
			}
			catch(DBException e) {
				Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
			}
		}

	} 
	
	
	public void fetchContributors() {
		try {
			List<Contributor> contributors = contributorDAO.getPaged(this.currentPage, this.perPage);
			int totalItems = contributorDAO.count();
			
			tableItems = FXCollections.observableArrayList(contributors);

			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
			phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
			roleColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));		
				
			table.setItems(tableItems);
			
			this.totalPages = (int)Math.ceil((float)totalItems / this.perPage);
			this.currentPageText.setText(String.valueOf(this.currentPage));
			this.pagesText.setText(String.valueOf(this.totalPages));
		} catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
