// Class:	NodeGraph
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

/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: 4 + 1 = 5

Test Cases: { 
			1: <1,2,4,5,7,8,10,11>
			2: <3,4,5,7,8,10,11>
			3: <1,2,6,7,8,10,11>
			4: <1,2,4,5,9,10,11>
			5: <1,2,4,5,7,8,12>
			}

Test 1 Input:
-	NodeGraph DOES NOT contain v
-	v DOES NOT contain w
-	w is NOT dependent on v
-	v is NOT a precedent of w


Test 2 Input:
-	Nodegraph contains v
-	v DOES NOT contain w
-	w is NOT dependent on v
-	v is NOT a precedent of w


Test 3 Input:
-	NodeGraph DOES NOT contain v
-	v contains w
INFEASIBLE


Test 4 Input:
-	NodeGraph DOES NOT contain v
-	v DOES NOT contain w
-	w is dependent on v
-	v is NOT a precedent of w
INFEASIBLE


Test 5 Input:
-	NodeGraph DOES NOT contain v
-	v DOES NOT contain w
-	w is NOT dependent on v
-	v is a precedent of w
INFEASIBLE

/*/////////////////////////////////////////////////////////