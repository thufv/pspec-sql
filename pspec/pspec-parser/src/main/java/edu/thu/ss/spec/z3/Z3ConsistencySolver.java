package edu.thu.ss.spec.z3;

import java.util.List;

import org.slf4j.Logger;

import com.microsoft.z3.ApplyResult;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Goal;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Tactic;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Restriction;


public abstract class Z3ConsistencySolver {
	protected Logger logger;

	protected Context context = null;
	protected Symbol[] allSymbols = null;
	protected int[] varIndex = null;
	protected int[] dummyIndex = null;
	
	protected void init(int size) {
		try {
			context = new Context();
			allSymbols = new Symbol[size];
			varIndex = new int[size];
			dummyIndex = new int[size];
			for (int i = 0; i < size; i++) {
				allSymbols[i] = context.mkSymbol("p" + i);
				dummyIndex[i] = i;
			}
		} catch (Z3Exception e) {
			logger.error("Fail to initialize Z3Context.", e);
		}
	}
	
	protected BoolExpr buildExpression(int[] index, Restriction[] restrictions, IntExpr[] vars) throws Z3Exception {
		BoolExpr[] exprs = new BoolExpr[restrictions.length];
		for (int i = 0; i < restrictions.length; i++) {
			exprs[i] = buildExpression(index, restrictions[i], vars);
		}
		return context.mkOr(exprs);
	}

	private BoolExpr buildExpression(int[] index, Restriction restriction, IntExpr[] vars) throws Z3Exception {
		List<Desensitization> des = restriction.getDesensitizations();
		BoolExpr[] exprs = new BoolExpr[des.size()];
		for (int i = 0; i < des.size(); i++) {
			if (des.get(i) != null) {
				exprs[i] = buildExpression(index[i], des.get(i), vars);
			} else {
				exprs[i] = context.mkTrue();
			}
		}
		return context.mkAnd(exprs);
	}

	private BoolExpr buildExpression(int index, Desensitization de, IntExpr[] vars) throws Z3Exception {
		if (index == -1) {
			return context.mkTrue();
		}
		BoolExpr[] exprs = new BoolExpr[de.getOperations().size()];
		int i = 0;
		for (DesensitizeOperation op : de.getOperations()) {
			IntExpr var = vars[index];
			Expr num = context.mkNumeral(op.getId(), context.getIntSort());
			exprs[i++] = context.mkEq(var, num);
		}
		return context.mkOr(exprs);
	}
	
	protected boolean isSatisfiable(BoolExpr expr) throws Z3Exception {
		Tactic simplifyTactic = context.mkTactic("ctx-solver-simplify");
		Goal g = context.mkGoal(false, false, false);
		g.add(expr);
		ApplyResult a = simplifyTactic.apply(g);
		Goal[] goals = a.getSubgoals();
		for (int i = 0; i < goals.length; i++) {
			if (goals[i].isDecidedUnsat()) {
				simplifyTactic.dispose();
				return false;
			}
		}
		simplifyTactic.dispose();
		return true;
	}
}
