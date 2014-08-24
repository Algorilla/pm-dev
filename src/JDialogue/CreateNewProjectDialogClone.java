package JDialogue;

import Controller.DisplayController;
import Controller.ErrorController;
import Controller.MainController;
import Controller.PModelChange;
import PModel.Member;
import PModel.Project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.TextArea;

import com.toedter.calendar.JDateChooser;

/**
 * A dialogue used to create new project.
 * 
 * @author team B
 * 
 */

public class CreateNewProjectDialogClone extends JDialog {

	private JTextField textFieldNewProjectName;
	private Project newPro = null;
	private TextArea textAreaDescription = new TextArea();
	JDateChooser StartedDate;
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public CreateNewProjectDialogClone() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Create New Project");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 383, 411);
		getContentPane().setBackground(new Color(204, 255, 204));
		((JComponent) getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);

		JLabel lblProjectName = new JLabel("Project Name:");
		lblProjectName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProjectName.setBounds(20, 27, 123, 14);
		getContentPane().add(lblProjectName);

		textFieldNewProjectName = new JTextField();
		textFieldNewProjectName.setBounds(20, 50, 200, 20);
		getContentPane().add(textFieldNewProjectName);
		textFieldNewProjectName.setColumns(10);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(20, 81, 83, 14);
		getContentPane().add(lblDescription);

		textAreaDescription.setBounds(20, 106, 326, 180);
		getContentPane().add(textAreaDescription);

		StartedDate = new JDateChooser();
		StartedDate.setBounds(106, 307, 240, 20);
		getContentPane().add(StartedDate);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(20, 307, 86, 16);
		getContentPane().add(lblStartDate);

		JButton okButton = new JButton("OK");
		okButton.setBounds(263, 338, 83, 20);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// newPro = new Project();
				Member currentUser = MainController.get().getCurrentUser();
				int managerID = currentUser.getMemberID();
				String name = textFieldNewProjectName.getText();
				String description = textAreaDescription.getText();
				// JOptionPane.showMessageDialog(null,currentUser.getMemberID());
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
							"Please select a valid date");
				} else {
					newPro = new Project(managerID, name, description,
							StartedDate.getDate(), 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0);
					DisplayController.get().setNewProject(newPro);
					dispose();
				}
			}
		});

		getContentPane().add(okButton);

		this.setVisible(true);
	}
}