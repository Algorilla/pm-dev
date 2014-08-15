package Controller;

import JFrames.TeamMemberView;
import JFrames.UserInterface;
import PModel.Activity;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.text.SimpleDateFormat;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

/**
 * Main Controller class for Project Management Software, following MVC design pattern. 
 * Controls all manipulation of data in database, and reflects changes via Swing GUI.
 * @author  Robert Wolfstein
 */
public class MainController {

	// Singleton design pattern
	private static MainController self = new MainController();
    public static MainController get() { return self; }
	
    // Data
    private ArrayList<Member> Members = new ArrayList<Member>();
    private ArrayList<Project> Projects = new ArrayList<Project>();
    private ArrayList<Activity> Activities = new ArrayList<Activity>();
    private ArrayList<MemberActivity> MemberActivities = new ArrayList<MemberActivity>();
    
    // SQLite DB connection data
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	// Session variables
	Member currentUser;
	Project currentProject;
	
	// Date format
	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	
	// Assisting Objects
	
	
		
	/**
	* Initializes controller by connecting 
	* to the DB and loading the data into memory.
	*/
	private MainController()
	{
		conn = SQLiteDBConnection.ConnectDb();
		loadData();
	}
	
	/**
	* Loads Database into local memory.
	*/
	public void loadData()
	{
		String sqlMembers = "select * from Members";
		String sqlProjects = "select * from Projects";
		String sqlActivities = "select * from Activities";
		String sqlMemberActivities = "select * from MemberActivities";
		try{
			pst = conn.prepareStatement(sqlMembers);
			rs = pst.executeQuery();
			while(rs.next()){
				Member member = new Member(
						rs.getString("Name"),
						rs.getString("Type"),
						rs.getString("username"),
						rs.getString("password")						
						);
				member.setMemberID(rs.getInt("MID"));
				Members.add(member);
			}									
			rs.close();
			pst.close();
			pst = conn.prepareStatement(sqlProjects);
			rs = pst.executeQuery();
			while(rs.next()){
				Project project = new Project(
						rs.getInt("ManagerID"),
						rs.getString("Name"),
						rs.getString("Description"),
						rs.getDate("StartDate"),
						rs.getDouble("PercentComplete"),
						rs.getDouble("BudgetAtCompletion"),
						rs.getDouble("PercentScheduledForCompletion"),
						rs.getDouble("ActualCost"),
						rs.getDouble("EarnedValue"),
						rs.getDouble("CostVariance"),
						rs.getDouble("ScheduleVariance"),
						rs.getDouble("CostPerformanceIndex"),
						rs.getDouble("SchedulePerformanceIndex"),
						rs.getDouble("EstimateAtCompletion"),
						rs.getDouble("EstimateToComplete")
						);
				project.setProjectID(rs.getInt("PID"));
				Projects.add(project);
			}									
			rs.close();
			pst.close();
			pst = conn.prepareStatement(sqlActivities);
			rs = pst.executeQuery();
			
			while(rs.next()){
				Activity activity = new Activity(
						rs.getInt("PID"),
						rs.getString("Name"),
						rs.getString("Description"),
						rs.getDouble("PlannedValue"),
						rs.getDouble("MostLikelyTimeToCompletion"),
						rs.getDouble("OptimisticTimeToCompletion"),
						rs.getDouble("PessimisticTimeToCompletion"),
						rs.getDouble("TargetCompletionDate"),
						rs.getDouble("PercentComplete"),
						rs.getDouble("ActualCost"),
						rs.getBoolean("Status")
						);
				activity.setProjectID(rs.getInt("PID"));
				activity.setNumber(rs.getInt("Number"));
				Activities.add(activity);
			}									
			rs.close();
			pst.close();			
			pst = conn.prepareStatement(sqlMemberActivities);
			rs = pst.executeQuery();
			while(rs.next()){
            MemberActivity ma = new  MemberActivity(
						rs.getInt("MID"),
						rs.getInt("PID"),
						rs.getInt("Number")
						);				
            	MemberActivities.add(ma);
			}									
			rs.close();
			pst.close();	
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}
	}
	
	/**
	* Returns a reference to the current user.
	*
	* @return The current program user
	*/
	public Member getCurrentUser()
	{
		return currentUser;
	}
	
	/**
	* Returns a reference to the current project.
	*
	* @return The currently loaded project
	*/
	public Project getCurrentProject()
	{
		return currentProject;	
	}
	
	public void closeCurrentProject()
	{
		currentProject = null;
	}
	
	/**
	* Logs the user in.
	*
	* @param username Username
	* @param password Password
	* @return Login status ( success or fail )
	*/
	public boolean login(String username,String password)
	{
		for (Member member : Members)
		{
		    if (member.getUserName().equals(username) && member.getPassword().equals(password))
		    {
				currentUser = member;								
				if (currentUser.getType().equals("manager"))
				{
					UserInterface userAccount = new UserInterface(100,100,1000,600,"",username);
					userAccount.setVisible(true);				
					return true;
				}
				else
				{
					TeamMemberView memberView = new TeamMemberView();
					memberView.setVisible(true);
					return true;
				}
		    }			
		}
		//JOptionPane.showMessageDialog(null, "Username and password combination is not correct.");	
		ErrorController.get().AddError("Username and password combination is not correct.");
		ErrorController.get().DisplayErrors();
		return false;
	}
	
	/**
	* Returns a list of projects for the currently signed in user.
	*
	* @return A list of projects for the currently signed in user.
	*/	
	public List getProjectList()
	{
		final List projectList = new List();	
		for (Project project : Projects)
		    if (project.getManagerID() == currentUser.getMemberID())
				projectList.add(project.getName());
		return projectList;	
	}
	
	/**
	* Opens a project selected in the OpenProject list
	*
	* @param name Name of project to open
	*/	
	public void openProject(String name)
	{
		for (Project project : Projects)
		    if (project.getName() == name)
				currentProject = project;
	}
	
	/**
	* Display project and its data in the GUI
	*
	* @param table
	*/	
	public void getActivityList(JTable table)
	{
		int pid = currentProject.getProjectID();
		String sql = "select * from Activities  where PID = ?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));				
			pst.execute();
			pst.close();
		}catch(Exception ex){
			//JOptionPane.showMessageDialog(null,ex);	
		}			
	}
	/**
	* Display the Activities to which a Member has been assigned.
	*
	* @param table The JTable into which the results are stored
	*/	
	public void getActivityListForCurrentTeamMember(JTable table)
	{

		int mid = currentUser.getMemberID();
		String sql = "select Activities.PID, Activities.Number, Activities.Name" +
					" from Activities, MemberActivities" +
					"  where MemberActivities.PID = Activities.PID   AND " +
					" 	 	 MemberActivities.Number = Activities.Number   AND" +
					"  		 MemberActivities.MID = ?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, mid);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));				
			pst.execute();
			pst.close();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}
	}
	/**
	 *  
	 * */
	public ArrayList<Activity> getActivityListForCurrentProject()
	{
		final ArrayList<Activity> activityList = new ArrayList<Activity>();	
		for (Activity activity : Activities)
		    if (activity.getProjectID() == currentProject.getProjectID())
				activityList.add(activity);
		return activityList;	
	}
	
	
	//TODO: put this some place more appropriate
	/**
	 * Fills comboBox
	 * @param comboBox comboBox to fill
	 */
	public void Fillcombo(JComboBox comboBox){
		int pid = currentProject.getProjectID();
		String sql = "select * from Activities  where PID = ?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			rs = pst.executeQuery();
			
			while(rs.next()){
				String name = rs.getString("Name");
				comboBox.addItem(name);
			}					
			pst.execute();
			pst.close();
		}catch(Exception ex){
			//JOptionPane.showMessageDialog(null,ex);	
		}					
	}
	//TODO: put this some place more appropriate
	/**
	 * MouseClickTable (Description to be filled)
	 * @param table A JTable
	 */
	public void mouseClickTable(JTable table){
		int row = table.getSelectedRow();
		int pid = currentProject.getProjectID();		
		try{			
			String table_click = (table.getModel().getValueAt(row, 2)).toString();	
			String sql = "select * from Activities  where PID = ? and Name = ?";
			
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			pst.setString(2, table_click);
			rs = pst.executeQuery();

			
			pst.execute();
			pst.close();
		}catch(Exception ex){
			//JOptionPane.showMessageDialog(null,ex);	
		}			
		
	}
	
	/**
	* Returns a reference to an activity in local memory based on the needed identifiers.
	*
	* @param PID Project ID 
	* @param number Activity Number
	* @return A reference to an activity in local memory
	*/	
	public Activity getActivityFromID(int PID,int number)
	{
		for (Activity activity : Activities)
		    if (activity.getProjectID() == PID && activity.getNumber() == number)
		    	return activity;
		return null;
	}
	
	/**
	* Creates a user.
	*
	* @param member Member object from which to create user
	* @return The created user
	*/	
	public Member createMember(Member member)
	{
		if (!member.getName().equals("") && member.getName() != null &&
			!member.getType().equals("") && member.getType() != null &&
			!member.getUserName().equals("") && member.getUserName() != null &&
			!member.getPassword().equals("") && member.getPassword() != null
			)
		{
			for (Member theMember : Members)
			    if (theMember.getUserName().equals(member.getUserName()))
					ErrorController.get().AddError("Member with Username "+theMember.getUserName()+" already exists.");
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return null;
			}			
			String sql = "insert into Members (Name,Type,Username,Password)values(?,?,?,?)";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString(1, member.getName());
				pst.setString(2, member.getType());
				pst.setString(3, member.getUserName());
				pst.setString(4, member.getPassword());
				pst.execute();
				pst.close();
				sql = "select max(MID) from Members";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				member.setMemberID(rs.getInt(1));
				Members.add(member);
				return member;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,ex);	
			}		
		}
		return null;
	}
	
	/**
	* Updates a user's information into DB.
	*
	* @param member The Member to update
	* @return Update status ( success or fail )
	*/	
	public boolean updateMember(Member member)
	{
		if (!member.getName().equals("") && member.getName() != null &&
			!member.getType().equals("") && member.getType() != null &&
			!member.getUserName().equals("") && member.getUserName() != null &&
			!member.getPassword().equals("") && member.getPassword() != null
			)
		{
			int count = 0;
			for (Member theMember : Members)
			    if (theMember.getUserName().equals(member.getUserName()))
			    	count++;
			if (count > 1)
				ErrorController.get().AddError("Member with Username "+member.getUserName()+" already exists.");			
			String sql;
			try {
				sql = "select count(*) from Members where MID = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1,member.getMemberID());
				rs = pst.executeQuery();
				if(rs.getInt(1) == 0){
					ErrorController.get().AddError("Member with ID "+member.getMemberID()+" does not exist.");
				}
			} catch (SQLException ex) {}			
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return false;
			}			
			sql = "update Members set name=?,type=?,username=?,password=? where MID = ?";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString(1, member.getName());
				pst.setString(2, member.getType());
				pst.setString(3, member.getUserName());
				pst.setString(4, member.getPassword());
				pst.setInt(5, member.getMemberID());
				pst.execute();
				pst.close();
				/*int x=0;
				for (Member oldMember : Members)
				    if (oldMember.getMemberID() == member.getMemberID())
						break;
				    else
				    	x++;				
				Members.set(x,member);*/
				return true;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,ex);	
			}
		}		
		return false;
	}
	
	/**
	* Deletes a user from DB.
	*
	* @param MID ID of Member to delete
	* @return Deletion status ( success or fail )
	*/	
	// TODO: Ensure referential integrity such that objects in other relations do not refer to deleted Users
	public boolean deleteMember(int MID) 
	{
		int x = 0;
		for (Member member : Members)
		    if (member.getMemberID() == MID)
				break;
		    else
		    	x++;
		return deleteMember(Members.get(x));
	}
	
	/**
	 * Deletes a user
	 * 
	 * @param member Member object to delete
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteMember(Member member)
	{
		String sql;
		try {
			sql = "select count(*) from Members where MID = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1,member.getMemberID());
			rs = pst.executeQuery();
			if(rs.getInt(1) == 0){
				ErrorController.get().AddError("Member with ID "+member.getMemberID()+" does not exist.");
			}
		} catch (SQLException ex) {}		
		if (ErrorController.get().ErrorsExist()) {
			ErrorController.get().DisplayErrors();
			return false;
		}		
		sql = "delete from Members where MID = ?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, member.getMemberID());
			pst.execute();
			pst.close();
			int x=0;
			for (Member oldMember : Members)
			    if (oldMember.getMemberID() == member.getMemberID())
					break;
			    else
			    	x++;				
			Members.remove(x);
			return true;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}	
		return false;
	}
	
	/**
	* Creates a project.
	*
	* @param project Project object to create
	* @return Created project
	*/	
	public Project initializeProject(Project project)
	{
		if (!project.getName().equals("") && project.getName() != null &&
			!project.getDescr().equals("") && project.getDescr() != null
			)
		{		
				String sql;
				try {
					// TODO: Change to use local memory rather than DB
					
					sql = "select * from Projects where Name = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, project.getName());
					rs = pst.executeQuery();
					while(rs.next()){
						ErrorController.get().AddError("Project with Name \""+project.getName()+"\" already exists.");
					}
					sql = "select count(*) from Members where MID = ?";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, project.getManagerID());
					rs = pst.executeQuery();
					if(rs.getInt(1) == 0){
						ErrorController.get().AddError("Manager with ID "+project.getManagerID()+" does not exist.");	
					}
				} catch (SQLException ex) {}				
				if (ErrorController.get().ErrorsExist()) {
					ErrorController.get().DisplayErrors();
					return null;
				}	
				sql = "insert into Projects (Name,Description,StartDate,PercentComplete,BudgetAtCompletion," +
											"PercentScheduledForCompletion,ActualCost,EarnedValue,CostVariance," +
											"ScheduleVariance,CostPerformanceIndex,SchedulePerformanceIndex," +
											"EstimateAtCompletion,EstimateToComplete,ManagerID"
											 +")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,  project.getName());
					pst.setString(2,  project.getDescr());
					pst.setDate(  3,  project.getStartDate());
					pst.setDouble(4,  project.getPercentComplete());
					pst.setDouble(5,  project.getBudgetAtCompletion());
					pst.setDouble(6,  project.getPercentScheduledForCompletion());
					pst.setDouble(7,  project.getActualCost());
					pst.setDouble(8,  project.getEarnedValue());
					pst.setDouble(9,  project.getCostVariance());
					pst.setDouble(10, project.getScheduleVariance());
					pst.setDouble(11, project.getCostPerformanceIndex());
					pst.setDouble(12, project.getSchedulePerformanceIndex());
					pst.setDouble(13, project.getEstimateAtCompletion());
					pst.setDouble(14, project.getEstimateToComplete());
					pst.setInt(   15, project.getManagerID());
					
					pst.execute();
					pst.close();
					sql = "select max(PID) from Projects";
					pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					project.setProjectID(rs.getInt(1));
					Projects.add(project);
					return project;
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null,ex);	
				}
			}		
			return null;

			
	}
	
	/**
	* Updates a project's information into DB.
	*
	* @param project Project object o update
	* @return Update status ( success or fail )
	*/	
	public boolean updateProject(Project project)
	{		
		if (!project.getName().equals("") && project.getName() != null &&
			!project.getDescr().equals("") && project.getDescr() != null
			)
		{
			String sql;
			try {
				sql = "select count(*) from Projects where PID = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1,project.getProjectID());
				rs = pst.executeQuery();
				if(rs.getInt(1) == 0){
					ErrorController.get().AddError("Project with ID "+project.getProjectID()+" does not exist.");
				}
				sql = "select count(*) from Members where MID = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, project.getManagerID());
				rs = pst.executeQuery();
				if(rs.getInt(1) == 0){
					ErrorController.get().AddError("Manager with ID "+project.getManagerID()+" does not exist.");	
				}
				sql = "select count(*) from Projects where Name = ? and PID <> ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, project.getName());
				pst.setInt(2, project.getProjectID());
				rs = pst.executeQuery();
				if(rs.getInt(1) != 0){
					ErrorController.get().AddError("Another project with the Name "+project.getName()+" already exists.");
				}
				
			} catch (SQLException ex) {	}			
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return false;
			}			
			sql = "update Projects set Name=?,Description=?,StartDate=?,PercentComplete=?,BudgetAtCompletion=?," +
											"PercentScheduledForCompletion=?,ActualCost=?,EarnedValue=?," +
											"CostVariance=?,ScheduleVariance=?,CostPerformanceIndex=?,SchedulePerformanceIndex=?," +
											"EstimateAtCompletion=?,EstimateToComplete=?,ManagerID=? where PID = ?";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString(1,  project.getName());
				pst.setString(2,  project.getDescr());
				pst.setDate  (3,  project.getStartDate());
				pst.setDouble(4,  project.getPercentComplete());
				pst.setDouble(5,  project.getBudgetAtCompletion());
				pst.setDouble(6,  project.getPercentScheduledForCompletion());
				pst.setDouble(7,  project.getActualCost());
				pst.setDouble(8,  project.getEarnedValue());
				pst.setDouble(9,  project.getCostVariance());
				pst.setDouble(10, project.getScheduleVariance());
				pst.setDouble(11, project.getCostPerformanceIndex());
				pst.setDouble(12, project.getSchedulePerformanceIndex());
				pst.setDouble(13, project.getEstimateAtCompletion());
				pst.setDouble(14, project.getEstimateToComplete());
				pst.setInt   (15, project.getManagerID());
				pst.setInt   (16,project.getProjectID());
				
				pst.execute();
				pst.close();
				/*int x=0;
				for (Project oldProject : Projects)
				    if (oldProject.getProjectID() == project.getProjectID())
						break;
				    else
				    	x++;				
				Projects.set(x,project);*/
				return true;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,ex);	
			}
		}
		return false;
	}
	
	/**
	* Deletes a project from DB.
	*
	* @param PID Project's ID
	* @return Deletion status ( success or fail )
	*/	
	public boolean deleteProject(int PID) 
	{
		int x = 0;
		for (Project project : Projects)
		    if (project.getProjectID() == PID)
				break;
		    else
		    	x++;
		return deleteProject(Projects.get(x));
	}
	
	/**
	 * Deletes a project from DB
	 * 
	 * @param projectName Project's name
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteProject(String projectName) 
	{
		int x = 0;
		for (Project project : Projects)
		    if (project.getName() == projectName)
				break;
		    else
		    	x++;
		return deleteProject(Projects.get(x));
	}
	
	/**
	 * Deletes a project from DB
	 * 
	 * @param project Project object
	 * @return Deletion status ( success or fail )
	 */
	public boolean deleteProject(Project project)
	{
		String sql;
		try {
			sql = "select count(*) from Projects where Name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1,project.getName());
			rs = pst.executeQuery();
			if(rs.getInt(1) == 0){
				ErrorController.get().AddError("Project with name "+project.getName()+" does not exist.");
			}
		} catch (SQLException ex) {}
		
		if (ErrorController.get().ErrorsExist()) {
			ErrorController.get().DisplayErrors();
			return false;
		}		
		sql = "delete from Projects where Name = ?";
		try{
			deleteProjectActivities(project);
			pst = conn.prepareStatement(sql);
			pst.setString(1,project.getName());
			pst.execute();
			pst.close();
			int x=0;
			for (Project oldProject : Projects)
			    if (oldProject.getName() == project.getName())
					break;
			    else
			    	x++;
			Projects.remove(x);
			return true;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}	
		return false;
	}
	
	/**
	* Deletes all activities associated with a project.
	*
	* @param project Project from which to delete all activities
	* @return Deletion status ( success or fail )
	*/
	// TODO: Where this is done will most likely change as the project progresses.
	// TODO: Where this is done will most likely change as project development goes on
	private boolean deleteProjectActivities(Project project)
	{
		String sql = "delete from Activities where PID = ?";
		String sql2 = "delete from ActivityDependency where PID = ?";
		String sql3 = "delete from MemberActivities where PID = ?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1,project.getProjectID());
			pst.execute();
			pst.close();
			
			pst = conn.prepareStatement(sql2);
			pst.setInt(1,project.getProjectID());
			pst.execute();
			pst.close();
			
			pst = conn.prepareStatement(sql3);
			pst.setInt(1,project.getProjectID());
			pst.execute();
			pst.close();
						
			for (int x=0; x < Activities.size(); x++)
			{
			   if (Activities.get(x).getProjectID() == project.getProjectID())
			    {
			    	Activities.remove(x);
			    	x--;
			    }
			}
			
			return true;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}
		return false;
	}
	
	/**
	* Creates an activity
	*
	* @param activity Activity object to create
	* @return The created Activity
	*/	
	public Activity createActivity(Activity activity)
	{		
		if (!activity.getName().equals("") && activity.getName() != null &&
			!activity.getDescr().equals("") && activity.getDescr() != null
			)
		{		
			String sql;				
			try {
				sql = "select count(*) from Activities where PID = ? and Number = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, activity.getProjectID());
				pst.setInt(2, activity.getNumber());
				rs = pst.executeQuery();
				if(rs.getInt(1) == 1){
					ErrorController.get().AddError("Activity with PID "+activity.getProjectID()+" and Number "+activity.getNumber()+" already exists.");
				}
			} catch (SQLException ex) { }			
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return null;
			}						
			try{
				// Here we get the max number for the current project, as with 2 primary keys we cannot auto-increment them.
				sql = "select Max(number) from Activities where PID = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, currentProject.getProjectID());
				rs = pst.executeQuery();
				int x = rs.getInt(1);
				x++;		
				sql = "insert into Activities " +
					   "(PID,Number,Name,Description,PlannedValue,MostLikelyTimeToCompletion," +
                        "OptimisticTimeToCompletion,PessimisticTimeToCompletion,TargetCompletionDate,Status,ActualCost,PercentComplete)" +
						"values(?,?,?,?,?,?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt    (1,  currentProject.getProjectID());
				pst.setInt    (2,  x);
				pst.setString (3,  activity.getName());
				pst.setString (4,  activity.getDescr());
				pst.setDouble (5,  activity.getPlannedValue());
				pst.setDouble (6,  activity.getMostLikelyTimeToCompletion());
				pst.setDouble (7,  activity.getOptimisticTimeToCompletion());
				pst.setDouble (8,  activity.getPessimisticTimeToCompletion());
				pst.setDouble (9,  activity.getTargetCompletionDate());
				pst.setBoolean(10, activity.getStatus());
				pst.setDouble (11, activity.getActualCost());
				pst.setDouble (12, activity.getPercentComplete());
				pst.execute();
				pst.close();
				activity.setProjectID(currentProject.getProjectID());
				activity.setNumber(x);
				Activities.add(activity);
				return activity;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,ex);	
			}
		}
	return null;
	}
	
	/**
	* Updates an activity's information in DB.
	*
	* @param activity Activity to update
	* @return Update status ( success or fail )
	*/	
	public boolean updateActivity(Activity activity)
	{		
		if (!activity.getName().equals("") && activity.getName() != null &&
			!activity.getDescr().equals("") && activity.getDescr() != null //&&
			)
		{
			String sql;			
			sql = "update Activities set Name=?,Description=?,PlannedValue=?,MostLikelyTimeToCompletion=?," +
                        "OptimisticTimeToCompletion=?,PessimisticTimeToCompletion=?,TargetCompletionDate=?,Status=?,ActualCost=?,PercentComplete=?" 
			
					+" where PID = ? and Number = ?";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString( 1, activity.getName());
				pst.setString( 2, activity.getDescr());
				pst.setDouble( 3, activity.getPlannedValue());
				pst.setDouble( 4, activity.getMostLikelyTimeToCompletion());
				pst.setDouble( 5, activity.getOptimisticTimeToCompletion());
				pst.setDouble( 6, activity.getPessimisticTimeToCompletion());
				pst.setDouble( 7, activity.getTargetCompletionDate());
				pst.setBoolean(8, activity.getStatus());
				pst.setDouble( 9, activity.getActualCost());
				pst.setDouble(10, activity.getPercentComplete());
				pst.setInt(   11, activity.getProjectID());
				pst.setInt(   12, activity.getNumber());
				pst.execute();
				pst.close();
				/*int x=0;
				for (Activity oldActivity : Activities)
				    if (oldActivity.getProjectID() == activity.getProjectID() && oldActivity.getNumber() == activity.getNumber())
						break;
				    else
				    	x++;				
				Activities.set(x,activity);*/
				JOptionPane.showMessageDialog(null,"Data saved.");
				return true;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,ex);	
			}
		}		
		return false;
	}
	
	/**
	* Deletes an activity from DB.
	*
	* @param PID Activity's project ID
	* @param number Activity's number
	* @return Deletion status ( success or fail )
	*/	
	public boolean deleteActivity(int PID,int number)
	{
		String sql;
		try {
			sql = "select count(*) from Activities where PID = ? and Number = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1,PID);
			pst.setInt(2,number);
			rs = pst.executeQuery();
			if(rs.getInt(1) == 0){
				ErrorController.get().AddError("Activity with PID "+PID+" and name "+number+" does not exist.");
			}
		} catch (SQLException ex) {}
		finally{
			try{
				rs.close();
				pst.close();			
			}catch(Exception e){}
		}
		if (ErrorController.get().ErrorsExist()) {
			ErrorController.get().DisplayErrors();
			return false;
		}		
		sql = "delete from Activities where PID = ? and Number = ?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, PID);
			pst.setInt(2,number);
			pst.execute();
			int x=0;
			for (Activity oldActivity : Activities)
			    if (oldActivity.getProjectID() == PID && oldActivity.getNumber() == number )
					break;
			    else
			    	x++;				
			Activities.remove(x);
			
			sql = "delete from ActivityDependency where PID = ? and Number = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1,PID);
			pst.setInt(2,number);
			pst.execute();
			
			sql = "delete from ActivityDependency where DependantOnPID = ? and DependantOnNumber = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1,PID);
			pst.setInt(2,number);
			pst.execute();
			
			return true;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex+ "dde");	
		}finally{
			try{
				rs.close();
				pst.close();			
			}catch(Exception e){}
		}
		
		return false;
	}
	
	public boolean createActivityDependencies(Activity activity,ArrayList<Activity> activities)
	{
		String sql;				
		try {
			for (Activity dependantActivity : activities)
			{			
				sql = "insert into ActivityDependency(PID,Number,DependantOnPID,DependantOnNumber) values(?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, activity.getProjectID());
				pst.setInt(2, activity.getNumber());
				pst.setInt(3, dependantActivity.getProjectID());
				pst.setInt(4, dependantActivity.getNumber());
				pst.execute();
				pst.close();
			}
			return true;
		} catch (SQLException ex) { }
		return false;
	}
	
	
	public MemberActivity initializeMemberActivity (MemberActivity ma)
	{
		if (
				ma.getMemberID() > 0 &&  ma.getProjectID () > 0 && ma.getNumber() > 0 
			)
		{
			for (MemberActivity theMemberActivity : MemberActivities)
			    if ( theMemberActivity.getMemberID() == ma.getMemberID() && 
			    	 theMemberActivity.getProjectID() == ma.getProjectID() &&
			    	 theMemberActivity.getNumber() == ma.getNumber() )
			    {
					ErrorController.get().AddError("Member is already assigned to that activity");
			    }
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return null;
			}
			String sql = "insert into MemberActivities (MID,PID,Number) values(?,?,?)";
			try{
				pst = conn.prepareStatement(sql);
				pst.setInt(1, ma.getMemberID());
				pst.setInt(2, ma.getProjectID());
				pst.setInt(3, ma.getNumber());
				pst.execute();
				pst.close();
				MemberActivities.add(ma);
				return ma;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,ex);	
			}		
	}
		return null;
	}
	
	public ArrayList<Member> getMemberListForAddMemberToActivity()
	{
		return Members;
	}
	/**
	 * @param a An Activity Object
	 * @return temp An ArrayList of Activities depend on the Activity passed.
	 * */
	public ArrayList<Activity> getDependantActivities(Activity a){
		
		ArrayList<Activity> temp = new ArrayList<Activity>();

		for(Integer i : this.getRelatedActivities(a, "dependent")){
			for(Activity dep : MainController.get().getActivityListForCurrentProject()){
				if(dep.getNumber() == i){
					temp.add(dep);
				}
			}
		}
		return temp;
	}
	
	/**
	 * @param a An Activity Object
	 * @return temp An ArrayList of Activities depend on the Activity passed.
	 * */
	public ArrayList<Activity> getPrecedantActivities(Activity a){
		
		ArrayList<Activity> temp = new ArrayList<Activity>();

		for(Integer i : this.getRelatedActivities(a, "precedent")){
			for(Activity pre : MainController.get().getActivityListForCurrentProject()){
				if(pre.getNumber() == i){
					temp.add(pre);
				}
			}
		}
		return temp;
	}
	public ArrayList<Integer> getRelatedActivities(Activity a, String type){
		
		String sql, get;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		if(type.equals("dependent")){
			sql = 	"select Number " +
					"from ActivityDependency " +
					"where DependantOnPID = ? and DependantOnNumber = ? and PID = ?";
			get =   "Number";
		}else{
			sql = 	"select DependantOnNumber " +
					"from ActivityDependency " +
					"where PID = ? and Number = ? and DependantOnPID = ?";
			get =   "DependantOnNumber";
		}
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, a.getProjectID());
			pst.setInt(2, a.getNumber());
			pst.setInt(3, a.getProjectID());
			rs = pst.executeQuery();
			
			while(rs.next()){
				temp.add(rs.getInt(get));
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}finally{
			try{
				rs.close();
				pst.close();			
			}catch(Exception e){}
		}
		return temp;
	}
}
