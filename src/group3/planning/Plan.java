package group3.planning;

import java.util.ArrayList;
import java.util.List;

public class Plan {
	private List plan = new ArrayList(); 

	public void add(String string) {
		plan.add(string);
		
	}

	public List<String> getPlan() {
		return plan;
	}

}
