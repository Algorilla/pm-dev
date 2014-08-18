/**
 * 
 */
package Analysis;

import java.util.ArrayList;

import PModel.Activity;


/**
 * A MilestoneNode is part of an PertNetwork.
 * Arrows come in and out of Nodes
 */
public class MilestoneNode implements Comparable{

	private Integer name;
	private double 	targetDate;
	private double 	expectedDate;
	private double 	standardDeviation;

	private ArrayList<Activity> inArrows;
	private ArrayList<Activity> outArrows;
	
	private ArrayList<MilestoneNode> precedents;
	private ArrayList<MilestoneNode> dependents;
	
	public MilestoneNode(int n){
		name = n;
		inArrows   = new ArrayList<Activity>();
		outArrows  = new ArrayList<Activity>();
		precedents = new ArrayList<MilestoneNode>();
		dependents = new ArrayList<MilestoneNode>();
	}
	
	public void setTargetDate(){
		double latestDate = 0;
		
		for(Activity a: inArrows){
			if(a.getLatestFinish() > latestDate){
				latestDate = a.getLatestFinish();
			}
		}
		targetDate = latestDate;
	}
	
	public void addOutArrow(Activity a){
		this.outArrows.add(a);
	}
	
	public void addInArrow(Activity a){
		this.inArrows.add(a);
	}
	/**
	 * @return the precedents
	 */
	public ArrayList<MilestoneNode> getPrecedents() {
		return precedents;
	}

	/**
	 * @return the dependents
	 */
	public ArrayList<MilestoneNode> getDependents() {
		return dependents;
	}

	/**
	 * @param precedents the precedents to set
	 */
	public void setPrecedents(ArrayList<MilestoneNode> precedents) {
		this.precedents = precedents;
	}

	/**
	 * @param dependents the dependents to set
	 */
	public void setDependents(ArrayList<MilestoneNode> dependents) {
		this.dependents = dependents;
	}

	/**
	 * @return the inArrows
	 */
	public ArrayList<Activity> getInArrows() {
		return inArrows;
	}

	/**
	 * @return the outArrows
	 */
	public ArrayList<Activity> getOutArrows() {
		return outArrows;
	}

	/**
	 * @param inArrows the inArrows to set
	 */
	public void setInArrows(ArrayList<Activity> inArrows) {
		this.inArrows = inArrows;
	}

	/**
	 * @param outArrows the outArrows to set
	 */
	public void setOutArrows(ArrayList<Activity> outArrows) {
		this.outArrows = outArrows;
	}

	/**
	 * @return the name
	 */
	public Integer getName() {
		return name;
	}

	/**
	 * @return the targetDate
	 */
	public double getTargetDate() {
		return targetDate;
	}

	/**
	 * @return the expectedDate
	 */
	public double getExpectedDate() {
		return expectedDate;
	}

	/**
	 * @return the standardDeviation
	 */
	public double getStandardDeviation() {
		return standardDeviation;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(Integer name) {
		this.name = name;
	}

	/**
	 * @param targetDate the targetDate to set
	 */
	public void setTargetDate(double targetDate) {
		this.targetDate = targetDate;
	}

	/**
	 * @param expectedDate the expectedDate to set
	 */
	public void setExpectedDate(double expectedDate) {
		this.expectedDate = expectedDate;
	}

	/**
	 * @param standardDeviation the standardDeviation to set
	 */
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	
	public void addPrecedent(MilestoneNode n){
		this.precedents.add(n);
	}
	public void addDependent(MilestoneNode n){
		this.dependents.add(n);
	}

	@Override
	public String toString() {
		return this.getName().toString();
	}
	
	public String toStringVerbose() {
		
		String id   = "Milestone " + Integer.toString(name) + ":\n";
		
		String  tgt = "Target Date for this Milestone: " + targetDate + "\n",
				iAs = "Activities to Complete by this Milestone: ", 
				oAs = "Activities to Available to start after this Milestone: ";
		
		for(Activity a : this.inArrows){
			iAs += a.getName() + ", ";
		}
		if(iAs.length() > 2){
			iAs = iAs.substring(0, iAs.length()-2) + "\n\n";
		}
		
		for(Activity a : this.outArrows){
			oAs += a.getName() + ", ";
		}
		if(oAs.length() > 2){
			oAs = oAs.substring(0, oAs.length()-2) + "\n\n";
		}
		
		return id + iAs + oAs;
	}

	
	
	/**
	 * 
	 */
	@Override
	public int compareTo(Object o) {
		//TODO: compare by latest start date for sorting. Priority low.
		MilestoneNode other = (MilestoneNode)o;
		return this.name.compareTo(other.name);
	}
	
	/**
	 * Milestone Nodes should have unique names(ids).
	 * */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MilestoneNode other = (MilestoneNode) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
