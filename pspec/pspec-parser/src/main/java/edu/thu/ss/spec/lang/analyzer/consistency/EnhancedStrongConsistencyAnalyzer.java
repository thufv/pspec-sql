package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.PSpecUtil.SetRelation;

public class EnhancedStrongConsistencyAnalyzer extends ConsistencyAnalyzer {

	private InclusionUtil instance;
	private EnhancedStrongConsistencySearcher searcher;
	private int conflicts = 0;
	
	public EnhancedStrongConsistencyAnalyzer(EventTable table) {
		super(table);
		instance = InclusionUtil.instance;
		searcher = new EnhancedStrongConsistencySearcher();
	}
	
	@Override
	public boolean analyze(List<ExpandedRule> rules) {
		for (int i = 0; i < rules.size(); i++) {
			analyzeWithSeed(rules.get(i), rules);
		}
		System.out.println("Find conflicts: " + conflicts);
		return false;
	}
	
	private void analyzeWithSeed(ExpandedRule seed, List<ExpandedRule> rules) {
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
		Set<UserCategory> user1 = seed.getUsers();
		Set<UserCategory> user2 = rule.getUsers();
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
			return checkBothAssociationCandidate(seed, rule);
		}
		return false;
	}
	
	private boolean checkBothSingleCandidate(ExpandedRule rule, ExpandedRule seed) {
		DataRef dataRef1 = rule.getDataRef();
		DataRef dataRef2 = seed.getDataRef();
		if (instance.includes(dataRef1, dataRef2)) {
			return true;
		}
		return false;
	}
	
	private boolean checkSingleAssociationCandidate(ExpandedRule rule, ExpandedRule seed) {
		DataRef dataRef1 = rule.getDataRef();
		List<DataRef> dataRefs = seed.getAssociation().getDataRefs();
		for (DataRef dataRef2 : dataRefs) {
			if (instance.includes(dataRef1, dataRef2)) {
				return true;
			}
		}
		return false;
	}
 
	private boolean checkBothAssociationCandidate(ExpandedRule rule, ExpandedRule seed) {
		if (seed.getDimension() < rule.getDimension()) {
			return false;
		}
		
		DataAssociation assoc1 = rule.getAssociation();
		DataAssociation assoc2 = seed.getAssociation();
		
		boolean[] covered = new boolean[seed.getDimension()];
		Arrays.fill(covered, false);
		for (DataRef dataRef1 : assoc1.getDataRefs()) {
			boolean match = false;
			for (int i = 0; i < assoc2.getDataRefs().size(); i++) {
				if (covered[i]) {
					continue;
				}
				DataRef dataRef2 = assoc2.getDataRefs().get(i);
				if (instance.includes(dataRef1, dataRef2)) {
					match = true;
					covered[i] = true;
				}
			}
			if (!match) {
				return false;
			}
		}
		return true;
	}
	

}
