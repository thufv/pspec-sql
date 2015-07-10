package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.util.PSpecUtil;

/**
 * check rule constraints after rule is parsed.
 * @author luochen
 *
 */
public class RuleConstraintAnalyzer extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleConstraintAnalyzer.class);
	private String ruleId;
	private boolean error = false;;

	@Override
	public boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas) {
		error = false;

		if (rule.getDataRefs().size() == 0 && rule.getAssociation() == null) {
			logger.error("At least one data-category-ref or data-association should appear in rule: {}",
					rule.getId());
			return true;
		}
		this.ruleId = rule.getId();

		checkAssociaiton(rule);

		if (rule.getDataRefs().size() > 0) {
			checkSingleRestirction(rule);
		} else {
			checkAssociationRestriction(rule);
		}

		return error;
	}

	@Override
	public boolean stopOnError() {
		return true;
	}

	@Override
	public String errorMsg() {
		return "Error detected when checking constraints of restricted data categories, see error messages above.";
	}

	private void checkAssociaiton(Rule rule) {
		DataAssociation association = rule.getAssociation();
		if (association == null) {
			return;
		}
		List<DataRef> set = association.getDataRefs();

		DataRef[] refs = set.toArray(new DataRef[set.size()]);
		for (int i = 0; i < refs.length; i++) {
			DataRef ref1 = refs[i];
			for (int j = i + 1; j < refs.length; j++) {
				DataRef ref2 = refs[j];
				if (PSpecUtil.bottom(ref1.getAction(), ref2.getAction()) != null
						&& PSpecUtil.intersects(ref1.getMaterialized(), ref2.getMaterialized())) {
					logger.error(
							"Overlap of data category: {} and {} detected in data association in rule: {}.",
							ref1.getRefid(), ref2.getRefid(), ruleId);
					error = true;
				}
			}
		}
	}

	private void checkSingleRestirction(Rule rule) {
		List<Restriction> restrictions = rule.getRestrictions();
		if (restrictions.size() > 1) {
			logger
					.error(
							"Only one restriction should appear in rule when only data categories are referenced by rule: {}",
							rule.getId());
			error = true;
			return;
		}
		Restriction restriction = restrictions.get(0);
		if (restriction.isForbid()) {
			return;
		}

		Desensitization de = restriction.getDesensitization(0);
		for (DataRef ref : rule.getDataRefs()) {
			checkInclusion(ref.getData(), de.getOperations());
		}
	}

	private void checkAssociationRestriction(Rule rule) {
		List<Restriction> restrictions = rule.getRestrictions();
		for (Restriction restriction : restrictions) {
			if (restriction.isForbid()) {
				continue;
			}
			for (Desensitization de : restriction.getDesensitizations()) {
				if (de.effective()) {
					DataRef ref = de.getDataRef();
					checkInclusion(ref.getCategory(), de.getOperations());
				}
			}
		}
	}

	private void checkInclusion(DataCategory data, Set<DesensitizeOperation> operations) {
		if (operations == null) {
			return;
		}
		for (DesensitizeOperation op : operations) {
			if (!data.support(op)) {
				logger.error("Desensitize operation: {} is not supported by data category: {} in rule: {}",
						op.getName(), data.getId(), ruleId);
				error = true;
			}
		}

	}
}
