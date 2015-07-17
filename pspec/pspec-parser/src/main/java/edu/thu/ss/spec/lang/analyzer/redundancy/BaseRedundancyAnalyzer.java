package edu.thu.ss.spec.lang.analyzer.redundancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.analyzer.stat.RedundancyStat;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.AnalysisType;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.PSpecUtil.SetRelation;
import edu.thu.ss.spec.util.Z3Util;

/**
 * performs policy redundancy analysis, find all pairs r1, r2 such that r2 covers r1 (r1 is redundant) and r2 is not redundant
 * 
 * @author luochen
 * 
 */
public abstract class BaseRedundancyAnalyzer extends BasePolicyAnalyzer {

	protected static Logger logger = LoggerFactory.getLogger(BaseRedundancyAnalyzer.class);

	public static final int Max_Dimension = 10;

	protected final int[][] dataIncludes = new int[Max_Dimension][Max_Dimension];

	protected final int[] dataLength = new int[Max_Dimension];

	protected final boolean[] covered = new boolean[Max_Dimension];

	protected boolean remove = false;

	protected Set<ExpandedRule> removable = new HashSet<>();

	public BaseRedundancyAnalyzer(EventTable table) {
		super(table);
	}

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

		for (int i = 0; i < rules.size(); i++) {
			ExpandedRule prule = rules.get(i);
			if (removable.contains(prule)) {
				continue;
			}
			List<ExpandedRule> list = new ArrayList<>();
			for (int j = i + 1; j < rules.size(); j++) {
				ExpandedRule trule = rules.get(j);
				if (removable.contains(trule)) {
					continue;
				}
				if (prule == trule) {
					continue;
				}
				if (checkRedundancy(prule, trule)) {
					list.add(prule);
					prule = trule;
				} else if (checkRedundancy(trule, prule)) {
					list.add(trule);
				}
			}
			if (list.size() > 0) {
				for (ExpandedRule rule : list) {
					removable.add(rule);
					logger
							.warn(
									"The rule: {} is redundant since it is covered by rule: {}, consider revise your policy.",
									rule.getRuleId(), prule.getRuleId());
					table.onAnalysis(AnalysisType.Redundancy, rule, prule);
				}
				count += list.size();
			}
		}
		if (remove) {
			Iterator<ExpandedRule> it = rules.iterator();
			while (it.hasNext()) {
				ExpandedRule rule = it.next();
				if (removable.contains(rule)) {
					it.remove();
				}
			}
		}

		logger.error("{} redundant rules detected.", count);
		if (stat != null) {
			RedundancyStat rStat = (RedundancyStat) stat;
			rStat.rules[n] = count;
		}

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
		if (target.isFilter() && rule.isFilter()) {
			return checkFilter(rule, target);
		} else if (target.isFilter() || rule.isFilter()) {
			return false;
		}

		if (target.isSingle()) {
			return checkSingle(rule, target);
		} else {
			return checkAssociation(rule, target);
		}
	}

	/**
	 * both rule1 and rule2 are filter. Check rule1 implies rule2, i.e., whether
	 * rule2 is redundant
	 * @param rule1
	 * @param rule2
	 * @return redundant
	 */
	private boolean checkFilter(ExpandedRule rule1, ExpandedRule rule2) {
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		if (!PSpecUtil.contains(user1, user2)) {
			return false;
		}

		Set<DataRef> set1 = new HashSet<>();
		Set<DataRef> set2 = new HashSet<>();
		if (rule1.isSingle()) {
			set1.add(rule1.getDataRef());
		} else {
			set1.addAll(rule1.getAssociation().getDataRefs());
		}
		if (rule1.isSingle()) {
			set2.add(rule2.getDataRef());
		} else {
			set2.addAll(rule2.getAssociation().getDataRefs());
		}
		if (!PSpecUtil.contains(set1, set2)) {
			return false;
		}

		Set<DataCategory> categories1 = rule1.getCondition().getDataCategories();
		Set<DataCategory> categories2 = rule2.getCondition().getDataCategories();
		if (!PSpecUtil.contains(categories1, categories2)) {
			return false;
		}

		boolean res = false;
		res = Z3Util.implies(rule1.getCondition(), rule2.getCondition());

		return res;
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
		if (instance.isGlobal(rule2.getDataRef()) && !instance.isGlobal(rule1.getDataRef())) {
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

		if (userRelation.equals(SetRelation.contain) && dataRelation.equals(SetRelation.contain)) {
			return true;
		} else {
			return false;
		}
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

}
