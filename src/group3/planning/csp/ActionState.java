package group3.planning.csp;

public class ActionState implements State {
	private int column;
	private String action;
	
	public ActionState() {
		// TODO Auto-generated constructor stub
	}
	
	public ActionState(String s, int c) {
		action = s;
		column = c;
	}
}
