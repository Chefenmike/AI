package group3.definitions;

import gnu.prolog.term.AtomicTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class World {

	private ArrayList<ArrayList<ObjectInWorld>> worldRepresentationList;
	private ObjectInWorld holding;

	public World(JSONArray world, String holding, JSONObject objects) {
		worldRepresentationList = new ArrayList<ArrayList<ObjectInWorld>>();
		for (int i = 0; i < world.size(); i++) {
			ArrayList<ObjectInWorld> columnList = new ArrayList<ObjectInWorld>();
			JSONArray column = (JSONArray) world.get(i);
			for (int j = 0; j < column.size(); j++) {
				JSONObject obj = (JSONObject) objects.get(column.get(j));
				ObjectInWorld oiw = new ObjectInWorld(
						Shape.getShapeValueFromString((String) obj.get("form")),
						Colour.getColourValueFromString((String) obj
								.get("color")), Size
								.getSizeValueFromString((String) obj
										.get("size")), column.get(j).toString());
				columnList.add(oiw);
			}
			worldRepresentationList.add(columnList);
		}

		//holding object:
		JSONObject hold = (JSONObject) objects.get(holding); 
		if (hold != null) {
			ObjectInWorld oiw = new ObjectInWorld(
					Shape.getShapeValueFromString((String) hold.get("form")),
					Colour.getColourValueFromString((String) hold
							.get("color")), Size
							.getSizeValueFromString((String) hold
									.get("size")), 
									objects.get(holding).toString());
			this.holding = oiw;
		}
	}

	public World(World world) {
		worldRepresentationList = new ArrayList<ArrayList<ObjectInWorld>>();
		for (int i = 0; i < world.getWorldSize(); i++) {
			ArrayList<ObjectInWorld> columnList = new ArrayList<ObjectInWorld>();
			ArrayList<ObjectInWorld> column = world.getWorldRepresentationList().get(i);
			for (int j = 0; j < column.size(); j++) {
				ObjectInWorld tempObject = new ObjectInWorld(column.get(j).getShape(), 
						column.get(j).getColour(), 
						column.get(j).getSize(), 
						column.get(j).getId());
				columnList.add(tempObject);
			}
			worldRepresentationList.add(columnList);
		}
		holding = world.holding;
	}

	public ObjectInWorld getHoldingObject() {
		return holding;
	}

	public void removeHolding() {
		holding = null;
	}

	public int getWorldSize() {
		return this.getWorldRepresentationList().size();
	}

	public ArrayList<ArrayList<ObjectInWorld>> getWorldRepresentationList() {
		return this.worldRepresentationList;
	}

	public void addObjectInWorldToColumn(int column, ObjectInWorld oiw) {
		this.getWorldRepresentationList().get(column).add(oiw);
	}

	public ObjectInWorld getFirstObjectInColumn(int column) {
		return this.getWorldRepresentationList().get(column).
				get(this.getWorldRepresentationList().get(column).size() - 1);
	}

	public void removeTopObjectInColumn(int column) {
		this.getWorldRepresentationList().get(column).
		remove(this.getWorldRepresentationList().get(column).size() - 1);
	}

	public void addObjectInWorldToHolding(ObjectInWorld oiw) {
		holding = oiw;
	}

	

	/**
	 * Checks whether the relative position rp exists between object1 and any of the objects in the list otherObjects.
	 * @param object1
	 * @param rp 
	 * @param otherObjects 
	 * @return true if the relation exist between object1 and any object in the list
	 */
	public boolean checkRelation(ObjectInWorld object1, RelativePosition rp, ArrayList<ObjectInWorld> otherObjects) {
		for (ObjectInWorld object2 : otherObjects) {
			if (checkRelation(object1, rp, object2)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkRelation(ObjectInWorld object1, RelativePosition rp, ObjectInWorld object2) {
		if (rp.equals(RelativePosition.INSIDE)) {
			return isInside(object1, object2); 
		} else if (rp.equals(RelativePosition.ONTOP)) {
			//TODO
		} else if (rp.equals(RelativePosition.ABOVE)) {
			//TODO
		} else if (rp.equals(RelativePosition.UNDER)) {
			//TODO
		} else if (rp.equals(RelativePosition.BESIDE)) {
			//TODO
		} 

		return false;
	}

	//TODO: Implement this methods + methods for the other relations.
	/**
	 * checks if object1 is inside object2
	 * @param object1
	 * @param object2
	 * @return true if object1 is inside object2
	 */
	private boolean isInside(ObjectInWorld object1, ObjectInWorld object2) {
		return true;
	}
}
