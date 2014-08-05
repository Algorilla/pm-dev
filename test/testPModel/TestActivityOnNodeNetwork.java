//package testPModel;
//
//import static org.junit.Assert.*;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.ResourceBundle.Control;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import PModel.Activity;
//import PModel.ActivityOnNodeNetwork;
//import PModel.MainController;
//import PModel.Member;
//import PModel.Project;
//
//public class TestActivityOnNodeNetwork {
//
//	private static MainController controller;
//
//	private static Member member;
//	private static Project project;
//	
//	private static ActivityOnNodeNetwork network;
//	
//	@BeforeClass
//	public static void setup() {		
//		controller = PModel.MainController.get();
//		member = new Member("John Doe", "member", "JDoeNodeNetwork", "password123");
//		project = new Project(1, "Valid Project Node Network", "This is a valid testing project");
//		controller.CreateMember(member);
//		controller.InitializeProject(project);
//		controller.OpenProject(project.toString());
//	}
//
//	@AfterClass
//	public static void cleanUp() {
//		controller.DeleteProject(project);
//		controller.DeleteMember(member);
//	}
//	
//	@Test
//	public void testDependantActivity() 
//	{	
//		Activity firstActivity = new Activity(1, "First Activity", "This is a valid testing activity",10,14,14,14,14);
//		Activity dependantActivity = new Activity(1, "Dependant Activity", "This is a valid testing activity",5,5,5,5,19);
//		ArrayList<Activity> dependantActivities = new ArrayList<Activity>();
//		dependantActivities.add(firstActivity);
//		
//		controller.CreateActivity(firstActivity);
//		controller.CreateActivity(dependantActivity);
//		controller.CreateActivityDependencies(dependantActivity, dependantActivities);
//		
//		network = new ActivityOnNodeNetwork();
//				
//		//System.out.println(network);
//		
//		ArrayList<Activity> projectActivities = controller.getActivityListForCurrentProject();
//		assertEquals(0.0,projectActivities.get(0).getEarliestStart(),0);
//		assertEquals(0.0,projectActivities.get(0).getEarliestFinish(),14);
//		assertEquals(0.0,projectActivities.get(0).getLatestStart(),0);
//		assertEquals(0.0,projectActivities.get(0).getLatestFinish(),14);
//		assertEquals(0.0,projectActivities.get(1).getEarliestStart(),14);
//		assertEquals(0.0,projectActivities.get(1).getEarliestFinish(),19);
//		assertEquals(0.0,projectActivities.get(1).getLatestStart(),14);
//		assertEquals(0.0,projectActivities.get(1).getLatestFinish(),19);
//		
//		controller.DeleteActivity(firstActivity.getProjectID(),firstActivity.getNumber());
//		controller.DeleteActivity(dependantActivity.getProjectID(),dependantActivity.getNumber());
//	}
//	
//	@Test
//	public void testForwardAndBackwardPasses()
//	{		
//		Activity s = new Activity(1, "S", "Specification",10,14,14,14,14);
//		Activity dca = new Activity(1, "DCA", "Design and Code",5,5,5,5,19);
//		Activity dcb = new Activity(1,"DCB","Design and Code",5,10,10,10,24);
//		Activity dcc = new Activity(1,"DCC","Design and Code",5,10,10,10,24);	
//		Activity utab = new Activity(1,"UTAB","Unit Test A and B",10,14,14,14,28);	
//		Activity utc = new Activity(1,"UTC","Unit Test A and B",5,8,8,8,32);
//		Activity p = new Activity(1,"P","Planning of Integrated System Test",10,10,10,10,38);
//		Activity ist = new Activity(1,"IST","Implementation of Integrated System Test",20,21,21,21,69);
//		
//		controller.CreateActivity(s);
//		controller.CreateActivity(dca);
//		controller.CreateActivity(dcb);
//		controller.CreateActivity(dcc);
//		controller.CreateActivity(utab);
//		controller.CreateActivity(utc);
//		controller.CreateActivity(p);
//		controller.CreateActivity(ist);
//		
//		ArrayList<Activity> dependantActivities = new ArrayList<Activity>();
//		dependantActivities.add(s);
//		controller.CreateActivityDependencies(dca, dependantActivities);
//		controller.CreateActivityDependencies(dcb, dependantActivities);
//		controller.CreateActivityDependencies(dcc, dependantActivities);
//		dependantActivities = new ArrayList<Activity>();
//		dependantActivities.add(dca);
//		dependantActivities.add(dcb);
//		controller.CreateActivityDependencies(utab, dependantActivities);
//		dependantActivities = new ArrayList<Activity>();
//		dependantActivities.add(dcc);
//		controller.CreateActivityDependencies(utc, dependantActivities);
//		dependantActivities = new ArrayList<Activity>();
//		dependantActivities.add(utab);
//		dependantActivities.add(utc);
//		controller.CreateActivityDependencies(p, dependantActivities);
//		dependantActivities = new ArrayList<Activity>();
//		dependantActivities.add(p);
//		controller.CreateActivityDependencies(ist, dependantActivities);
//		
//		network = new ActivityOnNodeNetwork();
//				
//		//System.out.println(network);
//		
//		ArrayList<Activity> projectActivities = controller.getActivityListForCurrentProject();
//		
//		// S
//		assertEquals(0.0,projectActivities.get(0).getEarliestStart(),0);
//		assertEquals(14,projectActivities.get(0).getEarliestFinish(),0);
//		assertEquals(0.0,projectActivities.get(0).getLatestStart(),0);
//		assertEquals(14,projectActivities.get(0).getLatestFinish(),0);
//		
//		// DCA
//		assertEquals(14,projectActivities.get(1).getEarliestStart(),0);
//		assertEquals(19,projectActivities.get(1).getEarliestFinish(),0);
//		assertEquals(19,projectActivities.get(1).getLatestStart(),0);
//		assertEquals(24,projectActivities.get(1).getLatestFinish(),0);		
//		
//		// DCB
//		assertEquals(14,projectActivities.get(2).getEarliestStart(),0);
//		assertEquals(24,projectActivities.get(2).getEarliestFinish(),0);
//		assertEquals(14,projectActivities.get(2).getLatestStart(),0);
//		assertEquals(24,projectActivities.get(2).getLatestFinish(),0);
//		
//		// DCC
//		assertEquals(14,projectActivities.get(3).getEarliestStart(),0);
//		assertEquals(24,projectActivities.get(3).getEarliestFinish(),0);
//		assertEquals(20,projectActivities.get(3).getLatestStart(),0);
//		assertEquals(30,projectActivities.get(3).getLatestFinish(),0);
//		
//		// UTAB
//		assertEquals(24,projectActivities.get(4).getEarliestStart(),0);
//		assertEquals(38,projectActivities.get(4).getEarliestFinish(),0);
//		assertEquals(24,projectActivities.get(4).getLatestStart(),0);
//		assertEquals(38,projectActivities.get(4).getLatestFinish(),0);
//		
//		// UTC
//		assertEquals(24,projectActivities.get(5).getEarliestStart(),0);
//		assertEquals(32,projectActivities.get(5).getEarliestFinish(),0);
//		assertEquals(30,projectActivities.get(5).getLatestStart(),0);
//		assertEquals(38,projectActivities.get(5).getLatestFinish(),0);
//		
//		// P
//		assertEquals(38,projectActivities.get(6).getEarliestStart(),0);
//		assertEquals(48,projectActivities.get(6).getEarliestFinish(),0);
//		assertEquals(38,projectActivities.get(6).getLatestStart(),0);
//		assertEquals(48,projectActivities.get(6).getLatestFinish(),0);
//		
//		// IST
//		assertEquals(48,projectActivities.get(7).getEarliestStart(),0);
//		assertEquals(69,projectActivities.get(7).getEarliestFinish(),0);
//		assertEquals(48,projectActivities.get(7).getLatestStart(),0);
//		assertEquals(69,projectActivities.get(7).getLatestFinish(),0);
//		
//		controller.DeleteActivity(s.getProjectID(),s.getNumber());
//		controller.DeleteActivity(dca.getProjectID(),dca.getNumber());
//		controller.DeleteActivity(dcb.getProjectID(),dcb.getNumber());
//		controller.DeleteActivity(dcc.getProjectID(),dcc.getNumber());
//		controller.DeleteActivity(utab.getProjectID(),utab.getNumber());
//		controller.DeleteActivity(utc.getProjectID(),utc.getNumber());
//		controller.DeleteActivity(p.getProjectID(),p.getNumber());
//		controller.DeleteActivity(ist.getProjectID(),ist.getNumber());
//	}
//}
