/**
 * 
 */
package TestAnalysis;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import Analysis.MilestoneNode;
import PModel.Activity;


/**
 *
 */
public class TestMilestoneNode {

	
	@Test
	public void testEquals_whiteBox(){
		MilestoneNode caller;
		Object		  other;
		
		// Test 1 Input:
		//		- obj is NOT equal to calling MilestoneNode
		//		- obj is NOT null
		//		- obj class is of type MilestoneNode
		//		- Caller's name NOT null
		//		- obj name is same as calling MilestoneNode
		//		return true;
		caller = new MilestoneNode(1);
		other  = new MilestoneNode(1);
		assertTrue(caller.equals(other));
		
		// Test 2 Input:
		//		- obj is equal to calling MilestoneNode
		//		return true;
		assertTrue(caller.equals(caller));


		// Test 3 Input:
		//		- obj is not equal to calling MilestoneNode
		//		- obj is null
		//		return false;
		other = null;
		assertFalse(caller.equals(other));


		// Test 4 Input:
		//		- obj is not equal to calling MilestoneNode
		//		- obj is not null	
		//		- obj class is NOT of type MilestoneNode
		//		return false;
		other = new String();
		assertFalse(caller.equals(other));


		// Test 7 Input:
		//		- obj is NOT equal to calling MilestoneNode
		//		- obj is NOT null
		//		- obj class is of type MilestoneNode
		//		- Caller's name NOT null
		//		- obj name is NOT same as calling MilestoneNode
		//		- return false
		other = new MilestoneNode(2);
		assertFalse(caller.equals(other));


	}
	
	@Test
	public void testToStringArrows_whiteBoxPath1(){
		
		MilestoneNode testNode = new MilestoneNode(2);
		MilestoneNode toTestNode = new MilestoneNode(1);
		MilestoneNode fromTestNode = new MilestoneNode(3);
		
		Activity intoTestNode = new Activity(100, "intoTestNode", "Disposable test Activity", 10, 10, 8, 12, 10, 1.0, 10, true);
		Activity outofTestNode = new Activity(100, "outofTestNode", "Disposable test Activity", 10, 10, 8, 12, 10, 1.0, 10, true);
		
		intoTestNode.addDependent(outofTestNode);
		
		toTestNode.addOutArrow(intoTestNode);
		testNode.addInArrow(intoTestNode);

		testNode.addOutArrow(outofTestNode);
		fromTestNode.addInArrow(outofTestNode);
		
		
		assertTrue("Activities to Complete by this Milestone: intoTestNode".equals(testNode.toStringArrows("in")));

	}
	
	@Test
	public void testToStringArrows_whiteBoxPath2(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue("No in arrows".equals(testNode.toStringArrows("in")));
		
	}
	
	@Test
	public void testToStringArrows_whiteBoxPath5(){
		
		MilestoneNode testNode = new MilestoneNode(2);
		MilestoneNode toTestNode = new MilestoneNode(1);
		MilestoneNode fromTestNode = new MilestoneNode(3);
		
		Activity intoTestNode = new Activity(100, "intoTestNode", "Disposable test Activity", 10, 10, 8, 12, 10, 1.0, 10, true);
		Activity outofTestNode = new Activity(100, "outofTestNode", "Disposable test Activity", 10, 10, 8, 12, 10, 1.0, 10, true);
		
		intoTestNode.addDependent(outofTestNode);
		
		toTestNode.addOutArrow(intoTestNode);
		testNode.addInArrow(intoTestNode);

		testNode.addOutArrow(outofTestNode);
		fromTestNode.addInArrow(outofTestNode);
		
		assertTrue("Activities to Available to start after this Milestone: outofTestNode".equals(testNode.toStringArrows("out")));

	}
	
	@Test
	public void testToStringArrows_whiteBoxPath6(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue("No out arrows".equals(testNode.toStringArrows("out")));
		
	}
	
	@Test
	public void testToStringArrows_whiteBoxPath7(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue(testNode.toStringArrows("bad_input") == null);
		
	}
	
}
