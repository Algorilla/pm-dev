/**
 * 
 */
package Analysis;

import Controller.ErrorController;
import PModel.Activity;
import PModel.Project;

/**
 * 
 */
public class EarnedValue {

	private Project project;
	private double daysSinceStart;
	// TODO: set to static for testing purposes
	private static ErrorController ec = ErrorController.get();

	public EarnedValue(Project project, double daysSinceStart) {

		this.project = project;
		this.daysSinceStart = daysSinceStart;

		calculateBudgetAtCompletion();
		calculatePlannedValue();
		calculateEarnedValue();
		calculatePercentScheduledForCompletion();
		calculateActualCost();
		calculatePercentComplete();
		calculateCostVariance();
		calculateScheduleVariance();
		calculateCostPerformanceIndex();
		calculateSchedulePerformanceIndex();
		calculateEstimateAtCompletion();
		calculateEstimateToComplete();
	}

	private void calculateBudgetAtCompletion() {

		double bac = 0;

		for (Activity a : project.getActivityList()) {
			bac += a.getPlannedValue();
		}

		project.setBudgetAtCompletion(bac);
	}

	private void calculatePlannedValue() {

		double scheduledValue = 0;

		for (Activity a : project.getActivityList()) {
			scheduledValue += getActivityScheduleValue(a.getEarliestFinish(),
					a.getLatestFinish(), a.getPlannedValue(), a.getDuration(),
					daysSinceStart);
		}
		project.setPlannedValue(scheduledValue);
	}

	// TODO: This is set to public static for testing purposes. Will be set back
	// to private for deployment
	public static double getActivityScheduleValue(double activityEarlyFinish,
			double activityLateFinish, double activityPlannedVal,
			double activityDuration, double daysSinceStart) {

		double scheduledValue = 0;
		double percentScheduled;

		if (activityEarlyFinish > activityLateFinish) {
//			ec.showError("Latest Finish should not preced Earliest Finish");// DMITRI
			return -1.0;
		}
		
		if(activityPlannedVal < 0){
//			ec.showError("PlannedValue should not be less than Zero");// DMITRI
			return -1.0;
		}
		
		if(activityDuration <= 0){
//			ec.showError("Duration should not be less than Zero");// DMITRI
			return -1.0;
		}
		
		if(daysSinceStart < 0){
//			ec.showError("DaysSinceStart should not be less than Zero");// DMITRI
			return -1.0;
		}

		// If the activity should be finished, add its entire value
		if (activityEarlyFinish > daysSinceStart) {
//			ec.showError("DaysSinceStart should not be less than Zero");// DMITRI
			return -1.0;
		}
		
		if(daysSinceStart < activityLateFinish){
//			ec.showError("DaysSinceStart should not be less than activityLateFinish");// DMITRI
			return -1.0;
		}
		
		// Otherwise, calculate what percentage should be done, and add that to
		// the scheduled value
		else if (activityLateFinish < daysSinceStart) {
			if (activityDuration > 0) {

				percentScheduled = (daysSinceStart - activityLateFinish)
						/ activityDuration;

				scheduledValue += percentScheduled * activityPlannedVal;

			}
			else{
//				ec.showError("Duration is Zero");// DMITRI
				return -1.0;
			}
		}
		return scheduledValue;
	}

	private void calculateEarnedValue() {

		double ev = 0;

		for (Activity a : project.getActivityList()) {
			ev += (a.getPlannedValue() * a.getPercentComplete());
		}

		project.setEarnedValue(ev);

	}

	private void calculatePercentScheduledForCompletion() {

		if (project.getBudgetAtCompletion() > 0) {
			double sfc = project.getPlannedValue()
					/ project.getBudgetAtCompletion();
			project.setPercentScheduledForCompletion(sfc);
		}

	}
	// TODO: This is set to public static for testing purposes. Will be set back
	// to private for deployment
	public void calculateActualCost() {

		double ac = 0;

		for (Activity a : project.getActivityList()) {
			if (a.getStatus()) {
				ac += a.getActualCost();
			} else {
				if (a.getPercentComplete() > 0) {
					ac += a.getActualCost() * a.getPercentComplete();
				}
			}
		}

		project.setActualCost(ac);
	}

	private void calculatePercentComplete() {
		if (project.getBudgetAtCompletion() > 0) {
			double pc = project.getEarnedValue()
					/ project.getBudgetAtCompletion();
			project.setPercentComplete(pc);
		}
	}

	private void calculateCostVariance() {
		project.setCostVariance(project.getEarnedValue()
				- project.getActualCost());
	}

	private void calculateScheduleVariance() {
		project.setScheduleVariance(project.getEarnedValue()
				- project.getPlannedValue());
	}

	private void calculateCostPerformanceIndex() {
		if (project.getActualCost() > 0) {
			project.setCostPerformanceIndex(project.getEarnedValue()
					/ project.getActualCost());
		}
	}

	private void calculateSchedulePerformanceIndex() {
		if (project.getPlannedValue() > 0) {
			project.setSchedulePerformanceIndex(project.getEarnedValue()
					/ project.getPlannedValue());
		}
	}

	private void calculateEstimateAtCompletion() {
		if (project.getCostPerformanceIndex() > 0) {
			project.setEstimateAtCompletion(project.getBudgetAtCompletion()
					/ project.getCostPerformanceIndex());
		}
	}

	private void calculateEstimateToComplete() {
		project.setEstimateToComplete(project.getEstimateAtCompletion()
				- project.getActualCost());
	}
}
