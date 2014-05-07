package group3.world.definitions;

public enum Quantifier {
	ANY("any"), THE("the"), A("a"), ALL("all");
	
	private String quantifierStringDescription;
	
	private Quantifier(String quantifierStringDescription) {
		this.quantifierStringDescription = quantifierStringDescription; 
	}
	
	public static Quantifier getQuantifierValueFromString(String quantifierString) {
		if(quantifierString != null) {
			for(Quantifier quantifier : Quantifier.values()) {
				if(quantifierString.equalsIgnoreCase(quantifier.quantifierStringDescription)) {
					return quantifier;
				}
			}
		}
		throw new IllegalArgumentException("No quantifier with description " + quantifierString + " found");
	}

}
