package group3.planning;

import java.util.ArrayList;
import java.util.List;

import group3.definitions.World;

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

	public boolean isFulfilled(World world) {
		for (Goal g : goals) {
			if (g.isFulfilled(world)) {
				return true;
			}
		}
		return false;
	}
}
