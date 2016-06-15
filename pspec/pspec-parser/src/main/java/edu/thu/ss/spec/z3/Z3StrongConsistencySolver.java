package edu.thu.ss.spec.z3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.analyzer.consistency.ConsistencyAnalyzer.Leaf;
import edu.thu.ss.spec.lang.analyzer.consistency.ConsistencyAnalyzer.LeafAssociation;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;

public class Z3StrongConsistencySolver extends Z3ConsistencySolver {
	
	private IntExpr[] vars;
	private Sort[] types;
	private Symbol[] symbols;
	private ExpandedRule seed;
	private Map<Leaf, Integer> indexMap = new HashMap<>();
	
	private static final int Max_Dimension = 100;
	
	public Z3StrongConsistencySolver() {
		super.init(Max_Dimension);
		logger = LoggerFactory.getLogger(Z3StrongConsistencySolver.class);
		
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
		seed = rule;
		indexMap.clear();
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
		Arrays.fill(index, -1);
		
		if (seed.isSingle()) {
			Leaf leaf = leafAssoc.leafAssociation[0];
			if (indexMap.get(leaf) == null) {
				index[0] = indexMap.size();
				indexMap.put(leaf, index[0]);
			}
			else {
				index[0] = indexMap.get(leaf);
			}
			return buildExpression(index, rule.getRestrictions(), vars);
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
			list.add(buildExpression(index, rule.getRestrictions(), vars));
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
				if (indexMap.get(leaf) == null) {
					index[depth] = indexMap.size();
					indexMap.put(leaf, index[depth]);
				}
				else {
					index[depth] = indexMap.get(leaf);
				}
				buildExpression(leafAssoc, rule, dim, depth + 1, index, list);
				index[depth] = -1;
			}
		}
	}

	public boolean isSatisfiable(BoolExpr[] exprs) {
		try{
			for (int i = 0; i < exprs.length; i++) {
				BoolExpr condition = context.mkExists(types, symbols, exprs[i], 0, null, null, null, null);
				if (!isSatisfiable(condition)) {
					return false;
				}
			}
			return true;
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}
	}
}
