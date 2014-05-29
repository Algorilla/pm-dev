package PModel;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Activity extends Manageable
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	
	private int projectID;	
	private int activityNumber;		

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Activity(int ProjectID,String name, String description, Date start, Date deadline, int length) {
		super(name, description, start, deadline, length);
		projectID = ProjectID;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Activity(String name, String description, Date start, Date deadline, int length) {
		super(name, description, start, deadline, length);
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
	public void setNumber(int newNumber) {
		activityNumber = newNumber;
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
	public int getNumber() {
		return activityNumber;
	}
	
}

