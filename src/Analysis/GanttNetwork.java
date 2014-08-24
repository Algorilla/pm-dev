package Analysis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import PModel.Activity;

/**
 * The creation of a GanttNetwork sets the the Activity Start and Finish Dates
 * (Earliest and Latest) as well as the Duration and Float values for each
 * Activity in the Project.
 */
public class GanttNetwork {

	// Activities are Nodes, therefore the graph is essentially a list of
	// activities, the links between them are Activities and stored as the
	// activity Precedents, and Dependents. These are lists that are loaded into
	// the Activity by the MainController when the Analyzer is created.
	private ArrayList<Activity> activities;
	
	// These are 'dummy activities'. They exist in case the project has multiple
	// unconditioned activities or multiple final activities.
	private Activity start;
	private Activity finish;

	/**
	 * Constructor
	 * 
	 * @param activities
	 */
	public GanttNetwork(ArrayList<Activity> activities) {

		this.activities = activities;
		this.start = Activity.makeDummyStart();
		this.finish = Activity.makeDummyFinish();
	}

	/**
	 * A factory for setting up a Gantt Network.
	 * 
	 * @param activities
	 * @return GanttNetwork
	 */
	public static GanttNetwork getActivityNetwork(ArrayList<Activity> activities) {

		GanttNetwork network = new GanttNetwork(activities);

		// Add unconditioned Activities as Dependents on the dummyStart Node
		// and add the dummyStart as a dependent of them
		for (Activity a : network.getActivities()) {
			if (a.getPrecedents().size() == 0) {
				network.getStart().addDependent(a);
				a.addPrecedent(network.getStart());
			}
		}
		// Add Activities that have no dependents as Precedents of the
		// dummyFinish
		// and make dummyFinish depend on them
		for (Activity a : network.getActivities()) {
			if (a.getDependents().size() == 0) {
				network.getFinish().addPrecedent(a);
				a.addDependent(network.getFinish());
			}
		}

		// Ensure appropriate values in the dummyActivities
		network.getStart().setEarliestStart(0);
		network.getStart().setEarliestFinish(0);
		network.getStart().setDuration(0);
		network.getFinish().setDuration(0);

		// Add the dummyActivities to the Network
		network.activities.add(network.start);
		network.activities.add(network.finish);

		// Recursively update the Activity Values
		network.forwardPass(network.getStart());
		network.backwardPass(network.getFinish());

		// Use updated values to set Dates and Floats
		network.setDates();
		network.setFloats();

		return network;
	}

	/**
	 * Helper Function Sets the Earliest Activity Start and Finish Values
	 * 
	 * @param activity
	 * 
	 */
	private void forwardPass(Activity activity) {

		// Temporary variables
		double longestWait = 0, wait = 0;

		// Precedents of this Activity
		for (Activity pre : activity.getPrecedents()) {

			wait = pre.getEarliestFinish();

			if (wait > longestWait) {
				longestWait = wait;
			}
		}
		// Max Wait Time becomes Earliest Start
		activity.setEarliestStart(longestWait);
		activity.setEarliestFinish(longestWait + activity.getDuration());

		// Recursive Base Case
		if (activity.equals(this.finish)) {
			return;
		}

		// Continue Passing through the Network
		for (Activity dep : activity.getDependents()) {
			forwardPass(dep);
		}
	}

	/**
	 * Helper function. Sets the Latest Activity Start and Finish Values
	 * 
	 * @param activity
	 */
	private void backwardPass(Activity activity) {

		// Temporary variables
		double latestStart = Integer.MAX_VALUE, start;
		Activity constrainer = null;

		// Dependents of this Activity
		for (Activity dep : activity.getDependents()) {

			start = dep.getLatestStart();

			if (start < latestStart) {
				latestStart = start;
				constrainer = dep;
			}

		}

		// This is the Recursive Function's starting point. The pertinent values
		// are set during the forward pass.
		if (activity.equals(this.getFinish())) {
			// This is because the dummy finish has a duration of zero
			activity.setLatestFinish(activity.getEarliestFinish());
			activity.setLatestStart(activity.getEarliestStart());

			// Otherwise calculate latest start relative to duration.
		} else {
			activity.setLatestFinish(constrainer.getLatestStart());
			activity.setLatestStart(constrainer.getLatestStart()
					- activity.getDuration());
		}
		// Recursive Base Case
		if (activity.equals(this.getStart())) {
			return;
		}
		// Recursive Calls
		for (Activity pre : activity.getPrecedents()) {
			backwardPass(pre);
		}

	}

	/**
	 * Helper function. Sets duratioinFloat for each Activity
	 */
	private void setFloats() {
		for (Activity a : activities) {
			a.setDurationFloat(a.getLatestFinish() - a.getEarliestFinish());
		}
	}

	/**
	 * Gantt Display requires Date Objects. This helper function takes the times
	 * (type double) that are relative to the project time line and converts
	 * them into java Date Objects
	 */
	private void setDates() {

		Date projectStart = activities.get(1).getProject().getStartDate();
		Calendar c = Calendar.getInstance();
		Date eS, eF, lS, lF;

		for (Activity a : activities) {

			c.setTime(projectStart);
			c.add(Calendar.DATE, (int) a.getEarliestStart());
			eS = c.getTime();
			a.setES(eS);

			c.setTime(projectStart);
			c.add(Calendar.DATE, (int) a.getEarliestFinish());
			eF = c.getTime();
			a.setEF(eF);

			c.setTime(projectStart);
			c.add(Calendar.DATE, (int) a.getLatestStart());
			lS = c.getTime();
			a.setLS(lS);

			c.setTime(projectStart);
			c.add(Calendar.DATE, (int) a.getLatestFinish());
			lF = c.getTime();
			a.setLF(lF);
		}
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
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(Activity finish) {
		this.finish = finish;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		String output = "";

		for (Activity a : this.activities) {
			output += a.getName() + " {Start: " + a.getEarliestStart() + "/"
					+ a.getLatestStart() + "\tFinish: " + a.getEarliestFinish()
					+ "/" + a.getLatestFinish() + "\t Float: "
					+ a.getDurationFloat() + "}\n";

		}
		return output;
	}

}
