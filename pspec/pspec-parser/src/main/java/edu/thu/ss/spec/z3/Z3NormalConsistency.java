package edu.thu.ss.spec.z3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Status;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;

public class Z3NormalConsistency extends Z3Consistency {
	
	private IntExpr[] vars;
	private Sort[] types;
	private Symbol[] symbols;
	
	public Map<DataCategory, Integer> indexMap = new HashMap<>();
	public Map<ExpandedRule, BoolExpr> exprMap = new HashMap<>();
	
	public Z3NormalConsistency(int dim) {
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

	public BoolExpr buildExpression(ExpandedRule rule) {
		try{
			BoolExpr expr = null;
			if (rule.isSingle()) {
				expr = buildSingleExpression(rule);
				exprMap.put(rule, expr);
			}
			else if (rule.isAssociation()) {
				expr = buildAssociationExpression(rule);
				exprMap.put(rule, expr);
			}
			return expr;
		} catch (Z3Exception e) {
			logger.error("", e);
			return null;
		}
	}

	private BoolExpr buildAssociationExpression(ExpandedRule rule) 
			throws Z3Exception {
		int[] index = new int[rule.getDimension()];
		List<BoolExpr> list = new ArrayList<>();
		buildAssociationExpression(0, index, rule, list);
		return context.mkAnd(list.toArray(new BoolExpr[list.size()]));
	}
	
	private void buildAssociationExpression(int depth, int[] index, ExpandedRule rule, List<BoolExpr> list) 
			throws Z3Exception {
		if (depth == rule.getDimension()) {
			list.add(buildExpression(index, rule.getRestrictions()));
		}
		else {
			List<DataRef> dataRefs = rule.getAssociation().getDataRefs();
			Set<DataCategory> categories = dataRefs.get(depth).getMaterialized();
			for (DataCategory category : categories) {
				if (indexMap.get(category) == null) {
					index[depth] = indexMap.size();
					indexMap.put(category, index[depth]);
				}
				else {
					index[depth] = indexMap.get(category);
				}
				buildAssociationExpression(depth + 1, index, rule, list);
			}
		}
	}

	private BoolExpr buildSingleExpression(ExpandedRule rule) 
			throws Z3Exception {
		Set<DataCategory> categories = rule.getDataRef().getMaterialized();
		Restriction[] restrictions = rule.getRestrictions();
		List<BoolExpr> exprs = new ArrayList<>();
		int[] index = new int[1];
		for (DataCategory category : categories) {
			if (indexMap.get(category) == null) {
				index[0] = indexMap.size();
				indexMap.put(category, index[0]);
			}
			else {
				index[0] = indexMap.get(category);
			}
			exprs.add(buildExpression(index, restrictions));
		}
		return context.mkAnd(exprs.toArray(new BoolExpr[exprs.size()]));
	}

	private BoolExpr buildExpression(int[] index, Restriction[] restrictions) 
			throws Z3Exception {
		BoolExpr[] exprs = new BoolExpr[restrictions.length];
		for (int i = 0; i < restrictions.length; i++) {
			exprs[i] = buildExpression(index, restrictions[i]);
		}
		return context.mkOr(exprs);
	}

	private BoolExpr buildExpression(int[] index, Restriction restriction) 
			throws Z3Exception {
		List<Desensitization> des = restriction.getDesensitizations();
		BoolExpr[] exprs = new BoolExpr[des.size()];
		for (int i = 0; i < des.size(); i++) {
			if (des.get(i) != null) {
				exprs[i] = buildExpression(index[i], des.get(i));
			} else {
				exprs[i] = context.mkTrue();
			}
		}
		return context.mkAnd(exprs);
	}

	private BoolExpr buildExpression(int index, Desensitization de) 
			throws Z3Exception {
		BoolExpr[] exprs = new BoolExpr[de.getOperations().size()];
		int i = 0;
		for (DesensitizeOperation op : de.getOperations()) {
			IntExpr var = vars[index];
			Expr num = context.mkNumeral(op.getId(), context.getIntSort());
			exprs[i++] = context.mkEq(var, num);
		}
		return context.mkOr(exprs);
	}

	public boolean isSatisfiable(List<ExpandedRule> rules) {
		BoolExpr[] exprs = new BoolExpr[rules.size()];
		for (int i = 0; i < rules.size(); i++) {
			ExpandedRule rule = rules.get(i);
			BoolExpr expr = exprMap.get(rule);
			if (expr == null) {
				expr = buildExpression(rule);
				exprMap.put(rule, expr);
			}
			exprs[i] = expr;
		}
		
		try{
			BoolExpr expr = context.mkAnd(exprs);
			BoolExpr condition = context.mkExists(types, symbols, expr, 0, null, null, null, null);
			Solver solver = context.mkSolver();
			solver.add(condition);
			Status status = solver.check();
			return status.equals(Status.SATISFIABLE);
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}
	}
}
