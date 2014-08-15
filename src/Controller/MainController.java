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
	DataLoader dataLoader   = new DataLoader();
	DataUpdater dataUpdater = new DataUpdater();
	DataDeleter dataDeleter = new DataDeleter();

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
	public boolean login(String username, String password) {
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
		ErrorController.get().AddError(
				"Username and password combination is not correct.");
		ErrorController.get().DisplayErrors();
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
		//TODO: put this in a more appropriate place
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
		}
	}

	/**
	 * Display the Activities to which a Member has been assigned.
	 * 
	 * @param table
	 *            The JTable into which the results are stored
	 */
	public void getActivityListForCurrentTeamMember(JTable table) {
		//TODO: put this in a more appropriate place
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
		//TODO: put this in a more appropriate place
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
		//TODO: put this in a more appropriate place
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
			// JOptionPane.showMessageDialog(null,ex);
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
		if (!activity.getName().equals("") && activity.getName() != null
				&& !activity.getDescr().equals("")
				&& activity.getDescr() != null // &&
		) {
			String sql;
			sql = "update Activities set Name=?,Description=?,PlannedValue=?,MostLikelyTimeToCompletion=?,"
					+ "OptimisticTimeToCompletion=?,PessimisticTimeToCompletion=?,TargetCompletionDate=?,Status=?,ActualCost=?,PercentComplete=?"

					+ " where PID = ? and Number = ?";
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, activity.getName());
				pst.setString(2, activity.getDescr());
				pst.setDouble(3, activity.getPlannedValue());
				pst.setDouble(4, activity.getMostLikelyTimeToCompletion());
				pst.setDouble(5, activity.getOptimisticTimeToCompletion());
				pst.setDouble(6, activity.getPessimisticTimeToCompletion());
				pst.setDouble(7, activity.getTargetCompletionDate());
				pst.setBoolean(8, activity.getStatus());
				pst.setDouble(9, activity.getActualCost());
				pst.setDouble(10, activity.getPercentComplete());
				pst.setInt(11, activity.getProjectID());
				pst.setInt(12, activity.getNumber());
				pst.execute();
				pst.close();
				/*
				 * int x=0; for (Activity oldActivity : Activities) if
				 * (oldActivity.getProjectID() == activity.getProjectID() &&
				 * oldActivity.getNumber() == activity.getNumber()) break; else
				 * x++; Activities.set(x,activity);
				 */
				JOptionPane.showMessageDialog(null, "Data saved.");
				return true;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
		return false;
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
		String sql;
		try {
			sql = "select count(*) from Activities where PID = ? and Number = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, PID);
			pst.setInt(2, number);
			rs = pst.executeQuery();
			if (rs.getInt(1) == 0) {
				ErrorController.get().AddError(
						"Activity with PID " + PID + " and name " + number
								+ " does not exist.");
			}
		} catch (SQLException ex) {
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (Exception e) {
			}
		}
		if (ErrorController.get().ErrorsExist()) {
			ErrorController.get().DisplayErrors();
			return false;
		}
		sql = "delete from Activities where PID = ? and Number = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, PID);
			pst.setInt(2, number);
			pst.execute();
			int x = 0;
			for (Activity oldActivity : activities)
				if (oldActivity.getProjectID() == PID
						&& oldActivity.getNumber() == number)
					break;
				else
					x++;
			activities.remove(x);

			sql = "delete from ActivityDependency where PID = ? and Number = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, PID);
			pst.setInt(2, number);
			pst.execute();

			sql = "delete from ActivityDependency where DependantOnPID = ? and DependantOnNumber = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, PID);
			pst.setInt(2, number);
			pst.execute();

			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex + "dde");
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (Exception e) {
			}
		}

		return false;
	}

	public boolean createActivityDependencies(Activity activity,
			ArrayList<Activity> activities) {
		String sql;
		try {
			for (Activity dependantActivity : activities) {
				sql = "insert into ActivityDependency(PID,Number,DependantOnPID,DependantOnNumber) values(?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, activity.getProjectID());
				pst.setInt(2, activity.getNumber());
				pst.setInt(3, dependantActivity.getProjectID());
				pst.setInt(4, dependantActivity.getNumber());
				pst.execute();
				pst.close();
			}
			return true;
		} catch (SQLException ex) {
		}
		return false;
	}

	public MemberActivity initializeMemberActivity(MemberActivity ma) {
		if (ma.getMemberID() > 0 && ma.getProjectID() > 0 && ma.getNumber() > 0) {
			for (MemberActivity theMemberActivity : memberActivities)
				if (theMemberActivity.getMemberID() == ma.getMemberID()
						&& theMemberActivity.getProjectID() == ma
								.getProjectID()
						&& theMemberActivity.getNumber() == ma.getNumber()) {
					ErrorController.get().AddError(
							"Member is already assigned to that activity");
				}
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return null;
			}
			String sql = "insert into MemberActivities (MID,PID,Number) values(?,?,?)";
			try {
				pst = conn.prepareStatement(sql);
				pst.setInt(1, ma.getMemberID());
				pst.setInt(2, ma.getProjectID());
				pst.setInt(3, ma.getNumber());
				pst.execute();
				pst.close();
				memberActivities.add(ma);
				return ma;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
		return null;
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
			for (Activity dep : MainController.get()
					.getActivityListForCurrentProject()) {
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
			for (Activity pre : MainController.get()
					.getActivityListForCurrentProject()) {
				if (pre.getNumber() == i) {
					temp.add(pre);
				}
			}
		}
		return temp;
	}

	public ArrayList<Integer> getRelatedActivities(Activity a, String type) {

		String sql, get;
		ArrayList<Integer> temp = new ArrayList<Integer>();

		if (type.equals("dependent")) {
			sql = "select Number "
					+ "from ActivityDependency "
					+ "where DependantOnPID = ? and DependantOnNumber = ? and PID = ?";
			get = "Number";
		} else {
			sql = "select DependantOnNumber " + "from ActivityDependency "
					+ "where PID = ? and Number = ? and DependantOnPID = ?";
			get = "DependantOnNumber";
		}

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, a.getProjectID());
			pst.setInt(2, a.getNumber());
			pst.setInt(3, a.getProjectID());
			rs = pst.executeQuery();

			while (rs.next()) {
				temp.add(rs.getInt(get));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (Exception e) {
			}
		}
		return temp;
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
