package edu.thu.ss.editor.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesUtil {

	private static ResourceBundle bundle = ResourceBundle.getBundle("message", Locale.US);

	public static final String Editor_Name = "editor.name";

	public static final String File = "file";
	public static final String New = "new";
	public static final String Add = "add";
	public static final String Add_Child = "add.child";
	public static final String Delete = "delete";

	public static final String Confirm_Close = "confirm.close";
	public static final String Confirm_Close_Message = "confirm.close.message";

	public static final String Confirm_Exit = "confirm.exit";
	public static final String Confirm_Exit_Message = "confirm.exit.message";
	public static final String Close = "close";

	public static final String Clear = "clear";
	public static final String Visualize = "visualize";

	public static final String Open = "open";
	public static final String Open_Vocabulary = "open.vocabulary";
	public static final String Open_Policy = "open.policy";

	public static final String New_Vocabulary = "new.vocabulary";
	public static final String New_Policy = "new.policy";
	public static final String Save = "save";
	public static final String Save_As = "save.as";
	public static final String Save_Vocabulary = "save.vocabulary";
	public static final String Save_Policy = "save.policy";
	public static final String Exit = "exit";
	public static final String Edit = "edit";
	public static final String Help = "help";
	public static final String Help_Content = "help.content";
	public static final String About = "about";
	public static final String OK = "ok";
	public static final String Cancel = "cancel";
	public static final String Error = "error";
	public static final String Warning = "warning";
	public static final String Location = "location";
	public static final String Description = "description";
	public static final String Type = "type";
	public static final String Goto = "goto";
	public static final String Fix = "fix";
	public static final String Analysis = "analysis";
	public static final String Redundancy = "redundancy";
	public static final String Simplify = "simplify";
	public static final String Consistency = "consistency";
	public static final String NormalConsistency = "normal.consistency";
	public static final String ApproximateConsistency = "approximate.consistency";
	public static final String StrongConsistency = "strong.consistency";
	public static final String EnhancedStrongConsistency = "enhanced.strong.consistency";
	public static final String Select_As_Seed = "select.as.seed";

	public static final String Base_Vocabulary = "base.vocabulary";
	public static final String Base_Vocabulary_Contains_Error_Message = "base.vocabulary.contains.error.message";
	public static final String Vocabulary = "vocabulary";
	public static final String Vocabulary_Output = "vocabulary.output";
	public static final String Vocabulary_Ref = "vocabulary.ref";
	public static final String Vocabulary_Info = "vocabulary.info";
	public static final String Vocabulary_ID = "vocabulary.id";
	public static final String Vocabulary_Location = "vocabulary.location";
	public static final String Vocabulary_Issuer = "issuer";
	public static final String Vocabulary_Save_Error_Message = "vocabulary.save.error.message";
	public static final String Vocabulary_ID_Empty_Message = "vocabulary.id.empty.message";
	public static final String Vocabulary_Opened_Message = "vocabulary.opened.message";
	public static final String Vocabulary_Save_Success_Message = "vocabulary.save.success.message";
	public static final String Vocabulary_Cycle_Reference_Message = "vocabulary.cycle.reference.message";
	public static final String Vocabulary_Invalid_Document_Message = "vocabulary.invalid.document.message";
	public static final String Vocabulary_Parse_Error_Message = "vocabulary.parse.error.message";

	public static final String Issuer = "issuer";
	public static final String Issuer_Name = "issuer.name";
	public static final String Issuer_Organization = "issuer.organization";
	public static final String Issuer_Email = "issuer.email";
	public static final String Issuer_Address = "issuer.address";
	public static final String Issuer_Country = "issuer.country";

	public static final String Desensitize = "desensitize";
	public static final String Desensitize_Operation = "desensitize.operation";
	public static final String Desensitize_Operation_Empty_Message = "desensitize.operation.empty.message";
	public static final String Desensitize_Operation_Unique_Message = "desensitize.operation.unique.message";

	public static final String Short_Description = "short.description";
	public static final String Long_Description = "long.description";

	public static final String Base_ID = "base.id";
	public static final String Basic_Info = "basic.info";

	public static final String User_Container = "user.container";
	public static final String User_Container_ID = "user.container.id";
	public static final String User_Ref = "user.ref";
	public static final String User_Category = "user.category";
	public static final String User_Category_ID = "user.category.id";
	public static final String User_Category_Parent_ID = "user.category.parent.id";
	public static final String User_Category_Duplicate_Message = "user.category.duplicate.message";
	public static final String User_Container_ID_Not_Empty_Message = "user.container.id.not.empty.message";
	public static final String User_Category_ID_Not_Empty_Message = "user.category.id.not.empty.message";
	public static final String User_Category_ID_Unique_Message = "user.category.id.unique.message";
	public static final String User_Category_Parent_Same_Message = "user.category.parent.same.message";
	public static final String User_Category_Parent_Cycle_Message = "user.category.parent.cycle.message";
	public static final String User_Category_Exclude_Unique_Message = "user.category.exclude.unique.message";
	public static final String User_Category_Not_Excluded_Message = "user.category.not.excluded.message";
	public static final String User_Category_Exclude_Not_Exist_Message = "user.category.exclude.not.exist.message";
	public static final String User_Category_Exclude_Invalid_Message = "user.category.exclude.invalid.message";
	public static final String User_Category_Not_Exist_Message = "user.category.not.exist.message";
	public static final String User_Category_Parent_Not_Exist_Message = "user.category.parent.not.exist.message";

	public static final String Data_Ref = "data.ref";
	public static final String Data_Association = "data.association";
	public static final String Data_Container = "data.container";
	public static final String Data_Container_ID = "data.container.id";
	public static final String Data_Category = "data.category";
	public static final String Data_Category_ID = "data.category.id";
	public static final String Data_Category_Parent_ID = "data.category.parent.id";
	public static final String Data_Category_Duplicate_Message = "data.category.duplicate.message";
	public static final String Data_Container_ID_Not_Empty_Message = "data.container.id.not.empty.message";
	public static final String Data_Category_ID_Not_Empty_Message = "data.category.id.not.empty.message";
	public static final String Data_Category_ID_Unique_Message = "data.category.id.unique.message";
	public static final String Data_Category_Parent_Same_Message = "data.category.parent.same.message";
	public static final String Data_Category_Parent_Cycle_Message = "data.category.parent.cycle.message";
	public static final String Data_Category_Exclude_Unique_Message = "data.category.exclude.unique.message";
	public static final String Data_Category_Not_Excluded_Message = "data.category.exclude.not.exist.message";
	public static final String Data_Category_Exclude_Not_Exist_Message = "data.category.exclude.not.exist.message";
	public static final String Data_Category_Exclude_Invalid_Message = "data.category.exclude.invalid.message";
	public static final String Data_Category_Not_Exist_Message = "data.category.not.exist.message";
	public static final String Data_Category_Parent_Not_Exist_Message = "data.category.parent.not.exist.message";

	public static final String Policy = "policy";
	public static final String Policy_Output = "policy.output";
	public static final String Policy_Save_Error_Message = "policy.save.error.message";
	public static final String Policy_Vocabulary_Contains_Error_Message = "policy.vocabulary.contains.error.message";
	public static final String Policy_ID = "policy.id";
	public static final String Policy_Info = "policy.info";
	public static final String Policy_Rules = "policy.rules";
	public static final String Policy_No_Vocabulary_Message = "policy.no.vocabulary.message";
	public static final String Policy_Opened_Message = "policy.opened.message";
	public static final String Policy_Save_Success_Message = "policy.save.success.message";
	public static final String Policy_Invalid_Document_Message = "policy.invalid.document.message";
	public static final String Policy_Parse_Error_Message = "policy.parse.error.message";
	public static final String Policy_Invalid_Vocabulary_Document_Message = "policy.invalid.vocabulary.document.message";
	public static final String Policy_No_Simplify_Message = "policy.no.simplify.message";
	public static final String Policy_Simplify_Prompt_Message = "policy.simplify.prompt.message";
	public static final String Policy_No_Redundancy_Message = "policy.no.redundancy.message";
	public static final String Policy_Redundancy_Message = "policy.redundancy.message";
	public static final String Policy_No_Normal_Inconsistency_Message = "policy.no.normal.inconsistency.message";
	public static final String Policy_Normal_Inconsistency_Message = "policy.normal.inconsistency.message";
	public static final String Policy_No_Approximate_Inconsistency_Message = "policy.no.approximate.inconsistency.message";
	public static final String Policy_Approximate_Inconsistency_Message = "policy.approximate.inconsistency.message";
	public static final String Policy_No_Strong_Inconsistency_Message = "policy.no.strong.inconsistency.message";
	public static final String Policy_Strong_Inconsistency_Message = "policy.strong.inconsistency.message";
	public static final String Policy_No_Enhanced_Strong_Inconsistency_Message = "policy.no.enhanced.strong.inconsistency.message";
	public static final String Policy_Enhanced_Strong_Inconsistency_Message = "policy.enhanced.strong.inconsistency.message";
	public static final String Policy_Seed_No_Strong_Inconsistency_Message = "policy.seed.no.strong.inconsistency.message";
	public static final String Policy_Seed_Strong_Inconsistency_Message = "policy.seed.strong.inconsistency.message";
	public static final String Policy_Seed_No_Enhanced_Strong_Inconsistency_Message = "policy.seed.no.enhanced.strong.inconsistency.message";
	public static final String Policy_Seed_Enhanced_Strong_Inconsistency_Message = "policy.seed.enhanced.strong.inconsistency.message";

	public static final String Rule = "rule";
	public static final String Rule_Type = "rule.type";
	public static final String Rule_ID = "rule.id";
	public static final String Rule_Error_Message = "rule.error.message";
	public static final String Rule_ID_Unique_Message = "rule.id.unique.message";
	public static final String Rule_ID_Not_Empty_Message = "rule.id.not.empty.message";
	public static final String Rule_User_Ref_Not_Empty_Message = "rule.user.ref.not.empty.message";
	public static final String Rule_User_Ref_Unique_Message = "rule.user.ref.unique.message";
	public static final String Rule_Data_Ref_Not_Empty_Message = "rule.data.ref.not.empty.message";
	public static final String Rule_Data_Ref_Unique_Message = "rule.user.ref.unique.message";
	public static final String Rule_Desensitize_Operation_Unique_Message = "rule.desensitize.operation.unique.message";
	public static final String Rule_Desensitize_Operation_Not_Exist_Message = "rule.desensitize.operation.not.exist.message";
	public static final String Rule_Restriction_Not_Empty_Message = "rule.restriction.not.empty.message";
	public static final String Rule_Data_Association_Not_Overlap_Message = "rule.data.association.not.overlap.message";
	public static final String Rule_Restriction_Effective_Message = "rule.restriction.effective.message";
	@ParseOnly
	public static final String Rule_Restriction_DataRef_Not_Exist_Message = "rule.restriction.dataref.not.exist.message";
	@ParseOnly
	public static final String Rule_Restriction_Explicit_DataRef_Message = "rule.restriction.explicit.dataref.message";
	@ParseOnly
	public static final String Rule_Restriction_One_Forbid_Message = "rule.restriction.one.forbid.message";
	public static final String Rule_Restriction_Unsupported_Operation_Message = "rule.restriction.unsupported.operation.message";
	@ParseOnly
	public static final String Rule_Restriction_Single_One_Message = "rule.restriction.single.one.message";
	@ParseOnly
	public static final String Rule_Restriction_Single_No_DataRef_Message = "rule.restriction.single.no.dataref.message";
	@ParseOnly
	public static final String Rule_Restriction_Single_One_Desensitize_Message = "rule.restriction.single.one.desensitize.message";

	public static final String Rule_Restriction_None = "rule.restriction.none";

	public static final String Rule_No_Simplify_Message = "rule.no.simplify.message";
	public static final String Rule_Simplify_Prompt_Message = "rule.simplify.prompt.message";
	public static final String Rule_User_Ref_Simplify_Message = "rule.user.ref.simplify.message";
	public static final String Rule_Data_Ref_Simplify_Message = "rule.data.ref.simplify.message";
	public static final String Rule_Restriction_Simplify_Message = "rule.restriction.simplify.message";
	public static final String Rule_Redundancy_Message = "rule.redundancy.message";
	public static final String Rule_Normal_Inconsistency_Message = "rule.normal.inconsistency.message";
	public static final String Rule_Approximate_Inconsistency_Message = "rule.approximate.inconsistency.message";
	public static final String Rule_Strong_Inconsistency_Message = "rule.strong.inconsistency.message";
	public static final String Rule_Enhanced_Strong_Inconsistency_Message = "rule.enhanced.strong.inconsistency.message";

	public static final String Rule_No_Strong_Inconsistency_Message = "rule.enhanced.strong.inconsistency.message";
	public static final String Rule_No_Enhanced_Strong_Inconsistency_Message = "rule.enhanced.strong.inconsistency.message";
	
	public static final String Visualization = "visualization";
	public static final String ScopeRelation = "scope.relation";

	public static final String Metadata = "metadata";
	
	public static final String Forbid = "forbid";
	public static final String Restriction = "restriction";
	public static final String Restrict = "restrict";

	public static final String Output_Message = "output.message";

	public static final String Exclude = "exclude";
	public static final String ACTION = "action";
	public static final String Single = "single";
	public static final String Association = "association";

	public static final String Delete_User = "delete.user";
	public static final String Delete_User_Message = "delete.user.message";

	public static final String Delete_Data = "delete.data";
	public static final String Delete_Data_Message = "delete.data.message";

	public static String getMessage(String key, Object... contents) {
		try {
			String msg = bundle.getString(key);
			return MessageFormat.format(msg, contents);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static @interface ParseOnly {

	}
}
