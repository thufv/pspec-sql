package edu.thu.ss.spec.lang.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser;

public class PolicyParserTest {

	public void testPaper() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("paper/spark-policy.xml", false);
			System.out.println(policy);

			XMLMetaRegistryParser metaParser = new XMLMetaRegistryParser();
			MetaRegistry registry = metaParser.parse("paper/spark-meta.xml");
			System.out.println(registry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testMeta() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/spark-policy.xml", false);
			System.out.println(policy);

			XMLMetaRegistryParser metaParser = new XMLMetaRegistryParser();
			MetaRegistry registry = metaParser.parse("res/spark-meta.xml");
			System.out.println(registry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testConflict() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("test/res/conflict-policy.xml", false);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testRedundancy() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("test/res/redundancy-policy.xml", false);
			//	System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGlobalRedundancy() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("test/res/global-redundancy-policy.xml", true);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testIntel() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("intel/spark-policy.xml", false);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main1(String[] args) throws IOException {
		String path = "tmp";

		File dir = new File(path);

		File[] files = dir.listFiles();
		for (File f : files) {
			String table = f.getName();
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = null;
			System.out.println("<table name=\"" + table + "\">");
			while ((line = reader.readLine()) != null) {
				String[] tmp = line.split("\\.");
				String column = tmp[1];
				if (column.endsWith(",")) {
					column = column.substring(0, column.length() - 1);
				}
				column = column.substring(1, column.length() - 1);
				System.out.println("<column name=\"" + column + "\" data-category=\"\" />");

			}
			System.out.println("</table>");
			reader.close();

		}
	}
}
