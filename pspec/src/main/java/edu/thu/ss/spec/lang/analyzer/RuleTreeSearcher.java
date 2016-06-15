package edu.thu.ss.spec.lang.analyzer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.microsoft.z3.BoolExpr;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.SetUtil.SetRelation;

//TODO JGRAPHT
public class RuleTreeSearcher {
	
	public TreeNode root = new TreeNode(null);
	protected InclusionUtil instance = null;
	private Map<ExpandedRule, TreeNode> ruleNodeMap = new HashMap<>();
	
	public class TreeNode {
		
		private ExpandedRule rule;
		private List<TreeNode> parents = new ArrayList<>();
		private List<TreeNode> children = new ArrayList<>();
		
		public TreeNode(ExpandedRule rule) {
			this.rule = rule;
			if (rule != null) {
				ruleNodeMap.put(rule, this);
			}
		}
		
		public boolean isLeafNode() {
			return (children.size() == 0);
		}
		
		public ExpandedRule getRule() {
			return rule;
		}
		
		public void addParent(TreeNode parent) {
			this.parents.add(parent);
		}
		
		public void setParent(List<TreeNode> parent) {
			this.parents = parent;
		}
		
		public List<TreeNode> getParents() {
			return parents;
		}
		
		public List<TreeNode> getChildren() {
			return children;
		}
		
		public void addChild(TreeNode child) {
			children.add(child);
		}
		
		public void addChildren(Set<TreeNode> children) {
			this.children.addAll(children);
		}
		
		public void deleteChild(TreeNode child) {
			children.remove(child);
		}
		
		public void clearChild() {
			children.clear();
		}
		
		public boolean contains(TreeNode other) {
			if (this == root) {
				return true;
			}
			
			if (rule.isSingle() && other.rule.isSingle()) {
				DataRef ref1 = rule.getDataRef();
				DataRef ref2 = other.rule.getDataRef();
				
				Action action1 = ref1.getAction();
				Action action2 = ref2.getAction();
				if (!instance.includes(action2, action1)) {
					return false;
				}
				
				Set<DataCategory> data1 = ref1.getMaterialized();
				Set<DataCategory> data2 = ref2.getMaterialized();
				SetRelation dataRelation = SetUtil.relation(data1, data2);
				if (dataRelation.equals(SetRelation.contain)) {
					return true;
				}
				
				return false;
			}
			else if (rule.isAssociation()) {
				List<DataRef> dataRefs1 = rule.getAssociation().getDataRefs();
				if (other.rule.isSingle()) {
					DataRef ref2 = other.rule.getDataRef();
					for (DataRef ref1 : dataRefs1) {
						if (instance.includes(ref1, ref2)) {
							return true;
						}
					}
					return false;
				}
				else if (other.rule.isAssociation()) {
					List<DataRef> dataRefs2 = other.rule.getAssociation().getDataRefs();
					for (DataRef ref2 : dataRefs2) {
						boolean match = false;
						for (DataRef ref1 : dataRefs1) {
							if (instance.includes(ref1, ref2)) {
								match = true;
								break;
							}
						}
						if (!match) {
							return false;
						}
					}
					return true;
				}
			}
			return false;
		}
	}
	
	public RuleTreeSearcher(List<ExpandedRule> sortedRules) {
		Collections.sort(sortedRules, new Comparator<ExpandedRule>() {
			@Override
			public int compare(ExpandedRule o1, ExpandedRule o2) {
				return Integer.compare(o1.getDimension(), o2.getDimension());
			}
		});
		init(sortedRules);
	}
	
	public List<TreeNode> getLeafNodes() {
		return getLeafNodes(root);
	}
	
	public void deleteLeafNodes() {
		List<TreeNode> leafNodes = getLeafNodes();
		for (TreeNode node : leafNodes) {
			for (TreeNode parent : node.parents) {
				parent.children.remove(node);
			}
		}
	}
	
	public List<TreeNode> getLeafNodes(TreeNode node) {
		List<TreeNode> leafNodes = new ArrayList<>();
		if (node.isLeafNode()) {
			leafNodes.add(node);
		}
		else {
			for (TreeNode child : node.children) {
			leafNodes.addAll(getLeafNodes(child));
			}
		}
		return leafNodes;
	}
	
	private void buildGraph(List<ExpandedRule> rules) {
		Map<ExpandedRule, TreeNode> map = new HashMap<>();
		for (ExpandedRule rule : rules) {
			TreeNode ruleNode = map.get(rule);
			if (ruleNode == null) {
				ruleNode = new TreeNode(rule);
				map.put(rule, ruleNode);
			}
			for (ExpandedRule target : rules) {
				if (rule.equals(target)) {
					continue;
				}
				
				TreeNode targetNode = map.get(target);
				if (targetNode == null) {
					targetNode = new TreeNode(target);
					map.put(target, targetNode);
				}
				if (targetNode.contains(ruleNode)) {
					continue;
				}
			}
		}
	}
	private void init(List<ExpandedRule> rules) {
		this.instance = InclusionUtil.instance;
		for (int i = 0; i < rules.size(); i++) {
			addNode(rules.get(i));
		}
	}
	
	private void addNode(ExpandedRule rule) {
		if (root.getChildren().isEmpty()) {
			TreeNode node = new TreeNode(rule);
			node.addParent(root);
			root.getChildren().add(node);
		}
		else {
			TreeNode node = new TreeNode(rule);
			addNode(node, root, null);
		}
	}
	
	private boolean addNode(TreeNode target, TreeNode node, TreeNode parent) {
		if (node.equals(target)) {
			target.addParent(parent);
			parent.addChild(target);
			return true;
		}
		
		if (node.getRule() == null) {
			boolean match = false;
			for (TreeNode child : node.getChildren()) {
				if (addNode(target, child, node)) {
					match = true;
				}
			}
			if (!match) {
				node.addChild(target);
				target.addParent(node);
			}
			return true;
		}
		
		if (target.contains(node)) {
			node.addParent(target);
			node.getParents().remove(parent);
			target.addChild(node);
			target.addParent(parent);
			parent.getChildren().remove(node);
			parent.addChild(target);
			return true;
		}
		else if (node.contains(target)) {
			boolean match = false;
			for (TreeNode child : node.getChildren()) {
				if (addNode(target, child, node)) {
					match = true;
				}
			}
			if (!match) {
				node.addChild(target);	
				target.addParent(node);
			}
			return true;
		}
		return false;
	}
	
}
