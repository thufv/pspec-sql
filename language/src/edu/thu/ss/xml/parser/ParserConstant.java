package edu.thu.ss.xml.parser;

public interface ParserConstant {

	public static final String Attr_Id = "id";
	public static final String Attr_Refid = "refid";
	public static final String Attr_Parent = "parent";
	public static final String Attr_Policy_Default_Ruling = "default-ruling";
	public static final String Attr_Vocabulary_Base = "base";
	public static final String Attr_Restriction_Type = "type";
	public static final String Attr_Policy_Data_Action = "action";

	public static final String Ele_Contact_Address = "address";
	public static final String Ele_Contact_Country = "country";
	public static final String Ele_Contact_Email = "e-mail";
	public static final String Ele_Contact_Name = "name";
	public static final String Ele_Contact_Organization = "organization";
	public static final String Ele_Long_Description = "long-description";

	public static final String Ele_Vocabulary = "vocabulary";
	public static final String Ele_Vocabulary_Info = "vocabulary-information";
	public static final String Ele_Vocabulary_User_Category_Container = "user-category-container";
	public static final String Ele_Vocabulary_User_Category = "user-category";
	public static final String Ele_Vocabulary_Data_Category_Container = "data-category-container";
	public static final String Ele_Vocabulary_Data_Category = "data-category";

	public static final String Ele_Vocabulary_Desensitize_Op = "desensitize-operation";
	public static final String Ele_Vocabulary_Desensitize_UDF = "UDF";
	public static final String Ele_Vocabulary_Desensitize_Class = "class";

	public static final String Ele_Policy = "privacy-policy";
	public static final String Ele_Policy_Info = "policy-information";
	public static final String Ele_Policy_Issuer = "issuer";
	public static final String Ele_Policy_Location = "location";
	public static final String Ele_Policy_Rule_Allow = "allow";
	public static final String Ele_Policy_Rule_Deny = "deny";
	public static final String Ele_Policy_Rule_Restrict = "restrict";
	public static final String Ele_Policy_Rule_UserRef = "user-category-ref";
	public static final String Ele_Policy_Rule_DataRef = "data-category-ref";
	public static final String Ele_Policy_Rule_DataAsscoation = "data-association";
	public static final String Ele_Policy_Rule_Action = "action";
	public static final String Ele_Policy_Rule_Restriction = "restriction";
	public static final String Ele_Policy_Rule_Desensitize = "desensitize";
	public static final String Ele_Policy_Rule_Aggregate = "aggregate";
	public static final Object Ele_Policy_Rule_Desensitize_UDF = "UDF";

	public static final String Attr_Policy_Vocabulary_location = "location";
	public static final String Ele_Policy_Vocabulary_User = "user-category-container";
	public static final String Ele_Policy_Vocabulary_Data = "data-category-container";
	public static final String Ele_Policy_Refid = "refid";
	public static final String Ele_Policy_Rules = "rules";
	public static final String Ele_Policy_Vocabulary_Ref = "vocabulary-ref";
	public static final String Ele_Short_Description = "short-description";
	public static final String Schema_Location = "src/privacy.xsd";

}
