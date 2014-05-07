package group3.planning;

import group3.definitions.ObjectInWorld;
import group3.definitions.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSPGenerator {
	
	private World w;
	private HashMap<String, ArrayList<String>> varDom;
	
	public CSPGenerator(World world) {
		this.w = world;
		generateDomain();
		
		
	}
	
	private void generateDomain() {
		ArrayList<ObjectInWorld> y = w.getAllObjects();
		int x = w.getWorldSize();
		ArrayList<String> domTemp = null;
		
		for(ObjectInWorld yy : y){
			int yTemp = 1;
			for(int t = 0; t < x; t++){
				domTemp.add(yTemp + "." + t+1);
			}
		}
		for(ObjectInWorld yy : y){
			varDom.put(yy.getId(), domTemp);
		}
	}

	public CSPGenerator(World world, int horizon) {
		this.w = world;
		
	}
	

}
