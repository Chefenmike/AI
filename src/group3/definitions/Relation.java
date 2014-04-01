package group3.definitions;

public enum Relation {
	ON("in"), IN("in"), UNDER("under");
	
	private String relationStringDescription;
	
	private Relation(String relationStringDescription) {
		this.relationStringDescription = relationStringDescription; 
	}
	
	public static Relation getRelationValueFromString(String relationString) {
		if(relationString != null) {
			for(Relation relation : Relation.values()) {
				if(relationString.equalsIgnoreCase(relation.relationStringDescription)) {
					return relation;
				}
			}
		}
		throw new IllegalArgumentException("No relation with description " + relationString + " found");
	}
}
