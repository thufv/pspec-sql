package edu.thu.ss.spec.lang.parser;

public class PSpec {
	private static int seq = 0;

	public static final int Vocabulary_Category_Cycle_Reference = 0;

	public static final int Policy_Category_Ref_Not_Exist = 1;

	public static final int Vocabulary_Category_Parent_Not_Exist = 2;

	public static final int Vocabulary_Category_Duplicate = 3;

	public static final int Policy_Category_Exclude_Invalid = 4;

	public static final int Policy_Single_Restriction_One_Desensitize = 5;

	public static final int Policy_Single_Restriction_No_DataRef = 6;

	public static final int Policy_Associate_Restriction_Explicit_DataRef = 7;

	public static final int Policy_Associate_Restriction_DataRef_Not_Exist = 8;

	public static final int Policy_Data_Association_Overlap = 9;

	public static final int Policy_Restriction_One_Forbid = 10;

	public static final int Policy_Single_One_Restriction = 11;

	public static final int Policy_Restriction_Unsupported_Operation = 12;

}
