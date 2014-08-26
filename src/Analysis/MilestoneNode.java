/**
 * 
 */
package Analysis;

import java.util.ArrayList;
import java.util.Date;

import Controller.ErrorController;
import PModel.Activity;


/**
 * A MilestoneNode is part of an PertNetwork.
 * Arrows come in and out of Nodes
 */
public class MilestoneNode implements Comparable{

	private Integer name;
	private Date 	targetDate;
	private double 	expectedDate;
	private double 	standardDeviation;

	private ArrayList<Activity> inArrows;
	private ArrayList<Activity> outArrows;
	
	private ArrayList<MilestoneNode> precedents;
	private ArrayList<MilestoneNode> dependents;
	
	private ErrorController ec = ErrorController.get();
	
	public MilestoneNode(int n){
		name = n;
		inArrows   = new ArrayList<Activity>();
		outArrows  = new ArrayList<Activity>();
		precedents = new ArrayList<MilestoneNode>();
		dependents = new ArrayList<MilestoneNode>();
		targetDate = new Date();
	}
	
	public void setTargetDate(){
		double latestDate = 0;
		Activity latest = null;
		for(Activity a: inArrows){
			if(a.getLatestFinish() > latestDate){
				latestDate = a.getLatestFinish();
				latest = a;
			}
		}
		if(latest == null){
			targetDate = this.getOutArrows().get(0).getES();
		}
		else{
			targetDate = latest.getLF();
		}
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
	public Date getTargetDate() {
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
	public void setTargetDate(Date targetDate) {
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
		return "Milestone " + this.getName().toString() + "\n";
	}
	
	public String toStringArrows(String type) {
		
		String  arrowString = "";
		ArrayList<Activity> arrows;
		
		if(type.equals("in")){
			arrowString = "Activities to Complete by this Milestone: "; 
			if(this.hasInArrows()){
				arrows = this.getInArrows();
			}
			else{
				arrows = new ArrayList<Activity>();
				return "No in arrows";
			}
			
		} else if(type.equals("out")){
			arrowString  = "Activities to Available to start after this Milestone: ";
			if(this.hasOutArrows()){
				arrows = this.getOutArrows();
			}
			else{
				arrows = new ArrayList<Activity>();
				return "No out arrows";
			}
			
		} else {
//			ec.showError("You didn't enter 'in' or 'out' when you should've.");
			return null;
		}
		
		for(Activity a : arrows){
			arrowString += a.getName() + ", ";
		}
		if(arrowString.endsWith(", ")){
			arrowString = arrowString.substring(0, arrowString.length()-2);
		}
	
		return arrowString;
	}
	
	/**
	 * @return
	 */
	private boolean hasOutArrows() {
		return !outArrows.isEmpty();
	}

	/**
	 * @return
	 */
	private boolean hasInArrows() {
		return !inArrows.isEmpty();
	}

	public String toStringTargetDate(){
		if(this.hasTargetDate()){
			return targetDate.toString();
		}
		return "No Target Date";
	}
	
	public boolean hasTargetDate(){
		return targetDate != null;
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
	 */
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
