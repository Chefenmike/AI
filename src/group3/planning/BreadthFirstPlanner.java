package group3.planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.TimeLimitExceededException;

import group3.definitions.ObjectInWorld;
import group3.definitions.World;

public class BreadthFirstPlanner {

	private World world;
	private final Thread thisThread = Thread.currentThread();
	private final int maxSearchTime = 10000;

	public BreadthFirstPlanner(World world) {
		this.world = world;
	}

	public Plan findSolution(Goal goal) throws TimeLimitExceededException {
		if (goal.isFulfilled(world))
			return new Plan();
		Map<World, List<String>> worldList = new HashMap<World, List<String>>();
		worldList.put(world, new ArrayList<String>());
		ObjectInWorld tempObjectInWorld;
		World workingCopyOfWorld, tempWorld;
		startNewThread();
		while (!Thread.interrupted()) {
			Map<World, List<String>> newWorldList = new HashMap<World, List<String>>();
			for (Map.Entry<World, List<String>> entry : worldList.entrySet()) {
				workingCopyOfWorld = new World(entry.getKey());
				if (workingCopyOfWorld.getHoldingObject() != null) {
					tempObjectInWorld = workingCopyOfWorld.getHoldingObject();
					workingCopyOfWorld.removeHolding();
					for (int i = 0; i < workingCopyOfWorld.getWorldSize(); i++) {
						tempWorld = new World(workingCopyOfWorld);
						List<String> tempList = new ArrayList<String>();
						tempWorld.addObjectInWorldToColumn(i, tempObjectInWorld);
						addPutToSearchPathList(tempList, entry.getValue(), i);
						if (goal.isFulfilled(tempWorld)) {
							return new Plan(tempList);
						}
						newWorldList.put(tempWorld, tempList);
					}
				} else {	
					for (int i = 1; i < workingCopyOfWorld.getWorldSize(); i++) {
						tempWorld = new World(workingCopyOfWorld);
						List<String> tempList = new ArrayList<String>();
						tempObjectInWorld = tempWorld.getFirstObjectInColumn(i);
						tempWorld.removeTopObjectInColumn(i);
						tempWorld.addObjectInWorldToHolding(tempObjectInWorld);
						addPickToSearchPathList(tempList, entry.getValue(), i);
						if (goal.isFulfilled(tempWorld)) {
							return new Plan(tempList);
						}
						newWorldList.put(tempWorld, tempList);
					}
				}
			}
			worldList = newWorldList;
		}
		throw new TimeLimitExceededException("This took too long");
	}

	private void startNewThread() {
		new Thread(new Runnable() {
			public void run() {
				try {
					java.lang.Thread.sleep(maxSearchTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				thisThread.interrupt();
			}
		}).start();
	}
	
	private void addPutToSearchPathList(List<String> dst, List<String> src, int column) {
		Collections.copy(dst, src);
		dst.add("put " + column);
	}
	
	private void addPickToSearchPathList(List<String> dst, List<String> src, int column) {
		Collections.copy(dst, src);
		dst.add("pick " + column);
	}
}