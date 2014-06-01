package PModelTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import PModel.MainController;
import PModel.Member;

public class MainControllerTest {
	private static Member GoodMember;
	private static Member BlankMember;
	private static Member BlankName;
	private static Member BlankType;
	private static Member BlankUsername;
	private static Member BlankPassword;
	private static Member NonUniqueMember;
	
	@BeforeClass
	public static void testSetup(){
		GoodMember = new Member("John Doe", "member", "JDoe", "password123");
		BlankMember = new Member("", "", "", "");
		BlankName = new Member("", "member", "JDoe1", "password123");
		BlankType = new Member("Timmy", "", "Tim4", "password123");
		BlankUsername = new Member("Jane", "member", "", "password123");
		BlankPassword = new Member("Fred", "member", "Freddy", "");
		NonUniqueMember = new Member("John Doe", "member", "JDoe", "password123");
		
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

	@Test
	public void testCreateProject() {
		fail("Not yet implemented");
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

	@Test
	public void testCreateActivity() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateActivity() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteActivityString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteActivityIntString() {
		fail("Not yet implemented");
	}

}
