/**
 * 
 */
package PModel;

import java.util.ArrayList;

/**
 * 
 *
 */
public class ActivityOnNodeNetwork {
	
    // Data
	private ArrayList<Activity> activities;
	private ArrayList< ArrayList<Integer> > forwardGraph;
	private ArrayList< ArrayList<Integer> > backwardGraph;
	
	private ArrayList<Activity> tempActivities;
	
//	private Activity dummyStartNode;
//	private Activity dummyEndNode;
	
	
	public ActivityOnNodeNetwork(){
		
		activities = MainController.get().getActivityListForCurrentProject();
		
		forwardGraph = new ArrayList< ArrayList<Integer> >();
		backwardGraph = new ArrayList< ArrayList<Integer> >();
		
//		dummyStartNode 	= new Activity(MainController.get().GetCurrentProject().getProjectID(),
//									  "DummyStartNode", "PlaceHolder",
//									  0, 0, 0, 0, 0);
//		
//		dummyEndNode 	= new Activity(MainController.get().GetCurrentProject().getProjectID(),
//									   "DummyEndNode", "PlaceHolder",
//									   	0, 0, 0, 0, 0);
		
		buildForwardGraph();
		buildBackWardGraph();
		
	}
	/**
	 * 
	 */
	private void buildForwardGraph() {
		tempActivities = copy(activities);
		
		for(Activity a : activities){
			if(MainController.get().getDependantActivities(a).size() == 0){
				forwardGraph.get(0).add(a.getNumber());
//				tempActivities.remove(a));
			}
		}
		
	}
	/**
	 * 
	 */
	private void buildBackWardGraph() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param activities An ArrayList of Activities to be copied
	 * @return temp Deep Copy of the ArrayList passed as an argument
	 * */
	public ArrayList<Activity> copy(ArrayList<Activity> activities) {
		ArrayList<Activity> temp = new ArrayList<Activity>();
		for(Activity a : activities){
			temp.add(new Activity(a));
		}
		return temp;
	}
}
