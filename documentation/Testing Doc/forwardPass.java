// Class:	PertNetwork
private void forwardPass() {
		
		double 	expectedDate = 0,
				expectedSrdDev = 0,
				maxStdDev = 0;
		
		for(MilestoneNode n : this.graph.getNodes().keySet()){
			
			expectedDate = 0;
			expectedSrdDev = 0;
			maxStdDev = 0;
			
			for(Activity a : n.getInArrows()){
				if(a.getEarliestFinish() > expectedDate){
					expectedDate = a.getEarliestFinish();
				}
			}
			
			for(Activity a : n.getInArrows()){
				
				expectedSrdDev = Math.pow(a.getDurationStandardDevitation(), 2);
				
				for(MilestoneNode n1 : n.getPrecedents()){
					if(n1.getOutArrows().contains(a)){
						expectedSrdDev += Math.pow(n1.getStandardDeviation(), 2);
					}
				}
				
				if(Math.sqrt(expectedSrdDev) > maxStdDev){
					maxStdDev = Math.sqrt(expectedSrdDev);
				}
			}
			
			n.setExpectedDate(expectedDate);
			n.setStandardDeviation(maxStdDev);
		}
		
	}

/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: 7 + 1 = 8

Test Cases: { 
			1: <1,2,3,4,5,6,7,9,10,11,12,13,14,15,17,18,19,20,22,23,24,25>
			2: <1,2,25>
			3: <1,2,3,4,10,11,12,13,14,15,17,18,19,20,22,23,24,25>
			4: <1,2,3,4,5,8,9,10,11,12,13,14,15,17,18,19,20,22,23,24,25>
			5: <1,2,3,4,5,6,7,9,10,23,24,25>
			6: <1,2,3,4,5,6,7,9,10,11,12,18,19,20,22,23,24,25>
			7: <1,2,3,4,5,6,7,9,10,11,12,13,16,17,18,19,20,22,23,24,25>
			8: <1,2,3,4,5,6,7,9,10,11,12,13,14,15,17,18,21,22,23,24,25>
			}

Test 1 Input:
-	PertNetwork has ONE MilestoneNode(n)
-	n has ONE inArrow(a)
-	a.earliestFinish > 0
-	n has ONE inArrow(a)
-	n has ONE precedent(p)
-	p contains a
-	sqrt(expectedSrdDev) > 0


Test 2 Input:
-	PertNetwork has NO MilestoneNodes


Test 3 Input:
-	PertNetwork has ONE MilestoneNode(n)
-	n has NO inArrows
-	n has ONE inArrow(a)
INFEASIBLE


Test 4 Input:
-	PertNetwork has ONE MilestoneNode(n)
-	n has ONE inArrow(a)
-	a.earliestFinish < 0
INFEASIBLE


Test 5 Input:
-	PertNetwork has ONE MilestoneNode(n)
-	n has ONE inArrow(a)
-	a.earliestFinish > 0
-	n has ONE inArrow(a)
-	n has NO precedents
-	a.earliestFinish > 0


Test 6 Input:
-	PertNetwork has ONE MilestoneNode(n)
-	n has ONE inArrow(a)
-	a.earliestFinish > 0
-	n has ONE inArrow(a)
-	n has NO precedent
-	sqrt(expectedSrdDev) > 0


Test 7 Input:
-	PertNetwork has ONE MilestoneNode(n)
-	n has ONE inArrow(a)
-	a.earliestFinish > 0
-	n has ONE inArrow(a)
-	n has ONE precedent(p)
-	p DOES NOT contain a
-	sqrt(expectedSrdDev) > 0


Test Input 8:
-	PertNetwork has ONE MilestoneNode(n)
-	n has ONE inArrow(a)
-	a.earliestFinish > 0
-	n has ONE inArrow(a)
-	n has ONE precedent(p)
-	p contains a
-	sqrt(expectedSrdDev) < 0
INFEASIBLE

/*/////////////////////////////////////////////////////////