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
	private TextArea textArea_Description = new TextArea();
	JDateChooser StartedDate;
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public CreateNewProjectDialogClone() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Create New Project");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 450, 460);
		setVisible(true);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(204, 255, 204));
		((JComponent) getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		// add(getContentPane(), BorderLayout.CENTER);
		getContentPane().setLayout(null);

		JLabel lblProjectName = new JLabel("Project Name:");
		lblProjectName.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblProjectName.setBounds(44, 84, 123, 14);
		getContentPane().add(lblProjectName);

		textFieldNewProjectName = new JTextField();
		textFieldNewProjectName.setBounds(170, 82, 200, 20);
		getContentPane().add(textFieldNewProjectName);
		textFieldNewProjectName.setColumns(10);

		JLabel lblPleaseEnterNew = new JLabel("Please enter new project name:");
		lblPleaseEnterNew.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblPleaseEnterNew.setBounds(10, 25, 225, 14);
		getContentPane().add(lblPleaseEnterNew);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(47, 126, 83, 14);
		getContentPane().add(lblDescription);

		textArea_Description.setBounds(170, 110, 200, 119);
		getContentPane().add(textArea_Description);

		StartedDate = new JDateChooser();
		StartedDate.setBounds(170, 238, 200, 20);
		getContentPane().add(StartedDate);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(44, 242, 86, 16);
		getContentPane().add(lblStartDate);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// newPro = new Project();
				Member currentUser = MainController.get().getCurrentUser();
				int managerID = currentUser.getMemberID();
				String name = textFieldNewProjectName.getText();
				String description = textArea_Description.getText();
				// JOptionPane.showMessageDialog(null,currentUser.getMemberID());
				String startDate = sdf.format(StartedDate.getDate()); // date
																		// from
																		// JDateChooser

				if (name.isEmpty()) {
					ErrorController.get().showError(
							"Please enter the project name");
				} else if (description.isEmpty()) {
					ErrorController.get().showError(
							"Please fill in the project description");
				} else if (startDate.isEmpty()) {
					ErrorController.get().showError(
							"Please enter the start date for this project");
				} else {
					newPro = new Project(managerID, name, description,
							StartedDate.getDate(), 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0);
				}
				
				
				MainController.get().initializeProject(newPro);
				MainController.get().openProject(newPro.getName());
				MainController.get().notifyDisplayController(
						PModelChange.CREATED_PROJECT);
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

	}

}