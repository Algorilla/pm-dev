package Controller;

import JFrames.TeamMemberView;
import JFrames.UserInterface;
import PModel.Activity;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.text.SimpleDateFormat;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

/**
 * Main Controller class for Project Management Software, following MVC design
 * pattern. Controls all manipulation of data in database, and reflects changes
 * via Swing GUI.
 * 
 * @author Robert Wolfstein
 */
public class MainController {

	// Singleton design pattern
	private static MainController self = new MainController();

	public static MainController get() {
		return self;
	}

	// Data
	ArrayList<Member> members = new ArrayList<Member>();
	ArrayList<Project> projects = new ArrayList<Project>();
	ArrayList<Activity> activities = new ArrayList<Activity>();
	ArrayList<MemberActivity> memberActivities = new ArrayList<MemberActivity>();

	// SQLite DB connection data
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	// Session variables
	Member currentUser;
	Project currentProject;

	// Date format
	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

	// Assisting Objects
	DataLoader dataLoader = new DataLoader();
	DataUpdater dataUpdater = new DataUpdater();
	DataDeleter dataDeleter = new DataDeleter();

	// Error Controller Instance
	ErrorController ec = ErrorController.get();

	/**
	 * Initializes controller by connecting to the DB and loading the data into
	 * memory.
	 */
	private MainController() {
		conn = SQLiteDBConnection.ConnectDb();
		dataLoader.loadData(this);
	}

	/**
	 * Logs the user in.
	 * 
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 * @return Login status ( success or fail )
	 */
	public boolean login(String username, String password) {// DMITRI all this
															// garbage
		for (Member member : members) {
			if (member.getUserName().equals(username)
					&& member.getPassword().equals(password)) {
				currentUser = member;
				if (currentUser.getType().equals("manager")) {
					UserInterface userAccount = new UserInterface(100, 100,
							1000, 600, "", username);
					userAccount.setVisible(true);
					return true;
				} else {
					TeamMemberView memberView = new TeamMemberView();
					memberView.setVisible(true);
					return true;
				}
			}
		}
		// JOptionPane.showMessageDialog(null,
		// "Username and password combination is not correct.");
		ec.addError("Username and password combination is not correct.");
		ec.displayErrors();
		return false;
	}

	/**
	 * Returns a list of projects for the currently signed in user.
	 * 
	 * @return A list of projects for the currently signed in user.
	 */
	public List getProjectList() {
		final List projectList = new List();
		for (Project project : projects)
			if (project.getManagerID() == currentUser.getMemberID())
				projectList.add(project.getName());
		return projectList;
	}

	/**
	 * Opens a project selected in the OpenProject list
	 * 
	 * @param name
	 *            Name of project to open
	 */
	public void openProject(String name) {
		for (Project project : projects)
			if (project.getName() == name)
				currentProject = project;
	}

	/**
	 * Display project and its data in the GUI
	 * 
	 * @param table
	 */
	public void getActivityList(JTable table) {
		// TODO: put this in a more appropriate place - DMITRI
		int pid = currentProject.getProjectID();
		String sql = "select * from Activities  where PID = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.execute();
			pst.close();
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(null,ex);
			// TODO: error controller
			ec.addError(ex.getLocalizedMessage());
		}
	}

	/**
	 * Display the Activities to which a Member has been assigned.
	 * 
	 * @param table
	 *            The JTable into which the results are stored
	 */
	public void getActivityListForCurrentTeamMember(JTable table) {
		// TODO: put this in a more appropriate place - DMITRI
		int mid = currentUser.getMemberID();
		String sql = "select Activities.PID, Activities.Number, Activities.Name"
				+ " from Activities, MemberActivities"
				+ "  where MemberActivities.PID = Activities.PID   AND "
				+ " 	 	 MemberActivities.Number = Activities.Number   AND"
				+ "  		 MemberActivities.MID = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, mid);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.execute();
			pst.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	/**
	 *  
	 * */
	public ArrayList<Activity> getActivityListForCurrentProject() {
		final ArrayList<Activity> activityList = new ArrayList<Activity>();
		for (Activity activity : activities)
			if (activity.getProjectID() == currentProject.getProjectID())
				activityList.add(activity);
		return activityList;
	}

	/**
	 * Fills comboBox
	 * 
	 * @param comboBox
	 *            comboBox to fill
	 */
	public void Fillcombo(JComboBox comboBox) {
		// TODO: put this in a more appropriate place - DMITRI
		int pid = currentProject.getProjectID();
		String sql = "select * from Activities  where PID = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			rs = pst.executeQuery();

			while (rs.next()) {
				String name = rs.getString("Name");
				comboBox.addItem(name);
			}
			pst.execute();
			pst.close();
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(null,ex);
		}
	}

	/**
	 * MouseClickTable (Description to be filled)
	 * 
	 * @param table
	 *            A JTable
	 */
	public void mouseClickTable(JTable table) {
		// TODO: put this in a more appropriate place - DMITRI
		int row = table.getSelectedRow();
		int pid = currentProject.getProjectID();
		try {
			String table_click = (table.getModel().getValueAt(row, 2))
					.toString();
			String sql = "select * from Activities  where PID = ? and Name = ?";

			pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			pst.setString(2, table_click);
			rs = pst.executeQuery();

			pst.execute();
			pst.close();
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(null,ex); DMITRI
		}

	}

	/**
	 * Returns a reference to an activity in local memory based on the needed
	 * identifiers.
	 * 
	 * @param PID
	 *            Project ID
	 * @param number
	 *            Activity Number
	 * @return A reference to an activity in local memory
	 */
	public Activity getActivityFromID(int PID, int number) {
		for (Activity activity : activities)
			if (activity.getProjectID() == PID
					&& activity.getNumber() == number)
				return activity;
		return null;
	}

	/**
	 * Creates a user.
	 * 
	 * @param member
	 *            Member object from which to create user
	 * @return The created user
	 */
	public Member createMember(Member member) {
		return dataUpdater.createMember(this, member);
	}

	/**
	 * Updates a user's information into DB.
	 * 
	 * @param member
	 *            The Member to update
	 * @return Update status ( success or fail )
	 */
	public boolean updateMember(Member member) {
		return dataUpdater.updateMember(this, member);
	}

	/**
	 * Deletes a user from DB.
	 * 
	 * @param MID
	 *            ID of Member to delete
	 * @return Deletion status ( success or fail )
	 */
	// TODO: Ensure referential integrity such that objects in other relations
	// do not refer to deleted Users
	public boolean deleteMember(int MID) {
		int x = 0;
		for (Member member : members)
			if (member.getMemberID() == MID) {
				break;
			} else {
				x++;
			}
		return deleteMember(members.get(x));
	}

	/**
	 * Deletes a user
	 * 
	 * @param member
	 *            Member object to delete
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteMember(Member member) {
		return dataDeleter.deleteMember(this, member);
	}

	/**
	 * Creates a project.
	 * 
	 * @param project
	 *            Project object to create
	 * @return Created project
	 */
	public Project initializeProject(Project project) {
		return dataUpdater.initializeProject(this, project);
	}

	/**
	 * Updates a project's information into DB.
	 * 
	 * @param project
	 *            Project object o update
	 * @return Update status ( success or fail )
	 */
	public boolean updateProject(Project project) {
		return dataUpdater.updateProject(this, project);
	}

	/**
	 * Deletes a project from DB.
	 * 
	 * @param PID
	 *            Project's ID
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteProject(int PID) {
		int x = 0;
		for (Project project : projects)
			if (project.getProjectID() == PID) {
				break;
			} else {
				x++;
			}
		return deleteProject(projects.get(x));
	}

	/**
	 * Deletes a project from DB
	 * 
	 * @param projectName
	 *            Project's name
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteProject(String projectName) {
		int x = 0;
		for (Project project : projects)
			if (project.getName() == projectName) {
				break;
			} else {
				x++;
			}
		return deleteProject(projects.get(x));
	}

	/**
	 * Deletes a project from DB
	 * 
	 * @param project
	 *            Project object
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteProject(Project project) {
		return dataDeleter.deleteProject(this, project);
	}

	/**
	 * Creates an activity
	 * 
	 * @param activity
	 *            Activity object to create
	 * @return The created Activity
	 */
	public Activity createActivity(Activity activity) {
		return dataUpdater.createActivity(this, activity);
	}

	/**
	 * Updates an activity's information in DB.
	 * 
	 * @param activity
	 *            Activity to update
	 * @return Update status ( success or fail )
	 */
	public boolean updateActivity(Activity activity) {
		return dataUpdater.updateActivity(this, activity);
	}

	/**
	 * Deletes an activity from DB.
	 * 
	 * @param PID
	 *            Activity's project ID
	 * @param number
	 *            Activity's number
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteActivity(int PID, int number) {
		return dataDeleter.deleteActivity(this, PID, number);
	}

	public boolean createActivityDependencies(Activity activity,
			ArrayList<Activity> activities) {
		return dataUpdater.createActivityDependencies(this, activity,
				activities);
	}

	public MemberActivity initializeMemberActivity(MemberActivity ma) {
		return dataUpdater.initializeMemberActivity(this, ma);
	}

	public ArrayList<Member> getMemberListForAddMemberToActivity() {
		return members;
	}

	/**
	 * @param a
	 *            An Activity Object
	 * @return temp An ArrayList of Activities depend on the Activity passed.
	 * */
	public ArrayList<Activity> getDependantActivities(Activity a) {

		ArrayList<Activity> temp = new ArrayList<Activity>();

		for (Integer i : this.getRelatedActivities(a, "dependent")) {
			for (Activity dep : getActivityListForCurrentProject()) {
				if (dep.getNumber() == i) {
					temp.add(dep);
				}
			}
		}
		return temp;
	}

	/**
	 * @param a
	 *            An Activity Object
	 * @return temp An ArrayList of Activities depend on the Activity passed.
	 * */
	public ArrayList<Activity> getPrecedantActivities(Activity a) {

		ArrayList<Activity> temp = new ArrayList<Activity>();

		for (Integer i : this.getRelatedActivities(a, "precedent")) {
			for (Activity pre : getActivityListForCurrentProject()) {
				if (pre.getNumber() == i) {
					temp.add(pre);
				}
			}
		}
		return temp;
	}

	public ArrayList<Integer> getRelatedActivities(Activity a, String type) {

		return dataLoader.getRelatedActivities(this, a, type);
	}

	/**
	 * Returns a reference to the current user.
	 * 
	 * @return The current program user
	 */
	public Member getCurrentUser() {
		return currentUser;
	}

	/**
	 * Returns a reference to the current project.
	 * 
	 * @return The currently loaded project
	 */
	public Project getCurrentProject() {
		return currentProject;
	}

	public void closeCurrentProject() {
		currentProject = null;
	}
}
