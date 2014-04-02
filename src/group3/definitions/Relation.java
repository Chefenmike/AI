package group3.definitions;

public class Relation {
	
	private Location location;
	private ObjectInWorld objectToMoveTo;
	
	public Relation(Location location, ObjectInWorld objectToMoveTo) {
		this.location = location;
		this.objectToMoveTo = objectToMoveTo;
	}

	public Location getLocation() {
		return this.location;
	}

	public ObjectInWorld getObjectToMoveTo() {
		return this.objectToMoveTo;
	}
}
