package Analysis;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * NodeGraph is a simple implementation of a Graph. The Nodes (described in
 * MilestoneNode) are responsible for their own Edges. NodeGraph simply tracks
 * which nodes have Edges between them, NOT the Activities that constitute those
 * Edges.
 */
public class NodeGraph {

	private TreeMap<MilestoneNode, ArrayList<MilestoneNode>> nodes;

	/**
	 * Constructor must initialize the inner TreeMap
	 */
	public NodeGraph() {
		nodes = new TreeMap<MilestoneNode, ArrayList<MilestoneNode>>();
	}

	/**
	 * 
	 * @param v
	 *            MilestoneNode
	 * @param w
	 *            MilestoneNode
	 */
	public void addEdge(MilestoneNode v, MilestoneNode w) {

		if (!hasNode(v)) {
			nodes.put(v, new ArrayList<MilestoneNode>());
		}
		if (!nodes.get(v).contains(w)) {
			nodes.get(v).add(w);
		}
		if (!v.getDependents().contains(w)) {
			v.addDependent(w);
		}
		if (!w.getPrecedents().contains(v)) {
			w.addPrecedent(v);
		}
	}

	/**
	 * 
	 * @param node
	 *            MileStoneNode
	 * @return true if there is at least one node in the TreeMap
	 */
	public boolean hasNode(MilestoneNode node) {
		return nodes.containsKey(node);
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public MilestoneNode getPreviousNode(MilestoneNode n) {

		for (MilestoneNode node : this.nodes.keySet()) {
			if (this.nodes.get(node).contains(n)) {
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
