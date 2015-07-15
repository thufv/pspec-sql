package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.z3.Z3NormalConsistency;

public class NormalConsistencySearcher extends LevelwiseSearcher {

	protected List<ExpandedRule> rules;
	protected List<ExpandedRule> sortedRules;
	protected Z3NormalConsistency z3Util;
	protected int[] index;

	private static Logger logger = LoggerFactory.getLogger(NormalConsistencySearcher.class);

	public NormalConsistencySearcher(List<ExpandedRule> rules) {
		z3Util = new Z3NormalConsistency(100);
		this.rules = rules;
	}

	@Override
	protected boolean process(SearchKey key) {
		Set<UserCategory> users = null;
		List<ExpandedRule> rules = new ArrayList<>();
		for (int i = 0; i < key.index.length; i++) {
			ExpandedRule rule = sortedRules.get(key.index[i]);
			rules.add(rule);
			if (users == null) {
				users = new HashSet<>(rule.getUsers());
			} else {
				users.retainAll(rule.getUsers());
				if (users.size() == 0) {
					return false;
				}
			}
		}

		boolean result = z3Util.isSatisfiable(rules);
		if (!result) {
			logger.error("Possible conflicts:" + key.toString());
		}
		return result;
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
			z3Util.buildExpression(sortedRules.get(i));
		}

		for (int i = 0; i < sortedRules.size(); i++) {
			currentLevel.add(new SearchKey(i));
		}
	}

}
