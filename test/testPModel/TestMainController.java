package testPModel;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import DatabaseConnect.SQLiteDBConnection;
import PModel.Activity;
import PModel.MainController;
import PModel.Member;
import PModel.Project;

public class TestMainController {

	private PModel.MainController controller;

	// test members
	private Member validMemeber;
	
	private static Date date1;
	private static Date date2;
	
	/*private static Member blankMember;
	private static Member duplicateUsernameMember;
	private static Member deleteMember;
	private static Member activityTestMember;

	// test activities
	private static Activity validActivity;
	private static Activity blankActivity;
	private static Activity invalidDatesActivity;

	// test project
	private static Project validProject;
	private static Project blankProject;
	private static Project invalidDatesProject;
	private static Project deleteProject;
	private static Project activityProject;*/

	@Before
	public void before()
	{
		controller = PModel.MainController.get();
		
		validMemeber = new Member("John Doe", "member", "jdoe", "password");
	}
	
	@After
	public void after()
	{
		validMemeber = null;
		
		Connection conn = controller.GetControllerConnection();
		PreparedStatement pst = null;
		String sql1 = "delete from activities";
		String sql2 = "delete from projects";
		String sql3 = "delete from members where username <> jdoe";
		try {
			pst = conn.prepareStatement(sql1);
			pst.execute();
			pst = conn.prepareStatement(sql2);
			pst.execute();
			pst = conn.prepareStatement(sql3);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		controller = null;
	}
	
	@BeforeClass
	public static void setup() {		
		PModel.MainController.setTestMode(true);
		
		/*validMemeber = new Member("John Doe", "member", "JDoe", "password123");
		blankMember = new Member("", "", "", "");
		duplicateUsernameMember = new Member("John Doe", "member", "JDoe", "password123");
		deleteMember = new Member("Deleted", "manager", "DELETED2", "123");
		activityTestMember = new Member("Activity Tester", "manager", "acttest", "acttest");
*/
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			date1 = sdf.parse("31-JUN-2014");
			date2 = sdf.parse("31-JUL-2014");
			// Project

			/*validProject = new Project(1, "Valid Project", "This is a valid testing project", d1, d2, 8);
			blankProject = new Project(0, "", "", null, null, 0);
			invalidDatesProject = new Project(1, "Invalid Dates Project", "This is an invalid testing project", d2, d1, 8);
			deleteProject = new Project(1, "Delete Project", "This is a delete-testing project", d1, d2, 6);
			activityProject = new Project(1, "Activity Project", "This is an activity-testing project", d1, d2, 6);

			// Activity
			validActivity = new Activity(1, "Valid Activity", "This is a valid testing activity", d1, d2, 6);
			blankActivity = new Activity(0, "", "", null, null, 0);
			invalidDatesActivity = new Activity(1, "Invalid Activity", "this is an invalid testing activity", d2, d1, 8);*/

		} catch (ParseException e) {
			System.out.println("TestMainController: Improper Date Format");
		}
	}

	@AfterClass
	public static void cleanUp() {
		/*deleteJohnDoe("");
		deleteValidProject("");*/
		
		//TODO: Purge database when finished
	}

	@Test
	public void testLoginValidUsernameAndPassword() {
		assertTrue(controller.Login("jdoe", "password"));
	}

	@Test
	public void testLoginInvalidPassword() {
		assertFalse(controller.Login("jdoe", "badpass"));
	}

	@Test
	public void testLoginInvalidUsername() {
		assertFalse(controller.Login("jdont", "badpass"));
	}

	@Test
	public void testCreateMemberBlank() {
		Member blankMember = new Member("","","","");
		assertNull(controller.CreateMember(blankMember));
	}

	@Test
	public void testCreateMemberDuplicate() {
		// Should return null because no two users can have the same username.
		Member duplicateUsernameMember = new Member("John Doe", "member", "jdoe", "password");
		assertNull(controller.CreateMember(duplicateUsernameMember));
	}

	@Test
	public void testCreateMemberValid() {
		Member newMember = new Member("John Doe 2", "member", "jdoe2", "password");
		assertNotNull(controller.CreateMember(newMember));
		if (!controller.DeleteMember(newMember))
			System.out.println("Failed to delete John Doe in testCreateMemberValid");
		//TODO: Log this
	}

	@Ignore
	@Test
	public void testUpdateMember() {
		fail("UpdateMember(Member member) works only from GUI.");
	}

	@Test
	public void testDeleteMemberByMember() {
		Member deleteMember = new Member("Deleted", "manager", "DELETED2", "123");
		if (controller.CreateMember(deleteMember) != null)
		{
			assertTrue(controller.DeleteMember(deleteMember));
			assertFalse(controller.DeleteMember(deleteMember));
		}
		//TODO: Log if error
	}

	@Test
	public void testDeleteMemberByID() {
		Member deleteMember = new Member("Deleted", "manager", "DELETED2", "123");
		if (controller.CreateMember(deleteMember) != null)
		{
			assertTrue(controller.DeleteMember(deleteMember.getMemberID()));
			assertFalse(controller.DeleteMember(deleteMember));
		}
		//TODO: Log if error
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteMemberByIDInvalid() {
		// attempts to delete a with a non-existing MID
		assertFalse(controller.DeleteMember(Integer.MAX_VALUE));
	}

	@Test
	public void testCreateProjectValid() {
		// ensure that project does not already exist
		Project validProject = new Project(1, "Valid Project", "This is a valid testing project", date1, date2, 8);		
		assertEquals(validProject, controller.CreateProject(validProject));
	}

	@Test
	public void testCreateProjectBlankProject() {
		Project blankProject = new Project(1, "", "", date1, date2, 8);		
		assertNull(controller.CreateProject(blankProject));
	}

	@Test
	public void testCreateProjectDuplicate() {
		Project validProject = new Project(1, "Valid Project", "This is a valid testing project", date1, date2, 8);
		controller.CreateProject(validProject);
		assertNull(controller.CreateProject(validProject));
	}

	@Ignore
	public void testCreateProjectInvalidDates() {
		//project has start date after deadline date
		Project invalidDatesProject = new Project(1, "Valid Project", "This is a valid testing project", date2, date1, 8);
		assertNull(controller.CreateProject(invalidDatesProject));
	}

	@Ignore
	@Test
	public void testUpdateProject() {
		fail("UpdateProject(Project project) is tested in the GUI");
	}

	@Test
	public void testDeleteProjectByIDValid() {
		Project validProject = new Project(1, "Valid Project", "This is a valid testing project", date1, date2, 8);
		assertNotNull(controller.CreateProject(validProject));
		assertTrue(controller.DeleteProject(validProject.getProjectID()));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectByIDInvalid() {
		assertFalse(controller.DeleteProject(Integer.MAX_VALUE));
	}

	@Test
	public void testDeleteProjectByNameValid() {
		Project validProject = new Project(1, "Valid Project", "This is a valid testing project", date1, date2, 8);
		assertNotNull(controller.CreateProject(validProject));
		assertTrue(controller.DeleteProject(validProject.getName()));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectByNameInvalid() {
		assertFalse(controller.DeleteProject("Fake Project"));
	}

	@Test
	public void testCreateActivityValid() {
		loginToTestActivity();
		Activity validActivity = new Activity(1, "Valid Activity", "This is a valid testing activity", date1, date2, 6);
		assertEquals(validActivity, controller.CreateActivity(validActivity));
	}

	@Test
	public void testCreateActivityBlank() {
		loginToTestActivity();
		Activity blankActivity = new Activity(1, "", "", date1, date2, 6);
		assertNull(controller.CreateActivity(blankActivity));
	}

	@Ignore
	public void testCreateActivityInvalidDates() {
		loginToTestActivity();
		Activity invalidDatesActivity = new Activity(1, "Valid Activity", "This is a valid testing activity", date2, date1, 6);
		assertNull(controller.CreateActivity(invalidDatesActivity));	
	}

	@Ignore
	@Test
	public void testUpdateActivity() {
		fail("UpdateActivity(Activity activity) is tested in the GUI");
	}

	@Test
	public void testDeleteActivity() {
		loginToTestActivity();
		Activity validActivity = new Activity(1, "Valid Activity", "This is a valid testing activity", date1, date2, 6);
		assertNotNull(controller.CreateActivity(validActivity));
		assertTrue(controller.DeleteActivity(validActivity.getProjectID(), validActivity.getNumber()));
	}

	private void loginToTestActivity() {
		controller.Login("jdoe", "password");
		Project validProject = new Project(1, "Valid Project", "This is a valid testing project", date1, date2, 8);
		controller.CreateProject(validProject);
		controller.OpenProject(validProject.getName());
	}
}