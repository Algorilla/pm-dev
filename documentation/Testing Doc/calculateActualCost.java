// Class:	EarnedValue
private void calculateActualCost() {

	double ac = 0;

	for (Activity a : project.getActivityList()) {
		if (a.getStatus()) {
			ac += a.getActualCost();
		} else {
			if (a.getPercentComplete() > 0) {
				ac += a.getActualCost() * a.getPercentComplete();
			}
		}
	}

	project.setActualCost(ac);
}

/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: 3 + 1 = 4

Test Cases: { 
			1: <1,2,3,4,10,11>
			2: <1,11>
			3: <1,2,5,6,7,9,10,11>
			4: <1,2,5,8,9,10,11>
			}

Test 1 Input:
-	Project(p) has ONE Activity(a)
-	a.status = true

Test 2 Input:
-	Project(p) has NO Activities

Test 3 Input:
-	Project(p) has ONE Activity(a)
-	a.status = false
-	a.percentComplete > 0 

Test 4 Input:
-	Project(p) has ONE Activity(a)
-	Project(p) has ONE Activity(a)
-	a.status = false
-	a.percentComplete = 0 


/*/////////////////////////////////////////////////////////