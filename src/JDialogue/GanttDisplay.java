/**
 * 
 */
package JDialogue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Scrollbar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;

import Analysis.GanttNetwork;
import PModel.Activity;

/**
 *
 */
public class GanttDisplay extends JDialog {
	
	GanttNetwork ganttNetwork;
	String title;
	
	public GanttDisplay(final String title, GanttNetwork ganttNetwork) {
		
		this.ganttNetwork = ganttNetwork;
		this.title = title;
			
		final IntervalCategoryDataset dataset = createDataset();
		final JFreeChart chart = createGraph(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1080, 720));
		setContentPane(chartPanel);
		this.setBounds(20, 20, 1000, 800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public IntervalCategoryDataset createDataset(){
		final TaskSeries series = new TaskSeries("Activities Scheduled");
		final TaskSeries criticalPath = new TaskSeries("Critical Path");
		
		double zero = new Double(0);
		Task temp;
		
		for(Activity a : ganttNetwork.getActivities()){
			temp = new Task(a.getName(), a);
			
			if(((Double)(a.getDurationFloat())).equals(zero)){
				criticalPath.add(temp);
			}
			else{
				series.add(temp);
			}
		}
		
		final TaskSeriesCollection collection = new TaskSeriesCollection();
		
		collection.add(criticalPath);
		collection.add(series);
		
		return collection;
	}
	
    private JFreeChart createGraph(final IntervalCategoryDataset dataset){
    	final JFreeChart chart = ChartFactory.createGanttChart(title, "Tasks", "Date", dataset, true, true, false);
		return chart;
    }
	
//	private final JPanel contentPanel = new JPanel();
//	
//	public GanttDisplay(String theArtWork) {
//		getContentPane().setLayout(null);
//		
//		setModalityType(ModalityType.APPLICATION_MODAL);
//	    setTitle("Gantt Chart");
//	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//	    setLocationRelativeTo(null);
//		setBounds(100, 100, 1000, 593);
//		
//		getContentPane().setLayout(new BorderLayout());
//		contentPanel.setBackground(new Color(204, 255, 204));
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		contentPanel.setLayout(null);
//		
//		
//		getContentPane().add(contentPanel);
//		
//		
//		JTextArea textArea = new JTextArea();
//		textArea.setFont(new Font("Menlo", Font.PLAIN, 13));
//		textArea.setBounds(26, 27, 968, 501);
//		contentPanel.add(textArea);
//		textArea.setText(theArtWork);
//		
//	}	
}
