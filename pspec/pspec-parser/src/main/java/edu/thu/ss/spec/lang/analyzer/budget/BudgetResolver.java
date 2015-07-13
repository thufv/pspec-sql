package edu.thu.ss.spec.lang.analyzer.budget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.parser.PSpec.PSpecEventType;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.ParseListener;
import edu.thu.ss.spec.lang.parser.event.PolicyEvent;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.FineBudget;
import edu.thu.ss.spec.lang.pojo.GlobalBudget;
import edu.thu.ss.spec.lang.pojo.ObjectRef;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyBudget;
import edu.thu.ss.spec.lang.pojo.PrivacyParams;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.PSpecUtil;

public class BudgetResolver extends BasePolicyAnalyzer {

	public BudgetResolver(EventTable<PolicyEvent> table) {
		super(table);
	}

	private static Logger logger = LoggerFactory.getLogger(BudgetResolver.class);

	public boolean analyze(Policy policy) {
		ParseListener<PolicyEvent> listener = new ParseListener<PolicyEvent>() {
			@Override
			public void handleEvent(PolicyEvent e) {
				assert (e.data.length == 2);
				ObjectRef ref = (ObjectRef) e.data[0];
				if (ref instanceof UserRef) {
					logger.error("Fail to locate user category: {} in budget allocation", e.data[1]);
				} else {
					logger.error("Fail to locate data category: {} in budget allocation", e.data[1]);
				}
			}
		};
		table.hook(PSpecEventType.Policy_Category_Ref_Not_Exist, listener);

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

		table.unhook(PSpecEventType.Policy_Category_Ref_Not_Exist, listener);
		return error;
	}

	private boolean resolveGlobalBudget(GlobalBudget budget, UserContainer users, DataContainer datas) {
		boolean error = false;
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			GlobalBudget.BudgetAllocation galloc = (GlobalBudget.BudgetAllocation) alloc;
			error = error || PSpecUtil.resolveCategoryRef(galloc.userRef, users, false, table);
		}
		return error;
	}

	private boolean resolveFineBudget(FineBudget budget, UserContainer users, DataContainer datas) {
		boolean error = false;
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			FineBudget.BudgetAllocation falloc = (FineBudget.BudgetAllocation) alloc;
			error = error || PSpecUtil.resolveCategoryRef(falloc.userRef, users, false, table);
			error = error || PSpecUtil.resolveCategoryRef(falloc.dataRef, datas, false, table);
		}
		return error;
	}

	@Override
	public String errorMsg() {
		return "Error detected when resolving category references in rules, see error messages above.";
	}

}
