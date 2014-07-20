package PModel;
import java.util.Set;
import java.util.HashSet;


/**
 * Activities derive from Manageable Class, and are identified by a Project ID, as well as an activity number.
 * @author  Alex Huot
 */
public class Member
{

	/**
	 * @uml.property  name="memberID"
	 */
	private int memberID;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="type"
	 */
	private String type;
	/**
	 * @uml.property  name="project"
	 */
	public Set<Project> project;
	/**
	 * @uml.property  name="activity"
	 */
	public Set<Activity> activity;
	
	/**
	 * @uml.property  name="username"
	 */
	private String username;
	/**
	 * @uml.property  name="password"
	 */
	private String password;

	/**
	 * Sole constructor for a Member. Member ID will be automatically generated
	 * based on existing Member IDs in database. IDs are auto-incremented.
	 * @param name Member Name
	 * @param type Member Type
	 * @param username Member username
	 * @param password Member password
	 */
	public Member(String name, String type, String username, String password) {
		this.name = name;
		this.type = type;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Set Member's ID
	 * @param newID   New Member ID
	 * @uml.property  name="memberID"
	 */
	public void setMemberID(int newID) {
		memberID = newID;
	}
	
	/**
	 * Sets Member's Name	
	 * @param newName   New Member name
	 * @uml.property  name="name"
	 */
	public void setName(String newName) {
		name = newName;	
	}
	
	/**
	 * Sets Member's Type
	 * @param newType   New Member Type
	 * @uml.property  name="type"
	 */
	public void setType(String newType) {
		type = newType;
	}
	
	/**
	 * Sets Member's username	
	 * @param newUserName New username
	 */
	public void setUserName(String newUserName) {
		username = newUserName;
	}
	
	/**
	 * Sets	Member's password
	 * @param newPassword   New password
	 * @uml.property  name="password"
	 */
	public void setPassword(String newPassword) {
		password = newPassword;
	}
	
	/**
	 * Returns Member ID
	 * @return   Member ID
	 * @uml.property  name="memberID"
	 */
	public int getMemberID() {
		return memberID;
	}
	
	/**
	 * Returns Member name
	 * @return   Member name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns Member type
	 * @return   Member type
	 * @uml.property  name="type"
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns Member username
	 * @return Member username
	 */
	public String getUserName() {
		return username;
	}
	
	/**
	 * Returns Member password
	 * @return   Member password
	 * @uml.property  name="password"
	 */	
	public String getPassword() {
		return password;
	}
	
	@Override
    public String toString(){
      return name;
    }
}

