package group3.definitions;

import gnu.prolog.term.AtomicTerm;
import gnu.prolog.term.Term;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class World {

	private ArrayList<ArrayList<ObjectInWorld>> worldRepresentationList;

	public World(JSONArray world, JSONObject objects) {
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
										.get("size")));
				columnList.add(oiw);
			}
			worldRepresentationList.add(columnList);
		}
	}

	public ObjectInWorld getObject(Shape shape, Colour colour, Size size, Quantifier quantifier, Location location) {
		//in: term
		return new ObjectInWorld(shape, colour, size);
	}

	public ArrayList<ObjectInWorld> getObjects(Term term) {
		ArrayList<ObjectInWorld> returnList = new ArrayList<ObjectInWorld>();
		
		for (ArrayList<ObjectInWorld> a : worldRepresentationList) {
			for(ObjectInWorld obj : a) {
				
			}
		}
		
		// TODO Auto-generated method stub
		return returnList;
	}
	
	private String getAtomString(Term term) {
		AtomicTerm t = (AtomicTerm) term;
		return t.toString();
	}
}
