package testPModel;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import PModel.Activity;
import PModel.ActivityOnNodeNetwork;
import Controller.MainController;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

public class TestMainController {

	private static Controller.MainController controller;

	// test members
	private static Member validMemeber;
	private static Member blankMember;
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
	private static Project activityProject;

	@BeforeClass
	public static void setup() {

//		controller = Controller.MainController.get();
////		Controller.ErrorController.get().SetShowErrors(false);
//
//		validMemeber = new Member("John Doe", "member", "JDoe", "password123");
//		blankMember = new Member("", "", "", "");
//		duplicateUsernameMember = new Member("John Doe", "member", "JDoe", "password123");
//		deleteMember = new Member("Deleted", "manager", "DELETED2", "123");
//		activityTestMember = new Member("Activity Tester", "manager", "acttest", "acttest");
//
//		// Ensure that JDoe is not in the database prior to testing.
//		assert(!controller.Login("JDoe", "password123"));
//
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
//			Date d1 = sdf.parse("31-JUN-2014");
//			Date d2 = sdf.parse("31-JUL-2014");
//			
//			// Project
//			validProject = new Project(1, "Valid Project", "This is a valid testing project");
//			blankProject = new Project(0, "", "");
//			deleteProject = new Project(1, "Delete Project", "This is a delete-testing project");
//			activityProject = new Project(1, "Activity Project", "This is an activity-testing project");
//
//			// Activity
//			validActivity = new Activity(1, "Valid Activity", "This is a valid testing activity",0,0,0,0,0);
//			blankActivity = new Activity(0, "", "",0,0,0,0,0);
//			
//			// Old objects before overhaul
////			validProject = new Project(1, "Valid Project", "This is a valid testing project", d1, d2, 8);
////			blankProject = new Project(0, "", "", null, null, 0);
////			invalidDatesProject = new Project(1, "Invalid Dates Project", "This is an invalid testing project", d2, d1, 8);
////			deleteProject = new Project(1, "Delete Project", "This is a delete-testing project", d1, d2, 6);
////			activityProject = new Project(1, "Activity Project", "This is an activity-testing project", d1, d2, 6);
////
////			// Activity
////			validActivity = new Activity(1, "Valid Activity", "This is a valid testing activity", d1, d2, 6);
////			blankActivity = new Activity(0, "", "", null, null, 0);
////			invalidDatesActivity = new Activity(1, "Invalid Activity", "this is an invalid testing activity", d2, d1, 8);
//
//		} catch (ParseException e) {
//			System.out.println("TestMainController: Improper Date Format");
//		}
//
//		controller.Login("acttest", "acttest");
//		assertEquals(activityProject, controller.InitializeProject(activityProject));
	}

	@AfterClass
	public static void cleanUp() {
//		deleteJohnDoe("");
//		deleteValidProject("");
//		controller.DeleteProject(blankProject);
//		//controller.DeleteProject(invalidDatesProject);
//		controller.DeleteProject(deleteProject);
//		controller.DeleteProject(activityProject);
//		controller.DeleteMember(activityTestMember);
	}

	@Test
	public void testLoginValidUsernameAndPassword() {
//		assertTrue(controller.Login("root", "root"));
	}

	@Test
	public void testLoginInvalidPassword() {
//		assertFalse(controller.Login("root", "rot"));
	}

	@Test
	public void testLoginInvalidUsername() {
//		assertFalse(controller.Login("FailedUsername", "FailedPassword"));
	}

	@Test
	public void testCreateMemberBlank() {
//		assertNull(controller.CreateMember(blankMember));
	}

	@Test
	public void testCreateMemberDuplicate() {
		// Should return null because no two users can have the same username.
//		assertNull(controller.CreateMember(duplicateUsernameMember));
	}

	@Test
	public void testCreateMemberValid() {
//		deleteJohnDoe("testCreateMemberValid");
//		assertNotNull(controller.CreateMember(validMemeber));
	}

	@Ignore
	@Test
	public void testUpdateMember() {
		fail("UpdateMember(Member member) works only from GUI.");
	}

	@Test
	public void testDeleteMemberByMember() {
//		controller.CreateMember(deleteMember);
//		assertTrue(controller.DeleteMember(deleteMember));
//		assertFalse(controller.DeleteMember(deleteMember));
	}

	@Test
	public void testDeleteMemberByID() {
//		controller.CreateMember(deleteMember);
//		assertTrue(controller.DeleteMember(deleteMember.getMemberID()));
//		assertFalse(controller.DeleteMember(deleteMember));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteMemberByIDInvalid() {
		// attempts to delete a with a non-existing MID
//		assertFalse(controller.DeleteMember(Integer.MAX_VALUE));
	}

	@Test
	public void testCreateProjectValid() {
		// ensure that project does not already exist
		deleteValidProject("testCreateProjectValid");

//		assertEquals(validProject, controller.InitializeProject(validProject));
	}

	@Test
	public void testCreateProjectBlankProject() {
//		assertNull(controller.InitializeProject(blankProject));
	}

	@Test
	public void testCreateProjectDuplicate() {
		deleteValidProject("testCreateProjectDuplicate");

//		assertEquals(validProject, controller.InitializeProject(validProject));
//		assertNull(controller.InitializeProject(validProject));
	}

	/*@Test
	public void testCreateProjectInvalidDates() {
		//project has start date after deadline date
		assertNull(controller.InitializeProject(invalidDatesProject));
	}*/

	@Ignore
	@Test
	public void testUpdateProject() {
		fail("UpdateProject(Project project) is tested in the GUI");
	}

	@Test
	public void testDeleteProjectByIDValid() {
//		assertNotNull(controller.InitializeProject(deleteProject));
//		assertTrue(controller.DeleteProject(deleteProject.getProjectID()));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectByIDInvalid() {
//		assertFalse(controller.DeleteProject(Integer.MAX_VALUE));
	}

	@Test
	public void testDeleteProjectByNameValid() {
//		assertNotNull(controller.InitializeProject(deleteProject));
//		assertTrue(controller.DeleteProject(deleteProject.getName()));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testDeleteProjectByNameInvalid() {
//		assertFalse(controller.DeleteProject("Fake Project"));
	}

	@Test
	public void testCreateActivityValid() {
		loginToTestActivity();
//		assertEquals(validActivity, controller.CreateActivity(validActivity));
	}

	@Test
	public void testCreateActivityBlank() {
		loginToTestActivity();
//		assertNull(controller.CreateActivity(blankActivity));	
	}

	/*@Test
	public void testCreateActivityInvalid() {
		loginToTestActivity();
		assertNull(controller.CreateActivity(invalidDatesActivity));	
	}*/

	@Ignore
	@Test
	public void testUpdateActivity() {
		fail("UpdateActivity(Activity activity) is tested in the GUI");
	}

	@Test
	public void testDeleteActivity() {
		loginToTestActivity();
		boolean validActivityInCurrentProject = false;
		for (Activity act : controller.getActivityListForCurrentProject()) {
			// verify whether validActivity is in the current project
			if (act.getNumber() == validActivity.getNumber() &&
				act.getName() == validActivity.getName() &&
				act.getProjectID() == validActivity.getProjectID()) {
					validActivityInCurrentProject = true;
					break;
			}
		}
		
		if (validActivityInCurrentProject) {
//			assertTrue(controller.DeleteActivity(validActivity.getProjectID(),
//					validActivity.getNumber()));
		} else {
//			assertNotNull(controller.CreateActivity(validActivity));
//			assertTrue(controller.DeleteActivity(validActivity.getProjectID(), validActivity.getNumber()));
		}
	}

	// Helper methods used by some unit tests
	private static void deleteJohnDoe(String callingMethod) {
//		if (controller.Login("JDoe", "password123") && controller.DeleteMember(controller.GetCurrentUser())) {
//			if (!callingMethod.equals("")) {
//				System.out.println(callingMethod + " deleted newProject prior to test");
//			}
//		}
	}

	private static void deleteValidProject(String callingMethod) {
		// ensure that project does not already exist
//		if (controller.DeleteProject(validProject)) {
//			if (!callingMethod.equals("")) {
//				System.out.println(callingMethod + " deleted validProject prior to test");
//			}
//		}
	}
	
	@Test public void testInitializeMemberActivity()
	{
//		Member memberActivityMember = controller.CreateMember(new Member("MemberActivity", "member", "MemberActivityMember", "password123"));
//		loginToTestActivity();
//		Activity memberActivityActivity = controller.CreateActivity(new Activity(controller.GetCurrentProject().getProjectID(), "MemberActivityActivity", "This is a valid testing activity",0,0,0,0,0) );
//		
//		MemberActivity validMemberActivity = new MemberActivity(memberActivityMember.getMemberID(),memberActivityActivity.getProjectID(),memberActivityActivity.getNumber());
//		assertNotNull(controller.InitializeMemberActivity(validMemberActivity));
//		
//		controller.DeleteActivity(memberActivityActivity.getProjectID(),memberActivityActivity.getNumber());
//		controller.DeleteMember(memberActivityMember);
	}
	
	@Test public void testGetDependantActivities()
	{
//		loginToTestActivity();
//		Activity firstActivity = new Activity(controller.GetCurrentProject().getProjectID(), "First Activity", "This is a valid testing activity",10,14,14,14,14);
//		Activity secondActivity = new Activity(controller.GetCurrentProject().getProjectID(), "First Activity", "This is a valid testing activity",10,14,14,14,14);
//		Activity dependantActivity = new Activity(controller.GetCurrentProject().getProjectID(), "Dependant Activity", "This is a valid testing activity",5,5,5,5,19);
//		ArrayList<Activity> dependantActivities = new ArrayList<Activity>();
//		dependantActivities.add(firstActivity);
//		dependantActivities.add(secondActivity);
//		
//		controller.CreateActivity(firstActivity);
//		controller.CreateActivity(secondActivity);
//		controller.CreateActivity(dependantActivity);
//		controller.CreateActivityDependencies(dependantActivity, dependantActivities);
//		
//		ArrayList<Integer> retrievedDependantActivities = controller.getDependantActivities(dependantActivity);
//		assertEquals(2,retrievedDependantActivities.size());
//		assertEquals(1,retrievedDependantActivities.get(0).intValue());
//		assertEquals(2,retrievedDependantActivities.get(1).intValue());
//		
//		controller.DeleteActivity(firstActivity.getProjectID(),firstActivity.getNumber());
//		controller.DeleteActivity(secondActivity.getProjectID(),secondActivity.getNumber());
//		controller.DeleteActivity(dependantActivity.getProjectID(),dependantActivity.getNumber());
	}
	
	@Test public void testGetDependantActivitiesWhenThereAreNone()
	{
//		loginToTestActivity();
//		Activity firstActivity = new Activity(controller.GetCurrentProject().getProjectID(), "First Activity", "This is a valid testing activity",10,14,14,14,14);
//		
//		controller.CreateActivity(firstActivity);
//		
//		ArrayList<Integer> retrievedDependantActivities = controller.getDependantActivities(firstActivity);
//		assertEquals(0,retrievedDependantActivities.size());
//		
//		controller.DeleteActivity(firstActivity.getProjectID(),firstActivity.getNumber());
	}

	private static void loginToTestActivity() {
//		controller.Login("acttest", "acttest");
//		controller.OpenProject(activityProject.getName());
	}
}