package group3.planning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import group3.world.World;

/**
 * A collection of goals, which of only one needs to be fulfilled.
 * @author Gustav
 *
 */
public class CompositeGoal {
	private ArrayList<CompositeGoal> goals = new ArrayList<CompositeGoal>();
	private boolean andGoal = false; //false=or goal
	
	public void addAllGoals(List<Goal> goals) {
		this.goals.addAll(goals);
	}
	
	public void addGoal(CompositeGoal goal) {
		this.goals.add(goal);
	}

	public boolean isFulfilled(World world) {
		if (andGoal) {
			for (CompositeGoal g : goals) {
				if (!g.isFulfilled(world)) {
					return false;
				}
			}
			return true;
		} else {
			//or goal
			for (CompositeGoal g : goals) {
				if (g.isFulfilled(world)) {
					return true;
				}
			}
			return false;
		}
	}
	
	@Override
	public String toString() {
		String outputString = "";
		String delimiter;
		if (andGoal) {
			delimiter = " AND ";
		} else {
			delimiter = " OR ";
		}
		
		Iterator<CompositeGoal> iterator = goals.iterator();
		while (iterator.hasNext()) {
			CompositeGoal goal = iterator.next();
			outputString += goal.toString();
			if (iterator.hasNext()) {
				outputString += delimiter;
			}
		}
		
		return "\""+ outputString +"\"";
	}
	
	public void setAndGoal(boolean andGoal) {
		this.andGoal = andGoal;
	}
}
