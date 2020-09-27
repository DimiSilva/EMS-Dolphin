package model.DTOs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContributorWorkedHoursInYearByMonth {
	public Integer jan;
	public Integer fev;
	public Integer mar;
	public Integer abr;
	public Integer mai;
	public Integer jun;
	public Integer jul;
	public Integer ago;
	public Integer set;
	public Integer out;
	public Integer nov;
	public Integer dez;
	
	public ContributorWorkedHoursInYearByMonth(
		Integer jan,
		Integer fev,
		Integer mar,
		Integer abr,
		Integer mai,
		Integer jun,
		Integer jul,
		Integer ago,
		Integer set,
		Integer out,
		Integer nov,
		Integer dez
	) {
		this.jan = jan;
		this.fev = fev;
		this.mar = mar;
		this.abr = abr;
		this.mai = mai;
		this.jun = jun;
		this.jul = jul;
		this.ago = ago;
		this.set = set;
		this.out = out;
		this.nov = nov;
		this.dez = dez;
	}
	
	public static ContributorWorkedHoursInYearByMonth fromDBSet(ResultSet DBSet) throws SQLException {
		Integer jan = DBSet.getInt("hours_month_1");
		Integer fev = DBSet.getInt("hours_month_2");
		Integer mar = DBSet.getInt("hours_month_3");
		Integer abr = DBSet.getInt("hours_month_4");
		Integer mai = DBSet.getInt("hours_month_5");
		Integer jun = DBSet.getInt("hours_month_6");
		Integer jul = DBSet.getInt("hours_month_7");
		Integer ago = DBSet.getInt("hours_month_8");
		Integer set = DBSet.getInt("hours_month_9");
		Integer out = DBSet.getInt("hours_month_10");
		Integer nov = DBSet.getInt("hours_month_11");
		Integer dez = DBSet.getInt("hours_month_12");
		
		return new ContributorWorkedHoursInYearByMonth(
				jan,
				fev,
				mar,
				abr,
				mai,
				jun,
				jul,
				ago,
				set,
				out,
				nov,
				dez
			);
	}
}