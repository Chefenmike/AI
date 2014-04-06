package group3.planning;

import group3.definitions.Location;
import group3.definitions.ObjectInWorld;
import group3.definitions.RelativePosition;
import group3.definitions.TargetInterface;
import group3.definitions.World;

public class Goal {
	
	private TargetInterface objectToMove;
	private Location location;
	private RelativePosition relativePosition;
	private ObjectInWorld otherObject;
	private String outputString = "";
	
	public Goal() {
		
	}
	
	public Goal(TargetInterface target, Location location) {
		this.objectToMove = target;
		this.location = location;
	}
	
	public Goal(ObjectInWorld objectToMove, RelativePosition relativePosition, ObjectInWorld otherObject) {
		this.objectToMove = objectToMove;
		this.relativePosition = relativePosition;
		this.otherObject = otherObject;
	}
	
	public Goal(ObjectInWorld objectToMove, RelativePosition relativePosition) {
		this.objectToMove = objectToMove;
		this.relativePosition = relativePosition;
	}
	
	public TargetInterface GetTargetToMove() {
		return this.objectToMove;
	}
	
	public Location GetLocationToMoveTo() {
		return this.location;
	}
	
	public void setString(String s) {
		this.outputString = s;
	}
	
	/**
	 * Goals must have a string, surrounded with "fnutts".
	 * This method is called by the method put in JSONObject.
	 */
	@Override
	public String toString() {
		//TODO implement
		return "\""+ outputString +"\"";
	}
	
	public boolean isFulfilled(World world) {
		if (relativePosition.equals(RelativePosition.HOLDING)) {
			return world.getHoldingObject().equals(objectToMove);
		}
		else {
			return false;
		}
	}
}
