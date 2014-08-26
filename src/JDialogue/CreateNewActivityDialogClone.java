package JDialogue;

import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controller.DisplayController;
import Controller.ErrorController;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class CreateNewActivityDialogClone extends JDialog {

	private ArrayList<String> dependenciesListToReturn;

	public CreateNewActivityDialogClone(
			final JComboBox<String> activitiesComboBox) {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Create New Activity");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 477, 501);

		getContentPane().setBackground(new Color(204, 255, 204));
		getContentPane().setLayout(null);

		JLabel lblActivityName = new JLabel("Activity Name:");
		lblActivityName.setBounds(10, 11, 123, 28);
		getContentPane().add(lblActivityName);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(10, 37, 83, 14);
		getContentPane().add(lblDescription);

		JLabel lblTimeFormat = new JLabel(
				"Please insert time values in days, relative to the start date of the project");
		lblTimeFormat.setBounds(10, 195, 431, 14);
		getContentPane().add(lblTimeFormat);

		JLabel lblDependencies = new JLabel("Dependencies:");
		lblDependencies.setBounds(44, 350, 85, 14);
		getContentPane().add(lblDependencies);

		JLabel lblNewLabel = new JLabel("Optimistic Time");
		lblNewLabel.setBounds(44, 286, 137, 28);
		getContentPane().add(lblNewLabel);

		JLabel lblMostLikelyTime = new JLabel("Most Likely Time");
		lblMostLikelyTime.setBounds(44, 251, 137, 28);
		getContentPane().add(lblMostLikelyTime);

		JLabel lblPlannedValue = new JLabel("Planned Value");
		lblPlannedValue.setBounds(44, 220, 137, 28);
		getContentPane().add(lblPlannedValue);

		JLabel lblPessimisticTimeTo = new JLabel("Pessimistic Time");
		lblPessimisticTimeTo.setBounds(44, 316, 137, 28);
		getContentPane().add(lblPessimisticTimeTo);

		final JTextField ActivityNameTextField = new JTextField();
		ActivityNameTextField.setBounds(143, 15, 200, 20);
		getContentPane().add(ActivityNameTextField);
		ActivityNameTextField.setColumns(10);

		final JTextField optimisticTextField = new JTextField();
		optimisticTextField.setBounds(183, 290, 96, 20);
		getContentPane().add(optimisticTextField);
		optimisticTextField.setColumns(10);

		final JTextField pessimisticTextField = new JTextField();
		pessimisticTextField.setColumns(10);
		pessimisticTextField.setBounds(183, 320, 96, 20);
		getContentPane().add(pessimisticTextField);

		final JTextField mostLikelyTextField = new JTextField();
		mostLikelyTextField.setColumns(10);
		mostLikelyTextField.setBounds(183, 255, 96, 20);
		getContentPane().add(mostLikelyTextField);

		final JTextField plannedValueTextField = new JTextField();
		plannedValueTextField.setColumns(10);
		plannedValueTextField.setBounds(183, 224, 96, 20);
		getContentPane().add(plannedValueTextField);

		final JTextArea textAreaDescription = new JTextArea();
		textAreaDescription.setBounds(10, 58, 441, 126);
		getContentPane().add(textAreaDescription);

		activitiesComboBox.setBounds(132, 490, 207, 20);
		getContentPane().add(activitiesComboBox);

		final List selectedDependencies = new List();
		selectedDependencies.setBounds(136, 350, 207, 70);
		getContentPane().add(selectedDependencies);

		JButton btnAddDependency = new JButton("Add");
		btnAddDependency.setBounds(393, 356, 58, 23);
		getContentPane().add(btnAddDependency);

		JButton btnDone = new JButton("Done");
		btnDone.setBounds(393, 390, 58, 23);
		getContentPane().add(btnDone);

		btnAddDependency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String chosenActivity = (String) activitiesComboBox
						.getSelectedItem();
				if (!dependenciesListToReturn.contains(chosenActivity)) {
					dependenciesListToReturn.add(chosenActivity);
					selectedDependencies.add(chosenActivity);
				} else {
					ErrorController.get().showError(
							"Cannot add the same activity twice");
				}
			}
		});

		btnDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!ActivityNameTextField.getText().equals("")
						&& !optimisticTextField.getText().equals("")
						&& !pessimisticTextField.getText().equals("")
						&& !mostLikelyTextField.getText().equals("")
						&& !plannedValueTextField.getText().equals("")
						&& !textAreaDescription.getText().equals("")) {

					String activityName = ActivityNameTextField.getText();
					String activityDescription = textAreaDescription.getText();
					double plannedValue;
					double mostLikely;
					double optimistic;
					double pessimistic;
					double targetDate;
					try {
						plannedValue = Double.parseDouble(plannedValueTextField
								.getText());
						optimistic = Double.parseDouble(optimisticTextField
								.getText());
						pessimistic = Double.parseDouble(plannedValueTextField
								.getText());
						mostLikely = Double.parseDouble(mostLikelyTextField
								.getText());
						DisplayController.get().setActivityToCreate(
								activityName,
								activityDescription,
								new double[] { plannedValue, mostLikely,
										optimistic, pessimistic },
								dependenciesListToReturn);
					} catch (NumberFormatException ex) {
						ErrorController
								.get()
								.showError(
										"Please ensure all fields contain valid values.");
					}
				} else {
					ErrorController.get().showError(
							"Please ensure all fields are completed");
				}
			}
		});
		setVisible(true);
	}
}