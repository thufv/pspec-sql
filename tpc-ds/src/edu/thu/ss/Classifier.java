package edu.thu.ss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Classifier {

	public static void main(String[] args) throws Exception {
		String path = "query/queries_all.sql";
		BufferedReader reader = new BufferedReader(new FileReader(path));
		PrintWriter privateOut = new PrintWriter(new File("category/private.sql"));
		PrintWriter publicOut = new PrintWriter(new File("category/public.sql"));
		String line = null;

		List<String> list = new LinkedList<>();
		boolean isPublic = true;
		while ((line = reader.readLine()) != null) {
			if (line.contains("start")) {
				if (list.size() > 0) {
					PrintWriter out = isPublic ? publicOut : privateOut;
					for (String str : list) {
						out.println(str);
					}
					out.println();
				}
				list.clear();
			} else if (line.contains("public")) {
				isPublic = true;
			} else if (line.contains("private")) {
				isPublic = false;
			}

			list.add(line);
		}
		reader.close();
		privateOut.close();
		publicOut.close();
	}

}
