package group3.definitions;

public class Rules {

	public static boolean allowedMove(ObjectInWorld objectToMove,
			ObjectInWorld destinationObject) {
				if(objectToMove.getShape().equals(Shape.BALL) && !destinationObject.getShape().equals(Shape.BOX)){
					return false;
					//return (destinationObject.getShape().equals(Shape.BOX) || destinationObject.getShape().equals(Shape.FLOOR));
				} if (destinationObject.getShape().equals(Shape.BALL)) {
					return false;
				} if (destinationObject.getSize().equals(Size.SMALL) && objectToMove.getSize().equals(Size.LARGE)){
					return false;
				} if (destinationObject.getShape().equals(Shape.BOX)) {
					if (objectToMove.getShape().equals(Shape.PYRAMID) || objectToMove.getShape().equals(Shape.PLANK)){
						return (!objectToMove.getSize().equals(destinationObject.getSize()));
					} 
				} if (objectToMove.getShape().equals(Shape.BOX)) {
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
	
	public static boolean allowedMove(ObjectInWorld objectToMove, RelativePosition relativePosition,
			ObjectInWorld destinationObject) {
		if (relativePosition.equals(RelativePosition.HOLDING)) {
			return true;
		} else if (relativePosition.equals(RelativePosition.ONFLOOR)) {
			return true;
		} else if (relativePosition.equals(RelativePosition.ONTOP)) {
			//Destination object cannot be a box
			return allowedMove(objectToMove, destinationObject) && !destinationObject.getShape().equals(Shape.BOX);
		} else if (relativePosition.equals(RelativePosition.INSIDE)) {
			//Destination object must be a box
			return allowedMove(objectToMove, destinationObject) && destinationObject.getShape().equals(Shape.BOX);
		} else if (relativePosition.equals(RelativePosition.ABOVE)) {
			//Objects cannot be placed under balls. A large object cannot be placed under a small one.
			return !destinationObject.getShape().equals(Shape.BALL) && !(objectToMove.getSize().equals(Size.LARGE) && destinationObject.getSize().equals(Size.SMALL));
		} else if (relativePosition.equals(RelativePosition.UNDER)) {
			//Objects cannot be placed under balls. A large object cannot be placed under a small one.
			return !objectToMove.getShape().equals(Shape.BALL) && !(destinationObject.getSize().equals(Size.LARGE) && objectToMove.getSize().equals(Size.SMALL));
		} else if (relativePosition.equals(RelativePosition.BESIDE)) {
			return true;
		} else {
			return false;
		}
	}
}
