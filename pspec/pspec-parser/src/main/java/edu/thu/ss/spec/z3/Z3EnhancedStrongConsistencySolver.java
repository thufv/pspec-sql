package edu.thu.ss.spec.z3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.util.InclusionUtil;

public class Z3EnhancedStrongConsistencySolver extends Z3ConsistencySolver {
	private IntExpr[] vars;
	private Sort[] types;
	private Symbol[] symbols;
	private InclusionUtil instance = InclusionUtil.instance;
	private Map<ExpandedRule, BoolExpr> cache = new HashMap<>();
	private ExpandedRule seed;

	private static final int Max_Dimension = 20;

	public Z3EnhancedStrongConsistencySolver() {
		super.init(Max_Dimension);
		logger = LoggerFactory.getLogger(Z3EnhancedStrongConsistencySolver.class);

		vars = new IntExpr[Max_Dimension];
		types = new Sort[Max_Dimension];
		symbols = new Symbol[Max_Dimension];
		try {
			for (int i = 0; i < vars.length; i++) {
				symbols[i] = allSymbols[i];
				vars[i] = (IntExpr) context.mkBound(vars.length - i - 1, context.getIntSort());
				types[i] = context.getIntSort();
			}
		} catch (Z3Exception e) {
			logger.error("", e);
		}
	}

	public void setSeedRule(ExpandedRule rule) {
		cache.clear();
		seed = rule;
		buildExpression(seed);
	}

	public BoolExpr buildExpression(ExpandedRule rule) {
		int[] index = new int[rule.getDimension()];
		for (int i = 0; i < index.length; i++) {
			index[i] = -1;
		}

		try {
			if (seed.isSingle()) {
				index[0] = 0;
				return buildExpression(index, rule.getRestrictions(), vars);
			} else if (seed.isAssociation()) {
				List<BoolExpr> list = new ArrayList<>();
				buildExpression(rule, rule.getDimension(), 0, index, list);
				return context.mkAnd(list.toArray(new BoolExpr[list.size()]));
			}
		} catch (Z3Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private void buildExpression(ExpandedRule rule, int dim, int depth, int[] index,
			List<BoolExpr> list) throws Z3Exception {
		if (dim == depth) {
			list.add(buildExpression(index, rule.getRestrictions(), vars));
		} else {
			DataRef dataRef1 = null;
			if (rule.isSingle()) {
				dataRef1 = rule.getDataRef();
			} else if (rule.isAssociation()) {
				dataRef1 = rule.getAssociation().getDataRefs().get(depth);
			}
			List<DataRef> dataRefs = seed.getAssociation().getDataRefs();
			for (int i = 0; i < dataRefs.size(); i++) {
				DataRef dataRef2 = dataRefs.get(i);
				if (instance.includes(dataRef1, dataRef2)) {
					index[depth] = i;
					buildExpression(rule, dim, depth + 1, index, list);
					index[depth] = -1;
				}
			}
		}
	}

	public boolean isSatisfiable(ExpandedRule[] rules) {
		try {
			BoolExpr[] exprs = new BoolExpr[rules.length + 1];
			for (int i = 0; i < rules.length; i++) {
				ExpandedRule rule = rules[i];
				BoolExpr expr = cache.get(rule);
				if (expr == null) {
					expr = buildExpression(rule);
				}
				exprs[i] = expr;
			}
			exprs[exprs.length - 1] = cache.get(seed);
			if (exprs[exprs.length - 1] == null) {
				exprs[exprs.length - 1] = buildExpression(seed);
			}
			BoolExpr expr = context.mkAnd(exprs);
			BoolExpr condition = context.mkExists(types, symbols, expr, 0, null, null, null, null);
			return isSatisfiable(condition);
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}
	}

}
