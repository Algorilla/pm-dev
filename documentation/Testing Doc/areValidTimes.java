// Class:	Activity
	public static boolean areValidTimes(double mostLikely, double optimistic,
			double pessimistic) {

		if (mostLikely < 0 || optimistic < 0 || pessimistic < 0) {
			return false;
		}

		if (mostLikely > MAX_DURATION || optimistic > MAX_DURATION
				|| pessimistic > MAX_DURATION) {
			return false;
		}

		if (mostLikely > pessimistic || mostLikely < optimistic) {
			return false;
		}

		return true;
	}

/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: 3 + 1 = 4

Test Cases: { 
			1: <1,2,3,4>
			2: <5,6>
			3: <1,7,8>
			5: <1,2,9>
			}

Test 1 Input:
-	node a:	All inputs greater than 0
-	node b:	All inputs less than MAX_DURATION
-	node c:	most likely less than pessimistic and more than optimistic


Test 2 Input:
-	node a:	At least 1 input less than 0


Test 3 Input:
-	node a:	All inputs greater than 0
-	node b:	At east 1 input greater than MAX_DURATION


Test 4 Input:
-	node a:	All inputs greater than 0
-	node b:	All inputs less than MAX_DURATION
-	node c:	most likely greater than pessimistic or less than optimistic


/*/////////////////////////////////////////////////////////