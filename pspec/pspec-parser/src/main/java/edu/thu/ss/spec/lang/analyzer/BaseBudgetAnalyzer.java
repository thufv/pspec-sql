package edu.thu.ss.spec.lang.analyzer;

import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyBudget;
import edu.thu.ss.spec.lang.pojo.PrivacyParams;

public abstract class BaseBudgetAnalyzer<T extends PrivacyBudget<?>> extends BasePolicyAnalyzer {

	protected boolean error = false;

	protected Class<T> target;

	protected BaseBudgetAnalyzer(Class<T> target) {
		this.target = target;
	}

	@Override
	public String errorMsg() {
		return "error occurred when resolving privacy budget allocation, see messages above.";
	}

	@Override
	public boolean stopOnError() {
		return true;
	}

	@Override
	public boolean analyze(Policy policy) {
		PrivacyParams params = policy.getPrivacyParams();
		if(params == null){
			return false;
		}
		PrivacyBudget<?> budget = params.getPrivacyBudget();

		if (target.isInstance(budget)) {
			analyze(target.cast(budget), policy);
		}

		return error;
	}

	protected abstract void analyze(T budget, Policy policy);

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
