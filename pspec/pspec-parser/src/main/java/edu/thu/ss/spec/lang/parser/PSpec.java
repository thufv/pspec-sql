package edu.thu.ss.spec.lang.parser;

public class PSpec {
	public static enum PSpecEventType {
		Vocabulary_Category_Cycle_Reference,
		Vocabulary_Category_Parent_Not_Exist,
		Vocabulary_Category_Duplicate,
		Policy_Category_Ref_Not_Exist,
		Policy_Category_Exclude_Invalid,
		Policy_Single_Restriction_One_Desensitize,
		Policy_Single_Restriction_No_DataRef,
		Policy_Associate_Restriction_Explicit_DataRef,
		Policy_Associate_Restriction_DataRef_Not_Exist,
		Policy_Data_Association_Overlap,
		Policy_Restriction_One_Forbid,
		Policy_Single_One_Restriction,
		Policy_Restriction_Unsupported_Operation
	}

}
