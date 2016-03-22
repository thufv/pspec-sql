package edu.thu.ss.spec.lang.expr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

class Statistics {

	static final String redundantTime = "redundant_time";

	static final String redundantRules = "redundant_rules";

	static final String aredundantTime = "aredundant_time";

	static final String aredundantRules = "aredundant_rules";

	static final String consistencyTime = "consistency_time";

	static final String consistencyLevel = "consistency_levels";

	static final String consistencyCandidate = "consistency_candidate";

	static final String consistencyConflict = "consistency_conflict";

	static final String consistencySeed = "consistency_seed";

	static final String[] keys = { redundantTime, redundantRules, aredundantTime, aredundantRules,
			consistencyTime, consistencyLevel, consistencyCandidate, consistencyConflict,
			consistencySeed };

	Map<String, Double> values = new HashMap<>();

	void add(String key, double value) {
		Double exist = values.get(key);
		if (exist == null) {
			values.put(key, value);
		} else {
			values.put(key, exist + value);
		}
	}

}

class Experiment {
	SortedMap<Integer, Statistics> map = new TreeMap<>();

	public Statistics getStatistics(int value) {
		Statistics stat = map.get(value);
		if (stat == null) {
			stat = new Statistics();
			map.put(value, stat);
		}
		return stat;
	}

}

public class Summarizer {

	public static void main(String[] args) throws Exception {
		String inputDir = args[0];
		String outputPath = args[1];
		int number = Integer.parseInt(args[2]);

		Experiment[] experiments = new Experiment[number];
		for (int i = 0; i < number; i++) {
			experiments[i] = new Experiment();
		}

		File input = new File(inputDir);
		File[] inputs = input.listFiles();

		for (File file : inputs) {
			String name = file.getName();
			if (!name.contains("-")) {
				continue;
			}

			String[] parts = name.split("-");

			String expr = parts[0];
			Integer value = Integer.valueOf(parts[1]);
			Integer n = Integer.valueOf(parts[2]);

			Experiment experiment = experiments[n];
			Statistics stat = experiment.getStatistics(value);

			parseFile(expr, file, stat);
		}

		Experiment finalExperiment = mergeExperiments(experiments);

		outputExperiment(outputPath, inputDir, finalExperiment);

	}

	private static void parseFile(String expr, File file, Statistics stat) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line;

		while ((line = reader.readLine()) != null) {
			if (!line.contains("###")) {
				continue;
			}
			line = line.replace("###", "");
			String[] parts = line.split(" ");
			String param = parts[0];
			Double value = Double.valueOf(parts[1]);
			switch (param) {
			case "time":
				switch (expr) {
				case "redundancy":
					stat.values.put(Statistics.redundantTime, value);
					break;
				case "aredundancy":
					stat.values.put(Statistics.aredundantTime, value);
				case "consistency":
					stat.values.put(Statistics.consistencyTime, value);
				default:
					break;
				}
			case "redundants":
				switch (expr) {
				case "redundancy":
					stat.values.put(Statistics.redundantRules, value);
					break;
				case "aredundancy":
					stat.values.put(Statistics.aredundantRules, value);
					break;
				}
				break;
			case "average_levels":
				stat.values.put(Statistics.consistencyLevel, value);
				break;
			case "average_conflicts":
				stat.values.put(Statistics.consistencyConflict, value);
				break;
			case "average_candidates":
				stat.values.put(Statistics.consistencyCandidate, value);
				break;
			case "seeds":
				stat.values.put(Statistics.consistencySeed, value);
				break;
			default:
				break;
			}

		}
		reader.close();

	}

	private static Experiment mergeExperiments(Experiment[] experiments) {
		Experiment sum = new Experiment();

		for (Experiment experiment : experiments) {
			for (int value : experiment.map.keySet()) {
				Statistics sumStat = sum.getStatistics(value);

				Statistics stat = experiment.getStatistics(value);
				for (String key : Statistics.keys) {
					sumStat.add(key, stat.values.get(key));
				}
			}
		}

		int n = experiments.length;
		for (int value : sum.map.keySet()) {
			Statistics stat = sum.getStatistics(value);
			for (String key : Statistics.keys) {
				stat.values.put(key, stat.values.get(key) / n);
			}
		}
		return sum;
	}

	private static void outputExperiment(String path, String param, Experiment experiment)
			throws Exception {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}

		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}

		PrintWriter writer = new PrintWriter(file);
		writer.print(path);
		writer.print('\t');
		for (String key : Statistics.keys) {
			writer.print(key);
			writer.print('\t');
		}
		writer.println();
		for (int value : experiment.map.keySet()) {
			writer.print(value);
			writer.print('\t');
			Statistics stat = experiment.map.get(value);
			for (String key : Statistics.keys) {
				writer.print(stat.values.get(key));
				writer.print('\t');
			}
			writer.println();
		}
		writer.flush();
		writer.close();
	}
}
