package edu.thu.ss.spec.lang.exp;

import java.util.List;

import edu.thu.ss.spec.lang.parser.PolicyWriter;
import edu.thu.ss.spec.lang.parser.VocabularyParser;
import edu.thu.ss.spec.lang.pojo.ContactInfo;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

public class PolicyGenerator {

	public PolicyGenerator() {
	}

	private void init(Policy policy, String vocabPath, String userContainerId, String dataContainerId) {
		Info policyInfo = new Info();

		ContactInfo contact = new ContactInfo();
		contact.setAddress("Beijing China");
		contact.setCountry("China");
		contact.setEmail("luochen01@vip.qq.com");
		contact.setOrganization("Tsinghua University");
		contact.setName("Luo Chen");

		policy.setInfo(policyInfo);
		policy.setVocabularyLocation(vocabPath);
	}

	public void generate(RuleGenerator generator, int num, String outputPath, String vocabPath,
			String userId, String dataId) throws Exception {
		Policy policy = new Policy();
		init(policy, vocabPath, userId, dataId);
		List<ExpandedRule> rules = generator.generate(num);
		policy.setExpandedRules(rules);
		PolicyWriter writer = new PolicyWriter();
		writer.output(policy, outputPath);
	}

	public static void main(String[] args) throws Exception {
		VocabularyParser vocabParser = new VocabularyParser();

		Vocabulary vocab = vocabParser.parse("paper/spark-vocab.xml");

		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.setUsers(vocab.getUserContainer());
		ruleGenerator.setDatas(vocab.getDataContainer());
		ruleGenerator.forbidRatio = 0.2;
		ruleGenerator.maxDim = 5;

		PolicyGenerator generator = new PolicyGenerator();

		generator.generate(ruleGenerator, 200, "tmp/output.xml", "paper/spark-vocab.xml", "tpcds-user",
				"tpcds-data");
	}

}
