package testPModel;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import PModel.Activity;
import PModel.MainController;
import PModel.Member;
import PModel.Project;

public class TestMainController {
	// members
	private static Member GoodMember;
	private static Member BlankMember;
	private static Member NonUniqueMember;
	private static Member DeleteMember;

	// activities
	private static Activity UpdateActivityTrue;
	private static Activity UpdateActivityFalse;
	private static Activity NewActivity;
	private static Activity BlankActivity;
	private static Activity InvalidActivity;

	// project
	private static Project newProject;
	private static Project blankProject;
	private static Project invalidProject;
	private static Project myProject; // tests deleteProject

	/**
	 * Initializing Method done prior to test creation
	 */
	@BeforeClass
	public static void testSetup() {
		GoodMember = new Member("John Doe", "member", "JDoe3", "password123");
		BlankMember = new Member("", "", "", "");
		NonUniqueMember = new Member("John Doe", "member", "JDoe",
				"password123");

		DeleteMember = new Member("DELETED", "manager", "DELETED2", "123");

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Date d1 = sdf.parse("31-JUN-2014");
			Date d2 = sdf.parse("31-JUL-2014");
			// Activity
			// project = new Project()
			NewActivity = new Activity(1, "New Activity 1",
					"This is a testing activity", d1, d2, 6);
			BlankActivity = new Activity(0, "", "", null, null, 0);
			InvalidActivity = new Activity(1, "Invalid Activity",
					"this is an invalid activity", d2, d1, 8);

			// Project
			newProject = new Project(4, "New Project 1",
					"This is a testing project", d1, d2, 8);
			blankProject = new Project(1, "", "", null, null, 0);
			invalidProject = new Project(1, "Invalid Project",
					"This should Fail", d2, d1, 8);
			myProject = new Project(1, "My Project",
					"THIS PROJECT TESTS DELETE", d1, d2, 6);
		} catch (ParseException e) {
			System.out.println("Improper Date Format");
		}
	}

	/**
	 * Attempts to Login
	 * 
	 * Test will pass if: - True is returned and already created user is able to
	 * log in - False is returned when a new user is not created
	 */
	@Test
	public void testLogin() {
		// valid login
		assertTrue(MainController.get().Login("root", "root"));

		// invalid login
		assertFalse(MainController.get().Login("FailedUsername",
				"FailedPassword"));
	}

	/**
	 * Attempts to Create a valid member, a blank member and a non-unique member
	 * 
	 * Test will pass if: - Null is returned when blank member is created - Null
	 * is return if a non-unique member is attempted to be created
	 * 
	 */
	@Test
	public void testCreateMember() {
		// Invalid
		assertNull(MainController.get().CreateMember(BlankMember));
		assertNull(MainController.get().CreateMember(NonUniqueMember));

		// Valid
		// Add Valid Test
	}

	/**
	 * This Method is to be tested in GUI
	 */
	@Test
	public void testUpdateMember() {
		fail("UpdateMember(Member member) is tested in the GUI");
	}

	/**
	 * Attempts to Delete a member that exists and a member that doesn't exist
	 * 
	 * Test passes if: True is returned and member is deleted False is returned
	 * and non-existant member is not deleted
	 */
	@Test
	public void testDeleteMemberByMember() {
		MainController.get().CreateMember(DeleteMember);
		// Delete Existing Member
		assertTrue(MainController.get().DeleteMember(DeleteMember));
		// Deletes Non-Existant Member
		assertFalse(MainController.get().DeleteMember(BlankMember));
	}

	/**
	 * Attempts To Delete Member ID that does no exist/is not in list of members
	 * 
	 * example: MemberID = 2
	 * 
	 * Test passes if True is returned and the member is deleted
	 * 
	 */
	@Test
	public void testDeleteMemberIDTrue() {
		// This test will fail if no user with ID 2
		assertTrue(MainController.get().DeleteMember(5));
	}

	/**
	 * Attempts To Delete Member ID that does no exist/is not in list of members
	 * 
	 * example: MemberID = 999999999
	 * 
	 * Test passes if False is returned and no member is deleted
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteMemberIDFalse() {
		assertFalse(MainController.get().DeleteMember(999999999));
	}

	/**
	 * Attempts to Create a Project
	 * 
	 * Test Passes if: Valid project is created Blank Project is not created
	 * Invalid Project is not created
	 * 
	 * Deletes project in order to clear list of already existing projects
	 * before assertions
	 */
	@Test
	public void testCreateProject() {
		// to avoid test Failure because project already exists incase
		MainController.get().DeleteProject(newProject);
		assertEquals(newProject, MainController.get().CreateProject(newProject));
		assertNull(MainController.get().CreateProject(blankProject));

		// Project has start date after deadline date
		assertNull(MainController.get().CreateProject(invalidProject));
		// check if unique project
		assertNull(MainController.get().CreateProject(newProject));
	}

	/**
	 * This Method is to be tested in GUI
	 */
	@Test
	public void testUpdateProject() {
		fail("UpdateProject(Project project) is tested in the GUI");
	}

	/**
	 * Attempts to Delete Project using Project ID
	 * 
	 * Example: project id = 2
	 * 
	 * Test Passes if True is returned and project is deleted
	 * 
	 * Prior to test, ensure you're deleting a project that has been created.
	 * Project ID are assigned automatically when creating a project
	 */
	@Test
	public void testDeleteProjectIDTrue() {
		assertTrue(MainController.get().DeleteProject(3));
	}

	/**
	 * Attempts to Delete Project using Project ID
	 * 
	 * Example: project id = 999999999
	 * 
	 * Test Passes if False is returned and no project is deleted
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectIDFalse() {
		assertFalse(MainController.get().DeleteProject(999999999));
	}

	/**
	 * Attempts to Delete a Project using Project Name
	 * 
	 * Example: project name = My Project
	 * 
	 * Test passes if True is returned and project is deleted
	 * 
	 */
	@Test
	public void testDeleteProjectByNameTrue() {
		MainController.get().CreateProject(myProject);
		// Replace My Project with a valid Name in order to pass the test
		assertTrue(MainController.get().DeleteProject("My Project"));
	}

	/**
	 * Attempts to Delete a Project using Fake Project Name
	 * 
	 * Example: project Name = Fake Project
	 * 
	 * Test passes if False is Returned and project is not deleted
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectByNameFalse() {
		// Replace My Project with a valid Name in order to pass the test
		assertFalse(MainController.get().DeleteProject("Fake Project"));
	}

	/**
	 * Attempts to Create an Activity
	 * 
	 * Passes if: Valid Activity is created Blank Activity is not created
	 * Invalid Activity is not created
	 */
	@Test
	public void testCreateActivity() {
		// creates valid activity
		assertEquals(NewActivity,
				MainController.get().CreateActivity(NewActivity));
		// creates blank activity
		assertNull(MainController.get().CreateActivity(BlankActivity));
		// created activity where start is after deadline
		assertNull(MainController.get().CreateActivity(InvalidActivity));
	}

	/**
	 * This Method is to be tested in GUI
	 */
	@Test
	public void testUpdateActivity() {
		fail("UpdateActivity(Activity activity) is tested in the GUI");
	}

	/**
	 * Attempts to Delete an Activity using ProjectID and Activity Number
	 * 
	 * Example: Project ID = 1 Activity Number = 1
	 * 
	 * Test Passes if True is Returned and Activity is Deleted for valid
	 * Activity and False is Returned and Activity is Not Deleted for invalid
	 * Activity
	 */
	@Test
	public void testDeleteActivity() {
		assertTrue(MainController.get().DeleteActivity(1, 1));
		assertFalse(MainController.get().DeleteActivity(99, 99));
	}

}
