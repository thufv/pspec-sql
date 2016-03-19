package edu.thu.ss.spec.z3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.pojo.AssociatedDataAccess;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.util.Conf;

public class FormulaBuilder {
	private Context context = null;

	public static final String Rule_Variable = "rule.variable";
	public static final String Rule_Formula = "rule.formula";

	public static final Map<Integer, IntExpr[]> mappings = new HashMap<>();

	static {
		for (int i = 1; i < Conf.Max_Dimension; i++) {
			mappings.put(i, new IntExpr[i]);
		}
	}

	public FormulaBuilder(Context context) {
		this.context = context;
	}

	public IntExpr[] getVariableMapping(int length) {
		return mappings.get(length);
	}

	public BoolExpr buildTargetFormula(AssociatedDataAccess sourceAda, AssociatedDataAccess targetAda,
			Rule sourceRule, Rule targetRule) throws Z3Exception {
		BoolExpr sourceFormula = buildRuleFormula(sourceRule);
		IntExpr[] targetVariables = getRuleVariables(targetRule);

		IntExpr[] mapping = mappings.get(sourceRule.getDimension());
		int targetLength = targetAda.length();

		for (int i = 0; i < mapping.length; i++) {
			for (int j = 0; j < targetLength; j++) {
				if (sourceAda.get(i).equals(targetAda.get(j))) {
					mapping[i] = targetVariables[j];
					break;
				}
			}
		}
		return (BoolExpr) sourceFormula.substitute(getRuleVariables(sourceRule), mapping);
	}

	public BoolExpr buildRuleFormula(Rule rule) throws Z3Exception {
		BoolExpr ruleFormula = (BoolExpr) rule.get(Rule_Formula);
		if (ruleFormula != null) {
			return ruleFormula;
		}

		ruleFormula = (BoolExpr) buildRuleFormula(rule, getRuleVariables(rule)).simplify();
		rule.put(Rule_Formula, ruleFormula);

		return ruleFormula;
	}

	public BoolExpr buildRuleFormula(Rule rule, IntExpr[] variables) throws Z3Exception {
		List<Restriction> restrictions = rule.getRestrictions();
		if (restrictions.isEmpty()) {
			return context.mkFalse();
		} else {
			BoolExpr[] formulas = new BoolExpr[restrictions.size()];
			for (int i = 0; i < formulas.length; i++) {
				formulas[i] = buildRestrictionFormula(variables, restrictions.get(i));
			}
			return context.mkOr(formulas);
		}
	}

	private BoolExpr buildRestrictionFormula(IntExpr[] variables, Restriction res)
			throws Z3Exception {
		List<Desensitization> desensitizationList = res.getDesensitizations();
		BoolExpr[] formulas = new BoolExpr[desensitizationList.size()];
		for (int i = 0; i < formulas.length; i++) {
			formulas[i] = buildDesensizationFormula(variables[i], desensitizationList.get(i));
		}
		return context.mkAnd(formulas);
	}

	private BoolExpr buildDesensizationFormula(IntExpr variable, Desensitization de)
			throws Z3Exception {
		if (de.getOperations().isEmpty()) {
			return context.mkTrue();
		}
		BoolExpr[] formulas = new BoolExpr[de.getOperations().size()];
		int i = 0;
		for (DesensitizeOperation op : de.getOperations()) {
			formulas[i++] = context.mkEq(variable, context.mkInt(op.getId()));
		}
		return context.mkOr(formulas);
	}

	public IntExpr[] getRuleVariables(Rule rule) throws Z3Exception {
		IntExpr[] variables = (IntExpr[]) rule.get(Rule_Variable);
		if (variables == null) {
			variables = new IntExpr[rule.getDimension()];
			for (int i = 0; i < variables.length; i++) {
				variables[i] = context.mkIntConst(rule.getId() + "#" + i);
			}
			rule.put(Rule_Variable, variables);
		}
		return variables;
	}

}
