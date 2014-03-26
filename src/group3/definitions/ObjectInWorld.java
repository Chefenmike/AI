package group3.definitions;

public abstract class ObjectInWorld {
	
	String shape, colour, size;
	
	public ObjectInWorld(String shape, String colour, String size) {
		this.shape = shape;
		this.colour = colour;
		this.size = size;
	}

	public String getShape() {
		return shape;
	}

	public String getColour() {
		return colour;
	}

	public String getSize() {
		return size;
	}
}
