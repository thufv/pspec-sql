package org.apache.spark.sql.catalyst.checker

import edu.thu.ss.spec.lang.pojo.DesensitizeOperation
import edu.thu.ss.spec.global.MetaManager

object LabelConstants extends LabelConstants {

}

trait LabelConstants {

  val Arithmetic_Add = "+";
  val Arithmetic_Divide = "/";
  val Arithmetic_Multiply = "*";
  val Arithmetic_Remainder = "%";
  val Arithmetic_Subtract = "-";
  val Arithmetic_UnaryMinus = "UnaryMinus";
  val Arithmetic_Abs = "Abs";

  val Func_Dummy = "";

  val Func_Upper = "Upper";
  val Func_Lower = "Lower";

  val Func_ApproximateCount = "APPROXIMATE COUNT";
  val Func_AddToHashSet = "AddToHashSet";
  val Func_CombineAndCount = "CombineAndCount";
  val Func_Sum = "SUM";
  val Func_Avg = "AVG";
  val Func_Count = "COUNT";
  val Func_First = "FIRST";
  val Func_Max = "MAX";
  val Func_Min = "MIN";
  val Func_MaxOf = "MaxOf";
  val Func_Substr = "SUBSTR";
  val Func_Case = "CASE";
  val Func_If = "IF";
  val Func_Cast = "CAST";
  val Func_Coalesce = "COALESCE";
  val Func_Intersect = "INTERSECT";
  val Func_Union = "UNION";
  val Func_Except = "EXCEPT";

  val Func_GetField = "GetField";
  val Func_GetItem = "GetItem";
  val Func_GetEntry = "GetEntry";

  def isGetItem(trans: String) = trans == Func_GetItem;

  def isGetField(trans: String) = trans.startsWith(Func_GetField);

  def isGetEntry(trans: String) = trans.startsWith(Func_GetEntry);

  def getSubType(trans: String): String = {
    val strs = trans.split("\\.");
    if (strs.length > 1) {
      strs(1);
    } else {
      "";
    }
  }

  def isSubtypeOperation(trans: String) = isGetItem(trans) || isGetField(trans) || isGetEntry(trans) || MetaManager.isExtractOperation(trans);

  val Pred_Equal = "=";
  val Pred_Greater = ">";
  val Pred_GreaterEqual = ">=";
  val Pred_Less = "<";
  val Pred_LessEqual = "<=";
  val Pred_Contains = "CONTAINS";
  val Pred_EndsWith = "EndsWith";
  val Pred_Like = "LIKE";
  val Pred_RLike = "RLIKE";
  val Pred_StartsWtih = "StartsWith";
  val Pred_In = "IN";
  val Pred_IsNull = "IsNull";
  val Pred_IsNotNull = "IsNotNull";

  val Func_Aggregates = Set(Func_Count, Func_Sum, Func_Avg, Func_Min, Func_Max);

  val Op_Aggregates = Func_Aggregates.map(DesensitizeOperation.get(_));

  val Func_SetOperations = Set(Func_Intersect, Func_Union, Func_Except);

  val Func_SubTypeOperations = Set(Func_GetField, Func_GetItem, Func_GetEntry);

  private val Ignorables = Func_SetOperations;

  private val Skippables = Func_SubTypeOperations ++ Func_SetOperations;

  def ignorable(transform: String) = Ignorables.contains(transform);

  def skippable(transform: String) = Skippables.exists(transform.startsWith(_));

}