package Controller;

import JDialogue.CreateNewProjectDialog;
import JDialogue.DeleteProjectDialog;
import JDialogue.OpenProjectListDialog;
import JFrames.LoginFrameClone;
import JFrames.UserInterfaceClone;
import PModel.Project;

public class DisplayController {
	private static DisplayController self = null;

	private MainController mc = MainController.get();
	private Project currentProject = null;

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
			//TODO: ErrorController display pop-up: username cannot be blank
			return;
		} else if (password.isEmpty()) {
			//TODO: ErrorController display pop-up: password cannot be blank
			return;
		} else {
			if (mc.login(username, password)) {
				if (userInterface == null) {
					userInterface = new UserInterfaceClone();
				}

				userInterface.setVisible(true);
				loginFrame.setVisible(false);
			} else {
				//TODO: ErrorController display pop-up invalid log in
			}
		}
	}

	public void createNewProject() {
		//TODO: refactor CreateNewProjectDialog
		CreateNewProjectDialog newProjectDialog = new CreateNewProjectDialog();
		newProjectDialog.setVisible(true);
		//TODO: ensure project is selected
		currentProject = mc.getCurrentProject();
		userInterface.loadProject();
	}

	public void openProject() {
		//TODO: refactor openProjectListDialog
		OpenProjectListDialog openProjectDialog = new OpenProjectListDialog();
		openProjectDialog.setVisible(true);
		//TODO: ensure project is selected
		userInterface.loadProject();
	}

	public void deleteProject() {
		//TODO: refactor delete project
		DeleteProjectDialog deleteProjectDialog = new DeleteProjectDialog();
		deleteProjectDialog.setVisible(true);
		//TODO: ensure project is selected
	}

	public void exit() {
		System.exit(0);
	}
}