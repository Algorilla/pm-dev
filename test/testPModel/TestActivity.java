package testPModel;

import static org.junit.Assert.*;

import org.junit.Test;

import PModel.Activity;

public class TestActivity {

	@Test
	public void testAreValidPercentAndCost() {
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
	public void testAreValidTimes(){
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

//	@Test
//	public void testActivityIntStringStringDateDateInt() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testActivityStringStringDateDateInt() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetProjectID() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetNumber() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetProjectID() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetNumber() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testManageable() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetName() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetDescr() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetStart() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetDeadline() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetLength() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetStatus() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetName() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDescr() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetStart() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDeadline() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetLength() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetStatus() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetMemberList() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddMember() {
//		// fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveMember() {
//		// fail("Not yet implemented");
//	}

}
