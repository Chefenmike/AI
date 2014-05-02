package group3.definitions;

import gnu.prolog.term.AtomicTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
	
	/**
	 * Returns a list of all objects in the world (including the object currently being held).
	 * @return all objects in world.
	 */
	public ArrayList<ObjectInWorld> getAllObjects() {
		ArrayList<ObjectInWorld> allObjects = new ArrayList<ObjectInWorld>();
		
		if (holding != null) {
			allObjects.add(holding); //add object being held			
		}
		
		for (ArrayList<ObjectInWorld> column : worldRepresentationList) {
			allObjects.addAll(column);
		}
		
		return allObjects;
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
		if (otherObjects.isEmpty()) {
			return checkRelation(object1, rp);
		} else {
			for (ObjectInWorld object2 : otherObjects) {
				if (checkRelation(object1, rp, object2)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Checks if object1 is [RELATION] object2
	 * @param object1
	 * @param rp the relation
	 * @param object2
	 * @return
	 */
	public boolean checkRelation(ObjectInWorld object1, RelativePosition rp, ObjectInWorld object2) {
		if (rp.equals(RelativePosition.INSIDE)) {
			return isInside(object1, object2); 
		} else if (rp.equals(RelativePosition.ONTOP)) {
			return isOnTopOf(object1, object2);
		} else if (rp.equals(RelativePosition.ABOVE)) {
			return isAbove(object1, object2);
		} else if (rp.equals(RelativePosition.UNDER)) {
			return isUnder(object1, object2);
		} else if (rp.equals(RelativePosition.BESIDE)) {
			return isBeside(object1, object2);
		} 

		return false;
	}
	
	/**
	 * Checks unary relations (on floor)
	 * @param object1
	 * @param rp
	 * @return
	 */
	public boolean checkRelation(ObjectInWorld object1, RelativePosition rp) {
		if (rp.equals(RelativePosition.ONFLOOR)) {
			return isOnFloor(object1);
		}

		return false;
	}

	public boolean isOnFloor(ObjectInWorld object1) {
		for(ArrayList<ObjectInWorld> objList : worldRepresentationList){
			if(!objList.isEmpty() && objList.get(0).equals(object1)) {
				return true;
			}
		}
		return false;
	}

	public boolean isInside(ObjectInWorld a, ObjectInWorld b) {
		if (b.getShape()==Shape.BOX) {
			return isOnTopOf(a, b);
		} else {
			return false;
		}
	}
	
	public boolean isOnTopOf(ObjectInWorld b, ObjectInWorld a){
		for(ArrayList<ObjectInWorld> objList : worldRepresentationList){
			for(int i = 0; i < objList.size(); i++){
				ObjectInWorld obj = objList.get(i);
				if(obj.equals(a) && i != objList.size()-1){
					return objList.get(i+1).equals(b);
				}
			}
		}
		return false;
	}

	public boolean isAbove(ObjectInWorld b, ObjectInWorld a){
		for(ArrayList<ObjectInWorld> objList : worldRepresentationList){
			for(int i = 0; i < objList.size(); i++){
				ObjectInWorld obj = objList.get(i);
				if(obj.equals(a)){
					for(int j = i+1; j < objList.size(); j++){
						if(objList.get(j).equals(b)){
							return true;
						}
					}
					return false;
				}
			}
		}
		return false;
	}

	public boolean isUnder(ObjectInWorld b, ObjectInWorld a){
		for(ArrayList<ObjectInWorld> objList : worldRepresentationList){
			for(int i = 0; i < objList.size(); i++){
				ObjectInWorld obj = objList.get(i);
				if(obj.equals(a)){
					for(int j = i-1; j > -1; j--){
						if(objList.get(j).equals(b)){
							return true;
						}
					}
					return false;
				}
			}
		}
		return false;
	}

	public boolean isBeside(ObjectInWorld b, ObjectInWorld a){
		for(int i = 0; i < worldRepresentationList.size(); i++){
			ArrayList<ObjectInWorld> objList = worldRepresentationList.get(i);
			for(ObjectInWorld obj : objList){
				if(obj.equals(a)){
					if(i == 0) {
						return worldRepresentationList.get(i+1).contains(b);
					} else if(i == worldRepresentationList.size() - 1) {
						return worldRepresentationList.get(i-1).contains(b);
					} else {
						return (worldRepresentationList.get(i-1).contains(b) || worldRepresentationList.get(i+1).contains(b));
				
					}
				}
			}
		}
		return false;
	}

	public boolean isLeftOf(ObjectInWorld b, ObjectInWorld a){
		for(int i = 0; i < worldRepresentationList.size(); i++){
			ArrayList<ObjectInWorld> objList = worldRepresentationList.get(i);
			for(ObjectInWorld obj : objList){
				if(obj.equals(a)){
					for(int j = i-1; j > -1; j--){
						if(worldRepresentationList.get(j).contains(b)) return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isRightOf(ObjectInWorld b, ObjectInWorld a){
		for(int i = 0; i < worldRepresentationList.size(); i++){
			ArrayList<ObjectInWorld> objList = worldRepresentationList.get(i);
			for(ObjectInWorld obj : objList){
				if(obj.equals(a)){
					for(int j = i+1; j < worldRepresentationList.size(); j++){
						if(worldRepresentationList.get(j).contains(b)) return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns current world as a string with a jsonobject-form.
	 * 
	 */
	public String getWorldAsString() {

		String s = "{\"world\":[";
		for (int i = 0; i < worldRepresentationList.size(); i++) {
			s += "[";
			for (int j = 0; j < worldRepresentationList.get(i).size(); j++) {
				s += "\"";
				s += worldRepresentationList.get(i).get(j).getId();
				s += "\"";
				if (j != worldRepresentationList.get(i).size() - 1
						&& worldRepresentationList.get(i).size() > 1) {
					s += ",";
				}
			}
			s += "]";
			if (i != worldRepresentationList.get(i).size() - 1) {
				s += ",";
			}
		}
		s += "]}";
		if(holding != null) {
			s += "\n Holding: " + holding.getId();
		} else {
			s += "\n Holding: null";
		}
		
		return s;
	}

}
