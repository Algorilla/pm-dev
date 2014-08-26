package TestAnalysis;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import Analysis.NodeGraph;
import Analysis.MilestoneNode;

public class TestNodeGraph {

	@Test
	public void testAddEdge_whiteBox(){

		
		// Test 1 Input:
		//			-	NodeGraph DOES NOT contain v
		//			-	v DOES NOT contain w
		//			-	w is NOT dependent on v
		//			-	v is NOT a precedent of w
        NodeGraph graph1 = new NodeGraph();
		MilestoneNode v1 = new MilestoneNode(1), 
				      w1 = new MilestoneNode(2);
		graph1.addEdge(v1,  w1);
		
		assertTrue(graph1.hasNode(v1));
		assertTrue(v1.getDependents().contains(w1));
		assertTrue(w1.getPrecedents().contains(v1));

		
		

		// Test 2 Input:
		//			-	NodeGraph contains v
		//			-	v DOES NOT contain w
		//			-	w is NOT dependent on v
		//			-	v is NOT a precedent of w
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
