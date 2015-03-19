package edu.thu.ss.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Sorter {

	public static void main(String[] args) throws Exception {
		String path = "queries.sql";

		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;

		SortedMap<Integer, List<String>> map = new TreeMap<>();
		String template = "";
		int index = 0;
		List<String> list = new ArrayList<>();
		while ((line = reader.readLine()) != null) {
			if (line.contains("-- end")) {
				line = "--end query " + index + " using template " + template;
				list.add(line);
				map.put(index, list);
				list = new ArrayList<>();
			} else if (line.contains("-- start")) {
				String[] strs = line.split(" ");
				template = strs[strs.length - 1];

				index = Integer.valueOf(template.substring(5).split("\\.")[0]);

				line = "--start query " + index + " using template " + template;
				list.add(line);
			} else {
				list.add(line);
			}
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter("sorted.sql"));

		for (Integer key : map.keySet()) {
			for (String line1 : map.get(key)) {
				writer.append(line1);
				writer.append('\n');
			}
			writer.append('\n');
		}
		reader.close();
		writer.close();
	}
}
