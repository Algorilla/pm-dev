package testPModel;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle.Control;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import PModel.Activity;
import PModel.ActivityOnNodeNetwork;
import PModel.MainController;
import PModel.Member;
import PModel.Project;

public class TestActivityOnNodeNetwork {

	private static MainController controller;

	private static Member member;
	private static Project project;
	
	private static ActivityOnNodeNetwork network;
	
	@BeforeClass
	public static void setup() {		
		controller = PModel.MainController.get();
	}

	@AfterClass
	public static void cleanUp() {
		controller.DeleteProject(project);
		controller.DeleteMember(member);
	}
	
	@Test
	public void testDependantActivity() {		
		member = new Member("John Doe", "member", "JDoe", "password123");
		project = new Project(1, "Valid Project", "This is a valid testing project");		
		controller.CreateMember(member);
		controller.InitializeProject(project);
		controller.OpenProject(project.toString());
		
		Activity firstActivity = new Activity(1, "First Activity", "This is a valid testing activity",10,14,14,14,14);
		Activity dependantActivity = new Activity(1, "Dependant Activity", "This is a valid testing activity",5,5,5,5,19);
		ArrayList<Activity> dependantActivities = new ArrayList<Activity>();
		dependantActivities.add(firstActivity);
		
		controller.CreateActivity(firstActivity);
		controller.CreateActivity(dependantActivity);
		controller.CreateActivityDependencies(dependantActivity, dependantActivities);
		
		network = new ActivityOnNodeNetwork();
				
		//System.out.println(network);
		
		ArrayList<Activity> projectActivities = controller.getActivityListForCurrentProject();
		assertEquals(0.0,projectActivities.get(0).getEarliestStart(),0);
		assertEquals(0.0,projectActivities.get(0).getEarliestFinish(),14);
		assertEquals(0.0,projectActivities.get(0).getLatestStart(),0);
		assertEquals(0.0,projectActivities.get(0).getLatestFinish(),14);
		assertEquals(0.0,projectActivities.get(1).getEarliestStart(),14);
		assertEquals(0.0,projectActivities.get(1).getEarliestFinish(),19);
		assertEquals(0.0,projectActivities.get(1).getLatestStart(),14);
		assertEquals(0.0,projectActivities.get(1).getLatestFinish(),19);
		
		controller.DeleteActivity(firstActivity.getProjectID(),firstActivity.getNumber());
		controller.DeleteActivity(dependantActivity.getProjectID(),dependantActivity.getNumber());
	}
	
	@Test
	public void testForwardAndBackwardPasses()
	{
		//TODO: Implement
	}
}
