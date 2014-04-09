package group3.definitions;

public class Location {
	
	private RelativePosition relativePosition;
	private ObjectInWorld objectToMoveTo;
	
	public Location(RelativePosition relativePosition, ObjectInWorld objectInWorld) {
		this.relativePosition = relativePosition;
		this.objectToMoveTo = objectInWorld;
	}
	
	public RelativePosition getRelativePosition() {
		return this.relativePosition;
	}
	
	public ObjectInWorld getObjectToMoveTo() {
		return this.objectToMoveTo;
	}
}
