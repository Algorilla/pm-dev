/**
 * 
 */
package Analysis;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;

import PModel.Activity;

/**
 *
 */
public class PertNetwork {
	
	static int index = 1;
	
	private ArrayList<Activity> activities;
	private RegularNode 		start;
	private RegularNode 		finish;
	private NodeGraph			graph;
	
	private static NormalDistribution normalDist;

	
	public PertNetwork(ArrayList<Activity> activities){
		index = 1;
		this.activities = activities;
		this.start 		= new RegularNode(index++);
		this.finish 	= new RegularNode(Integer.MAX_VALUE);
		this.graph 		= new NodeGraph();
		
		this.normalDist =  new NormalDistribution();
		
		this.removeDummies();
				
	}
	
	/**
	 * 
	 */
	private void removeDummies() {
		
		ArrayList<Activity> temp = new ArrayList<Activity>();
		
		for(Activity a : this.activities){
			if(a.getName() == "DummyStart" || a.getName() == "DummyFinish"){
				temp.add(a);
			}
		}
		for(Activity dum : temp){
			if(activities.contains(dum)){
				activities.remove(dum);
			}
		}
		temp.clear();
		
		
		
		for(Activity a : this.activities){
			for(Activity dep : a.getDependents()){
				if(dep.getName() == "DummyStart" || dep.getName() == "DummyFinish"){
					temp.add(dep);
				}
			}
			for(Activity dep : temp){
				if(a.getDependents().contains(dep)){
					a.getDependents().remove(dep);
				}
			}
			temp.clear();
		}
		
		
		
		for(Activity a : this.activities){
			for(Activity pre : a.getPrecedents()){
				if(pre.getName() == "DummyStart" || pre.getName() == "DummyFinish"){
					temp.add(pre);
				}
			}
			for(Activity pre : temp){
				if(a.getPrecedents().contains(pre)){
					a.getPrecedents().remove(pre);
				}
			}
			temp.clear();
		}
		
	}

	public static PertNetwork getArrowNetwork(ArrayList<Activity> activities){
		
		PertNetwork an = new PertNetwork(activities);
		
		// This is the default
		an.start.setExpectedDate(0);

		// All unconditioned activities leave the start node
		for(Activity a : activities){
			if(a.getPrecedents().size() == 0){
				an.start.addOutArrow(a);
			}
		}
		
		// All activities without dependents feed into the final node
		for(Activity a : activities){
			if(a.getDependents().size() == 0){
				an.finish.addInArrow(a);
			}
		}
		
		// Recursively connects the graph
		an.linkNodeForward(an.start);
		
		// Now we know the last node number and set it
		an.finish.setName(index);
		
		an.forwardPass();

		return an;
	}

	/**
	 * @param node
	 */
	private void forwardPass() {
		
		double 	expectedDate = 0,
				expectedSrdDev = 0,
				maxStdDev = 0;
		
		for(RegularNode n : this.graph.getNodes().keySet()){
			
			expectedDate = 0;
			expectedSrdDev = 0;
			maxStdDev = 0;
			
			for(Activity a : n.getInArrows()){
				if(a.getEarliestFinish() > expectedDate){
					expectedDate = a.getEarliestFinish();
				}
			}
			
			for(Activity a : n.getInArrows()){
				
				expectedSrdDev = Math.pow(a.getDurationStandardDevitation(), 2);
				
				for(RegularNode n1 : n.getPrecedents()){
					if(n1.getOutArrows().contains(a)){
						expectedSrdDev += Math.pow(n1.getStandardDeviation(), 2);
					}
				}
				
				if(Math.sqrt(expectedSrdDev) > maxStdDev){
					maxStdDev = Math.sqrt(expectedSrdDev);
				}
			}
			
			n.setExpectedDate(expectedDate);
			n.setStandardDeviation(maxStdDev);
		}
		
	}

	/**
	 * 
	 */
	public void reverse() {
		
		ArrayList<Activity> tempActivities = null;
		ArrayList<RegularNode> tempNodes   = null;
		
		for(RegularNode node : this.graph.getNodes().keySet()){
			tempActivities = node.getInArrows();
			node.setInArrows(node.getOutArrows());
			node.setOutArrows(tempActivities);
		}
		
		
	}

	/**
	 * @param node
	 */
	private void linkNodeForward(RegularNode node) {
		
		boolean found = false;
		
		// Base case for recursive function
		if(node.equals(this.finish)){
			if(!this.graph.getNodes().keySet().contains(this.finish)){
				this.graph.getNodes().put(finish, new ArrayList<RegularNode>());
			}
//			node.name=(index);
			return;
		}
		
		// Initialized to comfort the compiler
		RegularNode tempNode = null;
		
		// Arrows are Activities
		for(Activity a : node.getOutArrows()){
			found = false;
			
			// Those activities depending on a may already be arrows out of some existing node.
			for(Activity dep : a.getDependents()){
				for(RegularNode n : this.graph.getNodes().keySet()){
					
					// If one such node exists, add 'a' to its incoming arrow list
					if(n.getOutArrows().contains(dep)){
						if(!n.getInArrows().contains(a)){
							n.addInArrow(a);
						}
						
						// And add to the out going arrow list any other activity that depends on 'a'
						for(Activity out : a.getDependents()){
							if(!n.getOutArrows().contains(out)){
								n.addOutArrow(out);
							}
						}
						// This is the node that we want to connect to.
						tempNode = n;
						found = true;
						break;
					}
				}
				if(found){
					break;
				}
			}
			// If a node did not exist, we create one
			if(!found){
				// Unless it's without dependents, then this node should connect to the final node
				if(a.getDependents().size() == 0){
					
					tempNode = this.finish;
					if(!tempNode.getInArrows().contains(a)){
						tempNode.addInArrow(a);
					}
					
				}else{
					
					tempNode =  new RegularNode(index++);
					tempNode.addInArrow(a);
					
					for(Activity out : a.getDependents()){
						tempNode.addOutArrow(out);
					}
				}
				
				this.graph.addEdge(node, tempNode);
			}	
			this.linkNodeForward(tempNode);
		}
		
	}

	/**
	 * @return the activities
	 */
	public ArrayList<Activity> getActivities() {
		return activities;
	}

	/**
	 * @return the graph
	 */
	public NodeGraph getGraph() {
		return graph;
	}

	/**
	 * @return the normalDist
	 */
	public static NormalDistribution getNormalDist() {
		return normalDist;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String nodeString = "";
		
		for(RegularNode n : this.graph.getNodes().keySet()){
			nodeString += n.toString() + "\tED: " + n.getExpectedDate()
					+ "\tSTD: " + n.getStandardDeviation();
			
			nodeString += "\n";
		}
		
//		for(Activity a : activities){
//			nodeString += a.getName() + " EF " + a.getEarliestFinish() + "\n";
//		}
		
//		for(RegularNode n : this.graph.getNodes().keySet()){
//			nodeString += "{";
//			for(Activity a : n.getInArrows()){
//				nodeString += a.getName() + ", ";
//			}
//			if(nodeString.length() > 2){
//				nodeString = nodeString.substring(0, nodeString.length() - 2);
//			}
//			
//			nodeString += "} -> ";
//			
//			nodeString += "[" + Integer.toString(n.name) + "]";
//			nodeString += " -> {";
//			for(Activity a : n.getOutArrows()){
//				nodeString += a.getName() + ", " ;
//			}
//			if(nodeString.charAt(nodeString.length() - 2) == ','){
//				nodeString = nodeString.substring(0, nodeString.length() - 2);
//			}
//			nodeString += "}\n";
//		}

		
		return nodeString;
	}
	

}
