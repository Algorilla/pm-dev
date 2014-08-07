/**
 * 
 */
package Analysis;

import java.util.ArrayList;

import PModel.Activity;

/**
 *
 */
public class GanttNetwork {
	
	private ArrayList<Activity> activities;
	private Activity	 		start;
	private Activity	 		finish;
//	private ActivityGraph 		graph;
	
	public GanttNetwork(ArrayList<Activity> activities){
		
		this.activities = activities;
		this.start 		= Activity.makeDummyStart();
		this.finish 	= Activity.makeDummyFinish();
//		this.graph 		= new ActivityGraph();
				
	}
	
	public static GanttNetwork getActivityNetwork(ArrayList<Activity> activities){
		
		GanttNetwork fnn = new GanttNetwork(activities);
		
		for(Activity a : fnn.getActivities()){
			if(a.getPrecedents().size() == 0){
				fnn.getStart().addDependent(a);
				a.addPrecedent(fnn.getStart());
			}
		}
		
		for(Activity a : fnn.getActivities()){
			if(a.getDependents().size() == 0){
				fnn.getFinish().addPrecedent(a);
				a.addDependent(fnn.getFinish());
			}
		}
		
		fnn.getStart().setEarliestStart(0);
		fnn.getStart().setEarliestFinish(0);
		fnn.getStart().setDuration(0);
		fnn.getFinish().setDuration(0);
		
		fnn.activities.add(fnn.start);
		fnn.activities.add(fnn.finish);
		
		fnn.forwardPass(fnn.getStart());
		fnn.backwardPass(fnn.getFinish());
		
		fnn.setFloats();
		
		return fnn;
	}


	/**
	 * @return the activities
	 */
	public ArrayList<Activity> getActivities() {
		return activities;
	}

	/**
	 * @return the start
	 */
	public Activity getStart() {
		return start;
	}

	/**
	 * @return the finish
	 */
	public Activity getFinish() {
		return finish;
	}

	/**
	 * @param finish the finish to set
	 */
	public void setFinish(Activity finish) {
		this.finish = finish;
	}

	/**
	 * @param activity 
	 * 
	 */
	private void forwardPass(Activity activity) {
		
		double longestWait = 0, wait = 0;

		for(Activity pre : activity.getPrecedents()){
			wait = pre.getEarliestFinish();
			
			if(wait > longestWait){
				longestWait = wait;
			}
		}
		
		activity.setEarliestStart(longestWait);
		activity.setEarliestFinish(longestWait + activity.getDuration());
		
		if(activity.equals(this.finish)){
			return;
		}
		
		for(Activity dep : activity.getDependents()){
			forwardPass(dep);
		}
	}
	/**
	 * @param activity
	 */
	private void backwardPass(Activity activity) {
		
		double latestStart = Integer.MAX_VALUE, start;
		Activity constrainer = null;
		
		for(Activity dep : activity.getDependents()){
			
			start = dep.getLatestStart();
			
			if(start < latestStart){
				latestStart = start;
				constrainer = dep;
			}
			
		}
		if(activity.equals(this.getFinish())){
			activity.setLatestFinish(activity.getEarliestFinish());
			activity.setLatestStart(activity.getEarliestStart());
		}
		else{
			activity.setLatestFinish(constrainer.getLatestStart());
			activity.setLatestStart(constrainer.getLatestStart() - activity.getDuration());
		}
		
		
		if(activity.equals(this.getStart())){
			return;
		}
		
		for(Activity pre : activity.getPrecedents()){
			backwardPass(pre);
		}
		
		
	}
	
	public void setFloats(){
		for(Activity a : activities){
			a.setDurationFloat(a.getLatestFinish() - a.getEarliestFinish());
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String output = "";
		
		for(Activity a : this.activities){
			output += a.getName() + " {Start: " + a.getEarliestStart() + "/" + a.getLatestStart() +
					"\tFinish: " +a.getEarliestFinish() + "/" + a.getLatestFinish() + "\t Float: " + a.getDurationFloat()+"}\n";
			
		}
		return output;
	}



}
