package edu.thu.ss.spec.util;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.z3.BoolExpr;
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

public class DesensitizeZ3Util extends Z3Util{

	private IntExpr[] vars;
	private Sort[] types;
	private Symbol[] symbols;
	private boolean isInitiated = false;
	
	public DesensitizeZ3Util() {
		super.init(BaseRedundancyAnalyzer.Max_Dimension);
	}
	/**
	 * rule1 implies rule2
	 * @param rule1
	 * @param rule2
	 * @param dataIncludes
	 * @param dataLength
	 * @return
	 */
	public boolean implies(ExpandedRule rule1, ExpandedRule rule2, int[][] dataIncludes,
			int[] dataLength, boolean[] covered) {
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
					if (!covered[i] && res2.getDesensitizations()[i] != null) {
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

	private boolean impliesImplement(Restriction[] restrictions1, int dim1,
			Restriction[] restrictions2, int dim2, int[][] dataIncludes, int[] dataLength)
			throws Z3Exception {

		vars = new IntExpr[dim2];
		types = new Sort[dim2];
		symbols = new Symbol[dim2];
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

	private void buildPreCondition(Restriction[] restrictions, int dim, int[][] dataIncludes,
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

	private BoolExpr buildExpr(Restriction[] restrictions, IntExpr[] vars, int[] varIndex)
			throws Z3Exception {
		BoolExpr[] exprs = new BoolExpr[restrictions.length];
		for (int i = 0; i < restrictions.length; i++) {
			exprs[i] = buildExpr(restrictions[i], vars, varIndex);
		}
		return context.mkOr(exprs);
	}

	private BoolExpr buildExpr(Restriction res, IntExpr[] vars, int[] varIndex)
			throws Z3Exception {
		Desensitization[] des = res.getDesensitizations();
		BoolExpr[] exprs = new BoolExpr[des.length];
		for (int i = 0; i < des.length; i++) {
			if (des[i] != null) {
				exprs[i] = buildExpr(i, des[i], vars, varIndex);
			} else {
				exprs[i] = context.mkTrue();
			}
		}
		return context.mkAnd(exprs);
	}

	private BoolExpr buildExpr(int data, Desensitization de, IntExpr[] vars, int[] varIndex)
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
	
	public void init(int dim) {
		vars = new IntExpr[dim];
		types = new Sort[dim];
		symbols = new Symbol[dim];
		try {
			for (int i = 0; i < vars.length; i++) {
				symbols[i] = allSymbols[i];
				vars[i] = (IntExpr) context.mkBound(i, context.getIntSort());
				types[i] = context.getIntSort();
			}
		} catch (Z3Exception e) {
			logger.error("", e);
		}
	}
	
	public boolean isExprSatisifiable(BoolExpr expr) {
		try {
			BoolExpr condition = context.mkExists(types, symbols, expr, 0, null, null, null, null);
			Solver solver = context.mkSolver();
			solver.add(condition);
			Status status = solver.check();
			return status.equals(Status.SATISFIABLE);
		} catch (Z3Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public BoolExpr buildAndExpr(BoolExpr expr1, BoolExpr expr2) {
		try {
			BoolExpr[] expr = {expr1, expr2};
			return context.mkAnd(expr);
		} catch (Z3Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BoolExpr buildExpression(ExpandedRule rule) {
		if (rule == null) {
			try {
				return context.mkTrue();
			} catch (Z3Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		Restriction[] restrictions = rule.getRestrictions();
		int dim = rule.getDimension();
		init(dim);
		for (int i = 0; i < varIndex.length; i++) {
			varIndex[i] = i;
		}
		try {
			return buildExpr(restrictions, vars, varIndex);
		} catch (Z3Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BoolExpr combineExpressions(BoolExpr expr1, BoolExpr expr2, int dim, int[][] dataIncludes,
			int[] dataLength, boolean[] covered) {
		vars = new IntExpr[dim];
		types = new Sort[dim];
		symbols = new Symbol[dim];
		try {
			for (int i = 0; i < vars.length; i++) {
				symbols[i] = allSymbols[i];
				vars[i] = (IntExpr) context.mkBound(vars.length - i - 1, context.getIntSort());
				types[i] = context.getIntSort();
			}
		} catch (Z3Exception e) {
			logger.error("", e);
		}
		
		List<BoolExpr> list = new ArrayList<>();
		try {
			combineExpressions(expr2, dim, dataIncludes, dataLength, vars, varIndex, 0, list);
			expr2 = context.mkOr(list.toArray(new BoolExpr[list.size()]));
			return expr1 = context.mkAnd(expr1, expr2);
		} catch (Z3Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void combineExpressions(BoolExpr expr, int dim, int[][] dataIncludes,
			int[] dataLength, IntExpr[] vars, int[] varIndex, int index, List<BoolExpr> list)
			throws Z3Exception {
		if (index == dim) {
			list.add(expr);
		} else {
			for (int i = 0; i < dataLength[index]; i++) {
				BoolExpr newExpr = (BoolExpr) expr.substitute(vars[index], vars[i]);
				combineExpressions(newExpr, dim, dataIncludes, dataLength, vars, varIndex, index + 1,
						list);
			}
		}
	}
}
