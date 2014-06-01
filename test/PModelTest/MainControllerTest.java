package PModelTest;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import PModel.Activity;
import PModel.MainController;
import PModel.Member;
import PModel.Project;

public class MainControllerTest {
	//members
	private static Member GoodMember;
	private static Member BlankMember;
	private static Member BlankName;
	private static Member BlankType;
	private static Member BlankUsername;
	private static Member BlankPassword;
	private static Member NonUniqueMember;
	
	//activities
	private static Activity UpdateActivityTrue;
	private static Activity UpdateActivityFalse;
	private static Activity NewActivity;
	private static Activity BlankActivity;
	private static Activity InvalidActivity;
	
	//project
	private static Project newProject;
	private static Project blankProject;
	private static Project invalidProject;
	@BeforeClass
	public static void testSetup(){
		GoodMember = new Member("John Doe", "member", "JDoe", "password123");
		BlankMember = new Member("", "", "", "");
		BlankName = new Member("", "member", "JDoe1", "password123");
		BlankType = new Member("Timmy", "", "Tim4", "password123");
		BlankUsername = new Member("Jane", "member", "", "password123");
		BlankPassword = new Member("Fred", "member", "Freddy", "");
		NonUniqueMember = new Member("John Doe", "member", "JDoe", "password123");
		
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Date d1 = sdf.parse("31-JUN-2014");
			Date d2 = sdf.parse("31-JUL-2014");
			//Activity
			NewActivity = new Activity(1,"New Activity 1", "This is a testing activity", d1,  d2, 6);
			BlankActivity = new Activity(0, "","",null,null,0);
			InvalidActivity = new Activity(1, "Invalid Activity", "this is an invalid activity", d2, d1, 8);
			
			//Project
			newProject = new Project(4, "New Project 1", "This is a testing project", d1, d2, 8);
			blankProject = new Project(0, "", "", null, null, 0);
			invalidProject = new Project(5,"Invalid Project","This should Fail",d2,d1,8);
		}catch(ParseException e)
		{
			System.out.println("Improper Date Format");
		}
		
		
	}

	@Test	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testMainController() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentProject() {
		fail("Not yet implemented");
	}

	/*
	 * Login Test
	 * Checks if valid login passes
	 * Checks if invalid login doesn't pass
	 * 		On failed login GUI opens saying invalid login
	 * 			could this be changed?
	 */
	@Test
	public void testLogin() {
		//valid login
		assertTrue(MainController.get().Login("root", "root"));
		
		//invalid login
		assertFalse(MainController.get().Login("FailedUsername", "FailedPassword"));
	}

	@Test
	public void testGetProjectList() {
		fail("Not yet implemented");
	}

	@Test
	public void testOpenProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActivityList() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillcombo() {
		fail("Not yet implemented");
	}

	@Test
	public void testMouseClickTable() {
		fail("Not yet implemented");
	}

	/*
	 * Create Member Test
	 * 	Valid
	 * 		All categories filled in, name, type, usernmae, password
	 * Invalid
	 * 		Missing Name, type, username, and/or password
	 * 		Creation of second exact same member
	 */
	@Test
	public void testCreateMember() {
		//Valid
		assertEquals(GoodMember, MainController.get().CreateMember(GoodMember));
		
		//Invalid
		assertNull(MainController.get().CreateMember(BlankMember));
		assertNull(MainController.get().CreateMember(BlankName));
		assertNull(MainController.get().CreateMember(BlankType));
		assertNull(MainController.get().CreateMember(BlankUsername));
		assertNull(MainController.get().CreateMember(BlankPassword));
		assertNull(MainController.get().CreateMember(NonUniqueMember));
		
	}

	@Test
	public void testUpdateMember() {
		fail("Not yet implemented");
	}

	/*
	 * Test to delete Member
	 * Parameter: Member
	 */
	@Test
	public void testDeleteMemberMember() {
		//Delete Existing Member
		assertTrue(MainController.get().DeleteMember(GoodMember));
		//Deletes Non-Existant Member
		assertFalse(MainController.get().DeleteMember(BlankMember));
	}

	/*
	 * Attempts To Delete Member ID that does no exist/is not in list of members
	 *
	 * example: MemberID = 2
	 * 
	 * Test passes if True is returned and the member is deleted
	 */
	@Test
	public void testDeleteMemberIDTrue() {
		/*
		 * Not sure of valid MemberID, Don't want to delete root so add valid non-root id
		 */
		// assertTrue(MainController.get().DeleteMember(2));
	}
	
	/*
	 * Attempts To Delete Member ID that does no exist/is not in list of members
	 *
	 * example: MemberID = 999999999
	 * 
	 * Test passes if False is returned and no member is deleted
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testDeleteMemberIDFalse(){
		assertFalse(MainController.get().DeleteMember(999999999));
	}

	/*
	 * Attempts to Create a Project 
	 * 
	 * Test Passes if:
	 * 	Valid project is created
	 * 	Blank Project is not created
	 * 	Invalid Project is not created
	 */
	@Test
	public void testCreateProject() {
		assertEquals(newProject, MainController.get().CreateProject(newProject));
		assertNull(MainController.get().CreateProject(blankProject));
		assertNull(MainController.get().CreateProject(invalidProject));
	}

	@Test
	public void testUpdateProject() {
		fail("Not yet implemented");
	}

	/*
	 * Attempts to Delete Project using Project ID
	 * 
	 * Example: project id = 2
	 * 
	 * Test Passes if True is returned and project is deleted
	 */
	@Test
	public void testDeleteProjectIDTrue() {
		assertTrue(MainController.get().DeleteProject(2));
	}
	
	/*
	 * Attempts to Delete Project using Project ID
	 * 
	 * Example: project id = 999999999
	 * 
	 * Test Passes if False is returned and no project is deleted
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testDeleteProjectIDFalse(){
		assertFalse(MainController.get().DeleteProject(999999999));
	}	

	/*
	 * Attempts to Delete a Project using Project Name
	 * 
	 * Example: project name = My Project
	 * 
	 * Test passes if True is returned and project is deleted
	 */
	@Test
	public void testDeleteProjectByNameTrue() {
		//Replace My Project with a valid Name in order to pass the test
		assertTrue(MainController.get().DeleteProject("My Project"));
	}
	
	/*
	 * Attempts to Delete a Project using Fake Project Name
	 * 
	 * Example: project Name = Fake Project
	 * 
	 * Test passes if False is Returned and project is not deleted
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testDeleteProjectByNameFalse() {
		//Replace My Project with a valid Name in order to pass the test
		assertFalse(MainController.get().DeleteProject("Fake Project"));
	}

	/*
	 * Attempts to Create an Activity
	 * 
	 * Passes if:
	 * 	Valid Activity is created
	 * 	Blank Activity is not created 
	 *  Invalid Activity is not created
	 */
	@Test
	public void testCreateActivity() {
		//creates valid activity
		assertEquals(NewActivity, MainController.get().CreateActivity(NewActivity));
		//creates blank activity
		assertNull(MainController.get().CreateActivity(BlankActivity));
		//created activity where start is after deadline
		assertNull(MainController.get().CreateActivity(InvalidActivity));
	}

	@Test
	public void testUpdateActivity() {
		fail("Not yet implemented");
	}

	/*
	 * Attempts to Delete an Activity using ProjectID and Activity Number
	 * 
	 * Example: Project ID = 1  Activity Number = 1
	 * 
	 * Test Passes if True is Returned and Activity is Deleted for valid Activity
	 * 			and False is Returned and Activity is Not Deleted for invalid Activity
	 */
	@Test
	public void testDeleteActivity() {
		assertTrue(MainController.get().DeleteActivity(1, 1));
		assertFalse(MainController.get().DeleteActivity(99, 99));
	}
	
}
