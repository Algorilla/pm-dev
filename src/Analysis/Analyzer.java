/**
 * 
 */
package Analysis;

import java.util.ArrayList;

import PModel.Activity;
import PModel.MainController;
import PModel.Project;

/**
 *
 */
public class Analyzer {
	
	private Project 				project;
	private ArrayList<Activity> 	activities;
	MainController 					mc = MainController.get();;
	
	private PertNetwork 			pertNetwork;
	private GanttNetwork 			ganttNetwork;
	private EarnedValue				earnedValue;
	 
	public Analyzer(Project project, double daysSinceStart){
		
		this.project    = project;
		this.activities = mc.getActivityListForCurrentProject();
		this.project.setActivityList(activities);
		
		for(Activity a : activities){
			a.setDependents(mc.getDependantActivities(a));
			a.setPrecedents(mc.getPrecedantActivities(a));
		}
		

		this.ganttNetwork = GanttNetwork.getActivityNetwork(this.activities);
		this.pertNetwork  = PertNetwork.getArrowNetwork(this.activities);
		this.earnedValue  = new EarnedValue(project, 0.0);
	}

	/**
	 * @return the forwardActivityNetwork
	 */
	public GanttNetwork getGanttNetwork() {
		return ganttNetwork;
	}

	/**
	 * @return the pertNetwork
	 */
	public PertNetwork getPertNetwork() {
		return pertNetwork;
	}

}