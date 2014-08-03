package PModel;

import java.util.ArrayList;
import java.util.Calendar;
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

public class GanttChart extends ApplicationFrame {
	private ArrayList<GanttActivities> gaList;
	private String currentProjTitle;
	
	public GanttChart(final String title,ArrayList<GanttActivities> gaList) {
		super(title);
		this.gaList = gaList;
		this.currentProjTitle = MainController.get().GetCurrentProject().getName();
		
		final IntervalCategoryDataset dataset = createDataset();
		final JFreeChart chart = createGraph(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1080, 720));
		setContentPane(chartPanel);
	}
	
	public IntervalCategoryDataset createDataset(){
		final TaskSeries series = new TaskSeries("Activities Scheduled");
		for(GanttActivities ga : gaList){
			series.add(new Task(ga.getTitle(), 
					new SimpleTimePeriod(date(ga.getStartDay(), ga.getStartMonth(), ga.getStartYear()),
							date(ga.getEndDay(), ga.getEndMonth(), ga.getEndYear()))));
		}
		
		final TaskSeriesCollection collection = new TaskSeriesCollection();
		collection.add(series);
		return collection;
	}
	
    private static Date date(final int day, final int month, final int year) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final Date result = calendar.getTime();
        return result;
    }
    
    private JFreeChart createGraph(final IntervalCategoryDataset dataset){
    	final JFreeChart chart = ChartFactory.createGanttChart(currentProjTitle, "Tasks", "Date", dataset, true, true, false);
		return chart;
    }
}
