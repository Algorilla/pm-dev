package Controller;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;

import Analysis.Analyzer;
import Analysis.GanttNetwork;
import Analysis.PertNetwork;
import JDialogue.AddTeamMember;
import JDialogue.AddTeamMemberClone;
import JDialogue.CreateNewActivityDialog;
import JDialogue.CreateNewProjectDialog;
import JDialogue.CreateNewProjectDialogClone;
import JDialogue.DeleteProjectDialog;
import JDialogue.DeleteProjectDialogClone;
import JDialogue.EarnedValueDisplay;
import JDialogue.GanttDisplay;
import JDialogue.OpenProjectListDialog;
import JDialogue.OpenProjectListDialogClone;
//import JDialogue.PertDisplay;
import JDialogue.PertDisplayClone;
import JFrames.LoginFrameClone;
import JFrames.TeamMemberView;
import JFrames.TeamMemberViewClone;
import JFrames.UserInterfaceClone;
import PModel.Activity;
import PModel.Manageable;
import PModel.Member;
import PModel.MemberActivity;
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
	private JTable activitesTable;

	private LoginFrameClone loginFrame;
	private UserInterfaceClone userInterface;
	private TeamMemberViewClone teamMemberView;

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
				userInterface = new UserInterfaceClone();
				userInterface.setVisible(true);
				userInterface.setUserName(mc.getCurrentUser().getName());
				userInterface.setProjectName("Please select a project");
			} else {
				teamMemberView = new TeamMemberViewClone();
				teamMemberView.setVisible(true);
			}
			loginFrame.setVisible(false);
		} else {
			ec.showError("Invalid log in");
		}
	}

	public void createNewProject(JTable activitiesTable) {
		new CreateNewProjectDialogClone(mc.getCurrentUser().getMemberID());

		if (newProject != null) {
			mc.initializeProject(newProject);
		}

		if (isProjectCreated) {
			currentProject = mc.getCurrentProject();
			userInterface.setProjectName(currentProject.getName());

			currentActivity = null;
			userInterface.resetActivity(false);

			this.activitesTable = activitiesTable;
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
			userInterface.setProjectName(currentProject.getName());

			currentActivity = null;
			userInterface.resetActivity(false);

			this.activitesTable = activitiesTable;
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
				userInterface.setProjectName("Please select a project");
				userInterface.resetActivity(true);
			}

			isProjectDeleted = false;
		}

		projectToDelete = null;
	}

	public void exit() {
		System.exit(0);
	}

	public void updatePercentComplete(Double percentComplete) {
		// TODO: figure out conversion
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
			new AddTeamMemberClone(currentActivity);
		}
	}

	public void selectActivity(int PID, int activityNumber, boolean isManager) {
		if (isManager
				&& isManageableNull(currentProject, "Please select a project")) {
			return;
		} else if (isManager) {
			currentActivity = mc.getActivityFromID(PID, activityNumber);
			userInterface.setActivityName(currentActivity.getName());
			userInterface.setActivityDescription(currentActivity.getDescr());
			userInterface.setPercentComplete(currentActivity
					.getPercentComplete());
			userInterface.setActualCost(currentActivity.getActualCost());
		} else if (!isManager) {
			currentActivity = mc.getActivityFromID(PID, activityNumber);
			teamMemberView.setDescription(currentActivity.getDescr());
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

	public void setProjectToOpen(String projectName) {
		projectToOpen = projectName;
	}

	public void setProjectToDelete(String projectName) {
		projectToDelete = projectName;
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

	// TODO
	public void getActivityListTeamMember(JTable activityTable) {
		mc.getActivityListForCurrentTeamMember(activityTable);
	}

	public ArrayList<Member> getMemberListForAddMemberToActivity() {
		return mc.members;
	}

	public MemberActivity initializeMemberActivity(MemberActivity ma) {
		return mc.initializeMemberActivity(ma);
	}
}