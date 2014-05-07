package group3.planning;

import group3.definitions.World;

import java.util.ArrayList;
import java.util.List;

public class Action {
	
	private List<String> plan = new ArrayList<String>(); 
	private World w;
	public Action() {
		
	}
	
	public Action(List<String> plan, World w) {
		this.plan = plan;
		this.w = w;
	}
	
}
