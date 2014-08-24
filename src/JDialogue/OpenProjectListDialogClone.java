package JDialogue;

import Controller.DisplayController;
import Controller.ErrorController;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.List;

public class OpenProjectListDialogClone extends JDialog {

	String projectName = null;

	public OpenProjectListDialogClone(final List projectList) {

		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Open Project");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(null);

		JLabel lblProject = new JLabel("Projects:");
		lblProject.setBounds(23, 11, 321, 14);
		getContentPane().add(lblProject);

		projectList.setBounds(33, 31, 350, 136);
		getContentPane().add(projectList);

		JButton okButton = new JButton("OK");
		okButton.setSize(68, 28);
		okButton.setLocation(356, 173);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (projectList.getSelectedItem() == null) {
					ErrorController.get().showError("Please select a project to open");
				} else {
					String projectName = projectList.getSelectedItem();
					DisplayController.get().setProjectToOpen(projectName);
					dispose();
				}
			}
		});
		getContentPane().add(okButton);
		getRootPane().setDefaultButton(okButton);
		setVisible(true);
	}
}
