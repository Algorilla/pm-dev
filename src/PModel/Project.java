package PModel;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */
public class Project extends Manageable
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */	
	private int projectID;
	private int managerID;
	private Set<Activity> activityList;	

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */	
	public Project(int managerID, String name, String description, Date start, Date deadline, int length) {
		super(name, description, start, deadline, length);
		this.managerID = managerID;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public void setProjectID(int newProjectID) {
		projectID = newProjectID;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */	
	public void setManagerID(int newManagerID) {
		managerID = newManagerID;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */	
	public int getProjectID() {
		return projectID;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public int getManagerID() {
		return managerID;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */	
	public void addActivity(Activity newActivity) {
		// TODO implement me	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */	
	public void removeActivity(Activity activity) {
		// TODO implement me	
	}
	
}

