package PModel;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;


/**
 * The abstract class from which all manageable objects are derived,
 * including Projects and Activities.
 * @author Alex Huot
 */
public abstract class Manageable
{

	private String name;
	private String description;
	private double plannedValue;
	private boolean status;
	private Set<Member> memberList;

	/**
	 * Sole constructor for <tt>Manageable</tt>
	 * @param name Name of Manageable object
	 * @param description Description of object
	 */
	public Manageable(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.status = false;
	}
	
	/**
	 * Sets Manageable's name
	 * @param newName New name
	 */
	public void setName(String newName) {
		name = newName;
	}
	
	/**
	 * Sets description of Manageable object
	 * @param newDescr New description
	 */
	public void setDescr(String newDescr) {
		description = newDescr;
	}
	/**
	 * Sets Planned Value of the Manageable Entity
	 * @param newPlannedValue New Planned Value
	 *  */
	public void setPlannedValue(double newPlannedValue) {
		this.plannedValue = newPlannedValue;
	}
	/** 
	 * Sets the status of the Manageable
	 * @param newStatus Updated Status value
	 * */
	public void setStatus(boolean newStatus) {
		status = newStatus;
	}
	
	/**
	 * Returns Manageable's name
	 * @return Manageable's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns Manageable's Description
	 * @return Manageable's Description
	 */
	public String getDescr() {
		return description;
	}
	/**
	 * Returns the PlannedValue (projected budget) of the Manageable entity
	 * @return Manageable's Planned Value*/
	public double getPlannedValue() {
		return plannedValue;
	}
	/**
	 * Returns Manageable's Status
	 * @return Manageable's Status
	 */
	public boolean getStatus() {
		return status;
	}
	/**
	 * Returns Manageable's Member List
	 * @return Manageable's Member List
	 */
	public List<Member> getMemberList() {
		// TODO implement me
		return null;	
	}
	
	/**
	 * Adds a new member to the Manageable's list
	 * @param newMember Member to add
	 * @return Addition status ( success or fail )
	 */
	public boolean addMember(Member newMember) {
		// TODO implement me
		return false;	
	}
	
	/**
	 * Removes a member from Manageable's Member List
	 * @return Remove Status ( success or fail )
	 */
	public boolean removeMember(Member memberToDel) {
		// TODO implement me
		return false;	
	}

	@Override
    public String toString(){
      return name;
    }
}

