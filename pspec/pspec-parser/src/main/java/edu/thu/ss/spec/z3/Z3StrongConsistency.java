package edu.thu.ss.spec.z3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.microsoft.z3.ApplyResult;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Goal;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Tactic;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.analyzer.consistency.StrongConsistencySearcher.Leaf;
import edu.thu.ss.spec.lang.analyzer.consistency.StrongConsistencySearcher.LeafAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;

public class Z3StrongConsistency extends Z3Consistency {
	
	private IntExpr[] vars;
	private Sort[] types;
	private Symbol[] symbols;
	private ExpandedRule seed;
	private Map<DataCategory, Integer> indexMap = new HashMap<>();
	
	public Z3StrongConsistency(int dim) {
		super.init(dim);
		logger = LoggerFactory.getLogger(Z3StrongConsistency.class);
		
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
		seed = rule;
	}
	
	public BoolExpr[] buildExpression(Set<LeafAssociation> set, ExpandedRule[] rules) {
		BoolExpr[] exprs = new BoolExpr[set.size()];
		int i = 0;
		for (LeafAssociation leafAssoc : set) {
			exprs[i++] = buildExpression(leafAssoc, rules);
		}
		return exprs;
	}
	
	public BoolExpr buildExpression(LeafAssociation leafAssoc, ExpandedRule[] rules) {
		BoolExpr[] exprs = new BoolExpr[rules.length + 1];
		try {
			for (int i = 0; i < rules.length; i++) {
				exprs[i] = buildExpression(leafAssoc, rules[i]);
			}
			exprs[exprs.length - 1] = buildExpression(leafAssoc, seed);
			return context.mkAnd(exprs);
		} catch (Z3Exception e) {
			logger.error("", e);		
			return null;
		}
	}
	
	
	private BoolExpr buildExpression(LeafAssociation leafAssoc, ExpandedRule rule) 
			throws Z3Exception {
		int[] index = new int[rule.getDimension()];
		for (int i = 0; i < index.length; i++) {
			index[i] = -1;
		}
		
		if (seed.isSingle()) {
			DataCategory category = leafAssoc.leafAssociation[0].category;
			if (indexMap.get(category) == null) {
				index[0] = indexMap.size();
				indexMap.put(category, index[0]);
			}
			else {
				index[0] = indexMap.get(category);
			}
			return buildExpression(index, rule.getRestrictions());
		}
		else if (seed.isAssociation()) {
			List<BoolExpr> list = new ArrayList<>();
			buildExpression(leafAssoc, rule, rule.getDimension(), 0, index, list);
			return context.mkAnd(list.toArray(new BoolExpr[list.size()]));
		}
		return null;		
	}
	
	private void buildExpression(LeafAssociation leafAssoc, ExpandedRule rule,
			int dim, int depth, int[] index, List<BoolExpr> list) throws Z3Exception {
		if (dim == depth) {
			list.add(buildExpression(index, rule.getRestrictions()));
			return;
		}
		
		DataRef dataRef = null;
		if (rule.isSingle()) {
			dataRef = rule.getDataRef();
		}
		else if (rule.isAssociation()) {
			dataRef = rule.getAssociation().getDataRefs().get(depth);;
		}
		for (int i = 0; i < leafAssoc.leafAssociation.length; i++) {
			Leaf leaf = leafAssoc.leafAssociation[i];
			if (leaf.belongTo(dataRef)) {
				if (indexMap.get(leaf.category) == null) {
					index[depth] = indexMap.size();
					indexMap.put(leaf.category, index[depth]);
				}
				else {
					index[depth] = indexMap.get(leaf.category);
				}
				buildExpression(leafAssoc, rule, dim, depth + 1, index, list);
				index[depth] = -1;
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

	public boolean isSatisfiable(BoolExpr[] exprs) {
		try{
			Tactic simplifyTactic = context.mkTactic("ctx-solver-simplify");
			for (int i = 0; i < exprs.length; i++) {
				BoolExpr condition = context.mkExists(types, symbols, exprs[i], 0, null, null, null, null);
				Goal g = context.mkGoal(false, false, false);
				g.add(condition);
				ApplyResult a = simplifyTactic.apply(g);
				Goal[] goals = a.getSubgoals();
				for (int j = 0; j < goals.length; j++) {
					if (goals[j].isDecidedUnsat()) {
						return false;
					}
				}
				/*
				Solver solver = context.mkSolver();
				solver.add(condition);
				Status status = solver.check();
				if (status.equals(Status.SATISFIABLE)) {
					solver.dispose();
				}
				else {
					solver.dispose();
					return false;
				}
				*/
			}
			return true;
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}
	}
}
