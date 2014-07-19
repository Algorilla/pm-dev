/**
 * 
 */
package PModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * 
 *
 */
public class ActivityOnNodeNetwork {
	
    // Data
	private int PID = MainController.get().GetCurrentProject().getProjectID();
	private double scale;
	private ArrayList<Activity> activities;
	private TreeMap<Integer, ArrayList<Integer> >  forwardGraph;
	private TreeMap<Integer, ArrayList<Integer> > backwardGraph;
	
	public ActivityOnNodeNetwork(){
		
		activities = MainController.get().getActivityListForCurrentProject();
		
		forwardGraph = new TreeMap<Integer, ArrayList<Integer> >();
		backwardGraph = new TreeMap<Integer, ArrayList<Integer> >();
		
		buildForwardGraph();
		buildBackWardGraph();
		forwardPass();
		backwardPass();
		setFloats();
	}
	/**
	 * Helper method.
	 */
	private void buildForwardGraph() {
		ArrayList<Integer> tempDeps;
		
		for(Activity a : activities){
			tempDeps = MainController.get().getDependantActivities(a);
			forwardGraph.put(a.getNumber(), tempDeps);
		}		
	}
	/**
	 * Helper method.
	 */
	private void buildBackWardGraph() {
		ArrayList<Integer> tempDeps;
		
		for(Activity a : activities){
			tempDeps = MainController.get().getDependantActivities(a);
			for(Integer i : tempDeps){
				if(!backwardGraph.containsKey(i)){
					backwardGraph.put(i, new ArrayList<Integer>());
				}
				backwardGraph.get(i).add(a.getNumber());
			}
		}
	}
	/**
	 * 
	 * */
	private void findUnconditionedActivitiesAndSetEarliestStartAndFinish() {
		ArrayList<Integer> tempDeps;
		for(Activity a : activities){
			tempDeps = MainController.get().getDependantActivities(a);
			tempDeps.trimToSize();
			if(tempDeps.size() == 0){
				a.setEarliestStart(0);
				a.setEarliestFinish(a.getDuration());
			}
		}
	}
	/**
	 * 
	 * */
	private void findActivitiesWithoutDependantsAndSetLatestStartAndFinish() {
		ArrayList<Integer> tempDeps;
		boolean found = false;
		int number;
		
		for(Activity a : activities){
			number  = a.getNumber();
			for(Integer i : backwardGraph.keySet()){
				if(i == number){
					found = true;
					break;
				}
			}
			if(!found){
				a.setLatestFinish(a.getEarliestFinish());
				a.setLatestStart(a.getLatestFinish() - a.getDuration());
			}
			found = false;
		}
	}
	/**
	 * 
	 * */
	private void forwardPass(){
		findUnconditionedActivitiesAndSetEarliestStartAndFinish();
		Activity temp;
		double longestWait, wait;
		
		for (Entry<Integer, ArrayList<Integer>> l : forwardGraph.entrySet()){
			longestWait = 0;
			for(Integer i : l.getValue()){
				temp =  MainController.get().GetActivityFromID(PID, i);
				wait = temp.getEarliestFinish();
				if(wait > longestWait){
					longestWait = wait;
				}
			}
			temp = MainController.get().GetActivityFromID(PID, l.getKey());
			temp.setEarliestStart(longestWait);
			temp.setEarliestFinish(longestWait + temp.getDuration());
		}
	}
	/**
	 * 
	 */
	private void backwardPass() {
		findActivitiesWithoutDependantsAndSetLatestStartAndFinish();
		Activity temp, constrainer;
		double latestStart, start;
		
		for (Entry<Integer, ArrayList<Integer>> l : backwardGraph.descendingMap().entrySet()){
			latestStart = Integer.MAX_VALUE;
			constrainer = null;
			
			for(Integer i : l.getValue()){
				temp =  MainController.get().GetActivityFromID(PID, i);
				start = temp.getLatestStart();
				if(start < latestStart){
					latestStart = start;
					constrainer = temp;
				}
			}
			temp = MainController.get().GetActivityFromID(PID, l.getKey());
			temp.setLatestFinish(constrainer.getLatestStart());
			temp.setLatestStart(temp.getLatestFinish() - temp.getDuration());
		}
		
	}
	/**
	 * 
	 * */
	private void setFloats(){
		for(Activity a : activities){
			a.setDurationFloat(a.getLatestStart() - a.getEarliestStart());
		}
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
	public String createString(int len, char ch){
		char[] chars = new char[len];
		Arrays.fill(chars, ch);
		return new String(chars);
	}
	
	/**
	 * @return
	 */
	public String getGanttChart() {
		
		scale = 1.5;
		String artWork = "";
		String name, spaces, dashes, stars, intermediate;
		int nameLen, activityLength;//, aMid, nMid;
		int offset = 0;
		
		for(Activity a : activities){
			activityLength = (int)(a.getDuration() * scale);
//			if (activityLength % 2 == 0){
//				aMid = activityLength/2 + 1;
//			}
//			else{
//				aMid = activityLength/2;
//			}
			
			name = " " + a.getName() + " ";
			nameLen = name.length();
			
//			if(nameLen%2 == 0){
//				nMid = nameLen/2 + 1;
//			}
//			else{
//				nMid = nameLen/2;
//			}
			
			offset = (int)(a.getEarliestStart() * scale);
			
			spaces = createString(offset, ' ');
			dashes = createString((activityLength - nameLen)/2, '-');
			stars = createString((activityLength - nameLen)/2, '*');
			
			if(a.getDurationFloat() == 0){
				intermediate = spaces + stars + name + stars;
			}
			else{
				intermediate = spaces + dashes + name + dashes;
			}
			
			artWork += intermediate + "\n";
		}
		return artWork;
	}
	
	@Override
	public String toString(){
		String out = "";
		out += String.format("%-10s", "test");
		Activity temp;
		for (Entry<Integer, ArrayList<Integer>> l : forwardGraph.entrySet()){
			temp = MainController.get().GetActivityFromID(PID, l.getKey());
			out += temp.getName() + "\t --> ES(" + temp.getEarliestStart() + ") Duration(" + temp.getDuration() + ") EF(" + temp.getEarliestFinish() + ")\n"
					+ "\t --> LS(" + temp.getLatestStart() + ") Duration(" + temp.getDuration() + ") LF(" + temp.getLatestFinish() + ")\n";
		}
		return out;
	}
}
