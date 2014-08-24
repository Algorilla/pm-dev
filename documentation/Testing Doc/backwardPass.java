// Class:	GanttNetwork
private void backwardPass(Activity activity) {
	
	double latestStart = Integer.MAX_VALUE, start;
	Activity constrainer = null;
	
	for(Activity dep : activity.getDependents()){
		
		start = dep.getLatestStart();
		
		if(start < latestStart){
			latestStart = start;
			constrainer = dep;
		}
		
	}
	if(activity.equals(this.getFinish())){
		activity.setLatestFinish(activity.getEarliestFinish());
		activity.setLatestStart(activity.getEarliestStart());
	}
	else{
		activity.setLatestFinish(constrainer.getLatestStart());
		activity.setLatestStart(constrainer.getLatestStart() - activity.getDuration());
	}
	
	if(activity.equals(this.getStart())){
		return;
	}
	
	for(Activity pre : activity.getPrecedents()){
		backwardPass(pre);
	}
	
	
}

/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: 6 + 1 = 7

Test Cases: { 
			1: <1,2,3,4,5,6,8,9,10,11,12,15,18,19,20,21>,
			2: <1,2,3,10,11,12,15,18,19,20,21>,
			3: <1,2,3,4,5,7,9,10,11,12,15,18,19,20,21>,
			4: <1,2,3,10,13,15,18,19,20,21>,
			5: <1,2,3,10,13,16,17,18,19,20,21>,
			6: <1,2,3,10,13,15,18,21>
			}

/*/////////////////////////////////////////////////////////