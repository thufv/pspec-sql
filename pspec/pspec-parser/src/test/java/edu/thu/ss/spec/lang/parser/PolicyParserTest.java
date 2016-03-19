package edu.thu.ss.spec.lang.parser;

import org.junit.Test;

import edu.thu.ss.spec.lang.analyzer.consistency.ConsistencyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.redundancy.ApproximateRedundancyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.redundancy.RedundancyAnalyzer;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser;

public class PolicyParserTest {

	private static final String prefix = "src/test/res/";

	public void testMeta() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "spark-policy.xml");
			System.out.println(policy);

			XMLMetaRegistryParser metaParser = new XMLMetaRegistryParser();
			MetaRegistry registry = metaParser.parse(prefix + "spark-meta.xml");
			System.out.println(registry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConflict() {
		try {
			PolicyParser parser = new PolicyParser();
			parser.addAnalyzer(new ConsistencyAnalyzer(EventTable.getDummy()));
			Policy policy = parser.parse(prefix + "conflict-policy.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testRedundancy() {
		try {
			PolicyParser parser = new PolicyParser();
			parser.addAnalyzer(new ApproximateRedundancyAnalyzer(EventTable.getDummy()));
			parser.addAnalyzer(new RedundancyAnalyzer(EventTable.getDummy()));
			Policy policy = parser.parse(prefix + "redundancy-policy.xml");
			//System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
