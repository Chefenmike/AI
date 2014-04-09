package group3.definitions;

public enum Size {
	SMALL("small"), LARGE("large"), UNSPECIFIED("(-)");
	
	private String sizeStringDescription;
	
	private Size(String sizeStringDescription) {
		this.sizeStringDescription = sizeStringDescription; 
	}
	
	public static Size getSizeValueFromString(String sizeString) {
		if(sizeString != null) {
			for(Size size : Size.values()) {
				if(sizeString.equalsIgnoreCase(size.sizeStringDescription)) {
					return size;
				}
			}
		}
		throw new IllegalArgumentException("No size with description " + sizeString + " found");
	}
}
