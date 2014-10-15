package edu.thu.ss.xml.parser;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.Action;
import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategory;
import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.Restriction;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategory;
import edu.thu.ss.xml.pojo.UserCategoryContainer;
import edu.thu.ss.xml.pojo.UserCategoryRef;

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
		error = error || substituteUsers(rule.getUserRefs(), users, rule.getId());
		error = error || substituteDatas(rule.getDataRefs(), datas, rule.getId());
		for (DataAssociation association : rule.getAssociations()) {
			error = error || substituteDatas(association.getDataRefs(), datas, rule.getId());
		}
		Restriction restriction = rule.getRestriction();
		if (restriction != null) {
			error = error || substituteDatas(restriction.getDataRefs(), datas, rule.getId());
		}
		return error;
	}

	public boolean consistencyCheck(Rule rule) {
		boolean error = false;
		if (rule.getRestriction() == null) {
			return false;
		}
		Set<DataCategoryRef> datas = null;
		if (rule.getDataRefs().size() > 0) {
			datas = rule.getDataRefs();
		} else {
			datas = rule.getAssociations().get(0).getDataRefs();
		}
		Set<DataCategoryRef> list = rule.getRestriction().getDataRefs();
		for (DataCategoryRef ref : list) {
			if (!datas.contains(ref)) {
				logger.error("Restricted data category: " + ref.getRefid()
						+ " not appeared in referenced data categories of rule: " + rule.getId());
				error = true;
			}
		}
		return error;
	}

	public void simplify(Rule rule) {
		simplifyUserCategories(rule.getUserRefs());
		simplifyDataCategories(rule.getDataRefs());
	}

	private void simplifyUserCategories(Set<UserCategoryRef> users) {
		Iterator<UserCategoryRef> it = users.iterator();
		while (it.hasNext()) {
			UserCategoryRef ref = it.next();
			boolean removable = false;
			for (UserCategoryRef ref2 : users) {
				if (ref.getUser().descedantOf(ref2.getUser())) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
			}
		}
	}

	private void simplifyDataCategories(Set<DataCategoryRef> datas) {
		Iterator<DataCategoryRef> it = datas.iterator();
		while (it.hasNext()) {
			DataCategoryRef ref = it.next();
			boolean removable = false;
			for (DataCategoryRef ref2 : datas) {
				if (ref.getData().descedantOf(ref2.getData())) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
			}
		}
		for (DataCategoryRef ref : datas) {
			if (ref.getActions().size() > 1) {
				simplifyActions(ref.getActions());
			}
		}
	}

	private void simplifyActions(Set<Action> actions) {
		Iterator<Action> it = actions.iterator();
		while (it.hasNext()) {
			Action a = it.next();
			boolean removable = false;
			for (Action a2 : actions) {
				if (a.descedantOf(a2)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
			}
		}
	}

	private boolean substituteUsers(Set<UserCategoryRef> refs, UserCategoryContainer users, String ruleId) {
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

	private boolean substituteDatas(Set<DataCategoryRef> refs, DataCategoryContainer datas, String ruleId) {
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
