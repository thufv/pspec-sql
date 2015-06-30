package org.apache.spark.sql.catalyst.checker.dp

import com.microsoft.z3.Context
import com.microsoft.z3.BoolExpr
import com.microsoft.z3.ArithExpr
import com.microsoft.z3.Solver

object Z3Example {

  def main(args: Array[String]) {
    val ctx = new Context();

    val a = ctx.mkConst("a", ctx.getRealSort()).asInstanceOf[ArithExpr];
    val b = ctx.mkConst("b", ctx.mkRealSort()).asInstanceOf[ArithExpr];
    val c = ctx.mkConst("c", ctx.mkRealSort()).asInstanceOf[ArithExpr];

    val cond1 = ctx.mkOr(ctx.mkAnd(ctx.mkGt(a, ctx.mkReal(5)), ctx.mkLt(b, ctx.mkReal(10))), ctx.mkLt(a, ctx.mkReal(0)));
    val cond2 = ctx.mkAnd(ctx.mkGt(a, ctx.mkReal(2)));

    val cond = ctx.mkAnd(cond1, cond2);

    val solver = ctx.mkSolver();
    solver.add(cond);
    val status = solver.check();
    
    println(solver.getHelp());
    System.out.println(status);

    ctx.dispose();
  }

  def mainJoin(args: Array[String]) {
    val ctx = new Context();
    val t1a = ctx.mkConst("t1a", ctx.getRealSort()).asInstanceOf[ArithExpr];
    val t2a = ctx.mkConst("t2a", ctx.mkRealSort()).asInstanceOf[ArithExpr];

    val t3a = ctx.mkConst("t3a", ctx.mkRealSort()).asInstanceOf[ArithExpr];
    val ta = ctx.mkConst("ta", ctx.mkRealSort()).asInstanceOf[ArithExpr];

    val cond1 = ctx.mkAnd(ctx.mkGt(t1a, ctx.mkReal(5)), ctx.mkLt(t2a, ctx.mkReal(10)));
    val cond2 = ctx.mkAnd(ctx.mkGt(t3a, ctx.mkReal(20)));
    val cond3 = ctx.mkOr(ctx.mkEq(ta, t1a), ctx.mkEq(ta, t2a));
    val cond4 = ctx.mkEq(ta, t3a);
    val cond = ctx.mkAnd(cond1, cond2, cond3, cond4);

    val solver = ctx.mkSolver();
    solver.add(cond);
    val status = solver.check();

    System.out.println(status);

    ctx.dispose();
  }

  def mainExcept(args: Array[String]) {
    val ctx = new Context();

    val t1a = ctx.mkConst("t1a", ctx.getRealSort()).asInstanceOf[ArithExpr];
    val t2a = ctx.mkConst("t2a", ctx.mkRealSort()).asInstanceOf[ArithExpr];

    val t3a = ctx.mkConst("t3a", ctx.mkRealSort()).asInstanceOf[ArithExpr];
    val ta = ctx.mkConst("ta", ctx.mkRealSort()).asInstanceOf[ArithExpr];

    val cond1 = ctx.mkAnd(ctx.mkGt(t1a, ctx.mkReal(5)), ctx.mkNot(ctx.mkGt(t2a, ctx.mkReal(10))), ctx.mkEq(t1a, t2a));
    val cond2 = ctx.mkGt(t3a, ctx.mkReal(15));
    val cond3 = ctx.mkOr(ctx.mkEq(ta, t1a), ctx.mkEq(ta, t2a));
    val cond4 = ctx.mkEq(ta, t3a);
    val cond = ctx.mkAnd(cond1, cond2, cond3, cond4);

    val solver = ctx.mkSolver();
    solver.add(cond);
    val status = solver.check();
    System.out.println(status);

    ctx.dispose();
  }

  def mainn(args: Array[String]) {
    val ctx = new Context();

    val t1 = ctx.mkConst("t", ctx.getRealSort()).asInstanceOf[ArithExpr];
    val t2 = ctx.mkConst("t", ctx.mkRealSort()).asInstanceOf[ArithExpr];

    val cond = ctx.mkAnd(ctx.mkEq(t1, ctx.mkReal(1)), ctx.mkEq(t2, ctx.mkReal(2)));

    val solver = ctx.mkSolver();
    solver.add(cond);
    val status = solver.check();
    System.out.println(status);

    ctx.dispose();
  }

}