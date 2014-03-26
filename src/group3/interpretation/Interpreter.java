package group3.interpretation;
import java.util.ArrayList;
import java.util.List;

import gnu.prolog.term.Term;
import group3.planning.Goal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Interpreter {
	private JSONArray world;
	private String holding;
	private JSONObject objects;

	public Interpreter(JSONArray world, String holding, JSONObject objects) {
		this.world=world;
		this.holding=holding;
		this.objects=objects;
	}

	/**
	 * Converts a tree to a list of goals.
	 * @param tree The tree to be interpreted as goals.
	 * @return a list of all possible interpretations of the tree.
	 */
	public List<Goal> interpret(Term tree) {
		// TODO Auto-generated method stub
		
		List<Goal> goals = new ArrayList<>();
		
		Goal g = new Goal();
        goals.add(g);
		
		return goals;
	}

}
