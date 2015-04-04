package edu.thu.ss.spec.lang.parser;

import org.junit.Test;

import edu.thu.ss.spec.global.CategoryManager;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

public class PolicyWriterTest {

	public void testPaper() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("paper/spark-policy.xml", false, true);
			//System.out.println(policy);

			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/output.xml");

			policy = parser.parse("tmp/output.xml", false);

			System.out.println(policy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMeta() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("res/spark-policy.xml", false, true);
			//System.out.println(policy);
			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/spark-policy.xml");

			policy = parser.parse("tmp/spark-policy.xml", false, true);
			System.out.println(policy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConflict() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("test/res/conflict-policy.xml", false, true);
			//System.out.println(policy);
			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/conflict-policy.xml");

			policy = parser.parse("tmp/conflict-policy.xml", false, true);
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
			//System.out.println(policy);
			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/redundancy-policy.xml");

			policy = parser.parse("tmp/redundancy-policy.xml", false);
			System.out.println(policy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIntel() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("intel/spark-policy.xml", false);
			//System.out.println(policy);
			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/intel-policy.xml");

			policy = parser.parse("tmp/intel-policy.xml", false);
			System.out.println(policy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testVocab() {
		try {
			VocabularyParser parser = new VocabularyParser();
			parser.parse("paper/spark-vocab.xml", "default-user", "tpcds-data");
			Vocabulary vocabulary = CategoryManager.getVocab("paper/spark-vocab.xml");
			CategoryManager.clear();
			VocabularyWriter writer = new VocabularyWriter();
			writer.output(vocabulary, "tmp/vocab.xml");
			parser.parse("tmp/vocab.xml", "default-user", "tpcds-data");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
