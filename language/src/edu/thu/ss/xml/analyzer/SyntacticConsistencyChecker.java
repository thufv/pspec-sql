package edu.thu.ss.xml.analyzer;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.DataAssociation;
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
			logger.error("At least one data-category-ref or data-association should appear in rule: " + rule.getId());
			return true;
		}
		if (rule.getRestriction() == null) {
			return false;
		}
		// restriction should contain no data ref.
		if (rule.getDataRefs().size() > 0) {
			return checkRestrictedCategory(rule);
		} else {
			return checkRestrictedAssociation(rule);
		}
	}

	@Override
	public boolean stopOnError() {
		return false;
	}

	@Override
	public String errorMsg() {
		return "Error detected when checking consistency of restricted data categories, see error messages above.";
	}

	private boolean checkRestrictedCategory(Rule rule) {
		Restriction restriction = rule.getRestriction();
		if (restriction.getDesensitizations().size() > 1) {
			logger.error("Only one desensitize element should appear when only data category is referenced in rule: "
					+ rule.getId());
			return true;
		}
		Desensitization de = restriction.getDesensitizations().iterator().next();
		if (!de.isForAllDataCategory()) {
			logger.error("No data-category-ref element should appear in desensitize element when only data category is referenced in rule: "
					+ rule.getId());
			return true;
		}
		boolean error = false;
		for (DataCategoryRef dataRef : rule.getDataRefs()) {
			error = error || checkInclusion(dataRef.getData(), de.getOperations(), rule.getId());
		}
		return error;
	}

	private boolean checkRestrictedAssociation(Rule rule) {
		Restriction restriction = rule.getRestriction();
		boolean error = false;
		for (Desensitization de : restriction.getDesensitizations()) {
			if (de.getDataRefs().size() == 0) {
				logger.error("Restricted data category must be specified explicitly when data association is referenced by rule: "
						+ rule.getId());
				return true;
			}
			DataAssociation association = rule.getAssociations().iterator().next();
			for (DataCategoryRef dataRef : de.getDataRefs()) {
				if (!association.getDataRefs().contains(dataRef)) {
					logger.error("Restricted data category: " + dataRef.getRefid()
							+ " does not appear in data association of rule: " + rule.getId());
					error = true;
				}
			}
			if (error) {
				continue;
			}
			for (DataCategoryRef dataRef : de.getDataRefs()) {
				error = error || checkInclusion(dataRef.getData(), de.getOperations(), rule.getId());

			}
		}
		return error;
	}

	private boolean checkInclusion(DataCategory data, Set<DesensitizeOperation> operations, String id) {
		boolean error = false;
		for (DesensitizeOperation op : operations) {
			if (!data.getOperations().contains(op)) {
				logger.error("Desensitize operation : " + op.getUdf() + " is not supported by data category: "
						+ data.getId() + " in rule: " + id);
				error = true;
			}
		}

		return error;

	}
}
