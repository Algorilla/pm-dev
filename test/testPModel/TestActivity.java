package testPModel;

import static org.junit.Assert.*;

import org.junit.Test;

import PModel.Activity;

public class TestActivity {

	////////////////////////////// BLACKBOX ////////////////////////////////////////////////////////////////////	

	/**
	 * Function to test:	Activity::areValidPercentAndCost()
	 * Identifier:			areValidPercentAndCost_blackBox
	 * Description:			Blackbox Test for areValidPercentAndCost()
	 * Inputs:
	 * -	double percent, cost
	 * Outputs:
	 * -	boolean
	 * PostConditions:
	 * -	return true
	 */
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

	/**
	 * Function to test:	Activity::areValidTimes()
	 * Identifier:			areValidTimes_blackBox
	 * Description:			Blackbox Test for areValidTimes()
	 * Inputs:
	 * -	double mostLikey, optimistic, pessimistic
	 * Outputs:
	 * -	boolean
	 * PostConditions:
	 * -	return true
	 */
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

	////////////////////////////// WHITEBOX ////////////////////////////////////////////////////////////////////	

	
	@Test
	public void testAreValidTimes_whiteBox(){
		double mostLikely, optimistic, pessimistic;
		/**
		 * Function to test:	Activity::areValidTimes()
		 * Identifier:			areValidTimes_whiteBoxPath_1
		 * Description:			Test for basis-coverage path 1
		 * Preconditions:
		 * -	All inputs greater than 0
		 * -	All inputs less than Activity.MAX_DURATION
		 * -	mostlikely less than pessimistic and more than optimistic
		 * Inputs:
		 * -	double mostLikely, optimistic, pessimistic
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	return true
		 */
		mostLikely = 2; optimistic = 1; pessimistic = 3;
		assertTrue(Activity.areValidTimes(mostLikely, optimistic, pessimistic));

		/**
		 * Function to test:	Activity::areValidTimes()
		 * Identifier:			areValidTimes_whiteBoxPath_2
		 * Description:			Test for basis-coverage path 2
		 * Preconditions:
		 * -	At least 1 input less than 0
		 * Inputs:
		 * -	double mostLikely, optimistic, pessimistic
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	return false
		 */
		mostLikely = -1; optimistic = 1; pessimistic = 3;
		assertFalse(Activity.areValidTimes(mostLikely, optimistic, pessimistic));

		/**
		 * Function to test:	Activity::areValidTimes()
		 * Identifier:			areValidTimes_whiteBoxPath_3
		 * Description:			Test for basis-coverage path 3
		 * Preconditions:
		 * -	All inputs greater than 0
		 * -	At least 1 input greater than Activity.MAX_DURATION
		 * Inputs:
		 * -	double mostLikely, optimistic, pessimistic
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	return false
		 */
		mostLikely =  Activity.MAX_COST + 1; optimistic = 1; pessimistic = 3;
		assertFalse(Activity.areValidTimes(mostLikely, optimistic, pessimistic));

		/**
		 * Function to test:	Activity::areValidTimes()
		 * Identifier:			areValidTimes_whiteBoxPath_4
		 * Description:			Test for basis-coverage path 4
		 * Preconditions:
		 * -	All inputs greater than 0
		 * -	All inputs less than Activity.MAX_DURATION
		 * -	mostLikely greater than pessimistic or less than optimistic
		 * Inputs:
		 * -	double mostLikely, optimistic, pessimistic
		 * Outputs:
		 * -	boolean
		 * PostConditions:
		 * -	return false
		 */
		mostLikely =  4; optimistic = 1; pessimistic = mostLikely - 1;
		assertFalse(Activity.areValidTimes(mostLikely, optimistic, pessimistic));

	}
}