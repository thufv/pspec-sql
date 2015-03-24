package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.InclusionUtil;

/**
 * simplifies {@link UserRef}, {@link DataRef} and {@link Restriction} in each rule.
 * @author luochen
 *
 */
public class RuleSimplifier extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleSimplifier.class);

	@Override
	protected boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas) {

		simplifyUsers(rule.getUserRefs(), rule.getId());
		simplifyDatas(rule.getDataRefs(), rule.getId());

		//simplifyDataAssociations(rule.getAssociations());

		simplifyRestrictions(rule.getRestrictions());
		return false;
	}

	private void simplifyUsers(List<UserRef> users, String ruleId) {
		Iterator<UserRef> it = users.iterator();
		while (it.hasNext()) {
			UserRef user1 = it.next();
			boolean removable = false;
			for (UserRef user2 : users) {
				if (user1 != user2 && InclusionUtil.instance.includes(user2, user1)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("User category: {} is removed from rule: {} since it is redundant.", user1.getRefid(), ruleId);
			}
		}
	}

	/**
	 * global {@link DataRef} cannot be covered by local {@link UserRef}
	 * @param datas
	 * @param ruleId
	 */
	private void simplifyDatas(List<DataRef> datas, String ruleId) {
		Iterator<DataRef> it = datas.iterator();
		while (it.hasNext()) {
			DataRef data1 = it.next();
			boolean removable = false;
			for (DataRef data2 : datas) {
				if (data1.isGlobal() && !data2.isGlobal()) {
					continue;
				}
				if (data1 != data2 && InclusionUtil.instance.includes(data2, data1)) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("Data category: {} is removed from rule: {} since it is redundant.", data1.getRefid(), ruleId);
			}
		}
	}

	private void simplifyRestrictions(List<Restriction> restrictions) {
		if (restrictions.size() <= 1) {
			return;
		}
		Iterator<Restriction> it = restrictions.iterator();
		int i = 1;
		while (it.hasNext()) {
			Restriction res1 = it.next();
			boolean removable = false;
			for (Restriction res2 : restrictions) {
				if (res1 != res2 && InclusionUtil.instance.innerStricterThan(res2, res1)) {
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
