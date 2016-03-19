package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.pojo.AssociatedDataAccess;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.z3.FormulaBuilder;
import edu.thu.ss.spec.z3.Z3Manager;

public class ConsistencySearcher extends LevelwiseSearcher {
	private class CacheEntry {
		Set<UserCategory> users;
		Map<Integer, BoolExpr> formulas;

		public CacheEntry(Set<UserCategory> users, Map<Integer, BoolExpr> formulas) {
			super();
			this.users = users;
			this.formulas = formulas;
		}

	}

	private Rule seed;
	private List<Rule> candidates;
	private Map<SearchKey, CacheEntry> cache;
	private Map<SearchKey, CacheEntry> nextCache = new HashMap<>();

	private Z3Manager z3;
	private FormulaBuilder builder;

	private static Logger logger = LoggerFactory.getLogger(ConsistencySearcher.class);

	public ConsistencySearcher(Rule seed, List<Rule> candidates) throws Z3Exception {
		this.seed = seed;
		this.candidates = candidates;

		z3 = Z3Manager.getInstance();
		builder = z3.getBuilder();
	}

	@Override
	protected void beginLevel(int level) {

		cache = nextCache;
		nextCache = new HashMap<>();
	}

	@Override
	protected void endLevel(int level) {
	}

	@Override
	protected void initLevel(Set<SearchKey> currentLevel) throws Z3Exception {
		int candSize = candidates.size();
		for (int candIndex = 0; candIndex < candSize; candIndex++) {
			Rule rule = candidates.get(candIndex);

			Set<UserCategory> users = PSpecUtil.intersect(rule.getUserRef().getMaterialized(),
					seed.getUserRef().getMaterialized());

			AssociatedDataAccess[] seedAdas = seed.getDataAssociation().getAssociatedDataAccesses();
			BoolExpr seedFormula = builder.buildRuleFormula(seed);

			Map<Integer, BoolExpr> formulas = new HashMap<>();
			boolean conflict = false;
			for (int i = 0; i < seedAdas.length; i++) {
				if (!processAda(seedAdas[i], rule, seedFormula, i, formulas)) {
					logger.warn("Conflict detected for {} with seed: {}.", rule.getId(), seed.getId());
					conflict = true;
					break;
				}
			}
			if (!conflict) {
				SearchKey key = new SearchKey(candIndex);
				currentLevel.add(key);
				CacheEntry entry = new CacheEntry(users, formulas);
				nextCache.put(key, entry);
			}
		}
	}

	@Override
	protected boolean process(SearchKey key) throws Z3Exception {
		int firstIndex = key.getFirst();
		key.setFirst(-1);
		CacheEntry entry = cache.get(key);
		key.setFirst(firstIndex);

		Rule rule = candidates.get(firstIndex);

		Set<UserCategory> users = PSpecUtil.intersect(entry.users, rule.getUserRef().getMaterialized());
		if (users.isEmpty()) {
			return false;
		}

		AssociatedDataAccess[] seedAdas = seed.getDataAssociation().getAssociatedDataAccesses();

		Map<Integer, BoolExpr> formulas = new HashMap<>();

		for (Entry<Integer, BoolExpr> e : entry.formulas.entrySet()) {
			int i = e.getKey();
			BoolExpr formula = e.getValue();
			if (!processAda(seedAdas[i], rule, formula, i, formulas)) {
				//conflict
				logger.warn("Conflict detected for {} with seed: {}.", key, seed.getId());
				return false;
			}
		}
		if (formulas.isEmpty()) {
			//prune
			return false;
		} else {
			CacheEntry newEntry = new CacheEntry(users, formulas);
			nextCache.put(key, newEntry);
			return true;
		}
	}

	/**
	 * 
	 * @param entry, an existing cache entry
	 * @param rule, a new rule
	 * @return
	 * @throws Z3Exception 
	 */
	private boolean processAda(AssociatedDataAccess seedAda, Rule rule, BoolExpr formula, int i,
			Map<Integer, BoolExpr> formulas) throws Z3Exception {
		//check user

		List<BoolExpr> ruleFormulas = new ArrayList<>();
		AssociatedDataAccess[] ruleAdas = rule.getDataAssociation().getAssociatedDataAccesses();
		boolean match = false;
		for (int j = 0; j < ruleAdas.length; j++) {
			AssociatedDataAccess ruleAda = ruleAdas[j];
			if (ruleAda.subsumedBy(seedAda)) {
				if (rule.isForbid()) {
					conflicts++;
					return false;
				}
				ruleFormulas.add(builder.buildTargetFormula(ruleAda, seedAda, rule, seed));
				match = true;
			}
		}
		if (match) {
			//check satisfiability
			ruleFormulas.add(formula);
			if (!z3.satisfiable(ruleFormulas)) {
				//detected.
				conflicts++;
				return false;
			} else {
				formulas.put(i, z3.mkAnd(ruleFormulas));
			}
		}
		return true;
	}

}
