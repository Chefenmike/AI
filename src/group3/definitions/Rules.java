package group3.definitions;

public class Rules {

	public static boolean allowedMove(ObjectInWorld objectToMove,
			ObjectInWorld destinationObject) {
				if(objectToMove.getShape().equals(Shape.BALL)){
					return (destinationObject.getShape().equals(Shape.BOX) || destinationObject.getShape().equals(Shape.FLOOR));
				} else if (destinationObject.getShape().equals(Shape.BALL)) {
					return false;
				} else if (destinationObject.getSize().equals(Size.SMALL) && objectToMove.getSize().equals(Size.LARGE)){
					return false;
				} else if (destinationObject.getShape().equals(Shape.BOX)) {
					if (objectToMove.getShape().equals(Shape.PYRAMID) || objectToMove.getShape().equals(Shape.PLANK)){
						return (!objectToMove.getSize().equals(destinationObject.getSize()));
					} 
				} else if (objectToMove.getShape().equals(Shape.BOX)) {
					if(objectToMove.getSize().equals(Size.SMALL)){
						if(destinationObject.getShape().equals(Shape.TABLE) || destinationObject.getShape().equals(Shape.PLANK)){
							return (objectToMove.getSize().equals(destinationObject.getSize()));
						} else {
							return false;
						}
					} else {
						if(destinationObject.getShape().equals(Shape.TABLE) || destinationObject.getShape().equals(Shape.PLANK) || destinationObject.getShape().equals(Shape.BRICK)){
							return (objectToMove.getSize().equals(destinationObject.getSize()));
						} else {
							return false;
						}
					}
				}
 			
		return true;
	}

}
