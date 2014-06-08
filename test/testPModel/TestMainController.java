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
	private static Member validMemeber;
	private static Member blankMember;
	private static Member duplicateUsernameMember;
	private static Member deleteMember;

	// test activities
	private static Activity validActivity;
	private static Activity blankActivity;
	private static Activity invalidDatesActivity;

	// test project
	private static Project validProject;
	private static Project blankProject;
	private static Project invalidDatesProject;
	private static Project deleteProject;

	@BeforeClass
	public static void setup() {

		controller = PModel.MainController.get();

		validMemeber = new Member("John Doe", "member", "JDoe", "password123");
		blankMember = new Member("", "", "", "");
		duplicateUsernameMember = new Member("John Doe", "member", "JDoe", "password123");
		deleteMember = new Member("DELETED", "manager", "DELETED2", "123");

		// Ensure that JDoe is not in the database prior to testing.
		assert(!controller.Login("JDoe", "password123"));

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Date d1 = sdf.parse("31-JUN-2014");
			Date d2 = sdf.parse("31-JUL-2014");
			// Project

			validProject = new Project(1, "Valid Project", "This is a valid testing project", d1, d2, 8);
			blankProject = new Project(0, "", "", null, null, 0);
			invalidDatesProject = new Project(1, "Invalid Dates Project", "This is an invalid testing project", d2, d1, 8);
			deleteProject = new Project(1, "Delete Project", "This is a delete testing project", d1, d2, 6);

			// Activity
			validActivity = new Activity(1, "Valid Activity", "This is a valid testing activity", d1, d2, 6);
			blankActivity = new Activity(0, "", "", null, null, 0);
			invalidDatesActivity = new Activity(1, "Invalid Activity", "this is an invalid testing activity", d2, d1, 8);

		} catch (ParseException e) {
			System.out.println("TestMainController: Improper Date Format");
		}
	}

	@AfterClass
	public static void cleanUp() {
		deleteJohnDoe("");
		controller.DeleteProject(validProject);
		controller.DeleteProject(blankProject);
		controller.DeleteProject(invalidDatesProject);
		controller.DeleteProject(deleteProject);
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
	public void testCreateMemberBlank() {
		assertNull(controller.CreateMember(blankMember));
	}

	@Test
	public void testCreateMemberDuplicate() {
		// Should return null because no two users can have the same username.
		assertNull(controller.CreateMember(duplicateUsernameMember));
	}

	@Test
	public void testCreateMemberValid() {
		deleteJohnDoe("testCreateMemberValid");
		assertNotNull(controller.CreateMember(validMemeber));
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
		assertFalse(controller.DeleteMember(deleteMember));
	}

	@Test
	public void testDeleteMemberByID() {
		controller.CreateMember(deleteMember);
		assertTrue(controller.DeleteMember(deleteMember.getMemberID()));
		assertFalse(controller.DeleteMember(deleteMember));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteMemberByIDInvalid() {
		// attempts to delete a with a non-existing MID
		assertFalse(controller.DeleteMember(Integer.MAX_VALUE));
	}

	@Test
	public void testCreateProjectValid() {
		// ensure that project does not already exist
		deleteValidProject("testCreateProjectValid");

		assertEquals(validProject, controller.CreateProject(validProject));
	}

	@Test
	public void testCreateProjectBlankProject() {
		assertNull(controller.CreateProject(blankProject));
	}

	@Test
	public void testCreateProjectDuplicate() {
		deleteValidProject("testCreateProjectDuplicate");

		assertEquals(validProject, controller.CreateProject(validProject));
		assertNull(controller.CreateProject(validProject));
	}

	@Test
	public void testCreateProjectInvalidDates() {
		//project has start date after deadline date
		assertNull(controller.CreateProject(invalidDatesProject));
	}

	@Ignore
	@Test
	public void testUpdateProject() {
		fail("UpdateProject(Project project) is tested in the GUI");
	}

	@Test
	public void testDeleteProjectByIDValid() {
		assertNotNull(controller.CreateProject(deleteProject));
		assertTrue(controller.DeleteProject(deleteProject.getProjectID()));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectByIDInvalid() {
		assertFalse(controller.DeleteProject(Integer.MAX_VALUE));
	}

	@Test
	public void testDeleteProjectByNameValid() {
		assertNotNull(controller.CreateProject(deleteProject));
		assertTrue(controller.DeleteProject(deleteProject.getName()));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectByNameInvalid() {
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
		assertEquals(validActivity,
				controller.CreateActivity(validActivity));
		// creates blank activity
		assertNull(controller.CreateActivity(blankActivity));
		// created activity where start is after deadline
		assertNull(controller.CreateActivity(invalidDatesActivity));
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

	private static void deleteJohnDoe(String callingMethod) {
		if (controller.Login("JDoe", "password123") && controller.DeleteMember(controller.GetCurrentUser())) {
			if (!callingMethod.equals("")) {
				System.out.println(callingMethod + " deleted newProject prior to test");
			}
		}
	}
	
	private static void deleteValidProject(String callingMethod) {
		// ensure that project does not already exist
		if (controller.DeleteProject(validProject)) {
			System.out.println(callingMethod + " deleted newProject prior to test");
		}
	}
}