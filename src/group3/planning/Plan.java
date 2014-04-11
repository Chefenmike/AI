package group3.planning;

import group3.definitions.World;

import java.util.ArrayList;
import java.util.List;

public class Plan {
	
	private List<String> plan = new ArrayList<String>(); 
	private World w;
	public Plan() {
		
	}
	
	public Plan(List<String> plan, World w) {
		this.plan = plan;
		this.w = w;
	}
	
	public void add(String string) {
		plan.add(string);
		
	}

	public List<String> getPlan() {
		return plan;
	}
	
	public World getWorld() {
		return w;
	}
}
