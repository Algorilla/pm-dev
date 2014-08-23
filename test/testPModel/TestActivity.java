package testPModel;

import static org.junit.Assert.*;

import org.junit.Test;

import PModel.Activity;

public class TestActivity {

	@Test
	public void testAreValidPercentAndCost() {
		double minPercent = 0.0, minPlusPercent = 0.01, nominalPercent = 0.5, maxMinusPercent = 0.99, maxPercent = 1.0;
		double minCost = 0, minPlusCost = 1, nominalCost = 500000, maxMinusCost = 99999, maxCost = 1000000;
		double[] percentValues = { minPercent, minPlusPercent, nominalPercent, maxMinusPercent, maxPercent };
		double[] costValues = { minCost, minPlusCost, nominalCost, maxMinusCost, maxCost };
		
		Activity a = Activity.makeDummyStart();
		
		for(int  i = 0; i < percentValues.length; i++){
			for(int j = 0; j < costValues.length; j++){
				assertTrue(a.areValidPercentAndCost(percentValues[i], costValues[j]));
			}
		}
		
	}
	
	@Test
	public void testAreValidTimes(){
//		double 	mostLikelyMin, mostLikelyMinPlus, mostLikelyNominal, mostLikelyMaxMinus, mostLikelyMax,   
//				optimisticMin, optimisticMinPlus, optimisticNominal, optimisticMaxMinus, optimisticMax,
//				pessimisticMin, pessimisticMinPlus, pessimisticNominal, pessimisticMaxMinus,  
//				targetCompletionDate
	}

	@Test
	public void testActivityIntStringStringDateDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testActivityStringStringDateDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProjectID() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testManageable() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDescr() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStart() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDeadline() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLength() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDescr() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStart() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDeadline() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLength() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMemberList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveMember() {
		fail("Not yet implemented");
	}

}
