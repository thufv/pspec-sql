package edu.thu.ss.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Merger {

	public static void main(String[] args) throws Exception {
		String path = "transformed/";
		String out = "transformed/all.sql";
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("query");
			}
		});

		SortedMap<Integer, List<String>> map = new TreeMap<>();

		for (File f : files) {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = null;

			List<String> list = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			int index = Integer.valueOf(f.getName().substring(5).split("\\.")[0]);
			map.put(index, list);
			reader.close();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(out));

		for (Integer key : map.keySet()) {
			writer.append("--start query " + key + " using template query" + key + ".tpl\n");
			for (String line : map.get(key)) {
				writer.append(line);
				writer.append("\n");
			}
			writer.append("--end query " + key + " using template query" + key + ".tpl\n");
			writer.append('\n');

		}
		writer.close();
	}
}
