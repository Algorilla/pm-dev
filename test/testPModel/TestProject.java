package testPModel;

import static org.junit.Assert.*;

import org.junit.Test;

import PModel.Project;

public class TestProject {

	
	@Test
	public void testAreValidPercentages(){
		
		double minPercent1 = 0.0, minPlusPercent1 = 0.01, nominalPercent1 = 0.5, maxMinusPercent1 = 0.99, maxPercent1 = 1.0;
		double minPercent2 = 0.0, minPlusPercent2 = 0.01, nominalPercent2 = 0.5, maxMinusPercent2 = 0.99, maxPercent2 = 1.0;
		
		double [] percents1 = { minPercent1, minPlusPercent1, nominalPercent1, maxMinusPercent1, maxPercent1 };
		double [] percents2 = { minPercent2, minPlusPercent2, nominalPercent2, maxMinusPercent2, maxPercent2 };
		
		for(int i = 0; i < percents1.length; i++){
			for(int j = 0; j < percents1.length; j++){
				assertTrue(Project.areValidPercentages(percents1[i], percents2[j]));
			}
		}
	}
	
	@Test
	public void testAreValidValues(){
		
		double 	minValue1, minPlusValue1, nominalValue1, maxMinusValue1, maxValue1,
				minValue2, minPlusValue2, nominalValue2, maxMinusValue2, maxValue2,
				minValue3, minPlusValue3, nominalValue3, maxMinusValue3, maxValue3;
		
		
		minValue1 = minValue2= minValue3 = 0.0;
		minPlusValue1 = minPlusValue2 = minPlusValue3 = 0.01;
		nominalValue1 = nominalValue2 = nominalValue3 = 0.5;
		maxMinusValue1 = maxMinusValue2 = maxMinusValue3 = 0.99;
		maxValue1 = maxValue2 = maxValue3 = 1.0;
		
		double [] values1 = { minValue1, minPlusValue1, nominalValue1, maxMinusValue1, maxValue1};
		double [] values2 = { minValue2, minPlusValue2, nominalValue2, maxMinusValue2, maxValue2};
		double [] values3 = { minValue3, minPlusValue3, nominalValue3, maxMinusValue3, maxValue3};
		
		for(int i = 0; i < values1.length; i++){
			for(int j = 0; j < values1.length; j++){
				for(int k = 0; k < values1.length; k++){
					
					assertTrue(Project.areValidValues(values1[i], values2[j], values3[k]));
				}
			}
		}
	}
//	@Test
//	public void testProject() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetProjectID() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetManagerID() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetProjectID() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetManagerID() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddActivity() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveActivity() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testManageable() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetName() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetDescr() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetStart() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetDeadline() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetLength() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetStatus() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetName() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDescr() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetStart() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDeadline() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetLength() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetStatus() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetMemberList() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddMember() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveMember() {
////		fail("Not yet implemented");
//	}

}
