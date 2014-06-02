package PModel;
import java.util.Set;
import java.util.HashSet;


/**
 * Activities derive from Manageable Class, and are identified by a Project
 * ID, as well as an activity number.
 * @author Alex Huot
 */
public class Member
{

	private int memberID;
	private String name;
	private String type;
	public Set<Project> project;
	public Set<Activity> activity;
	
	private String username;
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
	 * @param newID New Member ID
	 */
	public void setMemberID(int newID) {
		memberID = newID;
	}
	
	/**
	 * Sets Member's Name	
	 * @param newName New Member name
	 */
	public void setName(String newName) {
		name = newName;	
	}
	
	/**
	 * Sets Member's Type
	 * @param newType New Member Type
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
	 * @param newPassword New password
	 */
	public void setPassword(String newPassword) {
		password = newPassword;
	}
	
	/**
	 * Returns Member ID
	 * @return Member ID
	 */
	public int getMemberID() {
		return memberID;
	}
	
	/**
	 * Returns Member name
	 * @return Member name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns Member type
	 * @return Member type
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
	 * @return Member password
	 */	
	public String getPassword() {
		return password;
	}
}

