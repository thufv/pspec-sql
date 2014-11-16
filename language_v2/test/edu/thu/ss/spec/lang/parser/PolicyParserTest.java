package edu.thu.ss.spec.lang.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.JoinCondition;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistry;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser;

public class PolicyParserTest {

	public void testParse() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/example-policy.xml");
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testTpcDs() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("tpc-ds/store-policy.xml");
			System.out.println(policy);

			XMLMetaRegistryParser metaParser = new XMLMetaRegistryParser(policy);
			MetaRegistry registry = metaParser.parse("tpc-ds/tpc-ds-meta.xml");
			System.out.println(registry);

			DataCategory data = registry.lookup("tpc", "date_dim", "d_date");
			assertSame(null, data);

			Map<JoinCondition, DataCategory> conds = registry.conditionalLookup("tpc", "datE_dim", "d_Date");
			assertEquals(1, conds.size());

			conds = registry.conditionalLookup("tpc", "date_dim", "d_dom");
			assertEquals(2, conds.size());
			DesensitizeOperation op = registry.lookup(policy.getDatas().get("sa"), "sum1", "tpc", "date_dim", "d_dom");
			assertSame(null, op);

			op = registry.lookup(policy.getDatas().get("financial"), "sum1", "Tpc", "dAte_dim", "d_dom");
			assertEquals("sum", op.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSpark() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/spark-policy.xml");
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testConflict() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/conflict.xml");
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testRedundancy() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/redundancy.xml");
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
