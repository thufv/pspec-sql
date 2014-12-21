package edu.thu.ss.spec.lang.parser;

import java.io.File;

import org.junit.Test;

import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser;

public class PolicyParserTest {
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

	@Test
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

	public static void main1(String[] args) {
		String path = "/Users/luochen/Documents/Research/DSGen_v1.1.0/data/";
		File dir = new File(path);
		File[] files = dir.listFiles();
		String pattern = "load data  local infile 'pos_0' into table pos_1 "
				+ " fields terminated by '|'  lines terminated by '\\n';  ";

		for (File f : files) {
			if (f.getName().contains(".DS_Store")) {
				continue;
			}
			String table = f.getName().split("\\.")[0];
			System.out.println(pattern.replaceAll("pos_0", f.getAbsolutePath()).replaceAll("pos_1", table));
		}
	}
}
