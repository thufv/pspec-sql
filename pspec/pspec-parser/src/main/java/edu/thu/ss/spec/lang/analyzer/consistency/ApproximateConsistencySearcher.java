package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.analyzer.consistency.ApproximateConsistencyAnalyzer.RuleRelation;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.PSpecUtil.SetRelation;

public class ApproximateConsistencySearcher extends LevelwiseSearcher {
	private static Logger logger = LoggerFactory.getLogger(ApproximateConsistencySearcher.class);

	/**
	 * intermediate representation of restriction
	 * datas, action => list< operations >
	 * @author luochen
	 *
	 */
	protected class Triple {
		Set<DataCategory> datas;
		Action action;

		/**
		 * list == null means the data is forbid.
		 */
		List<Set<DesensitizeOperation>> list; //

		public Triple(DataRef ref, List<Set<DesensitizeOperation>> list) {
			this.action = ref.getAction();
			this.datas = new HashSet<>(ref.getMaterialized());
			this.list = list;
		}

		public Triple(Action action, Set<DataCategory> datas, List<Set<DesensitizeOperation>> list) {
			this.action = action;
			this.datas = datas;
			this.list = list;
		}
	}

	/**
	 * intermediate representation of rule
	 * @author luochen
	 *
	 */
	protected class RuleObject {
		Set<UserCategory> users;
		Triple[] triples;

		public List<Set<DesensitizeOperation>> getList(Set<DataCategory> datas) {
			for (Triple t : triples) {
				if (PSpecUtil.containOrDisjoint(t.datas, datas).equals(SetRelation.contain)) {
					return t.list;
				}
			}
			return null;
		}
	}

	protected List<ExpandedRule> rules;
	protected RuleObject[] ruleObjects;
	protected List<ExpandedRule> sortedRules;
	protected int[] index;

	public ApproximateConsistencySearcher(List<ExpandedRule> rules) {
		this.rules = rules;
	}

	@Override
	protected void initLevel(Set<SearchKey> currentLevel) {
		sortedRules = new ArrayList<>(rules);
		Collections.sort(sortedRules, new Comparator<ExpandedRule>() {
			@Override
			public int compare(ExpandedRule o1, ExpandedRule o2) {
				return Integer.compare(o1.getDimension(), o2.getDimension());
			}
		});

		index = new int[sortedRules.size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = sortedRules.indexOf(sortedRules.get(i));
		}

		ruleObjects = new RuleObject[sortedRules.size()];
		for (int i = 0; i < ruleObjects.length; i++) {
			ruleObjects[i] = ruleToObject(sortedRules.get(i));
		}

		for (int i = 0; i < sortedRules.size(); i++) {
			currentLevel.add(new SearchKey(i));
		}
	}

	@Override
	protected boolean process(SearchKey key) {
		Set<UserCategory> users = null;
		for (int i = 0; i < key.index.length; i++) {
			RuleObject rule = ruleObjects[key.index[i]];
			if (users == null) {
				users = new HashSet<>(rule.users);
			} else {
				users.retainAll(rule.users);
				if (users.size() == 0) {
					return false;
				}
			}
		}

		RuleRelation relation = processDatas(key, 0, null, null);
		return RuleRelation.consistent.equals(relation);

	}

	private RuleRelation processDatas(SearchKey key, int i, Action joinAction,
			Set<DataCategory> joinDatas) {
		if (i == key.index.length) {
			RuleRelation relation = checkRestrictions(key, joinAction, joinDatas);
			if (relation.equals(RuleRelation.conflict)) {
				logger
						.warn(
								"Desensitize operation conflicts detected between expanded sortedRules: #{} for data categories: {}.",
								PSpecUtil.toString(key.index, sortedRules), PSpecUtil.format(joinDatas, ","));
			}
			return relation;
		}
		RuleObject obj = ruleObjects[key.index[i]];
		Triple[] triples = obj.triples;
		boolean consistent = false;
		for (Triple triple : triples) {
			Action action = null;
			Set<DataCategory> datas = null;
			if (i == 0) {
				action = triple.action;
				datas = new HashSet<>(triple.datas);
			} else {
				action = PSpecUtil.bottom(triple.action, joinAction);
				if (action == null) {
					continue;
				}
				datas = PSpecUtil.intersect(triple.datas, joinDatas);
				if (datas.size() == 0) {
					continue;
				}
			}
			RuleRelation relation = processDatas(key, i + 1, action, datas);
			if (RuleRelation.conflict.equals(relation)) {
				return relation;
			}
			if (RuleRelation.consistent.equals(relation)) {
				consistent = true;
			}
		}
		if (consistent) {
			return RuleRelation.consistent;
		} else {
			return RuleRelation.disjoint;
		}
	}

	protected List<Set<DesensitizeOperation>> collectOperations(ExpandedRule rule,
			Set<DataCategory> datas) {
		Restriction[] restrictions = rule.getRestrictions();
		List<Set<DesensitizeOperation>> list = null;
		for (Restriction res : restrictions) {
			boolean match = false;
			Set<DesensitizeOperation> ops = null;
			for (Desensitization de : res.getDesensitizations()) {
				if (de.effective()) {
					Set<DataCategory> set = de.getDatas();
					if (PSpecUtil.containOrDisjoint(set, datas).equals(SetRelation.contain)) {
						ops = de.getOperations();
						match = true;
						break;
					}
				}
			}
			if (!match) {
				continue;
			}
			if (list == null) {
				list = new ArrayList<>();
			}
			PSpecUtil.mergeOperations(list, ops);
		}
		return list;
	}

	private RuleRelation checkRestrictions(SearchKey key, Action action, Set<DataCategory> datas) {
		List<Set<DesensitizeOperation>> joins = null;
		for (int i = 0; i < key.index.length; i++) {
			RuleObject rule = ruleObjects[key.index[i]];
			List<Set<DesensitizeOperation>> list = rule.getList(datas);
			if (list == null) {
				logger
						.warn(
								"Possible conflicts between expanded sortedRules: #{}, since rule :#{} forbids the data access.",
								PSpecUtil.toString(key.index, sortedRules), sortedRules.get(key.index[i])
										.getRuleId());
				return RuleRelation.forbid;
			}
			if (joins == null) {
				joins = new ArrayList<>();
				for (Set<DesensitizeOperation> ops : list) {
					if (ops == null) {
						joins.add(null);
					} else {
						joins.add(new HashSet<>(ops));
					}
				}
			} else {
				List<Set<DesensitizeOperation>> tmp = new LinkedList<>();
				for (Set<DesensitizeOperation> ops1 : joins) {
					for (Set<DesensitizeOperation> ops2 : list) {
						if (ops1 == null) {
							PSpecUtil.mergeOperations(tmp, ops2);
						} else if (ops2 == null) {
							PSpecUtil.mergeOperations(tmp, ops1);
						} else {
							Set<DesensitizeOperation> ops = PSpecUtil.intersect(ops1, ops2);
							if (ops.size() == 0) {
								return RuleRelation.conflict;
							}
							PSpecUtil.mergeOperations(tmp, ops);
						}
					}
				}
				joins = tmp;
			}
		}
		return RuleRelation.consistent;
	}

	private RuleObject ruleToObject(ExpandedRule rule) {
		RuleObject obj = new RuleObject();
		obj.users = new HashSet<>(rule.getUsers());
		if (rule.getRestriction().isForbid()) {
			if (rule.isSingle()) {
				obj.triples = new Triple[1];
				obj.triples[0] = new Triple(rule.getDataRef(), null);
			} else {
				DataAssociation assoc = rule.getAssociation();
				obj.triples = new Triple[assoc.getDimension()];
				for (int i = 0; i < obj.triples.length; i++) {
					obj.triples[i] = new Triple(assoc.get(i), null);
				}
			}
		} else {
			List<Triple> triples = new ArrayList<>();
			if (rule.isSingle()) {
				DataRef ref = rule.getDataRef();
				List<Set<DesensitizeOperation>> list = collectOperations(rule, rule.getDataRef()
						.getMaterialized());
				if (list != null) {
					Triple triple = new Triple(ref, list);
					triples.add(triple);
				}
			} else {
				DataAssociation assoc = rule.getAssociation();
				for (DataRef ref : assoc.getDataRefs()) {
					List<Set<DesensitizeOperation>> list = collectOperations(rule, ref.getMaterialized());
					if (list != null) {
						Triple triple = new Triple(ref, list);
						triples.add(triple);
					}
				}
			}
			for (Iterator<Triple> it = triples.iterator(); it.hasNext();) {
				Triple t = it.next();
				if (t.list != null && t.list.size() == 0) {
					it.remove();
				}
			}
			obj.triples = triples.toArray(new Triple[triples.size()]);
		}
		return obj;
	}
}
