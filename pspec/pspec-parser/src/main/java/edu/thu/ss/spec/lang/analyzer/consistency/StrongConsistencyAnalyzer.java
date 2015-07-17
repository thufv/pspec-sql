package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.PSpecUtil.SetRelation;

public class StrongConsistencyAnalyzer extends ConsistencyAnalyzer {
	
	private StrongConsistencySearcher searcher;
	
	private int conflicts = 0;
	
	public StrongConsistencyAnalyzer(EventTable table) {
		super(table);
		searcher = new StrongConsistencySearcher(table);
	}
	
	@Override
	public boolean analyze(List<ExpandedRule> rules) {
		for (ExpandedRule rule : rules) {
			analyzeWithSeed(rule, rules);
		}
		System.out.println("Find conflicts: " + conflicts);
		return false;
	}
	
	public void analyzeWithSeed(ExpandedRule seed, List<ExpandedRule> rules) {
		List<ExpandedRule> candidates =  getCandidateRules(seed, rules);
		searcher.init(seed, candidates);
		searcher.search();
		conflicts += searcher.conflicts;
	}

	private List<ExpandedRule> getCandidateRules(ExpandedRule seed,	List<ExpandedRule> rules) {
		List<ExpandedRule> candidates = new ArrayList<>();
		for (ExpandedRule rule : rules) {
			if (rule.equals(seed)) {
				continue;
			}
			else if (checkCandidate(rule, seed)) {
				candidates.add(rule);
			}
		}
		return candidates;
	}

	private boolean checkCandidate(ExpandedRule rule, ExpandedRule seed) {
		Set<UserCategory> user1 = rule.getUsers();
		Set<UserCategory> user2 = seed.getUsers();
		if (PSpecUtil.relation(user1, user2).equals(SetRelation.disjoint)) {
			return false;
		}
		
		if (rule.isSingle()) {
			return checkSingleCandidate(rule, seed);
		}
		else if (rule.isAssociation()) {
			return checkAssociationCandidate(rule, seed);
		}
		return false;
	}

	private boolean checkSingleCandidate(ExpandedRule rule, ExpandedRule seed) {
		if (seed.isSingle()) {
			return checkBothSingleCandidate(rule, seed);
		}
		else if (seed.isAssociation()) {
			return checkSingleAssociationCandidate(rule, seed);
		}
		return false;
	}


	private boolean checkAssociationCandidate(ExpandedRule rule, ExpandedRule seed) {
		if (seed.isSingle()) {
			return false;
		}
		else if (seed.isAssociation()) {
			return checkBothAssociationCandidate(rule, seed);
		}
		return false;
	}
	
	private boolean checkBothSingleCandidate(ExpandedRule rule, ExpandedRule seed) {
		DataRef dataRef1 = rule.getDataRef();
		DataRef dataRef2 = seed.getDataRef();
		
		
		SetRelation DataRelation = PSpecUtil.relation(dataRef1.getMaterialized(), dataRef2.getMaterialized());
		if (DataRelation.equals(SetRelation.disjoint)) {
			return false;
		}
		
		if (!checkActionDisjoint(dataRef1.getAction(), dataRef2.getAction())) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkSingleAssociationCandidate(ExpandedRule rule, ExpandedRule seed) {
		DataRef dataRef1 = rule.getDataRef();
		List<DataRef> dataRefs = seed.getAssociation().getDataRefs();
			for (DataRef dataRef2 : dataRefs) {
				if (!checkActionDisjoint(dataRef1.getAction(), dataRef2.getAction())) {
					return false;
				}
				SetRelation relation = PSpecUtil.relation(dataRef1.getMaterialized(), dataRef2.getMaterialized());
				if (!relation.equals(SetRelation.disjoint)) {
					return true;
				}
			}
		return false;
	}
 
	private boolean checkBothAssociationCandidate(ExpandedRule rule, ExpandedRule seed) {
		if (seed.getDimension() < rule.getDimension()) {
			return false;
		}
		
		List<DataRef> ruleDataRefs = rule.getAssociation().getDataRefs();
		List<DataRef> seedDataRefs = seed.getAssociation().getDataRefs();
		
		boolean[] matches = new boolean[seed.getDimension()];
		for (int i = 0; i < matches.length; i++) {
			matches[i] = false;
		}
		return checkBothAssociationCandidate(0, rule.getDimension(), ruleDataRefs, seedDataRefs, matches);
	}
	
	private boolean checkBothAssociationCandidate(int index, int dim, List<DataRef> ruleDataRefs,
			List<DataRef> seedDataRefs, boolean[] matches) {
		if (index == dim) {
			return true;
		}
		else {
			DataRef dataRef1 = ruleDataRefs.get(index);
			for (int i = 0; i < seedDataRefs.size(); i++) {
				DataRef dataRef2 = seedDataRefs.get(i);
				if (!checkActionDisjoint(dataRef1.getAction(), dataRef2.getAction())) {
					return false;
				}
				SetRelation relation = PSpecUtil.relation(dataRef1.getMaterialized(), dataRef2.getMaterialized());
				if (!relation.equals(SetRelation.disjoint)) {
					matches[i] = true;
					if (checkBothAssociationCandidate(index + 1, dim, ruleDataRefs, seedDataRefs, matches)) {
						return true;
					}
					else {
						matches[i] = false;
					}
				}
			}
		}
		return false;
	}
	
	private boolean checkActionDisjoint(Action action1, Action action2) {
		if (action1.ancestorOf(action2) || action2.ancestorOf(action1)) {
			return true;
		}
		return false;
	}
}
