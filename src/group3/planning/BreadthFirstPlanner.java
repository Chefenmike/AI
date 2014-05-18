package group3.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.TimeLimitExceededException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import group3.world.ObjectInWorld;
import group3.world.RelativePosition;
import group3.world.Rules;
import group3.world.World;

public class BreadthFirstPlanner extends Planner {

	private World world;
	private final Thread thisThread = Thread.currentThread();
	private final int maxSearchTime = 10000;

	public BreadthFirstPlanner(World world) {
		super();
		this.world = world;
	}

	public BreadthFirstPlanner(JSONArray world, String holding,
			JSONObject objects) {
		super(world, holding, objects);
		this.world = new World(world, holding, objects);
	}

	@Override
	public Plan findSolution(CompositeGoal goal) throws TimeLimitExceededException {
		if (goal.isFulfilled(world))
			return new Plan();
		Map<World, List<String>> worldList = new HashMap<World, List<String>>();
		worldList.put(world, new ArrayList<String>());
		ObjectInWorld tempObjectInWorld;
		World workingCopyOfWorld, tempWorld;
		//startNewThread();
		//while (!Thread.interrupted()) {
		while (true) {
			Map<World, List<String>> newWorldList = new HashMap<World, List<String>>();
			for (Map.Entry<World, List<String>> entry : worldList.entrySet()) {
				workingCopyOfWorld = new World(entry.getKey());
				if (workingCopyOfWorld.getHoldingObject() != null) {
					tempObjectInWorld = workingCopyOfWorld.getHoldingObject();
					workingCopyOfWorld.removeHolding();
					for (int i = 0; i < workingCopyOfWorld.getWorldSize(); i++) {
						tempWorld = new World(workingCopyOfWorld);
						List<String> tempList = new ArrayList<String>();
						if (Rules.allowedMove(tempObjectInWorld, RelativePosition.UNSPECIFIED,
								tempWorld.getFirstObjectInColumn(i))) {
							tempWorld.addObjectInWorldToColumn(i,
									tempObjectInWorld);
							tempList = addPutToSearchPathList(
									entry.getValue(), i);
							newWorldList.put(tempWorld, tempList);
						}
						if (goal.isFulfilled(tempWorld)) {
							return new Plan(tempList,tempWorld);
						}
					}
				} else {
					for (int i = 0; i < workingCopyOfWorld.getWorldSize(); i++) {
						if (workingCopyOfWorld.getWorldRepresentationList()
								.get(i).size() > 0) {
							tempWorld = new World(workingCopyOfWorld);
							// System.out.println("Kolumn " + i +
							// " har storlek " +
							// tempWorld.getWorldRepresentationList().get(i).size());
							tempObjectInWorld = tempWorld
									.getFirstObjectInColumn(i);
							tempWorld.removeTopObjectInColumn(i);
							tempWorld
							.addObjectInWorldToHolding(tempObjectInWorld);
							List<String> tempList = addPickToSearchPathList(
									entry.getValue(), i);
							if (goal.isFulfilled(tempWorld)) {
								return new Plan(tempList,tempWorld);
							}
							newWorldList.put(tempWorld, tempList);
						}
					}
				}
			}
			worldList = newWorldList;
		}
		//throw new TimeLimitExceededException("This took too long");
	}

	private void startNewThread() {
		new Thread(new Runnable() {	
			public void run() {
				while(!Thread.currentThread().isInterrupted()){
					try {
						java.lang.Thread.sleep(maxSearchTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Thread.currentThread().interrupt();
						return;
					}
				}
			}
		}).start();
	}

	private List<String> addPutToSearchPathList(List<String> src, int column) {
		// Collections.copy(dst, src);
		List<String> dst = new ArrayList<String>(src);
		dst.add("drop " + column);
		return dst;
	}

	private List<String> addPickToSearchPathList(List<String> src, int column) {
		// Collections.copy(dst, src);
		List<String> dst = new ArrayList<String>(src);
		dst.add("pick " + column);
		return dst;
	}
}