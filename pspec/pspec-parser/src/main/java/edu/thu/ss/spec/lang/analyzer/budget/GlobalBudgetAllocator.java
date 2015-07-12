package edu.thu.ss.spec.lang.analyzer.budget;

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

public class GlobalBudgetAllocator extends BaseBudgetAnalyzer<GlobalBudget> {

	private static Logger logger = LoggerFactory.getLogger(GlobalBudgetAllocator.class);

	private Map<UserCategory, BudgetAllocation> allocations = new HashMap<>();

	public GlobalBudgetAllocator() {
		super(GlobalBudget.class);
	}

	@Override
	protected boolean analyze(GlobalBudget budget, Policy policy) {
		boolean error = false;
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			BudgetAllocation galloc = (BudgetAllocation) alloc;
			error = error || allocateBudget(galloc);
		}

		budget.materialize(transform(allocations));
		return error;
	}

	private boolean allocateBudget(BudgetAllocation alloc) {
		boolean error = false;
		Set<UserCategory> users = alloc.userRef.getMaterialized();
		for (UserCategory user : users) {
			BudgetAllocation allocated = allocations.get(user);
			if (allocated == null) {
				allocations.put(user, alloc);
			} else {
				BudgetAllocation resolved = resolveConflict(user, alloc, allocated);
				allocations.put(user, resolved);
				if (resolved == null) {
					error = true;
				}
			}
		}
		return error;
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
			logger.error("cannot specify multiple privacy budgets for user:{}.", user.getId());
			return null;
		}
	}

	private Map<UserCategory, Double> transform(Map<UserCategory, BudgetAllocation> allocations) {
		Map<UserCategory, Double> materialized = new HashMap<>();
		for (Entry<UserCategory, BudgetAllocation> e : allocations.entrySet()) {
			materialized.put(e.getKey(), e.getValue().budget);
		}
		return materialized;
	}

}
