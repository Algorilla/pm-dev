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
	
	private ArrowNetwork 	arrowNetwork;
	private ActivityNetwork activityNetwork;
	 
	public Analyzer(ArrayList<Activity> activities){
		
		this.activities = activities;
		
		for(Activity a : activities){
			a.setDependents(mc.getDependantActivities(a));
			a.setPrecedents(mc.getPrecedantActivities(a));
		}
		
		this.arrowNetwork 	 = ArrowNetwork.getArrowNetwork(this.activities);
		this.activityNetwork = ActivityNetwork.getActivityNetwork(this.activities);
	}

	/**
	 * @return the forwardActivityNetwork
	 */
	public ActivityNetwork getForwardActivityNetwork() {
		return activityNetwork;
	}

	/**
	 * @return the forwardArrowNetwork
	 */
	public ArrowNetwork getForwardArrowNetwork() {
		return arrowNetwork;
	}

}
