package edu.thu.ss.spec.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Status;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.analyzer.BaseRedundancyAnalyzer;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;

public class Z3Util {
	private static Logger logger = LoggerFactory.getLogger(Z3Util.class);

	private static Context context = null;
	private static Symbol[] allSymbols = null;
	private static int[] varIndex = null;
	private static int[] dummyIndex = null;

	private static boolean initialized = false;

	private static void init(int size) {
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
		} finally {
			initialized = true;
		}

	}

	/**
	 * rule1 implies rule2
	 * @param rule1
	 * @param rule2
	 * @param dataIncludes
	 * @param dataLength
	 * @return
	 */
	public static boolean implies(ExpandedRule rule1, ExpandedRule rule2, int[][] dataIncludes,
			int[] dataLength, boolean[] covered) {
		if (!initialized) {
			init(BaseRedundancyAnalyzer.Max_Dimension);
		}
		if (context == null) {
			return false;
		}
		try {
			//pre check
			if (rule1.getRestriction().isForbid()) {
				return true;
			}
			if (rule2.getRestriction().isForbid()) {
				return false;
			}

			List<Restriction> filtered = new ArrayList<>();

			Restriction[] restrictions2 = rule2.getRestrictions();
			int dim2 = rule2.getDimension();
			for (Restriction res2 : restrictions2) {
				boolean retain = true;
				for (int i = 0; i < dim2; i++) {
					if (!covered[i] && res2.getDesensitization(i).effective()) {
						retain = false;
						break;
					}
				}
				if (retain) {
					filtered.add(res2);
				}
			}
			if (filtered.size() == 0) {
				return false;
			}
			restrictions2 = filtered.toArray(new Restriction[filtered.size()]);
			return impliesImplement(rule1.getRestrictions(), rule1.getDimension(), restrictions2, dim2,
					dataIncludes, dataLength);
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}

	}

	static boolean impliesImplement(Restriction[] restrictions1, int dim1,
			Restriction[] restrictions2, int dim2, int[][] dataIncludes, int[] dataLength)
			throws Z3Exception {

		IntExpr[] vars = new IntExpr[dim2];
		Sort[] types = new Sort[dim2];
		Symbol[] symbols = new Symbol[dim2];
		for (int i = 0; i < vars.length; i++) {
			symbols[i] = allSymbols[i];
			vars[i] = (IntExpr) context.mkBound(vars.length - i - 1, context.getIntSort());
			types[i] = context.getIntSort();
		}

		List<BoolExpr> list = new ArrayList<>();
		buildPreCondition(restrictions1, dim1, dataIncludes, dataLength, vars, varIndex, 0, list);
		BoolExpr pre = context.mkAnd(list.toArray(new BoolExpr[list.size()]));

		BoolExpr post = buildExpr(restrictions2, vars, dummyIndex);

		BoolExpr implies = context.mkImplies(pre, post);

		BoolExpr condition = context.mkForall(types, symbols, implies, 0, null, null, null, null);

		//	logger.info(condition.toString());

		Solver solver = context.mkSolver();
		solver.add(condition);
		Status status = solver.check();
		return status.equals(Status.SATISFIABLE);
	}

	private static void buildPreCondition(Restriction[] restrictions, int dim, int[][] dataIncludes,
			int[] dataLength, IntExpr[] vars, int[] varIndex, int index, List<BoolExpr> list)
			throws Z3Exception {
		if (index == dim) {
			BoolExpr expr = buildExpr(restrictions, vars, varIndex);
			list.add(expr);
		} else {
			for (int i = 0; i < dataLength[index]; i++) {
				varIndex[index] = dataIncludes[index][i];
				buildPreCondition(restrictions, dim, dataIncludes, dataLength, vars, varIndex, index + 1,
						list);
			}
		}

	}

	private static BoolExpr buildExpr(Restriction[] restrictions, IntExpr[] vars, int[] varIndex)
			throws Z3Exception {
		BoolExpr[] exprs = new BoolExpr[restrictions.length];
		for (int i = 0; i < restrictions.length; i++) {
			exprs[i] = buildExpr(restrictions[i], vars, varIndex);
		}
		return context.mkOr(exprs);
	}

	private static BoolExpr buildExpr(Restriction res, IntExpr[] vars, int[] varIndex)
			throws Z3Exception {
		List<Desensitization> des = res.getDesensitizations();
		BoolExpr[] exprs = new BoolExpr[des.size()];
		for (int i = 0; i < des.size(); i++) {
			if (des.get(i).effective()) {
				exprs[i] = buildExpr(i, des.get(i), vars, varIndex);
			} else {
				exprs[i] = context.mkTrue();
			}
		}
		return context.mkAnd(exprs);
	}

	private static BoolExpr buildExpr(int data, Desensitization de, IntExpr[] vars, int[] varIndex)
			throws Z3Exception {
		BoolExpr[] exprs = new BoolExpr[de.getOperations().size()];
		int i = 0;
		for (DesensitizeOperation op : de.getOperations()) {
			IntExpr var = vars[varIndex[data]];
			Expr num = context.mkNumeral(op.getId(), context.getIntSort());
			exprs[i++] = context.mkEq(var, num);
		}
		return context.mkOr(exprs);
	}
}
