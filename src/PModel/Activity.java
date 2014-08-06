package PModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;


/**
 * Activities derive from Manageable Class, and are identified by a Project
 * ID, as well as an activity number.
 * @author Alex Huot
 */

public class Activity extends Manageable
{
	public static final int DURATION_SCALAR  = 4;
	public static final int DURATION_DIVISOR = 6;
	
	// User entered and DB stored attributes
	private int    projectID;	
	private int    activityNumber;
	private double mostLikelyTimeToCompletion;
	private double optimisticTimeToCompletion;
	private double pessimisticTimeToCompletion;
	private double targetCompletionDate;
	private double percentComplete;
	private double actualCost;
	
	// Calculated attributes
	private double duration;
	private double durationFloat;
	private double durationStandardDevitation;
	private double maximumPossibleDuration;
	private double earliestStart;
	private double earliestFinish;
	private double latestStart;
	private double latestFinish;
	
	private ArrayList<Activity> precedents;
	private ArrayList<Activity> dependents;
	
	public Project project; //TODO Use me

	/**
	 * Constructor for an Activity.
	 * @param ProjectID Associated Project's ID
	 * @param name Activity name
	 * @param description Activity Description
	 * @param plannedValue Activity PlannedValue
	 */
	public Activity(int ProjectID, 
					String name, 
					String description, 
					double plannedValue,
					double mostLikelyTimeToCompletion,
					double optimisticTimeToCompletion,
					double pessimisticTimeToCompletion,
					double targetCompletionDate,
					double percentComplete,
					double actualCost,
					boolean status
					) {
		
		super(name, description);
		this.projectID 						= ProjectID;
		this.mostLikelyTimeToCompletion 	= mostLikelyTimeToCompletion;
		this.optimisticTimeToCompletion 	= optimisticTimeToCompletion;
		this.pessimisticTimeToCompletion	= pessimisticTimeToCompletion;
		this.targetCompletionDate			= targetCompletionDate;
		this.percentComplete				= percentComplete;
		this.actualCost 					= actualCost;
		
		this.dependents = new ArrayList<Activity>();
		this.precedents = new ArrayList<Activity>();
		
		setStatus(status);
		setPlannedValue(plannedValue);
		setDuration();
		this.setStandardDevitation();
		
	}

	/**
	 * Copy Constructor for Activity
	 * @param a An Activity Object
	 * */
	public Activity(Activity a){
		
		super(a.getName(), a.getDescr());
		
		this.projectID 						= a.getProjectID();
		this.mostLikelyTimeToCompletion 	= a.getMostLikelyTimeToCompletion();
		this.optimisticTimeToCompletion 	= a.getOptimisticTimeToCompletion();
		this.pessimisticTimeToCompletion	= a.getPessimisticTimeToCompletion();
		this.targetCompletionDate			= a.getTargetCompletionDate();
		this.percentComplete				= a.getPercentComplete();
		this.actualCost						= a.getActualCost();
		
		this.setPlannedValue(a.getPlannedValue());
		this.updateStatus();
		this.setDuration();
		this.setStandardDevitation();
		
	}
	
	/**
	 * 
	 */
	private void setStandardDevitation() {
		double numerator = this.pessimisticTimeToCompletion - this.optimisticTimeToCompletion;
		this.durationStandardDevitation = numerator / DURATION_DIVISOR;
		
	}

	/**
	 * Set the single duration value for the activity, calculated for estimated entries.
	 * */
	private void setDuration() {
		double numerator = (this.mostLikelyTimeToCompletion * DURATION_SCALAR +
							this.optimisticTimeToCompletion + this.pessimisticTimeToCompletion);
		this.duration = numerator / DURATION_DIVISOR;
	}
	
	////////////////////////////// SETTERS AND GETTERS //////////////////////////////
	public void updateStatus(){
		if(this.getPercentComplete() > 0.999){
			this.setStatus(true);
		}
		else{
			this.setStatus(false);
		}
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	/**
	 * @param activityNumber the activityNumber to set
	 */
	public void setNumber(int activityNumber) {
		this.activityNumber = activityNumber;
	}
	/**
	 * @param mostLikelyTimeToCompletion the mostLikelyTimeToCompletion to set
	 */
	public void setMostLikelyTimeToCompletion(double mostLikelyTimeToCompletion) {
		this.mostLikelyTimeToCompletion = mostLikelyTimeToCompletion;
	}
	/**
	 * @param optimisticTimeToCompletion the optimisticTimeToCompletion to set
	 */
	public void setOptimisticTimeToCompletion(double optimisticTimeToCompletion) {
		this.optimisticTimeToCompletion = optimisticTimeToCompletion;
	}
	/**
	 * @param pessimisticTimeToCompletion the pessimisticTimeToCompletion to set
	 */
	public void setPessimisticTimeToCompletion(double pessimisticTimeToCompletion) {
		this.pessimisticTimeToCompletion = pessimisticTimeToCompletion;
	}
	/**
	 * @param targetCompletionDate the targetCompletionDate to set
	 */
	public void setTargetCompletionDate(double targetCompletionDate) {
		this.targetCompletionDate = targetCompletionDate;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
	/**
	 * @param durationFloat the durationFloat to set
	 */
	public void setDurationFloat(double durationFloat) {
		this.durationFloat = durationFloat;
	}
	/**
	 * @param durationStandardDevitation the durationStandardDevitation to set
	 */
	public void setDurationStandardDevitation(double durationStandardDevitation) {
		this.durationStandardDevitation = durationStandardDevitation;
	}
	/**
	 * @param maximumPossibleDuration the maximumPossibleDuration to set
	 */
	public void setMaximumPossibleDuration(double maximumPossibleDuration) {
		this.maximumPossibleDuration = maximumPossibleDuration;
	}
	/**
	 * @param earliestStart the earliestStart to set
	 */
	public void setEarliestStart(double earliestStart) {
		this.earliestStart = earliestStart;
	}
	/**
	 * @param earliestFinish the earliestFinish to set
	 */
	public void setEarliestFinish(double earliestFinish) {
		this.earliestFinish = earliestFinish;
	}
	/**
	 * @param latestStart the latestStart to set
	 */
	public void setLatestStart(double latestStart) {
		this.latestStart = latestStart;
	}
	/**
	 * @param latestFinish the latestFinish to set
	 */
	public void setLatestFinish(double latestFinish) {
		this.latestFinish = latestFinish;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	/**
	 * @param actualCost the actualCost to set
	 */
	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}
	/**
	 * @return the actualCost
	 */
	public double getActualCost() {
		return actualCost;
	}
	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}
	/**
	 * @return the activityNumber
	 */
	public int getNumber() {
		return activityNumber;
	}
	/**
	 * @return the mostLikelyTimeToCompletion
	 */
	public double getMostLikelyTimeToCompletion() {
		return mostLikelyTimeToCompletion;
	}
	/**
	 * @return the optimisticTimeToCompletion
	 */
	public double getOptimisticTimeToCompletion() {
		return optimisticTimeToCompletion;
	}
	/**
	 * @return the pessimisticTimeToCompletion
	 */
	public double getPessimisticTimeToCompletion() {
		return pessimisticTimeToCompletion;
	}
	/**
	 * @return the targetCompletionDate
	 */
	public double getTargetCompletionDate() {
		return targetCompletionDate;
	}
	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}
	/**
	 * @return the durationFloat
	 */
	public double getDurationFloat() {
		return durationFloat;
	}
	/**
	 * @return the durationStandardDevitation
	 */
	public double getDurationStandardDevitation() {
		return durationStandardDevitation;
	}
	/**
	 * @return the maximumPossibleDuration
	 */
	public double getMaximumPossibleDuration() {
		return maximumPossibleDuration;
	}
	/**
	 * @return the earliestStart
	 */
	public double getEarliestStart() {
		return earliestStart;
	}
	/**
	 * @return the earliestFinish
	 */
	public double getEarliestFinish() {
		return earliestFinish;
	}
	/**
	 * @return the latestStart
	 */
	public double getLatestStart() {
		return latestStart;
	}
	/**
	 * @return the latestFinish
	 */
	public double getLatestFinish() {
		return latestFinish;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	
	/**
	 * @return the precedents
	 */
	public ArrayList<Activity> getPrecedents() {
		return precedents;
	}
	/**
	 * @return the dependents
	 */
	public ArrayList<Activity> getDependents() {
		return dependents;
	}
	/**
	 * @param precedents the precedents to set
	 */
	public void setPrecedents(ArrayList<Activity> precedents) {
		this.precedents = precedents;
	}

	/**
	 * @param dependents the dependents to set
	 */
	public void setDependents(ArrayList<Activity> dependents) {
		this.dependents = dependents;
	}

	public void addDependent(Activity dep){
		this.dependents.add(dep);
	}
	
	public void addPrecedent(Activity pre){
		this.precedents.add(pre);
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

	@Override
    public String toString(){
      return getName();
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (activityNumber != other.activityNumber)
			return false;
		if (projectID != other.projectID)
			return false;
		return true;
	}

	/**
	 * @return
	 */
	public static Activity makeDummyStart() {
		Activity temp = new Activity(0,"DummyStart","",0,0,0,0,0,0,0,false);
		return temp;
	}

	/**
	 * @return
	 */
	public static Activity makeDummyFinish() {
		Activity temp = new Activity(Integer.MAX_VALUE,"DummyFinish","",0,0,0,0,0,0,0,false);
		return temp;
	}
	
	
}

