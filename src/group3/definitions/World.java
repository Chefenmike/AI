package group3.definitions;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class World {

	private ArrayList worldRepresentationList;

	public World(JSONArray world, JSONObject objects) {
		worldRepresentationList = new ArrayList<ObjectInWorld>();
		for (int i = 0; i < world.size(); i++) {
			ArrayList columnList = new ArrayList<ObjectInWorld>();
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

}
