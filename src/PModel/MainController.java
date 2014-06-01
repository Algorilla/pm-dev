package PModel;

import DatabaseConnect.SQLiteDBConnection;
import JFrames.UserInterface;

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

// Class for main data and application operations.
public class MainController {

	// Singleton design pattern
	private static MainController self = new MainController();
    public static MainController get() { return self; }
	
    // Data
    private ArrayList<Member> Members = new ArrayList<Member>();
    private ArrayList<Project> Projects = new ArrayList<Project>();
    private ArrayList<Activity> Activities = new ArrayList<Activity>();
    
    // SQLite DB connection data
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	// Session variables
	Member currentUser;
	Project currentProject;
	
	// Date format
	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		
	/**
	* Description: Initializes controller by connecting 
	* to the DB and loading the data into memory.
	*
	* @author  Robert Wolfstein
	* @param
	* @return
	*/
	MainController()
	{
		conn = SQLiteDBConnection.ConnecrDb();
		LoadData();
		
		// Sample usage:
		//CreateMember(new Member("example user","manager","example","password"));
		//CreateProject(new Project(1,"Project 1","Project 1",new Date(),new Date(),1));
	}
	
	/**
	* Description: Loads Database into local memory.
	*
	* @author  Robert Wolfstein
	* @param
	* @return
	*/
	public void LoadData()
	{
		String sqlMembers = "select * from Members";
		String sqlProjects = "select * from Projects";
		String sqlActivities = "select * from Activities";
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
						df.parse(rs.getString("StartDate")),
						df.parse(rs.getString("Deadline")),
						rs.getInt("ProjectedLength")
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
						rs.getString("Name"),
						rs.getString("Description"),
						df.parse(rs.getString("StartDate")),
						df.parse(rs.getString("Deadline")),
						rs.getInt("ProjectedLength")
						);
				activity.setProjectID(rs.getInt("PID"));
				activity.setNumber(rs.getInt("Number"));
				Activities.add(activity);
			}									
			rs.close();
			pst.close();	
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}
	}
	
	/**
	* Description: Returns a reference to the current
	* user.
	*
	* @author  Robert Wolfstein
	* @param
	* @return Member
	*/
	public Member GetCurrentUser()
	{
		return currentUser;
	}
	
	/**
	* Description: Returns a reference to the current
	* project.
	*
	* @author  Robert Wolfstein
	* @param
	* @return Project
	*/
	public Project GetCurrentProject()
	{
		return currentProject;	
	}
	
	/**
	* Description: Logs the user in.
	*
	* @author  Robert Wolfstein
	* @param username, password
	* @return boolean
	*/
	public boolean Login(String username,String password)
	{
		for (Member member : Members)
		{
		    if (member.getUserName().equals(username) && member.getPassword().equals(password))
		    {
				currentUser = member;								
				UserInterface userAccount = new UserInterface(100,100,1000,600,"",username);
				userAccount.setVisible(true);				
				return true;
		    }			
		}
		JOptionPane.showMessageDialog(null, "Username and password combination is not correct.");	
		return false;
	}
	
	/**
	* Description: Returns a list of projects for the 
	* currently signed in user.
	*
	* @author  Robert Wolfstein
	* @param
	* @return List (string)
	*/	
	public List GetProjectList()
	{
		final List projectList = new List();	
		for (Project project : Projects)
		    if (project.getManagerID() == currentUser.getMemberID())
				projectList.add(project.getName());
		return projectList;	
	}
	
	/**
	* Description: Opens a project selected in the 
	* OpenProject list
	*
	* @author  Robert Wolfstein
	* @param name
	* @return
	*/	
	public void OpenProject(String name)
	{
		for (Project project : Projects)
		    if (project.getName() == name)
				currentProject = project;
	}
	
	/**
	* Description: Display project and its data in 
	* the GUI
	*
	* @author  Junhui C
	* @param table
	* @return
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
	* Description: Returns a reference to an activity
	* in local memory based on the needed identifiers.
	*
	* @author  Robert Wolfstein
	* @param int PID (Project ID) and int number
	* (Activity ID in project)
	* @return Activity
	*/	
	public Activity GetActivityFromID(int PID,int number)
	{
		for (Activity activity : Activities)
		    if (activity.getProjectID() == PID && activity.getNumber() == number)
		    	return activity;
		return null;
	}
	
	/**
	* Description: Creates a user.
	*
	* @author  Robert Wolfstein
	* @param Member
	* @return Member
	*/	
	public Member CreateMember(Member member)
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
	* Description: Updates a user.
	*
	* @author  Robert Wolfstein
	* @param Member
	* @return boolean
	*/	
	public boolean UpdateMember(Member member)
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
	* Description: Deletes a user.
	*
	* @author  Robert Wolfstein
	* @param int (Member ID) OR Member
	* @return boolean
	*/	
	// TODO: Ensure referential integrity such that objects in other relations do not refer to deleted Users
	public boolean DeleteMember(int MID) 
	{
		int x = 0;
		for (Member member : Members)
		    if (member.getMemberID() == MID)
				break;
		    else
		    	x++;
		return DeleteMember(Members.get(x));
	}
	public boolean DeleteMember(Member member)
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
	* Description: Creates a project.
	*
	* @author  Robert Wolfstein
	* @param Project
	* @return Project
	*/	
	public Project CreateProject(Project project)
	{
		if (!project.getName().equals("") && project.getName() != null &&
			!project.getDescr().equals("") && project.getDescr() != null &&
			project.getStart() != null &&
			project.getDeadline() != null &&
			project.getLength() != 0
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
				sql = "insert into Projects (Name,Description,ManagerID,StartDate,Deadline,ProjectedLength)values(?,?,?,?,?,?)";				
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1, project.getName());
					pst.setString(2, project.getDescr());
					pst.setInt(3, project.getManagerID());
					pst.setString(4, df.format(project.getStart()));
					pst.setString(5, df.format(project.getDeadline()));
					pst.setInt(6, project.getLength());
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
	* Description: Updates a project.
	*
	* @author  Robert Wolfstein
	* @param Project
	* @return boolean
	*/	
	public boolean UpdateProject(Project project)
	{		
		if (!project.getName().equals("") && project.getName() != null &&
			!project.getDescr().equals("") && project.getDescr() != null &&
			project.getStart() != null &&
			project.getDeadline() != null &&
			project.getLength() != 0
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
			sql = "update Projects set Name=?,Description=?,ManagerID=?,StartDate=?,Deadline=?,ProjectedLength=? where PID = ?";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString(1, project.getName());
				pst.setString(2, project.getDescr());
				pst.setInt(3, project.getManagerID());
				pst.setString(4, df.format(project.getStart()));
				pst.setString(5, df.format(project.getDeadline()));
				pst.setInt(6, (project.getLength()));
				pst.setInt(7,project.getProjectID());
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
	* Description: Deletes a project.
	*
	* @author  Robert Wolfstein
	* @param int (Project ID) OR string (Project Name) Or Project
	* @return boolean
	*/	
	public boolean DeleteProject(int PID) 
	{
		int x = 0;
		for (Project project : Projects)
		    if (project.getProjectID() == PID)
				break;
		    else
		    	x++;
		return DeleteProject(Projects.get(x));
	}
	public boolean DeleteProject(String projectName) 
	{
		int x = 0;
		for (Project project : Projects)
		    if (project.getName() == projectName)
				break;
		    else
		    	x++;
		return DeleteProject(Projects.get(x));
	}
	public boolean DeleteProject(Project project)
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
			DeleteProjectActivities(project);
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
	* Description: Deletes all activities associated
	* with a project.
	*
	* @author  Robert Wolfstein
	* @param Project
	* @return boolean
	*/
	// TODO: Where this is done will most likely change as the project progresses.
	// TODO: Where this is done will most likely change as project development goes on
	private boolean DeleteProjectActivities(Project project)
	{
		String sql = "delete from Activities where PID = ?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1,project.getProjectID());
			pst.execute();
			pst.close();
			for (int x=0;x<Activities.size();x++)
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
	* Description: Creates an activity
	*
	* @author  Robert Wolfstein
	* @param Activity
	* @return Activity
	*/	
	public Activity CreateActivity(Activity activity)
	{		
		if (!activity.getName().equals("") && activity.getName() != null &&
			!activity.getDescr().equals("") && activity.getDescr() != null &&
			activity.getStart() != null &&
			activity.getDeadline() != null
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
				sql = "insert into Activities (PID,Number,Name,Description,StartDate,Deadline,ProjectedLength)values(?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, currentProject.getProjectID());
				pst.setInt(2, x);
				pst.setString(3, activity.getName());
				pst.setString(4, activity.getDescr());
				pst.setString(5, df.format(activity.getStart()));
				pst.setString(6, df.format(activity.getDeadline()));
				pst.setString(7, String.valueOf(activity.getLength()));
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
	* Description: Updates an activity.
	*
	* @author  Robert Wolfstein
	* @param Activity
	* @return boolean
	*/	
	public boolean UpdateActivity(Activity activity)
	{		
		if (!activity.getName().equals("") && activity.getName() != null &&
			!activity.getDescr().equals("") && activity.getDescr() != null &&
			activity.getStart() != null &&
			activity.getDeadline() != null &&
			activity.getProjectID() != 0
			)
		{
			String sql;			
			sql = "update Activities set Name=?,Description=?,StartDate=?,Deadline=?,ProjectedLength=? where PID = ? and Number = ?";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString(1, activity.getName());
				pst.setString(2, activity.getDescr());
				pst.setString(3, df.format(activity.getStart()));
				pst.setString(4, df.format(activity.getDeadline()));
				pst.setInt(5, activity.getLength());
				pst.setInt(6, activity.getProjectID());
				pst.setInt(7, activity.getNumber());
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
	* Description: Deletes an activity
	*
	* @author  Robert Wolfstein
	* @param int (Project ID), int (Activity Number)
	* @return boolean
	*/	
	public boolean DeleteActivity(int PID,int number)
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
}
