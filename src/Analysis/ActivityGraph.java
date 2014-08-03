/**
 * 
 */
package Analysis;

import java.util.ArrayList;
import java.util.TreeMap;

import PModel.Activity;

/**
 *
 */
public class ActivityGraph {
	
	private TreeMap<Activity, ArrayList<Activity> > nodes;	
	
	public ActivityGraph(){
		nodes = new TreeMap<Activity, ArrayList<Activity> >();
	}
	
	
	
	public void addEdge(Activity v, Activity w){
		
		if(!hasNode(v)){
			nodes.put(v, new ArrayList<Activity>());
		}
		if(!nodes.get(v).contains(w)){
			nodes.get(v).add(w);
		}
	}
	
	public boolean hasNode(Activity node){
		return nodes.containsKey(node);
	}



	/**
	 * @return the nodes
	 */
	public TreeMap<Activity, ArrayList<Activity>> getNodes() {
		return nodes;
	}

}
