//package JDialogue;
//
//import java.awt.Color;
//import javax.swing.JFrame;
//import javax.swing.SwingUtilities;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartMouseEvent;
//import org.jfree.chart.ChartMouseListener;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.annotations.XYTextAnnotation;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
//import org.jfree.data.xy.IntervalXYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.ui.TextAnchor;
//
//public class EarnedValueDisplay extends JFrame {
//
//   private ChartPanel mChartPanel;
//
//   /**
//    * Create the GUI and show it.
//    */
//   public void createAndShowGUI() {
//      // Create and set up the window.
//      JFrame frame = new JFrame("Crosshair example");
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      ChartPanel chartPanel = createChart();
//      frame.add(chartPanel);
//      // Display the window.
//      frame.pack();
//      frame.setVisible(true);
//   }
//
//   private ChartPanel createChart() {
//
//      mChartPanel = new ChartPanel(ChartFactory.createXYLineChart(null,
//            "xAxisLabel", "yAxisLabel", createDataset1(),
//            PlotOrientation.VERTICAL, false, true, true));
//
//      mChartPanel.getChart().setTextAntiAlias(true);
//      mChartPanel.getChart().setBackgroundPaint(Color.WHITE);
//
//      XYPlot xyplot = (XYPlot) mChartPanel.getChart().getPlot();
//
//      // Add one axis to the right
//      final NumberAxis numberaxis2 = new NumberAxis("yAxisLabel2");
//
//      xyplot.setRangeAxis(1, numberaxis2);
//      xyplot.setDataset(1, createDataset2());
//      xyplot.mapDatasetToRangeAxis(1, 1);
//
//      final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
//      xyplot.setRenderer(1, renderer2);
//
//      xyplot.setDomainPannable(true);
//      xyplot.setRangePannable(true);
//
//      xyplot.setDomainCrosshairVisible(true);
//      xyplot.setRangeCrosshairVisible(true);
//
//      xyplot.setDomainCrosshairLockedOnData(true);
//      xyplot.setRangeCrosshairLockedOnData(true);
//
//      mChartPanel.addChartMouseListener(new ChartMouseListener() {
//
//         public void chartMouseMoved(ChartMouseEvent chartmouseevent) {
//
//         }
//
//         public void chartMouseClicked(ChartMouseEvent chartmouseevent) {
//
//            SwingUtilities.invokeLater(new Runnable() {
//               public void run() {
//                  XYPlot xyplot = (XYPlot) mChartPanel.getChart().getPlot();
//                  xyplot.clearAnnotations();
//                  double x, y;
//                  x = xyplot.getDomainCrosshairValue();
//                  y = xyplot.getRangeCrosshairValue();
//                  XYTextAnnotation annotation = new XYTextAnnotation("("+ x + ", " + y + ")", x, y);
//                  annotation.setTextAnchor(TextAnchor.BOTTOM_CENTER);
//                  xyplot.addAnnotation(annotation);
//               }
//            });
//         }
//      });
//
//      return mChartPanel;
//
//   }
//
//   /**
//    * Creates a sample dataset.
//    * 
//    * @return A sample dataset.
//    */
//   private static IntervalXYDataset createDataset1() {
//      XYSeries series1 = new XYSeries("AA");
//      int j = 0;
//      for (int i = 0; i < 366; i++) {
//         series1.add(i, j);
//         j = (i % 2 == 0) ? j += 1 : j + 2;
//      }
//      XYSeriesCollection dataset = new XYSeriesCollection();
//      dataset.addSeries(series1);
//      return dataset;
//   }
//
//   /**
//    * Creates a sample dataset.
//    * 
//    * @return A sample dataset.
//    */
//   private static IntervalXYDataset createDataset2() {
//      XYSeries series2 = new XYSeries("BB");
//      int j = 1000;
//      for (int i = 0; i < 366; i++) {
//         series2.add(i, j);
//         j = (i % 2 == 0) ? j += -2 : j + 3;
//      }
//
//      XYSeriesCollection dataset = new XYSeriesCollection();
//      dataset.addSeries(series2);
//      return dataset;
//   }
//
//   public static void main(String[] args) {
//      javax.swing.SwingUtilities.invokeLater(new Runnable() {
//         public void run() {
//            new EarnedValueDisplay().createAndShowGUI();
//         }
//      });
//   }
//
//}

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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(900,900);
		setVisible(true);

//		setLayout(new FlowLayout());

		
		
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
