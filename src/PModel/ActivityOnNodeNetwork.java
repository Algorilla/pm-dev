package PModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Activity On Node Networks are used to perform Critical Path Analysis.
 * Critical Paths are used in the generation of Gantt Charts.
 *
 */
public class ActivityOnNodeNetwork {
	
    // Data
//	private int PID = MainController.get().GetCurrentProject().getProjectID();
//	private int MAX_INDEX;
//	
//	private ArrayList<Activity> activities;
//	
//	private TreeMap<Integer, ArrayList<Integer> >  forwardGraph;
//	private TreeMap<Integer, ArrayList<Integer> > backwardGraph;
//	
//	// Dummy Activities
//	Activity dummyStart  = new Activity(PID, "Start","DummyStart",0,0,0,0,0);
//	Activity dummyFinish = new Activity(PID, "Finish","DummyFinish",0,0,0,0,0);
//	
//	public ActivityOnNodeNetwork(){
//		
//		activities = MainController.get().getActivityListForCurrentProject();
//		
//		forwardGraph = new TreeMap<Integer, ArrayList<Integer> >();
//		backwardGraph = new TreeMap<Integer, ArrayList<Integer> >();
//		
//		forwardGraph.put(0, new ArrayList<Integer>());
//		
//		dummyStart.setNumber(0);
//		dummyStart.setDuration(0);
//		
//		buildForwardGraph();
//		buildBackWardGraph();
//		
//		forwardPass();
//		backwardPass();
//		setFloats();
//	}
//	/**
//	 * Helper method.
//	 */
//	private void buildForwardGraph() {
//		ArrayList<Integer> tempDeps;
//		
//		for(Activity a : activities){
//			tempDeps = MainController.get().getDependantActivities(a);
//			tempDeps.trimToSize();
//			if(tempDeps.size() == 0){
//				tempDeps.add(0);
//				forwardGraph.put(a.getNumber(), tempDeps);
//			}
//			else{
//				forwardGraph.put(a.getNumber(), tempDeps);
//			}
//		}
//		MAX_INDEX = forwardGraph.lastKey() + 1;
//		dummyFinish.setNumber(MAX_INDEX);
//		dummyFinish.setDuration(0);
//		forwardGraph.put(MAX_INDEX, new ArrayList<Integer>());
//		
//		for(Activity a : activities){
//			int number = a.getNumber();
//			boolean found = false;
//			
//			for(Entry<Integer, ArrayList<Integer> > l : forwardGraph.entrySet()){
//				for(Integer i : l.getValue()){
//					if(i == number){
//						found = true;
//						break;
//					}
//				}
//			}
//			if(!found){
//				forwardGraph.get(MAX_INDEX).add(number);
//			}
//			
//		}
//		
//	}
//	/**
//	 * Helper method.
//	 */
//	private void buildBackWardGraph() {
//		ArrayList<Integer> tempDeps;
//		
//		for(Activity a : activities){
//			tempDeps = MainController.get().getDependantActivities(a);
//			for(Integer i : tempDeps){
//				if(!backwardGraph.containsKey(i)){
//					backwardGraph.put(i, new ArrayList<Integer>());
//				}
//				backwardGraph.get(i).add(a.getNumber());
//			}
//		}
//		backwardGraph.put(MAX_INDEX, new ArrayList<Integer>());
//		backwardGraph.put(0, new ArrayList<Integer>());
//		
//		for(Activity a : activities){
//			tempDeps = MainController.get().getDependantActivities(a);
//			tempDeps.trimToSize();
//			
//			if(tempDeps.size() == 0){
//				backwardGraph.get(0).add(a.getNumber());
//			}
//		}
//	}
//	/**
//	 * 
//	 * */
//	private void findUnconditionedActivitiesAndSetEarliestStartAndFinish() {
//		ArrayList<Integer> tempDeps;
//		
//		for(Activity a : activities){
//			tempDeps =  MainController.get().getDependantActivities(a);
//			tempDeps.trimToSize();
//			if(tempDeps.size() == 1 && tempDeps.get(0) == 0){
//				a.setEarliestStart(0);
//				a.setEarliestFinish(a.getDuration());
//			}
//		}
//	}
//	/**
//	 * 
//	 * */
//	private void findActivitiesWithoutDependantsAndSetLatestStartAndFinish() {
//		ArrayList<Integer> lasts = forwardGraph.get(MAX_INDEX);
//		Activity a;
//		
//		for(Integer i : lasts){
//			a = MainController.get().GetActivityFromID(PID, i);
//			a.setLatestFinish(dummyFinish.getEarliestStart());
//			a.setLatestStart(a.getLatestFinish() - a.getDuration());
//		}
//	}
//	/**
//	 * 
//	 * */
//	private void forwardPass(){
//		
//		findUnconditionedActivitiesAndSetEarliestStartAndFinish();
//		
//		Activity temp;
//		double longestWait, wait;
//		
//		for (Entry<Integer, ArrayList<Integer>> l : forwardGraph.entrySet()){
//			longestWait = 0;
//			
//			for(Integer i : l.getValue()){
//				if(i == 0){
//					temp = dummyStart;
//				}else if(i == MAX_INDEX){
//					temp = dummyFinish;
//				}else{
//					temp =  MainController.get().GetActivityFromID(PID, i);
//				}
//				
//				wait = temp.getEarliestFinish();
//				
//				if(wait > longestWait){
//					longestWait = wait;
//				}
//			}
//				
//			int number = l.getKey();
//			
//			if(number == 0){
//				temp = dummyStart;
//			}else if(number == MAX_INDEX){
//				temp = dummyFinish;
//			}else{
//				temp = MainController.get().GetActivityFromID(PID, number);
//			}
//			temp.setEarliestStart(longestWait);
//			temp.setEarliestFinish(longestWait + temp.getDuration());
//		}
//	}
//	/**
//	 * 
//	 */
//	private void backwardPass() {
//		findActivitiesWithoutDependantsAndSetLatestStartAndFinish();
//		
//		Activity temp, constrainer;
//		double latestStart, start;
//		
//		for (Entry<Integer, ArrayList<Integer>> l : backwardGraph.descendingMap().entrySet()){
//			
//			latestStart = Integer.MAX_VALUE;
//			constrainer = null;
//			
//			for(Integer i : l.getValue()){
//				if(i == 0){
//					temp = dummyStart;
//				}else if(i == MAX_INDEX){
//					temp = dummyFinish;
//				}else{
//					temp =  MainController.get().GetActivityFromID(PID, i);
//				}
//				
//				start = temp.getLatestStart();
//				
//				if(start < latestStart){
//					latestStart = start;
//					constrainer = temp;
//				}
//			}
//			int number = l.getKey();
//			if(number == 0){
//				temp = dummyStart;
//			}else if(number == MAX_INDEX){
//				temp = dummyFinish;
//				constrainer = dummyFinish;
//				
//			}else{
//				temp = MainController.get().GetActivityFromID(PID, number);
//				temp.setLatestFinish(constrainer.getLatestStart());
//				temp.setLatestStart(temp.getLatestFinish() - temp.getDuration());
//			}
//		}
//	}
//	/**
//	 * 
//	 * */
//	private void setFloats(){
//		for(Activity a : activities){
//			a.setDurationFloat(a.getLatestStart() - a.getEarliestStart());
//		}
//	}
//	/**
//	 * @param activities An ArrayList of Activities to be copied
//	 * @return temp Deep Copy of the ArrayList passed as an argument
//	 * */
//	public ArrayList<Activity> copy(ArrayList<Activity> activities) {
//		ArrayList<Activity> temp = new ArrayList<Activity>();
//		for(Activity a : activities){
//			temp.add(new Activity(a));
//		}
//		return temp;
//	}
//	public String createString(int len, char ch){
//		char[] chars = new char[len];
//		Arrays.fill(chars, ch);
//		return new String(chars);
//	}
//	private double calculateScale(){
//		double scale = 0;
//		double lf;
//		for(Activity a : activities){
//			lf = a.getLatestFinish();
//			if(lf > scale){
//				scale = lf;
//			}
//		}
//		
//		return 965 / (8* scale);
//	}
//	/**
//	 * @return
//	 */
//	public String getGanttChart() {
//		
//		double scale = calculateScale();
//		String artWork = "";
//		String name, spaces, dashes, stars, intermediate;
//		int nameLen, activityLength;
//		int offset = 0;
//		
//		for(Activity a : activities){
//			activityLength = (int)(a.getDuration() * scale);
//			
//			name = " " + a.getName() + " ";
//			nameLen = name.length();
//			
//			offset = (int)(a.getEarliestStart() * scale);
//			
//			spaces = createString(offset, ' ');
//			dashes = createString((activityLength - nameLen)/2, '-');
//			stars = createString((activityLength - nameLen)/2, '*');
//			
//			if(a.getDurationFloat() == 0){
//				intermediate = spaces + stars + name + stars;
//			}
//			else{
//				intermediate = spaces + dashes + name + dashes;
//			}
//			
//			artWork += intermediate + "\n";
//		}
//		return artWork;
//	}
//	
//	@Override
//	public String toString(){
//		String out = "";
//		int dummyStart = 0;
//		int dummyEnd = forwardGraph.lastKey();
//		int number;
//		Activity temp;
//		for (Entry<Integer, ArrayList<Integer>> l : forwardGraph.entrySet()){
//			number = l.getKey();
//			if(number != dummyStart && number != dummyEnd){
//				temp = MainController.get().GetActivityFromID(PID, number);
//				out += temp.getName() + "\t --> ES(" + temp.getEarliestStart() + ") Duration(" + temp.getDuration() + ") EF(" + temp.getEarliestFinish() + ")\n"
//					+ "\t --> LS(" + temp.getLatestStart() + ") Duration(" + temp.getDuration() + ") LF(" + temp.getLatestFinish() + ")\n";
//			}
//		}
//		return out;
//	}
}
