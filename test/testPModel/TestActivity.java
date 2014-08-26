package testPModel;

import static org.junit.Assert.*;

import org.junit.Test;

import PModel.Activity;

public class TestActivity {

	@Test
	public void testAreValidPercentAndCost_blackBox() {
		double minPercent = 0.0, minPlusPercent = 0.01, nominalPercent = 0.5, maxMinusPercent = 0.99, maxPercent = 1.0;
		double minCost = 0, minPlusCost = 1, nominalCost = 500000, maxMinusCost = Activity.MAX_COST - 1.0, maxCost = Activity.MAX_COST;

		double[] percentValues = { minPercent, minPlusPercent, nominalPercent,
				maxMinusPercent, maxPercent };
		double[] costValues = { minCost, minPlusCost, nominalCost,
				maxMinusCost, maxCost };

		for (int i = 0; i < percentValues.length; i++) {
			for (int j = 0; j < costValues.length; j++) {
				assertTrue(Activity.areValidPercentAndCost(percentValues[i],
						costValues[j]));
			}
		}

	}

	@Test
	public void testAreValidTimes_blackBox(){
		double 	mostLikelyMin, mostLikelyMinPlus, mostLikelyNominal, mostLikelyMaxMinus, mostLikelyMax,   
				optimisticMin, optimisticMinPlus, optimisticNominal, optimisticMaxMinus, optimisticMax,
				pessimisticMin, pessimisticMinPlus, pessimisticNominal, pessimisticMaxMinus, pessimisticMax; 
		
		mostLikelyMin = optimisticMin = pessimisticMin = 0.0;
		mostLikelyMinPlus = optimisticMinPlus = pessimisticMinPlus = 1.0;
		mostLikelyNominal = optimisticNominal = pessimisticNominal = 30.0;
		mostLikelyMaxMinus = optimisticMaxMinus = pessimisticMaxMinus = Activity.MAX_DURATION - 1.0;
		mostLikelyMax = optimisticMax = pessimisticMax = Activity.MAX_DURATION;
		
		double [] mostLikelyTimes  = { mostLikelyMin, mostLikelyMinPlus, mostLikelyNominal, mostLikelyMaxMinus, mostLikelyMax };
		double [] optimisticTimes  = { optimisticMin, optimisticMinPlus, optimisticNominal, optimisticMaxMinus, optimisticMax };
		double [] pessimisticTimes = { pessimisticMin, pessimisticMinPlus, pessimisticNominal, pessimisticNominal, pessimisticMaxMinus, pessimisticMax };
		
		for(int i = 0; i < mostLikelyTimes.length; i++){
			for(int j = 0; j < optimisticTimes.length; j++){
				for(int k = 0; k < pessimisticTimes.length; k++){
					
					if(mostLikelyTimes[i] > pessimisticTimes[k] || optimisticTimes[j] > mostLikelyTimes[i] || optimisticTimes[j] > pessimisticTimes[k]){
						assertFalse(Activity.areValidTimes(mostLikelyTimes[i], optimisticTimes[j], pessimisticTimes[k]));
					}
					else{
						assertTrue(Activity.areValidTimes(mostLikelyTimes[i], optimisticTimes[j], pessimisticTimes[k]));
					}
					
				}
			}
		}
	}

	@Test
	public void testAreValidTimes_whiteBox(){
		
		// Test Path 1
		//		-	node a:	All inputs greater than 0
		//		-	node b:	All inputs less than MAX_DURATION
		//		-	node c:	most likely less than pessimistic and more than optimistic
		//		return true
		assertTrue(Activity.areValidTimes(2, 1, 3));

		// Test Path 2
		//		-	node a:	At least 1 input less than 0
		//		return false
		assertFalse(Activity.areValidTimes(-1, 1, 3));

		// Test Path 3
		//		-	node a:	All inputs greater than 0
		//		-	node b:	At east 1 input greater than MAX_DURATION
		//		return false
		assertFalse(Activity.areValidTimes(Activity.MAX_DURATION + 1, 1, 3));

		// Test Path 4
		//		-	node a:	All inputs greater than 0
		//		-	node b:	All inputs less than MAX_DURATION
		//		-	node c:	most likely greater than pessimistic or less than optimistic
		//		return false
		assertFalse(Activity.areValidTimes(4, 1, 3));

	}
}