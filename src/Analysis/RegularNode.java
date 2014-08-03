/**
 * 
 */
package Analysis;

import java.util.ArrayList;

import PModel.Activity;


/**
 *
 */
public class RegularNode implements Comparable{

	private Integer name;
	private double 	targetDate;
	private double 	expectedDate;
	private double 	standardDeviation;

	private ArrayList<Activity> inArrows;
	private ArrayList<Activity> outArrows;
	
	private ArrayList<RegularNode> precedents;
	private ArrayList<RegularNode> dependents;
	
	public RegularNode(int n){
		name = n;
		inArrows   = new ArrayList<Activity>();
		outArrows  = new ArrayList<Activity>();
		precedents = new ArrayList<RegularNode>();
		dependents = new ArrayList<RegularNode>();
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
	public ArrayList<RegularNode> getPrecedents() {
		return precedents;
	}

	/**
	 * @return the dependents
	 */
	public ArrayList<RegularNode> getDependents() {
		return dependents;
	}

	/**
	 * @param precedents the precedents to set
	 */
	public void setPrecedents(ArrayList<RegularNode> precedents) {
		this.precedents = precedents;
	}

	/**
	 * @param dependents the dependents to set
	 */
	public void setDependents(ArrayList<RegularNode> dependents) {
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
	
	public void addPrecedent(RegularNode n){
		this.precedents.add(n);
	}
	public void addDependent(RegularNode n){
		this.dependents.add(n);
	}

	@Override
	public String toString() {
		return this.getName().toString();
	}
	
	public String toStringVerbose() {
		
		String id = "Milestone " + Integer.toString(name) + ":\n";
		
		String  iAs = "Activities to Complete by this Milestone:\n", 
				oAs = "Activities to Available to start after this Milestone:\n";
		
		for(Activity a : this.inArrows){
			iAs += a.getName() + ", ";
		}
		if(iAs.length() > 2){
			iAs = iAs.substring(0, iAs.length()-2) + "\n";
		}
		
		for(Activity a : this.outArrows){
			oAs += a.getName() + ", ";
		}
		if(oAs.length() > 2){
			oAs = oAs.substring(0, oAs.length()-2) + "\n";
		}
		
		return id + iAs + oAs;
	}

	
	
	/**
	 * 
	 */
	@Override
	public int compareTo(Object o) {
		RegularNode other = (RegularNode)o;
		return this.name.compareTo(other.name);
	}
	
	/**
	 * 
	 * */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegularNode other = (RegularNode) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
