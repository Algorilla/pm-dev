package PModel;
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

	private int projectID;	
	private int activityNumber;
	private Project project; //TODO Use me

	/**
	 * Constructor for an Activity.
	 * @param ProjectID Associated Project's ID
	 * @param name Activity name
	 * @param description Activity Description
	 * @param start Start date
	 * @param deadline Deadline
	 * @param length Projected length in days
	 */
	public Activity(int ProjectID, String name, String description, Date start, Date deadline, int length) {
		super(name, description, start, deadline, length);
		projectID = ProjectID;
	}
	
	/**
	 * Constructor for an Activity.
	 * @param name
	 * @param description
	 * @param start
	 * @param deadline
	 * @param length
	 */
	public Activity(String name, String description, Date start, Date deadline, int length) {
		super(name, description, start, deadline, length);
	}	
	
	/**
	 * Set Activity's associated Project ID
	 * @param newProjectID New Project ID
	 */
	public void setProjectID(int newProjectID) {
		projectID = newProjectID;
	}
	
	/**
	 * Set Activity's number
	 * @param newNumber New Activity Number
	 */
	public void setNumber(int newNumber) {
		activityNumber = newNumber;
	}
	
	/**
	 * Returns Project ID
	 * @return Project ID
	 */
	public int getProjectID() {
		return projectID;
	}
	
	/**
	 * Returns Activity Number
	 * @return Activity Number
	 */
	public int getNumber() {
		return activityNumber;
	}	
}

