package edu.thu.ss.spec.lang.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser;

public class PolicyParserTest {

	private static final String prefix = "src/test/res/";
	
	
	public void testMeta() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "spark-policy.xml", false, false);
			System.out.println(policy);

			XMLMetaRegistryParser metaParser = new XMLMetaRegistryParser();
			MetaRegistry registry = metaParser.parse(prefix + "spark-meta.xml");
			System.out.println(registry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void testConflict() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "conflict-policy.xml", false, true);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
  public void testRedundancy() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "redundancy-policy.xml", false, true);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void testGlobalRedundancy() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "global-redundancy-policy.xml", true, true);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testIntel() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("intel/spark-policy.xml", false, true);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGlobalDP() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "dp-global-policy.xml", false, false);
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testFineDP() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "dp-fine-policy.xml", false, false);
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
