package JDialogue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import PModel.Activity;
import PModel.MainController;
import PModel.Member;
import PModel.Project;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JList;
/**
 * A dialogue used to create new activity of the project
 * @author Administrator
 *
 */
public class CreateNewActivityDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_newProjectNameField;
	private JTextField textField_length;
	JDateChooser StartedDate;
	JDateChooser Deadline;
	private Project newPro = null;
	private TextArea textArea_Description = new TextArea();
	private ArrayList<Activity> activities;
	private ArrayList<Activity> selectedActivities;	
	List listSelectedDependencies = new List();
	
	public CreateNewActivityDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Create New Activity");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 477, 593);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(204, 255, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblProjectName = new JLabel("Activity Name:");
			lblProjectName.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblProjectName.setBounds(44, 84, 123, 14);
			contentPanel.add(lblProjectName);
		}
		{
			txt_newProjectNameField = new JTextField();
			txt_newProjectNameField.setBounds(170, 82, 200, 20);
			contentPanel.add(txt_newProjectNameField);
			txt_newProjectNameField.setColumns(10);
		}
		{
			JLabel lblPleaseEnterNew = new JLabel("Please enter new activity name:");
			lblPleaseEnterNew.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblPleaseEnterNew.setBounds(10, 25, 225, 14);
			contentPanel.add(lblPleaseEnterNew);
		}
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(47, 126, 83, 14);
		contentPanel.add(lblDescription);
		{
			JLabel lblStartedDate = new JLabel("Started Date:");
			lblStartedDate.setBounds(44, 238, 86, 14);
			contentPanel.add(lblStartedDate);
		}

		
		JLabel lblEndedDate = new JLabel("Deadline:");
		lblEndedDate.setBounds(47, 281, 66, 14);
		contentPanel.add(lblEndedDate);
		
		JLabel lblProjectLength = new JLabel("Length (Days):");
		lblProjectLength.setBounds(44, 317, 86, 14);
		contentPanel.add(lblProjectLength);
		
		textField_length = new JTextField();
		textField_length.setBounds(170, 314, 200, 20);
		contentPanel.add(textField_length);
		textField_length.setColumns(10);
		

		textArea_Description.setBounds(170, 110, 200, 119);
		contentPanel.add(textArea_Description);
		
		StartedDate = new JDateChooser();
		StartedDate.setDate(new Date());
		StartedDate.setBounds(170, 238, 200, 20);
		contentPanel.add(StartedDate);
		
		Deadline = new JDateChooser();
		Deadline.setDate(new Date());
		Deadline.setBounds(170, 275, 200, 20);
		contentPanel.add(Deadline);
		
		JLabel lblDependencies = new JLabel("Dependencies:");
		lblDependencies.setBounds(44, 364, 107, 14);
		contentPanel.add(lblDependencies);
		
		final JComboBox dependenciesComboBox = new JComboBox();
		dependenciesComboBox.setBounds(170, 458, 200, 20);
		activities = MainController.get().getActivityListForCurrentProject();		
		for (Activity activity : activities)
			dependenciesComboBox.addItem(activity);		
		contentPanel.add(dependenciesComboBox);
		
		selectedActivities = new ArrayList<Activity>();
		JButton btnAddDependency = new JButton("Add");
		btnAddDependency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedActivities.add((Activity)dependenciesComboBox.getSelectedItem());
				listSelectedDependencies.addItem(((Activity)dependenciesComboBox.getSelectedItem()).toString());
			}
		});
		btnAddDependency.setBounds(376, 457, 58, 23);
		contentPanel.add(btnAddDependency);
		
				listSelectedDependencies.setBounds(170, 364, 200, 87);
				contentPanel.add(listSelectedDependencies);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (
								!txt_newProjectNameField.getText().equals("") &&
								!textArea_Description.getText().equals("") &&
								!textField_length.getText().equals("") &&
								Deadline.getDate().after(StartedDate.getDate()))
						{
							int pid = MainController.get().GetCurrentProject().getProjectID();
							String name = txt_newProjectNameField.getText();
							String description = textArea_Description.getText();
							Date start = StartedDate.getDate();
							Date deadline = Deadline.getDate();
							int length = Integer.parseInt(textField_length.getText());
							//JOptionPane.showMessageDialog(null,currentUser.getMemberID());						
							Activity ac = new Activity(pid, name, description, start, deadline, length);
					        //table_1 = MainController.get().ProjectList(table_1);
					        Activity ac2 = MainController.get().CreateActivity(ac);
					        MainController.get().CreateActivityDependencies(ac2, selectedActivities);
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null,"There were errors, please check your input.");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}

