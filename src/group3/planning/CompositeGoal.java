package group3.planning;

import java.util.ArrayList;
import java.util.List;

import group3.world.World;

/**
 * A collection of goals, which of only one needs to be fulfilled.
 * @author Gustav
 *
 */
public class CompositeGoal {
	private ArrayList<Goal> goals = new ArrayList<Goal>();
	
	public void addAllGoals(List<Goal> goals) {
		this.goals.addAll(goals);
	}
	
	public void addGoal(Goal goal) {
		this.goals.add(goal);
	}

	public boolean isFulfilled(World world) {
		for (Goal g : goals) {
			if (g.isFulfilled(world)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String outputString = "";
		for (Goal g : goals) {
			outputString += g.toString() + ",";
		}
		return "\""+ outputString +"\"";
	}
}
