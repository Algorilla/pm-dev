/**
 * 
 */
package TestAnalysis;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Analysis.MilestoneNode;
import PModel.Activity;

/**
 *
 */
public class TestMilestoneNode {

	@Test
	public void testToStringArrowsPath1(){
		
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
	public void testToStringArrowsPath2(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue("No in arrows".equals(testNode.toStringArrows("in")));
		
	}
	@Test
	public void testToStringArrowsPath5(){
		
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
	public void testToStringArrowsPath6(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue("No out arrows".equals(testNode.toStringArrows("out")));
		
	}
	@Test
	public void testToStringArrowsPath7(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue(testNode.toStringArrows("bad_input") == null);
		
	}
	
}
