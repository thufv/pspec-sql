package edu.thu.ss.spec.lang.analyzer.redundancy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.PSpecUtil.SetRelation;

public class ScopeAnalyzer extends BaseRedundancyAnalyzer {

	public enum ScopeRelation {
		contain, equal, free
	}

	private Map<String, List<String>> equalRules = new HashMap<>();
	private DirectedAcyclicGraph<String, ?> graph;

	public ScopeAnalyzer() {
		super(null);
		this.instance = InclusionUtil.instance;
	}

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> rules = policy.getExpandedRules();
		filterEqualScopeRules(rules);
		generateDAG(rules);
		return false;
	}

	public boolean containsEdge(String source, String target) {
		if (graph.containsEdge(source, target)) {
			return true;
		}
		List<String> list;
		list = equalRules.get(source);
		if (list != null && list.contains(target)) {
			return true;
		}
		list = equalRules.get(target);
		if (list != null && list.contains(source)) {
			return true;
		}
		return false;
	}

	private void filterEqualScopeRules(List<ExpandedRule> rules) {
		for (int i = 0; i < rules.size(); i++) {
			ExpandedRule target = rules.get(i);
			Iterator<ExpandedRule> it = rules.iterator();
			while (it.hasNext()) {
				ExpandedRule rule = (ExpandedRule) it.next();
				if (rule.equals(target)) {
					while (it.hasNext()) {
						rule = (ExpandedRule) it.next();
						ScopeRelation relation = checkScopeRelation(target, rule);
						if (relation.equals(ScopeRelation.equal)) {
							List<String> list = equalRules.get(target.getRuleId());
							if (list == null) {
								list = new ArrayList<>();
								list.add(rule.getRuleId());
								equalRules.put(target.getRuleId(), list);
							} else {
								list.add(rule.getRuleId());
							}
							it.remove();
						}
					}
				}
			}
		}
	}

	private void generateDAG(List<ExpandedRule> rules) {
		graph = new DirectedAcyclicGraph(DefaultEdge.class);
		for (int i = 0; i < rules.size(); i++) {
			graph.addVertex(rules.get(i).getRuleId());
		}
		for (int i = 0; i < rules.size(); i++) {
			ExpandedRule rule1 = rules.get(i);
			for (int j = 0; j < rules.size(); j++) {
				if (i == j) {
					continue;
				}
				ExpandedRule rule2 = rules.get(j);
				ScopeRelation relation = checkScopeRelation(rule1, rule2);
				if (relation.equals(ScopeRelation.contain)) {
					graph.addEdge(rule1.getRuleId(), rule2.getRuleId());
				}
			}
		}

		TransitiveReduction.prune(graph);
	}

	private ScopeRelation checkScopeRelation(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule2.isSingle()) {
			return checkSingle(rule1, rule2);
		} else {
			return checkAssociation(rule1, rule2);
		}
	}

	private ScopeRelation checkSingle(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isAssociation()) {
			return ScopeRelation.free;
		}

		DataRef ref1 = rule1.getDataRef();
		DataRef ref2 = rule2.getDataRef();

		Action action1 = ref1.getAction();
		Action action2 = ref2.getAction();
		if (!instance.includes(action1, action2)) {
			return ScopeRelation.free;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		SetRelation userRelation = PSpecUtil.relation(user1, user2);
		if (userRelation.equals(SetRelation.disjoint)) {
			return ScopeRelation.free;
		}

		Set<DataCategory> data1 = ref1.getMaterialized();
		Set<DataCategory> data2 = ref2.getMaterialized();
		SetRelation dataRelation = PSpecUtil.relation(data1, data2);
		if (dataRelation.equals(SetRelation.disjoint)) {
			return ScopeRelation.free;
		}

		if (action1.equals(action2) && user1.equals(user2) && data1.equals(data2)) {
			return ScopeRelation.equal;
		}
		if (userRelation.equals(SetRelation.contain) && dataRelation.equals(SetRelation.contain)) {
			return ScopeRelation.contain;
		} else {
			return ScopeRelation.free;
		}
	}

	private ScopeRelation checkAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isSingle()) {
			return checkSingleAssociation(rule1, rule2);
		} else {
			return checkBothAssociation(rule1, rule2);
		}
	}

	private ScopeRelation checkSingleAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();

		if (!PSpecUtil.contains(user1, user2)) {
			return ScopeRelation.free;
		}

		DataRef ref1 = rule1.getDataRef();
		DataAssociation assoc2 = rule2.getAssociation();

		boolean match = false;
		List<DataRef> dataRefs = assoc2.getDataRefs();
		for (int i = 0; i < dataRefs.size(); i++) {
			DataRef ref2 = dataRefs.get(i);
			if (instance.includes(ref1, ref2)) {
				match = true;
			}
		}
		if (!match) {
			return ScopeRelation.free;
		}
		return ScopeRelation.contain;
	}

	private ScopeRelation checkBothAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		DataAssociation assoc1 = rule1.getAssociation();
		DataAssociation assoc2 = rule2.getAssociation();

		if (assoc1.getDimension() > assoc2.getDimension()) {
			return ScopeRelation.free;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		if (!PSpecUtil.contains(user1, user2)) {
			return ScopeRelation.free;
		}

		List<DataRef> dataRefs1 = assoc1.getDataRefs();
		List<DataRef> dataRefs2 = assoc2.getDataRefs();

		boolean equal = true;
		for (int i = 0; i < dataRefs1.size(); i++) {
			boolean match = false;
			DataRef ref1 = dataRefs1.get(i);
			for (int j = 0; j < dataRefs2.size(); j++) {
				DataRef ref2 = dataRefs2.get(j);
				if (instance.includes(ref1, ref2)) {
					match = true;
				}
				if (equal && ref1.getAction().equals(ref2.getAction())
						&& ref1.getMaterialized().equals(ref2.getMaterialized())) {
					equal = true;
				} else {
					equal = false;
				}
			}
			if (!match) {
				return ScopeRelation.free;
			}
		}

		if (equal && user1.equals(user2)) {
			return ScopeRelation.equal;
		}
		return ScopeRelation.contain;
	}
}
