package edu.thu.ss.xml.analyzer;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.DataCategory;
import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.Desensitization;
import edu.thu.ss.xml.pojo.DesensitizeOperation;
import edu.thu.ss.xml.pojo.Restriction;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryContainer;

public class SyntacticConsistencyChecker extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(SyntacticConsistencyChecker.class);

	@Override
	public boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas) {
		if (rule.getDataRefs().size() == 0 && rule.getAssociations().size() == 0) {
			logger.error("At least one data-category-ref or data-association should appear in rule: {}", rule.getId());
			return true;
		}

		boolean error = false;
		if (rule.getDataRefs().size() > 0) {
			error = checkDataRestriction(rule);
		} else {
			error = checkAssociationRestriction(rule);
		}

		return error;
	}

	@Override
	public boolean stopOnError() {
		return true;
	}

	@Override
	public String errorMsg() {
		return "Error detected when checking consistency of restricted data categories, see error messages above.";
	}

	private boolean checkDataRestriction(Rule rule) {
		List<Restriction> restrictions = rule.getRestrictions();
		if (restrictions.size() > 1) {
			logger.error(
					"Only one restriction should appear in rule when only data categories are referenced by rule: {}",
					rule.getId());
			return true;
		}
		Restriction restriction = restrictions.get(0);
		if (restriction.isForbid()) {
			return false;
		}
		Set<Desensitization> desensitizations = restriction.getDesensitizations();
		if (desensitizations.size() > 1) {
			logger.error(
					"Only one desensitize should appear in rule when only data categories are referenced by rule: {}",
					rule.getId());
			return true;
		}
		boolean error = false;
		Desensitization de = desensitizations.iterator().next();
		for (DataCategoryRef ref : rule.getDataRefs()) {
			error |= checkInclusion(ref.getData(), de.getOperations(), rule.getId());
		}
		return error;
	}

	private boolean checkAssociationRestriction(Rule rule) {
		boolean error = false;
		List<Restriction> restrictions = rule.getRestrictions();
		for (Restriction restriction : restrictions) {
			if (restriction.isForbid()) {
				continue;
			}
			for (Desensitization de : restriction.getDesensitizations()) {
				for (DataCategoryRef ref : de.getDataRefs()) {
					error |= checkInclusion(ref.getCategory(), de.getOperations(), rule.getId());
				}
			}
		}
		return error;
	}

	private boolean checkInclusion(DataCategory data, Set<DesensitizeOperation> operations, String id) {
		boolean error = false;
		for (DesensitizeOperation op : operations) {
			if (!data.getOperations().contains(op)) {
				logger.error("Desensitize operation :{} is not supported by data category: {} in rule: {}",
						op.getUdf(), data.getId(), id);
				error = true;
			}
		}

		return error;

	}
}
