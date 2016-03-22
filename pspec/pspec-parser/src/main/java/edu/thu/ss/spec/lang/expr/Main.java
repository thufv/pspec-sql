package edu.thu.ss.spec.lang.expr;

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
import edu.thu.ss.spec.util.Profiling;

public class Main {
	public static void main(String[] args) throws Exception {
		String option = args[0];

		switch (option) {
		case "policy":
			generatePolicy(args);
			break;
		case "redundancy":
			analyze(new RedundancyAnalyzer(EventTable.getDummy()), new RedundancyStat(), args);
			break;

		case "a-redundancy":
			analyze(new ApproximateRedundancyAnalyzer(EventTable.getDummy()), new RedundancyStat(), args);
			break;

		case "consistency":
			analyze(new ConsistencyAnalyzer(EventTable.getDummy()), new ConsistencyStat(), args);
			break;
		default:
			System.err.println("Invalid argument: " + args[0]);
			break;
		}

	}

	private static <T extends AnalyzerStat> void analyze(IPolicyAnalyzer<T> analyzer, T stat,
			String[] args) throws Exception {
		System.out.println("Begin analyze policy.");
		String policyPath = args[1];

		PolicyParser parser = new PolicyParser();
		Policy policy = parser.parse(policyPath);

		IPolicyAnalyzer<T> timingAnalyzer = new TiminingAnalyzer<T>(analyzer, stat);
		timingAnalyzer.analyze(policy);

		System.out.println(stat);
		Profiling.print();
	}

	private static void generatePolicy(String[] args) throws Exception {
		String vocabPath = args[1];
		String outputPath = args[2];

		int rules = Integer.valueOf(args[3]);
		int maxDimension = Integer.valueOf(args[4]);
		int maxRestriction = Integer.valueOf(args[5]);

		double desensitizeRatio = 0.5;
		if (args.length > 7) {
			desensitizeRatio = Double.valueOf(args[6]);
		}
		double operationRatio = 0.5;
		if (args.length > 8) {
			desensitizeRatio = Double.valueOf(args[7]);
		}

		VocabularyParser parser = new VocabularyParser();
		Vocabulary vocab = parser.parse(vocabPath);
		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.setUsers(vocab.getUserContainer());
		ruleGenerator.setDatas(vocab.getDataContainer());
		ruleGenerator.maxDim = maxDimension;
		ruleGenerator.maxRes = maxRestriction;
		ruleGenerator.desensitizeRatio = desensitizeRatio;
		ruleGenerator.desensitzeOperationRatio = operationRatio;

		PolicyGenerator policyGenerator = new PolicyGenerator();
		policyGenerator.generate(ruleGenerator, rules, outputPath, vocabPath);

	}

}
