package Controller;

import java.util.Date;

import javax.swing.JTable;

import Analysis.Analyzer;
import Analysis.GanttNetwork;
import Analysis.PertNetwork;
import JDialogue.AddTeamMember;
import JDialogue.CreateNewActivityDialog;
import JDialogue.CreateNewProjectDialog;
import JDialogue.DeleteProjectDialog;
import JDialogue.EarnedValueDisplay;
import JDialogue.GanttDisplay;
import JDialogue.OpenProjectListDialog;
import JDialogue.PertDisplay;
import JFrames.LoginFrameClone;
import JFrames.UserInterfaceClone;
import PModel.Activity;
import PModel.Manageable;
import PModel.Project;

public class DisplayController {
	private static DisplayController self = null;

	private MainController mc = MainController.get();
	private Project currentProject = null;
	private Activity currentActivity = null;

	private LoginFrameClone loginFrame;
	private UserInterfaceClone userInterface;

	private boolean isProjectCreated;
	private boolean isProjectOpen;
	private boolean isProjectDeleted;
	private boolean isActivityCreated;

	private String deletedProjectName;

	private DisplayController() {
		loginFrame = new LoginFrameClone();
		loginFrame.setVisible(true);
	}

	public synchronized static DisplayController get() {
		if (self == null) {
			self = new DisplayController();
		}
		return self;
	}

	// TODO: ATTENTION: all of the ErrorController message assume that ErrorController is refactored
	// TODO: DEV refactor all JDialogs so that they set own visibility to true and notify 
	// DisplayController once PModel elements actually change, when needed
	// TODO: TEST can you guys give us all of the error-handling to be done in the different
	// JDialogs? For example, to create a project, a data must be selected
	// We will implement these once we refactor the JDialogs
	public void login(String username, String password) {
		if (username.isEmpty()) {
			// TODO: ErrorController display pop-up: username cannot be blank
			return;
		} else if (password.isEmpty()) {
			// TODO: ErrorController display pop-up: password cannot be blank
			return;
		} else {
			if (mc.login(username, password)) {
				if (userInterface == null) {
					userInterface = new UserInterfaceClone();
				}

				// TODO: DEV check whether user is manager or team-member
				// TODO: DEV refactor TeamMemberView similarly to UserInterfaceClone
				// TODO: the TeamMemeberView doesn't work as of now
				userInterface.setVisible(true);
				userInterface.setUserName(mc.getCurrentUser().getName());
				userInterface.setProjectName("Please select a project");
				loginFrame.setVisible(false);
			} else {
				// TODO: ErrorController display pop-up invalid log in
			}
		}
	}

	public void createNewProject(JTable activitiesTable) {
		// TODO: DEV refactor CreateNewProjectDialog
		CreateNewProjectDialog newProjectDialog = new CreateNewProjectDialog();
		// TODO: DEV CreateNewProjectDialog should set its visibility to true
		// TODO: TEST add error handling to ensure no other identical project exists
		// in newProjectDialog
		newProjectDialog.setVisible(true);

		if (isProjectCreated) {
			currentProject = mc.getCurrentProject();
			currentActivity = null;
			mc.getActivitiesListForCurrentProject(activitiesTable);
			userInterface.setProjectName(currentProject.getName());
			userInterface.resetActivityNameAndDescription(false);
			isProjectCreated = false;
		}
	}

	public void openProject(JTable activitiesTable) {
		// TODO: DEV refactor openProjectListDialog
		OpenProjectListDialog openProjectDialog = new OpenProjectListDialog();
		// TODO: DEV OpenProjectDialog should set its visibility to true
		openProjectDialog.setVisible(true);
		if (isProjectOpen) {
			currentProject = mc.getCurrentProject();
			currentActivity = null;
			mc.getActivitiesListForCurrentProject(activitiesTable);
			userInterface.setProjectName(currentProject.getName());
			userInterface.resetActivityNameAndDescription(false);
			isProjectOpen = false;
		}
	}

	public boolean deleteProject() {
		boolean projectDeletedIsCurrentProject = false;
		String currentProjectName = currentProject == null ? ""
				: currentProject.getName();

		DeleteProjectDialog deleteProjectDialog = new DeleteProjectDialog();
		deleteProjectDialog.setVisible(true);
		if (isProjectDeleted) {
			if (!currentProjectName.isEmpty()
					&& currentProjectName.equalsIgnoreCase(deletedProjectName)) {
				currentProject = null;
				userInterface.setProjectName("Please select a project");
				userInterface.resetActivityNameAndDescription(true);
				projectDeletedIsCurrentProject = true;
				// TODO: DEV/TEST SOMEONE FIGURE OUT HOW TO FREAKING UPDATE THE ACTIVITY
				// TABLE!!!
				// TODO: also update mntmDelete
			}

			deletedProjectName = null;
			isProjectDeleted = false;
		}
		return projectDeletedIsCurrentProject;
	}

	public void exit() {
		System.exit(0);
	}

	public boolean updatePercentComplete(Double percentComplete) {
		boolean ret = false;
		if (checkNotNull(currentProject, "Please select a project")) {

		} else if (checkNotNull(currentActivity, "Please select an activity")) {

		} else if (percentComplete < 0 || percentComplete > 100) {
			// TODO: ErrorController display pop-up please enter valid values
		} else {
			currentActivity.setPercentComplete(percentComplete);
			ret = true;
		}
		return ret;
	}

	public boolean updateActualCost(Double actualCost) {
		boolean ret = false;
		if (checkNotNull(currentProject, "Please select a project")) {

		} else if (checkNotNull(currentActivity, "Please select an activity")) {

		} else if (actualCost < 0) {
			// TODO: ErrorController display pop-up please enter valid values
		} else {
			currentActivity.setActualCost(actualCost);
			ret = true;
		}
		return ret;
	}

	// Analysis logic
	// TODO: DEV refactor GANTT, PERT, EARNED-VALUE?
	public void createGantt() {
		if (checkNotNull(currentProject, "Please select a project")) {
			return;
		} else {
			Analyzer a = new Analyzer(currentProject, 161);
			String projectName = currentProject.getName();
			GanttNetwork gn = a.getGanttNetwork();
			new GanttDisplay(projectName, gn);
		}
	}

	public void createPert() {
		if (checkNotNull(currentProject, "Please select a project")) {
			return;
		} else {
			Date today = new Date();
			Date start = currentProject.getStartDate();
			int daysSinceStart = daysBetween(today, start);
			Analyzer a = new Analyzer(currentProject, daysSinceStart);
			PertNetwork p = a.getPertNetwork();
			new PertDisplay(p);
		}
	}

	public void createEVA() {
		// TODO: DEV DEBUG WHY FREEZE ON WINDOWS
		if (checkNotNull(currentProject, "Please select a project")) {
			return;
		} else {
			Date today = new Date();
			Date start = currentProject.getStartDate();
			int daysSinceStart = daysBetween(today, start);
			Analyzer a = new Analyzer(currentProject, Math.abs(daysSinceStart));
			new EarnedValueDisplay(currentProject);
		}
	}

	public void save(String activityName, String description) {
		if (checkNotNull(currentProject, "Please select a project")) {
			return;
		} else if (checkNotNull(currentActivity,
				"Please create/select an activity")) {
			return;
		} else if (activityName.isEmpty()) {
			// TODO: ErrorController display pop-up activity name cannot be
			// blank
		} else if (description.isEmpty()) {
			// TODO: ErrorController display pop-up description cannot be blank
		} else {
			currentActivity.setName(activityName);
			currentActivity.setDescr(description);

			if (mc.updateActivity(currentActivity)) {
				// TODO: DEV should we display a "successfully updated" pop-up?
				// VALIDATE THAT THIS ACTUALLY WORKS
			} else {
				// TODO: ErrorController display pop-up update failed
			}
		}
	}

	public void addTeamMember() {
		if (checkNotNull(currentProject, "Please select a project")) {
			return;
		} else if (checkNotNull(currentActivity, "Please select an activity")) {
			return;
		} else {
			new AddTeamMember(currentActivity);
		}
	}

	public boolean selectActivity(int PID, int activityNumber) {
		if (checkNotNull(currentProject, "Please select a project")) {
			return false;
		} else {
			currentActivity = mc.getActivityFromID(PID, activityNumber);
			// TODO: is there a chance that this might fail???
		}
		return currentActivity != null;
	}

	public void createNewActivity(JTable activitiesTable) {
		if (checkNotNull(currentProject, "Please select a project")) {
			return;
		} else {
			CreateNewActivityDialog newActivity = new CreateNewActivityDialog();
			// TODO: createNewActivtiy should set its visibility to true
			newActivity.setVisible(true);
			if (isActivityCreated) {
				mc.getActivitiesListForCurrentProject(activitiesTable);
				isActivityCreated = false;
				// TODO: ensure that ActivitiesTable gets updated
			}
		}
	}

	public void deleteActivity(int PID, int activityNumber,
			JTable activitiesTable) {
		if (checkNotNull(currentProject, "Please select a project")) {
			return;
		} else if (checkNotNull(currentActivity, "Please select an activity")) {
			return;
		} else {
			if (mc.deleteActivity(PID, activityNumber)) {
				mc.getActivitiesListForCurrentProject(activitiesTable);
				// TODO: SOMEONE FIGURE OUT HOW TO FREAKING UPDATE THE ACTIVITY
				// TABLE!!!
				// TODO: also update btnDeleteActivity
			}
		}
	}

	private int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	// Package access so that only MainController can call this method, not the
	// JDialogs
	void notifyChange(PModelChange type) {
		switch (type) {
		case CREATED_PROJECT:
			isProjectCreated = true;
			break;
		case OPENED_PROJECT:
			isProjectOpen = true;
			break;
		case DELETED_PROJECT:
			isProjectDeleted = true;
			break;
		case CREATED_ACTIVITY:
			isActivityCreated = true;
			break;
		}
	}

	public void setDeletedProjectName(String name) {
		deletedProjectName = name;
	}

	private boolean checkNotNull(Manageable manageable, String message) {
		boolean ret = false;
		if (manageable == null) {
			ret = true;
			// TODO: ErrorController should display a pop-up with this message
		}
		return ret;
	}
}