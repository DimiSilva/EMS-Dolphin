package controller.Contributor;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.DTOs.ContributorRunningProject;
import model.DTOs.ContributorWorkedHoursInYearByMonth;
import model.DTOs.ContributorsWorkedHoursInYearByMonth;
import model.DTOs.ProjectsWorkedHours;
import model.enums.Months;
import model.enums.messages.Shared;
import model.exceptions.DBException;
import model.helpers.Utils;
import model.persistence.DashboardDAO;
import model.stages.AuthStage;

public class ContributorAppointmentsListController implements Initializable {
	@FXML
	BarChart<String, Integer> contributorsWorkedHoursInYearByMonthChart;
	@FXML
	VBox runningProjectsContainer;
	@FXML
	BarChart<String, Integer> projectsWorkedHoursChart;
	
	DashboardDAO dashboardDAO;
	
	ContributorWorkedHoursInYearByMonth contributorWorkedHoursInMonthData;
	List<ProjectsWorkedHours> projectsWorkedHoursData;
	List<ContributorRunningProject> runningProjects;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dashboardDAO = new DashboardDAO();
		diffDateTime();
	}
	
	public void loadDashboardData() {
		try {	
			loadContributorWorkedHoursInMonthChart();
			loadProjectsWorkedHoursChart();
			loadRunningProjects();
		}
		catch(DBException e) {
			Utils.showErrorAlert("Erro!", Shared.SOMETHING_WENT_WRONG.getText(), null);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadContributorWorkedHoursInMonthChart() throws DBException {
		contributorWorkedHoursInMonthData = dashboardDAO.getContributorWorkedHoursInYearByMonth(AuthStage.loggedUser.getId());
		
		XYChart.Series<String, Integer> contributorWorkedHoursInMonthDataSet = new XYChart.Series<String, Integer>();
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.JANEIRO.getText(), contributorWorkedHoursInMonthData.jan));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.FEVEREIRO.getText(), contributorWorkedHoursInMonthData.fev));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.MARCO.getText(), contributorWorkedHoursInMonthData.mar));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.ABRIL.getText(), contributorWorkedHoursInMonthData.abr));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.MAIO.getText(), contributorWorkedHoursInMonthData.mai));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.JUNHO.getText(), contributorWorkedHoursInMonthData.jun));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.JULHO.getText(), contributorWorkedHoursInMonthData.jul));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.AGOSTO.getText(), contributorWorkedHoursInMonthData.ago));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.SETEMBRO.getText(), contributorWorkedHoursInMonthData.set));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.OUTUBRO.getText(), contributorWorkedHoursInMonthData.out));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.NOVEMBRO.getText(), contributorWorkedHoursInMonthData.nov));
		contributorWorkedHoursInMonthDataSet.getData().add(new XYChart.Data<String, Integer>(Months.DEZEMBRO.getText(), contributorWorkedHoursInMonthData.dez));
		
		contributorsWorkedHoursInYearByMonthChart.getData().addAll(contributorWorkedHoursInMonthDataSet);
	}
	
	@SuppressWarnings("unchecked")
	private void loadProjectsWorkedHoursChart() throws DBException {
		projectsWorkedHoursData = dashboardDAO.getContributorWorkedHoursByProject(AuthStage.loggedUser.getId());
		
		XYChart.Series<String, Integer> projectsWorkedHoursDataSet = new XYChart.Series<String, Integer>();
		projectsWorkedHoursData.forEach(
			item -> projectsWorkedHoursDataSet
				.getData()
				.add(new XYChart.Data<String, Integer>(item.projectName, item.hours)
			)
		);
		
		projectsWorkedHoursChart.getData().addAll(projectsWorkedHoursDataSet);
	}
	
	private void diffDateTime() {
		LocalDateTime d1 = LocalDateTime.of(2017, 7, 6, 23, 30, 0);
		LocalDateTime d2 = LocalDateTime.of(2017, 7, 7, 7, 0, 55);
		System.out.println("here");
		Duration duration = Duration.between(d1, d2);
		// total seconds of difference (using Math.abs to avoid negative values)
		long seconds = Math.abs(duration.getSeconds());
		long hours = seconds / 3600;
		seconds -= (hours * 3600);
		long minutes = seconds / 60;
		seconds -= (minutes * 60);
		System.out.println(hours + " hours " + minutes + " minutes " + seconds + " seconds");
	}
	
	private void loadRunningProjects() throws DBException {
		runningProjects = dashboardDAO.getContributorRunningProjects(AuthStage.loggedUser.getId());

		runningProjects.forEach(item -> {
				Label projectName = new Label(item.projectName);
				projectName.getStyleClass().add("text-white");
				HBox projectNameContainer = new HBox(projectName);
				projectNameContainer.setAlignment(Pos.CENTER);
				HBox.setHgrow(projectNameContainer, Priority.ALWAYS);
				
				Label initDate = new Label(item.initDate.toString());
				initDate.getStyleClass().add("text-white");
				HBox initDateContainer = new HBox(initDate);
				initDateContainer.setAlignment(Pos.CENTER);
				HBox.setHgrow(initDateContainer, Priority.ALWAYS);
				
				Label endDate = new Label(item.endDate.toString());
				endDate.getStyleClass().add("text-white");
				HBox endDateContainer = new HBox(endDate);
				endDateContainer.setAlignment(Pos.CENTER);
				HBox.setHgrow(endDateContainer, Priority.ALWAYS);
				
				HBox row = new HBox();
				row.getStyleClass().add("list-line");
				row.setPadding(new Insets(4, 4, 4, 4));
				HBox.setMargin(row, new Insets(0, 0, 8, 0));
				
				row.getChildren().add(projectNameContainer);
				row.getChildren().add(initDateContainer);
				row.getChildren().add(endDateContainer);
				
				runningProjectsContainer.getChildren().add(row);
			}
		);
	}
}
