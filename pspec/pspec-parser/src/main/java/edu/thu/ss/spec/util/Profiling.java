package edu.thu.ss.spec.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Profiling {

	public static class Stat {
		public int count;
		public double value;
		public double min = Double.MAX_VALUE;
		public double max = Double.MIN_VALUE;

	}

	private static Map<String, Stat> stats = new LinkedHashMap<>();

	public static final String Solving_Time = "solving";

	public static final String Ada_Number = "ada number";

	public static final String Substitute = "subsitute";

	public static final boolean enable = false;

	private static long lastTime = 0L;

	public static void profile(String key, double value) {
		if (!enable) {
			return;
		}
		Stat stat = stats.get(key);
		if (stat == null) {
			stat = new Stat();
			stats.put(key, stat);
		}

		stat.count++;
		stat.value += value;
		if (stat.min > value) {
			stat.min = value;
		}
		if (stat.max < value) {
			stat.max = value;
		}
	}

	public static void beginTiming(String key) {
		if (!enable) {
			return;
		}
		lastTime = System.currentTimeMillis();
	}

	public static void endTiming(String key) {
		if (!enable) {
			return;
		}
		assert (lastTime != 0);

		profile(key, System.currentTimeMillis() - lastTime);
		lastTime = 0;
	}

	public static void print() {
		for (Entry<String, Stat> e : stats.entrySet()) {
			System.out.println(e.getKey());
			Stat stat = e.getValue();
			System.out.println("count: " + stat.count);
			System.out.println("value: " + stat.value / stat.count);
			System.out.println("min: " + stat.min);
			System.out.println("max: " + stat.max);
			System.out.println();
		}

	}

}
