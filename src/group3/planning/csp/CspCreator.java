package group3.planning.csp;

import group3.world.ObjectInWorld;
import group3.world.World;

import java.awt.List;
import java.util.ArrayList;

public class CspCreator implements State {
	private ArrayList<LocationState> states = new ArrayList<LocationState>();
	private ArrayList<ActionState> actions = new ArrayList<ActionState>();
	private int maxCol, maxRow;
	private HolderState holding;

	public CspCreator(World w) {
		maxCol = w.getWorldSize();
		maxRow = w.getAllObjects().size();
		if(w.getHoldingObject() == null) {
			holding = new HolderState(false);
		} else {
			createLocationState(w.getHoldingObject(), -1, -1);
			holding = new HolderState(true);
		}
		
		for (int x = 0; x < w.getWorldRepresentationList().size(); x++) {
			ArrayList<ObjectInWorld> col = w.getWorldRepresentationList()
					.get(x);
			for (int y = 0; y < col.size(); y++) {
				ObjectInWorld obj = col.get(y);
				createLocationState(obj, x, y);
			}
		}

		for (int i = 0; i < maxCol; i++) {
			actions.add(new ActionState("pick", i));
			actions.add(new ActionState("drop", i));
		}
	}

	private void createLocationState(ObjectInWorld obj, int x, int y) {
		states.add(new LocationState(obj, x, y));
	}
}
