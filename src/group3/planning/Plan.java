package group3.planning;

import java.util.ArrayList;
import java.util.List;

public class Plan {
	
	private List<String> plan = new ArrayList<String>(); 

	public Plan() {
		
	}
	
	public Plan(List<String> plan) {
		this.plan = plan;
	}
	
	public void add(String string) {
		plan.add(string);
		
	}

	public List<String> getPlan() {
		return plan;
	}
}
