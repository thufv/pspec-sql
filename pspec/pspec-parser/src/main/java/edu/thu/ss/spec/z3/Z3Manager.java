package edu.thu.ss.spec.z3;

import java.util.List;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Params;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Tactic;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.util.Profiling;

public class Z3Manager {

	private static Z3Manager z3 = null;

	public static Z3Manager getInstance() throws Z3Exception {
		if (z3 == null) {
			z3 = new Z3Manager();
		}
		return z3;
	}

	private Context context;

	private FormulaBuilder builder;

	private Solver solver;

	private Params params;

	public Z3Manager() throws Z3Exception {
		context = new Context();
		builder = new FormulaBuilder(context);

		params = context.mkParams();
		params.add("model", false);

		solver = context.mkSimpleSolver();

	}

	public FormulaBuilder getBuilder() {
		return builder;
	}

	public Context getContext() {
		return context;
	}

	public boolean satisfiable(BoolExpr formula) throws Z3Exception {

		Profiling.beginTiming(Profiling.Solving_Time);
		solver.setParameters(params);
		solver.add(formula);
		Status status = solver.check();
		boolean result = status.equals(Status.SATISFIABLE);
		Profiling.endTiming(Profiling.Solving_Time);
		solver.reset();
		return result;
	}

	public boolean satisfiable(List<BoolExpr> formulas) throws Z3Exception {

		Profiling.beginTiming(Profiling.Solving_Time);
		solver.setParameters(params);
		for (BoolExpr formula : formulas) {
			solver.add(formula);
		}
		Status status = solver.check();
		boolean result = status.equals(Status.SATISFIABLE);
		Profiling.endTiming(Profiling.Solving_Time);
		solver.reset();
		return result;
	}

	public BoolExpr mkAnd(List<BoolExpr> ruleFormulas) throws Z3Exception {
		return context.mkAnd(ruleFormulas.toArray(new BoolExpr[ruleFormulas.size()]));
	}
}
