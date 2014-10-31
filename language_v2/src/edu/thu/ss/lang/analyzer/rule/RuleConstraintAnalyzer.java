package edu.thu.ss.lang.analyzer.rule;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.DataCategoryContainer;
import edu.thu.ss.lang.pojo.DesensitizeOperation;
import edu.thu.ss.lang.pojo.UserCategoryContainer;
import edu.thu.ss.lang.util.SetUtil;
import edu.thu.ss.lang.xml.XMLDataAssociation;
import edu.thu.ss.lang.xml.XMLDataCategoryRef;
import edu.thu.ss.lang.xml.XMLDesensitization;
import edu.thu.ss.lang.xml.XMLRestriction;
import edu.thu.ss.lang.xml.XMLRule;

public class RuleConstraintAnalyzer extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleConstraintAnalyzer.class);
	private String ruleId;

	@Override
	public boolean analyzeRule(XMLRule rule, UserCategoryContainer users, DataCategoryContainer datas) {
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
	private boolean checkAssociations(XMLRule rule) {
		boolean error = false;
		Set<XMLDataAssociation> associations = rule.getAssociations();
		for (XMLDataAssociation association : associations) {
			error |= checkAssociaiton(association);
		}
		return error;
	}

	private boolean checkAssociaiton(XMLDataAssociation association) {
		boolean error = false;
		Set<XMLDataCategoryRef> set = association.getDataRefs();

		XMLDataCategoryRef[] refs = set.toArray(new XMLDataCategoryRef[set.size()]);
		for (int i = 0; i < refs.length; i++) {
			XMLDataCategoryRef ref1 = refs[i];
			for (int j = i + 1; j < refs.length; j++) {
				XMLDataCategoryRef ref2 = refs[j];
				if (SetUtil.intersects(ref1.getMaterialized(), ref2.getMaterialized())) {
					logger.error("Overlap of data category: {} and {} detected in data association in rule: {}.",
							ref1.getRefid(), ref2.getRefid(), ruleId);
					error = true;
				}
			}
		}
		return error;
	}

	private boolean checkDataRestriction(XMLRule rule) {
		List<XMLRestriction> restrictions = rule.getRestrictions();
		if (restrictions.size() > 1) {
			logger.error(
					"Only one restriction should appear in rule when only data categories are referenced by rule: {}",
					rule.getId());
			return true;
		}
		XMLRestriction restriction = restrictions.get(0);
		if (restriction.isForbid()) {
			return false;
		}
		Set<XMLDesensitization> desensitizations = restriction.getDesensitizations();
		if (desensitizations.size() > 1) {
			logger.error(
					"Only one desensitize should appear in rule when only data categories are referenced by rule: {}",
					rule.getId());
			return true;
		}
		boolean error = false;
		XMLDesensitization de = desensitizations.iterator().next();
		for (XMLDataCategoryRef ref : rule.getDataRefs()) {
			error |= checkInclusion(ref.getData(), de.getOperations());
		}
		return error;
	}

	private boolean checkAssociationRestriction(XMLRule rule) {
		boolean error = false;
		List<XMLRestriction> restrictions = rule.getRestrictions();
		for (XMLRestriction restriction : restrictions) {
			if (restriction.isForbid()) {
				continue;
			}
			for (XMLDesensitization de : restriction.getDesensitizations()) {
				for (XMLDataCategoryRef ref : de.getDataRefs()) {
					error |= checkInclusion(ref.getCategory(), de.getOperations());
				}
			}
		}
		return error;
	}

	private boolean checkInclusion(DataCategory data, Set<DesensitizeOperation> operations) {
		boolean error = false;
		if (operations == null) {
			return error;
		}
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
