package group3.interpretation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gnu.prolog.io.ParseException;
import gnu.prolog.term.AtomicTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.CompoundTerm;
import group3.planning.CompositeGoal;
import group3.planning.Goal;
import group3.utils.PlanningException;
import group3.world.ObjectInWorld;
import group3.world.ObjectInterface;
import group3.world.ObjectOperator;
import group3.world.RelativePosition;
import group3.world.Rules;
import group3.world.World;
import group3.world.definitions.Colour;
import group3.world.definitions.OperatorEnum;
import group3.world.definitions.Shape;
import group3.world.definitions.Size;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Interpreter {
	private World world;
	private String holding;
	private JSONObject objects;

	public Interpreter(JSONArray world, String holding, JSONObject objects) {
		this.world = new World(world, holding, objects);
	}

	public Interpreter(World currentWorld) {
		this.world = currentWorld;
	}

	/**
	 * Converts a tree to a list of goals.
	 * 
	 * @param tree
	 *            The tree to be interpreted as goals.
	 * @return a list of all possible interpretations of the tree.
	 * @throws PlanningException
	 */
	public List<CompositeGoal> interpret(Term tree) throws PlanningException {
		CompoundTerm term = (CompoundTerm) tree;
		List<CompositeGoal> goals = new ArrayList<CompositeGoal>();
		ArrayList<ObjectInterface> possibleObjects = new ArrayList<ObjectInterface>();

		String command = term.tag.toString();
		if (command.contains("take")) {
			possibleObjects = getObjects(term.args[0]);

			// create a goal for every possible object that will fulfill the
			// goal
			CompositeGoal compositeGoal = new CompositeGoal();
			for (ObjectInterface o : possibleObjects) {
				if (o instanceof ObjectInWorld) {
					ObjectInWorld obj = (ObjectInWorld) o;
					Goal g = new Goal(obj, RelativePosition.HOLDING);
					g.setString(obj.getId());
					compositeGoal.addGoal(g);
				} else {
					ObjectOperator operator = (ObjectOperator) o;
					if (operator.getOperator().equals(OperatorEnum.AND)) {
						throw new PlanningException(
								"Cannot pick up more than one object!!");
					} else if (operator.getOperator().equals(OperatorEnum.OR)) {
						// Do nothing, add next object to the same composite
						// goal
					} else if (operator.getOperator().equals(OperatorEnum.XOR)) {
						// create a new composite goal that the next object will
						// be added to
						goals.add(compositeGoal);
						compositeGoal = new CompositeGoal();
					}
				}

			}
			goals.add(compositeGoal);
		} else if (command.contains("move")) {
			possibleObjects = getObjects(term.args[0]);
			CompoundTerm relative = (CompoundTerm) term.args[1];

			if (relative.args[1].getTermType() == Term.ATOM) {
				// Target = floor
				RelativePosition rp = RelativePosition.ONFLOOR;
				CompositeGoal compositeGoal = new CompositeGoal();
				for (ObjectInterface o : possibleObjects) {
					if (o instanceof ObjectInWorld) {
						ObjectInWorld obj = (ObjectInWorld) o;
						Goal g = new Goal(obj, rp);
						g.setString(obj.getId() + " " + rp.toString());
						compositeGoal.addGoal(g);
					} else {
						ObjectOperator operator = (ObjectOperator) o;
						if (operator.getOperator().equals(OperatorEnum.AND)) {
							compositeGoal.setAndGoal(true);
						} else if (operator.getOperator().equals(
								OperatorEnum.OR)) {
							compositeGoal.setAndGoal(false);
							// Do nothing, add next object to the same composite
							// goal
						} else if (operator.getOperator().equals(
								OperatorEnum.XOR)) {
							// create a new composite goal that the next object
							// will be added to
							goals.add(compositeGoal);
							compositeGoal = new CompositeGoal();
						}
					}
				}
				goals.add(compositeGoal);
			} else {
				RelativePosition rp = RelativePosition
						.getrelativepositionValueFromString(getAtomString(relative.args[0]));
				ArrayList<ObjectInterface> relativeObjects = getObjects(relative.args[1]);

				CompositeGoal compositeGoal = new CompositeGoal();
				for (ObjectInterface o : possibleObjects) {
					if (o instanceof ObjectInWorld) {
						ObjectInWorld obj = (ObjectInWorld) o;
						CompositeGoal innerCompositeGoal = new CompositeGoal();
						for (ObjectInterface r : relativeObjects) {
							if (r instanceof ObjectInWorld) {
								ObjectInWorld robj = (ObjectInWorld) r;
								if (Rules.allowedMove(obj, rp, robj)) {
									Goal g = new Goal(obj, rp, robj);
									g.setString(obj.getId() + " "
											+ rp.toString() + " "
											+ robj.getId());
									innerCompositeGoal.addGoal(g);
								} 
								/*else {
									//For some reason, putting this in rules isnt enough.
									//box cannot be put inside of another box
									if (obj.getShape().equals(Shape.BOX)
											&& robj.getShape()
													.equals(Shape.BOX)
											&& rp.equals(RelativePosition.INSIDE))
										throw new PlanningException(
												"Unallowed goal");
								}*/
							} else {
								ObjectOperator operator = (ObjectOperator) r;
								if (operator.getOperator().equals(
										OperatorEnum.AND)) {
									innerCompositeGoal.setAndGoal(true);
								} else if (operator.getOperator().equals(
										OperatorEnum.OR)) {
									innerCompositeGoal.setAndGoal(false);
								} else if (operator.getOperator().equals(
										OperatorEnum.XOR)) {
									throw new PlanningException(
											"Ambiguity error!!");
								}
							}
						}
						compositeGoal.addGoal(innerCompositeGoal);
					} else {
						ObjectOperator operator = (ObjectOperator) o;
						if (operator.getOperator().equals(OperatorEnum.AND)) {
							compositeGoal.setAndGoal(true);
						} else if (operator.getOperator().equals(
								OperatorEnum.OR)) {
							compositeGoal.setAndGoal(false);
							// Do nothing, add next object to the same composite
							// goal
						} else if (operator.getOperator().equals(
								OperatorEnum.XOR)) {
							// create a new composite goal that the next object
							// will be added to
							goals.add(compositeGoal);
							compositeGoal = new CompositeGoal();
						}
					}
				}
				goals.add(compositeGoal);
			}
		}

		// Check if goal is possible according to rules of the world:
		List<CompositeGoal> toBeRemoved = new ArrayList<CompositeGoal>();
		for (CompositeGoal g : goals) {
			if (!g.isAllowed()) {
				toBeRemoved.add(g);
			}
		}
		goals.removeAll(toBeRemoved);

		return goals;
	}

	/**
	 * Returns a list of all objects in the world that fits the description
	 * given in the input Prolog-term.
	 * 
	 * @param term
	 * @return
	 * @throws PlanningException
	 */
	public ArrayList<ObjectInterface> getObjects(Term term)
			throws PlanningException {
		ArrayList<ObjectInterface> returnList = new ArrayList<ObjectInterface>();

		CompoundTerm compound;
		if (term instanceof CompoundTerm) {
			compound = (CompoundTerm) term;
		} else {
			throw new PlanningException("Parse error");
		}

		if (compound.tag.toString().contains("object")) {
			Shape shape = Shape
					.getShapeValueFromString(getAtomString(compound.args[0]));
			Size size = Size
					.getSizeValueFromString(getAtomString(compound.args[1]));
			Colour color = Colour
					.getColourValueFromString(getAtomString(compound.args[2]));

			for (ObjectInWorld obj : world.getAllObjects()) {
				boolean suitable = true; // object fits description

				if (!shape.equals(Shape.UNSPECIFIED)
						&& !obj.getShape().equals(shape)) {
					// wrong shape
					suitable = false;
				} else if (!size.equals(Size.UNSPECIFIED)
						&& !obj.getSize().equals(size)) {
					// wrong size
					suitable = false;
				} else if (!color.equals(Colour.UNSPECIFIED)
						&& !obj.getColour().equals(color)) {
					// wrong color
					suitable = false;
				}

				if (suitable) {
					returnList.add(obj);
				}
			}

			if (returnList.isEmpty()) {
				String exString = "No such object in world (";
				if (!size.equals(Size.UNSPECIFIED)) {
					exString += size.toString() + " ";
				}
				if (!color.equals(Colour.UNSPECIFIED)) {
					exString += color.toString() + " ";
				}
				exString += shape.toString() + ")";
				throw new PlanningException(exString);
			}
		} else if (compound.tag.toString().contains("basic_entity")) {
			switch (getAtomString(compound.args[0])) {
			case "the":
				ArrayList<ObjectInterface> xorObjects = getObjects(compound.args[1]);
				xorObjects = addOperators(xorObjects, OperatorEnum.XOR);
				return xorObjects;
			case "any":
				ArrayList<ObjectInterface> orObjects = getObjects(compound.args[1]);
				orObjects = addOperators(orObjects, OperatorEnum.OR);
				return orObjects;
			case "all":
				ArrayList<ObjectInterface> allObjects = getObjects(compound.args[1]);
				allObjects = addOperators(allObjects, OperatorEnum.AND);
				return allObjects;
			}
		} else if (compound.tag.toString().contains("relative_entity")) {
			ArrayList<ObjectInterface> matchingObjects = new ArrayList<ObjectInterface>();
			switch (getAtomString(compound.args[0])) {
			case "the":
				matchingObjects.addAll(getObjects(compound.args[1]));
				matchingObjects = addOperators(matchingObjects,
						OperatorEnum.XOR);
				return getRelative(matchingObjects, compound.args[2]);
			case "any":
				matchingObjects.addAll(getObjects(compound.args[1]));
				matchingObjects = addOperators(matchingObjects, OperatorEnum.OR);
				return getRelative(matchingObjects, compound.args[2]);
			case "all":
				matchingObjects = getObjects(compound.args[1]);
				matchingObjects = addOperators(matchingObjects,
						OperatorEnum.AND);
				return getRelative(matchingObjects, compound.args[2]);
			}
		}

		if (returnList.isEmpty()) {
			throw new PlanningException("No such object in world!!!");
		}

		return returnList;
	}

	/**
	 * Adds either AND or OR between all elements in the supplied ArrayList
	 * 
	 * @param objectList
	 *            the list to add operators to
	 * @param operator
	 *            the operator to be added
	 * @return
	 */
	private ArrayList<ObjectInterface> addOperators(
			ArrayList<ObjectInterface> objectList, OperatorEnum operator) {
		ArrayList<ObjectInterface> returnList = new ArrayList<ObjectInterface>();
		Iterator<ObjectInterface> iterator = objectList.iterator();
		while (iterator.hasNext()) {
			ObjectInterface object = iterator.next();
			returnList.add(object);
			if (iterator.hasNext()) {
				ObjectOperator op = new ObjectOperator(operator);
				returnList.add(op);
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @param matchingObjects
	 * @param relation
	 * @return
	 * @throws PlanningException
	 */
	private ArrayList<ObjectInterface> getRelative(
			ArrayList<ObjectInterface> matchingObjects, Term relation)
			throws PlanningException {
		ArrayList<ObjectInterface> returnList = new ArrayList<ObjectInterface>();
		CompoundTerm compound = (CompoundTerm) relation;
		RelativePosition rp = RelativePosition
				.getrelativepositionValueFromString(getAtomString(compound.args[0]));

		ArrayList<ObjectInterface> relativeObjects = new ArrayList<ObjectInterface>();
		if (compound.args[1].getTermType() == Term.COMPOUND) {
			relativeObjects = getObjects(compound.args[1]);
		} else {
			if (rp == RelativePosition.ONTOP
					&& getAtomString(compound.args[1]).equals("floor")) {
				rp = RelativePosition.ONFLOOR;
			}
		}

		for (ObjectInterface o : matchingObjects) {
			if (o instanceof ObjectInWorld) {
				ObjectInWorld obj = (ObjectInWorld) o;
				boolean stillMatching = world.checkRelation(obj, rp,
						relativeObjects);
				if (stillMatching) {
					returnList.add(o);
				}
			} else {
				// TODO
			}

		}

		if (returnList.isEmpty()) {
			throw new PlanningException("No such object in world!!");
		}

		return returnList;
	}

	private String getAtomString(Term term) {
		AtomicTerm t = (AtomicTerm) term;
		return t.toString();
	}

}
