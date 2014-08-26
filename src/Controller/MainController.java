package Controller;

import PModel.Activity;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class MainController {

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
	private Member currentUser;
	private Project currentProject;

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

	boolean login(String username, String password) {
		for (Member member : members) {
			if (member.getUserName().equals(username)
					&& member.getPassword().equals(password)) {
				currentUser = member;
				return true;
			}
		}
		return false;
	}

	void logout() {
		currentUser = null;
		currentProject = null;
	}

	void openProject(String name) {
		for (Project project : projects) {
			if (project.getName() == name) {
				currentProject = project;
				DisplayController.get().notifyChange(
						PModelChange.OPENED_PROJECT);
				break;
			}
		}
	}

	ArrayList<String> getProjectNamesForCurrentManager() {
		ArrayList<String> projectList = new ArrayList<String>();
		for (Project project : projects)
			if (project.getManagerID() == currentUser.getMemberID())
				projectList.add(project.getName());
		return projectList;
	}

	ArrayList<String> getMemberNamesForAddMemberToActivity() {
		ArrayList<String> memberNames = new ArrayList<String>();
		for (Member member : members) {
			memberNames.add(member.getName());
		}
		return memberNames;
	}

	void loadFormatedActivityListForCurrentProject() {
		dataLoader.getActivityListForCurrentProject(this);
	}

	void loadFormatedActivityListForCurrentTeamMember() {
		dataLoader.getActivityListForCurrentTeamMember(this);
	}

	void loadEmptyActivitiesListForDeleteProject() {
		dataLoader.getEmptyActivityTable(this);
	}

	public ArrayList<Activity> getActivityListForCurrentProject() {
		final ArrayList<Activity> activityList = new ArrayList<Activity>();
		for (Activity activity : activities) {
			if (activity.getProjectID() == currentProject.getProjectID()) {
				activityList.add(activity);
			}
		}
		return activityList;
	}

	public ArrayList<String> getActivityNamesForCurrentProject() {
		ArrayList<String> activityList = new ArrayList<String>();
		for (Activity activity : activities) {
			if (activity.getProjectID() == currentProject.getProjectID()) {
				activityList.add(activity.getName());
			}
		}
		return activityList;
	}

	Activity getActivityFromID(int PID, int number) {
		for (Activity activity : activities)
			if (activity.getProjectID() == PID
					&& activity.getNumber() == number)
				return activity;
		return null;
	}

	Member createMember(Member member) {
		return dataUpdater.createMember(this, member);
	}

	boolean updateMember(Member member) {
		return dataUpdater.updateMember(this, member);
	}

	public boolean deleteMember(Member member) {
		return dataDeleter.deleteMember(this, member);
	}

	void initializeProject(String[] newProjectArgs) {
		boolean projectNameAlreadyExists = false;

		for (Project project : projects) {
			if (project.getManagerID() == currentUser.getMemberID()
					&& project.getName().equalsIgnoreCase(newProjectArgs[0])) {
				projectNameAlreadyExists = true;
				break;
			}
		}

		if (projectNameAlreadyExists) {
			ec.showError("Project with same name already exists");
		} else {
			Project newProject = new Project(currentUser.getMemberID(),
					newProjectArgs[0], newProjectArgs[1], new Date(
							newProjectArgs[2]), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

			if (dataUpdater.initializeProject(this, newProject)) {
				if (updateProject(newProject)) {
					currentProject = newProject;
					projects.add(currentProject);
				DisplayController.get().notifyChange(
						PModelChange.CREATED_PROJECT);
				}
			} else {
				ec.showError("Unable to create new project");
			}
		}
	}

	void initializeActivity(String activityName, String activityDescription,
			double[] activityArgs, ArrayList<String> dependentActivityNames) {

		if (getActivityNamesForCurrentProject().contains(activityName)) {
			ec.showError("An activity with that name already exists in project");
		} else {
			Activity newActivity = new Activity(currentProject.getProjectID(),
					activityName, activityDescription, activityArgs[0], // plannedValue
					activityArgs[1], // mostLikely
					activityArgs[2], // optimistic
					activityArgs[3], // pessimistic
					0, // targetCompletionDate
					0, // percentComplete
					0, // actualCost
					false); // isComplete

			if (newActivity.getMostLikelyTimeToCompletion() == 0 &&
					newActivity.getOptimisticTimeToCompletion() == 0 &&
					newActivity.getPessimisticTimeToCompletion() == 0) {
				return;
			}

			dataUpdater.createActivity(this, newActivity);

			if (!dependentActivityNames.isEmpty()) {
				ArrayList<Activity> dependentActivities = new ArrayList<Activity>();
				for (String name : dependentActivityNames) {
					for (Activity activity : getActivityListForCurrentProject()) {
						if (activity.getName().equals(name)) {
							dependentActivities.add(activity);
						}
					}
				}
				createActivityDependencies(newActivity, dependentActivities);
			}

			activities.add(newActivity);
			DisplayController.get().notifyChange(PModelChange.CREATED_ACTIVITY);
		}
	}

	private boolean updateProject(Project project) {
		return dataUpdater.updateProject(this, project);
	}

	void deleteProject(String projectName) {
		int projectIdCounter = 0;
		for (Project project : projects) {
			if (project.getName() == projectName) {
				break;
			} else {
				projectIdCounter++;
			}
		}

		if (deleteProject(projects.get(projectIdCounter))) {
			notifyDisplayController(PModelChange.DELETED_PROJECT);
		} else {
			ec.showError("Unable to delete project");
		}
	}

	public boolean deleteProject(Project project) {
		return dataDeleter.deleteProject(this, project);
	}

	Activity createActivity(Activity activity) {
		return dataUpdater.createActivity(this, activity);
	}

	boolean updateActivity(Activity activity) {
		return dataUpdater.updateActivity(this, activity);
	}

	boolean deleteActivity(int PID, int number) {
		return dataDeleter.deleteActivity(this, PID, number);
	}

	void initializeMemberActivity(String memberToAdd, int activityNumber) {
		int memberId = -1;
		for (Member member : members) {
			if (member.getName().equals(memberToAdd)) {
				memberId = member.getMemberID();
				break;
			}
		}

		for (MemberActivity theMemberActivity : memberActivities) {
			if (theMemberActivity.getMemberID() == memberId
					&& theMemberActivity.getNumber() == activityNumber) {
				ec.showError(memberToAdd
						+ " is already assigned to that activity");
				return;
			}
		}

		memberActivities.add(dataUpdater.initializeMemberActivity(this,
				new MemberActivity(memberId, currentProject.getProjectID(),
						activityNumber)));
	}

	public boolean createActivityDependencies(Activity activity,
			ArrayList<Activity> activities) {
		return dataUpdater.createActivityDependencies(this, activity,
				activities);
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

	public void updateActualCost(int activityNumber, double actualCost) {
		Activity activityToUpdate = getActivityFromID(currentProject.getProjectID(), activityNumber);
		activityToUpdate.setActualCost(actualCost);
		dataUpdater.updateActivity(this, activityToUpdate);
		loadFormatedActivityListForCurrentProject();
	}

	public void updatePercentComplete(int activityNumber, double percentComplete) {
		Activity activityToUpdate = getActivityFromID(currentProject.getProjectID(), activityNumber);
		activityToUpdate.setPercentComplete(percentComplete);
		dataUpdater.updateActivity(this, activityToUpdate);
		loadFormatedActivityListForCurrentProject();
	}

	public ArrayList<Integer> getRelatedActivities(Activity a, String type) {
		return dataLoader.getRelatedActivities(this, a, type);
	}

	public Member getCurrentUser() {
		return currentUser;
	}

	Project getCurrentProject() {
		return currentProject;
	}

	boolean hasProjectOpen() {
		return currentProject != null;
	}

	public void closeCurrentProject() {
		currentProject = null;
	}

	void notifyDisplayController(PModelChange updateType) {
		DisplayController.get().notifyChange(updateType);
	}
}