package group3.planning;

import group3.definitions.World;

import java.util.ArrayList;
import java.util.List;

public class State {

	private int stack;
	private int height;
	
	public State(int s, int h) {
		this.stack = s;
		this.height = h;
	}
	
	public void setState(int s, int h){
		this.stack = s;
		this.height = h;
	}
	
	public int getStack(){
		return stack;
	}
	public int getHeight(){
		return height;
	}
}
