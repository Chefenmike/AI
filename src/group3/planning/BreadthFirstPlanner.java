package group3.planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.TimeLimitExceededException;

import group3.definitions.Colour;
import group3.definitions.ObjectInWorld;
import group3.definitions.Rules;
import group3.definitions.Shape;
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
						if(Rules.allowedMove(tempObjectInWorld, tempWorld.getFirstObjectInColumn(i))){
							tempWorld.addObjectInWorldToColumn(i, tempObjectInWorld);
							List<String> tempList = addPutToSearchPathList(entry.getValue(), i);
							if (goal.isFulfilled(tempWorld)) {
								return new Plan(tempList);
							}
							newWorldList.put(tempWorld, tempList);	
						}						
					}
				} else {	
					for (int i = 0; i < workingCopyOfWorld.getWorldSize(); i++) {
						if(workingCopyOfWorld.getWorldRepresentationList().get(i).size() != 0) {
							tempWorld = new World(workingCopyOfWorld);
							tempObjectInWorld = tempWorld.getFirstObjectInColumn(i);
							tempWorld.removeTopObjectInColumn(i);
							tempWorld.addObjectInWorldToHolding(tempObjectInWorld);
							List<String> tempList = addPickToSearchPathList(entry.getValue(), i);
							if(tempWorld.getHoldingObject().getColour() == Colour.BLACK && tempWorld.getHoldingObject().getShape() == Shape.BALL) {
//								System.out.println("svart");
							}
//							System.out.println(tempWorld.getHoldingObject().getShape() + " " + tempWorld.getHoldingObject().getColour());
							if (goal.isFulfilled(tempWorld)) {
//								System.out.println("mål");
								return new Plan(tempList);
							}
							newWorldList.put(tempWorld, tempList);
						}
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
	
	private List<String> addPutToSearchPathList(List<String> src, int column) {
//		Collections.copy(dst, src);
		List<String> dst = new ArrayList<String>(src);
		dst.add("put " + column);
		return dst;
	}
	
	private List<String> addPickToSearchPathList(List<String> src, int column) {
//		Collections.copy(dst, src);
		List<String> dst = new ArrayList<String>(src);
		dst.add("pick " + column);
		return dst;
	}
}