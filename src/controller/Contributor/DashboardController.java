package controller.Contributor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import model.DTOs.ContributorsWorkedHoursInYearByMonthData;
import model.DTOs.ProjectsWorkedHours;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.DashboardDAO;

public class DashboardController implements Initializable {
	@FXML
	BarChart<String, Integer> ContributorsWorkedHoursInYearByMonthChart;
	@FXML
	BarChart<String, Integer> projectsWorkedHoursChart;
	
	DashboardDAO dashboardDAO;
	
	List<ContributorsWorkedHoursInYearByMonthData> contributorsWorkedHoursInMonthData;
	List<ProjectsWorkedHours> projectsWorkedHoursData;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {	
			dashboardDAO = new DashboardDAO();
			contributorsWorkedHoursInMonthData = dashboardDAO.getAllContributorsWorkedHoursInYearByMonth();
			projectsWorkedHoursData = dashboardDAO.getAllProjectsWorkedHours();
			
			XYChart.Series<String, Integer> contributorsWorkedHoursPerMonthInYearDataSet = new XYChart.Series<String, Integer>();
			contributorsWorkedHoursPerMonthInYearDataSet.getData().add(new XYChart.Data<String, Integer>("teste", 500));
			
			XYChart.Series<String, Integer> projectsWorkedHoursDataSet = new XYChart.Series<String, Integer>();
			projectsWorkedHoursDataSet.getData().add(new XYChart.Data<String, Integer>("testee", 2));
			
			ContributorsWorkedHoursInYearByMonthChart.getData().addAll(contributorsWorkedHoursPerMonthInYearDataSet);
			projectsWorkedHoursChart.getData().addAll(projectsWorkedHoursDataSet);
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
