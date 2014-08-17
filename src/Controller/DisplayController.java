package Controller;

import java.util.Date;

import javax.swing.JTable;

import Analysis.Analyzer;
import Analysis.GanttNetwork;
import Analysis.PertNetwork;
import JDialogue.AddTeamMember;
import JDialogue.CreateNewProjectDialog;
import JDialogue.DeleteProjectDialog;
import JDialogue.EarnedValueDisplay;
import JDialogue.GanttDisplay;
import JDialogue.OpenProjectListDialog;
import JDialogue.PertDisplay;
import JFrames.LoginFrameClone;
import JFrames.UserInterfaceClone;
import PModel.Activity;
import PModel.Project;

public class DisplayController {
	private static DisplayController self = null;

	private MainController mc = MainController.get();
	private Project currentProject = null;
	private Activity currentActivity = null;

	private LoginFrameClone loginFrame;
	private UserInterfaceClone userInterface;

	private DisplayController() {
		loginFrame = new LoginFrameClone();
		loginFrame.setVisible(true);
	}

	// TODO: synchronize other Controllers
	public synchronized static DisplayController get() {
		if (self == null) {
			self = new DisplayController();
		}
		return self;
	}

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

				// TODO: check whether user is manager or team-member
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
		// TODO: refactor CreateNewProjectDialog ???
		CreateNewProjectDialog newProjectDialog = new CreateNewProjectDialog();
		newProjectDialog.setVisible(true);
		currentProject = mc.getCurrentProject();
		mc.getActivitiesListForCurrentProject(activitiesTable);
		userInterface.setProjectName(currentProject.getName());
	}

	public void openProject(JTable activitiesTable) {
		// TODO: refactor openProjectListDialog ???
		OpenProjectListDialog openProjectDialog = new OpenProjectListDialog();
		openProjectDialog.setVisible(true);
		// TODO: ensure project is selected
		currentProject = mc.getCurrentProject();
		mc.getActivitiesListForCurrentProject(activitiesTable);
		userInterface.setProjectName(currentProject.getName());
	}

	public void deleteProject() {
		// TODO: refactor delete project ???
		DeleteProjectDialog deleteProjectDialog = new DeleteProjectDialog();
		deleteProjectDialog.setVisible(true);
		// TODO: ensure project is selected
	}

	public void exit() {
		System.exit(0);
	}

	public boolean updatePercentComplete(Double percentComplete) {
		boolean ret = false;
		if (percentComplete < 0 || percentComplete > 100) {
			//TODO: ErrorController display pop-up please enter valid values
		} else {
			currentActivity.setPercentComplete(percentComplete);
			ret = true;
		}
		return ret;
	}
	
	public boolean updateActualCost(Double actualCost) {
		boolean ret = false;
		if (actualCost < 0) {
			//TODO: ErrorController display pop-up please enter valid values
		} else {
			currentActivity.setActualCost(actualCost);
			ret = true;
		}
		return ret;
	}
	
	// Analysis logic
	public void createGantt() {
		if (currentProject == null) {
			// TODO: ErrorController display pop-up please select project
		} else {
			Analyzer a = new Analyzer(currentProject, 161);
			String projectName = currentProject.getName();
			GanttNetwork gn = a.getGanttNetwork();
			new GanttDisplay(projectName, gn);
		}
	}

	public void createPert() {
		if (currentProject == null) {
			// TODO: ErrorController display pop-up please select project
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
		if (currentProject == null) {
			// TODO: ErrorController display pop-up please select project
		} else {
			Date today = new Date();
			Date start = currentProject.getStartDate();
			int daysSinceStart = daysBetween(today, start);
			Analyzer a = new Analyzer(currentProject, Math.abs(daysSinceStart));
			new EarnedValueDisplay(currentProject);
		}
	}

	public void save(String activityName, String description) {
		if (currentProject == null) {
			// TODO: ErrorController display pop-up please select project
		} else if (currentActivity == null) {
			// TOD: ErrorController display pop-up activity cannot be null
		} else if (activityName.isEmpty()) {
			// TODO: ErrorController display pop-up activity name cannot be
			// blank
		} else if (description.isEmpty()) {
			// TODO: ErrorController display pop-up description cannot be blank
		} else {
			currentActivity.setName(activityName);
			currentActivity.setDescr(description);

			if (mc.updateActivity(currentActivity)) {
				// TODO: display updated???
			} else {
				// TODO: ErrorController display pop-up update failed
			}
		}
	}

	public void addTeamMember() {
		if (currentProject == null) {
			// TODO: display pop-up please select project
		} else if (currentActivity == null) {
			// TODO: display pop-up please select an activity
		} else {
			new AddTeamMember(currentActivity);
		}
	}

	private int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
}