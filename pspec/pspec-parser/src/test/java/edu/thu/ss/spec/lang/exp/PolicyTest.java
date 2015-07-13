package edu.thu.ss.spec.lang.exp;

import org.junit.Test;

import edu.thu.ss.spec.lang.analyzer.IPolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.TiminingAnalyzer;
import edu.thu.ss.spec.lang.analyzer.consistency.ConsistencyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.redundancy.LocalRedundancyAnalyzer;
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

public class PolicyTest {

	String outputPath = "tmp/output.xml";

	String vocabPath = "expr/expr-vocab.xml";

	String userContainerId = "expr-user-10";

	String dataContainerId = "expr-data-60";

	int times = 3;

	int[] rules = { 200, 400, 600, 800, 1000 };

	int defaultRule = 800;

	VocabularyParser vocabParser = new VocabularyParser();

	PolicyGenerator generator = new PolicyGenerator();

	public void testRedundancy() throws Exception {

		RuleGenerator ruleGenerator = new RuleGenerator();

		for (int rule : rules) {
			AnalyzerStat stat = new RedundancyStat(times);
			IPolicyAnalyzer analyzer = new LocalRedundancyAnalyzer(new EventTable());
			expr("Redundancy-" + rule, vocabPath, userContainerId, dataContainerId, ruleGenerator, stat,
					analyzer, rule);
		}
	}

	public void testConflict() throws Exception {

		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.forbidRatio = 0;

		for (int rule : rules) {
			AnalyzerStat stat = new ConsistencyStat(times);
			IPolicyAnalyzer analyzer = new ConsistencyAnalyzer(new EventTable());

			expr("Consistency-" + rule, vocabPath, userContainerId, dataContainerId, ruleGenerator, stat,
					analyzer, rule);
		}
	}

	public void testRedundancyUser() throws Exception {

		String[] userIds = { "expr-user-3", "expr-user-5", "expr-user-10", "expr-user-15",
				"expr-user-20" };
		RuleGenerator ruleGenerator = new RuleGenerator();
		for (String userId : userIds) {
			RedundancyStat stat = new RedundancyStat(times);
			expr("Redundancy-" + userId, vocabPath, userId, dataContainerId, ruleGenerator, stat,
					new LocalRedundancyAnalyzer(new EventTable()), defaultRule);
		}
	}

	public void testConsistencyUser() throws Exception {
		String[] userIds = { "expr-user-5", "expr-user-10", "expr-user-15", "expr-user-20" };
		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.forbidRatio = 0;
		for (String userId : userIds) {
			ConsistencyStat stat = new ConsistencyStat(times);
			expr("Consistency-" + userId, vocabPath, userId, dataContainerId, ruleGenerator, stat,
					new ConsistencyAnalyzer(new EventTable()), defaultRule);
		}
	}

	public void testRedundancyData() throws Exception {

		String[] dataIds = { "expr-data-20", "expr-data-40", "expr-data-60", "expr-data-80",
				"expr-data-100" };

		RuleGenerator ruleGenerator = new RuleGenerator();

		for (String dataId : dataIds) {
			RedundancyStat stat = new RedundancyStat(times);
			expr("Redundancy-" + dataId, vocabPath, userContainerId, dataId, ruleGenerator, stat,
					new LocalRedundancyAnalyzer(new EventTable()), defaultRule);

		}

	}

	public void testConsistencyData() throws Exception {

		String[] dataIds = { "expr-data-40", "expr-data-60", "expr-data-80", "expr-data-100" };

		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.forbidRatio = 0;
		for (String dataId : dataIds) {
			ConsistencyStat stat = new ConsistencyStat(times);
			expr("Consistency-" + dataId, vocabPath, userContainerId, dataId, ruleGenerator, stat,
					new ConsistencyAnalyzer(new EventTable()), defaultRule);

		}
	}

	public void testRedundancyOp() throws Exception {

		String[] dataIds = { "expr-data-op-5", "expr-data-op-10", "expr-data-op-15", "expr-data-op-20",
				"expr-data-op-25" };

		RuleGenerator ruleGenerator = new RuleGenerator();

		for (String dataId : dataIds) {
			RedundancyStat stat = new RedundancyStat(times);
			expr("Redundancy-" + dataId, vocabPath, userContainerId, dataId, ruleGenerator, stat,
					new LocalRedundancyAnalyzer(new EventTable()), defaultRule);

		}

	}

	public void testConsistencyOp() throws Exception {

		String[] dataIds = { "expr-data-op-5", "expr-data-op-10", "expr-data-op-15", "expr-data-op-20",
				"expr-data-op-25" };

		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.forbidRatio = 0;
		for (String dataId : dataIds) {
			ConsistencyStat stat = new ConsistencyStat(times);
			expr("Consistency-" + dataId, vocabPath, userContainerId, dataId, ruleGenerator, stat,
					new ConsistencyAnalyzer(new EventTable()), defaultRule);

		}
	}

	public void testRedundancyDim() throws Exception {
		int[] dims = { 1, 3, 5, 7, 9 };
		RuleGenerator ruleGenerator = new RuleGenerator();
		for (int dim : dims) {
			RedundancyStat stat = new RedundancyStat(times);
			ruleGenerator.maxDim = dim;
			expr("Redundancy-" + String.valueOf(dim), vocabPath, userContainerId, dataContainerId,
					ruleGenerator, stat, new LocalRedundancyAnalyzer(new EventTable()), defaultRule);
		}
	}

	public void testConsistencyDim() throws Exception {
		int[] dims = { 1, 3, 5, 7, 9 };
		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.forbidRatio = 0;
		for (int dim : dims) {
			ConsistencyStat stat = new ConsistencyStat(times);
			ruleGenerator.maxDim = dim;
			expr("Consistency-" + String.valueOf(dim), vocabPath, userContainerId, dataContainerId,
					ruleGenerator, stat, new ConsistencyAnalyzer(new EventTable()), defaultRule);
		}
	}

	public void testRedundancyRes() throws Exception {
		int[] ress = { 1, 3, 5, 7, 9 };
		RuleGenerator ruleGenerator = new RuleGenerator();
		for (int res : ress) {
			RedundancyStat stat = new RedundancyStat(times);
			ruleGenerator.maxRes = res;
			expr("Redundancy-" + String.valueOf(res), vocabPath, userContainerId, dataContainerId,
					ruleGenerator, stat, new LocalRedundancyAnalyzer(new EventTable()), defaultRule);
		}
	}

	public void testConsistencyRes() throws Exception {
		int[] ress = { 1, 3, 5, 7, 9 };
		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.forbidRatio = 0;
		for (int res : ress) {
			ConsistencyStat stat = new ConsistencyStat(times);
			ruleGenerator.maxRes = res;
			expr("Consistency-" + String.valueOf(res), vocabPath, userContainerId, dataContainerId,
					ruleGenerator, stat, new ConsistencyAnalyzer(new EventTable()), defaultRule);
		}
	}

	@Test
	public void testRedundancyForbid() throws Exception {
		double[] forbids = { 0, 0.2, 0.4, 0.8, 1 };
		RuleGenerator ruleGenerator = new RuleGenerator();
		for (double f : forbids) {
			RedundancyStat stat = new RedundancyStat(times);
			ruleGenerator.forbidRatio = f;
			expr("Redundancy-" + String.valueOf(f), vocabPath, userContainerId, dataContainerId,
					ruleGenerator, stat, new LocalRedundancyAnalyzer(new EventTable()), defaultRule);
		}
	}

	private void expr(String expId, String vocabPath, String userId, String dataId,
			RuleGenerator ruleGenerator, AnalyzerStat stat, IPolicyAnalyzer analyzer, int rule)
			throws Exception {
		VocabularyManager.clear();
		Vocabulary vocab = vocabParser.parse(vocabPath);
		ruleGenerator.setUsers(vocab.getUserContainer());
		ruleGenerator.setDatas(vocab.getDataContainer());
		for (int i = 0; i < times; i++) {
			generator.generate(ruleGenerator, rule, outputPath, vocabPath, userId, dataId);
			LineCounter.count(outputPath, stat, i);
			PolicyManager.clear();
			PolicyParser parser = new PolicyParser();
			Policy policy = parser.parse(outputPath);
			IPolicyAnalyzer timingAnalyzer = new TiminingAnalyzer(analyzer, stat, i);
			timingAnalyzer.analyze(policy);

			System.gc();
		}

		System.out.println(ruleGenerator);
		System.out.println(stat);
		System.out.println(expId + " experiment finishes for #" + rule);
		System.out.println();

	}
}
