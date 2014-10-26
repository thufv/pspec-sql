package edu.thu.ss.xml.parser;

import org.junit.Test;

import edu.thu.ss.xml.pojo.Policy;

public class PolicyParserTest {

	@Test
	public void testParse() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/example-policy.xml");
			System.out.println(policy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
