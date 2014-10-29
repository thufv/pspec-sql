package edu.thu.ss.lang.analyzer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.lang.pojo.Action;
import edu.thu.ss.lang.pojo.DataActionPair;
import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.Desensitization;
import edu.thu.ss.lang.pojo.DesensitizeOperation;
import edu.thu.ss.lang.pojo.ExpandedRule;
import edu.thu.ss.lang.pojo.Policy;
import edu.thu.ss.lang.pojo.Restriction;
import edu.thu.ss.lang.pojo.UserCategory;

public class ConsistencyAnalyzer extends BasePolicyAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(ConsistencyAnalyzer.class);

	@Override
	public boolean analyze(Policy policy) {

		PolicyConsistencySearcher searcher = new PolicyConsistencySearcher(policy);
		searcher.search();
		return false;
	}

	private class PolicyConsistencySearcher extends LevelwiseSearcher {
		private Policy policy;
		private List<ExpandedRule> rules;

		public PolicyConsistencySearcher(Policy policy) {
			this.policy = policy;
			this.rules = policy.getExpandedRules();
		}

		@Override
		protected void initLevel(Set<SearchKey> currentLevel) {
			List<ExpandedRule> rules = policy.getExpandedRules();
			for (int i = 0; i < rules.size(); i++) {
				if (rules.get(i).isAssociation()) {
					continue;
				}
				SearchKey key = new SearchKey(i);
				currentLevel.add(key);
			}
		}

		@Override
		protected boolean process(SearchKey key) {

			Action action = Action.all;
			for (int i : key.rules) {
				ExpandedRule rule = rules.get(i);
				DataActionPair pair = rule.getData();
				action = action.bottom(pair.getAction());
				if (action == null) {
					return false;
				}

			}
			Set<UserCategory> users;
			Set<DataCategory> datas;
			users = new HashSet<>(rules.get(key.rules[0]).getUsers());
			datas = new HashSet<>(rules.get(key.rules[0]).getDatas()[0].getDatas());
			for (int i = 1; i < key.rules.length; i++) {
				ExpandedRule rule = rules.get(key.rules[i]);
				users.retainAll(rule.getUsers());
				if (users.size() == 0) {
					return false;
				}
				DataActionPair pair = rule.getData();
				datas.retainAll(pair.getDatas());
				if (datas.size() == 0) {
					return false;
				}
			}

			for (int i : key.rules) {
				ExpandedRule rule = rules.get(i);
				Restriction res = rule.getRestriction();
				if (res.isForbid()) {
					logger.warn(
							"Possible conflicts between expanded rules: #{}, since rule :#{} forbids the data access.",
							Arrays.toString(key.rules), i);
					return false;
				}

			}

			Set<DesensitizeOperation> ops = null;
			Desensitization de0 = rules.get(key.rules[0]).getRestriction().getDesensitization();
			boolean isDefault = de0.isDefaultOperation();
			if (!isDefault) {
				ops = new HashSet<>(de0.getOperations());
			}

			for (int i = 1; i < key.rules.length; i++) {
				ExpandedRule rule = rules.get(key.rules[i]);
				Desensitization de = rule.getRestriction().getDesensitization();
				if (de.isDefaultOperation()) {
					continue;
				} else if (isDefault) {
					ops = new HashSet<>(de.getOperations());
					isDefault = false;
					continue;
				}

				ops.retainAll(de.getOperations());
				if (ops.size() == 0) {
					StringBuilder sb = new StringBuilder();
					for (DataCategory data : datas) {
						sb.append(data.getId());
						sb.append(' ');
					}
					logger.error(
							"Desensitize operation conflicts detected between expanded rules: #{} for data categories: {}.",
							Arrays.toString(key.rules), sb);
				}
				return false;

			}

			return true;
		}
	}

}
