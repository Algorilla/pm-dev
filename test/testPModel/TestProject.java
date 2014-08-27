package testPModel;

import static org.junit.Assert.*;

import org.junit.Test;

import PModel.Project;

public class TestProject {

	////////////////////////////// BLACKBOX ////////////////////////////////////////////////////////////////////	

	/**
	 * Function to test:	Project::areValidPercentages()
	 * Identifier:			areValidPercentages_blackBox
	 * Description:			Blackbox Test for areValidPercentages()
	 * Inputs:
	 * -	double percent1, percent2
	 * Outputs:
	 * -	boolean
	 * PostConditions:
	 * -	return true
	 */
	@Test
	public void testAreValidPercentages_blackBox(){
		
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
	
	/**
	 * Function to test:	Project::areValidValues()
	 * Identifier:			areValidValues_blackBox
	 * Description:			Blackbox Test for areValidValues()
	 * Inputs:
	 * -	double value1, value2, value3
	 * Outputs:
	 * -	boolean
	 * PostConditions:
	 * -	return true
	 */
	@Test
	public void testAreValidValues_blackBox(){
		
		double 	minValue1, minPlusValue1, nominalValue1, maxMinusValue1, maxValue1,
				minValue2, minPlusValue2, nominalValue2, maxMinusValue2, maxValue2,
				minValue3, minPlusValue3, nominalValue3, maxMinusValue3, maxValue3;
		
		
		minValue1 = minValue2 = minValue3 = 0.0;
		minPlusValue1 = minPlusValue2 = minPlusValue3 = 1.0;
		nominalValue1 = nominalValue2 = nominalValue3 = 1000;
		maxMinusValue1 = maxMinusValue2 = maxMinusValue3 = Project.MAX_COST - 1.0;
		maxValue1 = maxValue2 = maxValue3 = Project.MAX_COST;
		
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
}