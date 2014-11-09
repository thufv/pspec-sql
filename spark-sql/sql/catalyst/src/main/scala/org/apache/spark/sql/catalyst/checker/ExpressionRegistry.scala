package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.expressions._

object ExpressionRegistry extends LabelConstants {

	def resolvePredicate(predicate: Expression): String = {
		predicate match {
			case _: EqualNullSafe => Pred_Equal;
			case _: EqualTo => Pred_Equal;
			case _: GreaterThan => Pred_Greater;
			case _: GreaterThanOrEqual => Pred_GreaterEqual;
			case _: LessThan => Pred_Less;
			case _: LessThanOrEqual => Pred_LessEqual;
			case _: Contains => Pred_Contains;
			case _: Like => Pred_Like;
			case _: RLike => Pred_RLike;
			case _: StartsWith => Pred_StartsWtih;
			case _: In => Pred_In;
		}
	}

	def resolveFunction(func: Expression): String = {
		func match {
			case arith: BinaryArithmetic => resolve(arith);
			case unary: UnaryExpression => resolve(unary);
			case agg: AggregateExpression => resolve(agg);
			case _: MaxOf => Func_MaxOf;
			case _: Substring => Func_Substr;
			case _: CaseWhen => Func_Case;
			case _: If => Func_If;
		}
	}

	private def resolve(arithmetic: BinaryArithmetic): String = {
		arithmetic match {
			case _: Add => Arithmetic_Add;
			case _: Divide => Arithmetic_Divide;
			case _: Multiply => Arithmetic_Multiply;
			case _: Remainder => Arithmetic_Remainder;
			case _: Subtract => Arithmetic_Subtract;
			case _ => throw new UnsupportedPlanException(s"unknown arithmetic expression: $arithmetic");
		}
	}

	private def resolve(unary: UnaryExpression): String = {
		unary match {
			case _: Cast => Func_Cast;
			case _: CountSet => null;
			case _: GetField => null;
			case _: Lower => Func_Lower;
			case _: Not => null;
			case _: Upper => Func_Upper;
			case _: UnaryMinus => Arithmetic_UnaryMinus;
		}
	}

	private def resolve(aggregate: AggregateExpression): String = {
		aggregate match {
			case _: ApproxCountDistinctMerge => Func_ApproximateCount;
			case _: ApproxCountDistinctPartition => Func_ApproximateCount;
			case _: CollectHashSet => Func_AddToHashSet;
			case _: CombineSetsAndCount => Func_CombineAndCount;
			case _: SumDistinct => Func_Sum;
			case _: ApproxCountDistinct => Func_Count;
			case _: Average => Func_Avg;
			case _: Count => Func_Count;
			case _: CountDistinct => Func_Count;
			case _: First => Func_First;
			case _: Max => Func_Max;
			case _: Min => Func_Min;
			case _: Sum => Func_Sum;
			case _ => aggregate.getName;
		}
	}
}