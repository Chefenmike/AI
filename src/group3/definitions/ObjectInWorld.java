package group3.definitions;

public class ObjectInWorld {
	
	private Shape shape;
	private Colour colour;
	private Size size;
	
	public ObjectInWorld(Shape shape, Colour colour, Size size) {
		this.shape = shape;
		this.colour = colour;
		this.size = size;
	}

	public Shape getShape() {
		return this.shape;
	}

	public Colour getColour() {
		return this.colour;
	}

	public Size getSize() {
		return this.size;
	}
}
