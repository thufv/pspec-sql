package edu.thu.ss.spec.lang.analyzer.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.RuleTreeSearcher;
import edu.thu.ss.spec.lang.analyzer.RuleTreeSearcher.TreeNode;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.util.FilterZ3Util;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.Z3Util;

public abstract class StrongConsistencyAnalyzer1 extends  BasePolicyAnalyzer {
	
	protected  Logger logger = null;
	protected InclusionUtil instance = null;
	
	protected RuleTreeSearcher tree;
	protected List<TreeNode> currentLevel;
	protected Map<TreeNode, BoolExpr> cache = new HashMap<>();
	
	protected abstract boolean checkConsistency(List<TreeNode> level);
	protected abstract List<ExpandedRule> getCheckRules(List<ExpandedRule> rules);
	protected abstract void combine(TreeNode node1, TreeNode node2);
	
	
	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> rules = policy.getExpandedRules();
		rules = getCheckRules(rules);
		tree = new RuleTreeSearcher(rules);
		
		initLevel();
		while (true) {
			checkConsistency(currentLevel);
			if (generateNextLevel()) {
				checkConsistency(currentLevel);
				break;
			}
		}
		boolean find = false;
		if (!find) {
			logger.error("no inconsistency is found");
		}
		return false;
	}

	private void initLevel() {
		currentLevel = tree.getLeafNodes();
	}
	
	private boolean generateNextLevel() {
		if (currentLevel.get(0).getParents().contains(tree.root)) {
			return true;
		}
		
		for (TreeNode node : currentLevel) {
			for (TreeNode parent : node.getParents()) {
				combine(parent, node);
			}
		}
		tree.deleteLeafNodes();
		currentLevel = tree.getLeafNodes();
		return false;
	}
}
