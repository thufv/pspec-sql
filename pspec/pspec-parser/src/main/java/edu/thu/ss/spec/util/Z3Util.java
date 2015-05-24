package edu.thu.ss.spec.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import edu.thu.ss.spec.lang.analyzer.redundancy.BaseRedundancyAnalyzer;
import edu.thu.ss.spec.lang.expression.BinaryComparison;
import edu.thu.ss.spec.lang.expression.BinaryPredicate;
import edu.thu.ss.spec.lang.expression.Expression;
import edu.thu.ss.spec.lang.expression.Function;
import edu.thu.ss.spec.lang.expression.Term;
import edu.thu.ss.spec.lang.expression.BinaryPredicate.binaryPredicateTypes;
import edu.thu.ss.spec.lang.expression.Expression.ExpressionTypes;
import edu.thu.ss.spec.lang.expression.Term.TermTypes;
import edu.thu.ss.spec.lang.pojo.Condition;
import edu.thu.ss.spec.lang.pojo.DataCategory;
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

	private static Map<DataCategory, Integer> refIndex = new HashMap<>();
	
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
	
	public static boolean implies(Condition condition1, Condition condition2) {
		int dim = condition1.getDataCategories().size();
		if (dim > BaseRedundancyAnalyzer.Max_Dimension) {
			return false;
		}
		
		IntExpr[] vars = new IntExpr[dim];
		Sort[] types = new Sort[dim];
		Symbol[] symbols = new Symbol[dim];
		
		try {
			for (int i = 0; i < vars.length; i++) {
				symbols[i] = allSymbols[i];
				vars[i] = (IntExpr) context.mkBound(vars.length - i - 1, context.getIntSort());
				types[i] = context.getIntSort();
			}
			
			refIndex.clear();
			Set<DataCategory> set1 = condition1.getDataCategories();
			Set<DataCategory> set2 = condition2.getDataCategories();
			for (DataCategory category : set1) {
				int index = refIndex.size();
				refIndex.put(category, index);
			}
			for (DataCategory category : set2) {
				int index = refIndex.size();
				refIndex.put(category, index);
			}
			
			BoolExpr pre = buildExpression(condition1.getExpression(), vars);
			BoolExpr post = buildExpression(condition2.getExpression(), vars);
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
	
	private static BoolExpr buildExpression(Expression<DataCategory> expression, IntExpr[] vars) throws Z3Exception {
		ExpressionTypes type = expression.getExpressionType();
		if (Expression.ExpressionTypes.binaryComparison.equals(type)) {
			return buildExpression((BinaryComparison) expression, vars);
		}
		else if (Expression.ExpressionTypes.binaryPredicate.equals(type)) {
			return buildExpression((BinaryPredicate) expression, vars);
		}
		return null;
	}
	
	private static BoolExpr buildExpression(BinaryPredicate expression, IntExpr[] vars) throws Z3Exception {
		List<Expression<DataCategory>> expressions = expression.getExpressionList();
		binaryPredicateTypes type = expression.getPredicateType();
		
		if (BinaryPredicate.binaryPredicateTypes.not.equals(type)) {
			return context.mkNot(buildExpression(expressions.get(0), vars));
		}
		
		List<BoolExpr> exprs = new ArrayList<>();
		for (Expression<DataCategory> expr : expressions) {
			exprs.add(buildExpression(expr, vars));
		}
		if (BinaryPredicate.binaryPredicateTypes.and.equals(type)) {
			return context.mkAnd(exprs.toArray(new BoolExpr[exprs.size()]));
		}
		else if (BinaryPredicate.binaryPredicateTypes.or.equals(type)) {
			return context.mkOr(exprs.toArray(new BoolExpr[exprs.size()]));
		}
		return null;
	}
	
	private static BoolExpr buildExpression(BinaryComparison expression, IntExpr[] vars) throws Z3Exception {
		Expression<DataCategory> left = expression.getLeftExpression();
		Expression<DataCategory> right = expression.getRightExpression();
		String type = expression.getComparisonType().toString();
		
		ArithExpr lexpr = buildArithExpr(left, vars);
		ArithExpr rexpr = buildArithExpr(right, vars);
		BoolExpr expr = null;
		switch(type) {
		case "EqualTo":
			expr = context.mkEq(lexpr, rexpr);
			break;
		case "LessThan":
			expr = context.mkLt(lexpr, rexpr);
			break;
		case "LessThanOrEqual":
			expr = context.mkLe(lexpr, rexpr);
			break;
		case "GreaterThan":
			expr = context.mkGt(lexpr, rexpr);
			break;
		case "GreaterThanOrEqual":
			expr = context.mkGe(lexpr, rexpr);
			break;
		}
		
		return expr;
	}
	
	private static ArithExpr buildArithExpr(Expression<DataCategory> expression, IntExpr[] vars) throws Z3Exception {
		ExpressionTypes type = expression.getExpressionType();
		if (Expression.ExpressionTypes.function.equals(type)) {
			return buildArithExpr((Function) expression, vars);
		}
		else if (Expression.ExpressionTypes.term.equals(type)) {
			return buildArithExpr((Term) expression, vars);
		}
		return null;
	}
	
	private static ArithExpr buildArithExpr(Function func, IntExpr[] vars) throws Z3Exception {
		Expression<DataCategory> left = func.getLeftExpression();
		Expression<DataCategory> right = func.getRightExpression();
		String type = func.getFunctionType().toString();
		
		ArithExpr lexpr = buildArithExpr(left, vars);
		ArithExpr rexpr = buildArithExpr(right, vars);
		ArithExpr expr = null;
		switch(type) {
		case "add":
			expr = context.mkAdd(lexpr, rexpr);
			break;
		case "subtract":
			expr = context.mkSub(lexpr, rexpr);
			break;
		case "multiply":
			expr = context.mkMul(lexpr, rexpr);
			break;
		case "divide":
			expr = context.mkDiv(lexpr, rexpr);
			break;
		case "remainder":
			expr = context.mkMod((IntExpr)lexpr, (IntExpr)rexpr);
			break;
		}
		
		return expr;
	}
	
	private static ArithExpr buildArithExpr(Term term, IntExpr[] vars) throws Z3Exception {
		TermTypes type = term.getTermType();
		ArithExpr expr = null;
		
		if (Term.TermTypes.value.equals(type)) {
			expr = (ArithExpr) context.mkNumeral(term.getData(), context.getIntSort());
		}
		else if (Term.TermTypes.dataCategory.equals(type)) {
			int index = refIndex.get(term.getDataCategory());
			expr = vars[index];
		}
		return expr;
	}
}
