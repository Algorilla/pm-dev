package Controller;

import java.util.Date;

import javax.swing.JTable;

import Analysis.Analyzer;
import Analysis.GanttNetwork;
import Analysis.PertNetwork;
import JDialogue.AddTeamMemberClone;
import JDialogue.CreateNewActivityDialog;
import JDialogue.CreateNewProjectDialogClone;
import JDialogue.DeleteProjectDialogClone;
import JDialogue.EarnedValueDisplay;
import JDialogue.GanttDisplay;
import JDialogue.OpenProjectListDialogClone;
import JDialogue.PertDisplayClone;
import JFrames.UserInterfaceView;
import JFrames.LoginFrameClone;
import JFrames.TeamMemberViewClone;
import JFrames.ManagerView;
import PModel.Activity;
import PModel.Manageable;
import PModel.Project;

public class DisplayController {
	private static DisplayController self = null;

	private MainController mc = MainController.get();
	private ErrorController ec = ErrorController.get();

	private Project currentProject;
	private Activity currentActivity;

	private Project newProject;
	private String projectToOpen;
	private String projectToDelete;
	private JTable activitiesTable;

	private LoginFrameClone loginFrame;
	private UserInterfaceView userInterface;

	// flags that MainController sets
	private boolean isProjectCreated;
	private boolean isProjectOpen;
	private boolean isProjectDeleted;
	private boolean isActivityCreated;

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

	public void login(String username, String password) {
		if (mc.login(username, password)) {
			if (mc.currentUser.getType().equals("manager")) {
				userInterface = new ManagerView();
				((ManagerView) userInterface).setUserName(mc.getCurrentUser()
						.getName());
				((ManagerView) userInterface)
						.setProjectName("Please select a project");
			} else {
				activitiesTable = new JTable();
				userInterface = new TeamMemberViewClone(activitiesTable);
			}
			userInterface.setVisible(true);
			loginFrame.setVisible(false);
		} else {
			ec.showError("Incorrect username and/or password");
		}
	}

	public void createNewProject(JTable activitiesTable) {
		new CreateNewProjectDialogClone(mc.getCurrentUser().getMemberID());

		if (newProject != null) {
			mc.initializeProject(newProject);
		}

		if (isProjectCreated) {
			currentProject = mc.getCurrentProject();
			((ManagerView) userInterface).setProjectName(currentProject
					.getName());

			currentActivity = null;
			((ManagerView) userInterface).resetActivity(false);

			this.activitiesTable = activitiesTable;
			mc.getActivitiesListForCurrentProject();
			isProjectCreated = false;
		}

		newProject = null;
	}

	public void openProject(JTable activitiesTable) {
		new OpenProjectListDialogClone(mc.getProjectList());

		if (projectToOpen != null) {
			mc.openProject(projectToOpen);
		}

		if (isProjectOpen) {
			currentProject = mc.getCurrentProject();
			((ManagerView) userInterface).setProjectName(currentProject
					.getName());

			currentActivity = null;
			((ManagerView) userInterface).resetActivity(false);

			this.activitiesTable = activitiesTable;
			mc.getActivitiesListForCurrentProject();

			isProjectOpen = false;
		}

		projectToOpen = null;
	}

	public void deleteProject() {
		String currentProjectName = currentProject == null ? ""
				: currentProject.getName();

		new DeleteProjectDialogClone(mc.getProjectList());

		if (projectToDelete != null) {
			mc.deleteProject(projectToDelete);
		}

		if (isProjectDeleted) {
			if (!currentProjectName.isEmpty()
					&& currentProjectName.equalsIgnoreCase(projectToDelete)) {
				currentProject = null;
				((ManagerView) userInterface)
						.setProjectName("Please select a project");
				((ManagerView) userInterface).resetActivity(true);
				mc.emptyActivitiesForDeletedProject();
			}

			isProjectDeleted = false;
		}

		projectToDelete = null;
	}

	public void logout() {
		currentProject = null;
		currentActivity = null;
		activitiesTable = null;
		mc.logout();
		userInterface.dispose();
		loginFrame.setVisible(true);
	}

	public void updatePercentComplete(Double percentComplete) {
		if (isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManageableNull(currentActivity,
				"Please select an activity")) {
			return;
		} else if (percentComplete < 0 || percentComplete > 1) {
			ec.showError("Please enter values between 0.0 to 1.0");
			return;
		} else {
			currentActivity.setPercentComplete(percentComplete);
			((ManagerView) userInterface).setPercentComplete(percentComplete);
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
			((ManagerView) userInterface).setActualCost(actualCost);
		}
	}

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
			new AddTeamMemberClone(currentActivity);
		}
	}

	public void selectActivity(int PID, int activityNumber, boolean isManager) {
		if (isManager
				&& isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManager) {
			currentActivity = mc.getActivityFromID(PID, activityNumber);
			((ManagerView) userInterface).setActivityName(currentActivity
					.getName());
			((ManagerView) userInterface)
					.setActivityDescription(currentActivity.getDescr());
			((ManagerView) userInterface).setPercentComplete(currentActivity
					.getPercentComplete());
			((ManagerView) userInterface).setActualCost(currentActivity
					.getActualCost());
		} else if (!isManager) {
			currentActivity = mc.getActivityFromID(PID, activityNumber);
			((TeamMemberViewClone) userInterface)
					.setDescription(currentActivity.getDescr());
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
				((ManagerView) userInterface).resetActivity(false);
			}
		}
	}

	// Package access so that only MainController can call this method
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
		return activitiesTable;
	}

	public void setNewProject(Project newProject) {
		this.newProject = newProject;
	}

	public void setProjectToOpen(String projectName) {
		projectToOpen = projectName;
	}

	public void setProjectToDelete(String projectName) {
		projectToDelete = projectName;
	}

	private boolean isManageableNull(Manageable manageable, String message) {
		boolean ret = false;
		if (manageable == null) {
			ret = true;
			ec.showError(message);
		}
		return ret;
	}

	private int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}