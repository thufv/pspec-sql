package edu.thu.ss.spec.lang.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
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
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.Z3Util;
import edu.thu.ss.spec.util.PSpecUtil.SetRelation;

/**
 * performs policy redundancy analysis, for all rule r1 and r2, if r1 covers r2,
 * then r2 can be removed.
 * 
 * @author luochen
 * 
 */
public abstract class BaseRedundancyAnalyzer extends BasePolicyAnalyzer {

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

	protected static Logger logger = LoggerFactory
			.getLogger(BaseRedundancyAnalyzer.class);

	protected List<SimplificationLog> logs = new LinkedList<>();

	public static final int Max_Dimension = 10;

	protected final int[][] dataIncludes = new int[Max_Dimension][Max_Dimension];

	protected final int[] dataLength = new int[Max_Dimension];

	protected final boolean[] covered = new boolean[Max_Dimension];

	protected boolean simplify = false;

	/**
	 * initialized by sub classes
	 */
	protected InclusionUtil instance = null;

	protected AnalyzerStat stat = null;
	protected int n;

	@Override
	public boolean analyze(Policy policy, AnalyzerStat stat, int n) {
		this.stat = stat;
		this.n = n;
		return analyze(policy);
	}

	public boolean analyze(Policy policy) {
		int count = 0;
		List<ExpandedRule> rules = policy.getExpandedRules();

		boolean[] removable = new boolean[rules.size()];
		for (int i = 0; i < rules.size(); i++) {
			if (removable[i]) {
				continue;
			}
			ExpandedRule prule = rules.get(i);
			List<Integer> list = new ArrayList<>();
			for (int j = i + 1; j < rules.size(); j++) {
				if (removable[j]) {
					continue;
				}
				ExpandedRule trule = rules.get(j);
				if (prule == trule) {
					continue;
				}
				if (checkRedundancy(prule, trule)) {
					list.add(i);
					prule = trule;
				} else if (checkRedundancy(trule, prule)) {
					list.add(j);
				}
			}
			if (list.size() > 0) {
				for (int index : list) {
					removable[index] = true;
					ExpandedRule rule = rules.get(index);
					logger
							.warn(
									"The rule: {} is redundant since it is covered by rule: {}, consider revise your policy.",
									rule.getRuleId(), prule.getRuleId());
				}
				count += list.size();
			}
		}
		Iterator<ExpandedRule> it = rules.iterator();
		int index = 0;
		while (it.hasNext()) {
			it.next();
			if (removable[index]) {
				it.remove();
			}
			index++;
		}

		logger.error("{} redundant rules detected.", count);
		if (stat != null) {
			RedundancyStat rStat = (RedundancyStat) stat;
			rStat.rules[n] = count;
		}

		commit();
		return false;
	}

	/**
	 * check whether target is redundant w.r.t rule
	 * 
	 * @param target
	 * @param rule
	 * @return redundant
	 */
	private boolean checkRedundancy(ExpandedRule target, ExpandedRule rule) {
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
		if (rule1.isAssociation()) {
			return false;
		}
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
		SetRelation userRelation = PSpecUtil.relation(user1, user2);
		if (userRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		Set<DataCategory> data1 = ref1.getMaterialized();
		Set<DataCategory> data2 = ref2.getMaterialized();
		SetRelation dataRelation = PSpecUtil.relation(data1, data2);
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
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();

		if (!PSpecUtil.contains(user1, user2)) {
			return false;
		}

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

		return Z3Util.implies(rule1, rule2, dataIncludes, dataLength, covered);

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

		if (assoc1.getDimension() > assoc2.getDimension()) {
			return false;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		if (!PSpecUtil.contains(user1, user2)) {
			return false;
		}

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

		return Z3Util.implies(rule1, rule2, dataIncludes, dataLength, covered);

	}

	protected abstract void commit();

}
