/**
 * 
 */
package Analysis;

import java.util.ArrayList;
import java.util.TreeMap;


/**
 *
 */
public class NodeGraph {
	
	private TreeMap<MilestoneNode, ArrayList<MilestoneNode> > nodes;	
	
	public NodeGraph(){
		nodes = new TreeMap<MilestoneNode, ArrayList<MilestoneNode> >();
	}
	
	
	
	public void addEdge(MilestoneNode v, MilestoneNode w){
		
		if(!hasNode(v)){
			nodes.put(v, new ArrayList<MilestoneNode>());
		}
		if(!nodes.get(v).contains(w)){
			nodes.get(v).add(w);
		}
		if(!v.getDependents().contains(w)){
			v.addDependent(w);
		}
		if(!w.getPrecedents().contains(v)){
			w.addPrecedent(v);
		}
	}
	
	public boolean hasNode(MilestoneNode node){
		return nodes.containsKey(node);
	}
	
	public MilestoneNode getPreviousNode(MilestoneNode n){
		
		for(MilestoneNode node : this.nodes.keySet()){
			if(this.nodes.get(node).contains(n)){
				return node;
			}
		}
		return null;
	}



	/**
	 * @return the nodes
	 */
	public TreeMap<MilestoneNode, ArrayList<MilestoneNode>> getNodes() {
		return nodes;
	}
}
