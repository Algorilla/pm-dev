Functional Requirements:
===

__US 1: As a project manager, I want to manage all my projects with the application.__ 
 
- The application will allow users to be of the type 'Project Manager'.  
- The application will support the creation of 'Project' entities.  
- The application will support 'Project Managers' having multiple 'Projects'.  

__US 2: As a project manager, I want to associate multiple interdependent activities with my project.__  

- The application will support the creation of 'Activities' within a 'Project'.  
- The application will support the interaction of 'Activity' entities.  

__US 3: As a project manager, I want to assign resources (project members) and project-relevant properties to my activities__   

- The application will allow users to be of the type 'Team Member'.
- The application will allow 'Managers' to assign 'Team Members' to 'Activities'.

__US 4: As a project member, I want to be able to see which tasks have been assigned to me.__ 

- The application will allow 'Team Members' to view the 'Activities' to which they are assigned.

__US 5: As a project manager, I want to generate GANT charts.__

- The application will support 'Managers' entering real 'Time Line' details about the 'Activities' in their 'Projects'. (Start Date and Due Date)
- The application will support 'Managers' entering projected 'Time Line' estimates to the 'Activities' in their 'Projects'. (Expected duration)
- The application will allow the 'Manager' to enter 'Dependancy' information relating the 'Activities' in the 'Project' to one another, indicating which 'Activities' must be completed before another may begin.
- The application will allow 'Managers' to generate 'GANTT Charts'. (Visual depiction of the 'Project's' projected progression)

__US 6: As a project manager, I want to perform a critical path analysis.__

- The application will allow the 'Manager' to identify the series of 'Activites' in a 'Project' that constitue its 'Critical Path'.
- The application will be able to calculate the sequence of 'Activities' whose dependencies are most critical and whose delayed completion would postpone the 'Project's' completion.

__US 7: As a project manager, I want to be able to perform PERT analysis.__

- The application will support calculations to determine the 'Float' values of 'Activities', that is, the degree of flexibility (in terms of the prefered time scale) allowable in completing the 'Activity' without disturbing the 'Project's' completion schedule.
- For every 'Activity' the application will encode the 'Earliest Start Time (ES)', 'Earliest Finish Time (EF)', 'Latest Start Time (LS)', and 'Latest Finish Time (LF)'.
- The application will allow the 'Manager' to view graphical charts expressing the information derived from the interdependance of these values (in 'Activities') across the 'Project'.
- For every 'Activity' the application will encode the 'Most Likely Time to Completion (m)', the 'Optimistic Time to Completion (o)', and the 'Pessimistic Time to Completion (p)'.
- The application will derive the 'Expected Duration (t)', and 'Standard Deviaiton (s)' for each 'Project' from the '(o),(m),and (p)'' values.
- The application will allow the 'Manager' to set a 'Target Date of Completion (T)' for each 'Project'.
- The application will calculate a 'z-score', calculate the probability of a 'Project' meeting its '(T)' based on that 'z-score'.

__US 8: As a project manager, I want to be able to perform earned value analysis.__

- The application will encode a 'Project Value (ProV)' for each 'Project' that will be calculated as the sum of the 'Planned Values (PV)' of each 'Activity' in the 'Project' that should have been completed by the time of the calculation.
- The application will encode the 'Earned Value (EV)' of the 'Project' using the 'Percentage Technique'.
- The application will encode the 'Budget at Completion (BAC)' as the sum of all the 'Planned Values (PV)' for the 'Activities' in the 'Project'.
- The application will calculate the '(EV)' of each 'Project' by dividing the 'Planned Value' of the 'Project' by the 'Budget at Completion'.
- The application will encode the 'Actual Cost (AC)' of a project as the total cost of all the 'Activities' completed up to the point of the calculation.
- The application will compute the 'Project's' 'Percent Complete (PC)' value by dividing the 'Earned Value (EV)' by the '(BAC)'.
- The application will encode the values 'Cost Variance (CV)', 'Schedule Variance (SV)', 'Cost Performance Index (CPI)', and 'Schedule Performance Index (SPI)'.
- The application will calculate the 'Cost Variance (CV)' as 'Earned Value (EV)' - 'Actual Cost (AC)' with positive values indicating that the 'Project' is on 'Budget' and negative ones indicating the opposite.
- The application will calculate 'Schedule Variance (SV)' as '(EV)' - '(PV)' with positive results indicating that the 'Project' is ahead of schedule, while negative values indicate that it is behind schedule.
- The application will calculate a 'Project's' 'Cost Performance Index' as the '(EV)'/'(AC)' with results of value greater than one indicating that the 'Project' is likely to cost less than what was planned and conversely, values less than one indicating the opposite.
- The application will calculate the 'Schedule Performance Index (SPI)' as the '(EV)'/'(PV)', again, with values greater than one being positive, and those less than one being unfavorable.
- The application will encode the 'Estimate at Completion (EAC)' for each 'Project'.
- The application will calculate the '(EAC)' as the '(BAC)'/'(CPI)'.
- The application will calculate the 'Estimate to Complete (ETC)' as the 'Estimate at Completion (EAC)' - the 'Actual Cost (AC)' which will indicate how much is projected to be spent between the current moment and the completion of the 'Project'.







