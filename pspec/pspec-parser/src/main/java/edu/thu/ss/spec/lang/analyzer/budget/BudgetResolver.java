package edu.thu.ss.spec.lang.analyzer.budget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener;
import edu.thu.ss.spec.lang.pojo.CategoryRef;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.FineBudget;
import edu.thu.ss.spec.lang.pojo.GlobalBudget;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyBudget;
import edu.thu.ss.spec.lang.pojo.PrivacyParams;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.PSpecUtil;

public class BudgetResolver extends BasePolicyAnalyzer {

	public BudgetResolver(EventTable table) {
		super(table);
	}

	private static Logger logger = LoggerFactory.getLogger(BudgetResolver.class);

	public boolean analyze(Policy policy) {
		PSpecListener listener = new PSpecListener() {
			@Override
			public void onRuleRefError(RefErrorType type, Rule rule, CategoryRef<?> ref, String refid) {
				if (type.equals(RefErrorType.Category_Ref_Not_Exist)) {
					if (ref instanceof UserRef) {
						logger.error("Fail to locate user category: {} in budget allocation", refid);
					} else {
						logger.error("Fail to locate data category: {} in budget allocation", refid);
					}
				}
			}

		};
		table.add(listener);

		boolean error = false;
		PrivacyParams params = policy.getPrivacyParams();
		if (params == null) {
			return error;
		}
		PrivacyBudget<?> budget = params.getPrivacyBudget();
		if (budget.isGlobal()) {
			error = error
					|| resolveGlobalBudget((GlobalBudget) budget, policy.getUserContainer(),
							policy.getDataContainer());
		} else {
			error = error
					|| resolveFineBudget((FineBudget) budget, policy.getUserContainer(),
							policy.getDataContainer());
		}
		table.remove(listener);
		return error;
	}

	private boolean resolveGlobalBudget(GlobalBudget budget, UserContainer users, DataContainer datas) {
		boolean error = false;
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			GlobalBudget.BudgetAllocation galloc = (GlobalBudget.BudgetAllocation) alloc;
			error = error || PSpecUtil.resolveCategoryRef(galloc.userRef, users, null, false, table);
		}
		return error;
	}

	private boolean resolveFineBudget(FineBudget budget, UserContainer users, DataContainer datas) {
		boolean error = false;
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			FineBudget.BudgetAllocation falloc = (FineBudget.BudgetAllocation) alloc;
			error = error || PSpecUtil.resolveCategoryRef(falloc.userRef, users, null, false, table);
			error = error || PSpecUtil.resolveCategoryRef(falloc.dataRef, datas, null, false, table);
		}
		return error;
	}

	@Override
	public String errorMsg() {
		return "Error detected when resolving category references in rules, see error messages above.";
	}

}
