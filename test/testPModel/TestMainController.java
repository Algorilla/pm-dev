package testPModel;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import PModel.Activity;
import PModel.MainController;
import PModel.Member;
import PModel.Project;

public class TestMainController {

	private static PModel.MainController controller;

	// test members
	private static Member goodMember;
	private static Member blankMember;
	private static Member duplicateUsernameMember;
	private static Member deleteMember;

	// test activities
	private static Activity newActivity;
	private static Activity blankActivity;
	private static Activity invalidActivity;

	// test project
	private static Project newProject;
	private static Project blankProject;
	private static Project invalidProject;
	private static Project myProject;

	@BeforeClass
	public static void setup() {
		controller = PModel.MainController.get();

		goodMember = new Member("John Doe", "member", "JDoe", "password123");
		blankMember = new Member("", "", "", "");
		duplicateUsernameMember = new Member("John Doe", "member", "JDoe", "password123");
		deleteMember = new Member("DELETED", "manager", "DELETED2", "123");

		// Ensure that JDoe is not in the database prior to testing.
		assert (!controller.Login("JDoe", "password123"));

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Date d1 = sdf.parse("31-JUN-2014");
			Date d2 = sdf.parse("31-JUL-2014");
			// Activity
			// project = new Project()
			newActivity = new Activity(1, "New Activity 1",	"This is a testing activity", d1, d2, 6);
			blankActivity = new Activity(0, "", "", null, null, 0);
			invalidActivity = new Activity(1, "Invalid Activity", "this is an invalid activity", d2, d1, 8);

			// Project
			newProject = new Project(4, "New Project 1", "This is a testing project", d1, d2, 8);
			blankProject = new Project(1, "", "", null, null, 0);
			invalidProject = new Project(1, "Invalid Project", "This should Fail", d2, d1, 8);
			myProject = new Project(1, "My Project", "THIS PROJECT TESTS DELETE", d1, d2, 6);
		
		} catch (ParseException e) {
			System.out.println("TestMainController: Improper Date Format");
		}
	}

	@AfterClass
	public static void cleanUp() {
		deleteJohnDoe();
	}

	@Test
	public void testLoginValidUsernameAndPassword() {
		assertTrue(controller.Login("root", "root"));
	}

	@Test
	public void testLoginInvalidPassword() {
		assertFalse(controller.Login("root", "rot"));
	}

	@Test
	public void testLoginInvalidUsername() {
		assertFalse(controller.Login("FailedUsername", "FailedPassword"));
	}

	@Test
	public void testCreateMember() {
		assertNull(controller.CreateMember(blankMember));
		assertNotNull(controller.CreateMember(goodMember));
		assertNull(controller.CreateMember(duplicateUsernameMember));
		// Last condition is should return null because no two users can have the same username.
	}

	@Ignore
	@Test
	public void testUpdateMember() {
		fail("UpdateMember(Member member) works only from GUI.");
	}

	@Test
	public void testDeleteMemberByMember() {
		controller.CreateMember(deleteMember);
		assertTrue(controller.DeleteMember(deleteMember));
		assertFalse(controller.DeleteMember(blankMember));
	}

	@Test
	public void testDeleteMemberByID() {
		assertFalse(controller.DeleteMember(5));
		assertTrue(controller.DeleteMember(goodMember.getMemberID()));
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
		assertFalse(controller.DeleteMember(999999999));
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
		controller.DeleteProject(newProject);
		assertEquals(newProject, controller.CreateProject(newProject));
		assertNull(controller.CreateProject(blankProject));

		// Project has start date after deadline date
		assertNull(controller.CreateProject(invalidProject));
		// check if unique project
		assertNull(controller.CreateProject(newProject));
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
		assertTrue(controller.DeleteProject(3));
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
		assertFalse(controller.DeleteProject(999999999));
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
		controller.CreateProject(myProject);
		// Replace My Project with a valid Name in order to pass the test
		assertTrue(controller.DeleteProject("My Project"));
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
		assertFalse(controller.DeleteProject("Fake Project"));
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
		assertEquals(newActivity,
				controller.CreateActivity(newActivity));
		// creates blank activity
		assertNull(controller.CreateActivity(blankActivity));
		// created activity where start is after deadline
		assertNull(controller.CreateActivity(invalidActivity));
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
		assertTrue(controller.DeleteActivity(1, 1));
		assertFalse(controller.DeleteActivity(99, 99));
	}

	private static boolean deleteJohnDoe() {
		return controller.Login("JDoe", "password123") && 
				controller.DeleteMember(controller.GetCurrentUser());
		}
	}