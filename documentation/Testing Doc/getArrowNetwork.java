// Class:	PertNetwork
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

/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: 4 + 1 = 5

Test Cases: { 
			1: <1,2,3,4,5,7,8,9,10,11,13,14,15,16,17>
			2: <1,2,8,9,10,11,13,14,15,16,17>
			3: <1,2,3,6,7,8,9,10,11,13,14,15,16,17>
			4: <1,2,3,4,5,7,8,14,15,16,17>
			5: <1,2,3,4,5,7,8,9,12,13,14,15,16,17>
			}

/*/////////////////////////////////////////////////////////