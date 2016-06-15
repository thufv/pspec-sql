package edu.thu.ss.spec.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.z3.ApplyResult;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Goal;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Status;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Tactic;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.pojo.Condition;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;

public class Z3EnhancedStrongConsistency extends Z3Util {
	private IntExpr[] vars;
	private Sort[] types;
	private Symbol[] symbols;
	private InclusionUtil instance = InclusionUtil.instance;
	private Map<ExpandedRule, BoolExpr> cache = new HashMap<>();
	private Map<DataCategory, Integer> indexMap = new HashMap<>();
	
	private ExpandedRule seed;
	
	public Z3EnhancedStrongConsistency(int dim) {
		super.init(dim);
		vars = new IntExpr[dim];
		types = new Sort[dim];
		symbols = new Symbol[dim];
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
		if (rule.getRestriction().isFilter()) {
			return buildFilterExpression(rule);
		}
		
		int[] index = new int[rule.getDimension()];
		for (int i = 0; i < index.length; i++) {
			index[i] = -1;
		}
		
		try {
			if (seed.isSingle()) {
				index[0] = 0;
				return buildExpression(index, rule.getRestrictions());
			}
			else if (seed.isAssociation()) {
				List<BoolExpr> list = new ArrayList<>();
				buildExpression(rule, rule.getDimension(), 0, index, list);
				return context.mkAnd(list.toArray(new BoolExpr[list.size()]));
			}
		} catch (Z3Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	private void buildExpression(ExpandedRule rule, int dim, int depth, int[] index, List<BoolExpr> list) 
			throws Z3Exception {
		if (dim == depth) {
			list.add(buildExpression(index, rule.getRestrictions()));
		}
		else {
			DataRef dataRef1 = null;
			if (rule.isSingle()) {
				dataRef1 = rule.getDataRef();
			}
			else if (rule.isAssociation()) {
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
	
	private BoolExpr buildExpression(int[] index, Restriction[] restrictions) throws Z3Exception {
		BoolExpr[] exprs = new BoolExpr[restrictions.length];
		for (int i = 0; i < restrictions.length; i++) {
			exprs[i] = buildExpression(index, restrictions[i]);
		}
		return context.mkOr(exprs);
	}

	private BoolExpr buildExpression(int[] index, Restriction restriction) throws Z3Exception {
		Desensitization[] des = restriction.getDesensitizations();
		BoolExpr[] exprs = new BoolExpr[des.length];
		for (int i = 0; i < des.length; i++) {
			if (des[i] != null) {
				exprs[i] = buildExpression(index[i], des[i]);
			} else {
				exprs[i] = context.mkTrue();
			}
		}
		return context.mkAnd(exprs);
	}

	private BoolExpr buildExpression(int index, Desensitization de) throws Z3Exception {
		if (index == -1) {
			return context.mkTrue();
		}
		BoolExpr[] exprs = new BoolExpr[de.getOperations().size()];
		int i = 0;
		for (DesensitizeOperation op : de.getOperations()) {
			IntExpr var = vars[index];
			Expr num = context.mkNumeral(op.getId(), context.getIntSort());
			exprs[i++] = context.mkEq(var, num);
		}
		return context.mkOr(exprs);
	}
	
	private BoolExpr buildFilterExpression(ExpandedRule rule) {
		Condition condition = rule.getRestriction().getFilter();
		try {
			return buildExpression(condition.getExpression(), vars);
		} catch (Z3Exception e) {
			logger.error("", e);
			return null;
		}
	}

	public boolean isSatisfiable(ExpandedRule[] rules) {
		try{
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
			Tactic simplifyTactic = context.mkTactic("ctx-solver-simplify");
			Goal g = context.mkGoal(false, false, false);
			g.add(condition);
			ApplyResult a = simplifyTactic.apply(g);
			Goal[] goals = a.getSubgoals();
			for (int i = 0; i < goals.length; i++) {
				if (goals[i].isDecidedUnsat()) {
					return false;
				}
			}
			simplifyTactic.dispose();
			//Solver solver = context.mkSolver();
			//solver.add(condition);
			//Status status = solver.check();
			//boolean result = status.equals(Status.SATISFIABLE);
			//solver.dispose();
			return true;
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}
	}

}
