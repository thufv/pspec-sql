package edu.thu.ss.xml.analyzer;

import static edu.thu.ss.xml.analyzer.InclusionUtil.includes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.Policy;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.Rule.Ruling;
import edu.thu.ss.xml.pojo.UserCategoryRef;

/**
 * Check redundancy of rules, i.e., if any rule with lower precedence is totally
 * overrode by rule with high precedence
 * 
 * @author luochen
 * 
 */
public class RedundancyAnalyzer extends BasePolicyAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(RedundancyAnalyzer.class);

	private Set<String> remove = new HashSet<>();
	private Map<String, Set<Object>> partialRemove = new HashMap<>();

	@Override
	public boolean analyze(Policy policy) {
		List<Rule> rules = policy.getRules();

		for (int i = 0; i < rules.size(); i++) {
			Rule rule1 = rules.get(i);
			for (int j = i + 1; j < rules.size(); j++) {
				Rule rule2 = rules.get(j);
				InclusionResult userInclusion = checkUserInclusion(rule1, rule2);
				InclusionResult dataInclusion = checkDataInclusion(rule1, rule2);
				if (userInclusion.total && dataInclusion.total) {
					logger.error("The rule: {} is redundant, since its scope is totally covered by rule: {}",
							rule2.getId(), rule1.getId());
					addRemove(rule2);
				} else if (userInclusion.total && dataInclusion.partial) {
					logger.error(
							"The following data category (association) in rule: {} is redundant, since it is covered by rule: {}. Consider revise your rules.",
							rule2.getId(), rule1.getId());
					logResult(dataInclusion.list);
					addPartialRemove(rule2, dataInclusion.list);
				} else if (userInclusion.partial && dataInclusion.total) {
					logger.error(
							"The following user category in rule: {} is redundant, since it is covered by rule: {}. Consider revise your rules.",
							rule2.getId(), rule1.getId());
					logResult(userInclusion.list);
					addPartialRemove(rule2, userInclusion.list);
				}
			}

		}

		removeRedundancy(policy);

		return false;
	}

	private void removeRedundancy(Policy policy) {
		List<Rule> rules = policy.getRules();
		Iterator<Rule> it = rules.iterator();
		while (it.hasNext()) {
			Rule rule = it.next();
			if (remove.contains(rule.getId())) {
				it.remove();
			}
		}

		for (Rule rule : rules) {
			Set<Object> set = partialRemove.get(rule.getId());
			if (set == null) {
				continue;
			}

			for (Object obj : set) {
				if (obj instanceof UserCategoryRef) {
					UserCategoryRef user = (UserCategoryRef) obj;
					rule.getUserRefs().remove(user);
				} else if (obj instanceof DataCategoryRef) {
					DataCategoryRef data = (DataCategoryRef) obj;
					rule.getDataRefs().remove(data);
				} else if (obj instanceof DataAssociation) {
					DataAssociation association = (DataAssociation) obj;
					rule.getAssociations().remove(association);
				} else {
					throw new UnsupportedOperationException();
				}
			}
		}
	}

	private void addRemove(Rule rule) {
		remove.add(rule.getId());
		partialRemove.remove(rule.getId());
	}

	private void addPartialRemove(Rule rule, List<Object> list) {
		if (remove.contains(rule.getId())) {
			return;
		}
		Set<Object> set = partialRemove.get(rule.getId());
		if (set == null) {
			set = new HashSet<>();
			partialRemove.put(rule.getId(), set);
		}
		set.addAll(list);
	}

	private void logResult(List<Object> list) {
		for (Object obj : list) {
			logger.error("\t" + obj.toString());
		}
	}

	private class InclusionResult {
		boolean total = true;
		boolean partial = false;
		List<Object> list = new LinkedList<>();
	}

	private InclusionResult checkUserInclusion(Rule rule1, Rule rule2) {
		Set<UserCategoryRef> users1 = rule1.getUserRefs();
		Set<UserCategoryRef> users2 = rule2.getUserRefs();
		InclusionResult result = new InclusionResult();
		for (UserCategoryRef user2 : users2) {
			boolean match = false;
			for (UserCategoryRef user1 : users1) {
				if (includes(user1, user2)) {
					match = true;
					break;
				}
			}
			if (match) {
				result.partial = true;
				result.list.add(user2);
			} else {
				result.total = false;
			}
		}
		return result;
	}

	private InclusionResult checkDataInclusion(Rule rule1, Rule rule2) {
		Set<DataCategoryRef> datas1 = rule1.getDataRefs();
		Set<DataCategoryRef> datas2 = rule2.getDataRefs();

		Set<DataAssociation> associations1 = rule1.getAssociations();
		Set<DataAssociation> associations2 = rule2.getAssociations();

		InclusionResult result = new InclusionResult();
		for (DataCategoryRef data2 : datas2) {
			boolean match = false;
			for (DataCategoryRef data1 : datas1) {
				if (includes(data1, data2)) {
					match = true;
					break;
				}
			}
			if (!match && rule1.getRuling().equals(Ruling.allow)) {
				// data association is allowed implies any single data category
				// contained is implied
				for (DataAssociation association1 : associations1) {
					if (includes(association1, data2, rule1.getRuling())) {
						match = true;
						break;
					}
				}
			}
			if (match) {
				result.partial = true;
				result.list.add(data2);
			} else {
				result.total = false;
			}
		}

		for (DataAssociation association2 : associations2) {
			boolean match = false;
			for (DataCategoryRef data1 : datas1) {
				if (includes(data1, association2, rule1.getRuling())) {
					match = true;
					break;
				}
			}
			if (!match) {
				for (DataAssociation association1 : associations1) {
					if (includes(association1, association2, rule1.getRuling())) {
						match = true;
						break;
					}
				}
			}
			if (match) {
				result.partial = true;
				result.list.add(association2);
			} else {
				result.total = false;
			}
		}
		return result;
	}

}
