package edu.thu.ss.spec.lang.exp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LineCounter {

	public static void count(String path, ExperimentStat stat, int n)
			throws IOException {
		int count = 0;
		BufferedReader reader = new BufferedReader(new FileReader(path));

		while (reader.readLine() != null) {
			count++;
		}

		stat.line[n] = count;
		System.out.println(path + " contains " + count + " lines");

		reader.close();
	}

}
