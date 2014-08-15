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

import Controller.MainController;
import PModel.Activity;
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
	private Project newPro = null;
	private TextArea textArea_Description = new TextArea();
	private ArrayList<Activity> activities;
	private ArrayList<Activity> selectedActivities;	
	List listSelectedDependencies = new List();
	private JTextField otc_textField;
	private JTextField ptc_textField;
	private JTextField mltc_textField;
	private JTextField pv_textField;
	private JTextField tcd_textField;
	
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
			lblProjectName.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			lblProjectName.setBounds(44, 58, 123, 14);
			contentPanel.add(lblProjectName);
		}
		{
			txt_newProjectNameField = new JTextField();
			txt_newProjectNameField.setBounds(170, 56, 200, 20);
			contentPanel.add(txt_newProjectNameField);
			txt_newProjectNameField.setColumns(10);
		}
		{
			JLabel lblPleaseEnterNew = new JLabel("Please enter new activity name:");
			lblPleaseEnterNew.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			lblPleaseEnterNew.setBounds(10, 25, 225, 14);
			contentPanel.add(lblPleaseEnterNew);
		}
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(47, 100, 83, 14);
		contentPanel.add(lblDescription);
		

		textArea_Description.setBounds(170, 84, 200, 119);
		contentPanel.add(textArea_Description);
		
		JLabel lblDependencies = new JLabel("Dependencies:");
		lblDependencies.setBounds(44, 396, 107, 14);
		contentPanel.add(lblDependencies);
		
		final JComboBox dependenciesComboBox = new JComboBox();
		dependenciesComboBox.setBounds(170, 490, 200, 20);
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
		btnAddDependency.setBounds(376, 489, 58, 23);
		contentPanel.add(btnAddDependency);
		
				listSelectedDependencies.setBounds(170, 396, 200, 87);
				contentPanel.add(listSelectedDependencies);
				
				JLabel lblNewLabel = new JLabel("Optimistic Time to Completion");
				lblNewLabel.setBounds(44, 275, 208, 42);
				contentPanel.add(lblNewLabel);
				
				otc_textField = new JTextField();
				otc_textField.setBounds(274, 282, 96, 28);
				contentPanel.add(otc_textField);
				otc_textField.setColumns(10);
				
				JLabel lblPessimisticTimeTo = new JLabel("Pessimistic Time to Completion");
				lblPessimisticTimeTo.setBounds(44, 305, 208, 42);
				contentPanel.add(lblPessimisticTimeTo);
				
				ptc_textField = new JTextField();
				ptc_textField.setColumns(10);
				ptc_textField.setBounds(274, 311, 96, 28);
				contentPanel.add(ptc_textField);
				
				JLabel lblMostLikelyTime = new JLabel("Most Likely Time to Completion");
				lblMostLikelyTime.setBounds(44, 244, 208, 42);
				contentPanel.add(lblMostLikelyTime);
				
				mltc_textField = new JTextField();
				mltc_textField.setColumns(10);
				mltc_textField.setBounds(274, 251, 96, 28);
				contentPanel.add(mltc_textField);
				
				JLabel lblPlannedValue = new JLabel("Planned Value");
				lblPlannedValue.setBounds(44, 209, 208, 42);
				contentPanel.add(lblPlannedValue);
				
				pv_textField = new JTextField();
				pv_textField.setColumns(10);
				pv_textField.setBounds(274, 216, 96, 28);
				contentPanel.add(pv_textField);
				
				JLabel lblTargetCompletionDate = new JLabel("Target Completion Date");
				lblTargetCompletionDate.setBounds(44, 340, 208, 42);
				contentPanel.add(lblTargetCompletionDate);
				
				tcd_textField = new JTextField();
				tcd_textField.setColumns(10);
				tcd_textField.setBounds(274, 346, 96, 28);
				contentPanel.add(tcd_textField);
		
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
								!otc_textField.getText().equals("") &&
								!ptc_textField.getText().equals("") &&
								!mltc_textField.getText().equals("") &&
								!pv_textField.getText().equals("") &&
								!tcd_textField.getText().equals("")
								)
						{
							int pid = MainController.get().getCurrentProject().getProjectID();
							String name = txt_newProjectNameField.getText();
							String description = textArea_Description.getText();
							double otc = Double.parseDouble(otc_textField.getText());
							double ptc = Double.parseDouble(ptc_textField.getText());
							double mltc = Double.parseDouble(mltc_textField.getText());
							double pv = Double.parseDouble(pv_textField.getText());
							double tcd = Double.parseDouble(tcd_textField.getText());
							
							//JOptionPane.showMessageDialog(null,currentUser.getMemberID());						
							Activity ac = new Activity(pid, name, description, pv, mltc, otc, ptc, tcd, 0, 0, false);
					        //table_1 = MainController.get().ProjectList(table_1);
					        Activity ac2 = MainController.get().createActivity(ac);
					        MainController.get().createActivityDependencies(ac2, selectedActivities);
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

