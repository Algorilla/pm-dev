package Controller;

import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JTable;

import Analysis.Analyzer;
import Analysis.GanttNetwork;
import Analysis.PertNetwork;
import JDialogue.AddTeamMemberClone;
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
import PModel.Manageable;
import PModel.Member;

public class DisplayController {
	private static DisplayController self = null;

	private MainController mc = MainController.get();
	private ErrorController ec = ErrorController.get();

	private String[] newProjectArgs;
	private String projectToOpen;
	private String projectToDelete;
	private Integer activityNumber;
	private Integer memberIdToAdd;
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
			if (mc.getCurrentUser().getType().equals("manager")) {
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
		new CreateNewProjectDialogClone();

		if (newProjectArgs != null) {
			mc.initializeProject(newProjectArgs);
		}

		if (isProjectCreated) {
			((ManagerView) userInterface).setProjectName(mc.getCurrentProject()
					.getName());

			((ManagerView) userInterface).resetActivity(false);

			this.activitiesTable = activitiesTable;
			mc.getActivitiesListForCurrentProject();
			isProjectCreated = false;
		}

		newProjectArgs = null;
	}

	public void openProject(JTable activitiesTable) {
		new OpenProjectListDialogClone(mc.getProjectListForCurrentManager());

		if (projectToOpen != null) {
			mc.openProject(projectToOpen);
		}

		if (isProjectOpen) {
			((ManagerView) userInterface).setProjectName(mc.getCurrentProject()
					.getName());

			((ManagerView) userInterface).resetActivity(false);

			this.activitiesTable = activitiesTable;
			mc.getActivitiesListForCurrentProject();

			isProjectOpen = false;
		}

		projectToOpen = null;
	}

	public void deleteProject() {
		String currentProjectName = mc.getCurrentProject() == null ? "" : mc
				.getCurrentProject().getName();

		new DeleteProjectDialogClone(mc.getProjectListForCurrentManager());

		if (projectToDelete != null) {
			mc.deleteProject(projectToDelete);
		}

		if (isProjectDeleted) {
			if (!currentProjectName.isEmpty()
					&& currentProjectName.equalsIgnoreCase(projectToDelete)) {
				mc.closeCurrentProject();
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
		// TODO : add save functionality
		activitiesTable = null;
		activityNumber = 0;
		mc.logout();
		userInterface.dispose();
		loginFrame.setVisible(true);
	}

	public void updatePercentComplete(Double percentComplete) {
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else if (activityNumber == null) {
			ec.showError("Please select an activity");
		} else if (percentComplete < 0 || percentComplete > 1) {
			ec.showError("Please enter values between 0.0 to 1.0");
			return;
		} else {
			mc.getActivityFromID(mc.getCurrentProject().getProjectID(),
					activityNumber).setPercentComplete(percentComplete);
			((ManagerView) userInterface).setPercentComplete(percentComplete);
		}
	}

	public void updateActualCost(Double actualCost) {
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else if (activityNumber == null) {
			ec.showError("Please select an activity");
		} else if (actualCost < 0) {
			ec.showError("Please enter a non-negative value");
		} else {
			mc.getActivityFromID(mc.getCurrentProject().getProjectID(),
					activityNumber).setActualCost(actualCost);
			((ManagerView) userInterface).setActualCost(actualCost);
		}
	}

	// TODO: DEV refactor GANTT, PERT, EARNED-VALUE?
	public void createGantt() {
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else {
			Analyzer a = new Analyzer(mc.getCurrentProject(), 161);
			String projectName = mc.getCurrentProject().getName();
			GanttNetwork gn = a.getGanttNetwork();
			new GanttDisplay(projectName, gn);
		}
	}

	public void createPert() {
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else {
			Date today = new Date();
			Date start = mc.getCurrentProject().getStartDate();
			int daysSinceStart = daysBetween(today, start);
			Analyzer a = new Analyzer(mc.getCurrentProject(), daysSinceStart);
			PertNetwork p = a.getPertNetwork();
			new PertDisplayClone(p);
		}
	}

	public void createEVA() {
		// TODO: DEV DEBUG WHY FREEZE ON WINDOWS
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else {
			Date today = new Date();
			Date start = mc.getCurrentProject().getStartDate();
			int daysSinceStart = daysBetween(today, start);
			Analyzer a = new Analyzer(mc.getCurrentProject(),
					Math.abs(daysSinceStart));
			new EarnedValueDisplay(mc.getCurrentProject());
		}
	}

	public void save(String activityName, String description) {
		/*
		 * if (isManageableNull(mc.getCurrentProject(),
		 * "Please select a project")) { return; } else if
		 * (isManageableNull(mc., "Please create/select an activity")) { return;
		 * } else if (activityName.isEmpty()) {
		 * ec.showError("Activity name cannot be blank"); } else if
		 * (description.isEmpty()) {
		 * ec.showError("Description cannot be blank"); } else {
		 * currentActivity.setName(activityName);
		 * currentActivity.setDescr(description);
		 * 
		 * if (mc.updateActivity(currentActivity)) { // TODO: DEV should we
		 * display a "successfully updated" pop-up? // VALIDATE THAT THIS
		 * ACTUALLY WORKS } else { ec.showError("Update failed"); } }
		 */
	}

	public void addTeamMember() {
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else if (activityNumber == null) {
				ec.showError("Please select an activity");
		} else {
			JComboBox<Member> memberComboBox = new JComboBox<Member>();
			for (Member member : mc.getMemberListForAddMemberToActivity()) {
					memberComboBox.addItem(member);
			}
			new AddTeamMemberClone(memberComboBox);

			if (memberIdToAdd != null) {
				mc.initializeMemberActivity(new PModel.MemberActivity(
						memberIdToAdd,
						mc.getCurrentProject().getProjectID(),
						activityNumber));
				memberIdToAdd = null;
			}
		}
	}

	public void selectActivity(int PID, int activityNumber, boolean isManager) {
		if (isManager) {
			((ManagerView) userInterface).setActivityName(mc.getActivityFromID(
					PID, activityNumber).getName());
			((ManagerView) userInterface).setActivityDescription(mc
					.getActivityFromID(PID, activityNumber).getDescr());
			((ManagerView) userInterface).setPercentComplete(mc
					.getActivityFromID(PID, activityNumber)
					.getPercentComplete());
			((ManagerView) userInterface).setActualCost(mc.getActivityFromID(
					PID, activityNumber).getActualCost());
		} else if (!isManager) {
			((TeamMemberViewClone) userInterface).setDescription(mc
					.getActivityFromID(PID, activityNumber).getDescr());
		}
		this.activityNumber = activityNumber;
	}

	public void createNewActivity() {
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else {
			// CreateNewActivityDialog newActivity = new
			// CreateNewActivityDialog();
			// TODO: createNewActivtiy should set its visibility to true
			// newActivity.setVisible(true);
			if (isActivityCreated) {
				mc.getActivitiesListForCurrentProject();
				isActivityCreated = false;
				// TODO: ensure that ActivitiesTable gets updated
			}
		}
	}

	public void deleteActivity(int PID, int activityNumber) {
		if (mc.getCurrentProject() == null) {
			ec.showError("Please select a project");
		} else if (this.activityNumber == null) {
			ec.showError("Please select an activity");
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

	public void setNewProject(String[] newProject) {
		this.newProjectArgs = newProject;
	}

	public void setProjectToOpen(String projectName) {
		projectToOpen = projectName;
	}

	public void setProjectToDelete(String projectName) {
		projectToDelete = projectName;
	}

	public void setMemberIdToAdd(Integer memberId) {
		memberIdToAdd = memberId;
	}

	private int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}