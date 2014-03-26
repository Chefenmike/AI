package group3.definitions;

public abstract class Location {
	
	String relation;
	ObjectInWorld objectToMoveTo;
	
	public Location(String relation, ObjectInWorld objectToMoveTo) {
		this.relation = relation;
		this.objectToMoveTo = objectToMoveTo;
	}

	public String getRelation() {
		return relation;
	}

	public ObjectInWorld getObjectToMoveTo() {
		return objectToMoveTo;
	}
}
