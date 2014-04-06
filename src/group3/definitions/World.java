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
	 * Returns a list of all objects in the world that fits the description given in the input Prolog-term.
	 * @param term
	 * @return
	 */
	public ArrayList<ObjectInWorld> getObjects(Term term) {
		ArrayList<ObjectInWorld> returnList = new ArrayList<ObjectInWorld>();
		
		CompoundTerm compound = (CompoundTerm) term;
		
		if (compound.tag.toString().contains("object")) {
			Shape shape = Shape.getShapeValueFromString(getAtomString(compound.args[0]));
			Size size = Size.getSizeValueFromString(getAtomString(compound.args[1]));
			Colour color = Colour.getColourValueFromString(getAtomString(compound.args[2]));
			
			for (ArrayList<ObjectInWorld> a : worldRepresentationList) {
				for(ObjectInWorld obj : a) {
					boolean suitable = true; //object fits description
					
					if (!shape.equals(Shape.UNSPECIFIED) && !obj.getShape().equals(shape)) {
						//wrong shape
						suitable = false;
					} else if (!size.equals(Size.UNSPECIFIED) && !obj.getSize().equals(size)) {
						//wrong size
						suitable = false;
					} else if (!color.equals(Colour.UNSPECIFIED) && !obj.getColour().equals(color)) {
						//wrong color
						suitable = false;
					} 
					
					if (suitable) {
						returnList.add(obj);
					}
				}
			}
		} else if (compound.tag.toString().contains("basic_entity")) {
			//TODO: implement quantifier (arg 0)
			return getObjects(compound.args[1]); //recursive call
		} else if (compound.tag.toString().contains("relative_entity")) {
			//TODO: arg 0 and 2
			return getObjects(compound.args[1]);
		} else if (compound.tag.toString().contains("relative")) {
			
		}
		
		return returnList;
	}
	
	private String getAtomString(Term term) {
		AtomicTerm t = (AtomicTerm) term;
		return t.toString();
	}
}
