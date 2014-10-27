package edu.thu.ss.lang.analyzer;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.lang.pojo.DataAssociation;
import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.DataCategoryContainer;
import edu.thu.ss.lang.pojo.DataCategoryRef;
import edu.thu.ss.lang.pojo.Desensitization;
import edu.thu.ss.lang.pojo.DesensitizeOperation;
import edu.thu.ss.lang.pojo.Restriction;
import edu.thu.ss.lang.pojo.Rule;
import edu.thu.ss.lang.pojo.UserCategoryContainer;
import edu.thu.ss.lang.util.SetUtil;

public class ConstraintChecker extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(ConstraintChecker.class);
	private String ruleId;

	@Override
	public boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas) {
		if (rule.getDataRefs().size() == 0 && rule.getAssociations().size() == 0) {
			logger.error("At least one data-category-ref or data-association should appear in rule: {}", rule.getId());
			return true;
		}
		this.ruleId = rule.getId();
		boolean error = false;

		error |= checkAssociations(rule);

		if (rule.getDataRefs().size() > 0) {
			error |= checkDataRestriction(rule);
		} else {
			error |= checkAssociationRestriction(rule);
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

	/**
	 * No overlap of data categories in each data association.
	 * 
	 * @param rule
	 * @return
	 */
	private boolean checkAssociations(Rule rule) {
		boolean error = false;
		Set<DataAssociation> associations = rule.getAssociations();
		for (DataAssociation association : associations) {
			error |= checkAssociaiton(association);
		}
		return error;
	}

	private boolean checkAssociaiton(DataAssociation association) {
		boolean error = false;
		Set<DataCategoryRef> set = association.getDataRefs();

		DataCategoryRef[] refs = set.toArray(new DataCategoryRef[set.size()]);
		for (int i = 0; i < refs.length; i++) {
			DataCategoryRef ref1 = refs[i];
			for (int j = i + 1; j < refs.length; j++) {
				DataCategoryRef ref2 = refs[j];
				if (SetUtil.intersects(ref1.getMaterialized(), ref2.getMaterialized())) {
					logger.error("Overlap of data category: {} and {} detected in data association in rule: {}.",
							ref1.getRefid(), ref2.getRefid(), ruleId);
					error = true;
				}
			}
		}
		return error;
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
			error |= checkInclusion(ref.getData(), de.getOperations());
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
					error |= checkInclusion(ref.getCategory(), de.getOperations());
				}
			}
		}
		return error;
	}

	private boolean checkInclusion(DataCategory data, Set<DesensitizeOperation> operations) {
		boolean error = false;
		for (DesensitizeOperation op : operations) {
			if (!data.getOperations().contains(op)) {
				logger.error("Desensitize operation: {} is not supported by data category: {} in rule: {}",
						op.getUdf(), data.getId(), ruleId);
				error = true;
			}
		}

		return error;

	}
}
