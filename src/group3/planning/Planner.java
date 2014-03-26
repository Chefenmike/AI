package group3.planning;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Planner {
	private JSONArray world;
	private String holding;
	private JSONObject objects;

	public Planner(JSONArray world, String holding, JSONObject objects) {
		this.world=world;
		this.holding=holding;
		this.objects=objects;
	}

	/**
	 * Finds the path from the current state to a state where the goal is fulfilled. 
	 * @param goal The goal to be fulfilled
	 * @return a Plan of how to solve the current problem
	 */
	public Plan solve(Goal goal) {
		// TODO Auto-generated method stub
		Plan plan = new Plan();
		
		int column = 0;
        while (((JSONArray)world.get(column)).isEmpty()) column++;
        
        plan.add("I pick up . . ."); 
        plan.add("pick " + column);
        plan.add(". . . and then I drop down"); 
        plan.add("drop " + column);
		
		return plan;
	}

}
