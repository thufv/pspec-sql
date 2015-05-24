package edu.thu.ss.spec.lang.analyzer.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.analyzer.LevelwiseSearcher;
import edu.thu.ss.spec.lang.analyzer.LevelwiseSearcher.SearchKey;
import edu.thu.ss.spec.lang.analyzer.RuleTreeSearcher.TreeNode;
import edu.thu.ss.spec.lang.analyzer.local.ConsistencySearcher.RuleObject;
import edu.thu.ss.spec.lang.analyzer.local.ConsistencySearcher.Triple;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.DesensitizeZ3Util;
import edu.thu.ss.spec.util.FilterZ3Util;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.SetUtil.SetRelation;

public class DesensitizeConsistencyAnalyzer extends StrongConsistencyAnalyzer{

	private DesensitizeZ3Util z3Util = new DesensitizeZ3Util();
	private int conflicts = 0;
	
	public DesensitizeConsistencyAnalyzer() {
		instance = InclusionUtil.instance;
		logger = LoggerFactory.getLogger(DesensitizeConsistencyAnalyzer.class);
	}

	@Override
	protected boolean checkConsistency(List<TreeNode> level) {
		for (TreeNode node : level) {
			BoolExpr expr = cache.get(node);
			if (expr == null) {
				expr = z3Util.buildExpression(node.getRule());
				cache.put(node, expr);
			}
			else {
				if (!z3Util.isExprSatisifiable(expr)) {
					logger.error("detect inconsistency when analyzing {}", node.getRule().getId());
					conflicts++;
				}
			}
		}
		return false;
	}

	@Override
	protected List<ExpandedRule> getCheckRules(List<ExpandedRule> rules) {
		List<ExpandedRule> list = new ArrayList<>();
		for (ExpandedRule rule : rules) {
			if (!rule.getRestriction().isFilter()) {
				list.add(rule);
			}
		}
		return list;
	}

	@Override
	protected void combine(TreeNode node1, TreeNode node2) {
		if (!node1.contains(node2)) {
			logger.error("invalid combines nodes");
		}
		
		if (cache.get(node1) == null) {
			BoolExpr expr1 = z3Util.buildExpression(node1.getRule());
			cache.put(node1, expr1);
		}
		
		if (node1.getRule().isSingle() && node2.getRule().isSingle()) {
			combineSingle(node1, node2);
		}
		else if (node1.getRule().isAssociation() && node2.getRule().isSingle()) {
			combineAssociationSingle(node1, node2);
		}
		else if (node1.getRule().isAssociation() && node2.getRule().isAssociation()) {
			combineBothAssociation(node1, node2);
		}
	}
	
	protected void combineSingle(TreeNode node1, TreeNode node2) {
		BoolExpr expr1 = cache.get(node1);
		BoolExpr expr2 = cache.get(node2);
		
		/*
		try {
			expr2 = (BoolExpr) expr2.substitute(expr2.getArgs(), expr1.getArgs());
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
		*/
		BoolExpr expr = z3Util.buildAndExpr(expr1, expr2);
		cache.remove(node2);
		cache.put(node1, expr);
	}
	
	protected void combineAssociationSingle(TreeNode node1, TreeNode node2) {
		BoolExpr expr1 = cache.get(node1);
		BoolExpr expr2 = cache.get(node2);
		
		DataAssociation assoc1 = node1.getRule().getAssociation();
		DataRef ref2 = node2.getRule().getDataRef();
		

		boolean match = false;
		int index = 0;
		int dim = assoc1.getDimension();
		boolean[] covered = new boolean[dim];
		int[][] dataIncludes = new int[dim][dim];
		int[] dataLength = new int[dim];
		
		Arrays.fill(covered, false);
		List<DataRef> dataRefs = assoc1.getDataRefs();
		for (int i = 0; i < dataRefs.size(); i++) {
			DataRef ref1 = dataRefs.get(i);
			if (instance.includes(ref2, ref1)) {
				match = true;
				dataIncludes[0][index++] = i;
				covered[i] = true;
			}
		}
		dataLength[0] = index;
		if (!match) {
			logger.error("cannot combine nodes");
			return;
		}

		BoolExpr expr = z3Util.combineExpressions(expr1, expr2, dim, dataIncludes, dataLength, covered);
		cache.remove(node2);
		cache.put(node1, expr);
	}
	
	protected void combineBothAssociation(TreeNode node1, TreeNode node2) {
		BoolExpr expr1 = cache.get(node1);
		BoolExpr expr2 = cache.get(node2);
		
		DataAssociation assoc1 = node1.getRule().getAssociation();
		DataAssociation assoc2 = node2.getRule().getAssociation();
		
		List<DataRef> dataRefs1 = assoc1.getDataRefs();
		List<DataRef> dataRefs2 = assoc2.getDataRefs();
		
		int dim = assoc1.getDimension();
		boolean[] covered = new boolean[dim];
		int[][] dataIncludes = new int[dim][dim];
		int[] dataLength = new int[dim];
		
		Arrays.fill(covered, false);
		for (int i = 0; i < dataRefs2.size(); i++) {
			boolean match = false;
			int index = 0;
			DataRef ref2 = dataRefs2.get(i);
			for (int j = 0; j < dataRefs1.size(); j++) {
				DataRef ref1 = dataRefs2.get(j);
				if (instance.includes(ref2, ref1)) {
					match = true;
					dataIncludes[i][index++] = j;
					covered[j] = true;
				}
			}
			if (!match) {
				logger.error("cannot combine nodes");
				return;
			}
			dataLength[i] = index;
		}

		BoolExpr expr = z3Util.combineExpressions(expr1, expr2, dim, dataIncludes, dataLength, covered);
		cache.remove(node2);
		cache.put(node1, expr);
	}
	
}
