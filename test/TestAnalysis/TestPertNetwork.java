/**
 * 
 */
package TestAnalysis;

import java.util.ArrayList;

import org.junit.Test;

import Analysis.MilestoneNode;
import Analysis.PertNetwork;
import PModel.Activity;

/**
 *
 */
public class TestPertNetwork {
	
	@Test
	public void testForwardPassPath1(){
		
		Activity testActivity = new Activity(100, "TestActivity", "Disposable test Activity", 10, 10, 8, 12, 10, 1.0, 10, true);
		testActivity.setEarliestFinish(10);
		testActivity.setDurationStandardDevitation(2.0);
		
		ArrayList<Activity> activities =  new ArrayList<Activity>();
		activities.add(testActivity);
		
		MilestoneNode testStartNode = new MilestoneNode(1);
		MilestoneNode testEndNode = new MilestoneNode(2);
		
		testStartNode.addOutArrow(testActivity);
		testEndNode.addInArrow(testActivity);
		
		PertNetwork pn =  new PertNetwork(activities);
		
		pn.linkNodeForward(testStartNode);
		pn.forwardPass();
		
		System.out.println(pn);
		

//		
//		
//		Activity outofTestNode = new Activity(100, "outofTestNode", "Disposable test Activity", 10, 10, 8, 12, 10, 1.0, 10, true);
//		
//		intoTestNode.addDependent(outofTestNode);
//		
//		toTestNode.addOutArrow(intoTestNode);
//		testNode.addInArrow(intoTestNode);
//
//		testNode.addOutArrow(outofTestNode);
//		fromTestNode.addInArrow(outofTestNode);
	}

	@Test
	public void testGetArrowNetwork(){
		
	}
}
