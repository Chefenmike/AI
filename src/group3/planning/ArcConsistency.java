package group3.planning;

import group3.definitions.ObjectInWorld;

import java.util.ArrayList;

public class ArcConsistency {
	
	private ArrayList<ArrayList<State>> allowedStates;

	public ArcConsistency(ArrayList<ObjectInWorld> objectList, int worldSize){
		int stackSize = objectList.size();
		ArrayList<State> tempList = null;
		
		for(int i = 0; i < worldSize; i++){
			for(int j = 0; j < stackSize; j++){
				tempList.add(new State(i,j)); 
			}
		}
		
		allowedStates = genStates(objectList, tempList);
		
	}

	private ArrayList<ArrayList<State>> genStates(ArrayList<ObjectInWorld> objectList,
			ArrayList<State> stateList) {
		
		ArrayList<State> tempList = null;
		ArrayList<ArrayList<State>> tempListList = null;
		
		for(int i = 0; i < stateList.size(); i++){
			for(int j = 0; j < stateList.size(); j++){
				if(!stateList.get(i).equals(stateList.get(j))){
					tempList.add(stateList.get(i));
					tempList.add(stateList.get(j));
					}
				}
			tempListList.add(tempList);
			tempList = null;
		}
		
		
		return null;
		
	}

}
