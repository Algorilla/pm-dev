package PModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

public class GanttCalculate {
	private Project project;
	private int projectID;
	private double taskEarliestStart;
	private double taskDuration;
	private Calendar projStartDate;
	private ArrayList<Activity> activityList;
	private GanttActivities ga;
	public ArrayList<GanttActivities> ganttActivityList;

	private int day;
	private int month;
	private int year;

	// Activity Date
	private int activityStartDay;
	private int activityStartMonth;
	private int activityStartYear;
	private int activityEndDay;
	private int activityEndMonth;
	private int activityEndYear;

	public GanttCalculate(ArrayList<Activity> activityList) {
		this.project = MainController.get().GetCurrentProject();
		this.activityList = activityList;
		this.projectID = project.getProjectID();

		// this.projStartDate;// = new GregorianCalendar();
		projStartDate = Calendar.getInstance();
		projStartDate.set(2014, Calendar.AUGUST, 2);
		this.day = projStartDate.DAY_OF_MONTH; // project.getStartDay();
		this.month = projStartDate.MONTH;// project.getStartMonth();
		this.year = projStartDate.YEAR; // project.getStartYear();

		ganttActivityList = new ArrayList<GanttActivities>();
	}

	public ArrayList<GanttActivities> calcDates() {
		int ctr = 0; //GET RID OUT COUNTER ONCE ACTIVITIES HAVE START DATES
		
		
		for (Activity activity : activityList) {
			try {
				if (activity.getProjectID() == project.getProjectID()) {
					taskEarliestStart = activity.getEarliestStart();
					taskDuration = activity.getDuration();
					System.out.println("ACTIVITY TITLE: " + activity.getName() + " EARLIEST START DATE: " + activity.getEarliestStart());// + ctr);
					System.out.println("ACTIVITY TITLE: " + activity.getName() + " Duration: " + activity.getDuration());
					System.out.println("");
					
					// Activity Start and End Dates (day, month, year)
					activityStartDay = (int) (day + taskEarliestStart );//+ ctr); 
					activityStartMonth = calcMonth(date(day, month, year),
							activityStartDay);
					activityStartYear = calcYear(date(day, month, year),
							activityStartDay);

					activityEndDay = (int) (day + taskDuration);// + ctr);
					activityEndMonth = calcMonth(date(day, month, year),
							activityEndDay);
					activityEndYear = calcYear(date(day, month, year),
							activityEndDay);

					// createGanttChartActivity (this is added to graph)
					ga = new GanttActivities(activityStartDay,
							activityStartMonth, activityStartYear,
							activityEndDay, activityEndMonth, activityEndYear,
							activity.getName());

					// add ganttActivities to list
					ganttActivityList.add(ga);
					ctr = ctr + 3;
				}
			} catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "Error calculating activity dates: Activity with invalid data"); // or something like that
			}
		}
		return ganttActivityList;
	}

	private static Date date(final int day, final int month, final int year) {

		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		final Date result = calendar.getTime();
		return result;
	}

	private int calcMonth(Date baseDate, int daysToAdd) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
		return calendar.MONTH;
	}

	private int calcYear(Date baseDate, int daysToAdd) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
		return calendar.YEAR;
	}
	
	public ArrayList<GanttActivities> getGanttActivitiesList(){
		return ganttActivityList;
	}
}
