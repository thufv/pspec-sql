package edu.thu.ss.xml.analyzer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.Restriction;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryContainer;
import edu.thu.ss.xml.pojo.UserCategoryRef;

public class RuleSimplifier extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleSimplifier.class);

	@Override
	protected boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas) {
		simplifyUsers(rule.getUserRefs(), rule.getId());
		simplifyDatas(rule.getDataRefs(), rule.getId());
		simplifyDataAssociations(rule.getAssociations(), rule.getId());
		simplifyRestrictions(rule.getRestrictions(), rule.getId());
		return false;
	}

	private void simplifyUsers(Set<UserCategoryRef> categories, String ruleId) {
		Iterator<UserCategoryRef> it = categories.iterator();
		while (it.hasNext()) {
			UserCategoryRef user1 = it.next();
			boolean removable = false;
			for (UserCategoryRef user2 : categories) {
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

	private void simplifyDatas(Set<DataCategoryRef> categories, String ruleId) {
		Iterator<DataCategoryRef> it = categories.iterator();
		while (it.hasNext()) {
			DataCategoryRef data1 = it.next();
			boolean removable = false;
			for (DataCategoryRef data2 : categories) {
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

	private void simplifyDataAssociations(Set<DataAssociation> associations, String ruleId) {
		Iterator<DataAssociation> it = associations.iterator();
		int i = 1;
		while (it.hasNext()) {
			DataAssociation ass1 = it.next();
			boolean removable = false;
			for (DataAssociation ass2 : associations) {
				if (ass1 != ass2 && InclusionUtil.includes(ass2, ass1)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("The #{} data association is removed from rule: {} since it is redundant.", i, ruleId);
			}
			i++;
		}
	}

	private void simplifyRestrictions(List<Restriction> restrictions, String ruleId) {
		if (restrictions.size() <= 1) {
			return;
		}
		Iterator<Restriction> it = restrictions.iterator();
		int i = 1;
		while (it.hasNext()) {
			Restriction res1 = it.next();
			boolean removable = false;
			for (Restriction res2 : restrictions) {
				if (res1 != res2 && InclusionUtil.includes(res2, res1)) {
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

	@Override
	public boolean stopOnError() {
		return false;
	}

	@Override
	public String errorMsg() {
		return "";
	}

}
