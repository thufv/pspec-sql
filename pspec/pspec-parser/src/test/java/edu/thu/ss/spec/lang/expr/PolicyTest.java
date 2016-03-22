package edu.thu.ss.spec.lang.expr;

import org.junit.Test;

import edu.thu.ss.spec.lang.analyzer.IPolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.TiminingAnalyzer;
import edu.thu.ss.spec.lang.analyzer.consistency.ConsistencyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.redundancy.ApproximateRedundancyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.redundancy.RedundancyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.analyzer.stat.ConsistencyStat;
import edu.thu.ss.spec.lang.analyzer.stat.RedundancyStat;
import edu.thu.ss.spec.lang.parser.PolicyParser;
import edu.thu.ss.spec.lang.parser.VocabularyParser;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.manager.PolicyManager;
import edu.thu.ss.spec.manager.VocabularyManager;
import edu.thu.ss.spec.util.Profiling;

public class PolicyTest {

	String outputPath = "tmp/output-1.xml";

	String vocabPath = "misc/experiment/expr-vocab.xml";

	String userContainerId = "expr-user-10";

	String dataContainerId = "expr-data-60";

	int times = 3;

	int[] rules = { 200, 400, 600, 800, 1000 };

	int defaultRule = 800;

	VocabularyParser vocabParser = new VocabularyParser();

	PolicyGenerator generator = new PolicyGenerator();

	public void testRedundancy1() throws Exception {

		RuleGenerator ruleGenerator = new RuleGenerator();

		for (int rule : rules) {
			AnalyzerStat stat = new RedundancyStat();
			IPolicyAnalyzer analyzer = new ApproximateRedundancyAnalyzer(new EventTable());
			expr("Redundancy-" + rule, vocabPath, ruleGenerator, stat, analyzer, rule);
		}
	}

	public void testRedundancyUser() throws Exception {

		String[] userIds = { "expr-user-3", "expr-user-5", "expr-user-10", "expr-user-15",
				"expr-user-20" };
		RuleGenerator ruleGenerator = new RuleGenerator();
		for (String userId : userIds) {
			RedundancyStat stat = new RedundancyStat();
			expr("Redundancy-" + userId, vocabPath, ruleGenerator, stat,
					new ApproximateRedundancyAnalyzer(new EventTable()), defaultRule);
		}
	}

	public void testRedundancyData() throws Exception {

		String[] dataIds = { "expr-data-20", "expr-data-40", "expr-data-60", "expr-data-80",
				"expr-data-100" };

		RuleGenerator ruleGenerator = new RuleGenerator();

		for (String dataId : dataIds) {
			RedundancyStat stat = new RedundancyStat();
			expr("Redundancy-" + dataId, vocabPath, ruleGenerator, stat,
					new ApproximateRedundancyAnalyzer(new EventTable()), defaultRule);

		}

	}

	public void testRedundancyOp() throws Exception {

		String[] dataIds = { "expr-data-op-5", "expr-data-op-10", "expr-data-op-15", "expr-data-op-20",
				"expr-data-op-25" };

		RuleGenerator ruleGenerator = new RuleGenerator();

		for (String dataId : dataIds) {
			RedundancyStat stat = new RedundancyStat();
			expr("Redundancy-" + dataId, vocabPath, ruleGenerator, stat,
					new ApproximateRedundancyAnalyzer(new EventTable()), defaultRule);

		}

	}

	public void testRedundancyDim() throws Exception {
		int[] dims = { 1, 3, 5, 7, 9 };
		RuleGenerator ruleGenerator = new RuleGenerator();
		for (int dim : dims) {
			RedundancyStat stat = new RedundancyStat();
			ruleGenerator.maxDim = dim;
			expr("Redundancy-" + String.valueOf(dim), vocabPath, ruleGenerator, stat,
					new ApproximateRedundancyAnalyzer(new EventTable()), defaultRule);
		}
	}

	public void testRedundancyRes() throws Exception {
		int[] ress = { 1, 3, 5, 7, 9 };
		RuleGenerator ruleGenerator = new RuleGenerator();
		for (int res : ress) {
			RedundancyStat stat = new RedundancyStat();
			ruleGenerator.maxRes = res;
			expr("Redundancy-" + String.valueOf(res), vocabPath, ruleGenerator, stat,
					new ApproximateRedundancyAnalyzer(new EventTable()), defaultRule);
		}
	}

	private void expr(String expId, String vocabPath, RuleGenerator ruleGenerator, AnalyzerStat stat,
			IPolicyAnalyzer analyzer, int rule) throws Exception {
		VocabularyManager.clear();
		Vocabulary vocab = vocabParser.parse(vocabPath);
		ruleGenerator.setUsers(vocab.getUserContainer());
		ruleGenerator.setDatas(vocab.getDataContainer());
		for (int i = 0; i < times; i++) {
			generator.generate(ruleGenerator, rule, outputPath, vocabPath);
			PolicyManager.clear();
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(outputPath);
			IPolicyAnalyzer timingAnalyzer = new TiminingAnalyzer(analyzer, stat);
			timingAnalyzer.analyze(policy);

			System.gc();
		}

		System.out.println(ruleGenerator);
		System.out.println(stat);
		System.out.println(expId + " experiment finishes for #" + rule);
		System.out.println();

	}

	@Test
	public void test() throws Exception {
		Vocabulary vocab = vocabParser.parse("misc/experiment/expr-30-60-15.xml");
		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.setUsers(vocab.getUserContainer());
		ruleGenerator.setDatas(vocab.getDataContainer());

		generator.generate(ruleGenerator, 500, outputPath, "misc/experiment/expr-30-60-15.xml");
	}

	@Test
	public void testRedundancy() throws Exception {
		RedundancyStat rstat = new RedundancyStat();
		PolicyManager.clear();
		PolicyParser parser = new PolicyParser();
		Policy policy = parser.parse(outputPath);
		IPolicyAnalyzer<RedundancyStat> timingAnalyzer = new TiminingAnalyzer<RedundancyStat>(
				new RedundancyAnalyzer(EventTable.getDummy()), rstat);
		timingAnalyzer.analyze(policy);

		System.out.println(rstat);
	}

	@Test
	public void testARedundancy() throws Exception {
		RedundancyStat arstat = new RedundancyStat();
		PolicyManager.clear();
		PolicyParser parser = new PolicyParser();
		Policy policy = parser.parse(outputPath);
		IPolicyAnalyzer<RedundancyStat> timingAnalyzer = new TiminingAnalyzer<RedundancyStat>(
				new ApproximateRedundancyAnalyzer(EventTable.getDummy()), arstat);
		timingAnalyzer.analyze(policy);
		System.gc();

		System.out.println(arstat);

	}

	@Test
	public void testConsistency() throws Exception {
		ConsistencyStat stat = new ConsistencyStat();
		PolicyManager.clear();
		PolicyParser parser = new PolicyParser();
		Policy policy = parser.parse(outputPath);
		IPolicyAnalyzer<ConsistencyStat> timingAnalyzer = new TiminingAnalyzer<ConsistencyStat>(
				new ConsistencyAnalyzer(EventTable.getDummy()), stat);
		timingAnalyzer.analyze(policy);
		System.gc();

		System.out.println(stat);

		Profiling.print();

	}
}
