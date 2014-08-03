/**
 * 
 */
package Analysis;

import java.util.ArrayList;

import PModel.Activity;
import PModel.MainController;

/**
 *
 */
public class Analyzer {
	
	private ArrayList<Activity> activities;
	MainController mc = MainController.get();
	
	private PertNetwork 	pertNetwork;
	private GanttNetwork 	ganttNetwork;
	 
	public Analyzer(ArrayList<Activity> activities){
		
		this.activities = activities;
		
		for(Activity a : activities){
			a.setDependents(mc.getDependantActivities(a));
			a.setPrecedents(mc.getPrecedantActivities(a));
		}
		

		this.ganttNetwork = GanttNetwork.getActivityNetwork(this.activities);
		this.pertNetwork  = PertNetwork.getArrowNetwork(this.activities);
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
