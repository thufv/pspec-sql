package edu.thu.ss.spec.z3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.analyzer.consistency.ConsistencyAnalyzer.Leaf;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;

public class Z3NormalConsistencySolver extends Z3ConsistencySolver {

	private IntExpr[] vars;
	private Sort[] types;
	private Symbol[] symbols;

	public Map<Leaf, Integer> indexMap = new HashMap<>();
	public Map<ExpandedRule, BoolExpr> exprMap = new HashMap<>();

	private static final int Max_Dimension = 200;

	public Z3NormalConsistencySolver() {
		super.init(Max_Dimension);
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

	public BoolExpr buildExpression(ExpandedRule rule) {
		try {
			BoolExpr expr = null;
			if (rule.isSingle()) {
				expr = buildSingleExpression(rule);
				exprMap.put(rule, expr);
			} else if (rule.isAssociation()) {
				expr = buildAssociationExpression(rule);
				exprMap.put(rule, expr);
			}
			return expr;
		} catch (Z3Exception e) {
			logger.error("", e);
			return null;
		}
	}

	private BoolExpr buildAssociationExpression(ExpandedRule rule) throws Z3Exception {
		int[] index = new int[rule.getDimension()];
		List<BoolExpr> list = new ArrayList<>();
		buildAssociationExpression(0, index, rule, list);
		return context.mkAnd(list.toArray(new BoolExpr[list.size()]));
	}

	private void buildAssociationExpression(int depth, int[] index, ExpandedRule rule,
			List<BoolExpr> list) throws Z3Exception {
		if (depth == rule.getDimension()) {
			list.add(buildExpression(index, rule.getRestrictions(), vars));
		} else {
			List<DataRef> dataRefs = rule.getAssociation().getDataRefs();
			Set<DataCategory> categories = dataRefs.get(depth).getMaterialized();
			for (DataCategory category : categories) {
				Leaf leaf = new Leaf(category, dataRefs.get(depth).getAction());
				if (indexMap.get(leaf) == null) {
					index[depth] = indexMap.size();
					indexMap.put(leaf, index[depth]);
				} else {
					index[depth] = indexMap.get(leaf);
				}
				buildAssociationExpression(depth + 1, index, rule, list);
			}
		}
	}

	private BoolExpr buildSingleExpression(ExpandedRule rule) throws Z3Exception {
		Set<DataCategory> categories = rule.getDataRef().getMaterialized();
		Restriction[] restrictions = rule.getRestrictions();
		List<BoolExpr> exprs = new ArrayList<>();
		int[] index = new int[1];
		for (DataCategory category : categories) {
			Leaf leaf = new Leaf(category,  rule.getDataRef().getAction());
			if (indexMap.get(leaf) == null) {
				index[0] = indexMap.size();
				indexMap.put(leaf, index[0]);
			} else {
				index[0] = indexMap.get(leaf);
			}
			exprs.add(buildExpression(index, restrictions, vars));
		}
		return context.mkAnd(exprs.toArray(new BoolExpr[exprs.size()]));
	}

	public boolean isSatisfiable(ExpandedRule[] rules) {
		BoolExpr[] exprs = new BoolExpr[rules.length];
		for (int i = 0; i < rules.length; i++) {
			ExpandedRule rule = rules[i];
			if (rule.getRestriction().isForbid()) {
				logger.warn("conflict detected because {} is forbid.", rule.getId());
				return false;
			}
			BoolExpr expr = exprMap.get(rule);
			if (expr == null) {
				expr = buildExpression(rule);
				exprMap.put(rule, expr);
			}
			exprs[i] = expr;
		}

		try {
			BoolExpr expr = context.mkAnd(exprs);
			BoolExpr condition = context.mkExists(types, symbols, expr, 0, null, null, null, null);
			return isSatisfiable(condition);
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}
	}
}
