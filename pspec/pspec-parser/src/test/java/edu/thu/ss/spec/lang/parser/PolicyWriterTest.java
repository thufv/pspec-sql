package edu.thu.ss.spec.lang.parser;

import org.junit.Test;

import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.manager.VocabularyManager;
import edu.thu.ss.spec.util.XMLUtil;

public class PolicyWriterTest {

	private static final String prefix = "src/main/test/";

	@Test
	public void testConflict() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "conflict-policy.xml");
			//System.out.println(policy);
			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/conflict-policy.xml");

			policy = parser.parse("tmp/conflict-policy.xml");
			System.out.println(policy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRedundancy() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(prefix + "redundancy-policy.xml");
			//System.out.println(policy);
			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/redundancy-policy.xml");

			policy = parser.parse("tmp/redundancy-policy.xml");
			System.out.println(policy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIntel() {
		try {
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse("intel/spark-policy.xml");
			//System.out.println(policy);
			PolicyWriter writer = new PolicyWriter();
			writer.output(policy, "tmp/intel-policy.xml");

			policy = parser.parse("tmp/intel-policy.xml");
			System.out.println(policy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testVocab() {
		try {
			VocabularyParser parser = new VocabularyParser();
			parser.parse("paper/spark-vocab.xml");
			Vocabulary vocabulary = VocabularyManager.getVocab(XMLUtil.toUri("paper/spark-vocab.xml"));
			VocabularyManager.clear();
			VocabularyWriter writer = new VocabularyWriter();
			writer.output(vocabulary, "tmp/vocab.xml");
			parser.parse("tmp/vocab.xml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
