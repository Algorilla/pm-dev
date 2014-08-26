package PModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import Controller.ErrorController;
import Controller.MainController;

/**
 * Projects derive from Manageable Class, and are identified by a Project ID, as
 * well as the Project Manager's ID, which is that Member's ID.
 * 
 * <p>
 * Project's can also contain a list of activities.
 * 
 * @author Alex Huot
 */
public class Project extends Manageable {

	public static final double MAX_COST = 10000000; // Ten Mill!?! High rollers
														// must buy the premium
														// version.

	private int projectID;
	private int managerID;

	private Date startDate;

	private double percentComplete;
	private double budgetAtCompletion;
	private double percentScheduledForCompletion;
	private double actualCost;
	private double earnedValue;
	private double costVariance;
	private double scheduleVariance;
	private double costPerformanceIndex;
	private double schedulePerformanceIndex;
	private double estimateAtCompletion;
	private double estimateToComplete;

	private ArrayList<Activity> activityList;

	private MainController mc;
	private ErrorController ec = ErrorController.get();

	/**
	 * Sole constructor for Project ID. Project ID is automatically assigned
	 * based on existing project IDs in the database.
	 * 
	 * @param managerID
	 *            Project Manager's ID
	 * @param name
	 *            Project name
	 * @param description
	 *            Project description
	 */
	public Project(int managerID, String name, String description,
			Date startDate, double percentComplete, double budgetAtCompletion,
			double percentScheduledForCompletion, double actualCost,
			double earnedValue, double costVariance, double scheduleVariance,
			double costPerformanceIndex, double schedulePerformanceIndex,
			double estimateAtCompletion, double estimateToComplete) {
		super(name, description);

		if (areValidPercentages(percentComplete, percentScheduledForCompletion)) {
			this.percentComplete = percentComplete;
			this.percentScheduledForCompletion = percentScheduledForCompletion;
		} else {
//			ec.showError("DMITRI");// DMITRI
			return;
		}

		if (areValidValues(budgetAtCompletion, actualCost, earnedValue)) {
			this.budgetAtCompletion = budgetAtCompletion;
			this.actualCost = actualCost;
			this.earnedValue = earnedValue;
		} else {
//			ec.showError("DMITRI");// DMITRI
			return;
		}

		this.managerID = managerID;
		this.startDate = startDate;

		this.costVariance = costVariance;
		this.scheduleVariance = scheduleVariance;
		this.costPerformanceIndex = costPerformanceIndex;
		this.schedulePerformanceIndex = schedulePerformanceIndex;
		this.estimateAtCompletion = estimateAtCompletion;
		this.estimateToComplete = estimateToComplete;
		
		this.activityList = new ArrayList<Activity>();

		// TODO Ensure consistency between start date, deadline and projected
		// length
	}

	/**
	 * 
	 * @param percentage1
	 * @param percentage2
	 * @return
	 */
	// TODO: This is public only during testing, for production it must revert
	// to private
	public static boolean areValidPercentages(double percentage1, double percentage2) {

		if (percentage1 < 0 || percentage2 < 0) {
			return false;
		}
		if (percentage1 > 1 || percentage2 > 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param percentage
	 * @return
	 */

	private boolean isValidPercentage(double percentage) {

		if (percentage < 0 || 1 < percentage) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param value1
	 * @param value2
	 * @param value3
	 * @return
	 */
	// TODO: This is public only during testing, for production it must revert
	// to private
	public static boolean areValidValues(double value1, double value2, double value3) {

		if (value1 < 0 || value2 < 0 || value3 < 0) {
			return false;
		}

		if (value1 > MAX_COST || value2 > MAX_COST || value3 > MAX_COST) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private boolean isValidValue(double value) {

		if (value < 0 || MAX_COST < value) {
			return false;
		}
		return true;
	}

	/**
	 * Sets a new Project ID to an existing project
	 * 
	 * @param newProjectID
	 *            New Project ID
	 */
	public void setProjectID(int newProjectID) {
		if (isValidValue(newProjectID)) {
			projectID = newProjectID;
		}

		// TODO Ensure unique Project ID: This is handled in the dataLoader,
		// should it not be?
	}

	/**
	 * Sets new Project Manager via their ID
	 * 
	 * @param newManagerID
	 *            New Manager's ID
	 */
	public void setManagerID(int newManagerID) {
		if (isValidValue(newManagerID)) {
			managerID = newManagerID;
		}
	}

	/**
	 * Returns Project ID
	 * 
	 * @return Project ID
	 */
	public int getProjectID() {
		return projectID;
	}

	/**
	 * Returns Manager's ID
	 * 
	 * @return Manager's ID
	 */
	public int getManagerID() {
		return managerID;
	}

	/**
	 * Adds a new Activity to Project's activityList
	 * 
	 * @param newActivity
	 *            Activity to be added to Project
	 */
	public void addActivity(Activity newActivity) {
		this.activityList.add(newActivity);
	}

	/**
	 * Removes an existing Activity from Project's activityList
	 * 
	 * @param activity
	 *            Activity to be removed from Project
	 */
	public void removeActivity(Activity activity) {
		this.activityList.remove(activity);
	}

	/**
	 * @return the budgetAtCompletion
	 */
	public double getBudgetAtCompletion() {
		return budgetAtCompletion;
	}

	/**
	 * @param budgetAtCompletion
	 *            the budgetAtCompletion to set
	 */
	public void setBudgetAtCompletion(double budgetAtCompletion) {
		if (isValidValue(budgetAtCompletion)) {
			this.budgetAtCompletion = budgetAtCompletion;
		}
	}

	/**
	 * @return the percentComplete
	 */
	public double getPercentComplete() {
		return percentComplete;
	}

	/**
	 * @param percentComplete
	 *            the percentComplete to set
	 */
	public void setPercentComplete(double percentComplete) {
		if (isValidPercentage(percentComplete)) {
			this.percentComplete = percentComplete;
		}
	}

	/**
	 * @return the percentScheduledForCompletion
	 */
	public double getPercentScheduledForCompletion() {
		return percentScheduledForCompletion;
	}

	/**
	 * @param percentScheduledForCompletion
	 *            the percentScheduledForCompletion to set
	 */
	public void setPercentScheduledForCompletion(
			double percentScheduledForCompletion) {
		if (isValidPercentage(percentScheduledForCompletion)) {
			this.percentScheduledForCompletion = percentScheduledForCompletion;
		}
	}

	/**
	 * @return the actualCost
	 */
	public double getActualCost() {
		return actualCost;
	}

	/**
	 * @param actualCost
	 *            the actualCost to set
	 */
	public void setActualCost(double actualCost) {
		if (isValidValue(actualCost)) {
			this.actualCost = actualCost;
		}
	}

	/**
	 * @return the earnedValue
	 */
	public double getEarnedValue() {
		return earnedValue;
	}

	/**
	 * @param earnedValue
	 *            the earnedValue to set
	 */
	public void setEarnedValue(double earnedValue) {
		if (isValidValue(earnedValue)) {
			this.earnedValue = earnedValue;
		}
	}

	/**
	 * @return the costVariance
	 */
	public double getCostVariance() {
		return costVariance;
	}

	/**
	 * @param costVariance
	 *            the costVariance to set
	 */
	public void setCostVariance(double costVariance) {
		if (isValidValue(costVariance)) {
			this.costVariance = costVariance;
		}
	}

	/**
	 * @return the scheduleVariance
	 */
	public double getScheduleVariance() {
		return scheduleVariance;
	}

	/**
	 * @param scheduleVariance
	 *            the scheduleVariance to set
	 */
	public void setScheduleVariance(double scheduleVariance) {
		if (isValidValue(scheduleVariance)) {
			this.scheduleVariance = scheduleVariance;
		}
	}

	/**
	 * @return the costPerformanceIndex
	 */
	public double getCostPerformanceIndex() {
		return costPerformanceIndex;
	}

	/**
	 * @param costPerformanceIndex
	 *            the costPerformanceIndex to set
	 */
	public void setCostPerformanceIndex(double costPerformanceIndex) {
		if (isValidValue(costPerformanceIndex)) {
			this.costPerformanceIndex = costPerformanceIndex;
		}
	}

	/**
	 * @return the schedulePerformanceIndex
	 */
	public double getSchedulePerformanceIndex() {
		return schedulePerformanceIndex;
	}

	/**
	 * @param schedulePerformanceIndex
	 *            the schedulePerformanceIndex to set
	 */
	public void setSchedulePerformanceIndex(double schedulePerformanceIndex) {
		if (isValidValue(schedulePerformanceIndex)) {
			this.schedulePerformanceIndex = schedulePerformanceIndex;
		}
	}

	/**
	 * @return the estimateAtCompletion
	 */
	public double getEstimateAtCompletion() {
		return estimateAtCompletion;
	}

	/**
	 * @param estimateAtCompletion
	 *            the estimateAtCompletion to set
	 */
	public void setEstimateAtCompletion(double estimateAtCompletion) {
		if (isValidValue(estimateAtCompletion)) {
			this.estimateAtCompletion = estimateAtCompletion;
		}
	}

	/**
	 * @return the estimateToComplete
	 */
	public double getEstimateToComplete() {
		return estimateToComplete;
	}

	/**
	 * @param estimateToComplete
	 *            the estimateToComplete to set
	 */
	public void setEstimateToComplete(double estimateToComplete) {
		if (isValidValue(estimateToComplete)) {
			this.estimateToComplete = estimateToComplete;
		}
	}

	/**
	 * @return the activityList
	 */
	public ArrayList<Activity> getActivityList() {
		return activityList;
	}

	/**
	 * @param activityList
	 *            the activityList to set
	 */
	public void setActivityList(ArrayList<Activity> activityList) {
		this.activityList = activityList;
	}

	/**
	 * @return
	 */
	public java.sql.Date getStartDate() {
		java.sql.Date sqlDate = new java.sql.Date(this.startDate.getTime());
		return (java.sql.Date) sqlDate;
	}

}
