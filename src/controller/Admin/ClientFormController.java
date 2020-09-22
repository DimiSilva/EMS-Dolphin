package controller.Admin;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.entities.Admin;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.DashboardDAO;

public class ClientFormController implements Initializable {

	@FXML
	private Labeled titlePage;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField emailInput;
	@FXML
	private TextField cpfInput;
	@FXML
	private PasswordField passwordInput;
	@FXML
	private Button registerButton;
	@FXML
	private Button backButton;
	@FXML
	private VBox passwordContainer;	
	@FXML
	private int updatingUserId;
	@FXML
	private Admin updatingUser;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*try {	
			dashboardDAO = new DashboardDAO();
			contributorsWorkedHoursInMonthData = dashboardDAO.getAllContributorsWorkedHoursInYearByMonth();
			projectsWorkedHoursData = dashboardDAO.getAllProjectsWorkedHours();
			
			XYChart.Series<String, Integer> contributorsWorkedHoursPerMonthInYearDataSet = new XYChart.Series<String, Integer>();
			contributorsWorkedHoursInMonthData.forEach(
					item -> contributorsWorkedHoursPerMonthInYearDataSet
								.getData()
								.add(
									new XYChart.Data<String, Integer>(Months.values()[item.month].getText(), item.hours)
								)
							);
			
			XYChart.Series<String, Integer> projectsWorkedHoursDataSet = new XYChart.Series<String, Integer>();
			projectsWorkedHoursData.forEach(
					item -> projectsWorkedHoursDataSet
								.getData()
								.add(
									new XYChart.Data<String, Integer>(item.projectName, item.hours)
								)
							);
			
			
			contributorsWorkedHoursInYearByMonthChart.getData().addAll(contributorsWorkedHoursPerMonthInYearDataSet);
			projectsWorkedHoursChart.getData().addAll(projectsWorkedHoursDataSet);
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}*/
	}
}
