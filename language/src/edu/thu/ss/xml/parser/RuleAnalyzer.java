package edu.thu.ss.xml.parser;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategory;
import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.HierarchicalObject;
import edu.thu.ss.xml.pojo.ReferringObject;
import edu.thu.ss.xml.pojo.Restriction;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategory;
import edu.thu.ss.xml.pojo.UserCategoryContainer;

public class RuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleAnalyzer.class);

	/**
	 * substitute user and data reference in rules with concrete elements
	 * 
	 * @param rule
	 * @param users
	 * @param datas
	 * @return
	 */
	public boolean subsitute(Rule rule, UserCategoryContainer users, DataCategoryContainer datas) {
		boolean error = false;
		error = error || substituteUsers(rule.getUserRefs(), rule.getUsers(), users, rule.getId());
		error = error || substituteDatas(rule.getDataRefs(), rule.getDatas(), datas, rule.getId());
		for (DataAssociation association : rule.getAssociations()) {
			error = error || substituteDatas(association.getDataRefs(), association.getDatas(), datas, rule.getId());
		}
		Restriction restriction = rule.getRestriction();
		if (restriction != null) {
			error = error || substituteDatas(restriction.getDataRefs(), restriction.getDatas(), datas, rule.getId());
		}
		return error;
	}

	public boolean consistencyCheck(Rule rule) {
		boolean error = false;
		if (rule.getRestriction() == null) {
			return false;
		}
		List<DataCategory> datas = null;
		if (rule.getDatas().size() > 0) {
			datas = rule.getDatas();
		} else {
			datas = rule.getAssociations().get(0).getDatas();
		}
		List<DataCategory> list = rule.getRestriction().getDatas();
		for (DataCategory data : list) {
			if (!datas.contains(data)) {
				logger.error("Restricted data category: " + data.getId()
						+ " not appeared in referenced data categories of rule: " + rule.getId());
				error = true;
			}
		}
		return error;
	}

	public void simplify(Rule rule) {
		simplifyCategories(rule.getUsers());
		simplifyCategories(rule.getDatas());
		simplifyCategories(rule.getActions());
	}

	@SuppressWarnings("rawtypes")
	private void simplifyCategories(Collection collection) {
		Iterator it = collection.iterator();
		while (it.hasNext()) {
			HierarchicalObject obj = (HierarchicalObject) it.next();
			boolean removable = false;
			for (Object obj2 : collection) {
				if (obj.descedantOf((HierarchicalObject) obj2)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
			}
		}
	}

	private boolean substituteUsers(Set<ReferringObject> refs, List<UserCategory> list, UserCategoryContainer users,
			String ruleId) {
		boolean error = false;
		for (ReferringObject ref : refs) {
			UserCategory user = users.get(ref.getRefid());
			if (user == null) {
				logger.error("Fail to location user category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
				error = true;
			} else {
				list.add(user);
			}
		}
		return error;
	}

	private boolean substituteDatas(Set<ReferringObject> refs, List<DataCategory> list, DataCategoryContainer datas,
			String ruleId) {
		boolean error = false;
		for (ReferringObject ref : refs) {
			DataCategory data = datas.get(ref.getRefid());
			if (data == null) {
				logger.error("Fail to location data category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
				error = true;
			} else {
				list.add(data);
			}
		}
		return error;
	}
}
