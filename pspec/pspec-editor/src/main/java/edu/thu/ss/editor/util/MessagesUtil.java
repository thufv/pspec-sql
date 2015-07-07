package edu.thu.ss.editor.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesUtil {

	private static ResourceBundle bundle = ResourceBundle.getBundle("message", Locale.US);

	public static final String Editor_Name = "editor.name";

	public static final String File = "file";
	public static final String New = "new";
	public static final String Add = "add";
	public static final String Delete = "delete";

	public static final String Open = "open";
	public static final String New_Vocabulary = "new.vocabulary";
	public static final String New_Policy = "new.policy";
	public static final String Save = "save";
	public static final String Exit = "exit";
	public static final String Edit = "edit";
	public static final String Help = "help";
	public static final String About = "about";
	public static final String OK = "ok";
	public static final String Cancel = "cancel";

	public static final String Vocabulary = "vocabulary";
	public static final String Vocabulary_Ref = "vocabulary.ref";
	public static final String Vocabulary_Info = "vocabulary.info";
	public static final String Vocabulary_ID = "vocabulary.id";
	public static final String Vocabulary_Location = "vocabulary.location";
	public static final String Vocabulary_Issuer = "issuer";

	public static final String Issuer = "issuer";
	public static final String Issuer_Name = "issuer.name";
	public static final String Issuer_Organization = "issuer.organization";
	public static final String Issuer_Email = "issuer.email";
	public static final String Issuer_Address = "issuer.address";
	public static final String Issuer_Country = "issuer.country";

	public static final String Desensitize = "desensitize";
	public static final String Desensitize_Operation = "desensitize.operation";

	public static final String Short_Description = "short.description";
	public static final String Long_Description = "long.description";

	public static final String Base_ID = "base.id";

	public static final String Basic_Info = "basic.info";
	public static final String Data_Ref = "data.ref";
	public static final String Data_Association = "data.association";
	public static final String Data_Container = "data.container";
	public static final String Data_Container_ID = "data.container.id";
	public static final String Data_Category = "data.category";
	public static final String Data_Category_ID = "data.category.id";
	public static final String Data_Category_Parent_ID = "data.category.parent.id";

	public static final String User_Container = "user.container";
	public static final String User_Container_ID = "user.container.id";
	public static final String User_Ref = "user.ref";
	public static final String User_Category = "user.category";
	public static final String User_Category_ID = "user.category.id";
	public static final String User_Category_Parent_ID = "user.category.parent.id";

	public static final String Policy = "policy";
	public static final String Policy_ID = "policy.id";
	public static final String Policy_Info = "policy.info";
	public static final String Policy_Rules = "policy.rules";

	public static final String Rule = "rule";
	public static final String Rule_Type = "rule.type";
	public static final String Rule_ID = "rule.id";
	public static final String Forbid = "forbid";
	public static final String Restriction = "restriction";
	public static final String Restrict = "restrict";

	public static final String Output = "output";

	public static final String Exclude = "exclude";
	public static final String ACTION = "action";
	public static final String Single = "single";
	public static final String Association = "association";

	public static String getMessage(String key) {
		try {
			return bundle.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
