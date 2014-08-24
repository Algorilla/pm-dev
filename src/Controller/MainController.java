package Controller;

import Driver.DriverClass;
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

	public synchronized static MainController get() {
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

	ErrorController ec = ErrorController.get();

	private MainController() {
		conn = SQLiteDBConnection.ConnectDb();
		dataLoader.loadData(this);
	}

	public boolean login(String username, String password) {
		boolean ret = false;
		for (Member member : members) {
			if (member.getUserName().equals(username)
					&& member.getPassword().equals(password)) {
				currentUser = member;
				if (currentUser.getType().equals("manager")) {
					// TODO: remove this when newGui replaces old
					// if (!DriverClass.newGui) {
					// UserInterface userAccount = new UserInterface(100, 100,
					// 1000, 600, "", username);
					// userAccount.setVisible(true);
					// }
					ret = true;
					break;
				} else {
					// TODO: once memberView is refactored, this should be gone
					//

					ret = true;
					break;
				}
			}
		}
		// JOptionPane.showMessageDialog(null,
		// "Username and password combination is not correct.");
		// TODO: move logic to DisplayController
		// ec.addError("Username and password combination is not correct.");
		// ec.displayErrors();
		return ret;
	}

	public List getProjectList() {
		final List projectList = new List();
		for (Project project : projects)
			if (project.getManagerID() == currentUser.getMemberID())
				projectList.add(project.getName());
		return projectList;
	}

	public void openProject(String name) {
		for (Project project : projects) {
			if (project.getName() == name) {
				currentProject = project;
				DisplayController.get().notifyChange(PModelChange.OPENED_PROJECT);
				break;
			}
		}
	}

	public void getActivitiesListForCurrentProject() {
		dataLoader.getTableFormattedActivityList(this);
	}

	public void getActivityListForCurrentTeamMember(JTable table) {
		// TODO : Invesrigate
		dataLoader.getActivityListForCurrentTeamMember(table);
	}

	public ArrayList<Activity> getActivityListForCurrentProject() {
		final ArrayList<Activity> activityList = new ArrayList<Activity>();
		for (Activity activity : activities)
			if (activity.getProjectID() == currentProject.getProjectID())
				activityList.add(activity);
		return activityList;
	}

	public void Fillcombo(JComboBox comboBox) {
		// TODO: this should be moved to DataLoader
		// See getTableFormattedActivityList for example
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

	public Activity getActivityFromID(int PID, int number) {
		for (Activity activity : activities)
			if (activity.getProjectID() == PID
					&& activity.getNumber() == number)
				return activity;
		return null;
	}

	public Member createMember(Member member) {
		return dataUpdater.createMember(this, member);
	}

	public boolean updateMember(Member member) {
		return dataUpdater.updateMember(this, member);
	}

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

	public boolean deleteMember(Member member) {
		return dataDeleter.deleteMember(this, member);
	}

	void initializeProject(Project newProject) {
		boolean projectNameAlreadyExists = false;

		for (Project project : projects) {
			if (project.getManagerID() == newProject.getManagerID()
					&& project.getName().equalsIgnoreCase(newProject.getName())) {
				projectNameAlreadyExists = true;
			}
		}

		if (projectNameAlreadyExists) {
			ec.showError("Project with same name already exists");
		} else if (dataUpdater.initializeProject(this, newProject)) {
			currentProject = newProject;
			DisplayController.get().notifyChange(PModelChange.CREATED_PROJECT);
		}
	}

	public boolean updateProject(Project project) {
		return dataUpdater.updateProject(this, project);
	}

	public boolean deleteProject(int PID) {
		int projectCounter = 0;
		for (Project project : projects)
			if (project.getProjectID() == PID) {
				break;
			} else {
				projectCounter++;
			}
		return deleteProject(projects.get(projectCounter));
	}

	public void deleteProject(String projectName) {
		int projectCounter = 0;
		for (Project project : projects) {
			if (project.getName() == projectName) {
				break;
			} else {
				projectCounter++;
			}
		}

		if (deleteProject(projects.get(projectCounter))) {
			notifyDisplayController(PModelChange.DELETED_PROJECT);
		} else {
			ec.showError("Unable to delete project");
		}
	}

	public boolean deleteProject(Project project) {
		return dataDeleter.deleteProject(this, project);
	}

	public Activity createActivity(Activity activity) {
		return dataUpdater.createActivity(this, activity);
	}

	public boolean updateActivity(Activity activity) {
		return dataUpdater.updateActivity(this, activity);
	}

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

	public Member getCurrentUser() {
		return currentUser;
	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void closeCurrentProject() {
		currentProject = null;
	}

	public void notifyDisplayController(PModelChange updateType) {
		DisplayController.get().notifyChange(updateType);
	}
}