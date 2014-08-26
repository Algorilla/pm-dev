// Class:	MilestoneNode
public String toStringArrows(String type) {
		
		String  arrowString = "";
		ArrayList<Activity> arrows;
		
		if(type.equals("in")){
			arrowString = "Activities to Complete by this Milestone: "; 
			if(this.hasInArrows()){
				arrows = this.getInArrows();
			}
			else{
				arrows = new ArrayList<Activity>();
				return "No in arrows";
			}
			
		} else if(type.equals("out")){
			arrowString  = "Activities to Available to start after this Milestone: ";
			if(this.hasOutArrows()){
				arrows = this.getOutArrows();
			}
			else{
				arrows = new ArrayList<Activity>();
				return "No out arrows";
			}
			
		} else {
			ec.showError("You didn't enter 'in' or 'out' when you should've.");
			return null;
		}
		
		for(Activity a : arrows){
			arrowString += a.getName() + ", ";
		}
		if(arrowString.endsWith(", ")){
			arrowString = arrowString.substring(0, arrowString.length()-2) + "\n\n";
		}
	
		return arrowString;
	}
	
/*/////////////////////////////////////////////////////////
Cyclomatic Complexity: E - N + 2 = (26 - 21 + 2) = 7

Test Cases: { 
			1: <1,2,3,4,5,6,7,8,9,10,11,13>
			2: <1,2,3,4,14,16>
			3: <1,2,3,4,5,6,9,10,11,13>
			4: <1,2,3,4,5,6,7,8,9,12,13>
			5: <1,2,17,18,19,20,7,8,9,10,11,13>
			6: <1,2,17,21,22,23>
			7: <1,2,24,25,26>
			}

Test 1 Input:


/*/////////////////////////////////////////////////////////