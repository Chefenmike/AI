package group3.planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group3.definitions.ObjectInWorld;
import group3.definitions.World;

public class BreadthFirstPlanner {

	private World world;
	private final Thread thisThread = Thread.currentThread();
	private final int maxSearchTime = 10000;

	public BreadthFirstPlanner(World world) {
		this.world = world;
	}

	public Plan findSolution(Goal goal) {
		if (goal.isFullfilled(world))
			return new Plan();

		Map<World, List<String>> worldList = new HashMap<World, List<String>>();
		worldList.put(world, new ArrayList<String>());
		ObjectInWorld tempObjectInWorld;
		World workingCopyOfWorld, tempWorld;
		startNewThread();

		while (!Thread.interrupted()) {
			Map<World, List<String>> newWorldList = new HashMap<World, List<String>>();
			
			for (Map.Entry<World, List<String>> entry : worldList.entrySet()) {
				workingCopyOfWorld = copyOfWorld(entry.getKey());
				if (robotIsHoldingObject(workingCopyOfWorld)) {
					tempObjectInWorld = getHeldObject(workingCopyOfWorld);
					removeHeldObject(workingCopyOfWorld);
					for (int i = 1; i < getWorldSize(workingCopyOfWorld); i++) {
						tempWorld = copyOfWorld(workingCopyOfWorld);
						List<String> tempList = new ArrayList<String>();
						addObjectInWorldToColumn(tempWorld, i, tempObjectInWorld);
						addPutToSearchPathList(tempList, entry.getValue(), i);
						if (goal.isFullfilled(tempWorld)) {
							return new Plan();
						}
						newWorldList.put(tempWorld, tempList);
					}
				} else {	
					for (int i = 1; i < getWorldSize(workingCopyOfWorld); i++) {
						tempWorld = copyOfWorld(workingCopyOfWorld);
						List<String> tempList = new ArrayList<String>();
						tempObjectInWorld = getFirstObjectInColumn(tempWorld, i);
						removeFirstObjectInColumn(tempWorld, i);
						addObjectInWorldToHolding(tempWorld, tempObjectInWorld);
						addPickToSearchPathList(tempList, entry.getValue(), i);
						if (goal.isFullfilled(tempWorld)) {
							return new Plan();
						}
						newWorldList.put(tempWorld, tempList);
					}
				}
			}
			worldList = newWorldList;
		}
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

	private boolean robotIsHoldingObject(World w) {
		return (w.getWorldRepresentationList().get(0).size() != 0);
	}
	
	private int getWorldSize(World w) {
		return w.getWorldRepresentationList().size();
	}
	
	private ObjectInWorld getHeldObject(World w) {
		return w.getWorldRepresentationList().get(0).get(0);
	}
	
	private void removeHeldObject(World w) {
		w.getWorldRepresentationList().get(0).remove(0);
	}
	
	private void addObjectInWorldToColumn(World w, int column, ObjectInWorld oiw) {
		w.getWorldRepresentationList().get(column).add(oiw);
	}
	
	private ObjectInWorld getFirstObjectInColumn(World w, int column) {
		return w.getWorldRepresentationList().get(column).
				get(w.getWorldRepresentationList().get(column).size() - 1);
	}
	
	private void removeFirstObjectInColumn(World w, int column) {
		w.getWorldRepresentationList().get(column).
		remove(w.getWorldRepresentationList().get(column).size() - 1);
	}
	
	private void addObjectInWorldToHolding(World w, ObjectInWorld oiw) {
		w.getWorldRepresentationList().get(0).add(oiw);
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