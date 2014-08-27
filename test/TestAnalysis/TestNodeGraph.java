package TestAnalysis;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import Analysis.NodeGraph;
import Analysis.MilestoneNode;

public class TestNodeGraph {

	////////////////////////////// WHITEBOX ////////////////////////////////////////////////////////////////////	

	/**
	 * White-Box Test for addEdge()
	 */
	@Test
	public void testAddEdge_whiteBox(){

		/**
		 * Function to test:	NodeGraph::addEdge()
		 * Identifier:			addEdge_whiteBoxPath_1
		 * Description:			Test for basis-coverage path 1
		 * Preconditions:	
		 * -	NodeGraph DOES NOT contain v
		 * -	v DOES NOT contain w
		 * -	w is NOT dependent on v
		 * -	v is NOT a precedent of w
		 * Inputs:
		 * -	MilestoneNode v1, w1;
		 * Outputs:
		 * -	void
		 * PostConditions:
		 * -	NodeGraph contains v1
		 * -	v1 is precedent of w1
		 * -	w1 is dependent of v1
		 */
        NodeGraph graph1 = new NodeGraph();
		MilestoneNode v1 = new MilestoneNode(1), 
				      w1 = new MilestoneNode(2);
		graph1.addEdge(v1,  w1);
		
		assertTrue(graph1.hasNode(v1));
		assertTrue(v1.getDependents().contains(w1));
		assertTrue(w1.getPrecedents().contains(v1));


		/**
		 * Function to test:	NodeGraph::addEdge()
		 * Identifier:			addEdge_whiteBoxPath_2
		 * Description:			Test for basis-coverage path 2
		 * Preconditions:	
		 * -	NodeGraph contains v
		 * -	v DOES NOT contain w
		 * -	w is NOT dependent on v
		 * -	v is NOT a precedent of w
		 * Inputs:
		 * -	MilestoneNode v2, w2, dummy;
		 * Outputs:
		 * -	void
		 * PostConditions:
		 * -	NodeGraph contains v2
		 * -	v2 is precedent of w2
		 * -	w2 is dependent of v2
		 */
		NodeGraph graph2 = new NodeGraph();
		MilestoneNode v2 = new MilestoneNode(1), 
				      w2 = new MilestoneNode(2), 
		           dummy = new MilestoneNode(3);
		graph2.addEdge(dummy,  v2);
		graph2.addEdge(v2,  w2);

		assertTrue(graph1.hasNode(v2));
		assertTrue(v2.getDependents().contains(w2));
		assertTrue(w2.getPrecedents().contains(v2));
	}
	
	
}
