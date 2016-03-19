package edu.thu.ss.spec.lang.analyzer.redundancy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.stat.RedundancyStat;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.AnalysisType;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.z3.FormulaBuilder;
import edu.thu.ss.spec.z3.Z3Manager;

/**
 * performs policy redundancy analysis, find all pairs r1, r2 such that r2 covers r1 (r1 is redundant) and r2 is not redundant
 * 
 * @author luochen
 * 
 */
public abstract class BaseRedundancyAnalyzer extends BasePolicyAnalyzer<RedundancyStat> {

	protected static Logger logger = LoggerFactory.getLogger(BaseRedundancyAnalyzer.class);

	protected Set<Rule> removable = new HashSet<>();

	protected Z3Manager z3;

	protected FormulaBuilder builder;

	public BaseRedundancyAnalyzer(EventTable table) throws Z3Exception {
		super(table);

		z3 = Z3Manager.getInstance();
		builder = z3.getBuilder();
	}

	public boolean analyze(Policy policy) throws Exception {

		int count = 0;
		List<Rule> rules = policy.getRules();

		for (int i = 0; i < rules.size(); i++) {
			Rule prule = rules.get(i);
			if (removable.contains(prule)) {
				continue;
			}

			//try to find as many redundant rules as possible
			List<Rule> list = new ArrayList<>();
			for (int j = i + 1; j < rules.size(); j++) {
				Rule trule = rules.get(j);
				if (removable.contains(trule)) {
					continue;
				}
				if (prule == trule) {
					continue;
				}
				if (implies(prule, trule)) {
					list.add(trule);
				} else if (implies(trule, prule)) {
					list.add(prule);
					prule = trule;
				}
			}
			if (list.size() > 0) {
				for (Rule rule : list) {
					removable.add(rule);
					logger.warn(
							"The rule: {} is redundant since it is covered by rule: {}, consider revise your policy.",
							rule.getId(), prule.getId());
					table.onAnalysis(AnalysisType.Redundancy, rule, prule);
				}
				count += list.size();
			}
		}

		logger.error("{} redundant rules detected.", count);
		if (stat != null) {
			stat.rules = count;
		}

		return false;
	}

	protected boolean scopeSubsumes(Rule rule1, Rule rule2) {
		if (rule1.getDimension() > rule2.getDimension()) {
			return false;
		}
		UserRef user1 = rule1.getUserRef();
		UserRef user2 = rule2.getUserRef();
		if (!user1.subsumes(user2)) {
			return false;
		}
		List<DataRef> assoc1 = rule1.getDataAssociation().getDataRefs();
		List<DataRef> assoc2 = rule2.getDataAssociation().getDataRefs();

		for (int i = 0; i < assoc1.size(); i++) {
			boolean match = false;
			DataRef ref1 = assoc1.get(i);
			for (int j = 0; j < assoc2.size(); j++) {
				DataRef ref2 = assoc2.get(j);
				if (ref1.subsumes(ref2)) {
					match = true;
				}
			}
			if (!match) {
				return false;
			}
		}

		return true;
	}

	protected abstract boolean implies(Rule rule1, Rule rule2) throws Z3Exception;

}
