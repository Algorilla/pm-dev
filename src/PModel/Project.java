package PModel;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;


/**
 * Projects derive from Manageable Class, and are identified by a Project ID, as well as the Project Manager's ID, which is that Member's ID. <p>Project's can also contain a list of activities.
 * @author  Alex Huot
 */
public class Project extends Manageable
{

	/**
	 * @uml.property  name="projectID"
	 */
	private int projectID;
	/**
	 * @uml.property  name="managerID"
	 */
	private int managerID;
	/**
	 * @uml.property  name="activityList"
	 */
	private Set<Activity> activityList;	

	/**
	 * Sole constructor for Project ID. Project ID is automatically assigned
	 * based on existing project IDs in the database.
	 * 
	 * @param managerID	Project Manager's ID
	 * @param name Project name
	 * @param description Project description
	 */
	public Project(int managerID, String name, String description) {
		super(name, description);
		this.managerID = managerID;
		// TODO Ensure consistency between start date, deadline and projected length
	}
	
	/**
	 * Sets a new Project ID to an existing project
	 * @param newProjectID   New Project ID
	 * @uml.property  name="projectID"
	 */
	public void setProjectID(int newProjectID) {
		projectID = newProjectID;
		// TODO Ensure unique Project ID
	}
	
	/**
	 * Sets new Project Manager via their ID	
	 * @param newManagerID   New Manager's ID
	 * @uml.property  name="managerID"
	 */
	public void setManagerID(int newManagerID) {
		managerID = newManagerID;
	}
	
	/**
	 * Returns Project ID
	 * @return   Project ID
	 * @uml.property  name="projectID"
	 */
	public int getProjectID() {
		return projectID;
	}
	
	/**
	 * Returns Manager's ID
	 * @return   Manager's ID
	 * @uml.property  name="managerID"
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
	
}

