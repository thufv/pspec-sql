package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PrivacyBudget<T> implements Parsable {

	public static abstract class BudgetAllocation {
		private static int unused = 0;

		private int id = unused++;

		public UserRef userRef;

		protected BudgetAllocation(UserRef userRef) {
			this.userRef = userRef;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BudgetAllocation other = (BudgetAllocation) obj;
			if (id != other.id)
				return false;
			return true;
		}

	}

	//should be initialized by child
	protected Map<UserCategory, T> budgets;

	protected List<BudgetAllocation> allocations = new ArrayList<>();

	public T getBudget(UserCategory user) {
		return budgets.get(user);
	}

	public List<BudgetAllocation> getAllocations() {
		return allocations;
	}

	public void materialize(Map<UserCategory, T> budgets) {
		this.budgets = budgets;
	}

	public abstract boolean isGlobal();
}
