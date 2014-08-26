package JDialogue;

import Controller.DisplayController;
import Controller.ErrorController;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.awt.TextArea;

import com.toedter.calendar.JDateChooser;

public class CreateNewProjectDialogClone extends JDialog {

	private JTextField textFieldNewProjectName;
	private TextArea textAreaDescription;
	private JDateChooser StartedDate;

	public CreateNewProjectDialogClone() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Create New Project");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 383, 411);
		getContentPane().setBackground(new Color(204, 255, 204));
		((JComponent) getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(null);

		JLabel lblProjectName = new JLabel("Project Name:");
		lblProjectName.setBounds(20, 27, 123, 14);
		getContentPane().add(lblProjectName);

		textFieldNewProjectName = new JTextField();
		textFieldNewProjectName.setBounds(20, 50, 200, 20);
		textFieldNewProjectName.setColumns(10);
		getContentPane().add(textFieldNewProjectName);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(20, 81, 83, 14);
		getContentPane().add(lblDescription);

		textAreaDescription = new TextArea();
		textAreaDescription.setBounds(20, 106, 326, 180);
		getContentPane().add(textAreaDescription);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(20, 307, 86, 16);
		getContentPane().add(lblStartDate);

		StartedDate = new JDateChooser();
		StartedDate.setBounds(106, 307, 240, 20);
		getContentPane().add(StartedDate);

		JButton okButton = new JButton("OK");
		okButton.setBounds(263, 338, 83, 20);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textFieldNewProjectName.getText();
				String description = textAreaDescription.getText();
				Date startDate = StartedDate.getDate();

				Calendar calender = Calendar.getInstance();
				calender.set(Calendar.HOUR, 0);
				calender.set(Calendar.MINUTE, 0);
				calender.set(Calendar.SECOND, 0);
				Date today = calender.getTime();

				if (name.isEmpty()) {
					ErrorController.get().showError(
							"Please enter the project name");
				} else if (description.isEmpty()) {
					ErrorController.get().showError(
							"Please enter the project description");
				} else if (startDate == null) {
					ErrorController.get().showError(
							"Please enter the start date for this project");
				} else if (startDate.before(today)) {
					ErrorController.get().showError(
							"Project date cannot start in the past");
				} else {
					String newProjectArgs[] = new String[3];
					newProjectArgs[0] = name;
					newProjectArgs[1] = description;
					newProjectArgs[2] = StartedDate.getDate().toString();
					DisplayController.get().setProjectToCreate(newProjectArgs);
					dispose();
				}
			}
		});

		getContentPane().add(okButton);
		setVisible(true);
	}
}