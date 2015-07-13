package edu.thu.ss.spec.lang.parser.event;

import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryRef;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;

public abstract class PSpecListener {

	public enum VocabularyErrorType {
		Cycle_Reference, Category_Cycle_Reference, Category_Parent_Not_Exist, Category_Duplicate
	}

	public enum RefErrorType {
		Category_Ref_Not_Exist,
		Category_Exclude_Invalid,
		Category_Exclude_Not_Exist,
		Data_Association_Overlap
	}

	public enum RestrictionErrorType {
		Single_Restriction_One_Desensitize,
		Single_Restriction_No_DataRef,
		Associate_Restriction_Explicit_DataRef,
		Associate_Restriction_DataRef_Not_Exist,
		One_Forbid,
		Single_One_Restriction,
		Unsupported_Operation
	}

	public void onVocabularyError(VocabularyErrorType type, Category<?> category, String refid) {

	}

	public void onRuleRefError(RefErrorType type, Rule rule, CategoryRef<?> ref, String refid) {

	}

	public void onRestrictionError(RestrictionErrorType type, Rule rule, Restriction res, String refid) {

	}

}
