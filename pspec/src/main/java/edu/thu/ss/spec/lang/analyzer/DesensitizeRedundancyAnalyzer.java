package edu.thu.ss.spec.lang.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.analyzer.stat.RedundancyStat;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.DesensitizeZ3Util;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.SetUtil.SetRelation;

public class DesensitizeRedundancyAnalyzer extends BaseRedundancyAnalyzer {

	protected static class SimplificationLog {
		public ExpandedRule rule;
		public List<UserRef> userRefs;
		public DataRef dataRef;

		public SimplificationLog(ExpandedRule rule, List<UserRef> userRefs,
				DataRef dataRef) {
			this.rule = rule;
			this.userRefs = userRefs;
			this.dataRef = dataRef;
		}
	}

	protected static DesensitizeZ3Util z3Util = new DesensitizeZ3Util();
	
	protected List<SimplificationLog> logs = new LinkedList<>();

	protected final int[][] dataIncludes = new int[Max_Dimension][Max_Dimension];

	protected final int[] dataLength = new int[Max_Dimension];

	protected final boolean[] covered = new boolean[Max_Dimension];

	protected boolean simplify = false;

	protected AnalyzerStat stat = null;
	protected int n;

	public DesensitizeRedundancyAnalyzer() {
		this.instance = InclusionUtil.instance;
		super.logger = LoggerFactory
				.getLogger(DesensitizeRedundancyAnalyzer.class);
	}
	
	@Override
	public boolean analyze(Policy policy, AnalyzerStat stat, int n) {
		this.stat = stat;
		this.n = n;
		return analyze(policy);
	}

	public boolean analyze(Policy policy) {
		return super.analyze(policy);
	}

	@Override
	protected List<ExpandedRule> getCheckRules(Policy policy) {
		List<ExpandedRule> list = policy.getExpandedRules();
		List<ExpandedRule> res = new ArrayList<>();
		for (ExpandedRule rule : list) {
			if (!rule.getRestriction().isFilter()) {
				res.add(rule);
			}
		}
		return res;
	}
	
	/**
	 * check whether target is redundant w.r.t rule
	 * 
	 * @param target
	 * @param rule
	 * @return redundant
	 */
	@Override
	protected boolean checkRedundancy(ExpandedRule target, ExpandedRule rule) {
		if (!checkScope(rule, target)) {
			return false;
		}
		
		if (target.isSingle()) {
			return checkSingle(rule, target);
		} else {
			return checkAssociation(rule, target);
		}
	}

	/**
	 * both rule1 and rule2 are single. Check rule1 implies rule2, i.e., whether
	 * rule2 is redundant
	 * 
	 * @param rule1
	 * @param rule2
	 * @return rule1 implies rule2
	 */
	private boolean checkSingle(ExpandedRule rule1, ExpandedRule rule2) {
		if (instance.isGlobal(rule2.getDataRef())
				&& !instance.isGlobal(rule1.getDataRef())) {
			// global rule cannot be covered by local rules.
			return false;
		}
		// pre-test, to filter out result early.
		DataRef ref1 = rule1.getDataRef();
		DataRef ref2 = rule2.getDataRef();

		Action action1 = ref1.getAction();
		Action action2 = ref2.getAction();
		if (!instance.includes(action1, action2)) {
			return false;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		SetRelation userRelation = SetUtil.relation(user1, user2);
		if (userRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		Set<DataCategory> data1 = ref1.getMaterialized();
		Set<DataCategory> data2 = ref2.getMaterialized();
		SetRelation dataRelation = SetUtil.relation(data1, data2);
		if (dataRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		// only 1 restriction each
		Restriction[] res1 = rule1.getRestrictions();
		Restriction[] res2 = rule2.getRestrictions();
		if (!instance.singleStricterThan(res1[0], res2[0])) {
			return false;
		}

		if (userRelation.equals(SetRelation.contain)
				&& dataRelation.equals(SetRelation.contain)) {
			return true;
		} else if (simplify) {
			if (userRelation.equals(SetRelation.contain)
					&& dataRelation.equals(SetRelation.intersect)) {
				logs.add(new SimplificationLog(rule2, null, ref1));
			} else if (userRelation.equals(SetRelation.intersect)
					&& dataRelation.equals(SetRelation.contain)) {
				logs.add(new SimplificationLog(rule2, rule1.getUserRefs(), null));
			}
		}
		return false;
	}

	/**
	 * rule2 is association, dispatch to
	 * {@link #checkSingleAssociation(ExpandedRule, ExpandedRule)} and
	 * {@link #checkBothAssociation(ExpandedRule, ExpandedRule)} only perform
	 * remove operation, not perform simplification operation.
	 * 
	 * @param rule1
	 * @param rule2
	 * @return rule1 implies rule2
	 */
	private boolean checkAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isSingle()) {
			return checkSingleAssociation(rule1, rule2);
		} else {
			return checkBothAssociation(rule1, rule2);
		}
	}

	/**
	 * rule1 is single, rule2 is association. Check rule1 implies rule2
	 * 
	 * @param rule1
	 * @param rule2
	 * @return rule1 implies rule2
	 */
	private boolean checkSingleAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		DataRef ref1 = rule1.getDataRef();
		DataAssociation assoc2 = rule2.getAssociation();

		boolean match = false;
		int index = 0;
		List<DataRef> dataRefs = assoc2.getDataRefs();
		Arrays.fill(covered, false);
		for (int i = 0; i < dataRefs.size(); i++) {
			DataRef ref2 = dataRefs.get(i);
			if (instance.includes(ref1, ref2)) {
				match = true;
				dataIncludes[0][index++] = i;
				covered[i] = true;
			}
		}
		dataLength[0] = index;
		if (!match) {
			return false;
		}

		return z3Util.implies(rule1, rule2, dataIncludes, dataLength, covered);

	}

	/**
	 * both rule1 and rule2 are association. Check rule1 implies rule2
	 * 
	 * @param rule1
	 * @param rule2
	 * @return
	 */
	private boolean checkBothAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		DataAssociation assoc1 = rule1.getAssociation();
		DataAssociation assoc2 = rule2.getAssociation();

		List<DataRef> dataRefs1 = assoc1.getDataRefs();
		List<DataRef> dataRefs2 = assoc2.getDataRefs();

		Arrays.fill(covered, false);
		for (int i = 0; i < dataRefs1.size(); i++) {
			boolean match = false;
			int index = 0;
			DataRef ref1 = dataRefs1.get(i);
			for (int j = 0; j < dataRefs2.size(); j++) {
				DataRef ref2 = dataRefs2.get(j);
				if (instance.includes(ref1, ref2)) {
					match = true;
					dataIncludes[i][index++] = j;
					covered[j] = true;
				}
			}
			if (!match) {
				return false;
			}
			dataLength[i] = index;
		}

		return z3Util.implies(rule1, rule2, dataIncludes, dataLength, covered);

	}

	@Override
	protected void commit(int count) {
		if (stat != null) {
			RedundancyStat rStat = (RedundancyStat) stat;
			rStat.rules[n] = count;
		}
		
		for (SimplificationLog log : logs) {
			ExpandedRule rule = log.rule;
			if (log.userRefs != null) {
				for (UserRef ref : log.userRefs) {
					rule.getUsers().removeAll(ref.getMaterialized());
				}
			} else if (log.dataRef != null) {
				rule.getDataRef().getMaterialized().removeAll(log.dataRef.getMaterialized());
			}
		}
	}
}
