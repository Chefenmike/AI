package group3.definitions;

public class Rules {

	/**
	 * Checks whether objectToMove is allowed ontop/inside destinationObject
	 * TODO: make this method private
	 * @param objectToMove
	 * @param destinationObject
	 * @return
	 */
	public static boolean allowedMove(ObjectInWorld objectToMove,
			ObjectInWorld destinationObject) {
				if(objectToMove.getShape().equals(Shape.BALL) && !destinationObject.getShape().equals(Shape.BOX)){
					//balls must be in boxes (or on the floor)
					return false;
					//return (destinationObject.getShape().equals(Shape.BOX) || destinationObject.getShape().equals(Shape.FLOOR));
				} if (destinationObject.getShape().equals(Shape.BALL)) {
					//balls cannot support anything
					return false;
				} if (destinationObject.getSize().equals(Size.SMALL) && objectToMove.getSize().equals(Size.LARGE)){
					//small objects cannot support large objects
					return false;
				} if (destinationObject.getShape().equals(Shape.BOX) && objectToMove.getSize().equals(destinationObject.getSize())) {
					if (objectToMove.getShape().equals(Shape.PYRAMID) || objectToMove.getShape().equals(Shape.PLANK)){
						//boxes cannot contain pyramids or planks of the same size
						return false;
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
			//all objects can be picked up
			return true;
		} else if (relativePosition.equals(RelativePosition.ONFLOOR)) {
			//all objects can be placed on the floor
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
