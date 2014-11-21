package edu.thu.ss.spec.lang.analyzer.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.analyzer.LevelwiseSearcher;
import edu.thu.ss.spec.lang.analyzer.local.ConsistencyAnalyzer.RuleRelation;
import edu.thu.ss.spec.lang.analyzer.local.LocalRule.DataActionPair;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.SetUtil.SetRelation;

class ConsistencySearcher extends LevelwiseSearcher {
	private static Logger logger = LoggerFactory.getLogger(ConsistencySearcher.class);

	protected class Triple {
		Set<DataCategory> datas;
		Action action;

		/**
		 * list == null means the data is forbid.
		 */
		List<Set<DesensitizeOperation>> list; //

		public Triple(DataActionPair pair, List<Set<DesensitizeOperation>> list) {
			this.action = pair.getAction();
			this.datas = pair.getDatas();
			this.list = list;
		}

		public Triple(Action action, Set<DataCategory> datas, List<Set<DesensitizeOperation>> list) {
			this.action = action;
			this.datas = datas;
			this.list = list;
		}
	}

	protected class RuleObject {
		Set<UserCategory> users;
		Triple[] triples;

		public List<Set<DesensitizeOperation>> getList(Set<DataCategory> datas) {
			for (Triple t : triples) {
				if (SetUtil.containOrDisjoint(t.datas, datas).equals(SetRelation.contain)) {
					return t.list;
				}
			}
			return null;
		}
	}

	protected Policy policy;
	protected List<LocalRule> rules;
	protected RuleObject[] ruleObjects;
	protected List<LocalRule> sortedRules;
	protected int[] index;

	public ConsistencySearcher(Policy policy) {
		this.policy = policy;
		this.rules = policy.getLocalRules();
	}

	@Override
	protected void initLevel(Set<SearchKey> currentLevel) {
		sortedRules = new ArrayList<>(rules);
		Collections.sort(sortedRules, new Comparator<LocalRule>() {
			@Override
			public int compare(LocalRule o1, LocalRule o2) {
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
		for (int i = 0; i < key.rules.length; i++) {
			RuleObject rule = ruleObjects[key.rules[i]];
			/*if() {
				logger.warn(
						"Possible conflicts between expanded sortedRules: #{}, since rule :#{} forbids the data access.",
						SetUtil.toString(key.rules, sortedRules), rule.getRuleId());
				return false;
			}*/
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

	private RuleRelation processDatas(SearchKey key, int i, Action joinAction, Set<DataCategory> joinDatas) {
		if (i == key.rules.length) {
			RuleRelation relation = checkRestrictions(key, joinAction, joinDatas);
			if (relation.equals(RuleRelation.conflict)) {
				logger.error(
						"Desensitize operation conflicts detected between expanded sortedRules: #{} for data categories: {}.",
						SetUtil.toString(key.rules, sortedRules), SetUtil.toString(joinDatas));
			}
			return relation;
		}
		RuleObject obj = ruleObjects[key.rules[i]];
		Triple[] triples = obj.triples;
		boolean consistent = false;
		for (Triple triple : triples) {
			Action action = null;
			Set<DataCategory> datas = null;
			if (i == 0) {
				action = triple.action;
				datas = new HashSet<>(triple.datas);
			} else {
				action = SetUtil.bottom(triple.action, joinAction);
				if (action == null) {
					continue;
				}
				datas = SetUtil.intersect(triple.datas, joinDatas);
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

	protected List<Set<DesensitizeOperation>> collectOperations(LocalRule rule, Set<DataCategory> datas) {
		Restriction[] restrictions = rule.getRestrictions();
		List<Set<DesensitizeOperation>> list = null;
		for (Restriction res : restrictions) {
			Set<Desensitization> des = res.getDesensitizations();
			boolean match = false;
			Set<DesensitizeOperation> ops = null;
			for (Desensitization de : des) {
				Set<DataCategory> set = de.getDatas() != null ? de.getDatas() : rule.getData().getDatas();
				if (SetUtil.containOrDisjoint(set, datas).equals(SetRelation.contain)) {
					ops = de.getOperations();
					match = true;
					break;
				}
			}
			if (!match) {
				continue;
			}
			if (list == null) {
				list = new ArrayList<>();
			}
			SetUtil.mergeOperations(list, ops);
		}
		return list;
	}

	private RuleRelation checkRestrictions(SearchKey key, Action action, Set<DataCategory> datas) {
		List<Set<DesensitizeOperation>> joins = null;
		for (int i = 0; i < key.rules.length; i++) {
			RuleObject rule = ruleObjects[key.rules[i]];
			List<Set<DesensitizeOperation>> list = rule.getList(datas);
			if (list == null) {
				logger.error("Possible conflicts between expanded sortedRules: #{}, since rule :#{} forbids the data access.",
						SetUtil.toString(key.rules, sortedRules), sortedRules.get(key.rules[i]).getRuleId());
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
							SetUtil.mergeOperations(tmp, ops2);
						} else if (ops2 == null) {
							SetUtil.mergeOperations(tmp, ops1);
						} else {
							Set<DesensitizeOperation> ops = SetUtil.intersect(ops1, ops2);
							if (ops.size() == 0) {
								return RuleRelation.conflict;
							}
							SetUtil.mergeOperations(tmp, ops);
						}
					}
				}
				joins = tmp;
			}
		}
		return RuleRelation.consistent;
	}

	private RuleObject ruleToObject(LocalRule rule) {
		RuleObject obj = new RuleObject();
		obj.users = new HashSet<>(rule.getUsers());
		DataActionPair[] pairs = rule.getDatas();
		if (rule.getRestriction().isForbid()) {
			obj.triples = new Triple[pairs.length];
			for (int i = 0; i < obj.triples.length; i++) {
				obj.triples[i] = new Triple(pairs[i], null);
				obj.triples[i].action = pairs[i].getAction();
				obj.triples[i].datas = new HashSet<>(pairs[i].getDatas());
			}
		} else {
			List<Triple> triples = new ArrayList<>(pairs.length);
			for (DataActionPair pair : pairs) {
				List<Set<DesensitizeOperation>> list = collectOperations(rule, pair.getDatas());
				if (list != null) {
					Triple triple = new Triple(pair, list);
					triples.add(triple);
				}
			}
			obj.triples = triples.toArray(new Triple[triples.size()]);
		}
		return obj;
	}

}