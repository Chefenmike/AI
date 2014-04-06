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
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import gnu.prolog.term.Term;
import gnu.prolog.vm.PrologException;
import group3.interpretation.Interpreter;
import group3.parsing.DCGParser;
import group3.planning.Goal;
import group3.planning.Plan;
import group3.planning.Planner;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Shrdlite {

	private static boolean consoleTest = true;
	private static boolean smallWorld = true;

	public static void main(String[] args) throws PrologException,
			ParseException, IOException {
		JSONObject jsinput;

		JSONArray utterance;
		JSONArray world;
		String holding;
		JSONObject objects;

		String takeCmd = "";

		if (consoleTest) {
			if (smallWorld) {
				System.out
						.print("The world: [\"e\"],[\"g\",\"l\"],[],[\"k\",\"m\",\"f\"],[] \n");
				System.out
						.print("The objects: \n{\"e\":{\"form\":\"ball\",\"size\":\"large\",\"color\":\"white\"},\"f\":{\"form\":\"ball\",\"size\":\"small\",\"color\":\"black\"},\"g\":{\"form\":\"table\",\"size\":\"large\",\"color\":\"blue\"},\n\"k\":{\"form\":\"box\",\"size\":\"large\",\"color\":\"yellow\"},\"l\":{\"form\":\"box\",\"size\":\"large\",\"color\":\"red\"},\"m\":{\"form\":\"box\",\"size\":\"small\",\"color\":\"blue\"}}\n");
			} else {
				// Medium world here later
			}

			System.out.print("What would you like me to do? \n");

			try {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				if ((takeCmd = in.readLine()) != null && takeCmd.length() != 0) {
					// System.out.println(takeCmd);
				}
			} catch (IOException io) {
				io.printStackTrace();
			}

			String[] out = takeCmd.split(" ");
			String command = "[";
			for (int i = 0; i < out.length; i++) {
				if (i != 0) {
					command += ",\"" + out[i] + "\"";
				} else {
					command += "\"" + out[i] + "\"";
				}
			}
			command += "]";
			if (smallWorld) {
				jsinput = (JSONObject) JSONValue
						.parse("{\"world\":[[\"e\"],[\"g\",\"l\"],[],[\"k\",\"m\",\"f\"],[]],\"objects\":{\"a\":{\"form\":\"brick\",\"size\":\"large\",\"color\":\"green\"},\"b\":{\"form\":\"brick\",\"size\":\"small\",\"color\":\"white\"},\"c\":{\"form\":\"plank\",\"size\":\"large\",\"color\":\"red\"},\"d\":{\"form\":\"plank\",\"size\":\"small\",\"color\":\"green\"},\"e\":{\"form\":\"ball\",\"size\":\"large\",\"color\":\"white\"},\"f\":{\"form\":\"ball\",\"size\":\"small\",\"color\":\"black\"},\"g\":{\"form\":\"table\",\"size\":\"large\",\"color\":\"blue\"},\"h\":{\"form\":\"table\",\"size\":\"small\",\"color\":\"red\"},\"i\":{\"form\":\"pyramid\",\"size\":\"large\",\"color\":\"yellow\"},\"j\":{\"form\":\"pyramid\",\"size\":\"small\",\"color\":\"red\"},\"k\":{\"form\":\"box\",\"size\":\"large\",\"color\":\"yellow\"},\"l\":{\"form\":\"box\",\"size\":\"large\",\"color\":\"red\"},\"m\":{\"form\":\"box\",\"size\":\"small\",\"color\":\"blue\"}},\"holding\":\"null\",\"utterance\":"
								+ command + "}");
			} else {
				// Add medium world later
				jsinput = (JSONObject) JSONValue
						.parse("{\"world\":[[\"e\"],[\"g\",\"l\"],[],[\"k\",\"m\",\"f\"],[]],\"objects\":{\"a\":{\"form\":\"brick\",\"size\":\"large\",\"color\":\"green\"},\"b\":{\"form\":\"brick\",\"size\":\"small\",\"color\":\"white\"},\"c\":{\"form\":\"plank\",\"size\":\"large\",\"color\":\"red\"},\"d\":{\"form\":\"plank\",\"size\":\"small\",\"color\":\"green\"},\"e\":{\"form\":\"ball\",\"size\":\"large\",\"color\":\"white\"},\"f\":{\"form\":\"ball\",\"size\":\"small\",\"color\":\"black\"},\"g\":{\"form\":\"table\",\"size\":\"large\",\"color\":\"blue\"},\"h\":{\"form\":\"table\",\"size\":\"small\",\"color\":\"red\"},\"i\":{\"form\":\"pyramid\",\"size\":\"large\",\"color\":\"yellow\"},\"j\":{\"form\":\"pyramid\",\"size\":\"small\",\"color\":\"red\"},\"k\":{\"form\":\"box\",\"size\":\"large\",\"color\":\"yellow\"},\"l\":{\"form\":\"box\",\"size\":\"large\",\"color\":\"red\"},\"m\":{\"form\":\"box\",\"size\":\"small\",\"color\":\"blue\"}},\"holding\":\"null\",\"utterance\":"
								+ command + "}");
			}

			world = (JSONArray) jsinput.get("world");
			utterance = (JSONArray) jsinput.get("utterance");
			holding = (String) jsinput.get("holding");
			objects = (JSONObject) jsinput.get("objects");

			// Might be usefull later
			/*
			 * System.out.print("The world: " + world.toString() + "\n");
			 * System.out.print("The utterance: " + utterance.toString() +
			 * "\n"); System.out.print("Holding: " + holding.toString() + "\n");
			 * System.out.print("Objects: " + objects.toString() + "\n");
			 */
		} else {

			jsinput = (JSONObject) JSONValue.parse(readFromStdin());
			utterance = (JSONArray) jsinput.get("utterance");
			world = (JSONArray) jsinput.get("world");
			holding = (String) jsinput.get("holding");
			objects = (JSONObject) jsinput.get("objects");
		}
		JSONObject result = new JSONObject();

		result.put("utterance", utterance);
		// System.out.print(result+"\n");

		// // This is how to get information about the top object in column 1:
		// JSONArray column = (JSONArray) world.get(1);
		// String topobject = (String) column.get(column.size() - 1);
		// JSONObject objectinfo = (JSONObject) objects.get(topobject);
		// String form = (String) objectinfo.get("form");

		DCGParser parser = new DCGParser("shrdlite_grammar.pl");
		List<Term> trees = parser.parseSentence("command", utterance);
		List tstrs = new ArrayList();
		result.put("trees", tstrs);
		for (Term t : trees) {
			tstrs.add(t.toString());
		}

		if (trees.isEmpty()) {
			result.put("output", "Parse error!");

		} else {
			List<Goal> goals = new ArrayList();
			Interpreter interpreter = new Interpreter(world, holding, objects);
			for (Term tree : trees) {
				for (Goal goal : interpreter.interpret(tree)) {
					goals.add(goal);
				}

			}
			result.put("goals", goals);

			if (goals.isEmpty()) {
				result.put("output", "Interpretation error!");

			} else if (goals.size() > 1) {
				result.put("output", "Ambiguity error!");

			} else {
				Planner planner = new Planner(world, holding, objects);
				Plan plan = planner.solve(goals.get(0));

				result.put("plan", plan.getPlan());

				if (plan.getPlan().isEmpty()) {
					result.put("output", "Planning error!");
				} else {
					result.put("output", "Success!");
				}
			}
		}

		System.out.print(result);
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
}