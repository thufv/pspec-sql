package edu.thu.ss.spec.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Status;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Z3Exception;

import edu.thu.ss.spec.lang.analyzer.BaseRedundancyAnalyzer;
import edu.thu.ss.spec.lang.expression.BinaryComparison;
import edu.thu.ss.spec.lang.expression.BinaryPredicate;
import edu.thu.ss.spec.lang.expression.BinaryPredicate.binaryPredicateTypes;
import edu.thu.ss.spec.lang.expression.Expression;
import edu.thu.ss.spec.lang.expression.Expression.ExpressionTypes;
import edu.thu.ss.spec.lang.expression.Function;
import edu.thu.ss.spec.lang.expression.Term;
import edu.thu.ss.spec.lang.expression.Term.TermTypes;
import edu.thu.ss.spec.lang.pojo.Condition;
import edu.thu.ss.spec.lang.pojo.DataRef;

public class FilterZ3Util extends Z3Util {
	
	private Map<DataRef, Integer> refIndex = new HashMap<>();
	
	public ConsistencyChecker checker = new ConsistencyChecker();
	
	public FilterZ3Util() {
		
	}
	
	public void init(Set<DataRef> dataRefs) {
		int dim = dataRefs.size();
		super.init(dim);
		addDataRefIndex(dataRefs);		
	}
	
	public void addDataRefIndex(Set<DataRef> dataRefs) {
		for (DataRef dataRef : dataRefs) {
			if (!refIndex.containsKey(dataRef)) {
				refIndex.put(dataRef, refIndex.size());
			}
		}
	}
	
	public void addDataRefIndex(DataRef dataRef) {
		if (!refIndex.containsKey(dataRef)) {
			refIndex.put(dataRef, refIndex.size());
		}
	}
	
	public class ConsistencyChecker {
		private IntExpr[] vars;
		private Sort[] types;
		private Symbol[] symbols;
		
		public ConsistencyChecker() {
			refIndex.clear();	
		}
		
		public void init (int dim) {
			FilterZ3Util.super.init(dim);
			refIndex.clear();	
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
				e.printStackTrace();
			}
		}
		
		public BoolExpr buildExpression(Condition filter) {
			Expression<DataRef> expression = filter.getExpression();
			BoolExpr expr = null;
			try {
				Set<DataRef> dataRef = expression.getDataSet();
				addDataRefIndex(dataRef);
				expr = FilterZ3Util.this.buildExpression(expression, this.vars);
			} catch (Z3Exception e) {
				e.printStackTrace();
			}
			return expr;
		}
		
		public boolean satisfiable(List<BoolExpr> expressions) {
			try {
				BoolExpr expr;
				if (expressions.size() > 1) {
					expr = context.mkAnd(expressions.toArray(new BoolExpr[expressions.size()]));
				}
				else {
					expr = expressions.get(0);
				}
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
	}
	
	
	/*
	 * filter1 implies filter2
	 */
	public boolean implies(Condition filter1, Condition filter2) {
		int dim = filter1.getDataRefs().size();
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
			
			BoolExpr pre = buildExpression(filter1.getExpression(), vars);
			BoolExpr post = buildExpression(filter2.getExpression(), vars);
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
	
	private BoolExpr buildExpression(Expression expression, IntExpr[] vars) throws Z3Exception {
		ExpressionTypes type = expression.getExpressionType();
		if (Expression.ExpressionTypes.binaryComparison.equals(type)) {
			return buildExpression((BinaryComparison) expression, vars);
		}
		else if (Expression.ExpressionTypes.binaryPredicate.equals(type)) {
			return buildExpression((BinaryPredicate) expression, vars);
		}
		return null;
	}
	
	private BoolExpr buildExpression(BinaryPredicate expression, IntExpr[] vars) throws Z3Exception {
		List<Expression<DataRef>> expressions = expression.getExpressionList();
		binaryPredicateTypes type = expression.getPredicateType();
		
		if (BinaryPredicate.binaryPredicateTypes.not.equals(type)) {
			return context.mkNot(buildExpression(expressions.get(0), vars));
		}
		
		List<BoolExpr> exprs = new ArrayList<>();
		for (Expression<DataRef> expr : expressions) {
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
	
	private BoolExpr buildExpression(BinaryComparison expression, IntExpr[] vars) throws Z3Exception {
		Expression<DataRef> left = expression.getLeftExpression();
		Expression<DataRef> right = expression.getRightExpression();
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
	
	private ArithExpr buildArithExpr(Expression expression, IntExpr[] vars) throws Z3Exception {
		ExpressionTypes type = expression.getExpressionType();
		if (Expression.ExpressionTypes.function.equals(type)) {
			return buildArithExpr((Function) expression, vars);
		}
		else if (Expression.ExpressionTypes.term.equals(type)) {
			return buildArithExpr((Term) expression, vars);
		}
		return null;
	}
	
	private ArithExpr buildArithExpr(Function func, IntExpr[] vars) throws Z3Exception {
		Expression<DataRef> left = func.getLeftExpression();
		Expression<DataRef> right = func.getRightExpression();
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
	
	private ArithExpr buildArithExpr(Term term, IntExpr[] vars) throws Z3Exception {
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
