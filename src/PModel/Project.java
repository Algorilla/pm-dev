package PModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import Controller.MainController;


/**
 * Projects derive from Manageable Class, and are identified by a Project
 * ID, as well as the Project Manager's ID, which is that Member's ID.
 * 
 * <p>Project's can also contain a list of activities.
 * @author Alex Huot
 */
public class Project extends Manageable
{

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
	
	MainController mc;

	
	/**
	 * Sole constructor for Project ID. Project ID is automatically assigned
	 * based on existing project IDs in the database.
	 * 
	 * @param managerID	Project Manager's ID
	 * @param name Project name
	 * @param description Project description
	 */
	public Project(int managerID, 
				   String name, 
				   String description,
				   Date   startDate, 
				   double percentComplete, 
				   double budgetAtCompletion,
				   double percentScheduledForCompletion, 
				   double actualCost, 
				   double earnedValue,
				   double costVariance, 
				   double scheduleVariance, 
				   double costPerformanceIndex,
				   double schedulePerformanceIndex,
				   double estimateAtCompletion, 
				   double estimateToComplete
				   ) {
		super(name, description);
		
		this.managerID 						= managerID;
		this.startDate			 			= startDate;
		this.percentComplete				= percentComplete;
		this.budgetAtCompletion				= budgetAtCompletion;
		this.percentScheduledForCompletion 	= percentScheduledForCompletion;
		this.actualCost						= actualCost;
		this.earnedValue					= earnedValue;
		this.costVariance					= costVariance;
		this.scheduleVariance				= scheduleVariance;
		this.costPerformanceIndex			= costPerformanceIndex;
		this.schedulePerformanceIndex		= schedulePerformanceIndex;
		this.estimateAtCompletion			= estimateAtCompletion;
		this.estimateToComplete				= estimateToComplete;
		
		// TODO Ensure consistency between start date, deadline and projected length

	}
	
	/**
	 * Sets a new Project ID to an existing project
	 * @param newProjectID New Project ID
	 */
	public void setProjectID(int newProjectID) {
		projectID = newProjectID;
		// TODO Ensure unique Project ID
	}
	
	/**
	 * Sets new Project Manager via their ID	
	 * @param newManagerID New Manager's ID
	 */
	public void setManagerID(int newManagerID) {
		managerID = newManagerID;
	}
	
	/**
	 * Returns Project ID
	 * @return Project ID
	 */
	public int getProjectID() {
		return projectID;
	}
	
	/**
	 * Returns Manager's ID
	 * @return Manager's ID
	 */
	public int getManagerID() {
		return managerID;
	}
	
	/**
	 * Adds a new Activity to Project's activityList
	 * @param newActivity Activity to be added to Project
	 */
	public void addActivity(Activity newActivity) {
		// TODO implement me	
	}
	
	/**
	 * Removes an existing Activity from Project's activityList
	 * @param activity Activity to be removed from Project
	 */
	public void removeActivity(Activity activity) {
		// TODO implement me	
	}

	/**
	 * @return the budgetAtCompletion
	 */
	public double getBudgetAtCompletion() {
		return budgetAtCompletion;
	}

	/**
	 * @param budgetAtCompletion the budgetAtCompletion to set
	 */
	public void setBudgetAtCompletion(double budgetAtCompletion) {
		this.budgetAtCompletion = budgetAtCompletion;
	}

	/**
	 * @return the percentComplete
	 */
	public double getPercentComplete() {
		return percentComplete;
	}

	/**
	 * @param percentComplete the percentComplete to set
	 */
	public void setPercentComplete(double percentComplete) {
		this.percentComplete = percentComplete;
	}

	/**
	 * @return the percentScheduledForCompletion
	 */
	public double getPercentScheduledForCompletion() {
		return percentScheduledForCompletion;
	}

	/**
	 * @param percentScheduledForCompletion the percentScheduledForCompletion to set
	 */
	public void setPercentScheduledForCompletion(
			double percentScheduledForCompletion) {
		this.percentScheduledForCompletion = percentScheduledForCompletion;
	}

	/**
	 * @return the actualCost
	 */
	public double getActualCost() {
		return actualCost;
	}

	/**
	 * @param actualCost the actualCost to set
	 */
	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	/**
	 * @return the earnedValue
	 */
	public double getEarnedValue() {
		return earnedValue;
	}

	/**
	 * @param earnedValue the earnedValue to set
	 */
	public void setEarnedValue(double earnedValue) {
		this.earnedValue = earnedValue;
	}

	/**
	 * @return the costVariance
	 */
	public double getCostVariance() {
		return costVariance;
	}

	/**
	 * @param costVariance the costVariance to set
	 */
	public void setCostVariance(double costVariance) {
		this.costVariance = costVariance;
	}

	/**
	 * @return the scheduleVariance
	 */
	public double getScheduleVariance() {
		return scheduleVariance;
	}

	/**
	 * @param scheduleVariance the scheduleVariance to set
	 */
	public void setScheduleVariance(double scheduleVariance) {
		this.scheduleVariance = scheduleVariance;
	}

	/**
	 * @return the costPerformanceIndex
	 */
	public double getCostPerformanceIndex() {
		return costPerformanceIndex;
	}

	/**
	 * @param costPerformanceIndex the costPerformanceIndex to set
	 */
	public void setCostPerformanceIndex(double costPerformanceIndex) {
		this.costPerformanceIndex = costPerformanceIndex;
	}

	/**
	 * @return the schedulePerformanceIndex
	 */
	public double getSchedulePerformanceIndex() {
		return schedulePerformanceIndex;
	}

	/**
	 * @param schedulePerformanceIndex the schedulePerformanceIndex to set
	 */
	public void setSchedulePerformanceIndex(double schedulePerformanceIndex) {
		this.schedulePerformanceIndex = schedulePerformanceIndex;
	}

	/**
	 * @return the estimateAtCompletion
	 */
	public double getEstimateAtCompletion() {
		return estimateAtCompletion;
	}

	/**
	 * @param estimateAtCompletion the estimateAtCompletion to set
	 */
	public void setEstimateAtCompletion(double estimateAtCompletion) {
		this.estimateAtCompletion = estimateAtCompletion;
	}

	/**
	 * @return the estimateToComplete
	 */
	public double getEstimateToComplete() {
		return estimateToComplete;
	}

	/**
	 * @param estimateToComplete the estimateToComplete to set
	 */
	public void setEstimateToComplete(double estimateToComplete) {
		this.estimateToComplete = estimateToComplete;
	}

	/**
	 * @return the activityList
	 */
	public ArrayList<Activity> getActivityList() {
		return activityList;
	}

	/**
	 * @param activityList the activityList to set
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

