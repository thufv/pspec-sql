package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

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
import edu.thu.ss.spec.util.SetUtil;

/**
 * check rule constraints after rule is parsed.
 * @author luochen
 *
 */
public class RuleConstraintAnalyzer extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleConstraintAnalyzer.class);
	private String ruleId;

	@Override
	public boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas) {
		if (rule.getDataRefs().size() == 0 && rule.getAssociation() == null) {
			logger.error("At least one data-category-ref or data-association should appear in rule: {}", rule.getId());
			return true;
		}
		this.ruleId = rule.getId();
		boolean error = false;

		error |= checkAssociaiton(rule);

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
		return "Error detected when checking constraints of restricted data categories, see error messages above.";
	}

	private boolean checkAssociaiton(Rule rule) {
		boolean error = false;
		DataAssociation association = rule.getAssociation();
		if (association == null) {
			return error;
		}
		List<DataRef> set = association.getDataRefs();

		DataRef[] refs = set.toArray(new DataRef[set.size()]);
		for (int i = 0; i < refs.length; i++) {
			DataRef ref1 = refs[i];
			for (int j = i + 1; j < refs.length; j++) {
				DataRef ref2 = refs[j];
				if (SetUtil.bottom(ref1.getAction(), ref2.getAction()) != null
						&& SetUtil.intersects(ref1.getMaterialized(), ref2.getMaterialized())) {
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
			logger.error("Only one restriction should appear in rule when only data categories are referenced by rule: {}",
					rule.getId());
			return true;
		}
		Restriction restriction = restrictions.get(0);
		if (restriction.isForbid()) {
			return false;
		}
		Set<Desensitization> desensitizations = restriction.getDesensitizations();
		if (desensitizations.size() > 1) {
			logger.error("Only one desensitize should appear in rule when only data categories are referenced by rule: {}",
					rule.getId());
			return true;
		}
		boolean error = false;
		Desensitization de = desensitizations.iterator().next();
		for (DataRef ref : rule.getDataRefs()) {
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
				for (DataRef ref : de.getDataRefs()) {
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
				logger.error("Desensitize operation: {} is not supported by data category: {} in rule: {}", op.getName(),
						data.getId(), ruleId);
				error = true;
			}
		}

		return error;
	}
}
