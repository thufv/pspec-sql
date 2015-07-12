package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PolicyEvent;
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

	private static class SimplificationLog {
		List<UserRef> redundantUsers = new ArrayList<>();

		List<DataRef> redundantDatas = new ArrayList<>();

		List<Restriction> redundantRestrictions = new ArrayList<>();
	}

	private static Logger logger = LoggerFactory.getLogger(RuleSimplifier.class);

	private boolean remove;

	private Map<Rule, SimplificationLog> logs = new HashMap<>();

	public RuleSimplifier(EventTable<PolicyEvent> table, boolean remove) {
		super(table);
		this.remove = remove;
	}

	@Override
	public boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas) {
		SimplificationLog log = new SimplificationLog();
		simplifyUsers(rule.getUserRefs(), rule.getId(), log);
		simplifyDatas(rule.getDataRefs(), rule.getId(), log);
		simplifyRestrictions(rule.getRestrictions(), rule.getId(), log);
		if (remove) {
			commit(rule, log);
		} else {
			logs.put(rule, log);
		}
		return false;
	}

	public SimplificationLog getSimplificationL(Rule rule) {
		return logs.get(rule);
	}

	public void commit() {
		for (Entry<Rule, SimplificationLog> e : logs.entrySet()) {
			commit(e.getKey(), e.getValue());
		}
	}

	public void commit(Rule rule) {
		SimplificationLog log = logs.remove(rule);
		if (log != null) {
			commit(rule, log);
		}
	}

	private void commit(Rule rule, SimplificationLog log) {
		rule.getUserRefs().removeAll(log.redundantUsers);
		rule.getDataRefs().removeAll(log.redundantDatas);
		rule.getRestrictions().removeAll(log.redundantRestrictions);
	}

	private void simplifyUsers(List<UserRef> users, String ruleId, SimplificationLog log) {
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
				//TODO
				logger.warn("User category: {} is redundant in rule: {}", user1.getRefid(), ruleId);
				log.redundantUsers.add(user1);
			}
		}
	}

	/**
	 * global {@link DataRef} cannot be covered by local {@link UserRef}
	 * @param datas
	 * @param ruleId
	 */
	private void simplifyDatas(List<DataRef> datas, String ruleId, SimplificationLog log) {
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
				//TODO
				log.redundantDatas.add(data1);
				logger.warn("Data category: {} is redundant in rule: {}", data1.getRefid(), ruleId);
			}
		}
	}

	private void simplifyRestrictions(List<Restriction> restrictions, String ruleId,
			SimplificationLog log) {
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
				//TODO
				log.redundantRestrictions.add(res1);
				logger.warn("The #{} restriction is redundant in rule: {}", i, ruleId);
			}
			i++;
		}
	}

}
