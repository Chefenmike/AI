package group3.interpretation;
import java.util.ArrayList;
import java.util.List;

import gnu.prolog.term.Term;
import gnu.prolog.term.CompoundTerm;
import group3.definitions.ObjectInWorld;
import group3.definitions.RelativePosition;
import group3.definitions.World;
import group3.planning.Goal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Interpreter {
	private World world;
	private String holding;
	private JSONObject objects;
	
	public Interpreter(JSONArray world, String holding, JSONObject objects) {
		this.world = new World(world, holding, objects);
	}

	/**
	 * Converts a tree to a list of goals.
	 * @param tree The tree to be interpreted as goals.
	 * @return a list of all possible interpretations of the tree.
	 */
	public List<Goal> interpret(Term tree) {
		CompoundTerm term = (CompoundTerm) tree;
		List<Goal> goals = new ArrayList<Goal>();
		ArrayList<ObjectInWorld> possibleObjects = new ArrayList<ObjectInWorld>();
		
		String command = term.tag.toString();
		if (command.contains("take")) {
			possibleObjects = world.getObjects(term.args[0]);
			
			//create a goal for every possible object that will fulfill the goal
			for (ObjectInWorld o : possibleObjects) {
				Goal g = new Goal(o, RelativePosition.HOLDING);		
				g.setString(o.getId());
		        goals.add(g);
			}
		} else if (command.contains("move")) {
			
		}
		
		return goals;
	}
	
	

}
