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
import edu.thu.ss.spec.z3.Z3NormalConsistencySolver;

public class NormalConsistencySearcher extends LevelwiseSearcher {

	protected List<ExpandedRule> rules;
	protected List<ExpandedRule> sortedRules;
	protected static Z3NormalConsistencySolver z3Util = null;
	protected int[] index;
	
	private static Logger logger = LoggerFactory.getLogger(NormalConsistencySearcher.class);
	
	public NormalConsistencySearcher(List<ExpandedRule> rules) {
		if (z3Util == null) {
			z3Util = new Z3NormalConsistencySolver();
		}
		this.rules = rules;
		conflicts = 0;
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
			conflicts++;
			StringBuilder sb = new StringBuilder();
			sb.append("Possible conflicts:");
			for (int item : key.index) {
				sb.append(sortedRules.get(item).getId());
				sb.append(' ');
			}
			logger.warn(sb.toString());
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
