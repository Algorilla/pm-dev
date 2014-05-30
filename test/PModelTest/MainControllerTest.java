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

	@Test
	public void testDeleteMemberInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProjectProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProjectString() {
		fail("Not yet implemented");
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
