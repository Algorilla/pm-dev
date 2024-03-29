package PModel;



public class MemberActivity 

{
 //  MID ( Member ID ) and Project-Activity ( Project ID, Number (of activity) )
    private int MID;
    
    private int PID ;
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
   	 * @param newProjectID New Project ID
   	 */
   	public int getNumber () 
   	{   		
   		return Number ;
   	}
   	
   	/**
   	 * Sets new Project Manager via their ID	
   	 * @param newManagerID New Manager's ID
   	 */
   	public void setNumber(int Number) {
   		this.Number = Number;
   	}




}
