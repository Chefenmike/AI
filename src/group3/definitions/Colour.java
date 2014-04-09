package group3.definitions;

public enum Colour {
	WHITE("white"), BLACK("black"), RED("red"), GREEN("green"), BLUE("blue"), YELLOW("yellow"), UNSPECIFIED("(-)");
	
	private String colourStringDescription;
	
	private Colour(String colourStringDescription) {
		this.colourStringDescription = colourStringDescription; 
	}
	
	public static Colour getColourValueFromString(String colourString) {
		if(colourString != null) {
			for(Colour colour : Colour.values()) {
				if(colourString.equalsIgnoreCase(colour.colourStringDescription)) {
					return colour;
				}
			}
		}
		throw new IllegalArgumentException("No colour with description " + colourString + " found");
	}
}
