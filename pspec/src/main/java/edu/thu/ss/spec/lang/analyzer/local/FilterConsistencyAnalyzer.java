package edu.thu.ss.spec.lang.analyzer.local;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;

import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.util.FilterZ3Util;

public class FilterConsistencyAnalyzer extends StrongConsistencyAnalyzer{

	private Set<String> inconsistency = new HashSet<>();
	private FilterZ3Util z3Util;
	
	public FilterConsistencyAnalyzer() {
		z3Util = new FilterZ3Util();
		logger = LoggerFactory.getLogger(FilterConsistencyAnalyzer.class);
	}
	
	@Override
	protected boolean checkConsistency(List<ExpandedRule> chain) {
		int dim = 0;
		for (ExpandedRule rule : chain) {
			if (rule.getRestriction().isFilter()) {
				int curDim = rule.getRestriction().getFilter().getExpression().getDataSet().size();
				if (curDim > dim) {
					dim = curDim;
				}
			}
		}
		if (dim == 0) {
			return false;
		}
		z3Util.checker.init(dim);
		
		boolean find = false;
		List<BoolExpr> exprs = new ArrayList<>();
		for (ExpandedRule rule : chain) {
			if (!rule.getRestriction().isFilter()) {
				continue;
			}
			BoolExpr expr = expressions.get(rule);
			if (expr == null) {
				expr = z3Util.checker.buildExpression(rule.getRestriction().getFilter());
				expressions.put(rule, expr);
			}
			exprs.add(expr);
			if (!z3Util.checker.satisfiable(exprs)) {
				if (!inconsistency.contains(rule.getId())) {
					inconsistency.add(rule.getId());
					find = true;
					logger.error(rule.getId() + " cause inconsistency");
				}
				
			}
		}	
		return find;
	}
}
