package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.event.EventTable;
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

	public static class SimplificationLog {
		public Rule rule;

		public List<UserRef> redundantUsers = new ArrayList<>();

		public List<DataRef> redundantDatas = new ArrayList<>();

		public List<Restriction> redundantRestrictions = new ArrayList<>();

		public SimplificationLog(Rule rule) {
			this.rule = rule;
		}

		public void clear() {
			redundantUsers.clear();
			redundantDatas.clear();
			redundantRestrictions.clear();
		}

		public boolean isEmpty() {
			if (!redundantUsers.isEmpty()) {
				return false;
			}
			if (!redundantDatas.isEmpty()) {
				return false;
			}
			if (!redundantRestrictions.isEmpty()) {
				return false;
			}
			return true;
		}
	}

	private static Logger logger = LoggerFactory.getLogger(RuleSimplifier.class);

	private boolean remove;

	private List<SimplificationLog> logs = new ArrayList<>();

	public RuleSimplifier(EventTable table, boolean remove) {
		super(table);
		this.remove = remove;
	}

	public SimplificationLog analyze(Rule rule, UserContainer users, DataContainer datas) {
		SimplificationLog log = new SimplificationLog(rule);
		simplifyUsers(rule.getUserRefs(), rule.getId(), log);
		if (rule.isSingle()) {
			simplifyDatas(rule.getDataRefs(), rule.getId(), log);
		}
		simplifyRestrictions(rule.getRestrictions(), rule.getId(), log);
		if (remove) {
			commit(log);
			return null;
		}
		return log;
	}

	@Override
	public boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas) {
		SimplificationLog log = analyze(rule, users, datas);
		if (log != null && !log.isEmpty()) {
			if (logs == null) {
				logs = new ArrayList<>();
			}
			logs.add(log);
		}

		return false;
	}

	public List<SimplificationLog> getLogs() {
		return logs;
	}

	public void commit() {
		if (logs == null) {
			return;
		}
		for (SimplificationLog log : logs) {
			commit(log);
		}
	}

	private void commit(SimplificationLog log) {
		Rule rule = log.rule;
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

	public boolean isEmpty() {
		if (logs == null || logs.isEmpty()) {
			return true;
		}
		for (SimplificationLog log : logs) {
			if (!log.isEmpty()) {
				return false;
			}
		}
		return true;
	}

}
