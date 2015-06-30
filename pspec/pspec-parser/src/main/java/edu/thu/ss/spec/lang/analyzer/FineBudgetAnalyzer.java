package edu.thu.ss.spec.lang.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.FineBudget;
import edu.thu.ss.spec.lang.pojo.FineBudget.BudgetAllocation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyBudget;
import edu.thu.ss.spec.lang.pojo.UserCategory;

public class FineBudgetAnalyzer extends BaseBudgetAnalyzer<FineBudget> {

	private static Logger logger = LoggerFactory.getLogger(FineBudgetAnalyzer.class);

	private Map<UserCategory, Map<DataCategory, BudgetAllocation>> allocations = new HashMap<>();

	public FineBudgetAnalyzer() {
		super(FineBudget.class);
	}

	@Override
	protected void analyze(FineBudget budget, Policy policy) {
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			BudgetAllocation falloc = (BudgetAllocation) alloc;
			allocateBudget(falloc);
		}

		budget.materialize(transform(allocations));
	}

	private Map<UserCategory, Map<DataCategory, Double>> transform(
			Map<UserCategory, Map<DataCategory, BudgetAllocation>> allocations) {
		Map<UserCategory, Map<DataCategory, Double>> materialized = new HashMap<>();
		for (Entry<UserCategory, Map<DataCategory, BudgetAllocation>> e : allocations.entrySet()) {
			Map<DataCategory, Double> map = new HashMap<>();
			for (Entry<DataCategory, BudgetAllocation> de : e.getValue().entrySet()) {
				map.put(de.getKey(), de.getValue().budget);
			}
			materialized.put(e.getKey(), map);
		}
		return materialized;
	}

	private void allocateBudget(BudgetAllocation alloc) {
		Set<UserCategory> users = alloc.userRef.getMaterialized();
		Set<DataCategory> datas = alloc.dataRef.getMaterialized();
		for (UserCategory user : users) {
			for (DataCategory data : datas) {
				BudgetAllocation allocated = getAllocation(user, data);
				if (allocated == null) {
					allocate(user, data, alloc);
				} else {
					BudgetAllocation resolved = resolveConflict(user, data, alloc, allocated);
					allocate(user, data, resolved);
				}
			}
		}
	}

	private BudgetAllocation resolveConflict(UserCategory user, DataCategory data,
			BudgetAllocation alloc1, BudgetAllocation alloc2) {
		int ud1 = distance(user, alloc1.userRef.getCategory());
		int ud2 = distance(user, alloc2.userRef.getCategory());
		int dd1 = distance(data, alloc1.dataRef.getCategory());
		int dd2 = distance(data, alloc2.dataRef.getCategory());
		if (ud1 < ud2) {
			//user takes priority
			return alloc1;
		} else if (ud1 > ud2) {
			return alloc2;
		} else {
			if (dd1 == dd2) {
				error = true;
				logger.error("cannot specify multiple privacy budgets for user:{} and data:{}.",
						user.getId(), data.getId());
				return null;
			}
			return dd1 < dd2 ? alloc1 : alloc2;
		}

	}

	private BudgetAllocation getAllocation(UserCategory user, DataCategory data) {
		Map<DataCategory, BudgetAllocation> map = allocations.get(user);
		if (map == null) {
			map = new HashMap<>();
			allocations.put(user, map);
		}
		return map.get(allocations);
	}

	private void allocate(UserCategory user, DataCategory data, BudgetAllocation allocation) {
		Map<DataCategory, BudgetAllocation> map = allocations.get(user);
		map.put(data, allocation);
	}

	

}
