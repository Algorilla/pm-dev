/**
 * 
 */
package Analysis;

import java.util.ArrayList;

import PModel.Activity;

/**
 *
 */
public class ArrowNetwork {
	
	static int index = 1;
	
	private ArrayList<Activity> activities;
	private RegularNode 		start;
	private RegularNode 		finish;
	private NodeGraph			graph;

	
	public ArrowNetwork(ArrayList<Activity> activities){
		index = 1;
		this.activities = activities;
		this.start 		= new RegularNode(index++);
		this.finish 	= new RegularNode(Integer.MAX_VALUE);
		this.graph 		= new NodeGraph();
				
	}
	
	public static ArrowNetwork getArrowNetwork(ArrayList<Activity> activities){
		
		ArrowNetwork fan = new ArrowNetwork(activities);
		


		for(Activity a : activities){
			if(a.getPrecedents().size() == 0){
				fan.start.addOutArrow(a);
			}
		}
		
		for(Activity a : activities){
			if(a.getDependents().size() == 0){
				fan.finish.addInArrow(a);
			}
		}
		
		fan.linkNodeForward(fan.start);
		fan.finish.name = index;
		fan.reverse();
		return fan;
	}

	/**
	 * 
	 */
	private void reverse() {
		ArrayList<Activity> temp = null;
		for(RegularNode node : this.graph.getNodes().keySet()){
			temp = node.getInArrows();
			node.setInArrows(node.getOutArrows());
			node.setOutArrows(temp);
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String nodeString = "";
		
		for(RegularNode n : this.graph.getNodes().keySet()){
			nodeString += "{";
			for(Activity a : n.getInArrows()){
				nodeString += a.getName() + ", ";
			}
			if(nodeString.length() > 2){
				nodeString = nodeString.substring(0, nodeString.length() - 2);
			}
			
			nodeString += "} -> ";
			
			nodeString += "[" + Integer.toString(n.name) + "]";
			nodeString += " -> {";
			for(Activity a : n.getOutArrows()){
				nodeString += a.getName() + ", " ;
			}
			if(nodeString.charAt(nodeString.length() - 2) == ','){
				nodeString = nodeString.substring(0, nodeString.length() - 2);
			}
			nodeString += "}\n";
		}
		
		return nodeString;
	}
	

}
