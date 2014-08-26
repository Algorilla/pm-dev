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
Cyclomatic Complexity: 5 + 1 = 6

Test Cases: { 
			1: <1,2,3,4,5,6,7,9,10,11,12,15,18,19,20,21>
			2: <1,2,3,10,11,12,15,18,19,20,21>
			3: <1,2,3,4,5,8,9,10,11,12,15,18,19,20,21>
			4: <1,2,3,4,5,6,7,9,10,13,14,15,18,19,20,21>
			5: <1,2,3,4,5,6,7,9,10,11,12,16,17,18,19,20,21>
			6: <1,2,3,4,5,6,7,9,10,11,12,15,18,21>
			}

Test 1 Input:
-	Activity(a) with ONE dependent(d)
-	d.start < Integer.MAX_VALUE
-	Activity is NOT last
-	Activity is NOT start
-	Activity has ONE precedent(p)


Test 2 Input:
-	Activity(a) with NO dependents
-	Activity is NOT last
-	Activity is NOT start
-	Activity has ONE precedent(p)


Test 3 Input:
-	Activity(a) with ONE dependent(d)
-	d.start > Interger.MAX_VALUE
INFEASIBLE


Test 4 Input:
-	Activity(a) with ONE dependent(d)
-	d.start < Integer.MAX_VALUE
-	Activity is NOT last
-	Activity is start
-	Activity has ONE precedent(p)


Test 5 Input:
-	Activity(a) with ONE dependent(d)
-	d.start < Integer.MAX_VALUE
-	Activity is NOT last
-	Activity is start
-	Activity has ONE precedent(p)


Test 6 Input:
-	Activity(a) with ONE dependent(d)
-	d.start < Integer.MAX_VALUE
-	Activity is NOT last
-	Activity is NOT start
-	Activity has NO precedents

/*/////////////////////////////////////////////////////////