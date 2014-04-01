package group3.planning;

import group3.definitions.Location;
import group3.definitions.ObjectInWorld;

public class Goal {
	
	private ObjectInWorld objectToMove;
	private Location locationToMoveTo;
	
	public Goal() {
		
	}
	
	public Goal(ObjectInWorld objectToMove, Location locationToMoveTo) {
		this.objectToMove = objectToMove;
		this.locationToMoveTo = locationToMoveTo;
	}
	
	public ObjectInWorld GetObjectToMove() {
		return this.objectToMove;
	}
	
	public Location GetLocationToMoveTo() {
		return this.locationToMoveTo;
	}
	
	/**
	 * Goals must have a string, surrounded with "fnutts".
	 * This method is called by the method put in JSONObject.
	 */
	@Override
	public String toString() {
		return "\"halloj\"";
	}
}
