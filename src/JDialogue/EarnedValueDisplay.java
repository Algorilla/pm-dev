package JDialogue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import PModel.Project;

/**
 *
 */
public class EarnedValueDisplay extends JDialog {

	private String[] columnNames;
	private Project project;

	public EarnedValueDisplay(Project project) {
		this.project = project;

		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Earned Value Report");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		DefaultTableModel model;
		JTable table;
		columnNames = new String[] { "Project Name", "BAC", "%C", "%S", "AC",
				"EV", "PV", "CPI", "SPI", "EAC", "ETC" };

		model = new DefaultTableModel(columnNames, 0);
		table = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 884, 84);

		table.getColumnModel().getColumn(0).setMinWidth(150);

		Object[] data = new Object[columnNames.length];
		data = listValues(project);

		model.addRow(data);
		getContentPane().setLayout(null);

		getContentPane().add(scrollPane);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(900, 122);
		setVisible(true);

	}

	/**
	 * @param p
	 * @return
	 */
	private Object[] listValues(Project p) {
		double budget = roundNumber(project.getBudgetAtCompletion());
		double percentComplete = roundNumber(project.getPercentComplete());
		double percentScheduled = roundNumber(project
				.getPercentScheduledForCompletion());
		double actualCost = roundNumber(project.getActualCost());
		double earnedValue = roundNumber(project.getEarnedValue());
		double plannedValue = roundNumber(project.getPlannedValue());
		double cpi = roundNumber(project.getCostPerformanceIndex());
		double spi = roundNumber(project.getSchedulePerformanceIndex());
		double estimateAtComplete = roundNumber(project
				.getEstimateAtCompletion());
		double estimateToComplete = roundNumber(project.getEstimateToComplete());

		Object[] data = new Object[] { project.getName(), budget,
				percentComplete, percentScheduled, actualCost, earnedValue,
				plannedValue, cpi, spi, estimateAtComplete, estimateToComplete };

		return data;
	}

	private double roundNumber(double value) {
		return (double) Math.round(value * 100) / 100;
	}
}
