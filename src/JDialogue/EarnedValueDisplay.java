/**
 * 
 */
package JDialogue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import PModel.Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

/**
 *
 */
public class EarnedValueDisplay extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private String[] columnNames;
	private String[] rowNames;
//	private ArrayList<Project> projects;
	private Project project;
	private JTable table_1;
	private JTable table_2;
	
	public EarnedValueDisplay(Project project) {
		
		this.project = project;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Earned Value Report");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(300, 900, 477, 593);
		
		Dimension paneSize = new Dimension();
		paneSize.setSize(800, 350);
		this.setSize(paneSize);
		
		DefaultTableModel model;
		JTable table;

		columnNames = new String[] {"Project Name",
									"BAC",
									"%C",
									"%S",
									"AC",
									"EV",
									"PV", 
									"CPI",
									"SPI",
									"EAC",
									"ETC"
									};
		
		model = new DefaultTableModel(columnNames, 2);
		table = new JTable(model);
		
		
		
		JScrollPane	pane = new JScrollPane(table);
		pane.setBounds(200, 200, 900, 900);
		
		table.getColumnModel().getColumn(0).setMinWidth(150);

		
		
		Object [] data = new Object[columnNames.length];
		data = listValues(project);
		
		model.addRow(data);
		
		add(pane);
		setVisible(true);
		setSize(900,900);
//		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		
//		

//		
	
//		getContentPane().add(table_2, BorderLayout.CENTER);
//		
		
	}

	/**
	 * @param p
	 * @return
	 */
	private Object[] listValues(Project p) {
		Object [] data = new Object [] {project.getName(),
				new Double(project.getBudgetAtCompletion()),
				new Double(project.getPercentComplete()),
				new Double(project.getPercentScheduledForCompletion()),
				new Double(project.getActualCost()),
				new Double(project.getEarnedValue()),
				new Double(project.getPlannedValue()),
				new Double(project.getCostPerformanceIndex()),
				new Double(project.getSchedulePerformanceIndex()),
				new Double(project.getEstimateAtCompletion()),
				new Double(project.getEstimateToComplete())
									   };
		
		return data;
	}
}
