package edu.thu.ss.xml.analyzer;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategory;
import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.Desensitization;
import edu.thu.ss.xml.pojo.Restriction;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategory;
import edu.thu.ss.xml.pojo.UserCategoryContainer;
import edu.thu.ss.xml.pojo.UserCategoryRef;

public class RuleResolver extends BaseRuleAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(RuleResolver.class);

	@Override
	public boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas) {
		boolean error = false;
		error = error || resolveUsers(rule.getUserRefs(), users, rule.getId());
		error = error || resolveDatas(rule.getDataRefs(), datas, rule.getId());
		for (DataAssociation association : rule.getAssociations()) {
			error = error || resolveDatas(association.getDataRefs(), datas, rule.getId());
		}
		Restriction restriction = rule.getRestriction();
		if (restriction != null) {
			for (Desensitization de : restriction.getDesensitizations()) {
				error = error || resolveDatas(de.getDataRefs(), datas, rule.getId());
			}
		}
		return error;
	}

	@Override
	public boolean stopOnError() {
		return true;
	}

	@Override
	public String errorMsg() {
		return "Error detected when analyzing category references in rule, see error messages above.";
	}

	private boolean resolveUsers(Set<UserCategoryRef> refs, UserCategoryContainer users, String ruleId) {
		boolean error = false;
		for (UserCategoryRef ref : refs) {
			UserCategory user = users.get(ref.getRefid());
			if (user == null) {
				logger.error("Fail to location user category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
				error = true;
			} else {
				ref.setUser(user);
			}
		}
		return error;
	}

	private boolean resolveDatas(Set<DataCategoryRef> refs, DataCategoryContainer datas, String ruleId) {
		boolean error = false;
		for (DataCategoryRef ref : refs) {
			DataCategory data = datas.get(ref.getRefid());
			if (data == null) {
				logger.error("Fail to location data category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
				error = true;
			} else {
				ref.setData(data);
			}
		}
		return error;
	}

}
