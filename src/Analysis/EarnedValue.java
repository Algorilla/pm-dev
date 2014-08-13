/**
 * 
 */
package Analysis;

import PModel.Activity;
import PModel.Project;

/**
 * 
 */
public class EarnedValue {

	private Project project;
	private double  daysSinceStart;
	
	public EarnedValue(Project project, double daysSinceStart){
		
		this.project 		= project;
		this.daysSinceStart = daysSinceStart;
		
		calculateBudgetAtCompletion();
		calculatePlannedValue();
		calculateEarnedValue();
		calculatePercentScheduledForCompletion();
		calculateActualCost();
		calculatePercentComplete();
		calculateCostVariance();
		calculateScheduleVariance();
		calculateCostPerformanceIndex();
		calculateSchedulePerformanceIndex();
		calculateEstimateAtCompletion();
		calculateEstimateToComplete();
		
	}
	
	private void calculateBudgetAtCompletion(){
		
		double bac = 0;
		
		for(Activity a : project.getActivityList()){
			bac += a.getPlannedValue();
		}

		project.setBudgetAtCompletion(bac);
	}
	
	private void calculatePlannedValue(){
		
		double scheduledValue = 0;
		
		for(Activity a : project.getActivityList()){
			if(a.getLatestFinish() <= daysSinceStart){
				scheduledValue += a.getPlannedValue();
			}
			else if(a.getLatestStart() < daysSinceStart){
				if(a.getDuration() > 0){
					double percentScheduled = (daysSinceStart - a.getLatestStart())/a.getDuration();
					scheduledValue += percentScheduled * a.getPlannedValue();
				}
			}
		}

		project.setPlannedValue(scheduledValue);
		
	}
	
	private void calculateEarnedValue(){
		
		double ev = 0;
		
		for(Activity a : project.getActivityList()){
			ev += a.getPlannedValue() * a.getPercentComplete();
		}
		
		project.setEarnedValue(ev);
		
	}
	
	private void calculatePercentScheduledForCompletion(){
		
		if(project.getBudgetAtCompletion() > 0){
			double sfc = project.getPlannedValue()/project.getBudgetAtCompletion();
			project.setPercentScheduledForCompletion(sfc);
		}
		
	}
	
	private void calculateActualCost(){
		
		double ac = 0;
		
		for(Activity a : project.getActivityList()){
			if(a.getStatus()){
				ac += a.getActualCost();
			}
			else{
				ac += a.getActualCost() * a.getPercentComplete();
			}
		}
		
		project.setActualCost(ac);
	}
	
	private void calculatePercentComplete(){
		if(project.getBudgetAtCompletion() > 0){
			double pc = project.getEarnedValue()/project.getBudgetAtCompletion();
			project.setPercentComplete(pc);
		}
	}
	
	private void calculateCostVariance(){
		project.setCostVariance(project.getEarnedValue()-project.getActualCost());
	}
	
	private void calculateScheduleVariance(){
		project.setScheduleVariance(project.getEarnedValue()-project.getPlannedValue());
	}
	
	private void calculateCostPerformanceIndex(){
		if(project.getActualCost() > 0){
			project.setCostPerformanceIndex(project.getEarnedValue()/project.getActualCost());
		}
	}
	
	private void calculateSchedulePerformanceIndex(){
		if(project.getPlannedValue() > 0){
			project.setSchedulePerformanceIndex(project.getEarnedValue()/project.getPlannedValue());
		}
	}
	
	private void calculateEstimateAtCompletion(){
		if(project.getCostPerformanceIndex() > 0){
			project.setEstimateAtCompletion(project.getBudgetAtCompletion()/project.getCostPerformanceIndex());
		}
	}
	
	private void calculateEstimateToComplete(){
		project.setEstimateToComplete(project.getEstimateAtCompletion()-project.getActualCost());
	}
}
