package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.stat.ConsistencyStat;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.Conf;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.PSpecUtil.SetRelation;

public class ConsistencyAnalyzer extends BasePolicyAnalyzer<ConsistencyStat> {

	private static Logger logger = LoggerFactory.getLogger(ConsistencyAnalyzer.class);

	private boolean[] occupied = new boolean[Conf.Max_Dimension];

	private int[][] matched = new int[Conf.Max_Dimension][Conf.Max_Dimension];

	private static final int Unknown = 0;
	private static final int Match = 1;
	private static final int Unmatch = 2;

	public ConsistencyAnalyzer(EventTable table) {
		super(table);
	}

	@Override
	public boolean analyze(Policy policy) throws Exception {
		List<Rule> rules = policy.getRules();
		int conflicts = 0;
		for (Rule seed : rules) {
			if (!seed.getRestrictions().isEmpty()) {
				//non-forbidden rule
				List<Rule> candidates = getCandidateRules(seed, rules);
				logger.error("Find {} candidates for seed: {}.", candidates.size(), seed.getId());

				ConsistencySearcher searcher = new ConsistencySearcher(seed, candidates);
				searcher.search();
				conflicts += searcher.conflicts;
				if (stat != null) {
					stat.count++;
					stat.levels += searcher.level;
					stat.candidates += candidates.size();
				}
				if (searcher.conflicts != 0) {
					logger.error("Find {} conflicts for seed: {}.", searcher.conflicts, seed.getId());
				}
			}
		}
		logger.error("Find {} conflicts.", conflicts);
		if (stat != null) {
			stat.conflicts = conflicts;
		}
		return false;
	}

	private List<Rule> getCandidateRules(Rule seed, List<Rule> rules) {
		List<Rule> candidates = new ArrayList<>();
		for (Rule rule : rules) {
			if (rule == seed) {
				continue;
			}
			if (isCandidate(seed, rule)) {
				candidates.add(rule);
			}
		}
		return candidates;
	}

	private boolean isCandidate(Rule seed, Rule rule) {
		if (seed.getDimension() < rule.getDimension()) {
			return false;
		}

		Set<UserCategory> seedUsers = seed.getUserRef().getMaterialized();
		Set<UserCategory> ruleUsers = rule.getUserRef().getMaterialized();
		if (PSpecUtil.relation(seedUsers, ruleUsers).equals(SetRelation.disjoint)) {
			return false;
		}
		List<DataRef> seedRefs = seed.getDataAssociation().getDataRefs();
		List<DataRef> ruleRefs = rule.getDataAssociation().getDataRefs();

		int ruleLength = ruleRefs.size();
		int seedLength = seedRefs.size();
		for (int i = 0; i < ruleLength; i++) {
			for (int j = 0; j < seedLength; j++) {
				matched[i][j] = Unknown;
			}
			occupied[i] = false;
		}

		return checkCandidateData(0, ruleRefs, seedRefs);

	}

	private boolean checkCandidateData(int index, List<DataRef> ruleRefs, List<DataRef> seedRefs) {
		if (index == ruleRefs.size()) {
			return true;
		}
		DataRef ruleRef = ruleRefs.get(index);

		int seedLength = seedRefs.size();
		for (int i = 0; i < seedLength; i++) {
			if (occupied[i]) {
				continue;
			}
			if (matched[index][i] == Unknown) {
				DataRef seedRef = seedRefs.get(i);
				if (ruleRef.getAction().ancestorOf(seedRef.getAction())
						|| seedRef.getAction().ancestorOf(ruleRef.getAction())) {
					if (!PSpecUtil.relation(seedRef.getMaterialized(), ruleRef.getMaterialized())
							.equals(SetRelation.disjoint)) {
						matched[index][i] = Match;
					} else {
						matched[index][i] = Unmatch;
					}
				} else {
					matched[index][i] = Unmatch;
				}
			}
			if (matched[index][i] == Match) {
				occupied[i] = true;
				if (checkCandidateData(index + 1, ruleRefs, seedRefs)) {
					return true;
				} else {
					occupied[i] = false;
				}
			}
		}
		return false;
	}

}
