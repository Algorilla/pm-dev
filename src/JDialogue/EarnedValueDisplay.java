/**
 * 
 */
package JDialogue;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import PModel.Project;

import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 *
 */
public class EarnedValueDisplay extends JDialog {
	private JTable table;
	private String[] columnNames;
	private String[] rowNames;
	private ArrayList<Project> projects;
	
	public EarnedValueDisplay(ArrayList<Project> activities) {
		
		this.projects = activities;
		
		columnNames = new String[] {"Project Name",
									"Budget At Completion",
									"Percent Complete",
									"Percent Scheduled for Completion",
									"Actual Cost",
									"Earned Value",
									"Planned Value", 
									"CPI",
									"SPI",
									"Estimate at Completion",
									"Estimate to Complete"
									};
		
		
		Object[][] data = new Object[columnNames.length][activities.size()];
		
		Project p;
		for(int i = 0; i < data.length; i ++){
			p = activities.get(i);
//			data[i] = this.listValues(p);
		}
		
		
		table = new JTable();
		getContentPane().add(table, BorderLayout.CENTER);
	}

	/**
	 * @param p
	 * @return
	 */
	private Object[] listValues(Project p) {
		Object [] data = new Object [] {p.getName(),
										p.getBudgetAtCompletion(),
										p.getPercentComplete(),
										p.getPercentScheduledForCompletion(),
										p.getActualCost(),
										p.getEarnedValue(),
										p.getPlannedValue(),
										p.getCostPerformanceIndex(),
										p.getSchedulePerformanceIndex(),
										p.getEstimateAtCompletion(),
										p.getEstimateToComplete()
									   };
		
		return data;
	}

}
