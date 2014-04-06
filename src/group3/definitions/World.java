package group3.definitions;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class World {

	private ArrayList<ArrayList<ObjectInWorld>> worldRepresentationList;

	public World(JSONArray world, JSONObject objects) {
		worldRepresentationList = new ArrayList<>();
		for (int i = 0; i < world.size(); i++) {
			ArrayList<ObjectInWorld> columnList = new ArrayList<>();
			JSONArray column = (JSONArray) world.get(i);

			for (int j = 0; j < column.size(); j++) {
				JSONObject obj = (JSONObject) objects.get(column.get(j));
				ObjectInWorld oiw = new ObjectInWorld(
						Shape.getShapeValueFromString((String) obj.get("form")),
						Colour.getColourValueFromString((String) obj
								.get("colour")), Size
								.getSizeValueFromString((String) obj
										.get("size")));
				columnList.add(oiw);
			}
			worldRepresentationList.add(columnList);
		}
	}
	
	public ArrayList<ArrayList<ObjectInWorld>> getWorldRepresentationList() {
		return this.worldRepresentationList;
	}

}
