package edu.thu.ss.spec.lang.expr;

import java.util.List;

import edu.thu.ss.spec.lang.parser.PolicyWriter;
import edu.thu.ss.spec.lang.parser.VocabularyParser;
import edu.thu.ss.spec.lang.pojo.ContactInfo;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.util.XMLUtil;

public class PolicyGenerator {

	public PolicyGenerator() {
	}

	private void init(Policy policy, String vocabPath) {
		Info policyInfo = new Info();

		ContactInfo contact = new ContactInfo();
		contact.setAddress("Beijing China");
		contact.setCountry("China");
		contact.setEmail("luochen01@vip.qq.com");
		contact.setOrganization("Tsinghua University");
		contact.setName("Luo Chen");

		policy.setInfo(policyInfo);
		policy.setVocabularyLocation(XMLUtil.toUri(vocabPath));
	}

	public void generate(RuleGenerator generator, int num, String outputPath, String vocabPath)
			throws Exception {
		Policy policy = new Policy();
		init(policy, vocabPath);
		List<Rule> rules = generator.generate(num);
		policy.setRules(rules);
		PolicyWriter writer = new PolicyWriter();
		writer.output(policy, outputPath);
	}

	public static void main(String[] args) throws Exception {
		VocabularyParser vocabParser = new VocabularyParser();

		Vocabulary vocab = vocabParser.parse("paper/spark-vocab.xml");

		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.setUsers(vocab.getUserContainer());
		ruleGenerator.setDatas(vocab.getDataContainer());
		ruleGenerator.maxDim = 5;

		PolicyGenerator generator = new PolicyGenerator();

		generator.generate(ruleGenerator, 200, "tmp/output.xml", "paper/spark-vocab.xml");
	}

}
