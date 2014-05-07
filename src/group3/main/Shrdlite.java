package group3.main;

/**
 * If consoleTest is set to true the program will run in eclipse console
 * If consoleTest is false, program should be runnable via the flashy web-thingy
 * smallWorld will set small or medium world
 * 
 * Just hit Run Shrdlite
 * Program takes commands like this "Take the white ball" [ENTER] (no "fnutts")
 * 
 * For now, something is wrong with the interpretator.
 * 
 * TODO: Add medium world
 *
 *
 */

import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import javax.naming.TimeLimitExceededException;

import gnu.prolog.term.Term;
import gnu.prolog.vm.PrologException;
import group3.interpretation.Interpreter;
import group3.parsing.DCGParser;
import group3.planning.BreadthFirstPlanner;
import group3.planning.CompositeGoal;
import group3.planning.Goal;
import group3.planning.Plan;
import group3.planning.Planner;
import group3.utils.AbsolutePaths;
import group3.world.World;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Shrdlite {
	private static World currentWorld;

	private static boolean consoleTest = true;
	private static String worldPath = "examples\\small.json";
	//private static String worldPath = "examples\\medium.json";

	public static void main(String[] args) throws PrologException,
	ParseException, IOException, TimeLimitExceededException {
		JSONObject jsinput;

		JSONArray utterance;
		JSONArray world;
		String holding;
		JSONObject objects;

		if (consoleTest) {
			String command = "[]";
			jsinput = (JSONObject) JSONValue.parse(readFromFile(worldPath));

			world = (JSONArray) jsinput.get("world");
			utterance = (JSONArray) jsinput.get("utterance");
			holding = (String) jsinput.get("holding");
			objects = (JSONObject) jsinput.get("objects");
			currentWorld = new World(world, holding, objects);
		} else {

			jsinput = (JSONObject) JSONValue.parse(readFromStdin());
			utterance = (JSONArray) jsinput.get("utterance");
			world = (JSONArray) jsinput.get("world");
			holding = (String) jsinput.get("holding");
			objects = (JSONObject) jsinput.get("objects");
		}
		JSONObject result = new JSONObject();

		//result.put("utterance", utterance);

		while (consoleTest) {
			System.out.print("What would you like me to do? \n");
			String takeCmd = "";
			try {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				takeCmd = in.readLine();
			} catch (IOException io) {
				io.printStackTrace();
			}
			List<String> lista = new ArrayList<String>();

			String[] out = takeCmd.split(" ");

			for (int i = 0; i < out.length; i++) {
				lista.add(out[i]);
			}
			result.put("utterance", lista);

			// // This is how to get information about the top object in column
			// 1:
			// JSONArray column = (JSONArray) world.get(1);
			// String topobject = (String) column.get(column.size() - 1);
			// JSONObject objectinfo = (JSONObject) objects.get(topobject);
			// String form = (String) objectinfo.get("form");

			DCGParser parser = new DCGParser(AbsolutePaths.DCGPARSERFILE);
			List<Term> trees = parser.parseSentence("command", lista);
			List tstrs = new ArrayList();
			result.put("trees", tstrs);
			for (Term t : trees) {
				tstrs.add(t.toString());
			}

			if (trees.isEmpty()) {
				result.put("output", "Parse error!");

			} else {
				List<CompositeGoal> goals = new ArrayList();
				Interpreter interpreter = new Interpreter(currentWorld);
				for (Term tree : trees) {
					goals.addAll(interpreter.interpret(tree));
				}
				result.put("goals", goals.get(0));

				if (goals.isEmpty()) {
					result.put("output", "Interpretation error!");

				} else if (goals.size() > 1) {
					result.put("output", "Ambiguity error!");

				} else {
					Planner planner = new BreadthFirstPlanner(currentWorld);
					//CompositeGoal compositeGoal = new CompositeGoal();
					//compositeGoal.addAllGoals(goals);
					Plan plan = planner.findSolution(goals.get(0));

					result.put("plan", plan.getPlan());

					if (plan.getPlan().isEmpty()) {
						result.put("output", "Planning error!");
					} else {
						result.put("output", "Success!");
						System.out.println(plan.getWorld().getWorldAsString());
						currentWorld = plan.getWorld();
					}
				}
			}

			System.out.println(result);
		}
	}

	public static String readFromStdin() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder data = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			data.append(line).append('\n');
		}
		return data.toString();
	}

	public static String readFromFile(String path) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(path));
		StringBuilder data = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			data.append(line).append('\n');
		}
		in.close();
		return data.toString();
	}
}