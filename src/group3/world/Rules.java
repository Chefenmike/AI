package group3.world;

import group3.world.definitions.Shape;
import group3.world.definitions.Size;

public class Rules {

	/**
	 * Checks whether objectToMove is allowed ontop/inside destinationObject
	 * I reworked this method a bit so that it doesn't return true until all rules have been checked /Gustav.
	 * @param objectToMove
	 * @param destinationObject
	 * @return
	 */
	private static boolean allowedMove(ObjectInWorld objectToMove,
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
					if (destinationObject.getShape().equals(Shape.BOX)) {
						return false;
					}
					
					//Boxes can only be supported by tables or planks of the same size, 
					//but large boxes can also be supported by large bricks.
					if (!objectToMove.getSize().equals(destinationObject.getSize())) {
						//both the box and the other object needs to be of the same size
						return false;
					} else if(!(destinationObject.getShape().equals(Shape.TABLE) || destinationObject.getShape().equals(Shape.PLANK) || destinationObject.getShape().equals(Shape.BRICK))) {
						//only tables, planks and bricks can support boxes
						return false;
					} else if (destinationObject.getShape().equals(Shape.BRICK) && destinationObject.getSize().equals(Size.SMALL)) {
						//Small bricks cannot support any boxes
						return false;
					} 
				}

		return true; //all rules fulfilled
	}	
	
	/**
	 * Checks whether a certain move is allowed
	 * @param objectToMove 
	 * @param relativePosition the relation that objectToMove has to destinationObject
	 * @param destinationObject
	 * @return
	 */
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
		} else if (relativePosition.equals(RelativePosition.LEFTOF)) {
			return true;
		} else if (relativePosition.equals(RelativePosition.RIGHTOF)) {
			return true;
		} else if (relativePosition.equals(RelativePosition.UNSPECIFIED) && destinationObject==null) {
			//Corresponds to either a pick up or put on floor action,
			//since these are the only actions with only one object involved
			return true;
		} else if (relativePosition.equals(RelativePosition.UNSPECIFIED)) {
			//Corresponds to either a put ontop or a put inside action,
			//depending on whether destinationobject is a box or not
			return allowedMove(objectToMove, destinationObject);
		} else {
			return false;
		}
	}
}
