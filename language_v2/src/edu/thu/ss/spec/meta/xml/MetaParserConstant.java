package edu.thu.ss.spec.meta.xml;

public interface MetaParserConstant {
	public static final String Meta_Schema_Location = "res/meta.xsd";

	public static final String Ele_Root = "meta-mapping";
	public static final String Ele_Database = "database";
	public static final String Ele_Table = "table";
	public static final String Ele_Column = "column";
	public static final String Ele_Desensitize_Operation = "desensitize-operation";
	public static final String Ele_Desensitize_UDF = "UDF";
	public static final String Ele_Condition = "condition";
	public static final String Ele_Join = "join";
	public static final String Ele_Join_Column = "column";

	public static final String Attr_Name = "name";
	public static final String Attr_Data_Category = "data-category";
	public static final String Attr_Join_Table = "table";
	public static final String Attr_Join_Target = "target";
	public static final String Attr_Join_Column = "name";

	public static final String Attr_Policy = "policy";
}
