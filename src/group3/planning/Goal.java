package group3.planning;
import group3.world.ObjectInWorld;
import group3.world.RelativePosition;
import group3.world.Rules;
import group3.world.World;

public class Goal extends CompositeGoal{
	
	private ObjectInWorld objectToMove;
	private RelativePosition relativePosition;
	private ObjectInWorld otherObject;
	private String outputString = "";
	
	public Goal() {
		
	}
	
	public Goal(ObjectInWorld objectToMove, RelativePosition relativePosition, ObjectInWorld otherObject) {
		this.objectToMove = objectToMove;
		this.relativePosition = relativePosition;
		this.otherObject = otherObject;
	}
	
	public Goal(ObjectInWorld objectToMove, RelativePosition relativePosition) {
		this.objectToMove = objectToMove;
		this.relativePosition = relativePosition;
	}
	
	public ObjectInWorld GetTargetToMove() {
		return this.objectToMove;
	}
	
	public void setString(String s) {
		this.outputString = s;
	}
	
	@Override
	public String toString() {
		return outputString;
	}

	/**
	 * Return true if the goal is fulfilled in the world given as parameter
	 */
	public boolean isFulfilled(World world) {
		if (relativePosition.equals(RelativePosition.HOLDING) && world.getHoldingObject() != null) {
			return world.getHoldingObject().equals(objectToMove);
		} else if (relativePosition.equals(RelativePosition.ONFLOOR)) {
			return world.isOnFloor(objectToMove);
		} else if (relativePosition.equals(RelativePosition.ONTOP)) {
			return world.isOnTopOf(objectToMove, otherObject);
		} else if (relativePosition.equals(RelativePosition.INSIDE)) {
			return world.isInside(objectToMove, otherObject);
		} else if (relativePosition.equals(RelativePosition.ABOVE)) {
			return world.isAbove(objectToMove, otherObject);
		} else if (relativePosition.equals(RelativePosition.UNDER)) {
			return world.isUnder(objectToMove, otherObject);
		} else if (relativePosition.equals(RelativePosition.BESIDE)) {
			return world.isBeside(objectToMove, otherObject);
		} else if (relativePosition.equals(RelativePosition.LEFTOF)) {
			return world.isLeftOf(objectToMove, otherObject);
		}else if (relativePosition.equals(RelativePosition.RIGHTOF)) {
			return world.isRightOf(objectToMove, otherObject);
		}else {
			return false;
		}
	}

	/**
	 * Returns true if this goal is possible to fulfill according to the rules of the world.
	 * @return
	 */
	public boolean isAllowed() {
		return Rules.allowedMove(objectToMove, relativePosition, otherObject);
	}
}
