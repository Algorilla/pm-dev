package PModel;



/**
 * @author  Lee
 */
public class MemberActivity 

{
 //  MID ( Member ID ) and Project-Activity ( Project ID, Number (of activity) )
    /**
	 * @uml.property  name="mID"
	 */
    private int MID;
    
    /**
	 * @uml.property  name="pID"
	 */
    private int PID ;
    /**
	 * @uml.property  name="number"
	 */
    private int Number;
 
     public MemberActivity(int MID, int PID, int Number)
{

	 // CONSTRUCTOR 
	 
       this.MID = MID;
       this.PID=PID;
       this.Number=Number;
 }
       /**
   	 * Sets a new Project ID to an existing project
   	 * @param newProjectID New Project ID
   	 */
   	public int getMemberID () {   		
   		return MID ;
   	}
   	
   	/**
   	 * Sets new Project Manager via their ID	
   	 * @param newManagerID New Manager's ID
   	 */
   	public void setMemberID(int MID) {
   		this.MID = MID;
   	}
   
   
    /**
   	 * Sets a new Project ID to an existing project
   	 * @param newProjectID New Project ID
   	 */
   	public int getProjectID () 
   	{   		
   		return PID ;
   	}
   	
   	/**
   	 * Sets new Project Manager via their ID	
   	 * @param newManagerID New Manager's ID
   	 */
   	public void setProjectID(int PID) {
   		this.PID = PID;
   	}
   
    
    /**
	 * Sets a new Project ID to an existing project
	 * @param newProjectID   New Project ID
	 * @uml.property  name="number"
	 */
   	public int getNumber () 
   	{   		
   		return Number ;
   	}
   	
   	/**
	 * Sets new Project Manager via their ID	
	 * @param newManagerID   New Manager's ID
	 * @uml.property  name="number"
	 */
   	public void setNumber(int Number) {
   		this.Number = Number;
   	}




}
