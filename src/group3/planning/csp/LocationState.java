package group3.planning.csp;

import group3.world.ObjectInWorld;

public class LocationState implements State {
	private ObjectInWorld object;
	private int column;
	private int row;
	
	public LocationState() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param oiw
	 * @param c
	 * @param r 
	 * 
	 * if c and r is -1, this object is being held.
	 */
	public LocationState(ObjectInWorld oiw, int c, int r) {
		object = oiw;
		column = c;
		row = r;
	}

}
