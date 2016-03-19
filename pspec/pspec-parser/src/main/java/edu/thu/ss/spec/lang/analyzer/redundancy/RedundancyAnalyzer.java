package edu.thu.ss.spec.lang.analyzer.redundancy;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.AssociatedDataAccess;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.Rule;

public class RedundancyAnalyzer extends BaseRedundancyAnalyzer {

	public RedundancyAnalyzer(EventTable table) throws Z3Exception {
		super(table);
	}

	@Override
	protected boolean implies(Rule rule1, Rule rule2) throws Z3Exception {
		if (!scopeSubsumes(rule1, rule2)) {
			return false;
		}

		DataAssociation assoc1 = rule1.getDataAssociation();
		DataAssociation assoc2 = rule2.getDataAssociation();

		for (AssociatedDataAccess ada2 : assoc2.getAssociatedDataAccesses()) {
			BoolExpr rule2Formula = z3.getContext().mkNot(builder.buildRuleFormula(rule2));

			List<BoolExpr> rule1Formulas = new ArrayList<>();

			for (AssociatedDataAccess ada1 : assoc1.getAssociatedDataAccesses()) {
				if (ada1.subsumedBy(ada2)) {
					BoolExpr rule1Formula = builder.buildTargetFormula(ada1, ada2, rule1, rule2);
					rule1Formulas.add(rule1Formula);
				}
			}
			assert (rule1Formulas.size() > 0);
			rule1Formulas.add(rule2Formula);

			// f1 /\.../\f1 /\ not (f2)
			BoolExpr formula = z3.getContext()
					.mkAnd(rule1Formulas.toArray(new BoolExpr[rule1Formulas.size()]));
			if (z3.satisfiable(formula)) {
				return false;
			}
		}

		return true;
	}

}
