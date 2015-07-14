package edu.thu.ss.spec.lang.analyzer.rule;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserContainer;

/**
 * base class for rule analyzer
 * rule analyzer analyzes each rule independently rather than entire policy.
 * @author luochen
 *
 */
public abstract class BaseRuleAnalyzer extends BasePolicyAnalyzer {

	protected Policy policy;

	public BaseRuleAnalyzer(EventTable table) {
		super(table);
	}

	@Override
	public boolean analyze(Policy policy) {
		boolean error = false;
		this.policy = policy;
		for (Rule rule : policy.getRules()) {
			error = this.analyzeRule(rule, policy.getUserContainer(), policy.getDataContainer()) || error;
		}
		return error;
	}

	protected abstract boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas);

}
