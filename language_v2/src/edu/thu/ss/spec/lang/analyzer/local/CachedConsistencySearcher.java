package edu.thu.ss.spec.lang.analyzer.local;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.SetUtil;

/**
 * an enhanced consistency search with caching results in each level.
 * @author luochen
 *
 */
public class CachedConsistencySearcher extends ConsistencySearcher {
	private static Logger logger = LoggerFactory.getLogger(CachedConsistencySearcher.class);

	private Map<SearchKey, RuleObject> cache;
	private Map<SearchKey, RuleObject> nextCache = new HashMap<>();

	public CachedConsistencySearcher(Policy policy) {
		super(policy);
	}

	@Override
	protected void beginLevel(int level) {
		cache = nextCache;
		nextCache = new HashMap<>();

	}

	@Override
	protected void endLevel(int level) {
	};

	@Override
	protected boolean process(SearchKey key) {
		RuleObject[] objs = getRuleObjects(key);
		assert (objs.length == 2);
		RuleObject rule1 = objs[0];
		RuleObject rule2 = objs[1];

		RuleObject result = new RuleObject();

		Set<UserCategory> users = SetUtil.intersect(rule1.users, rule2.users);
		if (users.size() == 0) {
			return false;
		}
		result.users = users;
		List<Triple> triples = new LinkedList<>();
		boolean match = false;
		for (Triple t1 : rule1.triples) {
			for (Triple t2 : rule2.triples) {
				Action action = SetUtil.bottom(t1.action, t2.action);
				if (action == null) {
					continue;
				}
				Set<DataCategory> datas = SetUtil.intersect(t1.datas, t2.datas);
				if (datas.size() == 0) {
					continue;
				}
				match = true;
				List<Set<DesensitizeOperation>> list = checkRestriction(key, t1.list, t2.list, datas);
				if (list == null) {
					return false;
				}
				Triple triple = new Triple(action, datas, list);
				triples.add(triple);
				match = true;
			}
		}

		if (!match) {
			return false;
		}

		result.triples = triples.toArray(new Triple[triples.size()]);
		nextCache.put(key, result);
		return true;
	}

	private List<Set<DesensitizeOperation>> checkRestriction(SearchKey key, List<Set<DesensitizeOperation>> list1,
			List<Set<DesensitizeOperation>> list2, Set<DataCategory> datas) {
		if (list1 == null || list2 == null) {
			if (!(list1 == null && list2 == null)) {
				int index = (list1 == null) ? key.index[0] : key.index[1];
				logger.error("Possible conflicts between expanded rules: {}, since rule :#{} forbids the data access.",
						SetUtil.toString(key.index, sortedRules), sortedRules.get(index).getRuleId());
			}
			return null;
		}
		List<Set<DesensitizeOperation>> list = new LinkedList<>();
		for (Set<DesensitizeOperation> ops1 : list1) {
			for (Set<DesensitizeOperation> ops2 : list2) {
				if (ops1 == null) {
					SetUtil.mergeOperations(list, ops2);
				} else if (ops2 == null) {
					SetUtil.mergeOperations(list, ops1);
				} else {
					Set<DesensitizeOperation> ops = SetUtil.intersect(ops1, ops2);
					if (ops.size() == 0) {
						logger.error(
								"Desensitize operation conflicts detected between expanded sortedRules: #{} for data categories: {}.",
								SetUtil.toString(key.index, sortedRules), SetUtil.format(datas, ","));
						return null;
					}
					SetUtil.mergeOperations(list, ops);

				}
			}
		}
		return list;
	}

	private RuleObject[] getRuleObjects(SearchKey key) {
		int tmp = key.getFirst();
		RuleObject[] objs = new RuleObject[2];
		objs[0] = ruleObjects[tmp];
		key.setFirst(-1);
		objs[1] = cache.get(key);
		if (objs[1] == null) {
			if (key.index.length > 2) {
				throw new RuntimeException("Invalid cache state for key: " + Arrays.toString(key.index));
			}
			objs[1] = ruleObjects[key.getLast()];
		}
		key.setFirst(tmp);
		return objs;
	}
}
