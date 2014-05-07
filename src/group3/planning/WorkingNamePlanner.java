package group3.planning;

import group3.definitions.World;

public class WorkingNamePlanner extends Planner {
	
	private World originalWorld;
/**
 * In order to apply backtracking to a specific class of problems, one must provide the data P 
 * for the particular instance of the problem that is to be solved, and six procedural parameters, 
 * root, reject, accept, first, next, and output. These procedures should take the instance data P 
 * as a parameter and should do the following:
 * root(P): return the partial candidate at the root of the search tree.
 * reject(P,c): return true only if the partial candidate c is not worth completing.
 * accept(P,c): return true if c is a solution of P, and false otherwise.
 * first(P,c): generate the first extension of candidate c.
 * next(P,s): generate the next alternative extension of a candidate, after the extension s.
 * output(P,c): use the solution c of P, as appropriate to the application.
 * The backtracking algorithm reduces then to the call bt(root(P)), where bt is the following recursive 
 * procedure:
 * procedure bt(c)
 * if reject(P,c) then return
 * if accept(P,c) then output(P,c)
 * s <- first(P,c)
 * while s != null do
 * bt(s)
 * s <- next(P,s)
 */
	
	public Plan backtrackingAlgorithm(World w) {
		if (reject(originalWorld, w))
			return null;
		if (accept(originalWorld, w))
			return output(originalWorld, w);
		World testWorld = first(originalWorld, w);
		while (testWorld != null) {
			backtrackingAlgorithm(testWorld);
			testWorld = next(originalWorld, testWorld);
		}
		return null;
	}
	
	private World root() {
		return null;
	}
	
	private Boolean reject(World originalWorld, World w) {
		return false;
	}
	
	private Boolean accept(World originalWorld, World w) {
		return null;
	}
	
	private Plan output(World originalWorld, World w) {
		return null;
	}
	
	private World first(World originalWorld, World w) {
		return null;
	}
	
	private World next(World originalWorld, World testWorld) {
		return null;
	}
	
}
