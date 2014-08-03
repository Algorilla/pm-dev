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

	Integer name;

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

	@Override
	public String toString() {
		String pres = "";
		String deps = "";
		for(RegularNode n : precedents){
			pres += n.name + " ";
		}
		for(RegularNode n : dependents){
			deps += n.name + " ";
		}
		return Integer.toString(name) ;//+ ": pres[" + pres + "],\tdeps[" + deps + "]\n";
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
