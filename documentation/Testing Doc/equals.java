// Class:	MilestoneNode
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	MilestoneNode other = (MilestoneNode) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name)) {
		return false;
	}
	return true;
}

/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: 6 + 1 = 7

Test Cases: { 
			1: <1,6,9,10,15,16,18,19>
			2: <2,3>
			3: <1,4,5>
			4: <1,6,7,8>
			5: <1,6,9,10,11,12,13>
			6: <1,6,9,10,11,12,14>
			7: <1,6,9,10,15,16,17>
			}

Test 1 Input:
-	node a:		obj is NOT equal to calling MilestoneNode
-	node c:		obj is NOT null
-	node e:		obj class is of type MilestoneNode
-	node i:		Caller's name NOT null
-	node l:		obj name is same as calling MilestoneNode


Test 2 Input:
-	node a:		obj is equal to calling MilestoneNode


Test 3 Input:
-	node a:		obj is not equal to calling MilestoneNode
-	node c:		obj is null


Test 4 Input:
-	node a:		obj is not equal to calling MilestoneNode
-	node c:		obj is not null	
-	node e:		obj class is NOT of type MilestoneNode


Test 5 Input:
-	node a:		obj is NOT equal to calling MilestoneNode
-	node c:		obj is NOT null
-	node e:		obj class is of type MilestoneNode
-	node i:		Caller's name is null		
-	node j:		obj's name is NOT null

Test 6 Input:
-	node a:		obj is NOT equal to calling MilestoneNode
-	node c:		obj is NOT null
-	node e:		obj class is of type MilestoneNode
-	node i:		Caller's name is null		
-	node j:		obj's name is null

Test 7 Input:
-	node a:		obj is NOT equal to calling MilestoneNode
-	node c:		obj is NOT null
-	node e:		obj class is of type MilestoneNode
-	node i:		Caller's name NOT null
-	node l:		obj name is NOT same as calling MilestoneNode

/*/////////////////////////////////////////////////////////