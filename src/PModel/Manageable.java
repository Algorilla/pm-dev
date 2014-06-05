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
	private Date startDate;
	private Date deadline;
	private int length;
	private boolean status;
	private Set<Member> memberList;

	/**
	 * Sole constructor for <tt>Manageable</tt>
	 * @param name Name of Manageable object
	 * @param description Description of object
	 * @param start Start date of object
	 * @param deadline Deadline of object
	 * @param length Projected length to completion of object in days
	 */
	public Manageable(String name, String description, Date start, Date deadline, int length) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = start;
		this.deadline = deadline;
		this.length = length;
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
	 * Sets start date for Manageable Object
	 * @param newStart New start date
	 */
	public void setStart(Date newStart) {
		startDate = newStart;
	}
	
	/**
	 * Sets deadline for Manageable object
	 * @param newDeadline New deadline
	 */
	public void setDeadline(Date newDeadline) {
		deadline = newDeadline;	
	}
	
	/**
	 * Sets projected length for Manageable object
	 * @param newLength New projected length in days
	 */
	public void setLength(int newLength) {
		length = newLength;
	}
	
	/**
	 * Sets Manageable's status as Active, Completed, Late or Rush 
	 * @param newStatus New status
	 */
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
	 * Returns Manageable's Start Date
	 * @return Manageable's Start Date
	 */
	public Date getStart() {
		return startDate;	
	}
	
	/**
	 * Returns Manageable's Deadline
	 * @return Manageable's Deadline
	 */
	public Date getDeadline() {
		return deadline;
	}
	
	/**
	 * Returns Manageable's Description
	 * @return Manageable's Description
	 */
	public int getLength() {
		return length;
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

