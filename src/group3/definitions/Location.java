package group3.definitions;

public class Location {
	
	private Relation relation;
	private ObjectInWorld objectToMoveTo;
	
	public Location(Relation relation, ObjectInWorld objectToMoveTo) {
		this.relation = relation;
		this.objectToMoveTo = objectToMoveTo;
	}

	public Relation getRelation() {
		return this.relation;
	}

	public ObjectInWorld getObjectToMoveTo() {
		return this.objectToMoveTo;
	}
}
