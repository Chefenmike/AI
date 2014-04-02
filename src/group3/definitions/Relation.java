package group3.definitions;

public class Relation {
	
	private RelativePosition relativePosition;
	private ObjectInWorld objectToMoveTo;
	
	public Relation(RelativePosition relativePosition, ObjectInWorld objectToMoveTo) {
		this.relativePosition = relativePosition;
		this.objectToMoveTo = objectToMoveTo;
	}

	public RelativePosition getRelativePosition() {
		return this.relativePosition;
	}

	public ObjectInWorld getObjectToMoveTo() {
		return this.objectToMoveTo;
	}
}
