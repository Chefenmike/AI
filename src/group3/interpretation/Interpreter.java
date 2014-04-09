package group3.interpretation;

import java.util.ArrayList;
import java.util.List;

import gnu.prolog.term.AtomicTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.CompoundTerm;
import group3.definitions.Colour;
import group3.definitions.ObjectInWorld;
import group3.definitions.RelativePosition;
import group3.definitions.Shape;
import group3.definitions.Size;
import group3.definitions.World;
import group3.planning.Goal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Interpreter {
	private World world;
	private String holding;
	private JSONObject objects;

	public Interpreter(JSONArray world, String holding, JSONObject objects) {
		this.world = new World(world, holding, objects);
	}

	/**
	 * Converts a tree to a list of goals.
	 * 
	 * @param tree
	 *            The tree to be interpreted as goals.
	 * @return a list of all possible interpretations of the tree.
	 */
	public List<Goal> interpret(Term tree) {
		CompoundTerm term = (CompoundTerm) tree;
		List<Goal> goals = new ArrayList<Goal>();
		ArrayList<ObjectInWorld> possibleObjects = new ArrayList<ObjectInWorld>();

		String command = term.tag.toString();
		if (command.contains("take")) {
			possibleObjects = getObjects(term.args[0]);

			// create a goal for every possible object that will fulfill the
			// goal
			for (ObjectInWorld o : possibleObjects) {
				Goal g = new Goal(o, RelativePosition.HOLDING);
				g.setString(o.getId());
				goals.add(g);
			}
		} else if (command.contains("move")) {
			possibleObjects = getObjects(term.args[0]);
			CompoundTerm relative = (CompoundTerm) term.args[1];

			if (relative.args[1].getTermType() == Term.ATOM) {
				// Target = floor
				RelativePosition rp = RelativePosition.ONFLOOR;
				for (ObjectInWorld o : possibleObjects) {
					Goal g = new Goal(o, rp);
					g.setString(o.getId() + " " + rp.toString());
					goals.add(g);
				}
			} else {
				RelativePosition rp = RelativePosition
						.getrelativepositionValueFromString(getAtomString(relative.args[0]));
				ArrayList<ObjectInWorld> relativeObjects = getObjects(relative.args[1]);

				for (ObjectInWorld o : possibleObjects) {
					for (ObjectInWorld r : relativeObjects) {
						Goal g = new Goal(o, rp, r);
						g.setString(o.getId() + " " + rp.toString() + " "
								+ r.getId());
						goals.add(g);
					}
				}
			}
		}

		return goals;
	}

	/**
	 * Returns a list of all objects in the world that fits the description
	 * given in the input Prolog-term.
	 * 
	 * @param term
	 * @return
	 */
	public ArrayList<ObjectInWorld> getObjects(Term term) {
		ArrayList<ObjectInWorld> returnList = new ArrayList<ObjectInWorld>();

		CompoundTerm compound = (CompoundTerm) term;

		if (compound.tag.toString().contains("object")) {
			Shape shape = Shape
					.getShapeValueFromString(getAtomString(compound.args[0]));
			Size size = Size
					.getSizeValueFromString(getAtomString(compound.args[1]));
			Colour color = Colour
					.getColourValueFromString(getAtomString(compound.args[2]));

			for (ArrayList<ObjectInWorld> a : world
					.getWorldRepresentationList()) {
				for (ObjectInWorld obj : a) {
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
			}
		} else if (compound.tag.toString().contains("basic_entity")) {
			switch (getAtomString(compound.args[0])) {		
			case "the":
				returnList.add(getObjects(compound.args[1]).get(0));
				return returnList;
			case "any":
				returnList.add(getObjects(compound.args[1]).get(0));
				return returnList; // TODO: Fix proper Any clause.
			case "all":
				return getObjects(compound.args[1]);
			}
		} else if (compound.tag.toString().contains("relative_entity")) {
			ArrayList<ObjectInWorld> matchingObjects = new ArrayList<ObjectInWorld>();
			switch (getAtomString(compound.args[0])) {
			case "the":
				matchingObjects.add(getObjects(compound.args[1]).get(0));
				return getRelative(matchingObjects, compound.args[2]);
			case "any":
				//TODO: fix proper any clause
				matchingObjects.addAll(getObjects(compound.args[1]));
				return getRelative(matchingObjects, compound.args[2]);
			case "all":
				matchingObjects = getObjects(compound.args[1]);
				return getRelative(matchingObjects, compound.args[2]);
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @param matchingObjects
	 * @param relation
	 * @return
	 */
	private ArrayList<ObjectInWorld> getRelative(ArrayList<ObjectInWorld> matchingObjects, Term relation) {
		ArrayList<ObjectInWorld> returnList = new ArrayList<ObjectInWorld>();
		CompoundTerm compound = (CompoundTerm) relation;
		RelativePosition rp = RelativePosition
				.getrelativepositionValueFromString(getAtomString(compound.args[0]));
		
		ArrayList<ObjectInWorld> relativeObjects = new ArrayList<ObjectInWorld>();
		if(compound.args[1].getTermType()==Term.COMPOUND) {
			relativeObjects = getObjects(compound.args[1]);
		} else {
			if (rp==RelativePosition.ONTOP && getAtomString(compound.args[1]).equals("floor")) {
				rp = RelativePosition.ONFLOOR;
			}
		}

		for (ObjectInWorld o : matchingObjects) {
			boolean stillMatching = world.checkRelation(o, rp, relativeObjects);
			if (stillMatching) {
				returnList.add(o);
			}
		}

		return returnList;
	}

	private String getAtomString(Term term) {
		AtomicTerm t = (AtomicTerm) term;
		return t.toString();
	}

}
