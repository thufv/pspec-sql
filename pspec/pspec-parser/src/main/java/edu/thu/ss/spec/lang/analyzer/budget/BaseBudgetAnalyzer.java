package edu.thu.ss.spec.lang.analyzer.budget;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyBudget;
import edu.thu.ss.spec.lang.pojo.PrivacyParams;

public abstract class BaseBudgetAnalyzer<T extends PrivacyBudget<?>> extends BasePolicyAnalyzer {

	protected Class<T> target;

	protected BaseBudgetAnalyzer(Class<T> target) {
		super(null);
		this.target = target;
	}

	@Override
	public String errorMsg() {
		return "error occurred when resolving privacy budget allocation, see messages above.";
	}

	@Override
	public boolean analyze(Policy policy) {
		boolean error = false;
		PrivacyParams params = policy.getPrivacyParams();
		if (params == null) {
			return false;
		}
		PrivacyBudget<?> budget = params.getPrivacyBudget();

		if (target.isInstance(budget)) {
			error = error || analyze(target.cast(budget), policy);
		}

		return error;
	}

	protected abstract boolean analyze(T budget, Policy policy);

	protected int distance(Category<? extends Category<?>> child,
			Category<? extends Category<?>> parent) {
		int distance = 0;
		Category<? extends Category<?>> current = child;
		while (!current.equals(parent)) {
			distance++;
			current = current.getParent();
		}
		return distance;

	}
}
