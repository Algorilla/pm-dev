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
	
	private TreeMap<RegularNode, ArrayList<RegularNode> > nodes;	
	
	public NodeGraph(){
		nodes = new TreeMap<RegularNode, ArrayList<RegularNode> >();
	}
	
	
	
	public void addEdge(RegularNode v, RegularNode w){
		
		if(!hasNode(v)){
			nodes.put(v, new ArrayList<RegularNode>());
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
	
	public boolean hasNode(RegularNode node){
		return nodes.containsKey(node);
	}
	
	public RegularNode getPreviousNode(RegularNode n){
		
		for(RegularNode node : this.nodes.keySet()){
			if(this.nodes.get(node).contains(n)){
				return node;
			}
		}
		return null;
	}



	/**
	 * @return the nodes
	 */
	public TreeMap<RegularNode, ArrayList<RegularNode>> getNodes() {
		return nodes;
	}
}
