package org.apache.spark.sql.catalyst.checker.dp

import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import scala.collection.mutable.ListBuffer

object DNFRewriter {
  def apply(expr: Expression): Seq[Expression] = {
    val newExpr = pushNot(expr);
    val dnf = rewrite(newExpr);
    return collect(dnf, classOf[Or]);
  }

  private def pushNot(expr: Expression): Expression = {
    expr match {
      case not: Not => {
        val child = not.child;
        child match {
          case not2: Not => pushNot(not2.child);
          case and: And => Or(pushNot(Not(and.left)), pushNot(Not(and.right)));
          case or: Or => And(pushNot(Not(or.left)), pushNot(Not(or.right)));
          case _ => not;
        }
      }
      case and: And => And(pushNot(and.left), pushNot(and.right));
      case or: Or => Or(pushNot(or.left), pushNot(or.right));
      case _ => expr;
    }
  }

  private def rewrite(expr: Expression): Expression = {
    expr match {
      case or: Or => Or(rewrite(or.left), rewrite(or.right));
      case and: And => {
        val left = rewrite(and.left);
        val right = rewrite(and.right);
        left match {
          case lor: Or => {
            right match {
              case ror: Or => {
                val a1 = rewrite(And(lor.left, ror.left));
                val a2 = rewrite(And(lor.left, ror.right));
                val a3 = rewrite(And(lor.right, ror.left));
                val a4 = rewrite(And(lor.right, ror.right));
                Or(Or(a1, a2), Or(a3, a4));
              }
              case _ => {
                val a1 = rewrite(And(lor.left, right));
                val a2 = rewrite(And(lor.right, right));
                Or(a1, a2);
              }
            }
          }
          case _ => {
            right match {
              case ror: Or => {
                val a1 = rewrite(And(left, ror.left));
                val a2 = rewrite(And(left, ror.right));
                Or(a1, a2);
              }
              case _ => And(left, right);
            }
          }
        }
      }
      case _ => expr;
    }
  }

  def main(args: Array[String]) {
    val t = Literal(true);
    val f = Literal(false);
    val expr = And(And(And(Or(t, f), t), Or(t, t)), t);

    val exprs = apply(expr);
    exprs.foreach(println(_));
  }

}