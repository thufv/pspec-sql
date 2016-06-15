package edu.thu.ss.spec.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.ArithExpr;
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
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Filter;
import edu.thu.ss.spec.lang.pojo.FilterNode;
import edu.thu.ss.spec.lang.pojo.Function;
import edu.thu.ss.spec.lang.pojo.Predicate;
import edu.thu.ss.spec.lang.pojo.Restriction;

public class Z3Util {
	private static Logger logger = LoggerFactory.getLogger(Z3Util.class);

	private static Context context = null;
	private static Symbol[] allSymbols = null;
	private static int[] varIndex = null;
	private static int[] dummyIndex = null;

	private static boolean initialized = false;

	private static filterConditions fc = new filterConditions();
	
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
	
	public static boolean implies(Filter filter1, Filter filter2, DataRef[] dataRefs1,
			DataRef[] dataRefs2) {
		int dim = filter1.getDataRefs().size();
		IntExpr[] vars = new IntExpr[dim];
		Sort[] types = new Sort[dim];
		Symbol[] symbols = new Symbol[dim];
		
		try {
			for (int i = 0; i < vars.length; i++) {
				symbols[i] = allSymbols[i];
				vars[i] = (IntExpr) context.mkBound(vars.length - i - 1, context.getIntSort());
				types[i] = context.getIntSort();
			}
			
			BoolExpr pre = buildExpr(filter1, vars, dataRefs1);
			BoolExpr post = buildExpr(filter2, vars, dataRefs1);
			BoolExpr implies = context.mkImplies(pre, post);
			BoolExpr condition = context.mkForall(types, symbols, implies, 0, null, null, null, null);
			
			//	logger.info(condition.toString());
			Solver solver = context.mkSolver();
			solver.add(condition);
			Status status = solver.check();
			return status.equals(Status.SATISFIABLE);
		} catch (Z3Exception e) {
			logger.error("", e);
			return false;
		}

	}
	
	private static BoolExpr buildExpr(Filter filter, IntExpr[] vars, DataRef[] dataRefs) throws Z3Exception {
		List<BoolExpr> exprs = new ArrayList<>();
		List<FilterNode> flist = filter.getFilterNodes();
		for (int i = 0; i < flist.size(); i++) {
			exprs.add(buildExpr(flist.get(i), vars, dataRefs));
		}
		
		BoolExpr expr = context.mkAnd(exprs.toArray(new BoolExpr[exprs.size()]));
		
		return expr;
	}
	private static BoolExpr buildExpr(FilterNode fNode, IntExpr[] vars, DataRef[] dataRefs) throws Z3Exception {
		List<BoolExpr> exprs = new ArrayList<>();
		List<FilterNode> flist = fNode.getFilterNodes();
		List<Predicate> plist = fNode.getPredicates();
		BoolExpr expr = null;
		
		for (int i = 0; i < flist.size(); i++) {
			exprs.add(buildExpr(flist.get(i), vars, dataRefs));
		}
		
		for (int i = 0; i < plist.size(); i++) {
			exprs.add(buildExpr(plist.get(i), vars, dataRefs));
		}
		
		if (fNode.getNodeType().equals("and")) {
			expr =  context.mkAnd(exprs.toArray(new BoolExpr[exprs.size()]));
		}
		else if (fNode.getNodeType().equals("or")){ 
			expr = context.mkOr(exprs.toArray(new BoolExpr[exprs.size()]));
		}
		
		return expr;
	}
	
	private static BoolExpr buildExpr(Predicate pre, IntExpr[] vars, DataRef[] dataRefs) throws Z3Exception {
		Function left = pre.getLeftFunction();
		Function right = pre.getRightFunction();
		ArithExpr lexpr = buildExpr(left, vars, dataRefs);
		ArithExpr rexpr = buildExpr(right, vars, dataRefs);
		BoolExpr expr = null;
		
		switch (pre.getOperator()) {
		case "eq":
			expr = context.mkEq(lexpr, rexpr);
			break;
		case "gt":
			expr = context.mkGt(lexpr, rexpr);
			break;
		case "ge":
			expr = context.mkGe(lexpr, rexpr);
			break;
		case "lt":
			expr = context.mkLt(lexpr, rexpr);
			break;
		case "le":
			expr = context.mkLe(lexpr, rexpr);
			break;
		case "neq":
			expr = context.mkEq(rexpr, lexpr);
			expr = context.mkNot(expr);
			break;
		}
		return expr;
		
	}
	
	private static ArithExpr buildExpr(Function func, IntExpr[] vars, DataRef[] dataRefs) throws Z3Exception {
		String name = func.getName();
		String type = func.getType();
		Function left = func.getLeftFunction();
		Function right = func.getRightFunction();
		ArithExpr expr = null, lexpr, rexpr;
		
		switch (name)
		{
		case "ret":
			if (type.equals("dataref")) {
				int index = 0;
				for (index = 0; index < dataRefs.length; index++ ) {
					if (dataRefs[index].equals(func.getDataRefs().toArray(new DataRef[1])[0])) {
						expr = vars[index];
					}
				}
			}
			else if (type.equals("value")) {
				expr = (ArithExpr) context.mkNumeral(func.getValue(), context.getIntSort());
			}
			break;
		case "+":
			lexpr = buildExpr(left, vars, dataRefs);
			rexpr = buildExpr(right, vars, dataRefs);
			expr = context.mkAdd(lexpr, rexpr);
			break;
		case "-":
			lexpr = buildExpr(left, vars, dataRefs);
			rexpr = buildExpr(right, vars, dataRefs);
			expr = context.mkSub(lexpr, rexpr);
			break;
		case "*":
			lexpr = buildExpr(left, vars, dataRefs);
			rexpr = buildExpr(right, vars, dataRefs);
			expr = context.mkMul(lexpr, rexpr);
			break;
		case "/":
			lexpr = buildExpr(left, vars, dataRefs);
			rexpr = buildExpr(right, vars, dataRefs);
			expr = context.mkDiv(lexpr, rexpr);
			break;
		}

		return expr;
	}

	public static class filterConditions {
		int dim;
		IntExpr[] vars;
		Sort[] types;
		Symbol[] symbols;
		DataRef[] dataRefs;
		
		public void initFilterConditions(DataRef[] dataRefs) {
			this.dataRefs = dataRefs;
			this.dim = dataRefs.length;
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
		}
		
		
		public BoolExpr getFilterCondition(Filter filter){
			try {
				return buildExpr(filter, vars, dataRefs);
			} catch (Z3Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public boolean satisfiable(BoolExpr condition1, BoolExpr condition2) {
			try {
				BoolExpr expr = context.mkAnd(condition1, condition2);
				BoolExpr condition = context.mkForall(types, symbols, expr, 0, null, null, null, null);
				Solver solver = context.mkSolver();
				solver.add(condition);
				Status status = solver.check();
				return status.equals(Status.SATISFIABLE);
			} catch (Z3Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static void initFilterConditions(DataRef[] dataRefs) {
		fc.initFilterConditions(dataRefs);
	}
	
	public static BoolExpr getFilterCondition(Filter filter) {
		return fc.getFilterCondition(filter);
	}

	public static boolean satisfiable(BoolExpr condition1, BoolExpr condition2) {
		return fc.satisfiable(condition1, condition2);
	}
}
