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
import edu.thu.ss.spec.z3.Z3EnhancedStrongConsistency;

public class EnhancedStrongConsistencySearcher extends LevelwiseSearcher{

	private ExpandedRule seed;
	private List<ExpandedRule> candidates;
	private List<ExpandedRule> sortedRules;
	private Z3EnhancedStrongConsistency z3Util;
	
	private static Logger logger = LoggerFactory.getLogger(EnhancedStrongConsistencySearcher.class);
	
	public EnhancedStrongConsistencySearcher() {
		z3Util = new Z3EnhancedStrongConsistency(20);
	}
	
	public void init(ExpandedRule seed, List<ExpandedRule> candidates) {
		this.seed = seed;
		this.candidates = candidates;
		z3Util.setSeedRule(seed);
	}
	
	@Override
	protected boolean process(SearchKey key) {
		Set<UserCategory> users = null;
		ExpandedRule[] rules = new ExpandedRule[key.index.length];
		for (int i = 0; i < key.index.length; i++) {
			ExpandedRule rule = sortedRules.get(key.index[i]);
			rules[i] = rule;
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
			logger.error("Possible conflicts when adding: "+sortedRules.get(key.getLast()).getId());
		}
		return result;
	}

	@Override
	protected void initLevel(Set<SearchKey> currentLevel) {			
		sortedRules = new ArrayList<>(candidates);
		Collections.sort(sortedRules, new Comparator<ExpandedRule>() {
			@Override
			public int compare(ExpandedRule o1, ExpandedRule o2) {
				return Integer.compare(o1.getDimension(), o2.getDimension());
			}
		});

		int[] index = new int[sortedRules.size()];
		for (int i = 0; i < index.length; i++) {
			ExpandedRule rule = sortedRules.get(i);
			index[i] = sortedRules.indexOf(rule);
			ExpandedRule[] rules = {rule};
			if (z3Util.isSatisfiable(rules)) {
				SearchKey key = new SearchKey(i);
				currentLevel.add(key);
			}
			else {
				logger.error("conflict between {} and {}", rule.getId(), seed.getId());
			}
			
		}
	}

}
