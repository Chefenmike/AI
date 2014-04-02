package group3.definitions;

public enum RelativePosition {
	ON("on"), IN("in"), UNDER("under");
	
	private String relativepositionStringDescription;
	
	private RelativePosition(String relativepositionStringDescription) {
		this.relativepositionStringDescription = relativepositionStringDescription; 
	}
	
	public static RelativePosition getrelativepositionValueFromString(String relativepositionString) {
		if(relativepositionString != null) {
			for(RelativePosition relativeposition : RelativePosition.values()) {
				if(relativepositionString.equalsIgnoreCase(relativeposition.relativepositionStringDescription)) {
					return relativeposition;
				}
			}
		}
		throw new IllegalArgumentException("No relativeposition with description " + relativepositionString + " found");
	}
}
