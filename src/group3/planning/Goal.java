package group3.planning;

import group3.definitions.Location;
import group3.definitions.TargetInterface;

public class Goal {
	
	private TargetInterface target;
	private Location location;
	
	public Goal() {
		
	}
	
	public Goal(TargetInterface target, Location location) {
		this.target = target;
		this.location = location;
	}
	
	public TargetInterface GetTargetToMove() {
		return this.target;
	}
	
	public Location GetLocationToMoveTo() {
		return this.location;
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
