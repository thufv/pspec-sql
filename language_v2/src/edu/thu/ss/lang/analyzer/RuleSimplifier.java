package edu.thu.ss.lang.analyzer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.lang.util.InclusionUtil;
import edu.thu.ss.lang.xml.XMLDataAssociation;
import edu.thu.ss.lang.xml.XMLDataCategoryContainer;
import edu.thu.ss.lang.xml.XMLDataCategoryRef;
import edu.thu.ss.lang.xml.XMLRestriction;
import edu.thu.ss.lang.xml.XMLRule;
import edu.thu.ss.lang.xml.XMLUserCategoryContainer;
import edu.thu.ss.lang.xml.XMLUserCategoryRef;

public class RuleSimplifier extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleSimplifier.class);

	@Override
	protected boolean analyzeRule(XMLRule rule, XMLUserCategoryContainer users, XMLDataCategoryContainer datas) {
		/**
		 * simplification of users/datas in a rule is no longer needed, since
		 * all users/datas are expanded into a single set.
		 */
		// simplifyUsers(rule.getUserRefs(), rule.getId());
		// simplifyDatas(rule.getDataRefs(), rule.getId());

		simplifyDataAssociations(rule.getAssociations());
		simplifyRestrictions(rule.getRestrictions());
		return false;
	}

	@SuppressWarnings("unused")
	private void simplifyUsers(Set<XMLUserCategoryRef> categories, String ruleId) {
		Iterator<XMLUserCategoryRef> it = categories.iterator();
		while (it.hasNext()) {
			XMLUserCategoryRef user1 = it.next();
			boolean removable = false;
			for (XMLUserCategoryRef user2 : categories) {
				if (user1 != user2 && InclusionUtil.includes(user2, user1)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("User category: {} is removed from rule: {} since it is redundant.", user1.getRefid(),
						ruleId);
			}
		}
	}

	@SuppressWarnings("unused")
	private void simplifyDatas(Set<XMLDataCategoryRef> categories) {
		Iterator<XMLDataCategoryRef> it = categories.iterator();
		while (it.hasNext()) {
			XMLDataCategoryRef data1 = it.next();
			boolean removable = false;
			for (XMLDataCategoryRef data2 : categories) {
				if (data1 != data2 && InclusionUtil.includes(data2, data1)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("Data category: {} is removed from rule: {} since it is redundant.", data1.getRefid(),
						ruleId);
			}
		}
	}

	private void simplifyDataAssociations(Set<XMLDataAssociation> associations) {
		Iterator<XMLDataAssociation> it = associations.iterator();
		int i = 1;
		while (it.hasNext()) {
			XMLDataAssociation ass1 = it.next();
			boolean removable = false;
			for (XMLDataAssociation ass2 : associations) {
				if (ass1 != ass2 && InclusionUtil.includes(ass2, ass1)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("The #{} data association is removed from rule: {} since it is redundant.", i);
			}
			i++;
		}
	}

	private void simplifyRestrictions(List<XMLRestriction> restrictions) {
		if (restrictions.size() <= 1) {
			return;
		}
		Iterator<XMLRestriction> it = restrictions.iterator();
		int i = 1;
		while (it.hasNext()) {
			XMLRestriction res1 = it.next();
			boolean removable = false;
			for (XMLRestriction res2 : restrictions) {
				if (res1 != res2 && InclusionUtil.stricterThan(res1, res2)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("The #{} restriction is removed from rule: {} since it is redundant.", i, ruleId);
			}
			i++;
		}

	}

}
