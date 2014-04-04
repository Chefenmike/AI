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
										.get("size")), (String) objects.get(column.get(j)).toString());
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
									(String) objects.get(holding).toString());
			this.holding = oiw;
		}
	}
	
	public ObjectInWorld getHoldingObject() {
		return holding;
	}

	public ArrayList<ObjectInWorld> getObjects(Term term) {
		ArrayList<ObjectInWorld> returnList = new ArrayList<ObjectInWorld>();
		
		CompoundTerm compound = (CompoundTerm) term;
		
/*		int termType = term.getTermType();
		if(termType==term.ATOM) {
			
		} else if (termType==term.COMPOUND) {
			
		}*/
		
		if (compound.tag.toString().contains("object")) {
			Shape shape = Shape.getShapeValueFromString(getAtomString(compound.args[0]));
			Size size = Size.getSizeValueFromString(getAtomString(compound.args[1]));
			Colour color = Colour.getColourValueFromString(getAtomString(compound.args[2]));
			
			for (ArrayList<ObjectInWorld> a : worldRepresentationList) {
				for(ObjectInWorld obj : a) {
					if (obj.getShape().equals(shape) && obj.getColour().equals(color)) {
						if (obj.getSize().equals(Size.UNSPECIFIED) || obj.getSize().equals(size)) {
							returnList.add(obj);
						}
					}
				}
			}
		} else if (compound.tag.toString().contains("basic_entity")) {
			return getObjects(compound.args[1]); //recursive call
		}
		
		
		
		
		// TODO Auto-generated method stub
		return returnList;
	}
	
	private String getAtomString(Term term) {
		AtomicTerm t = (AtomicTerm) term;
		return t.toString();
	}
}
