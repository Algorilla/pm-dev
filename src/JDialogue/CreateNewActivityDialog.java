package JDialogue;

import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import Controller.DisplayController;
import Controller.ErrorController;

public class CreateNewActivityDialog extends JDialog {

	private ArrayList<String> dependenciesListToReturn = new ArrayList<String>();

	public CreateNewActivityDialog(
			final JComboBox<String> activitiesComboBox) {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Create New Activity");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 477, 501);

		getContentPane().setBackground(new Color(204, 255, 204));
		getContentPane().setLayout(null);

		JLabel lblActivityName = new JLabel("Activity Name:");
		lblActivityName.setBounds(10, 7, 123, 28);
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
		lblNewLabel.setBounds(44, 280, 137, 28);
		getContentPane().add(lblNewLabel);

		JLabel lblMostLikelyTime = new JLabel("Most Likely Time");
		lblMostLikelyTime.setBounds(44, 250, 137, 28);
		getContentPane().add(lblMostLikelyTime);

		JLabel lblPlannedValue = new JLabel("Planned Value");
		lblPlannedValue.setBounds(44, 220, 137, 28);
		getContentPane().add(lblPlannedValue);

		JLabel lblPessimisticTimeTo = new JLabel("Pessimistic Time");
		lblPessimisticTimeTo.setBounds(44, 310, 137, 28);
		getContentPane().add(lblPessimisticTimeTo);

		final JTextField ActivityNameTextField = new JTextField();
		ActivityNameTextField.setBounds(143, 11, 200, 20);
		getContentPane().add(ActivityNameTextField);
		ActivityNameTextField.setColumns(10);

		final JTextField optimisticTextField = new JTextField();
		optimisticTextField.setBounds(191, 284, 96, 20);
		getContentPane().add(optimisticTextField);
		optimisticTextField.setColumns(10);

		final JTextField pessimisticTextField = new JTextField();
		pessimisticTextField.setColumns(10);
		pessimisticTextField.setBounds(191, 314, 96, 20);
		getContentPane().add(pessimisticTextField);

		final JTextField mostLikelyTextField = new JTextField();
		mostLikelyTextField.setColumns(10);
		mostLikelyTextField.setBounds(191, 254, 96, 20);
		getContentPane().add(mostLikelyTextField);

		final JTextField plannedValueTextField = new JTextField();
		plannedValueTextField.setColumns(10);
		plannedValueTextField.setBounds(191, 224, 96, 20);
		getContentPane().add(plannedValueTextField);

		final JTextArea textAreaDescription = new JTextArea();
		textAreaDescription.setBounds(10, 58, 441, 126);
		getContentPane().add(textAreaDescription);

		activitiesComboBox.setBounds(135, 425, 186, 20);
		getContentPane().add(activitiesComboBox);

		final List selectedDependencies = new List();
		selectedDependencies.setBounds(136, 350, 186, 70);
		getContentPane().add(selectedDependencies);

		JButton btnAddDependency = new JButton("Add");
		btnAddDependency.setBounds(328, 428, 123, 23);
		getContentPane().add(btnAddDependency);

		JButton btnCreate = new JButton("Create Activity");
		btnCreate.setBounds(328, 377, 123, 23);
		getContentPane().add(btnCreate);

		btnAddDependency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String chosenActivity = (String) activitiesComboBox
						.getSelectedItem();
				if (chosenActivity == null) {
					ErrorController.get().showError(
							"No dependencies to choose from");
				} else if (!dependenciesListToReturn.contains(chosenActivity)) {
					dependenciesListToReturn.add(chosenActivity);
					selectedDependencies.add(chosenActivity);
				} else {
					ErrorController
							.get()
							.showError(
									"Cannot add the same activity twice as a dependency");
				}
			}
		});

		btnCreate.addActionListener(new ActionListener() {
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
						dispose();
					} catch (NumberFormatException ex) {
						ErrorController
								.get()
								.showError(
										"Please ensure all numeric fields contain numbers.");
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