package JDialogue;

import javax.swing.JButton;
import javax.swing.JDialog;

import Controller.DisplayController;
import Controller.ErrorController;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.List;

public class DeleteProjectDialogClone extends JDialog {

	public DeleteProjectDialogClone(final List projectList) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Delete Project");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(null);

		JLabel lblProjects = new JLabel("Projects:");
		lblProjects.setBounds(23, 11, 321, 14);
		getContentPane().add(lblProjects);

		projectList.setBounds(44, 46, 329, 108);
		getContentPane().add(projectList);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(335, 177, 89, 23);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String projectName = projectList.getSelectedItem().toString();
				if (projectName == null) {
					ErrorController.get().showError("Please select a project to delete");
				} else {
					DisplayController.get().setProjectToDelete(projectName);
					dispose();
				}
			}
		});
		getContentPane().add(btnDelete);

		getRootPane().setDefaultButton(btnDelete);
		setVisible(true);
	}

}