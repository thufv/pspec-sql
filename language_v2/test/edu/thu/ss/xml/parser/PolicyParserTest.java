package edu.thu.ss.xml.parser;

import org.junit.Test;

import edu.thu.ss.lang.parser.PolicyParser;
import edu.thu.ss.lang.pojo.Policy;

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
	public void testConflict() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/conflict.xml");
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
