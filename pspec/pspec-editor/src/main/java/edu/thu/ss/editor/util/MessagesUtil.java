package edu.thu.ss.editor.util;

import java.text.Format;
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
	public static final String Confirm_Exit = "confirm.exit";
	public static final String Confirm_Exit_Message = "confirm.exit.message";

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
	public static final String About = "about";
	public static final String OK = "ok";
	public static final String Cancel = "cancel";
	public static final String Error = "error";
	public static final String Warning = "warning";
	public static final String Description = "description";
	public static final String Type = "type";

	public static final String Vocabulary = "vocabulary";
	public static final String Vocabulary_Ref = "vocabulary.ref";
	public static final String Vocabulary_Info = "vocabulary.info";
	public static final String Vocabulary_ID = "vocabulary.id";
	public static final String Vocabulary_Location = "vocabulary.location";
	public static final String Vocabulary_Issuer = "issuer";
	public static final String Vocabulary_ID_Empty_Message = "vocabulary.id.empty.message";
	public static final String Vocabulary_Opened_Message = "vocabulary.opened.message";
	public static final String Vocabulary_Save_Success_Message = "vocabulary.save.success.message";

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
	public static final String Base_Vocabualry = "base.vocabulary";
	public static final String Basic_Info = "basic.info";

	public static final String User_Container = "user.container";
	public static final String User_Container_ID = "user.container.id";
	public static final String User_Ref = "user.ref";
	public static final String User_Category = "user.category";
	public static final String User_Category_ID = "user.category.id";
	public static final String User_Category_Parent_ID = "user.category.parent.id";
	public static final String User_Container_ID_Empty_Message = "user.container.id.empty.message";
	public static final String User_Category_ID_Empty_Message = "user.category.id.empty.message";
	public static final String User_Category_ID_Unique_Message = "user.category.id.unique.message";
	public static final String User_Category_Parent_Same_Message = "user.category.parent.same.message";
	public static final String User_Category_Parent_Cycle_Message = "user.category.parent.cycle.message";
	public static final String User_Category_Exclude_Unique_Message = "user.category.exclude.unique.message";
	public static final String User_Category_Exclude_Not_Exist_Message = "user.category.exclude.not.exist.message";

	public static final String Data_Ref = "data.ref";
	public static final String Data_Association = "data.association";
	public static final String Data_Container = "data.container";
	public static final String Data_Container_ID = "data.container.id";
	public static final String Data_Category = "data.category";
	public static final String Data_Category_ID = "data.category.id";
	public static final String Data_Category_Parent_ID = "data.category.parent.id";
	public static final String Data_Container_ID_Empty_Message = "data.container.id.empty.message";
	public static final String Data_Category_ID_Empty_Message = "data.category.id.empty.message";
	public static final String Data_Category_ID_Unique_Message = "data.category.id.unique.message";
	public static final String Data_Category_Parent_Same_Message = "data.category.parent.same.message";
	public static final String Data_Category_Parent_Cycle_Message = "data.category.parent.cycle.message";
	public static final String Data_Category_Exclude_Unique_Message = "data.category.exclude.unique.message";
	public static final String Data_Category_Exclude_Not_Exist_Message = "data.category.exclude.not.exist.message";

	public static final String Policy = "policy";
	public static final String Policy_ID = "policy.id";
	public static final String Policy_Info = "policy.info";
	public static final String Policy_Rules = "policy.rules";
	public static final String Policy_Opened_Message = "policy.opened.message";
	public static final String Policy_Save_Success_Message = "policy.save.success.message";

	public static final String Rule = "rule";
	public static final String Rule_Type = "rule.type";
	public static final String Rule_ID = "rule.id";
	public static final String Rule_ID_Non_Empty_Message = "rule.id.none.empty.message";
	public static final String Rule_User_Ref_Non_Empty_Message = "rule.user.ref.non.empty.message";
	public static final String Rule_User_Ref_Unique_Message = "rule.user.ref.unique.message";
	public static final String Rule_Data_Ref_Non_Empty_Message = "rule.data.ref.non.empty.message";
	public static final String Rule_Data_Ref_Unique_Message = "rule.user.ref.unique.message";
	public static final String Rule_Desensitize_Operation_Unique_Message = "rule.desensitize.operation.unique.message";
	public static final String Rule_Desensitize_Operation_Not_Exist_Message = "rule.desensitize.operation.not.exist.message";
	public static final String Rule_Restriction_Not_Empty_Message = "rule.restriction.not.empty.message";
	public static final String Rule_Data_Association_Non_Overlap_Message = "rule.data.association.non.overlap.message";
	public static final String Rule_Restriction_Effective_Message = "rule.restriction.effective.message";
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

	public static String getMessage(String key, String... contents) {
		try {
			String msg = bundle.getString(key);
			return MessageFormat.format(msg, contents);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
