package PModel;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;


/**
 * Activities derive from Manageable Class, and are identified by a Project ID, as well as an activity number.
 * @author  Alex Huot
 */

public class Activity extends Manageable
{
	public static final int DURATION_SCALAR  = 4;
	public static final int DURATION_DIVISOR = 6;
	
	// User entered and DB stored attributes
	/**
	 * @uml.property  name="projectID"
	 */
	private int    projectID;	
	/**
	 * @uml.property  name="activityNumber"
	 */
	private int    activityNumber;
	/**
	 * @uml.property  name="mostLikelyTimeToCompletion"
	 */
	private double mostLikelyTimeToCompletion;
	/**
	 * @uml.property  name="optimisticTimeToCompletion"
	 */
	private double optimisticTimeToCompletion;
	/**
	 * @uml.property  name="pessimisticTimeToCompletion"
	 */
	private double pessimisticTimeToCompletion;
	/**
	 * @uml.property  name="targetCompletionDate"
	 */
	private double targetCompletionDate;
	
	// Calculated attributes
	/**
	 * @uml.property  name="duration"
	 */
	private double duration;
	/**
	 * @uml.property  name="durationFloat"
	 */
	private double durationFloat;
	/**
	 * @uml.property  name="durationStandardDevitation"
	 */
	private double durationStandardDevitation;
	/**
	 * @uml.property  name="maximumPossibleDuration"
	 */
	private double maximumPossibleDuration;
	/**
	 * @uml.property  name="earliestStart"
	 */
	private double earliestStart;
	/**
	 * @uml.property  name="earliestFinish"
	 */
	private double earliestFinish;
	/**
	 * @uml.property  name="latestStart"
	 */
	private double latestStart;
	/**
	 * @uml.property  name="latestFinish"
	 */
	private double latestFinish;
	
	/**
	 * @uml.property  name="project"
	 * @uml.associationEnd  
	 */
	private Project project; //TODO Use me

	/**
	 * Constructor for an Activity.
	 * @param ProjectID Associated Project's ID
	 * @param name Activity name
	 * @param description Activity Description
	 */
	public Activity(int ProjectID, 
					String name, 
					String description, 
					double plannedValue,
					double mostLikelyTimeToCompletion,
					double optimisticTimeToCompletion,
					double pessimisticTimeToCompletion,
					double targetCompletionDate) {
		
		super(name, description);
		this.projectID 						= ProjectID;
		this.mostLikelyTimeToCompletion 	= mostLikelyTimeToCompletion;
		this.optimisticTimeToCompletion 	= optimisticTimeToCompletion;
		this.pessimisticTimeToCompletion	= pessimisticTimeToCompletion;
		this.targetCompletionDate			= targetCompletionDate;
		
		setPlannedValue(plannedValue);
		setDuration();
		setStatus(false);
		
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
		
		setPlannedValue(a.getPlannedValue());
		setDuration();
		
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
	/**
	 * @param projectID   the projectID to set
	 * @uml.property  name="projectID"
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
	 * @param mostLikelyTimeToCompletion   the mostLikelyTimeToCompletion to set
	 * @uml.property  name="mostLikelyTimeToCompletion"
	 */
	public void setMostLikelyTimeToCompletion(double mostLikelyTimeToCompletion) {
		this.mostLikelyTimeToCompletion = mostLikelyTimeToCompletion;
	}
	/**
	 * @param optimisticTimeToCompletion   the optimisticTimeToCompletion to set
	 * @uml.property  name="optimisticTimeToCompletion"
	 */
	public void setOptimisticTimeToCompletion(double optimisticTimeToCompletion) {
		this.optimisticTimeToCompletion = optimisticTimeToCompletion;
	}
	/**
	 * @param pessimisticTimeToCompletion   the pessimisticTimeToCompletion to set
	 * @uml.property  name="pessimisticTimeToCompletion"
	 */
	public void setPessimisticTimeToCompletion(double pessimisticTimeToCompletion) {
		this.pessimisticTimeToCompletion = pessimisticTimeToCompletion;
	}
	/**
	 * @param targetCompletionDate   the targetCompletionDate to set
	 * @uml.property  name="targetCompletionDate"
	 */
	public void setTargetCompletionDate(double targetCompletionDate) {
		this.targetCompletionDate = targetCompletionDate;
	}
	/**
	 * @param duration   the duration to set
	 * @uml.property  name="duration"
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
	/**
	 * @param durationFloat   the durationFloat to set
	 * @uml.property  name="durationFloat"
	 */
	public void setDurationFloat(double durationFloat) {
		this.durationFloat = durationFloat;
	}
	/**
	 * @param durationStandardDevitation   the durationStandardDevitation to set
	 * @uml.property  name="durationStandardDevitation"
	 */
	public void setDurationStandardDevitation(double durationStandardDevitation) {
		this.durationStandardDevitation = durationStandardDevitation;
	}
	/**
	 * @param maximumPossibleDuration   the maximumPossibleDuration to set
	 * @uml.property  name="maximumPossibleDuration"
	 */
	public void setMaximumPossibleDuration(double maximumPossibleDuration) {
		this.maximumPossibleDuration = maximumPossibleDuration;
	}
	/**
	 * @param earliestStart   the earliestStart to set
	 * @uml.property  name="earliestStart"
	 */
	public void setEarliestStart(double earliestStart) {
		this.earliestStart = earliestStart;
	}
	/**
	 * @param earliestFinish   the earliestFinish to set
	 * @uml.property  name="earliestFinish"
	 */
	public void setEarliestFinish(double earliestFinish) {
		this.earliestFinish = earliestFinish;
	}
	/**
	 * @param latestStart   the latestStart to set
	 * @uml.property  name="latestStart"
	 */
	public void setLatestStart(double latestStart) {
		this.latestStart = latestStart;
	}
	/**
	 * @param latestFinish   the latestFinish to set
	 * @uml.property  name="latestFinish"
	 */
	public void setLatestFinish(double latestFinish) {
		this.latestFinish = latestFinish;
	}
	/**
	 * @param project   the project to set
	 * @uml.property  name="project"
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	/**
	 * @return   the projectID
	 * @uml.property  name="projectID"
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
	 * @return   the mostLikelyTimeToCompletion
	 * @uml.property  name="mostLikelyTimeToCompletion"
	 */
	public double getMostLikelyTimeToCompletion() {
		return mostLikelyTimeToCompletion;
	}
	/**
	 * @return   the optimisticTimeToCompletion
	 * @uml.property  name="optimisticTimeToCompletion"
	 */
	public double getOptimisticTimeToCompletion() {
		return optimisticTimeToCompletion;
	}
	/**
	 * @return   the pessimisticTimeToCompletion
	 * @uml.property  name="pessimisticTimeToCompletion"
	 */
	public double getPessimisticTimeToCompletion() {
		return pessimisticTimeToCompletion;
	}
	/**
	 * @return   the targetCompletionDate
	 * @uml.property  name="targetCompletionDate"
	 */
	public double getTargetCompletionDate() {
		return targetCompletionDate;
	}
	/**
	 * @return   the duration
	 * @uml.property  name="duration"
	 */
	public double getDuration() {
		return duration;
	}
	/**
	 * @return   the durationFloat
	 * @uml.property  name="durationFloat"
	 */
	public double getDurationFloat() {
		return durationFloat;
	}
	/**
	 * @return   the durationStandardDevitation
	 * @uml.property  name="durationStandardDevitation"
	 */
	public double getDurationStandardDevitation() {
		return durationStandardDevitation;
	}
	/**
	 * @return   the maximumPossibleDuration
	 * @uml.property  name="maximumPossibleDuration"
	 */
	public double getMaximumPossibleDuration() {
		return maximumPossibleDuration;
	}
	/**
	 * @return   the earliestStart
	 * @uml.property  name="earliestStart"
	 */
	public double getEarliestStart() {
		return earliestStart;
	}
	/**
	 * @return   the earliestFinish
	 * @uml.property  name="earliestFinish"
	 */
	public double getEarliestFinish() {
		return earliestFinish;
	}
	/**
	 * @return   the latestStart
	 * @uml.property  name="latestStart"
	 */
	public double getLatestStart() {
		return latestStart;
	}
	/**
	 * @return   the latestFinish
	 * @uml.property  name="latestFinish"
	 */
	public double getLatestFinish() {
		return latestFinish;
	}
	/**
	 * @return   the project
	 * @uml.property  name="project"
	 */
	public Project getProject() {
		return project;
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
	
	
}

