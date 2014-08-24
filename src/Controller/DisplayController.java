package Controller;

import java.util.Date;

import javax.swing.JTable;

import Analysis.Analyzer;
import Analysis.GanttNetwork;
import Analysis.PertNetwork;
import JDialogue.AddTeamMember;
import JDialogue.CreateNewActivityDialog;
import JDialogue.CreateNewProjectDialog;
import JDialogue.CreateNewProjectDialogClone;
import JDialogue.DeleteProjectDialog;
import JDialogue.EarnedValueDisplay;
import JDialogue.GanttDisplay;
import JDialogue.OpenProjectListDialog;
//import JDialogue.PertDisplay;
import JDialogue.PertDisplayClone;
import JFrames.LoginFrameClone;
import JFrames.UserInterfaceClone;
import PModel.Activity;
import PModel.Manageable;
import PModel.Project;

public class DisplayController {
	private static DisplayController self = null;

	private MainController mc = MainController.get();
	private ErrorController ec = ErrorController.get();

	Project newProject = null;
	private Project currentProject = null;
	private Activity currentActivity = null;

	private LoginFrameClone loginFrame;
	private UserInterfaceClone userInterface;

	private boolean isProjectCreated;
	private boolean isProjectOpen;
	private boolean isProjectDeleted;
	private boolean isActivityCreated;

	private String deletedProjectName;

	JTable activitesTable;

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

	// DisplayController once PModel elements actually change, when needed
	// TODO: TEST can you guys give us all of the error-handling to be done in
	// the different
	// JDialogs? For example, to create a project, a data must be selected
	// We will implement these once we refactor the JDialogs
	public void login(String username, String password) {
		if (mc.login(username, password)) {
			if (userInterface == null) {
				userInterface = new UserInterfaceClone();
			}
			// TODO: DEV check whether user is manager or team-member
			// TODO: DEV refactor TeamMemberView similarly to UserInterfaceClone
			// TODO: the TeamMemeberView doesn't work as of now
			// TODO: FIGURE out how to switch between login and userInteface:
			// i.e. instead of exitin the whole program, how to reset login too
			// visible
			userInterface.setVisible(true);
			userInterface.setUserName(mc.getCurrentUser().getName());
			userInterface.setProjectName("Please select a project");
			loginFrame.setVisible(false);
		} else {
			ec.showError("Invalid log in");
		}
	}

	public void createNewProject(JTable activitiesTable) {
		new CreateNewProjectDialogClone();

		if (newProject != null) {
			mc.initializeProject(newProject);
		}

		if (isProjectCreated) {
			currentProject = mc.getCurrentProject();
			currentActivity = null;
			this.activitesTable = activitiesTable;
			mc.getActivitiesListForCurrentProject();
			userInterface.setProjectName(currentProject.getName());
			userInterface.resetActivity(false);
			isProjectCreated = false;
		}

		newProject = null;
	}

	public void openProject(JTable activitiesTable) {
		// TODO: DEV refactor openProjectListDialog
		OpenProjectListDialog openProjectDialog = new OpenProjectListDialog();
		// TODO: DEV OpenProjectDialog should set its visibility to true
		openProjectDialog.setVisible(true);
		if (isProjectOpen) {
			currentProject = mc.getCurrentProject();
			currentActivity = null;
			this.activitesTable = activitiesTable;
			mc.getActivitiesListForCurrentProject();
			userInterface.setProjectName(currentProject.getName());
			userInterface.resetActivity(false);
			isProjectOpen = false;
		}
	}

	public void deleteProject() {
		String currentProjectName = currentProject == null ? ""
				: currentProject.getName();

		DeleteProjectDialog deleteProjectDialog = new DeleteProjectDialog();
		deleteProjectDialog.setVisible(true);
		if (isProjectDeleted) {
			if (!currentProjectName.isEmpty()
					&& currentProjectName.equalsIgnoreCase(deletedProjectName)) {
				currentProject = null;
				userInterface.setProjectName("Please select a project");
				userInterface.resetActivity(true);
			}

			deletedProjectName = null;
			isProjectDeleted = false;
		}
	}

	public void exit() {
		System.exit(0);
	}

	public void updatePercentComplete(Double percentComplete) {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManageableNull(currentActivity,
				"Please select an activity")) {
			return;
		} else if (percentComplete < 0 || percentComplete > 100) {
			ec.showError("Please enter valid values");
			return;
		} else {
			currentActivity.setPercentComplete(percentComplete);
			userInterface.setPercentComplete(percentComplete);
		}
	}

	public void updateActualCost(Double actualCost) {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManageableNull(currentActivity,
				"Please select an activity")) {
			return;
		} else if (actualCost < 0) {
			ec.showError("Please enter a non-negative value");
			return;
		} else {
			currentActivity.setActualCost(actualCost);
			userInterface.setActualCost(actualCost);
		}
	}

	// Analysis logic
	// TODO: DEV refactor GANTT, PERT, EARNED-VALUE?
	public void createGantt() {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else {
			Analyzer a = new Analyzer(currentProject, 161);
			String projectName = currentProject.getName();
			GanttNetwork gn = a.getGanttNetwork();
			new GanttDisplay(projectName, gn);
		}
	}

	public void createPert() {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else {
			Date today = new Date();
			Date start = currentProject.getStartDate();
			int daysSinceStart = daysBetween(today, start);
			Analyzer a = new Analyzer(currentProject, daysSinceStart);
			PertNetwork p = a.getPertNetwork();
			new PertDisplayClone(p);
		}
	}

	public void createEVA() {
		// TODO: DEV DEBUG WHY FREEZE ON WINDOWS
		if (isManageableNull(currentProject, "Please select a project")) {
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
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManageableNull(currentActivity,
				"Please create/select an activity")) {
			return;
		} else if (activityName.isEmpty()) {
			ec.showError("Activity name cannot be blank");
		} else if (description.isEmpty()) {
			ec.showError("Description cannot be blank");
		} else {
			currentActivity.setName(activityName);
			currentActivity.setDescr(description);

			if (mc.updateActivity(currentActivity)) {
				// TODO: DEV should we display a "successfully updated" pop-up?
				// VALIDATE THAT THIS ACTUALLY WORKS
			} else {
				ec.showError("Update failed");
			}
		}
	}

	public void addTeamMember() {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManageableNull(currentActivity,
				"Please select an activity")) {
			return;
		} else {
			new AddTeamMember(currentActivity);
		}
	}

	public void selectActivity(int PID, int activityNumber) {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else {
			currentActivity = mc.getActivityFromID(PID, activityNumber);
			userInterface.setActivityName(currentActivity.getName());
			userInterface.setActivityDescription(currentActivity.getDescr());
			userInterface.setPercentComplete(currentActivity
					.getPercentComplete());
			userInterface.setActualCost(currentActivity.getActualCost());
		}
	}

	public void createNewActivity() {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else {
			CreateNewActivityDialog newActivity = new CreateNewActivityDialog();
			// TODO: createNewActivtiy should set its visibility to true
			newActivity.setVisible(true);
			if (isActivityCreated) {
				mc.getActivitiesListForCurrentProject();
				isActivityCreated = false;
				// TODO: ensure that ActivitiesTable gets updated
			}
		}
	}

	public void deleteActivity(int PID, int activityNumber) {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManageableNull(currentActivity,
				"Please select an activity")) {
			return;
		} else {
			if (mc.deleteActivity(PID, activityNumber)) {
				mc.getActivitiesListForCurrentProject();
				userInterface.resetActivity(false);
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

	// package access so that only MainController can call this method
	JTable getActivityTable() {
		return activitesTable;
	}

	public void setDeletedProjectName(String name) {
		deletedProjectName = name;
	}

	public void setNewProject(Project newProject) {
		this.newProject = newProject;
	}

	private boolean isManageableNull(Manageable manageable, String message) {
		boolean ret = false;
		if (manageable == null) {
			ret = true;
			ec.showError(message);
		}
		return ret;
	}
}