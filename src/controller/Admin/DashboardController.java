package controller.Admin;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.DashboardDAO;

public class DashboardController implements Initializable {
	@FXML
	BarChart<String, Integer> contributorsWorkedHoursInYearByMonthChart;
	@FXML
	BarChart<String, Integer> projectsWorkedHoursChart;
	
	DashboardDAO dashboardDAO;
	
	List<ContributorsWorkedHoursInYearByMonth> contributorsWorkedHoursInMonthData;
	List<ProjectsWorkedHours> projectsWorkedHoursData;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {	
			dashboardDAO = new DashboardDAO();
			contributorsWorkedHoursInMonthData = dashboardDAO.getAllContributorsWorkedHoursInYearByMonth();
			projectsWorkedHoursData = dashboardDAO.getAllProjectsWorkedHours();
			
			XYChart.Series<String, Integer> contributorsWorkedHoursInMonthDataSet = new XYChart.Series<String, Integer>();
			Arrays.asList(Months.values()).forEach(
				month -> {
					Integer monthHours = 0;
					for(int i = 0; i < contributorsWorkedHoursInMonthData.size(); i++) {
						ContributorsWorkedHoursInYearByMonth item = contributorsWorkedHoursInMonthData.get(i);
						if(Months.values()[item.month].getText().compareTo(month.getText()) == 0) {
							monthHours = item.hours;
							break;
						}
					}
					contributorsWorkedHoursInMonthDataSet
						.getData()
						.add(new XYChart.Data<String, Integer>(month.getText(), monthHours));						
				}
			);
			
			XYChart.Series<String, Integer> projectsWorkedHoursDataSet = new XYChart.Series<String, Integer>();
			projectsWorkedHoursData.forEach(
				item -> projectsWorkedHoursDataSet
					.getData()
					.add(new XYChart.Data<String, Integer>(item.projectName, item.hours))
			);
			
			
			contributorsWorkedHoursInYearByMonthChart.getData().addAll(contributorsWorkedHoursInMonthDataSet);
			projectsWorkedHoursChart.getData().addAll(projectsWorkedHoursDataSet);
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}
}
