package edu.thu.ss.spec.lang.analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.util.DesensitizeZ3Util;
import edu.thu.ss.spec.util.FilterZ3Util;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.Z3Util;

public class FilterRedundancyAnalyzer extends BaseRedundancyAnalyzer {

	protected static FilterZ3Util z3Util = new FilterZ3Util();

	public FilterRedundancyAnalyzer() {
		this.instance = InclusionUtil.instance;
	}
	
	@Override
	public boolean analyze(Policy policy) {
		super.logger = LoggerFactory
				.getLogger(FilterRedundancyAnalyzer.class);
		return super.analyze(policy);
	}
	
	@Override
	protected List<ExpandedRule> getCheckRules(Policy policy) {
		List<ExpandedRule> list = policy.getExpandedRules();
		Set<DataRef> dataRefs = new HashSet<>();
		List<ExpandedRule> res = new ArrayList<>();
		
		for (ExpandedRule rule : list) {
			if (rule.getRestriction().isFilter()) {
				dataRefs.addAll(rule.getRestriction().getFilter().getDataRefs());
				res.add(rule);
			}
		}
		z3Util.init(dataRefs);
		return res;
	}

	@Override
	protected boolean checkRedundancy(ExpandedRule rule1, ExpandedRule rule2) {
		if (!checkScope(rule1, rule2)) {
			return false;
		}
		
		Set<DataRef> set1 = rule1.getRestriction().getFilter().getDataRefs();
		Set<DataRef> set2 = rule2.getRestriction().getFilter().getDataRefs();
		if (!SetUtil.contains(set1, set2)) {
			return false;
		}

		//TODO datacategory
		boolean res = false;
		res =  z3Util.implies(rule1.getRestriction().getFilter(), rule2.getRestriction().getFilter());

		return res;
	}

	@Override
	protected void commit(int count) {
		
	}

}
