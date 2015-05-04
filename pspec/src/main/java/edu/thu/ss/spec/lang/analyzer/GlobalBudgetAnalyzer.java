package edu.thu.ss.spec.lang.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.GlobalBudget;
import edu.thu.ss.spec.lang.pojo.GlobalBudget.BudgetAllocation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyBudget;
import edu.thu.ss.spec.lang.pojo.UserCategory;

public class GlobalBudgetAnalyzer extends BaseBudgetAnalyzer<GlobalBudget> {

	private static Logger logger = LoggerFactory.getLogger(GlobalBudgetAnalyzer.class);

	private Map<UserCategory, BudgetAllocation> allocations = new HashMap<>();

	public GlobalBudgetAnalyzer() {
		super(GlobalBudget.class);
	}

	@Override
	protected void analyze(GlobalBudget budget, Policy policy) {
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			BudgetAllocation galloc = (BudgetAllocation) alloc;
			allocateBudget(galloc);
		}

		budget.materialize(transform(allocations));
	}

	private Map<UserCategory, Double> transform(Map<UserCategory, BudgetAllocation> allocations) {
		Map<UserCategory, Double> materialized = new HashMap<>();
		for (Entry<UserCategory, BudgetAllocation> e : allocations.entrySet()) {
			materialized.put(e.getKey(), e.getValue().budget);
		}
		return materialized;
	}

	private void allocateBudget(BudgetAllocation alloc) {
		Set<UserCategory> users = alloc.userRef.getMaterialized();
		for (UserCategory user : users) {
			BudgetAllocation allocated = allocations.get(user);
			if (allocated == null) {
				allocations.put(user, alloc);
			} else {
				BudgetAllocation resolved = resolveConflict(user, alloc, allocated);
				allocations.put(user, resolved);
			}
		}
	}

	private BudgetAllocation resolveConflict(UserCategory user, BudgetAllocation alloc1,
			BudgetAllocation alloc2) {
		int ud1 = distance(user, alloc1.userRef.getCategory());
		int ud2 = distance(user, alloc2.userRef.getCategory());
		if (ud1 < ud2) {
			//user takes priority
			return alloc1;
		} else if (ud1 > ud2) {
			return alloc2;
		} else {
			error = true;
			logger.error("cannot specify multiple privacy budgets for user:{}.", user.getId());
			return null;
		}
	}

}
