package edu.thu.ss.spec.lang.analyzer.redundancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.Conf;
import edu.thu.ss.spec.util.PSpecUtil;

/**
 * performs policy redundancy analysis, find all pairs r1, r2 such that r2 covers r1 (r1 is redundant) and r2 is not redundant
 * 
 * @author luochen
 * 
 */
public class ApproximateRedundancyAnalyzer extends BaseRedundancyAnalyzer {

	protected static Logger logger = LoggerFactory.getLogger(ApproximateRedundancyAnalyzer.class);

	protected final int[][] mappings = new int[Conf.Max_Dimension][Conf.Max_Dimension];

	protected final int[] mappingLength = new int[Conf.Max_Dimension];

	protected final boolean[] covered = new boolean[Conf.Max_Dimension];

	public ApproximateRedundancyAnalyzer(EventTable table) throws Z3Exception {
		super(table);
	}

	protected boolean scopeSubsumes(Rule rule1, Rule rule2) {
		Set<UserCategory> user1 = rule1.getUserRef().getMaterialized();
		Set<UserCategory> user2 = rule2.getUserRef().getMaterialized();
		if (!PSpecUtil.contains(user1, user2)) {
			return false;
		}

		List<DataRef> dataRefs1 = rule1.getDataAssociation().getDataRefs();
		List<DataRef> dataRefs2 = rule2.getDataAssociation().getDataRefs();

		Arrays.fill(covered, false);
		for (int i = 0; i < dataRefs1.size(); i++) {
			boolean match = false;
			int index = 0;
			DataRef ref1 = dataRefs1.get(i);
			for (int j = 0; j < dataRefs2.size(); j++) {
				DataRef ref2 = dataRefs2.get(j);
				if (ref1.subsumes(ref2)) {
					match = true;
					mappings[i][index++] = j;
					covered[j] = true;
				}
			}
			if (!match) {
				return false;
			}
			mappingLength[i] = index;
		}
		return true;
	}

	/**
	 * check whether target is redundant w.r.t rule
	 * 
	 * @param target
	 * @param rule
	 * @return redundant
	 * @throws Z3Exception 
	 */
	protected boolean implies(Rule rule1, Rule rule2) throws Z3Exception {
		if (!scopeSubsumes(rule1, rule2)) {
			return false;
		}

		BoolExpr rule2Formula = z3.getContext().mkNot(z3.getBuilder().buildRuleFormula(rule2));

		List<BoolExpr> rule1Formulas = new ArrayList<>();

		IntExpr[] mapping = z3.getBuilder().getVariableMapping(rule1.getDimension());

		buildPrecondition(mapping, 0, rule1, rule2, rule1Formulas);

		assert (!rule1Formulas.isEmpty());
		rule1Formulas.add(rule2Formula);

		BoolExpr formula = z3.getContext()
				.mkAnd(rule1Formulas.toArray(new BoolExpr[rule1Formulas.size()]));

		return !z3.satisfiable(formula);

	}

	protected void buildPrecondition(IntExpr[] mapping, int index, Rule rule1, Rule rule2,
			List<BoolExpr> formulas) throws Z3Exception {
		if (index == mapping.length) {
			BoolExpr formula = (BoolExpr) builder.buildRuleFormula(rule1, mapping);
			formulas.add(formula);
		} else {
			int length = mappingLength[index];
			for (int i = 0; i < length; i++) {
				int target = mappings[index][i];
				mapping[index] = builder.getRuleVariables(rule2)[target];
				buildPrecondition(mapping, index + 1, rule1, rule2, formulas);
			}
		}

	}

}
