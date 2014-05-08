package group3.world;

public enum RelativePosition {
	ONTOP("ontop"), INSIDE("inside"), ABOVE("above"), UNDER("under"), BESIDE("beside"), HOLDING("holding"), ONFLOOR("onfloor"), UNSPECIFIED("(-)"), LEFTOF("leftof"), RIGHTOF("rightof");
	
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
