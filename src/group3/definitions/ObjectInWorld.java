package group3.definitions;

public class ObjectInWorld {
	
	private Shape shape;
	private Colour colour;
	private Size size;
	private String id;
	
	public ObjectInWorld(Shape shape, Colour colour, Size size, String id) {
		this.shape = shape;
		this.colour = colour;
		this.size = size;
		this.id =id;
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
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ObjectInWorld)) {
			return false;
		} else {
			ObjectInWorld o2 = (ObjectInWorld) obj;
			return this.id.equals(o2.id);
		}
	}
	
	/*@Override
	public String toString() {
		return "\"" + id + "\"";
	}*/
}
