package Controller;

import JFrames.LoginFrameClone;

public class DisplayController {
	private static DisplayController self = null;
	private MainController mc = MainController.get();
	private LoginFrameClone loginFrame;

	private DisplayController() {
		loginFrame = new LoginFrameClone();
		loginFrame.setSize(1000, 600);
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
			//TODO: ErrorController -> username cannot be blank
			return;
		} else if (password.isEmpty()) {
			//TODO: ErrorController -> password cannot be blank
			return;
		} else {
			if (mc.login(username, password)) {
				//TODO: launch application frame
				loginFrame.close();
			} else {
				//TODO: ErrorController -> invalid log in
			}
		}

	}
}