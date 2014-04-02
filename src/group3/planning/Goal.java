package group3.planning;

import group3.definitions.Relation;
import group3.definitions.ObjectInWorld;

public class Goal {
	
	private ObjectInWorld[] objectsToMove;
	private Relation locationsToMoveTo[];
	
	public Goal() {
		
	}
	
	public Goal(ObjectInWorld[] objectsToMove, Relation[] locationsToMoveTo) {
		this.objectsToMove = objectsToMove;
		this.locationsToMoveTo = locationsToMoveTo;
	}
	
	public ObjectInWorld[] GetObjectToMove() {
		return this.objectsToMove;
	}
	
	public Relation[] GetLocationToMoveTo() {
		return this.locationsToMoveTo;
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
