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

	////////////////////////////// WHITEBOX ////////////////////////////////////////////////////////////////////	

	
	@Test
	public void testEquals_whiteBox(){
		MilestoneNode caller;
		Object		  other;
		
		/**
		 * Function to test:	MilestoneNode::equals()
		 * Identifier:			equals_whiteBoxPath_1
		 * Description:			Test for basis-coverage path 1
		 * Preconditions:
		 * -	other is NOT equal to caller
		 * -	other is NOT null
		 * - 	other class is of type MilestoneNode
		 * -	caller name NOT null
		 * - 	other name is same as caller
		 * Inputs:
		 * -	Object other;
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	returns true
		 */
		caller = new MilestoneNode(1);
		other  = new MilestoneNode(1);
		assertTrue(caller.equals(other));
		

		/**
		 * Function to test:	MilestoneNode::equals()
		 * Identifier:			equals_whiteBoxPath_2
		 * Description:			Test for basis-coverage path 2
		 * Preconditions:
		 * -	other is equal to caller
		 * Inputs:
		 * -	Object other;
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	returns true
		 */
		assertTrue(caller.equals(caller));


		/**
		 * Function to test:	MilestoneNode::equals()
		 * Identifier:			equals_whiteBoxPath_3
		 * Description:			Test for basis-coverage path 3
		 * Preconditions:
		 * -	other is NOT equal to caller
		 * -	other is null
		 * Inputs:
		 * -	Object other;
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	returns false
		 */
		other = null;
		assertFalse(caller.equals(other));


		/**
		 * Function to test:	MilestoneNode::equals()
		 * Identifier:			equals_whiteBoxPath_4
		 * Description:			Test for basis-coverage path 4
		 * Preconditions:
		 * -	other is NOT equal to caller
		 * -	other is NOT null
		 * - 	other class is NOT of type MilestoneNode
		 * Inputs:
		 * -	Object other;
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	returns false
		 */
		other = new String();
		assertFalse(caller.equals(other));


		/**
		 * Function to test:	MilestoneNode::equals()
		 * Identifier:			equals_whiteBoxPath_7
		 * Description:			Test for basis-coverage path 7
		 * Preconditions:
		 * -	other is NOT equal to caller
		 * -	other is NOT null
		 * - 	other class is of type MilestoneNode
		 * -	caller name NOT null
		 * - 	other name is NOT same as caller
		 * Inputs:
		 * -	Object other;
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	returns false
		 */
		other = new MilestoneNode(2);
		assertFalse(caller.equals(other));


	}
	
	
	/**
	 * Function to test:	MilestoneNode::toStringArrows()
	 * Identifier:			toStringArrows_whiteBoxPath_1
	 * Description:			Test for basis-coverage path 1
	 * Preconditions:
	 * -	type = "in"
	 * -	MilestoneNode has ONE inArrow
	 * -	MilestoneNode has ONE inArrow
	 * Inputs:
	 * -	String type;
	 * Outputs:
	 * -	void
	 * PostConditions:
	 * -	outputs correct string
	 */
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
	
	/**
	 * Function to test:	MilestoneNode::toStringArrows()
	 * Identifier:			toStringArrows_whiteBoxPath_2
	 * Description:			Test for basis-coverage path 2
	 * Preconditions:
	 * -	type = "in"
	 * -	MilestoneNode has NO inArrow
	 * Inputs:
	 * -	String type;
	 * Outputs:
	 * -	void
	 * PostConditions:
	 * -	outputs correct string
	 */
	@Test
	public void testToStringArrows_whiteBoxPath2(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue("No in arrows".equals(testNode.toStringArrows("in")));
		
	}
	
	/**
	 * Function to test:	MilestoneNode::toStringArrows()
	 * Identifier:			toStringArrows_whiteBoxPath_5
	 * Description:			Test for basis-coverage path 5
	 * Preconditions:
	 * -	type = "in"
	 * -	MilestoneNode has ONE inArrow
	 * -	MilestoneNode has ONE inArrow
	 * Inputs:
	 * -	String type;
	 * Outputs:
	 * -	void
	 * PostConditions:
	 * -	outputs correct string
	 */
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
	
	/**
	 * Function to test:	MilestoneNode::toStringArrows()
	 * Identifier:			toStringArrows_whiteBoxPath_6
	 * Description:			Test for basis-coverage path 6
	 * Preconditions:
	 * -	type = "out"
	 * -	MilestoneNode has NO outArrows
	 * Inputs:
	 * -	String type;
	 * Outputs:
	 * -	void
	 * PostConditions:
	 * -	outputs correct string
	 */
	@Test
	public void testToStringArrows_whiteBoxPath6(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue("No out arrows".equals(testNode.toStringArrows("out")));
		
	}
	
	/**
	 * Function to test:	MilestoneNode::toStringArrows()
	 * Identifier:			toStringArrows_whiteBoxPath_7
	 * Description:			Test for basis-coverage path 7
	 * Preconditions:
	 * -	type != "in" or "out"
	 * Inputs:
	 * -	String type;
	 * Outputs:
	 * -	void
	 * PostConditions:
	 * -	outputs null
	 */
	@Test
	public void testToStringArrows_whiteBoxPath7(){
		
		MilestoneNode testNode = new MilestoneNode(1);
		
		assertTrue(testNode.toStringArrows("bad_input") == null);
		
	}
	
}
